package com.pragma.frauddetector.infrastructure.adapter;


import com.pragma.frauddetector.domain.model.FraudAnalysisResult;
import com.pragma.frauddetector.domain.model.Transaction;
import com.pragma.frauddetector.domain.port.FraudAnalysisService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class FraudAnalysisAdapter implements FraudAnalysisService {

    private static final Logger log = LoggerFactory.getLogger(FraudAnalysisAdapter.class);
    private static final double DEFAULT_FRAUD_SCORE = 0.0;
    private static final int MAX_CONCURRENT_ANALYSES = 100;

    private final RestTemplate restTemplate;
    private final String fraudAnalysisEndpoint;
    private final AtomicInteger currentWorkload;
    private final AtomicReference<Double> resourceUtilization;

    public FraudAnalysisAdapter(
            RestTemplate restTemplate,
            @Value("${frauddetector.analysis.endpoint:http://localhost:8081/api/analyze}") String fraudAnalysisEndpoint) {
        this.restTemplate = restTemplate;
        this.fraudAnalysisEndpoint = fraudAnalysisEndpoint;
        this.currentWorkload = new AtomicInteger(50);
        this.resourceUtilization = new AtomicReference<>(0.5);
    }

    @Override
    @CircuitBreaker(name = "fraudAnalysisCircuitBreaker", fallbackMethod = "analyzeFallback")
    @Retry(name = "fraudAnalysisRetry")
    public Transaction.FraudAnalysisResult analyzeTransaction(Transaction transaction) {
        log.info("Analizando transacción {} con agente IA", transaction.id());

        currentWorkload.incrementAndGet();
        updateResourceUtilization();

        try {
            Map<String, Object> requestBody = buildAnalysisRequest(transaction);

            Map<String, Object> response = invokeExternalAnalysisService(requestBody);

            return parseAnalysisResponse(response, transaction);
        } catch (Exception e) {
            log.error("Error al analizar transacción {}: {}", transaction.id(), e.getMessage());
            return createDefaultAnalysisResult();
        } finally {
            currentWorkload.decrementAndGet();
            updateResourceUtilization();
        }
    }

    @Override
    @CircuitBreaker(name = "fraudAnalysisBatchCircuitBreaker", fallbackMethod = "analyzeBatchFallback")
    public List<Transaction.FraudAnalysisResult> analyzeBatch(List<Transaction> transactions) {
        log.info("Analizando lote de {} transacciones con agentes IA", transactions.size());

        List<Transaction.FraudAnalysisResult> results = new ArrayList<>();
        int batchWorkloadIncrement = transactions.size();
        currentWorkload.addAndGet(batchWorkloadIncrement);
        updateResourceUtilization();

        try {
            for (Transaction transaction : transactions) {
                Transaction.FraudAnalysisResult result = analyzeTransaction(transaction);
                results.add(result);
            }
            log.info("Análisis por lote completado: {} transacciones procesadas", results.size());
        } catch (Exception e) {
            log.error("Error en análisis por lote: {}", e.getMessage());
            for (int i = 0; i < transactions.size(); i++) {
                results.add(createDefaultAnalysisResult());
            }
        } finally {
            currentWorkload.addAndGet(-batchWorkloadIncrement);
            updateResourceUtilization();
        }

        return results;
    }

    @Override
    public void adjustWorkload(int currentLoad) {
        log.info("Ajustando carga de trabajo de agentes IA a: {}", currentLoad);
        int clampedLoad = Math.max(0, Math.min(MAX_CONCURRENT_ANALYSES, currentLoad));
        currentWorkload.set(clampedLoad);
        updateResourceUtilization();
        log.info("Carga de trabajo ajustada exitosamente a: {}", clampedLoad);
    }

    @Override
    public int getCurrentWorkload() {
        return currentWorkload.get();
    }

    @Override
    public double getResourceUtilization() {
        return resourceUtilization.get();
    }

    private void updateResourceUtilization() {
        double utilization = (double) currentWorkload.get() / MAX_CONCURRENT_ANALYSES;
        resourceUtilization.set(utilization);
        log.debug("Utilización de recursos actualizada: {}", utilization);
    }

    private Map<String, Object> buildAnalysisRequest(Transaction transaction) {
        Map<String, Object> request = new HashMap<>();
        request.put("transactionId", transaction.id().toString());
        request.put("customerId", transaction.customerId());
        request.put("amount", transaction.amount().doubleValue());
        request.put("currency", transaction.currency());
        request.put("type", transaction.type().name());
        request.put("merchantId", transaction.merchantId());
        request.put("timestamp", transaction.timestamp().toString());
        return request;
    }

    private Map<String, Object> invokeExternalAnalysisService(Map<String, Object> requestBody) {
        try {
            Map<String, Object> response = restTemplate.postForObject(
                    fraudAnalysisEndpoint,
                    requestBody,
                    Map.class
            );
            return response != null ? response : createDefaultResponse();
        } catch (Exception e) {
            log.warn("Servicio de análisis externo no disponible, usando análisis local: {}", e.getMessage());
            return performLocalAnalysis(requestBody);
        }
    }

    private Map<String, Object> performLocalAnalysis(Map<String, Object> request) {
        Map<String, Object> localResult = new HashMap<>();

        double amount = ((Number) request.get("amount")).doubleValue();
        String type = (String) request.get("type");

        double fraudScore = calculateFraudScore(amount, type);
        boolean isFraud = fraudScore > 0.7;

        localResult.put("fraudScore", fraudScore);
        localResult.put("fraudIndicator", isFraud);
        localResult.put("details", generateAnalysisDetails(fraudScore, amount, type));
        localResult.put("analyzedAt", LocalDateTime.now().toString());

        return localResult;
    }

    private double calculateFraudScore(double amount, String type) {
        double baseScore = 0.1;

        if (amount > 10000) {
            baseScore += 0.3;
        } else if (amount > 5000) {
            baseScore += 0.2;
        } else if (amount > 1000) {
            baseScore += 0.1;
        }

        if ("TRANSFER".equalsIgnoreCase(type)) {
            baseScore += 0.15;
        } else if ("WITHDRAWAL".equalsIgnoreCase(type)) {
            baseScore += 0.1;
        }

        return Math.min(1.0, baseScore);
    }

    private String generateAnalysisDetails(double score, double amount, String type) {
        return String.format(
                "Análisis automático: score=%.2f, amount=%.2f, type=%s, recommendation=%s",
                score, amount, type, score > 0.7 ? "RECHAZAR" : score > 0.4 ? "REVISAR" : "APROBAR"
        );
    }

    private Transaction.FraudAnalysisResult parseAnalysisResponse(Map<String, Object> response, Transaction transaction) {
        double fraudScore = response.containsKey("fraudScore")
                ? ((Number) response.get("fraudScore")).doubleValue()
                : DEFAULT_FRAUD_SCORE;

        boolean isFraudIndicator = response.containsKey("fraudIndicator")
                && (Boolean) response.get("fraudIndicator");

        String details = response.containsKey("details")
                ? (String) response.get("details")
                : "Sin detalles disponibles";

        LocalDateTime analyzedAt = response.containsKey("analyzedAt")
                ? LocalDateTime.parse((String) response.get("analyzedAt"))
                : LocalDateTime.now();

        log.info("Resultado del análisis para {}: score={}, fraud={}",
                transaction.id(), fraudScore, isFraudIndicator);

        return new Transaction.FraudAnalysisResult(
                fraudScore,
                isFraudIndicator,
                details,
                analyzedAt
        );
    }

    private Transaction.FraudAnalysisResult createDefaultAnalysisResult() {
        return new Transaction.FraudAnalysisResult(
                DEFAULT_FRAUD_SCORE,
                false,
                "Análisis no disponible - modo offline",
                LocalDateTime.now()
        );
    }

    private Map<String, Object> createDefaultResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("fraudScore", DEFAULT_FRAUD_SCORE);
        response.put("fraudIndicator", false);
        response.put("details", "Respuesta por defecto");
        response.put("analyzedAt", LocalDateTime.now().toString());
        return response;
    }

    private Transaction.FraudAnalysisResult analyzeFallback(Transaction transaction, Throwable t) {
        log.warn("Circuit breaker activado para análisis de transacción {}. Usando resultado por defecto. Error: {}",
                transaction.id(), t.getMessage());
        return createDefaultAnalysisResult();
    }

    private List<Transaction.FraudAnalysisResult> analyzeBatchFallback(List<Transaction> transactions, Throwable t) {
        log.warn("Circuit breaker activado para análisis por lote. Usando resultados por defecto. Error: {}",
                t.getMessage());
        return transactions.stream()
                .map(txn -> createDefaultAnalysisResult())
                .toList();
    }
}
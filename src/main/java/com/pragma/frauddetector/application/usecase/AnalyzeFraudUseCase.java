package com.pragma.frauddetector.application.usecase;

import com.pragma.frauddetector.domain.model.Transaction;
import com.pragma.frauddetector.domain.model.Transaction.FraudAnalysisResult;
import com.pragma.frauddetector.domain.model.Transaction.TransactionStatus;
import com.pragma.frauddetector.domain.port.TransactionRepository;
import com.pragma.frauddetector.domain.port.FraudAnalysisService;
import com.pragma.frauddetector.domain.port.ResourceOptimizer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Caso de uso para analizar patrones de fraude en transacciones.
 * Coordina la recuperación de transacciones pendientes, el análisis mediante
 * agentes IA y la optimización dinámica de recursos.
 */
public class AnalyzeFraudUseCase {

    private final TransactionRepository transactionRepository;
    private final FraudAnalysisService fraudAnalysisService;
    private final ResourceOptimizer resourceOptimizer;

    private static final int DEFAULT_BATCH_SIZE = 100;
    private static final int MAX_RETRY_ATTEMPTS = 3;

    public AnalyzeFraudUseCase(
            TransactionRepository transactionRepository,
            FraudAnalysisService fraudAnalysisService,
            ResourceOptimizer resourceOptimizer) {
        this.transactionRepository = transactionRepository;
        this.fraudAnalysisService = fraudAnalysisService;
        this.resourceOptimizer = resourceOptimizer;
    }

    /**
     * Analiza todas las transacciones pendientes de fraude.
     * @return lista de resultados del análisis
     */
    public List<FraudAnalysisResult> execute() {
        List<Transaction> pendingTransactions = transactionRepository.findByFraudAnalysisRequired();
        
        if (pendingTransactions.isEmpty()) {
            return List.of();
        }

        adjustResourceLoad(pendingTransactions.size());
        
        List<Transaction> batch = pendingTransactions.stream()
            .limit(DEFAULT_BATCH_SIZE)
            .toList();

        return analyzeBatch(batch);
    }

    /**
     * Analiza una transacción específica por su ID.
     * @param transactionId ID de la transacción a analizar
     * @return resultado del análisis de fraude
     */
    public FraudAnalysisResult executeById(UUID transactionId) {
        if (transactionId == null) {
            throw new IllegalArgumentException("El ID de transacción no puede ser nulo");
        }

        Transaction transaction = transactionRepository.findById(transactionId)
            .orElseThrow(() -> new IllegalArgumentException(
                "Transacción no encontrada con ID: " + transactionId));

        FraudAnalysisResult result = performAnalysis(transaction);
        
        transactionRepository.updateFraudAnalysisResult(transactionId, result);
        
        updateResourceOptimization();
        
        return result;
    }

    /**
     * Analiza transacciones de un cliente específico.
     * @param customerId ID del cliente
     * @return lista de resultados del análisis
     */
    public List<FraudAnalysisResult> executeByCustomer(String customerId) {
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("El ID del cliente no puede estar vacío");
        }

        List<Transaction> customerTransactions = transactionRepository.findByCustomerId(customerId);
        
        List<Transaction> pendingAnalysis = customerTransactions.stream()
            .filter(t -> t.status() == TransactionStatus.ANALYSIS_PENDING)
            .toList();

        if (pendingAnalysis.isEmpty()) {
            return List.of();
        }

        return analyzeBatch(pendingAnalysis);
    }

    /**
     * Analiza transacciones en un rango de tiempo específico.
     * @param start fecha de inicio
     * @param end fecha de fin
     * @return lista de resultados del análisis
     */
    public List<FraudAnalysisResult> executeByTimeRange(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Las fechas de inicio y fin son obligatorias");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin");
        }

        List<Transaction> transactionsInRange = transactionRepository.findByTimestampBetween(start, end);
        
        List<Transaction> pendingAnalysis = transactionsInRange.stream()
            .filter(t -> t.status() == TransactionStatus.ANALYSIS_PENDING)
            .toList();

        if (pendingAnalysis.isEmpty()) {
            return List.of();
        }

        return analyzeBatch(pendingAnalysis);
    }

    /**
     * Analiza un lote de transacciones utilizando el servicio de análisis de fraude.
     * @param transactions lista de transacciones a analizar
     * @return lista de resultados del análisis
     */
    private List<FraudAnalysisResult> analyzeBatch(List<Transaction> transactions) {
        List<FraudAnalysisResult> results = new ArrayList<>();
        
        int attempt = 0;
        Exception lastException = null;
        
        while (attempt < MAX_RETRY_ATTEMPTS) {
            try {
                results = fraudAnalysisService.analyzeBatch(transactions);
                break;
            } catch (Exception e) {
                lastException = e;
                attempt++;
                if (attempt < MAX_RETRY_ATTEMPTS) {
                    try {
                        Thread.sleep(attempt * 1000L);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Análisis interrumpido", ie);
                    }
                }
            }
        }

        if (results.isEmpty() && lastException != null) {
            throw new RuntimeException("Error en el análisis por lotes después de " 
                + MAX_RETRY_ATTEMPTS + " intentos", lastException);
        }

        for (int i = 0; i < transactions.size() && i < results.size(); i++) {
            Transaction transaction = transactions.get(i);
            FraudAnalysisResult result = results.get(i);
            
            transactionRepository.updateFraudAnalysisResult(transaction.id(), result);
        }

        updateResourceOptimization();
        
        return results;
    }

    /**
     * Realiza el análisis de una transacción individual.
     * @param transaction transacción a analizar
     * @return resultado del análisis
     */
    private FraudAnalysisResult performAnalysis(Transaction transaction) {
        return fraudAnalysisService.analyzeTransaction(transaction);
    }

    /**
     * Ajusta la carga de recursos basada en el número de transacciones pendientes.
     * @param pendingCount número de transacciones pendientes
     */
    private void adjustResourceLoad(int pendingCount) {
        int currentWorkload = fraudAnalysisService.getCurrentWorkload();
        double utilization = fraudAnalysisService.getResourceUtilization();
        
        int totalLoad = currentWorkload + pendingCount;
        resourceOptimizer.adjustWorkload(totalLoad);
        
        if (utilization > 0.8 && pendingCount > DEFAULT_BATCH_SIZE) {
            int optimalAgents = resourceOptimizer.getOptimalAgentCount();
            if (optimalAgents > 0) {
                resourceOptimizer.adjustWorkload(pendingCount / optimalAgents);
            }
        }
    }

    /**
     * Actualiza la optimización de recursos después de completar análisis.
     */
    private void updateResourceOptimization() {
        List<Transaction> remainingPending = transactionRepository.findByFraudAnalysisRequired();
        int remainingCount = remainingPending.size();
        
        resourceOptimizer.adjustWorkload(remainingCount);
        
        if (!resourceOptimizer.canAcceptMoreLoad() && remainingCount > 0) {
            int optimalAgents = resourceOptimizer.getOptimalAgentCount();
            if (optimalAgents > 0) {
                int adjustedLoad = remainingCount / optimalAgents;
                resourceOptimizer.adjustWorkload(adjustedLoad);
            }
        }
    }
}
package com.pragma.frauddetector.application.usecase;

import com.pragma.frauddetector.domain.model.Transaction;
import com.pragma.frauddetector.domain.port.FraudAnalysisService;
import com.pragma.frauddetector.domain.port.ResourceOptimizer;
import com.pragma.frauddetector.domain.port.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OptimizeResourcesUseCase {

    private static final Logger log = LoggerFactory.getLogger(OptimizeResourcesUseCase.class);
    private static final double OPTIMAL_UTILIZATION_THRESHOLD = 0.75;
    private static final double LOW_UTILIZATION_THRESHOLD = 0.30;
    private static final int MAX_WORKLOAD = 100;
    private static final int MIN_WORKLOAD = 10;

    private final TransactionRepository transactionRepository;
    private final FraudAnalysisService fraudAnalysisService;
    private final ResourceOptimizer resourceOptimizer;

    public OptimizeResourcesUseCase(
            TransactionRepository transactionRepository,
            FraudAnalysisService fraudAnalysisService,
            ResourceOptimizer resourceOptimizer) {
        this.transactionRepository = transactionRepository;
        this.fraudAnalysisService = fraudAnalysisService;
        this.resourceOptimizer = resourceOptimizer;
    }

    public OptimizationResult optimizeResourceAllocation() {
        log.info("Iniciando optimización de recursos de agentes IA");

        double currentUtilization = fraudAnalysisService.getResourceUtilization();
        int currentWorkload = fraudAnalysisService.getCurrentWorkload();

        log.info("Utilización actual: {}, Carga de trabajo actual: {}", currentUtilization, currentWorkload);

        List<Transaction> pendingAnalysis = transactionRepository.findByFraudAnalysisRequired();
        int pendingCount = pendingAnalysis.size();

        log.info("Transacciones pendientes de análisis: {}", pendingCount);

        int optimizedWorkload = calculateOptimalWorkload(currentUtilization, pendingCount, currentWorkload);

        if (optimizedWorkload != currentWorkload) {
            fraudAnalysisService.adjustWorkload(optimizedWorkload);
            log.info("Carga de trabajo ajustada de {} a {}", currentWorkload, optimizedWorkload);
        }

        double projectedEfficiency = calculateProjectedEfficiency(optimizedWorkload, pendingCount);
        double projectedResponseTime = calculateProjectedResponseTime(optimizedWorkload, pendingCount);

        return new OptimizationResult(
                optimizedWorkload,
                currentUtilization,
                pendingCount,
                projectedEfficiency,
                projectedResponseTime,
                LocalDateTime.now()
        );
    }

    private int calculateOptimalWorkload(double utilization, int pendingCount, int currentWorkload) {
        if (pendingCount == 0) {
            log.info("No hay transacciones pendientes, reduciendo carga de trabajo");
            return Math.max(MIN_WORKLOAD, currentWorkload - 20);
        }

        if (utilization > OPTIMAL_UTILIZATION_THRESHOLD) {
            log.info("Utilización alta, aumentando carga para procesar más rápido");
            return Math.min(MAX_WORKLOAD, currentWorkload + 15);
        }

        if (utilization < LOW_UTILIZATION_THRESHOLD) {
            log.info("Utilización baja, reduciendo carga para ahorrar recursos");
            return Math.max(MIN_WORKLOAD, currentWorkload - 10);
        }

        int estimatedNeededWorkload = Math.min(MAX_WORKLOAD, Math.max(MIN_WORKLOAD, pendingCount / 10));
        log.info("Carga estimada necesaria: {}", estimatedNeededWorkload);
        return estimatedNeededWorkload;
    }

    private double calculateProjectedEfficiency(int workload, int pendingCount) {
        if (workload == 0) return 0.0;
        double throughput = (double) workload / 100.0;
        double efficiency = Math.min(1.0, throughput * (1.0 + (pendingCount > 100 ? 0.2 : 0.0)));
        return Math.round(efficiency * 100.0) / 100.0;
    }

    private double calculateProjectedResponseTime(int workload, int pendingCount) {
        if (workload == 0 || pendingCount == 0) return 0.0;
        double baseTime = 100.0;
        double workloadFactor = baseTime * (1.0 - (workload / (double) MAX_WORKLOAD) * 0.5);
        double pendingFactor = 1.0 + (pendingCount / 1000.0);
        double responseTime = workloadFactor * pendingFactor;
        return Math.round(responseTime * 100.0) / 100.0;
    }

    public record OptimizationResult(
            int optimizedWorkload,
            double currentUtilization,
            int pendingTransactions,
            double projectedEfficiency,
            double projectedResponseTimeMs,
            LocalDateTime timestamp
    ) {
        public String summary() {
            return String.format(
                    "Optimización: carga=%d, utilización=%.2f, eficiencia proyectada=%.2f, tiempo respuesta=%.2fms",
                    optimizedWorkload, currentUtilization, projectedEfficiency, projectedResponseTimeMs
            );
        }
    }
}
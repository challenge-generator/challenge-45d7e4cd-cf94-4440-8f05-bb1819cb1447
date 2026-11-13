package com.pragma.frauddetector.application.usecase;

import com.pragma.frauddetector.domain.model.Transaction;
import com.pragma.frauddetector.domain.model.Transaction.TransactionStatus;
import com.pragma.frauddetector.domain.model.Transaction.TransactionType;
import com.pragma.frauddetector.domain.port.TransactionRepository;
import com.pragma.frauddetector.domain.port.FraudAnalysisService;
import com.pragma.frauddetector.domain.port.ResourceOptimizer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Caso de uso para registrar una nueva transacción en el sistema.
 * Maneja la validación de datos, persistencia y coordinación con el servicio
 * de análisis de fraude y el optimizador de recursos.
 */
public class RegisterTransactionUseCase {

    private final TransactionRepository transactionRepository;
    private final FraudAnalysisService fraudAnalysisService;
    private final ResourceOptimizer resourceOptimizer;

    private static final BigDecimal HIGH_RISK_AMOUNT_THRESHOLD = new BigDecimal("10000.00");
    private static final int BATCH_THRESHOLD = 50;

    public RegisterTransactionUseCase(
            TransactionRepository transactionRepository,
            FraudAnalysisService fraudAnalysisService,
            ResourceOptimizer resourceOptimizer) {
        this.transactionRepository = transactionRepository;
        this.fraudAnalysisService = fraudAnalysisService;
        this.resourceOptimizer = resourceOptimizer;
    }

    /**
     * Registra una nueva transacción en el sistema.
     * @param customerId identificador del cliente
     * @param amount monto de la transacción
     * @param type tipo de transacción
     * @param description descripción de la transacción
     * @return la transacción registrada con su estado
     */
    public Transaction execute(String customerId, BigDecimal amount, 
                               TransactionType type, String description) {
        validateInput(customerId, amount, type);

        Transaction transaction = new Transaction(
            UUID.randomUUID(),
            customerId,
            amount,
            type,
            description,
            LocalDateTime.now(),
            TransactionStatus.PENDING,
            null
        );

        boolean requiresFraudAnalysis = determineFraudAnalysisRequirement(transaction);
        
        if (requiresFraudAnalysis) {
            transaction = new Transaction(
                transaction.id(),
                transaction.customerId(),
                transaction.amount(),
                transaction.type(),
                transaction.description(),
                transaction.timestamp(),
                TransactionStatus.ANALYSIS_PENDING,
                null
            );
        }

        Transaction savedTransaction = transactionRepository.save(transaction);
        
        adjustResourceOptimization();
        
        return savedTransaction;
    }

    /**
     * Registra múltiples transacciones en lote.
     * @param transactions lista de transacciones a registrar
     * @return lista de transacciones registradas
     */
    public List<Transaction> executeBatch(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            throw new IllegalArgumentException("La lista de transacciones no puede estar vacía");
        }

        for (Transaction transaction : transactions) {
            validateTransaction(transaction);
        }

        List<Transaction> savedTransactions = transactions.stream()
            .map(transactionRepository::save)
            .toList();

        adjustResourceOptimization();
        
        return savedTransactions;
    }

    private void validateInput(String customerId, BigDecimal amount, TransactionType type) {
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("El ID del cliente no puede estar vacío");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        if (type == null) {
            throw new IllegalArgumentException("El tipo de transacción es obligatorio");
        }
    }

    private void validateTransaction(Transaction transaction) {
        if (transaction.customerId() == null || transaction.customerId().isBlank()) {
            throw new IllegalArgumentException("El ID del cliente no puede estar vacío");
        }
        if (transaction.amount() == null || transaction.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        if (transaction.type() == null) {
            throw new IllegalArgumentException("El tipo de transacción es obligatorio");
        }
    }

    private boolean determineFraudAnalysisRequirement(Transaction transaction) {
        if (transaction.amount().compareTo(HIGH_RISK_AMOUNT_THRESHOLD) >= 0) {
            return true;
        }
        
        if (transaction.type() == TransactionType.INTERNATIONAL_TRANSFER) {
            return true;
        }

        List<Transaction> recentTransactions = transactionRepository
            .findByCustomerId(transaction.customerId());
        
        long recentCount = recentTransactions.stream()
            .filter(t -> t.timestamp().isAfter(LocalDateTime.now().minusHours(24)))
            .count();
        
        if (recentCount >= BATCH_THRESHOLD) {
            return true;
        }
        
        return false;
    }

    private void adjustResourceOptimization() {
        List<Transaction> pendingAnalysis = transactionRepository.findByFraudAnalysisRequired();
        int currentLoad = pendingAnalysis.size();
        
        if (currentLoad > 0) {
            resourceOptimizer.adjustWorkload(currentLoad);
        }
    }
}
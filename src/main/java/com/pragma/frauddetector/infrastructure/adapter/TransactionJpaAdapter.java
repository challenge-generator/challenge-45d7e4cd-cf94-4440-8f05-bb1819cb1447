package com.pragma.frauddetector.infrastructure.adapter;




import com.pragma.frauddetector.domain.model.TransactionType;
import com.pragma.frauddetector.domain.model.FraudAnalysisResult;
import com.pragma.frauddetector.domain.model.TransactionStatus;
import com.pragma.frauddetector.domain.model.Transaction;
import com.pragma.frauddetector.domain.port.TransactionRepository;
import com.pragma.frauddetector.infrastructure.entity.TransactionEntity;
import com.pragma.frauddetector.infrastructure.repository.TransactionJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class TransactionJpaAdapter implements TransactionRepository {

    private static final Logger log = LoggerFactory.getLogger(TransactionJpaAdapter.class);

    private final TransactionJpaRepository transactionJpaRepository;

    public TransactionJpaAdapter(TransactionJpaRepository transactionJpaRepository) {
        this.transactionJpaRepository = transactionJpaRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        log.debug("Guardando transacción: {}", transaction.id());
        TransactionEntity entity = toEntity(transaction);
        TransactionEntity savedEntity = transactionJpaRepository.save(entity);
        log.info("Transacción guardada exitosamente con ID: {}", savedEntity.getId());
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        log.debug("Buscando transacción por ID: {}", id);
        return transactionJpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Transaction> findByCustomerId(String customerId) {
        log.debug("Buscando transacciones para cliente: {}", customerId);
        return transactionJpaRepository.findByCustomerId(customerId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByTimestampBetween(LocalDateTime start, LocalDateTime end) {
        log.debug("Buscando transacciones entre {} y {}", start, end);
        return transactionJpaRepository.findByTimestampBetween(start, end)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByStatus(Transaction.TransactionStatus status) {
        log.debug("Buscando transacciones con estado: {}", status);
        return transactionJpaRepository.findByStatus(status.name())
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByFraudAnalysisRequired() {
        log.debug("Buscando transacciones que requieren análisis de fraude");
        return transactionJpaRepository.findByFraudAnalysisRequired()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void updateFraudAnalysisResult(UUID transactionId, Transaction.FraudAnalysisResult result) {
        log.debug("Actualizando resultado de análisis de fraude para transacción: {}", transactionId);
        transactionJpaRepository.findById(transactionId).ifPresent(entity -> {
            entity.setFraudScore(result.fraudScore());
            entity.setFraudIndicator(result.isFraudIndicator());
            entity.setAnalysisDetails(result.details());
            entity.setAnalyzedAt(LocalDateTime.now());
            transactionJpaRepository.save(entity);
            log.info("Resultado de fraude actualizado para transacción: {}", transactionId);
        });
    }

    private TransactionEntity toEntity(Transaction transaction) {
        TransactionEntity entity = new TransactionEntity();
        entity.setId(transaction.id());
        entity.setCustomerId(transaction.customerId());
        entity.setAmount(transaction.amount());
        entity.setCurrency(transaction.currency());
        entity.setType(transaction.type().name());
        entity.setStatus(transaction.status().name());
        entity.setDescription(transaction.description());
        entity.setMerchantId(transaction.merchantId());
        entity.setTimestamp(transaction.timestamp());
        entity.setFraudAnalysisRequired(transaction.fraudAnalysisRequired());

        if (transaction.fraudAnalysisResult() != null) {
            entity.setFraudScore(transaction.fraudAnalysisResult().fraudScore());
            entity.setFraudIndicator(transaction.fraudAnalysisResult().isFraudIndicator());
            entity.setAnalysisDetails(transaction.fraudAnalysisResult().details());
            entity.setAnalyzedAt(transaction.fraudAnalysisResult().analyzedAt());
        }

        return entity;
    }

    private Transaction toDomain(TransactionEntity entity) {
        Transaction.FraudAnalysisResult analysisResult = null;
        if (entity.getFraudAnalysisRequired() != null && entity.getFraudScore() != null) {
            analysisResult = new Transaction.FraudAnalysisResult(
                    entity.getFraudScore(),
                    entity.getFraudIndicator(),
                    entity.getAnalysisDetails(),
                    entity.getAnalyzedAt()
            );
        }

        return new Transaction(
                entity.getId(),
                entity.getCustomerId(),
                entity.getAmount(),
                entity.getCurrency(),
                Transaction.TransactionType.valueOf(entity.getType()),
                Transaction.TransactionStatus.valueOf(entity.getStatus()),
                entity.getDescription(),
                entity.getMerchantId(),
                entity.getTimestamp(),
                entity.getFraudAnalysisRequired(),
                analysisResult
        );
    }
}
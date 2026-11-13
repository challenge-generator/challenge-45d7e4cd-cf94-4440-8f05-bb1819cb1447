package com.pragma.frauddetector.domain.port;



import com.pragma.frauddetector.domain.model.FraudAnalysisResult;
import com.pragma.frauddetector.domain.model.TransactionStatus;
import com.pragma.frauddetector.domain.model.Transaction;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository {
    Transaction save(Transaction transaction);

    Optional<Transaction> findById(UUID id);

    List<Transaction> findByCustomerId(String customerId);

    List<Transaction> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    List<Transaction> findByStatus(Transaction.TransactionStatus status);

    List<Transaction> findByFraudAnalysisRequired();

    void updateFraudAnalysisResult(UUID transactionId, Transaction.FraudAnalysisResult result);
}
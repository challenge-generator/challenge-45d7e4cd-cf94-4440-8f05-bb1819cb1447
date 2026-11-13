package com.pragma.frauddetector.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Transaction(
    UUID id,
    BigDecimal amount,
    LocalDateTime timestamp,
    TransactionType type,
    TransactionStatus status,
    String merchant,
    String customerId,
    String details,
    FraudAnalysisResult fraudAnalysisResult
) {
    public enum TransactionType {
        DEPOSIT,
        WITHDRAWAL,
        TRANSFER,
        PAYMENT,
        REFUND
    }

    public enum TransactionStatus {
        PENDING,
        COMPLETED,
        FAILED,
        REJECTED
    }

    public record FraudAnalysisResult(
        boolean isFraud,
        double fraudScore,
        String analysisDetails,
        LocalDateTime analyzedAt
    ) {
        public FraudAnalysisResult {
            if (fraudScore < 0.0 || fraudScore > 1.0) {
                throw new IllegalArgumentException("Fraud score must be between 0.0 and 1.0");
            }
            if (analysisDetails == null || analysisDetails.isBlank()) {
                throw new IllegalArgumentException("Analysis details cannot be empty");
            }
            if (analyzedAt == null) {
                throw new IllegalArgumentException("Analyzed at timestamp cannot be null");
            }
        }
    }

    public Transaction {
        if (id == null) {
            throw new IllegalArgumentException("Transaction ID cannot be null");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("Transaction type cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Transaction status cannot be null");
        }
        if (merchant == null || merchant.isBlank()) {
            throw new IllegalArgumentException("Merchant cannot be empty");
        }
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("Customer ID cannot be empty");
        }
    }

    public Transaction withFraudAnalysisResult(FraudAnalysisResult result) {
        return new Transaction(
            this.id,
            this.amount,
            this.timestamp,
            this.type,
            this.status,
            this.merchant,
            this.customerId,
            this.details,
            result
        );
    }
}
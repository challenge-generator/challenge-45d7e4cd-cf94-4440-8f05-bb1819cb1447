package com.pragma.frauddetector.domain.port;


import com.pragma.frauddetector.domain.model.FraudAnalysisResult;
import com.pragma.frauddetector.domain.model.Transaction;
import java.util.List;

public interface FraudAnalysisService {
    Transaction.FraudAnalysisResult analyzeTransaction(Transaction transaction);

    List<Transaction.FraudAnalysisResult> analyzeBatch(List<Transaction> transactions);

    void adjustWorkload(int currentLoad);

    int getCurrentWorkload();

    double getResourceUtilization();
}
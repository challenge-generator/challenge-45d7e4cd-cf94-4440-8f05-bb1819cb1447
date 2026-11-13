package com.pragma.frauddetector.application.usecase;

import com.pragma.frauddetector.domain.model.Transaction;
import com.pragma.frauddetector.domain.model.Transaction.FraudAnalysisResult;
import com.pragma.frauddetector.domain.model.Transaction.TransactionStatus;
import com.pragma.frauddetector.domain.model.Transaction.TransactionType;
import com.pragma.frauddetector.domain.port.FraudAnalysisService;
import com.pragma.frauddetector.domain.port.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para AnalyzeFraudUseCase")
class AnalyzeFraudUseCaseTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private FraudAnalysisService fraudAnalysisService;

    @InjectMocks
    private AnalyzeFraudUseCase analyzeFraudUseCase;

    private Transaction transaction;
    private FraudAnalysisResult fraudResult;
    private UUID transactionId;

    @BeforeEach
    void setUp() {
        transactionId = UUID.randomUUID();
        transaction = new Transaction(
            transactionId,
            "CUST-001",
            TransactionType.TRANSFER,
            new BigDecimal("5000.00"),
            "USD",
            LocalDateTime.now(),
            TransactionStatus.PENDING,
            null
        );

        fraudResult = new FraudAnalysisResult(
            false,
            0.15,
            "Medium risk transaction",
            LocalDateTime.now()
        );
    }

    @Test
    @DisplayName("Debe analizar una transacción y retornar resultado de fraude")
    void shouldAnalyzeTransactionAndReturnFraudResult() {
        when(transactionRepository.findById(transactionId)).thenReturn(java.util.Optional.of(transaction));
        when(fraudAnalysisService.analyzeTransaction(transaction)).thenReturn(fraudResult);
        doNothing().when(transactionRepository).updateFraudAnalysisResult(eq(transactionId), any(FraudAnalysisResult.class));

        Transaction result = analyzeFraudUseCase.execute(transactionId);

        assertNotNull(result);
        assertNotNull(result.fraudAnalysisResult());
        assertFalse(result.fraudAnalysisResult().isFraudulent());
        verify(transactionRepository, times(1)).findById(transactionId);
        verify(fraudAnalysisService, times(1)).analyzeTransaction(transaction);
    }

    @Test
    @DisplayName("Debe marcar transacción como fraudulenta cuando el análisis lo indica")
    void shouldMarkTransactionAsFraudulentWhenAnalysisIndicatesSo() {
        FraudAnalysisResult fraudulentResult = new FraudAnalysisResult(
            true,
            0.95,
            "High risk: suspicious pattern detected",
            LocalDateTime.now()
        );

        when(transactionRepository.findById(transactionId)).thenReturn(java.util.Optional.of(transaction));
        when(fraudAnalysisService.analyzeTransaction(transaction)).thenReturn(fraudulentResult);
        doNothing().when(transactionRepository).updateFraudAnalysisResult(eq(transactionId), any(FraudAnalysisResult.class));

        Transaction result = analyzeFraudUseCase.execute(transactionId);

        assertTrue(result.fraudAnalysisResult().isFraudulent());
        assertEquals(0.95, result.fraudAnalysisResult().riskScore());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando la transacción no existe")
    void shouldThrowExceptionWhenTransactionDoesNotExist() {
        UUID nonExistentId = UUID.randomUUID();
        when(transactionRepository.findById(nonExistentId)).thenReturn(java.util.Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            analyzeFraudUseCase.execute(nonExistentId);
        });

        verify(fraudAnalysisService, never()).analyzeTransaction(any(Transaction.class));
    }

    @Test
    @DisplayName("Debe ajustar carga de trabajo del servicio de fraude")
    void shouldAdjustFraudServiceWorkload() {
        when(transactionRepository.findById(transactionId)).thenReturn(java.util.Optional.of(transaction));
        when(fraudAnalysisService.analyzeTransaction(transaction)).thenReturn(fraudResult);
        when(fraudAnalysisService.getCurrentWorkload()).thenReturn(50);
        doNothing().when(transactionRepository).updateFraudAnalysisResult(eq(transactionId), any(FraudAnalysisResult.class));
        doNothing().when(fraudAnalysisService).adjustWorkload(anyInt());

        analyzeFraudUseCase.execute(transactionId);

        verify(fraudAnalysisService, atLeastOnce()).getCurrentWorkload();
    }

    @Test
    @DisplayName("Debe analizar lote de transacciones")
    void shouldAnalyzeBatchOfTransactions() {
        List<Transaction> transactions = List.of(
            new Transaction(UUID.randomUUID(), "CUST-001", TransactionType.DEPOSIT, new BigDecimal("1000.00"), "USD", LocalDateTime.now(), TransactionStatus.PENDING, null),
            new Transaction(UUID.randomUUID(), "CUST-002", TransactionType.WITHDRAWAL, new BigDecimal("2000.00"), "USD", LocalDateTime.now(), TransactionStatus.PENDING, null),
            new Transaction(UUID.randomUUID(), "CUST-003", TransactionType.TRANSFER, new BigDecimal("3000.00"), "USD", LocalDateTime.now(), TransactionStatus.PENDING, null)
        );

        List<FraudAnalysisResult> results = List.of(
            new FraudAnalysisResult(false, 0.1, "Low risk", LocalDateTime.now()),
            new FraudAnalysisResult(false, 0.3, "Medium risk", LocalDateTime.now()),
            new FraudAnalysisResult(true, 0.85, "High risk", LocalDateTime.now())
        );

        when(fraudAnalysisService.analyzeBatch(transactions)).thenReturn(results);

        List<FraudAnalysisResult> batchResults = analyzeFraudUseCase.executeBatch(transactions);

        assertNotNull(batchResults);
        assertEquals(3, batchResults.size());
        verify(fraudAnalysisService, times(1)).analyzeBatch(transactions);
    }

    @Test
    @DisplayName("Debe obtener utilización de recursos del servicio de fraude")
    void shouldGetResourceUtilizationFromFraudService() {
        when(fraudAnalysisService.getResourceUtilization()).thenReturn(0.75);

        double utilization = analyzeFraudUseCase.getResourceUtilization();

        assertEquals(0.75, utilization, 0.001);
        verify(fraudAnalysisService, times(1)).getResourceUtilization();
    }

    @Test
    @DisplayName("Debe propagar excepción del servicio de análisis de fraude")
    void shouldPropagateFraudAnalysisServiceException() {
        when(transactionRepository.findById(transactionId)).thenReturn(java.util.Optional.of(transaction));
        when(fraudAnalysisService.analyzeTransaction(transaction))
            .thenThrow(new RuntimeException("Fraud service unavailable"));

        assertThrows(RuntimeException.class, () -> {
            analyzeFraudUseCase.execute(transactionId);
        });
    }

    @Test
    @DisplayName("Debe actualizar el resultado del análisis en el repositorio")
    void shouldUpdateAnalysisResultInRepository() {
        when(transactionRepository.findById(transactionId)).thenReturn(java.util.Optional.of(transaction));
        when(fraudAnalysisService.analyzeTransaction(transaction)).thenReturn(fraudResult);

        analyzeFraudUseCase.execute(transactionId);

        verify(transactionRepository, times(1)).updateFraudAnalysisResult(eq(transactionId), eq(fraudResult));
    }

    @Test
    @DisplayName("Debe manejar transacciones con alto riesgo correctamente")
    void shouldHandleHighRiskTransactionsCorrectly() {
        FraudAnalysisResult highRiskResult = new FraudAnalysisResult(
            true,
            0.92,
            "Transaction blocked: multiple suspicious indicators",
            LocalDateTime.now()
        );

        when(transactionRepository.findById(transactionId)).thenReturn(java.util.Optional.of(transaction));
        when(fraudAnalysisService.analyzeTransaction(transaction)).thenReturn(highRiskResult);

        Transaction result = analyzeFraudUseCase.execute(transactionId);

        assertTrue(result.fraudAnalysisResult().isFraudulent());
        assertTrue(result.fraudAnalysisResult().riskScore() > 0.8);
    }
}
package com.pragma.frauddetector.application.usecase;

import com.pragma.frauddetector.domain.model.Transaction;
import com.pragma.frauddetector.domain.model.Transaction.TransactionStatus;
import com.pragma.frauddetector.domain.model.Transaction.TransactionType;
import com.pragma.frauddetector.domain.port.ResourceOptimizer;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para OptimizeResourcesUseCase")
class OptimizeResourcesUseCaseTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ResourceOptimizer resourceOptimizer;

    @InjectMocks
    private OptimizeResourcesUseCase optimizeResourcesUseCase;

    private Transaction transaction;
    private UUID transactionId;

    @BeforeEach
    void setUp() {
        transactionId = UUID.randomUUID();
        transaction = new Transaction(
            transactionId,
            "CUST-001",
            TransactionType.TRANSFER,
            new BigDecimal("3000.00"),
            "USD",
            LocalDateTime.now(),
            TransactionStatus.PENDING,
            null
        );
    }

    @Test
    @DisplayName("Debe optimizar recursos cuando la utilización es alta")
    void shouldOptimizeResourcesWhenUtilizationIsHigh() {
        when(resourceOptimizer.getCurrentUtilization()).thenReturn(0.85);
        doNothing().when(resourceOptimizer).reduceLoad(anyInt());

        optimizeResourcesUseCase.execute();

        verify(resourceOptimizer, times(1)).getCurrentUtilization();
        verify(resourceOptimizer, atLeastOnce()).reduceLoad(anyInt());
    }

    @Test
    @DisplayName("Debe aumentar recursos cuando la utilización es baja")
    void shouldIncreaseResourcesWhenUtilizationIsLow() {
        when(resourceOptimizer.getCurrentUtilization()).thenReturn(0.25);
        doNothing().when(resourceOptimizer).increaseLoad(anyInt());

        optimizeResourcesUseCase.execute();

        verify(resourceOptimizer, times(1)).getCurrentUtilization();
        verify(resourceOptimizer, atLeastOnce()).increaseLoad(anyInt());
    }

    @Test
    @DisplayName("Debe mantener recursos estables cuando la utilización es óptima")
    void shouldMaintainStableResourcesWhenUtilizationIsOptimal() {
        when(resourceOptimizer.getCurrentUtilization()).thenReturn(0.60);

        optimizeResourcesUseCase.execute();

        verify(resourceOptimizer, times(1)).getCurrentUtilization();
        verify(resourceOptimizer, never()).reduceLoad(anyInt());
        verify(resourceOptimizer, never()).increaseLoad(anyInt());
    }

    @Test
    @DisplayName("Debe obtener transacciones que requieren análisis de fraude")
    void shouldGetTransactionsRequiringFraudAnalysis() {
        List<Transaction> pendingTransactions = List.of(
            new Transaction(UUID.randomUUID(), "CUST-001", TransactionType.DEPOSIT, new BigDecimal("1000.00"), "USD", LocalDateTime.now(), TransactionStatus.PENDING, null),
            new Transaction(UUID.randomUUID(), "CUST-002", TransactionType.WITHDRAWAL, new BigDecimal("2000.00"), "USD", LocalDateTime.now(), TransactionStatus.PENDING, null)
        );

        when(transactionRepository.findByFraudAnalysisRequired()).thenReturn(pendingTransactions);

        List<Transaction> result = optimizeResourcesUseCase.getPendingTransactions();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(transactionRepository, times(1)).findByFraudAnalysisRequired();
    }

    @Test
    @DisplayName("Debe retornar lista vacía cuando no hay transacciones pendientes")
    void shouldReturnEmptyListWhenNoPendingTransactions() {
        when(transactionRepository.findByFraudAnalysisRequired()).thenReturn(List.of());

        List<Transaction> result = optimizeResourcesUseCase.getPendingTransactions();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Debe calcular optimización basada en métricas de transacciones")
    void shouldCalculateOptimizationBasedOnTransactionMetrics() {
        List<Transaction> transactions = List.of(
            new Transaction(UUID.randomUUID(), "CUST-001", TransactionType.DEPOSIT, new BigDecimal("5000.00"), "USD", LocalDateTime.now(), TransactionStatus.PENDING, null),
            new Transaction(UUID.randomUUID(), "CUST-002", TransactionType.WITHDRAWAL, new BigDecimal("8000.00"), "USD", LocalDateTime.now(), TransactionStatus.PENDING, null),
            new Transaction(UUID.randomUUID(), "CUST-003", TransactionType.TRANSFER, new BigDecimal("12000.00"), "USD", LocalDateTime.now(), TransactionStatus.PENDING, null)
        );

        when(transactionRepository.findByFraudAnalysisRequired()).thenReturn(transactions);
        when(resourceOptimizer.getCurrentUtilization()).thenReturn(0.70);

        double optimizationScore = optimizeResourcesUseCase.calculateOptimizationScore();

        assertTrue(optimizationScore >= 0.0 && optimizationScore <= 1.0);
    }

    @Test
    @DisplayName("Debe ajustar carga dinámicamente según el volumen de transacciones")
    void shouldDynamicallyAdjustLoadAccordingToTransactionVolume() {
        List<Transaction> highVolumeTransactions = List.of(
            new Transaction(UUID.randomUUID(), "CUST-001", TransactionType.DEPOSIT, new BigDecimal("1000.00"), "USD", LocalDateTime.now(), TransactionStatus.PENDING, null),
            new Transaction(UUID.randomUUID(), "CUST-002", TransactionType.WITHDRAWAL, new BigDecimal("1000.00"), "USD", LocalDateTime.now(), TransactionStatus.PENDING, null),
            new Transaction(UUID.randomUUID(), "CUST-003", TransactionType.TRANSFER, new BigDecimal("1000.00"), "USD", LocalDateTime.now(), TransactionStatus.PENDING, null),
            new Transaction(UUID.randomUUID(), "CUST-004", TransactionType.TRANSFER, new BigDecimal("1000.00"), "USD", LocalDateTime.now(), TransactionStatus.PENDING, null),
            new Transaction(UUID.randomUUID(), "CUST-005", TransactionType.DEPOSIT, new BigDecimal("1000.00"), "USD", LocalDateTime.now(), TransactionStatus.PENDING, null)
        );

        when(transactionRepository.findByFraudAnalysisRequired()).thenReturn(highVolumeTransactions);
        when(resourceOptimizer.getCurrentUtilization()).thenReturn(0.90);
        doNothing().when(resourceOptimizer).reduceLoad(anyInt());

        optimizeResourcesUseCase.execute();

        verify(resourceOptimizer, atLeastOnce()).reduceLoad(anyInt());
    }

    @Test
    @DisplayName("Debe propagar excepción del optimizador de recursos")
    void shouldPropagateResourceOptimizerException() {
        when(resourceOptimizer.getCurrentUtilization())
            .thenThrow(new RuntimeException("Resource optimizer service unavailable"));

        assertThrows(RuntimeException.class, () -> {
            optimizeResourcesUseCase.execute();
        });
    }

    @Test
    @DisplayName("Debe obtener métricas de rendimiento del optimizador")
    void shouldGetPerformanceMetricsFromOptimizer() {
        when(resourceOptimizer.getCurrentUtilization()).thenReturn(0.65);
        when(resourceOptimizer.getActiveAgents()).thenReturn(10);

        var metrics = optimizeResourcesUseCase.getPerformanceMetrics();

        assertNotNull(metrics);
        assertEquals(0.65, metrics.utilization());
        assertEquals(10, metrics.activeAgents());
    }

    @Test
    @DisplayName("Debe ejecutar optimización con estrategia de escalado progresivo")
    void shouldExecuteOptimizationWithProgressiveScalingStrategy() {
        when(resourceOptimizer.getCurrentUtilization()).thenReturn(0.55, 0.65, 0.75);

        for (int i = 0; i < 3; i++) {
            optimizeResourcesUseCase.execute();
        }

        verify(resourceOptimizer, times(3)).getCurrentUtilization();
    }

    @Test
    @DisplayName("Debe filtrar transacciones por estado pending correctamente")
    void shouldFilterTransactionsByPendingStatusCorrectly() {
        List<Transaction> mixedStatusTransactions = List.of(
            new Transaction(UUID.randomUUID(), "CUST-001", TransactionType.DEPOSIT, new BigDecimal("1000.00"), "USD", LocalDateTime.now(), TransactionStatus.PENDING, null),
            new Transaction(UUID.randomUUID(), "CUST-002", TransactionType.WITHDRAWAL, new BigDecimal("2000.00"), "USD", LocalDateTime.now(), TransactionStatus.COMPLETED, null),
            new Transaction(UUID.randomUUID(), "CUST-003", TransactionType.TRANSFER, new BigDecimal("3000.00"), "USD", LocalDateTime.now(), TransactionStatus.PENDING, null)
        );

        when(transactionRepository.findByFraudAnalysisRequired()).thenReturn(mixedStatusTransactions);

        List<Transaction> pendingOnly = optimizeResourcesUseCase.getPendingTransactions();

        assertEquals(2, pendingOnly.size());
        pendingOnly.forEach(tx -> assertEquals(TransactionStatus.PENDING, tx.status()));
    }
}
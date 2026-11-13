package com.pragma.frauddetector.application.usecase;

import com.pragma.frauddetector.domain.model.Transaction;
import com.pragma.frauddetector.domain.model.Transaction.TransactionStatus;
import com.pragma.frauddetector.domain.model.Transaction.TransactionType;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para RegisterTransactionUseCase")
class RegisterTransactionUseCaseTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RegisterTransactionUseCase registerTransactionUseCase;

    private Transaction transaction;
    private UUID transactionId;
    private String customerId;
    private BigDecimal amount;

    @BeforeEach
    void setUp() {
        transactionId = UUID.randomUUID();
        customerId = "CUST-001";
        amount = new BigDecimal("1500.00");

        transaction = new Transaction(
            transactionId,
            customerId,
            TransactionType.DEPOSIT,
            amount,
            "USD",
            LocalDateTime.now(),
            TransactionStatus.PENDING,
            null
        );
    }

    @Test
    @DisplayName("Debe registrar una transacción exitosamente")
    void shouldRegisterTransactionSuccessfully() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = registerTransactionUseCase.execute(transaction);

        assertNotNull(result);
        assertEquals(transactionId, result.id());
        assertEquals(customerId, result.customerId());
        assertEquals(TransactionStatus.PENDING, result.status());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Debe guardar la transacción con estado PENDING por defecto")
    void shouldSaveTransactionWithPendingStatusByDefault() {
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> {
            Transaction t = invocation.getArgument(0);
            return new Transaction(
                t.id(),
                t.customerId(),
                t.type(),
                t.amount(),
                t.currency(),
                t.timestamp(),
                TransactionStatus.PENDING,
                t.fraudAnalysisResult()
            );
        });

        Transaction inputTransaction = new Transaction(
            UUID.randomUUID(),
            "CUST-002",
            TransactionType.WITHDRAWAL,
            new BigDecimal("500.00"),
            "EUR",
            LocalDateTime.now(),
            null,
            null
        );

        Transaction result = registerTransactionUseCase.execute(inputTransaction);

        assertEquals(TransactionStatus.PENDING, result.status());
        assertNotNull(result.timestamp());
    }

    @Test
    @DisplayName("Debe rechazar transacción con monto negativo")
    void shouldRejectTransactionWithNegativeAmount() {
        Transaction invalidTransaction = new Transaction(
            UUID.randomUUID(),
            "CUST-003",
            TransactionType.TRANSFER,
            new BigDecimal("-100.00"),
            "USD",
            LocalDateTime.now(),
            TransactionStatus.PENDING,
            null
        );

        assertThrows(IllegalArgumentException.class, () -> {
            registerTransactionUseCase.execute(invalidTransaction);
        });

        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Debe rechazar transacción con monto cero")
    void shouldRejectTransactionWithZeroAmount() {
        Transaction invalidTransaction = new Transaction(
            UUID.randomUUID(),
            "CUST-004",
            TransactionType.DEPOSIT,
            BigDecimal.ZERO,
            "USD",
            LocalDateTime.now(),
            TransactionStatus.PENDING,
            null
        );

        assertThrows(IllegalArgumentException.class, () -> {
            registerTransactionUseCase.execute(invalidTransaction);
        });

        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Debe rechazar transacción con customerId vacío")
    void shouldRejectTransactionWithEmptyCustomerId() {
        Transaction invalidTransaction = new Transaction(
            UUID.randomUUID(),
            "",
            TransactionType.DEPOSIT,
            new BigDecimal("100.00"),
            "USD",
            LocalDateTime.now(),
            TransactionStatus.PENDING,
            null
        );

        assertThrows(IllegalArgumentException.class, () -> {
            registerTransactionUseCase.execute(invalidTransaction);
        });

        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Debe rechazar transacción con currency nulo")
    void shouldRejectTransactionWithNullCurrency() {
        Transaction invalidTransaction = new Transaction(
            UUID.randomUUID(),
            "CUST-005",
            TransactionType.DEPOSIT,
            new BigDecimal("100.00"),
            null,
            LocalDateTime.now(),
            TransactionStatus.PENDING,
            null
        );

        assertThrows(NullPointerException.class, () -> {
            registerTransactionUseCase.execute(invalidTransaction);
        });
    }

    @Test
    @DisplayName("Debe permitir todos los tipos de transacción válidos")
    void shouldAllowAllValidTransactionTypes() {
        TransactionType[] validTypes = {TransactionType.DEPOSIT, TransactionType.WITHDRAWAL, TransactionType.TRANSFER};

        for (TransactionType type : validTypes) {
            Transaction tx = new Transaction(
                UUID.randomUUID(),
                "CUST-006",
                type,
                new BigDecimal("200.00"),
                "USD",
                LocalDateTime.now(),
                TransactionStatus.PENDING,
                null
            );

            when(transactionRepository.save(any(Transaction.class))).thenReturn(tx);

            Transaction result = registerTransactionUseCase.execute(tx);

            assertNotNull(result);
            assertEquals(type, result.type());
        }
    }

    @Test
    @DisplayName("Debe invocar al repositorio exactamente una vez")
    void shouldCallRepositoryExactlyOnce() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        registerTransactionUseCase.execute(transaction);

        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Debe propagar excepción del repositorio")
    void shouldPropagateRepositoryException() {
        when(transactionRepository.save(any(Transaction.class)))
            .thenThrow(new RuntimeException("Database connection failed"));

        assertThrows(RuntimeException.class, () -> {
            registerTransactionUseCase.execute(transaction);
        });
    }
}
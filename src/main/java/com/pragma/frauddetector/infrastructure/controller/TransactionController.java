package com.pragma.frauddetector.infrastructure.controller;




import com.pragma.frauddetector.domain.model.FraudAnalysisResult;
import com.pragma.frauddetector.domain.model.TransactionStatus;
import com.pragma.frauddetector.domain.model.TransactionType;
import com.pragma.frauddetector.application.usecase.AnalyzeFraudUseCase;
import com.pragma.frauddetector.application.usecase.RegisterTransactionUseCase;
import com.pragma.frauddetector.domain.model.Transaction;
import com.pragma.frauddetector.domain.port.TransactionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@Tag(name = "Transacciones", description = "API para gestión de transacciones y detección de fraude")
public class TransactionController {

    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    private final RegisterTransactionUseCase registerTransactionUseCase;
    private final AnalyzeFraudUseCase analyzeFraudUseCase;
    private final TransactionRepository transactionRepository;

    public TransactionController(
            RegisterTransactionUseCase registerTransactionUseCase,
            AnalyzeFraudUseCase analyzeFraudUseCase,
            TransactionRepository transactionRepository) {
        this.registerTransactionUseCase = registerTransactionUseCase;
        this.analyzeFraudUseCase = analyzeFraudUseCase;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping
    @Operation(summary = "Registrar transacción", description = "Registra una nueva transacción y ejecuta análisis de fraude")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transacción registrada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de transacción inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    public ResponseEntity<TransactionResponse> registerTransaction(
            @Valid @RequestBody TransactionRequest request) {
        log.info("Received transaction registration request for customer: {}", request.customerId());

        try {
            Transaction transaction = request.toTransaction();
            Transaction savedTransaction = registerTransactionUseCase.execute(transaction);

            TransactionResponse response = TransactionResponse.fromTransaction(savedTransaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error registering transaction: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar transacción", description = "Obtiene los detalles de una transacción por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transacción encontrada"),
            @ApiResponse(responseCode = "404", description = "Transacción no encontrada", content = @Content)
    })
    public ResponseEntity<TransactionResponse> getTransaction(
            @Parameter(description = "ID de la transacción") @PathVariable UUID id) {
        log.info("Fetching transaction with id: {}", id);

        return transactionRepository.findById(id)
                .map(transaction -> ResponseEntity.ok(TransactionResponse.fromTransaction(transaction)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Consultar transacciones por cliente", description = "Obtiene todas las transacciones de un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de transacciones del cliente")
    })
    public ResponseEntity<List<TransactionResponse>> getTransactionsByCustomer(
            @Parameter(description = "ID del cliente") @PathVariable String customerId) {
        log.info("Fetching transactions for customer: {}", customerId);

        List<TransactionResponse> transactions = transactionRepository.findByCustomerId(customerId)
                .stream()
                .map(TransactionResponse::fromTransaction)
                .toList();

        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/{id}/analyze")
    @Operation(summary = "Analizar transacción", description = "Ejecuta el análisis de fraude sobre una transacción existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Análisis completado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Transacción no encontrada", content = @Content)
    })
    public ResponseEntity<TransactionResponse> analyzeTransaction(
            @Parameter(description = "ID de la transacción") @PathVariable UUID id) {
        log.info("Analyzing transaction with id: {}", id);

        return transactionRepository.findById(id)
                .map(transaction -> {
                    Transaction analyzedTransaction = analyzeFraudUseCase.execute(transaction);
                    return ResponseEntity.ok(TransactionResponse.fromTransaction(analyzedTransaction));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/fraud-analysis")
    @Operation(summary = "Consultar transacciones pendientes de análisis",
               description = "Obtiene todas las transacciones que requieren análisis de fraude")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de transacciones pendientes")
    })
    public ResponseEntity<List<TransactionResponse>> getPendingFraudAnalysis() {
        log.info("Fetching transactions pending fraud analysis");

        List<TransactionResponse> transactions = transactionRepository.findByFraudAnalysisRequired()
                .stream()
                .map(TransactionResponse::fromTransaction)
                .toList();

        return ResponseEntity.ok(transactions);
    }

    public record TransactionRequest(
            @NotBlank(message = "El ID del cliente es obligatorio")
            String customerId,

            @NotNull(message = "El monto es obligatorio")
            @Positive(message = "El monto debe ser positivo")
            BigDecimal amount,

            @NotBlank(message = "El tipo de transacción es obligatorio")
            String transactionType,

            String description,
            String merchantId,
            String destinationAccount,
            String location,
            String deviceId) {

        public Transaction toTransaction() {
            Transaction.TransactionType type = Transaction.TransactionType.valueOf(
                    transactionType.toUpperCase().replace(" ", "_"));

            return new Transaction(
                    UUID.randomUUID(),
                    customerId,
                    amount,
                    type,
                    LocalDateTime.now(),
                    Transaction.TransactionStatus.PENDING,
                    description,
                    merchantId,
                    destinationAccount,
                    location,
                    deviceId,
                    null
            );
        }
    }

    public record TransactionResponse(
            UUID id,
            String customerId,
            BigDecimal amount,
            String transactionType,
            LocalDateTime timestamp,
            String status,
            String description,
            String merchantId,
            String destinationAccount,
            String location,
            String deviceId,
            FraudAnalysisResultDto fraudAnalysis) {

        public static TransactionResponse fromTransaction(Transaction transaction) {
            Transaction.FraudAnalysisResult fraudResult = transaction.fraudAnalysisResult();
            FraudAnalysisResultDto fraudDto = fraudResult != null
                    ? new FraudAnalysisResultDto(
                            fraudResult.isFraud(),
                            fraudResult.riskScore(),
                            fraudResult.recommendation(),
                            fraudResult.details())
                    : null;

            return new TransactionResponse(
                    transaction.id(),
                    transaction.customerId(),
                    transaction.amount(),
                    transaction.type().name(),
                    transaction.timestamp(),
                    transaction.status().name(),
                    transaction.description(),
                    transaction.merchantId(),
                    transaction.destinationAccount(),
                    transaction.location(),
                    transaction.deviceId(),
                    fraudDto
            );
        }
    }

    public record FraudAnalysisResultDto(
            boolean isFraud,
            double riskScore,
            String recommendation,
            String details) {
    }
}
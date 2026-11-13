package com.pragma.frauddetector.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Fraud Detector API")
                        .version("1.0.0")
                        .description("API para el sistema de detección de fraudes con optimización de recursos IA. "
                                + "Permite registrar transacciones financieras, analizar patrones de fraude "
                                + "y optimizar dinámicamente la carga de trabajo de los agentes de IA.")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("desarrollo@pragma.com")
                                .url("https://www.pragma.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Token JWT para autenticación"))
                        .addSchemas("ErrorResponse", createErrorResponseSchema())
                        .addSchemas("ValidationError", createValidationErrorSchema()))
                .addTagsItem(new io.swagger.v3.oas.models.tags.Tag()
                        .name("Transacciones")
                        .description("Operaciones relacionadas con el registro y consulta de transacciones"))
                .addTagsItem(new io.swagger.v3.oas.models.tags.Tag()
                        .name("Análisis de Fraude")
                        .description("Operaciones para el análisis de patrones de fraude"))
                .addTagsItem(new io.swagger.v3.oas.models.tags.Tag()
                        .name("Optimización de Recursos")
                        .description("Operaciones para optimizar el uso de recursos IA"));
    }

    private Schema<?> createErrorResponseSchema() {
        Schema<?> errorResponse = new Schema<>();
        errorResponse.type("object");
        errorResponse.description("Respuesta estándar de error");
        errorResponse.addProperty("timestamp", new StringSchema()
                .description("Marca de tiempo del error")
                .example("2024-01-15T10:30:00Z"));
        errorResponse.addProperty("status", new StringSchema()
                .description("Código de estado HTTP")
                .example("400"));
        errorResponse.addProperty("error", new StringSchema()
                .description("Tipo de error")
                .example("Bad Request"));
        errorResponse.addProperty("message", new StringSchema()
                .description("Mensaje descriptivo del error")
                .example("La transacción no pudo ser procesada"));
        errorResponse.addProperty("path", new StringSchema()
                .description("Ruta de la petición que causó el error")
                .example("/api/v1/transactions"));
        return errorResponse;
    }

    private Schema<?> createValidationErrorSchema() {
        Schema<?> validationError = new Schema<>();
        validationError.type("object");
        validationError.description("Error de validación de campo");
        validationError.addProperty("field", new StringSchema()
                .description("Nombre del campo que falló la validación")
                .example("amount"));
        validationError.addProperty("message", new StringSchema()
                .description("Mensaje de error de validación")
                .example("El monto debe ser mayor que cero"));
        validationError.addProperty("rejectedValue", new StringSchema()
                .description("Valor rechazado que no pasó la validación")
                .example("-50.00"));
        return validationError;
    }

    @Bean
    public ApiResponse defaultApiResponse() {
        return new ApiResponse()
                .description("Respuesta exitosa")
                .content(new Content()
                        .addMediaType("application/json", new MediaType()
                                .schema(new Schema<>().type("object").example(Map.of(
                                        "status", "success",
                                        "message", "Operación completada correctamente"
                                )))));
    }

    @Bean
    public ApiResponse notFoundApiResponse() {
        return new ApiResponse()
                .description("Recurso no encontrado")
                .content(new Content()
                        .addMediaType("application/json", new MediaType()
                                .schema(new Schema<>().type("object").example(Map.of(
                                        "timestamp", "2024-01-15T10:30:00Z",
                                        "status", 404,
                                        "error", "Not Found",
                                        "message", "El recurso solicitado no existe",
                                        "path", "/api/v1/transactions/123"
                                )))));
    }

    @Bean
    public ApiResponse badRequestApiResponse() {
        return new ApiResponse()
                .description("Solicitud incorrecta - Error de validación")
                .content(new Content()
                        .addMediaType("application/json", new MediaType()
                                .schema(new Schema<>().type("object").example(Map.of(
                                        "timestamp", "2024-01-15T10:30:00Z",
                                        "status", 400,
                                        "error", "Bad Request",
                                        "message", "Error de validación en los datos de entrada",
                                        "errors", new Object[]{
                                                Map.of("field", "amount", "message", "El monto debe ser positivo"),
                                                Map.of("field", "customerId", "message", "El ID del cliente es obligatorio")
                                        }
                                )))));
    }

    @Bean
    public ApiResponse internalServerErrorApiResponse() {
        return new ApiResponse()
                .description("Error interno del servidor")
                .content(new Content()
                        .addMediaType("application/json", new MediaType()
                                .schema(new Schema<>().type("object").example(Map.of(
                                        "timestamp", "2024-01-15T10:30:00Z",
                                        "status", 500,
                                        "error", "Internal Server Error",
                                        "message", "Error interno al procesar la solicitud",
                                        "path", "/api/v1/transactions"
                                )))));
    }

    @Bean
    public ApiResponse serviceUnavailableApiResponse() {
        return new ApiResponse()
                .description("Servicio no disponible - Circuit Breaker activo")
                .content(new Content()
                        .addMediaType("application/json", new MediaType()
                                .schema(new Schema<>().type("object").example(Map.of(
                                        "timestamp", "2024-01-15T10:30:00Z",
                                        "status", 503,
                                        "error", "Service Unavailable",
                                        "message", "El servicio de análisis de fraude no está disponible temporalmente. Por favor, intente más tarde.",
                                        "path", "/api/v1/transactions/analyze"
                                )))));
    }
# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Optimización de uso de recursos en agentes IA**.

| | |
|---|---|
| Tema | Optimización de componentes IA |
| Nivel | advanced-l2 |
| Chapter | Backend |
| Especialidad | Java |
| Stack | Java / Spring Boot 3.5 |
| Patron arquitectonico | hexagonal/clean |
| Tiempo estimado | 10 horas |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz`
- `clase con @SpringBootApplication`
- `application.yml en src/main/resources`
- `capa de dominio con entidades y puertos`
- `capa de aplicacion con casos de uso`
- `capa de infraestructura con adaptadores y @RestController`

Trampas conocidas:

- TODA `<version>` del pom va con tres segmentos: la del parent (ej. `3.5.6`) y la de cada dependencia que la lleve (ej. Resilience4j `2.2.0`). `3.4` y `2.0` no existen como artefacto y el build muere resolviendo dependencias.
- Las dependencias que el parent POM gestiona van SIN `<version>`: `spring-boot-starter-web`, `-data-jpa`, `-validation`, `-test`, etc.
- Resilience4j publica un artefacto por linea de Spring Boot. Con Spring Boot 3 va `resilience4j-spring-boot3` con version de tres segmentos (ej. `2.2.0`, no `2.0`). `resilience4j-spring-boot2` es de Spring Boot 2 y rompe el arranque.
- Si usas anotaciones de validacion (`@NotNull`, `@Size`, `@Positive`) declara `spring-boot-starter-validation`: el starter web no las trae.
- El `spring-boot-maven-plugin` tiene que estar en `<build><plugins>` o no se empaqueta ejecutable.
- Spring Boot 3 usa `jakarta.*`, nunca `javax.*`.
- Cada archivo empieza con su `package` y con un `import` por cada clase del proyecto que viva en otro paquete. Usar `PaymentService` desde `infrastructure` sin `import com.x.application.PaymentService` no compila.

Dependencias:

- org.springframework.boot:spring-boot-starter-web 3.5.6
- org.springframework.boot:spring-boot-starter-data-jpa n/a
- org.springframework.boot:spring-boot-starter-validation n/a
- org.springframework.boot:spring-boot-starter-actuator n/a
- org.postgresql:postgresql n/a
- io.github.resilience4j:resilience4j-spring-boot3 2.2.0
- org.springdoc:springdoc-openapi-starter-webmvc-ui 2.9.1
- org.projectlombok:lombok n/a
- org.springframework.boot:spring-boot-starter-test n/a
- org.junit.jupiter:junit-jupiter-api n/a
- org.mockito:mockito-core n/a

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Registro de transacciones**: Componente que registra transacciones con los detalles requeridos.
- **Fase 2 — Análisis de patrones de fraude**: Componente que analiza transacciones para detectar patrones de fraude y ajusta la carga de trabajo de los agentes IA.
- **Fase 3 — Optimización de recursos**: Componente que optimiza el uso de recursos con agentes IA y ajusta dinámicamente la carga de trabajo.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Superficie de practica (NO completes)

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs. No toques la logica que el reto pide completar.

- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/config/Resilience4jConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que falta y tenes que completar

### 1. Referencias colgando (76)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `com.pragma.frauddetector.infrastructure.entity.TransactionEntity`
      El import com.pragma.frauddetector.infrastructure.entity.TransactionEntity usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `com.pragma.frauddetector.infrastructure.repository.TransactionJpaRepository`
      El import com.pragma.frauddetector.infrastructure.repository.TransactionJpaRepository usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/pragma/frauddetector/application/usecase/OptimizeResourcesUseCase.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/FraudAnalysisAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/ResourceOptimizerAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `io.swagger.v3`
      El import io.swagger.v3.oas.annotations.Operation pertenece a io.swagger.v3, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/config/OpenApiConfig.java` — `io.swagger.v3`
      El import io.swagger.v3.oas.models.Components pertenece a io.swagger.v3, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/frauddetector/Application.java` — `ApplicationProperties.getCorePoolSize`
      Se invoca `getCorePoolSize` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/Application.java` — `ApplicationProperties.getMaxPoolSize`
      Se invoca `getMaxPoolSize` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/Application.java` — `ApplicationProperties.getQueueCapacity`
      Se invoca `getQueueCapacity` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCase.java` — `Transaction.id`
      Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCase.java` — `Transaction.customerId`
      Se invoca `customerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCase.java` — `Transaction.amount`
      Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCase.java` — `Transaction.type`
      Se invoca `type` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCase.java` — `Transaction.description`
      Se invoca `description` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCase.java` — `Transaction.timestamp`
      Se invoca `timestamp` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/application/usecase/AnalyzeFraudUseCase.java` — `Transaction.id`
      Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `Transaction.id`
      Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionStatus.name`
      Se invoca `name` sobre `TransactionStatus`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `FraudAnalysisResult.fraudScore`
      Se invoca `fraudScore` sobre `FraudAnalysisResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `FraudAnalysisResult.isFraudIndicator`
      Se invoca `isFraudIndicator` sobre `FraudAnalysisResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `FraudAnalysisResult.details`
      Se invoca `details` sobre `FraudAnalysisResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `Transaction.customerId`
      Se invoca `customerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `Transaction.amount`
      Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `Transaction.currency`
      Se invoca `currency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `Transaction.type`
      Se invoca `type` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `Transaction.status`
      Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `Transaction.description`
      Se invoca `description` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `Transaction.merchantId`
      Se invoca `merchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `Transaction.timestamp`
      Se invoca `timestamp` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `Transaction.fraudAnalysisRequired`
      Se invoca `fraudAnalysisRequired` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `Transaction.fraudAnalysisResult`
      Se invoca `fraudAnalysisResult` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/FraudAnalysisAdapter.java` — `Transaction.id`
      Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/FraudAnalysisAdapter.java` — `Transaction.customerId`
      Se invoca `customerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/FraudAnalysisAdapter.java` — `Transaction.amount`
      Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/FraudAnalysisAdapter.java` — `Transaction.currency`
      Se invoca `currency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/FraudAnalysisAdapter.java` — `Transaction.type`
      Se invoca `type` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/FraudAnalysisAdapter.java` — `Transaction.merchantId`
      Se invoca `merchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/adapter/FraudAnalysisAdapter.java` — `Transaction.timestamp`
      Se invoca `timestamp` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `TransactionRequest.customerId`
      Se invoca `customerId` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `TransactionRequest.toTransaction`
      Se invoca `toTransaction` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.fraudAnalysisResult`
      Se invoca `fraudAnalysisResult` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `FraudAnalysisResult.isFraud`
      Se invoca `isFraud` sobre `FraudAnalysisResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `FraudAnalysisResult.riskScore`
      Se invoca `riskScore` sobre `FraudAnalysisResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `FraudAnalysisResult.recommendation`
      Se invoca `recommendation` sobre `FraudAnalysisResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `FraudAnalysisResult.details`
      Se invoca `details` sobre `FraudAnalysisResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.id`
      Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.customerId`
      Se invoca `customerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.amount`
      Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.type`
      Se invoca `type` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.timestamp`
      Se invoca `timestamp` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.status`
      Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.description`
      Se invoca `description` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.merchantId`
      Se invoca `merchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.destinationAccount`
      Se invoca `destinationAccount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.location`
      Se invoca `location` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.deviceId`
      Se invoca `deviceId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCaseTest.java` — `Transaction.id`
      Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCaseTest.java` — `Transaction.customerId`
      Se invoca `customerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCaseTest.java` — `Transaction.status`
      Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCaseTest.java` — `Transaction.type`
      Se invoca `type` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCaseTest.java` — `Transaction.amount`
      Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCaseTest.java` — `Transaction.currency`
      Se invoca `currency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCaseTest.java` — `Transaction.timestamp`
      Se invoca `timestamp` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCaseTest.java` — `Transaction.fraudAnalysisResult`
      Se invoca `fraudAnalysisResult` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/frauddetector/application/usecase/AnalyzeFraudUseCaseTest.java` — `Transaction.fraudAnalysisResult`
      Se invoca `fraudAnalysisResult` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/frauddetector/application/usecase/AnalyzeFraudUseCaseTest.java` — `AnalyzeFraudUseCase.executeBatch`
      Se invoca `executeBatch` sobre `AnalyzeFraudUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/frauddetector/application/usecase/AnalyzeFraudUseCaseTest.java` — `AnalyzeFraudUseCase.getResourceUtilization`
      Se invoca `getResourceUtilization` sobre `AnalyzeFraudUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/frauddetector/application/usecase/OptimizeResourcesUseCaseTest.java` — `ResourceOptimizer.getCurrentUtilization`
      Se invoca `getCurrentUtilization` sobre `ResourceOptimizer`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/frauddetector/application/usecase/OptimizeResourcesUseCaseTest.java` — `OptimizeResourcesUseCase.execute`
      Se invoca `execute` sobre `OptimizeResourcesUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/frauddetector/application/usecase/OptimizeResourcesUseCaseTest.java` — `OptimizeResourcesUseCase.getPendingTransactions`
      Se invoca `getPendingTransactions` sobre `OptimizeResourcesUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/frauddetector/application/usecase/OptimizeResourcesUseCaseTest.java` — `OptimizeResourcesUseCase.calculateOptimizationScore`
      Se invoca `calculateOptimizationScore` sobre `OptimizeResourcesUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/frauddetector/application/usecase/OptimizeResourcesUseCaseTest.java` — `ResourceOptimizer.getActiveAgents`
      Se invoca `getActiveAgents` sobre `ResourceOptimizer`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/frauddetector/application/usecase/OptimizeResourcesUseCaseTest.java` — `OptimizeResourcesUseCase.getPerformanceMetrics`
      Se invoca `getPerformanceMetrics` sobre `OptimizeResourcesUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (19)

- `pom.xml`
- `src/main/java/com/pragma/frauddetector/Application.java`
- `src/main/resources/application.yml`
- `src/main/java/com/pragma/frauddetector/domain/model/Transaction.java`
- `src/main/java/com/pragma/frauddetector/domain/port/TransactionRepository.java`
- `src/main/java/com/pragma/frauddetector/domain/port/FraudAnalysisService.java`
- `src/main/java/com/pragma/frauddetector/domain/port/ResourceOptimizer.java`
- `src/main/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCase.java`
- `src/main/java/com/pragma/frauddetector/application/usecase/AnalyzeFraudUseCase.java`
- `src/main/java/com/pragma/frauddetector/application/usecase/OptimizeResourcesUseCase.java`
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java`
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/FraudAnalysisAdapter.java`
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/ResourceOptimizerAdapter.java`
- `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java`
- `src/main/java/com/pragma/frauddetector/infrastructure/config/Resilience4jConfig.java`
- `src/main/java/com/pragma/frauddetector/infrastructure/config/OpenApiConfig.java`
- `src/test/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCaseTest.java`
- `src/test/java/com/pragma/frauddetector/application/usecase/AnalyzeFraudUseCaseTest.java`
- `src/test/java/com/pragma/frauddetector/application/usecase/OptimizeResourcesUseCaseTest.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/pragma/frauddetector`
- `src/main/java/com/pragma/frauddetector/domain`
- `src/main/java/com/pragma/frauddetector/domain/model`
- `src/main/java/com/pragma/frauddetector/domain/port`
- `src/main/java/com/pragma/frauddetector/application`
- `src/main/java/com/pragma/frauddetector/application/usecase`
- `src/main/java/com/pragma/frauddetector/infrastructure`
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter`
- `src/main/java/com/pragma/frauddetector/infrastructure/config`
- `src/main/java/com/pragma/frauddetector/infrastructure/controller`
- `src/main/resources`
- `src/test/java/com/pragma/frauddetector`

## Verificacion

```bash
mvn clean compile
```

El comando tiene que pasar SIN implementar los archivos de la superficie de practica: solo andamiaje.

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Backend, Especialidad Desarrollador, Tecnología Java, Advanced L2
- Brecha que el reto ataca: APLICA prácticas y estrategias que optimizan el uso de recursos con agentes IA., APLICA prácticas y estrategias que optimizan el uso de recursos con agentes IA.
- Mision: Backend Developer con experiencia en Java. Enfocado en optimización de recursos y trabajo con agentes IA.

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*

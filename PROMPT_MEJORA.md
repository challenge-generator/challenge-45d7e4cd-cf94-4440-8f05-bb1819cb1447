# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Superficie de practica — NO resuelvas

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs.

- `src/main/java/com/pragma/frauddetector/infrastructure/config/Resilience4jConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `com.pragma.frauddetector.infrastructure.entity.TransactionEntity`: El import com.pragma.frauddetector.infrastructure.entity.TransactionEntity usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `com.pragma.frauddetector.infrastructure.repository.TransactionJpaRepository`: El import com.pragma.frauddetector.infrastructure.repository.TransactionJpaRepository usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/pragma/frauddetector/application/usecase/OptimizeResourcesUseCase.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/FraudAnalysisAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/ResourceOptimizerAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `io.swagger.v3`: El import io.swagger.v3.oas.annotations.Operation pertenece a io.swagger.v3, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/frauddetector/infrastructure/config/OpenApiConfig.java` — `io.swagger.v3`: El import io.swagger.v3.oas.models.Components pertenece a io.swagger.v3, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/frauddetector/Application.java` — `ApplicationProperties.getCorePoolSize`: Se invoca `getCorePoolSize` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/Application.java` — `ApplicationProperties.getMaxPoolSize`: Se invoca `getMaxPoolSize` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/Application.java` — `ApplicationProperties.getQueueCapacity`: Se invoca `getQueueCapacity` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCase.java` — `Transaction.id`: Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCase.java` — `Transaction.customerId`: Se invoca `customerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCase.java` — `Transaction.amount`: Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCase.java` — `Transaction.type`: Se invoca `type` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCase.java` — `Transaction.description`: Se invoca `description` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCase.java` — `Transaction.timestamp`: Se invoca `timestamp` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/application/usecase/AnalyzeFraudUseCase.java` — `Transaction.id`: Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `Transaction.id`: Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `TransactionStatus.name`: Se invoca `name` sobre `TransactionStatus`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `FraudAnalysisResult.fraudScore`: Se invoca `fraudScore` sobre `FraudAnalysisResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `FraudAnalysisResult.isFraudIndicator`: Se invoca `isFraudIndicator` sobre `FraudAnalysisResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `FraudAnalysisResult.details`: Se invoca `details` sobre `FraudAnalysisResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `Transaction.customerId`: Se invoca `customerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `Transaction.amount`: Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `Transaction.currency`: Se invoca `currency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `Transaction.type`: Se invoca `type` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `Transaction.status`: Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `Transaction.description`: Se invoca `description` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `Transaction.merchantId`: Se invoca `merchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `Transaction.timestamp`: Se invoca `timestamp` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `Transaction.fraudAnalysisRequired`: Se invoca `fraudAnalysisRequired` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java` — `Transaction.fraudAnalysisResult`: Se invoca `fraudAnalysisResult` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/FraudAnalysisAdapter.java` — `Transaction.id`: Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/FraudAnalysisAdapter.java` — `Transaction.customerId`: Se invoca `customerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/FraudAnalysisAdapter.java` — `Transaction.amount`: Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/FraudAnalysisAdapter.java` — `Transaction.currency`: Se invoca `currency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/FraudAnalysisAdapter.java` — `Transaction.type`: Se invoca `type` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/FraudAnalysisAdapter.java` — `Transaction.merchantId`: Se invoca `merchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/adapter/FraudAnalysisAdapter.java` — `Transaction.timestamp`: Se invoca `timestamp` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `TransactionRequest.customerId`: Se invoca `customerId` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `TransactionRequest.toTransaction`: Se invoca `toTransaction` sobre `TransactionRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.fraudAnalysisResult`: Se invoca `fraudAnalysisResult` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `FraudAnalysisResult.isFraud`: Se invoca `isFraud` sobre `FraudAnalysisResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `FraudAnalysisResult.riskScore`: Se invoca `riskScore` sobre `FraudAnalysisResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `FraudAnalysisResult.recommendation`: Se invoca `recommendation` sobre `FraudAnalysisResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `FraudAnalysisResult.details`: Se invoca `details` sobre `FraudAnalysisResult`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.id`: Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.customerId`: Se invoca `customerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.amount`: Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.type`: Se invoca `type` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.timestamp`: Se invoca `timestamp` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.status`: Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.description`: Se invoca `description` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.merchantId`: Se invoca `merchantId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.destinationAccount`: Se invoca `destinationAccount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.location`: Se invoca `location` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java` — `Transaction.deviceId`: Se invoca `deviceId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCaseTest.java` — `Transaction.id`: Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCaseTest.java` — `Transaction.customerId`: Se invoca `customerId` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCaseTest.java` — `Transaction.status`: Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCaseTest.java` — `Transaction.type`: Se invoca `type` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCaseTest.java` — `Transaction.amount`: Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCaseTest.java` — `Transaction.currency`: Se invoca `currency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCaseTest.java` — `Transaction.timestamp`: Se invoca `timestamp` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCaseTest.java` — `Transaction.fraudAnalysisResult`: Se invoca `fraudAnalysisResult` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/frauddetector/application/usecase/AnalyzeFraudUseCaseTest.java` — `Transaction.fraudAnalysisResult`: Se invoca `fraudAnalysisResult` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/frauddetector/application/usecase/AnalyzeFraudUseCaseTest.java` — `AnalyzeFraudUseCase.executeBatch`: Se invoca `executeBatch` sobre `AnalyzeFraudUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/frauddetector/application/usecase/AnalyzeFraudUseCaseTest.java` — `AnalyzeFraudUseCase.getResourceUtilization`: Se invoca `getResourceUtilization` sobre `AnalyzeFraudUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/frauddetector/application/usecase/OptimizeResourcesUseCaseTest.java` — `ResourceOptimizer.getCurrentUtilization`: Se invoca `getCurrentUtilization` sobre `ResourceOptimizer`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/frauddetector/application/usecase/OptimizeResourcesUseCaseTest.java` — `OptimizeResourcesUseCase.execute`: Se invoca `execute` sobre `OptimizeResourcesUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/frauddetector/application/usecase/OptimizeResourcesUseCaseTest.java` — `OptimizeResourcesUseCase.getPendingTransactions`: Se invoca `getPendingTransactions` sobre `OptimizeResourcesUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/frauddetector/application/usecase/OptimizeResourcesUseCaseTest.java` — `OptimizeResourcesUseCase.calculateOptimizationScore`: Se invoca `calculateOptimizationScore` sobre `OptimizeResourcesUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/frauddetector/application/usecase/OptimizeResourcesUseCaseTest.java` — `ResourceOptimizer.getActiveAgents`: Se invoca `getActiveAgents` sobre `ResourceOptimizer`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/frauddetector/application/usecase/OptimizeResourcesUseCaseTest.java` — `OptimizeResourcesUseCase.getPerformanceMetrics`: Se invoca `getPerformanceMetrics` sobre `OptimizeResourcesUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Backend, Especialidad Desarrollador, Tecnología Java, Advanced L2

### Brecha de conocimiento
APLICA prácticas y estrategias que optimizan el uso de recursos con agentes IA., APLICA prácticas y estrategias que optimizan el uso de recursos con agentes IA.

### Misión / candidato
Backend Developer con experiencia en Java. Enfocado en optimización de recursos y trabajo con agentes IA.

### Reto
- Tema: Optimización de componentes IA
- Seniority: advanced-l2
- Tipo: practical
- Título: Optimización de uso de recursos en agentes IA
- Tiempo estimado: 10 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Registro de transacciones — objetivo: Implementar la funcionalidad para registrar transacciones con detalles específicos. — entregable (NO resolver): Componente que registra transacciones con los detalles requeridos.
- Fase 2: Análisis de patrones de fraude — objetivo: Implementar la funcionalidad para analizar patrones de fraude utilizando agentes IA. — entregable (NO resolver): Componente que analiza transacciones para detectar patrones de fraude y ajusta la carga de trabajo de los agentes IA.
- Fase 3: Optimización de recursos — objetivo: Implementar la funcionalidad para optimizar el uso de recursos con agentes IA. — entregable (NO resolver): Componente que optimiza el uso de recursos con agentes IA y ajusta dinámicamente la carga de trabajo.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.pragma</groupId>
    <artifactId>fraud-detector</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>fraud-detector</name>
    <description>Sistema de detección de fraudes con optimización de recursos IA</description>

    <properties>
        <java.version>21</java.version>
        <resilience4j.version>2.2.0</resilience4j.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- Database -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Resilience4j -->
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot3</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <!-- OpenAPI -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>2.9.1</version>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- Test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <repositories>
        <repository>
            <id>spring-milestones</id>
            <name>Spring Milestones</name>
            <url>https://repo.spring.io/milestone</url>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
    </repositories>
</project>

// === ARCHIVO: src/main/java/com/pragma/frauddetector/Application.java ===
package com.pragma.frauddetector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties
public class Application {
    private final ApplicationProperties applicationProperties;

    public Application(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        validateProperties();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(applicationProperties.getCorePoolSize());
        executor.setMaxPoolSize(applicationProperties.getMaxPoolSize());
        executor.setQueueCapacity(applicationProperties.getQueueCapacity());
        executor.setThreadNamePrefix("fraud-detector-");
        executor.initialize();
        return executor;
    }

    private void validateProperties() {
        if (applicationProperties.getCorePoolSize() <= 0) {
            throw new IllegalStateException("Core pool size must be positive");
        }
        if (applicationProperties.getMaxPoolSize() < applicationProperties.getCorePoolSize()) {
            throw new IllegalStateException("Max pool size must be greater than or equal to core pool size");
        }
        if (applicationProperties.getQueueCapacity() <= 0) {
            throw new IllegalStateException("Queue capacity must be positive");
        }
    }
}

record ApplicationProperties(
    int corePoolSize,
    int maxPoolSize,
    int queueCapacity
) {}

// === ARCHIVO: src/main/resources/application.yml ===
spring:
  application:
    name: fraud-detector
  datasource:
    url: jdbc:postgresql://localhost:5432/fraud_detector
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD:postgres}
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 10
      minimum-idle: 2
      idle-timeout: 30000
      max-lifetime: 600000
      connection-timeout: 30000
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher

server:
  port: 8080
  servlet:
    context-path: /api

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
  metrics:
    tags:
      application: ${spring.application.name}

resilience4j:
  circuitbreaker:
    configs:
      default:
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenEnabled: true
        waitDurationInOpenState: 5s
        failureRateThreshold: 50
        eventConsumerBufferSize: 10
        recordExceptions:
          - org.springframework.web.client.HttpServerErrorException
          - java.io.IOException
          - java.util.concurrent.TimeoutException
    instances:
      fraudAnalysis:
        baseConfig: default
      resourceOptimizer:
        baseConfig: default
  retry:
    configs:
      default:
        maxAttempts: 3
        waitDuration: 100
        retryExceptions:
          - org.springframework.web.client.HttpServerErrorException
          - java.io.IOException
    instances:
      fraudAnalysis:
        baseConfig: default
      resourceOptimizer:
        baseConfig: default
  ratelimiter:
    configs:
      default:
        limitForPeriod: 10
        limitRefreshPeriod: 1s
        timeoutDuration: 0
    instances:
      fraudAnalysis:
        baseConfig: default

app:
  async:
    core-pool-size: 5
    max-pool-size: 10
    queue-capacity: 100

// === ARCHIVO: src/main/java/com/pragma/frauddetector/domain/model/Transaction.java ===
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

// === ARCHIVO: src/main/java/com/pragma/frauddetector/domain/port/TransactionRepository.java ===
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

// === ARCHIVO: src/main/java/com/pragma/frauddetector/domain/port/FraudAnalysisService.java ===
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

// === ARCHIVO: src/main/java/com/pragma/frauddetector/domain/port/ResourceOptimizer.java ===
package com.pragma.frauddetector.domain.port;

/**
 * Puerto del dominio para la optimización de recursos de agentes IA.
 * Define la interfaz que la infraestructura debe implementar para ajustar
 * dinámicamente la carga de trabajo de los agentes de análisis de fraude.
 */
public interface ResourceOptimizer {

    /**
     * Ajusta la carga de trabajo de los agentes IA basándose en la carga actual del sistema.
     * @param currentLoad número de transacciones pendientes de procesar
     */
    void adjustWorkload(int currentLoad);

    /**
     * Obtiene la carga de trabajo actual de los agentes IA.
     * @return número de transacciones que los agentes están procesando actualmente
     */
    int getCurrentWorkload();

    /**
     * Calcula la utilización actual de recursos de los agentes IA.
     * @return porcentaje de utilización entre 0.0 y 1.0
     */
    double getResourceUtilization();

    /**
     * Obtiene el número óptimo de agentes para procesar la carga actual.
     * @return número recomendado de agentes activos
     */
    int getOptimalAgentCount();

    /**
     * Verifica si el sistema tiene capacidad para procesar más transacciones.
     * @return true si puede aceptar más carga, false en caso contrario
     */
    boolean canAcceptMoreLoad();
}

// === ARCHIVO: src/main/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCase.java ===
package com.pragma.frauddetector.application.usecase;

import com.pragma.frauddetector.domain.model.Transaction;
import com.pragma.frauddetector.domain.model.Transaction.TransactionStatus;
import com.pragma.frauddetector.domain.model.Transaction.TransactionType;
import com.pragma.frauddetector.domain.port.TransactionRepository;
import com.pragma.frauddetector.domain.port.FraudAnalysisService;
import com.pragma.frauddetector.domain.port.ResourceOptimizer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Caso de uso para registrar una nueva transacción en el sistema.
 * Maneja la validación de datos, persistencia y coordinación con el servicio
 * de análisis de fraude y el optimizador de recursos.
 */
public class RegisterTransactionUseCase {

    private final TransactionRepository transactionRepository;
    private final FraudAnalysisService fraudAnalysisService;
    private final ResourceOptimizer resourceOptimizer;

    private static final BigDecimal HIGH_RISK_AMOUNT_THRESHOLD = new BigDecimal("10000.00");
    private static final int BATCH_THRESHOLD = 50;

    public RegisterTransactionUseCase(
            TransactionRepository transactionRepository,
            FraudAnalysisService fraudAnalysisService,
            ResourceOptimizer resourceOptimizer) {
        this.transactionRepository = transactionRepository;
        this.fraudAnalysisService = fraudAnalysisService;
        this.resourceOptimizer = resourceOptimizer;
    }

    /**
     * Registra una nueva transacción en el sistema.
     * @param customerId identificador del cliente
     * @param amount monto de la transacción
     * @param type tipo de transacción
     * @param description descripción de la transacción
     * @return la transacción registrada con su estado
     */
    public Transaction execute(String customerId, BigDecimal amount, 
                               TransactionType type, String description) {
        validateInput(customerId, amount, type);

        Transaction transaction = new Transaction(
            UUID.randomUUID(),
            customerId,
            amount,
            type,
            description,
            LocalDateTime.now(),
            TransactionStatus.PENDING,
            null
        );

        boolean requiresFraudAnalysis = determineFraudAnalysisRequirement(transaction);
        
        if (requiresFraudAnalysis) {
            transaction = new Transaction(
                transaction.id(),
                transaction.customerId(),
                transaction.amount(),
                transaction.type(),
                transaction.description(),
                transaction.timestamp(),
                TransactionStatus.ANALYSIS_PENDING,
                null
            );
        }

        Transaction savedTransaction = transactionRepository.save(transaction);
        
        adjustResourceOptimization();
        
        return savedTransaction;
    }

    /**
     * Registra múltiples transacciones en lote.
     * @param transactions lista de transacciones a registrar
     * @return lista de transacciones registradas
     */
    public List<Transaction> executeBatch(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            throw new IllegalArgumentException("La lista de transacciones no puede estar vacía");
        }

        for (Transaction transaction : transactions) {
            validateTransaction(transaction);
        }

        List<Transaction> savedTransactions = transactions.stream()
            .map(transactionRepository::save)
            .toList();

        adjustResourceOptimization();
        
        return savedTransactions;
    }

    private void validateInput(String customerId, BigDecimal amount, TransactionType type) {
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("El ID del cliente no puede estar vacío");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        if (type == null) {
            throw new IllegalArgumentException("El tipo de transacción es obligatorio");
        }
    }

    private void validateTransaction(Transaction transaction) {
        if (transaction.customerId() == null || transaction.customerId().isBlank()) {
            throw new IllegalArgumentException("El ID del cliente no puede estar vacío");
        }
        if (transaction.amount() == null || transaction.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        if (transaction.type() == null) {
            throw new IllegalArgumentException("El tipo de transacción es obligatorio");
        }
    }

    private boolean determineFraudAnalysisRequirement(Transaction transaction) {
        if (transaction.amount().compareTo(HIGH_RISK_AMOUNT_THRESHOLD) >= 0) {
            return true;
        }
        
        if (transaction.type() == TransactionType.INTERNATIONAL_TRANSFER) {
            return true;
        }

        List<Transaction> recentTransactions = transactionRepository
            .findByCustomerId(transaction.customerId());
        
        long recentCount = recentTransactions.stream()
            .filter(t -> t.timestamp().isAfter(LocalDateTime.now().minusHours(24)))
            .count();
        
        if (recentCount >= BATCH_THRESHOLD) {
            return true;
        }
        
        return false;
    }

    private void adjustResourceOptimization() {
        List<Transaction> pendingAnalysis = transactionRepository.findByFraudAnalysisRequired();
        int currentLoad = pendingAnalysis.size();
        
        if (currentLoad > 0) {
            resourceOptimizer.adjustWorkload(currentLoad);
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/frauddetector/application/usecase/AnalyzeFraudUseCase.java ===
package com.pragma.frauddetector.application.usecase;

import com.pragma.frauddetector.domain.model.Transaction;
import com.pragma.frauddetector.domain.model.Transaction.FraudAnalysisResult;
import com.pragma.frauddetector.domain.model.Transaction.TransactionStatus;
import com.pragma.frauddetector.domain.port.TransactionRepository;
import com.pragma.frauddetector.domain.port.FraudAnalysisService;
import com.pragma.frauddetector.domain.port.ResourceOptimizer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Caso de uso para analizar patrones de fraude en transacciones.
 * Coordina la recuperación de transacciones pendientes, el análisis mediante
 * agentes IA y la optimización dinámica de recursos.
 */
public class AnalyzeFraudUseCase {

    private final TransactionRepository transactionRepository;
    private final FraudAnalysisService fraudAnalysisService;
    private final ResourceOptimizer resourceOptimizer;

    private static final int DEFAULT_BATCH_SIZE = 100;
    private static final int MAX_RETRY_ATTEMPTS = 3;

    public AnalyzeFraudUseCase(
            TransactionRepository transactionRepository,
            FraudAnalysisService fraudAnalysisService,
            ResourceOptimizer resourceOptimizer) {
        this.transactionRepository = transactionRepository;
        this.fraudAnalysisService = fraudAnalysisService;
        this.resourceOptimizer = resourceOptimizer;
    }

    /**
     * Analiza todas las transacciones pendientes de fraude.
     * @return lista de resultados del análisis
     */
    public List<FraudAnalysisResult> execute() {
        List<Transaction> pendingTransactions = transactionRepository.findByFraudAnalysisRequired();
        
        if (pendingTransactions.isEmpty()) {
            return List.of();
        }

        adjustResourceLoad(pendingTransactions.size());
        
        List<Transaction> batch = pendingTransactions.stream()
            .limit(DEFAULT_BATCH_SIZE)
            .toList();

        return analyzeBatch(batch);
    }

    /**
     * Analiza una transacción específica por su ID.
     * @param transactionId ID de la transacción a analizar
     * @return resultado del análisis de fraude
     */
    public FraudAnalysisResult executeById(UUID transactionId) {
        if (transactionId == null) {
            throw new IllegalArgumentException("El ID de transacción no puede ser nulo");
        }

        Transaction transaction = transactionRepository.findById(transactionId)
            .orElseThrow(() -> new IllegalArgumentException(
                "Transacción no encontrada con ID: " + transactionId));

        FraudAnalysisResult result = performAnalysis(transaction);
        
        transactionRepository.updateFraudAnalysisResult(transactionId, result);
        
        updateResourceOptimization();
        
        return result;
    }

    /**
     * Analiza transacciones de un cliente específico.
     * @param customerId ID del cliente
     * @return lista de resultados del análisis
     */
    public List<FraudAnalysisResult> executeByCustomer(String customerId) {
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("El ID del cliente no puede estar vacío");
        }

        List<Transaction> customerTransactions = transactionRepository.findByCustomerId(customerId);
        
        List<Transaction> pendingAnalysis = customerTransactions.stream()
            .filter(t -> t.status() == TransactionStatus.ANALYSIS_PENDING)
            .toList();

        if (pendingAnalysis.isEmpty()) {
            return List.of();
        }

        return analyzeBatch(pendingAnalysis);
    }

    /**
     * Analiza transacciones en un rango de tiempo específico.
     * @param start fecha de inicio
     * @param end fecha de fin
     * @return lista de resultados del análisis
     */
    public List<FraudAnalysisResult> executeByTimeRange(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Las fechas de inicio y fin son obligatorias");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin");
        }

        List<Transaction> transactionsInRange = transactionRepository.findByTimestampBetween(start, end);
        
        List<Transaction> pendingAnalysis = transactionsInRange.stream()
            .filter(t -> t.status() == TransactionStatus.ANALYSIS_PENDING)
            .toList();

        if (pendingAnalysis.isEmpty()) {
            return List.of();
        }

        return analyzeBatch(pendingAnalysis);
    }

    /**
     * Analiza un lote de transacciones utilizando el servicio de análisis de fraude.
     * @param transactions lista de transacciones a analizar
     * @return lista de resultados del análisis
     */
    private List<FraudAnalysisResult> analyzeBatch(List<Transaction> transactions) {
        List<FraudAnalysisResult> results = new ArrayList<>();
        
        int attempt = 0;
        Exception lastException = null;
        
        while (attempt < MAX_RETRY_ATTEMPTS) {
            try {
                results = fraudAnalysisService.analyzeBatch(transactions);
                break;
            } catch (Exception e) {
                lastException = e;
                attempt++;
                if (attempt < MAX_RETRY_ATTEMPTS) {
                    try {
                        Thread.sleep(attempt * 1000L);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Análisis interrumpido", ie);
                    }
                }
            }
        }

        if (results.isEmpty() && lastException != null) {
            throw new RuntimeException("Error en el análisis por lotes después de " 
                + MAX_RETRY_ATTEMPTS + " intentos", lastException);
        }

        for (int i = 0; i < transactions.size() && i < results.size(); i++) {
            Transaction transaction = transactions.get(i);
            FraudAnalysisResult result = results.get(i);
            
            transactionRepository.updateFraudAnalysisResult(transaction.id(), result);
        }

        updateResourceOptimization();
        
        return results;
    }

    /**
     * Realiza el análisis de una transacción individual.
     * @param transaction transacción a analizar
     * @return resultado del análisis
     */
    private FraudAnalysisResult performAnalysis(Transaction transaction) {
        return fraudAnalysisService.analyzeTransaction(transaction);
    }

    /**
     * Ajusta la carga de recursos basada en el número de transacciones pendientes.
     * @param pendingCount número de transacciones pendientes
     */
    private void adjustResourceLoad(int pendingCount) {
        int currentWorkload = fraudAnalysisService.getCurrentWorkload();
        double utilization = fraudAnalysisService.getResourceUtilization();
        
        int totalLoad = currentWorkload + pendingCount;
        resourceOptimizer.adjustWorkload(totalLoad);
        
        if (utilization > 0.8 && pendingCount > DEFAULT_BATCH_SIZE) {
            int optimalAgents = resourceOptimizer.getOptimalAgentCount();
            if (optimalAgents > 0) {
                resourceOptimizer.adjustWorkload(pendingCount / optimalAgents);
            }
        }
    }

    /**
     * Actualiza la optimización de recursos después de completar análisis.
     */
    private void updateResourceOptimization() {
        List<Transaction> remainingPending = transactionRepository.findByFraudAnalysisRequired();
        int remainingCount = remainingPending.size();
        
        resourceOptimizer.adjustWorkload(remainingCount);
        
        if (!resourceOptimizer.canAcceptMoreLoad() && remainingCount > 0) {
            int optimalAgents = resourceOptimizer.getOptimalAgentCount();
            if (optimalAgents > 0) {
                int adjustedLoad = remainingCount / optimalAgents;
                resourceOptimizer.adjustWorkload(adjustedLoad);
            }
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/frauddetector/application/usecase/OptimizeResourcesUseCase.java ===
package com.pragma.frauddetector.application.usecase;

import com.pragma.frauddetector.domain.model.Transaction;
import com.pragma.frauddetector.domain.port.FraudAnalysisService;
import com.pragma.frauddetector.domain.port.ResourceOptimizer;
import com.pragma.frauddetector.domain.port.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OptimizeResourcesUseCase {

    private static final Logger log = LoggerFactory.getLogger(OptimizeResourcesUseCase.class);
    private static final double OPTIMAL_UTILIZATION_THRESHOLD = 0.75;
    private static final double LOW_UTILIZATION_THRESHOLD = 0.30;
    private static final int MAX_WORKLOAD = 100;
    private static final int MIN_WORKLOAD = 10;

    private final TransactionRepository transactionRepository;
    private final FraudAnalysisService fraudAnalysisService;
    private final ResourceOptimizer resourceOptimizer;

    public OptimizeResourcesUseCase(
            TransactionRepository transactionRepository,
            FraudAnalysisService fraudAnalysisService,
            ResourceOptimizer resourceOptimizer) {
        this.transactionRepository = transactionRepository;
        this.fraudAnalysisService = fraudAnalysisService;
        this.resourceOptimizer = resourceOptimizer;
    }

    public OptimizationResult optimizeResourceAllocation() {
        log.info("Iniciando optimización de recursos de agentes IA");

        double currentUtilization = fraudAnalysisService.getResourceUtilization();
        int currentWorkload = fraudAnalysisService.getCurrentWorkload();

        log.info("Utilización actual: {}, Carga de trabajo actual: {}", currentUtilization, currentWorkload);

        List<Transaction> pendingAnalysis = transactionRepository.findByFraudAnalysisRequired();
        int pendingCount = pendingAnalysis.size();

        log.info("Transacciones pendientes de análisis: {}", pendingCount);

        int optimizedWorkload = calculateOptimalWorkload(currentUtilization, pendingCount, currentWorkload);

        if (optimizedWorkload != currentWorkload) {
            fraudAnalysisService.adjustWorkload(optimizedWorkload);
            log.info("Carga de trabajo ajustada de {} a {}", currentWorkload, optimizedWorkload);
        }

        double projectedEfficiency = calculateProjectedEfficiency(optimizedWorkload, pendingCount);
        double projectedResponseTime = calculateProjectedResponseTime(optimizedWorkload, pendingCount);

        return new OptimizationResult(
                optimizedWorkload,
                currentUtilization,
                pendingCount,
                projectedEfficiency,
                projectedResponseTime,
                LocalDateTime.now()
        );
    }

    private int calculateOptimalWorkload(double utilization, int pendingCount, int currentWorkload) {
        if (pendingCount == 0) {
            log.info("No hay transacciones pendientes, reduciendo carga de trabajo");
            return Math.max(MIN_WORKLOAD, currentWorkload - 20);
        }

        if (utilization > OPTIMAL_UTILIZATION_THRESHOLD) {
            log.info("Utilización alta, aumentando carga para procesar más rápido");
            return Math.min(MAX_WORKLOAD, currentWorkload + 15);
        }

        if (utilization < LOW_UTILIZATION_THRESHOLD) {
            log.info("Utilización baja, reduciendo carga para ahorrar recursos");
            return Math.max(MIN_WORKLOAD, currentWorkload - 10);
        }

        int estimatedNeededWorkload = Math.min(MAX_WORKLOAD, Math.max(MIN_WORKLOAD, pendingCount / 10));
        log.info("Carga estimada necesaria: {}", estimatedNeededWorkload);
        return estimatedNeededWorkload;
    }

    private double calculateProjectedEfficiency(int workload, int pendingCount) {
        if (workload == 0) return 0.0;
        double throughput = (double) workload / 100.0;
        double efficiency = Math.min(1.0, throughput * (1.0 + (pendingCount > 100 ? 0.2 : 0.0)));
        return Math.round(efficiency * 100.0) / 100.0;
    }

    private double calculateProjectedResponseTime(int workload, int pendingCount) {
        if (workload == 0 || pendingCount == 0) return 0.0;
        double baseTime = 100.0;
        double workloadFactor = baseTime * (1.0 - (workload / (double) MAX_WORKLOAD) * 0.5);
        double pendingFactor = 1.0 + (pendingCount / 1000.0);
        double responseTime = workloadFactor * pendingFactor;
        return Math.round(responseTime * 100.0) / 100.0;
    }

    public record OptimizationResult(
            int optimizedWorkload,
            double currentUtilization,
            int pendingTransactions,
            double projectedEfficiency,
            double projectedResponseTimeMs,
            LocalDateTime timestamp
    ) {
        public String summary() {
            return String.format(
                    "Optimización: carga=%d, utilización=%.2f, eficiencia proyectada=%.2f, tiempo respuesta=%.2fms",
                    optimizedWorkload, currentUtilization, projectedEfficiency, projectedResponseTimeMs
            );
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/frauddetector/infrastructure/adapter/TransactionJpaAdapter.java ===
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

// === ARCHIVO: src/main/java/com/pragma/frauddetector/infrastructure/adapter/FraudAnalysisAdapter.java ===
package com.pragma.frauddetector.infrastructure.adapter;


import com.pragma.frauddetector.domain.model.FraudAnalysisResult;
import com.pragma.frauddetector.domain.model.Transaction;
import com.pragma.frauddetector.domain.port.FraudAnalysisService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class FraudAnalysisAdapter implements FraudAnalysisService {

    private static final Logger log = LoggerFactory.getLogger(FraudAnalysisAdapter.class);
    private static final double DEFAULT_FRAUD_SCORE = 0.0;
    private static final int MAX_CONCURRENT_ANALYSES = 100;

    private final RestTemplate restTemplate;
    private final String fraudAnalysisEndpoint;
    private final AtomicInteger currentWorkload;
    private final AtomicReference<Double> resourceUtilization;

    public FraudAnalysisAdapter(
            RestTemplate restTemplate,
            @Value("${frauddetector.analysis.endpoint:http://localhost:8081/api/analyze}") String fraudAnalysisEndpoint) {
        this.restTemplate = restTemplate;
        this.fraudAnalysisEndpoint = fraudAnalysisEndpoint;
        this.currentWorkload = new AtomicInteger(50);
        this.resourceUtilization = new AtomicReference<>(0.5);
    }

    @Override
    @CircuitBreaker(name = "fraudAnalysisCircuitBreaker", fallbackMethod = "analyzeFallback")
    @Retry(name = "fraudAnalysisRetry")
    public Transaction.FraudAnalysisResult analyzeTransaction(Transaction transaction) {
        log.info("Analizando transacción {} con agente IA", transaction.id());

        currentWorkload.incrementAndGet();
        updateResourceUtilization();

        try {
            Map<String, Object> requestBody = buildAnalysisRequest(transaction);

            Map<String, Object> response = invokeExternalAnalysisService(requestBody);

            return parseAnalysisResponse(response, transaction);
        } catch (Exception e) {
            log.error("Error al analizar transacción {}: {}", transaction.id(), e.getMessage());
            return createDefaultAnalysisResult();
        } finally {
            currentWorkload.decrementAndGet();
            updateResourceUtilization();
        }
    }

    @Override
    @CircuitBreaker(name = "fraudAnalysisBatchCircuitBreaker", fallbackMethod = "analyzeBatchFallback")
    public List<Transaction.FraudAnalysisResult> analyzeBatch(List<Transaction> transactions) {
        log.info("Analizando lote de {} transacciones con agentes IA", transactions.size());

        List<Transaction.FraudAnalysisResult> results = new ArrayList<>();
        int batchWorkloadIncrement = transactions.size();
        currentWorkload.addAndGet(batchWorkloadIncrement);
        updateResourceUtilization();

        try {
            for (Transaction transaction : transactions) {
                Transaction.FraudAnalysisResult result = analyzeTransaction(transaction);
                results.add(result);
            }
            log.info("Análisis por lote completado: {} transacciones procesadas", results.size());
        } catch (Exception e) {
            log.error("Error en análisis por lote: {}", e.getMessage());
            for (int i = 0; i < transactions.size(); i++) {
                results.add(createDefaultAnalysisResult());
            }
        } finally {
            currentWorkload.addAndGet(-batchWorkloadIncrement);
            updateResourceUtilization();
        }

        return results;
    }

    @Override
    public void adjustWorkload(int currentLoad) {
        log.info("Ajustando carga de trabajo de agentes IA a: {}", currentLoad);
        int clampedLoad = Math.max(0, Math.min(MAX_CONCURRENT_ANALYSES, currentLoad));
        currentWorkload.set(clampedLoad);
        updateResourceUtilization();
        log.info("Carga de trabajo ajustada exitosamente a: {}", clampedLoad);
    }

    @Override
    public int getCurrentWorkload() {
        return currentWorkload.get();
    }

    @Override
    public double getResourceUtilization() {
        return resourceUtilization.get();
    }

    private void updateResourceUtilization() {
        double utilization = (double) currentWorkload.get() / MAX_CONCURRENT_ANALYSES;
        resourceUtilization.set(utilization);
        log.debug("Utilización de recursos actualizada: {}", utilization);
    }

    private Map<String, Object> buildAnalysisRequest(Transaction transaction) {
        Map<String, Object> request = new HashMap<>();
        request.put("transactionId", transaction.id().toString());
        request.put("customerId", transaction.customerId());
        request.put("amount", transaction.amount().doubleValue());
        request.put("currency", transaction.currency());
        request.put("type", transaction.type().name());
        request.put("merchantId", transaction.merchantId());
        request.put("timestamp", transaction.timestamp().toString());
        return request;
    }

    private Map<String, Object> invokeExternalAnalysisService(Map<String, Object> requestBody) {
        try {
            Map<String, Object> response = restTemplate.postForObject(
                    fraudAnalysisEndpoint,
                    requestBody,
                    Map.class
            );
            return response != null ? response : createDefaultResponse();
        } catch (Exception e) {
            log.warn("Servicio de análisis externo no disponible, usando análisis local: {}", e.getMessage());
            return performLocalAnalysis(requestBody);
        }
    }

    private Map<String, Object> performLocalAnalysis(Map<String, Object> request) {
        Map<String, Object> localResult = new HashMap<>();

        double amount = ((Number) request.get("amount")).doubleValue();
        String type = (String) request.get("type");

        double fraudScore = calculateFraudScore(amount, type);
        boolean isFraud = fraudScore > 0.7;

        localResult.put("fraudScore", fraudScore);
        localResult.put("fraudIndicator", isFraud);
        localResult.put("details", generateAnalysisDetails(fraudScore, amount, type));
        localResult.put("analyzedAt", LocalDateTime.now().toString());

        return localResult;
    }

    private double calculateFraudScore(double amount, String type) {
        double baseScore = 0.1;

        if (amount > 10000) {
            baseScore += 0.3;
        } else if (amount > 5000) {
            baseScore += 0.2;
        } else if (amount > 1000) {
            baseScore += 0.1;
        }

        if ("TRANSFER".equalsIgnoreCase(type)) {
            baseScore += 0.15;
        } else if ("WITHDRAWAL".equalsIgnoreCase(type)) {
            baseScore += 0.1;
        }

        return Math.min(1.0, baseScore);
    }

    private String generateAnalysisDetails(double score, double amount, String type) {
        return String.format(
                "Análisis automático: score=%.2f, amount=%.2f, type=%s, recommendation=%s",
                score, amount, type, score > 0.7 ? "RECHAZAR" : score > 0.4 ? "REVISAR" : "APROBAR"
        );
    }

    private Transaction.FraudAnalysisResult parseAnalysisResponse(Map<String, Object> response, Transaction transaction) {
        double fraudScore = response.containsKey("fraudScore")
                ? ((Number) response.get("fraudScore")).doubleValue()
                : DEFAULT_FRAUD_SCORE;

        boolean isFraudIndicator = response.containsKey("fraudIndicator")
                && (Boolean) response.get("fraudIndicator");

        String details = response.containsKey("details")
                ? (String) response.get("details")
                : "Sin detalles disponibles";

        LocalDateTime analyzedAt = response.containsKey("analyzedAt")
                ? LocalDateTime.parse((String) response.get("analyzedAt"))
                : LocalDateTime.now();

        log.info("Resultado del análisis para {}: score={}, fraud={}",
                transaction.id(), fraudScore, isFraudIndicator);

        return new Transaction.FraudAnalysisResult(
                fraudScore,
                isFraudIndicator,
                details,
                analyzedAt
        );
    }

    private Transaction.FraudAnalysisResult createDefaultAnalysisResult() {
        return new Transaction.FraudAnalysisResult(
                DEFAULT_FRAUD_SCORE,
                false,
                "Análisis no disponible - modo offline",
                LocalDateTime.now()
        );
    }

    private Map<String, Object> createDefaultResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("fraudScore", DEFAULT_FRAUD_SCORE);
        response.put("fraudIndicator", false);
        response.put("details", "Respuesta por defecto");
        response.put("analyzedAt", LocalDateTime.now().toString());
        return response;
    }

    private Transaction.FraudAnalysisResult analyzeFallback(Transaction transaction, Throwable t) {
        log.warn("Circuit breaker activado para análisis de transacción {}. Usando resultado por defecto. Error: {}",
                transaction.id(), t.getMessage());
        return createDefaultAnalysisResult();
    }

    private List<Transaction.FraudAnalysisResult> analyzeBatchFallback(List<Transaction> transactions, Throwable t) {
        log.warn("Circuit breaker activado para análisis por lote. Usando resultados por defecto. Error: {}",
                t.getMessage());
        return transactions.stream()
                .map(txn -> createDefaultAnalysisResult())
                .toList();
    }
}

// === ARCHIVO: src/main/java/com/pragma/frauddetector/infrastructure/adapter/ResourceOptimizerAdapter.java ===
package com.pragma.frauddetector.infrastructure.adapter;

import com.pragma.frauddetector.domain.port.ResourceOptimizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class ResourceOptimizerAdapter implements ResourceOptimizer {

    private static final Logger log = LoggerFactory.getLogger(ResourceOptimizerAdapter.class);
    private static final double DEFAULT_TARGET_UTILIZATION = 0.75;
    private static final double MIN_UTILIZATION_THRESHOLD = 0.30;
    private static final double MAX_UTILIZATION_THRESHOLD = 0.90;
    private static final int MAX_WORKLOAD = 100;
    private static final int MIN_WORKLOAD = 10;
    private static final double SCALE_UP_FACTOR = 1.2;
    private static final double SCALE_DOWN_FACTOR = 0.8;

    private final AtomicInteger currentWorkload = new AtomicInteger(50);
    private final AtomicReference<Double> resourceUtilization = new AtomicReference<>(0.5);

    @Override
    public void optimizeResources(int currentLoad) {
        double utilization = calculateUtilization(currentLoad);
        resourceUtilization.set(utilization);

        if (utilization > MAX_UTILIZATION_THRESHOLD) {
            scaleDown(currentLoad);
        } else if (utilization < MIN_UTILIZATION_THRESHOLD) {
            scaleUp(currentLoad);
        } else {
            maintainSteadyState(currentLoad);
        }

        log.info("Resource optimization completed: load={}, utilization={}%, workload={}",
                currentLoad, String.format("%.2f", utilization * 100), currentWorkload.get());
    }

    @Override
    public int getCurrentWorkload() {
        return currentWorkload.get();
    }

    @Override
    public double getResourceUtilization() {
        return resourceUtilization.get();
    }

    @Override
    public boolean shouldScaleUp() {
        return resourceUtilization.get() > MAX_UTILIZATION_THRESHOLD;
    }

    @Override
    public boolean shouldScaleDown() {
        return resourceUtilization.get() < MIN_UTILIZATION_THRESHOLD;
    }

    @Override
    public void adjustWorkload(int targetWorkload) {
        int clampedWorkload = Math.max(MIN_WORKLOAD, Math.min(MAX_WORKLOAD, targetWorkload));
        int previousWorkload = currentWorkload.getAndSet(clampedWorkload);

        log.info("Workload adjusted from {} to {}", previousWorkload, clampedWorkload);
    }

    private double calculateUtilization(int currentLoad) {
        int workload = currentWorkload.get();
        if (workload == 0) {
            return 0.0;
        }
        return Math.min(1.0, (double) currentLoad / workload);
    }

    private void scaleUp(int currentLoad) {
        int newWorkload = (int) Math.ceil(currentWorkload.get() * SCALE_UP_FACTOR);
        adjustWorkload(newWorkload);
        log.info("Scaled up workload due to high resource utilization");
    }

    private void scaleDown(int currentLoad) {
        int newWorkload = (int) Math.floor(currentWorkload.get() * SCALE_DOWN_FACTOR);
        adjustWorkload(newWorkload);
        log.info("Scaled down workload to optimize resource usage");
    }

    private void maintainSteadyState(int currentLoad) {
        double targetUtilization = DEFAULT_TARGET_UTILIZATION;
        double currentUtilization = resourceUtilization.get();

        if (Math.abs(currentUtilization - targetUtilization) > 0.1) {
            int adjustment = (int) ((targetUtilization - currentUtilization) * currentWorkload.get());
            int newWorkload = currentWorkload.get() + adjustment;
            adjustWorkload(newWorkload);
            log.debug("Adjusted workload to maintain target utilization");
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/frauddetector/infrastructure/controller/TransactionController.java ===
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

// === ARCHIVO: src/main/java/com/pragma/frauddetector/infrastructure/config/Resilience4jConfig.java ===
package com.pragma.frauddetector.infrastructure.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(60))
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(3)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();

        return CircuitBreakerRegistry.of(config);
    }

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .build();

        return RetryRegistry.of(config);
    }

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(100)
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .timeoutDuration(Duration.ofMillis(500))
                .build();

        return RateLimiterRegistry.of(config);
    }
}

// === ARCHIVO: src/main/java/com/pragma/frauddetector/infrastructure/config/OpenApiConfig.java ===
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

// === ARCHIVO: src/test/java/com/pragma/frauddetector/application/usecase/RegisterTransactionUseCaseTest.java ===
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

// === ARCHIVO: src/test/java/com/pragma/frauddetector/application/usecase/AnalyzeFraudUseCaseTest.java ===
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

// === ARCHIVO: src/test/java/com/pragma/frauddetector/application/usecase/OptimizeResourcesUseCaseTest.java ===
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
```

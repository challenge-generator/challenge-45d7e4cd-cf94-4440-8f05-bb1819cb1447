# Optimización de uso de recursos en agentes IA

El sistema de procesamiento de transacciones financieras utiliza agentes de inteligencia artificial para optimizar la detección de fraudes. Los agentes IA consumen recursos significativos, por lo que es crucial optimizar su uso para mantener la eficiencia del sistema. El sistema debe registrar transacciones con detalles específicos, analizar patrones de fraude y ajustar dinámicamente la carga de trabajo de los agentes IA para maximizar la eficiencia y minimizar el tiempo de respuesta.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | Optimización de componentes IA |
| **Nivel** | advanced-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 10 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Registro de transacciones

**Objetivo:** Implementar la funcionalidad para registrar transacciones con detalles específicos.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- El sistema debe registrar transacciones con los siguientes detalles: ID de transacción, monto, fecha y hora, tipo de transacción (débito/crédito), y resultado de la transacción (aprobada/denegada).
- El sistema debe asegurar que los registros de transacciones sean precisos y estén disponibles para análisis posterior.

**Entregable:** Componente que registra transacciones con los detalles requeridos.

<details>
<summary>Pistas de conocimiento</summary>

- Considera la estructura de datos adecuada para almacenar los detalles de las transacciones.
- Piensa en cómo garantizar la integridad y consistencia de los datos registrados.

</details>

### Fase 2: Análisis de patrones de fraude

**Objetivo:** Implementar la funcionalidad para analizar patrones de fraude utilizando agentes IA.

**Tiempo estimado:** 4 horas

**Instrucciones:**

- El sistema debe analizar las transacciones registradas para detectar patrones de fraude utilizando agentes IA.
- El sistema debe ajustar dinámicamente la carga de trabajo de los agentes IA para maximizar la eficiencia y minimizar el tiempo de respuesta.

**Entregable:** Componente que analiza transacciones para detectar patrones de fraude y ajusta la carga de trabajo de los agentes IA.

<details>
<summary>Pistas de conocimiento</summary>

- Explora diferentes estrategias para optimizar el uso de recursos con agentes IA.
- Considera cómo manejar la carga de trabajo de los agentes IA de manera eficiente.

</details>

### Fase 3: Optimización de recursos

**Objetivo:** Implementar la funcionalidad para optimizar el uso de recursos con agentes IA.

**Tiempo estimado:** 4 horas

**Instrucciones:**

- El sistema debe optimizar el uso de recursos con agentes IA para mantener la eficiencia del sistema y minimizar el tiempo de respuesta.
- El sistema debe ajustar dinámicamente la carga de trabajo de los agentes IA en función de la demanda y los recursos disponibles.

**Entregable:** Componente que optimiza el uso de recursos con agentes IA y ajusta dinámicamente la carga de trabajo.

<details>
<summary>Pistas de conocimiento</summary>

- Evalúa diferentes algoritmos y estrategias para optimizar el uso de recursos con agentes IA.
- Considera cómo manejar la demanda y los recursos disponibles de manera eficiente.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es la optimización de recursos con agentes IA y por qué es importante en este sistema?
- **paraQueSirve**: ¿Para qué sirve la optimización de recursos con agentes IA en el contexto de detección de fraudes?
- **comoSeUsa**: ¿Cómo se usa la optimización de recursos con agentes IA para mejorar la eficiencia del sistema?
- **erroresComunes**: ¿Cuáles son los errores comunes al implementar la optimización de recursos con agentes IA?
- **queDecisionesImplica**: ¿Qué decisiones implica la implementación de la optimización de recursos con agentes IA?

## Criterios de Evaluacion

- Implementación correcta de la funcionalidad para registrar transacciones con detalles específicos.
- Implementación correcta de la funcionalidad para analizar patrones de fraude utilizando agentes IA.
- Implementación correcta de la funcionalidad para optimizar el uso de recursos con agentes IA.
- Ajuste dinámico correcto de la carga de trabajo de los agentes IA en función de la demanda y los recursos disponibles.
- Manejo eficiente de la carga de trabajo de los agentes IA.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*

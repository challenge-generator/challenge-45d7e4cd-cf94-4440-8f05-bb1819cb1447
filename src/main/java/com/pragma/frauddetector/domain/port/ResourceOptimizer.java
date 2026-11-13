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
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
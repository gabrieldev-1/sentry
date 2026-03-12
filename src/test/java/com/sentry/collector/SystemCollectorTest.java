package com.sentry.collector;

import org.junit.Test;

import com.sentry.collector.SystemCollector;

import static org.junit.Assert.*;

public class SystemCollectorTest {

    private final SystemCollector collector = new SystemCollector();

    @Test
    public void testMemoryUsagePercentage() {
        double percentage = collector.getMemoryUsagePercentage();
        assertTrue("Memory percentage should be between 0 and 100", percentage >= 0 && percentage <= 100);
    }

    @Test
    public void testTotalMemory() {
        long total = collector.getTotalMemory();
        assertTrue("Total memory should be greater than 0", total > 0);
    }

    @Test
    public void testCpuUsageIncremental() {
        // call twice to exercise tick update logic
        double first = collector.getCpuUsageIncremental();
        double second = collector.getCpuUsageIncremental();
        assertTrue("CPU percentage should be between 0 and 100", first >= 0 && first <= 100);
        assertTrue("CPU percentage should be between 0 and 100", second >= 0 && second <= 100);
    }

    @Test
    public void testCpuTemperature() {
        double temp = collector.getCpuTemperature();
        // Temperature could be 0 if unavailable; ensure non-negative value
        assertTrue("CPU temperature should be non-negative", temp >= 0);
    }

    @Test
    public void getCpuLevelsPerCoreTest() {
        double[] coresUsage = collector.getCpuLevelsPerCore();
        boolean isNegative = false;

        for(int i = 0; i < coresUsage.length; i++) {
            double core = coresUsage[i];
            
            if(core > 0) {
                isNegative = true;
                break;
            }
        }
        assertTrue("The percentage values ​​for the colors cannot be negative.", !isNegative);
    }
}
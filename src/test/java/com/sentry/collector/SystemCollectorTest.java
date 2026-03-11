package com.sentry.collector;

import org.junit.Test;
import static org.junit.Assert.*;

public class SystemCollectorTest {

    private final SystemCollector collector = new SystemCollector();

    @Test
    public void testMemoryPercentage() {
        double percentage = collector.memoryPercentage();
        assertTrue("Memory percentage should be between 0 and 100", percentage >= 0 && percentage <= 100);
    }

    @Test
    public void testTotalMemory() {
        long total = collector.totalMemory();
        assertTrue("Total memory should be greater than 0", total > 0);
    }

    @Test
    public void testCpuPercentage() {
        double percentage = collector.cpuPercentage();
        assertTrue("CPU percentage should be between 0 and 100", percentage >= 0 && percentage <= 100);
    }

    @Test
    public void testCpuTemp() {
        int temp = collector.cpuTemp();
        // Temperature might not be available, so just check it's an int
        // If temp <= 0, it prints error, but we can assert it's not negative or something
        assertTrue("CPU temperature should be non-negative", temp >= 0);
    }
}
package com.sentry.collector;

import java.lang.management.OperatingSystemMXBean;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Sensors;
import oshi.software.os.OperatingSystem;

/**
 * SystemCollector retrieves real-time hardware metrics using the OSHI library.
 * It maintains state for CPU ticks to provide incremental load calculations
 * between sampling intervals.
 */
public class SystemCollector {

    private static final SystemInfo SI = new SystemInfo();
    private final CentralProcessor cpu;
    private final GlobalMemory memory;
    private final Sensors sensors;
    private final OperatingSystem os;
    
    /** Global CPU tick storage for system-wide load calculation. */
    private long[] previousSystemTicks;

    /** Per-core tick storage for individual logical processor load calculation. */
    private long[][] previousProcessorTicks;

    public SystemCollector() {
        HardwareAbstractionLayer hal = SI.getHardware();
        this.os = SI.getOperatingSystem();
        this.sensors = hal.getSensors();
        this.memory = hal.getMemory();
        this.cpu = hal.getProcessor();

        this.previousSystemTicks = cpu.getSystemCpuLoadTicks();
        this.previousProcessorTicks = cpu.getProcessorCpuLoadTicks();
    }

    /**
     * Method that calculates the current uptime. Using the getSystemUptime * function of OSHI and an auxiliary method for converting values ​​and * formatting the text;
     *   
     * @return Current uptime as a string
     */

    public String getSystemUptime() {
        long seconds = os.getSystemUptime();
        return formatUptime(seconds);
    }

    private String formatUptime(long seconds) {
        long hours = (seconds % 86400) / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, secs);
            
    }

    /**
     * Calculates the percentage of physical memory currently in use.
     * @return Memory usage as a percentage (0.0 to 100.0).
     */
    public double getMemoryUsagePercentage() {
        long total = memory.getTotal();
        if (total == 0) return 0.0;
        
        long used = total - memory.getAvailable();
        return (double) used / total * 100;
    }

    /** @return Total physical memory available to the OS in bytes. */
    public long getTotalMemory() { return memory.getTotal(); }

    /** @return Currently unused physical memory in bytes. */
    public long getAvailableMemory() { return memory.getAvailable(); }

    /**
     * Calculates the average system-wide CPU load since the last call to this method.
     * Updates the internal tick state after calculation.
     *
     * @return Global CPU usage percentage (0.0 to 100.0).
     */
    public double getCpuUsage() {
        double load = cpu.getSystemCpuLoadBetweenTicks(previousSystemTicks) * 100;
        previousSystemTicks = cpu.getSystemCpuLoadTicks();
        return load;
    }

    /**
     * Calculates the load for each logical processor since the last call to this method.
     * Updates the internal per-core tick states after calculation.
     *
     * @return An array of percentages representing usage per core.
     */
    public double[] getCpuLevelsPerCore() {
        double[] levels = cpu.getProcessorCpuLoadBetweenTicks(previousProcessorTicks);
        previousProcessorTicks = cpu.getProcessorCpuLoadTicks();

        for(int i = 0; i < levels.length; i++) {
            levels[i] *= 100;
        }

        return levels;
    }
    
    /**
     * Retrieves the current CPU temperature reported by system sensors.
     * Note: On Linux, this typically requires the lm_sensors package.
     *
     * @return CPU temperature in Celsius.
     */
    public double getCpuTemperature() {
        return sensors.getCpuTemperature();
    }
}

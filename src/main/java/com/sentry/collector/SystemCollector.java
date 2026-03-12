package com.sentry.collector;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Sensors;

public class SystemCollector {

    private static final SystemInfo SI = new SystemInfo();
    private final CentralProcessor cpu;
    private final GlobalMemory memory;
    private final Sensors sensors;
    
    // global CPU state storage
    private long[] previousSystemTicks;

    // storage of the states of individual nuclei
    private long[][] previousProcessorTicks;

    public SystemCollector() {
        HardwareAbstractionLayer hal = SI.getHardware();
        this.sensors = hal.getSensors();
        this.memory = hal.getMemory();
        this.cpu = hal.getProcessor();
        this.previousSystemTicks = cpu.getSystemCpuLoadTicks();
        this.previousProcessorTicks = cpu.getProcessorCpuLoadTicks();
    }

    // memory methods
    
    public double getMemoryUsagePercentage() {
        long total = memory.getTotal();
        if (total == 0) return 0.0;
        
        long used = total - memory.getAvailable();
        return (double) used / total * 100;
    }
    public long getTotalMemory() { return memory.getTotal(); }
    public long getAvailableMemory() { return memory.getAvailable(); }

    // CPU methods

    public double getCpuUsage() {
        double load = cpu.getSystemCpuLoadBetweenTicks(previousSystemTicks) * 100;
        previousSystemTicks = cpu.getSystemCpuLoadTicks();
        return load;
    }

    public double[] getCpuLevelsPerCore() {
        double[] levels = cpu.getProcessorCpuLoadBetweenTicks(previousProcessorTicks);
        previousProcessorTicks = cpu.getProcessorCpuLoadTicks();

        for(int i = 0; i < levels.length; i++) {
            levels[i] *= 100;
        }

        return levels;
    }
    
    public double getCpuTemperature() {
        return sensors.getCpuTemperature();
    }

}

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
    
    private long[] previousTicks;

    public SystemCollector() {
        HardwareAbstractionLayer hal = SI.getHardware();
        this.sensors = hal.getSensors();
        this.memory = hal.getMemory();
        this.cpu = hal.getProcessor();
        this.previousTicks = cpu.getSystemCpuLoadTicks();
    }
    
    public double getMemoryUsagePercentage() {
        long total = memory.getTotal();
        if (total == 0) return 0.0;
        
        long used = total - memory.getAvailable();
        return (double) used / total * 100;
    }

    public double getCpuUsageIncremental() {
        double load = cpu.getSystemCpuLoadBetweenTicks(previousTicks) * 100;
        previousTicks = cpu.getSystemCpuLoadTicks();
        return load;
    }
    
    public double getCpuTemperature() {
        return sensors.getCpuTemperature();
    }

    public long getTotalMemory() { return memory.getTotal(); }
    public long getAvailableMemory() { return memory.getAvailable(); }
}

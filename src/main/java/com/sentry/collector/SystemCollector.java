package com.sentry.collector;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Sensors;

public class SystemCollector {

    private static final SystemInfo si = new SystemInfo();

    private final HardwareAbstractionLayer hal;
    private final CentralProcessor cpu;
    private final GlobalMemory memory;
    private final Sensors sensors;

    public SystemCollector() {
        this.hal = si.getHardware();
        this.sensors = hal.getSensors();
        this.memory = hal.getMemory();
        this.cpu = hal.getProcessor();
    }
    
    public double memoryPercentage() {
        long availableMemory = memory.getAvailable();
        long totalMemory = totalMemory();
        long usedMemory = totalMemory - availableMemory;

        return (double) usedMemory / totalMemory * 100;
    }

    public long totalMemory() {    
        return memory.getTotal();
    }
    
    public double cpuPercentage() {
        return cpu.getSystemCpuLoad(1000) * 100;
    }
    
    public double cpuTemp() {
        double temp = sensors.getCpuTemperature();

        if(temp <= 0) {
            System.err.println("Temperature not available.");
        }

        return temp;

    }
}

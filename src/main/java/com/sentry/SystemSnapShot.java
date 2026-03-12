package com.sentry;

import com.sentry.collector.ProcessCollector;
import com.sentry.collector.SystemCollector;
import oshi.software.os.OSProcess;

import java.util.List;

public class SystemSnapShot {
    private static final ProcessCollector pCollector = new ProcessCollector();
    private static final SystemCollector sCollector = new SystemCollector();

    private final double memoryUsage;
    private final double cpuUsage;
    private final double cpuTemp;
    private final double[] coresUsage; 

    private final List<OSProcess> processes;

    public SystemSnapShot() {
        this.memoryUsage = sCollector.getMemoryUsagePercentage();
        this.cpuUsage = sCollector.getCpuUsage();
        this.cpuTemp = sCollector.getCpuTemperature();
        this.coresUsage = sCollector.getCpuLevelsPerCore();
        // default to top 50 processes by CPU
        this.processes = pCollector.getTopProcess(50);
    }

    public double getMemoryUsage() { return memoryUsage; }
    public double getCpuUsage() { return cpuUsage; }
    public double getCpuTemp() { return cpuTemp; }
    public double[] getCoresUsage() { return coresUsage; } 
    public List<OSProcess> getProcesses() { return processes; }

    public OSProcess getProcessByPid(int pid) {
        return pCollector.getProcessByPid(pid);
    }
}

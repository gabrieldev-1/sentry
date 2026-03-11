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

    private final List<OSProcess> processes;

    public SystemSnapShot() {
        this.memoryUsage = sCollector.getMemoryUsagePercentage();
        this.cpuUsage = sCollector.getCpuUsageIncremental();
        this.cpuTemp = sCollector.getCpuTemperature();
        // default to top 10 processes by CPU
        this.processes = pCollector.getTopProcess(10);
    }

    public double getMemoryUsage() { return memoryUsage; }
    public double getCpuUsage() { return cpuUsage; }
    public double getCpuTemp() { return cpuTemp; }
    public List<OSProcess> getProcesses() { return processes; }

    public OSProcess getProcessByPid(int pid) {
        return pCollector.getProcessByPid(pid);
    }
}

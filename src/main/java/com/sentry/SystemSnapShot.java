package com.sentry;

import com.sentry.collector.ProcessCollector;
import com.sentry.collector.SystemCollector;
import oshi.software.os.OSProcess;

import java.util.List;

/**
 * SystemSnapShot represents an immutable point-in-time capture of system metrics.
 * It aggregates hardware utilization and process-level data into a single object 
 * to ensure data consistency across a single UI render cycle.
 */
public class SystemSnapShot {
    private static final ProcessCollector pCollector = new ProcessCollector();
    private static final SystemCollector sCollector = new SystemCollector();

    private final double memoryUsage;
    private final double cpuUsage;
    private final double cpuTemp;
    private final double[] coresUsage;
    private final String uptime;

    private final List<OSProcess> processes;
    private final int numThreads;
    private final int numProcesses;
    private final long totalMemory;
    private final long usedMemory;

    public SystemSnapShot() {
        this.memoryUsage = sCollector.getMemoryUsagePercentage();
        this.cpuUsage = sCollector.getCpuUsage();
        this.cpuTemp = sCollector.getCpuTemperature();
        this.coresUsage = sCollector.getCpuLevelsPerCore();
        this.uptime = sCollector.getSystemUptime();
        this.numThreads = pCollector.getNumOfThreads();
        this.numProcesses = pCollector.getNumOfProcesses();
        this.totalMemory = sCollector.getTotalMemory();
        this.usedMemory = this.totalMemory - sCollector.getAvailableMemory();
        // default to top 50 processes by CPU
        this.processes = pCollector.getTopProcess(50);
    }

    public double getMemoryUsage() { return memoryUsage; }
    public double getCpuUsage() { return cpuUsage; }
    public double getCpuTemp() { return cpuTemp; }
    public double[] getCoresUsage() { return coresUsage; }
    public String getUptime() { return uptime; }
    public List<OSProcess> getProcesses() { return processes; }
    public int getNumThreads() { return numThreads; }
    public int getNumProcesses() { return numProcesses; }
    public long getTotalMemory() { return totalMemory; }
    public long getUsedMemory() { return usedMemory; }

    public OSProcess getProcessByPid(int pid) {
        return pCollector.getProcessByPid(pid);
    }
}

package com.sentry.collector;

import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.software.os.OperatingSystem.ProcessSorting;

import java.util.List;

public class ProcessCollector {
    private final OperatingSystem os;

    public ProcessCollector() {
        this.os = new SystemInfo().getOperatingSystem();
    }

    /**
     * Return all running processes without any particular ordering.
     */
    public List<OSProcess> getProcess() {
        return os.getProcesses();
    }

    /**
     * Return up to {@code limit} processes sorted by CPU usage descending.
     */
    public List<OSProcess> getTopProcess(int limit) {
        // OSHI expects (filter, sort, limit)
        return os.getProcesses(null, ProcessSorting.CPU_DESC, limit);
    }

    public OSProcess getProcessByPid(int pid) {
        return os.getProcess(pid);
    }
}

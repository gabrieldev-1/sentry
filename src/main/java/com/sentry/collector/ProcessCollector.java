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

    public List<OSProcess> getProcess() {
        return os.getProcesses();
    }

    public OSProcess getProcessByPid(int pid) {
        return os.getProcess(pid);
    }
}

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
     * Returns up to {@code limit} processes sorted by CPU usage in descending order.
     * Uses OSHI library to retrieve and sort processes based on CPU consumption.
     *
     * @param limit maximum number of processes to return
     * @return list of OSProcess objects sorted by CPU usage descending, limited to the specified count
     */
    public List<OSProcess> getTopProcess(int limit) {
        // OSHI expects (filter, sort, limit)
        return os.getProcesses(null, ProcessSorting.CPU_DESC, limit);
    }

    public OSProcess getProcessByPid(int pid) {
        return os.getProcess(pid);
    }

    /**
     * Calculates the total number of threads across all running processes.
     * Iterates through all processes and sums their individual thread counts.
     *
     * @return total count of threads in the system
     */
    public int getNumOfThreads() {
        List<OSProcess> processes = os.getProcesses();
        int acc = 0;

        for(int i = 0; i < processes.size(); i++) {
            OSProcess process = processes.get(i);

            acc += process.getThreadCount();
        }

        return acc;
    }

    public int getNumOfProcesses() {
        List<OSProcess> processes = os.getProcesses();
        return processes.size();
    }
}

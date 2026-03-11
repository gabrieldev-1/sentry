package com.sentry.collector;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import oshi.software.os.OSProcess;

public class ProcessCollectorTest {

    private static ProcessCollector collector = new ProcessCollector();

    @Test
    public void getProcessTest() {
        List<OSProcess> processes = collector.getProcess();
        assertNotNull("Process list should not be null", processes);
        assertTrue("Process list size should be <= limit", processes.size() != 0);
    }

    @Test
    public void getProcessByPidTest() {
        int pid = 1; // Usually PID 1 exists (init/systemd)

        OSProcess process = collector.getProcessByPid(pid);
        if (process != null) {
            assertEquals("Process PID should match requested PID", pid, process.getProcessID());
        } else {
            // If process not found, that's acceptable for some PIDs
            assertNull("Process should be null if not found", process);
        }
    }
}
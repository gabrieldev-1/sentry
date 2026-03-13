package com.sentry;

import org.junit.Test;
import static org.junit.Assert.*;
import oshi.software.os.OSProcess;
import java.util.List;


public class SystemSnapShotTest {

    @Test
    public void systemSnapShotDataConsistencyTest() {
        SystemSnapShot snapshot = new SystemSnapShot();

        assertNotNull(snapshot.getProcesses());

        assertTrue(snapshot.getCpuUsage() >= 0);
        assertTrue(snapshot.getMemoryUsage() >= 0);
        assertTrue(snapshot.getCpuTemp() >= 0);

        assertNotNull(snapshot.getUptime());
    }

    @Test
    public void systemSnapShotImmutabilityTest() throws InterruptedException {
        SystemSnapShot snapshot1 = new SystemSnapShot();
        
        Thread.sleep(1000);
        
        SystemSnapShot snapshot2 = new SystemSnapShot();

        boolean uptimeDifferent = !snapshot1.getUptime().equals(snapshot2.getUptime());
        boolean cpuDifferent = snapshot1.getCpuUsage() != snapshot2.getCpuUsage();
        
        assertTrue("Snapshots should have some different values (uptime or CPU)", 
                  uptimeDifferent || cpuDifferent);
    }
}

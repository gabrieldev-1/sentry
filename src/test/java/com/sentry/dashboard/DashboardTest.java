package com.sentry.dashboard;

import org.junit.Test;
import static org.junit.Assert.*;
import oshi.software.os.OSProcess;
import com.sentry.dashboard.Dashboard;
import com.sentry.SystemSnapShot;

public class DashboardTest {
    @Test
    public void dashboardInitializationTest() throws Exception {
        Dashboard dashboard = new Dashboard();
        assertNotNull(dashboard.getScreen());
        dashboard.close();
    }

    @Test
    public void dashboardRenderingTest() throws Exception {
        Dashboard dashboard = new Dashboard();
        SystemSnapShot snapshot = new SystemSnapShot();
        
        try {
            dashboard.render(snapshot);
        } catch (Exception e) {
            fail("Dashboard render should not throw exceptions: " + e.getMessage());
        }
        
        dashboard.close();
    }
}

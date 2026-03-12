package com.sentry;

import com.sentry.dashboard.Dashboard;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Dashboard dashboard = null;

        try {
            dashboard = new Dashboard();
            while (true) {
                SystemSnapShot snapshot = new SystemSnapShot();
                dashboard.render(snapshot);
                Thread.sleep(2000);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (dashboard != null) {
                try {
                    dashboard.close();

                } catch (IOException e) {
                    e.printStackTrace();
                    
                }
            }
        }
    }
}
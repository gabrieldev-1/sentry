package com.sentry;

import com.sentry.dashboard.Dashboard;

import java.io.IOException;

import com.sentry.SystemSnapShot;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Dashboard dashboard = new Dashboard();

        while(true) {
            try {
                SystemSnapShot snapshot = new SystemSnapShot();
                dashboard.generateDashbord(snapshot);

                Thread.sleep(2000);
            } finally {
                dashboard.getScreen().stopScreen();

            }
        }
    }
}
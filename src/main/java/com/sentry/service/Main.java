package com.sentry;

import com.sentry.dashboard.Dashboard;

import com.sentry.SystemSnapShot;

import java.io.IOException;

public class Main {

    public static final String mark = """
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⢀⢀⢀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⢀⠀⡴⠰⠞⠿⠛⠁⠓⠖⠲⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⢸⠆⢁⠶⠿⠇⠹⠁⠸⠷⠏⣈⡀⢰⠀⠈⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⡁⠴⠛⢀⡀⠀⠀⢀⠀⠀⠀⠀⡀⠀⠀⠂⠄⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠠⠀⢠⣴⣿⠀⠄⠈⠉⠀⠀⢀⠀⢻⡗⠀⠀⠐⠡⣄⡀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⣤⠒⢺⣿⣿⣆⠙⠄⢤⠠⠔⠘⢢⣞⠋⠀⢀⣰⣧⣬⡇⠀⠀⠀⠀
            ⠀⠀⠀⠀⠈⠪⡅⠲⢿⢽⣿⣿⣶⣶⣦⣶⣿⠇⠴⠋⠍⢉⣹⣿⠿⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠰⠆⠁⠀⢈⠉⠹⣹⠈⠁⠀⠆⢰⢆⢀⣾⣾⠉⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠃⠷⠀⠄⣤⡀⠀⣠⠠⣤⠄⠼⠟⠉⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠁⠈⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                               Sentry v1.0
                      Author: gabrieldev-1
            """;

    /**
     * Displays help message with usage instructions for the Sentry monitoring application.
     */
    public static void helpMessage() {
        System.out.println(mark + "\n");
        System.out.println("DESCRIPTION:");
        System.out.println("    Real-time system monitoring tool that displays CPU, memory, and process information");
        System.out.println("    in a terminal-based dashboard using the Lanterna library.");
        System.out.println();
        System.out.println("USAGE:");
        System.out.println("    java -jar sentry.jar [OPTIONS]");
        System.out.println();
        System.out.println("OPTIONS:");
        System.out.println("    -h, --help    Show this help message and exit");
        System.out.println();
        System.out.println("CONTROLS:");
        System.out.println("    Ctrl+C        Exit the application");
        System.out.println();
        System.out.println("DISPLAYED INFORMATION:");
        System.out.println("    - CPU: Individual core usage, total CPU usage, and temperature");
        System.out.println("    - Memory: Total and used memory with percentage");
        System.out.println("    - System: Uptime, total processes and threads");
        System.out.println("    - Processes: Top processes by CPU usage with detailed metrics");
        System.out.println();
        System.out.println("EXAMPLES:");
        System.out.println("    java -jar sentry.jar        # Start monitoring");
        System.out.println("    java -jar sentry.jar -h     # Show this help");
        System.out.println("    java -jar sentry.jar --help # Show this help");
    }

    public static void main(String[] args) {
        if (args.length > 0 && (args[0].equals("-h") || args[0].equals("--help"))) {
            helpMessage();
            System.exit(0);
        }

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
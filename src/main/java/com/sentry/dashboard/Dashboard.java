package com.sentry.dashboard;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import com.sentry.SystemSnapShot;
import oshi.software.os.OSProcess;

import java.io.IOException;

/**
 * Dashboard renders a real-time system monitoring interface using Lanterna.
 * Displays CPU metrics, memory usage, process information, and system statistics.
 */
public class Dashboard {
    private Terminal defTerminal;
    private Screen screen;
    private TextGraphics tg;
    
    private static final int HEADER_HEIGHT = 13;
    private static final int SEPARATOR_ROW = 8;
    private static final int SEPARATOR_COLUMN = 1;

    /**
     * Initializes the Dashboard with a Lanterna terminal and screen
     *
     * @throws IOException if terminal initialization fails
     * @throws InterruptedException if thread interruption occurs
     */
    public Dashboard() throws IOException, InterruptedException {
        this.defTerminal = new DefaultTerminalFactory().createTerminal();
        this.screen = new TerminalScreen(defTerminal);
        this.screen.startScreen();
        this.tg = screen.newTextGraphics();
    }

    public Screen getScreen() {
        return screen;
    }

    /**
     * rendering each graphical component individually
     * 
     * @param snapshot the SystemSnap containg current system metrics
     * @throws IOException if rendering fails
    */
    public void render(SystemSnapShot snapshot) throws IOException {
        screen.clear();

        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.putString(1, 1, "SENTRY MONITOR - Press Ctril+Z to exit");

        renderHeader(snapshot);
        //renderSeparator(SEPARATOR_ROW);
        //renderProcessTable(snapshot);
        
        screen.refresh();
    }

    private void renderHeader(SystemSnapShot snapshot) {

        // Lanterna variables:
        int rowStart = 3;
        int col1 = 1;
        int col2 = 50;
        int col3 = 97;

        // CPU INFORMATION (Colomn 1)
        double cpuUsage = snapshot.getCpuUsage();
        double cpuTemp = snapshot.getCpuTemp();

        tg.setForegroundColor(TextColor.ANSI.YELLOW);
        tg.putString(col1, rowStart, "CPU INFORMATION");

        double[] cores = snapshot.getCoresUsage();
        StringBuilder coresStrBuilder = new StringBuilder("Cores: ");

        for (int i = 0; i < cores.length; i++) {
            coresStrBuilder.append(String.format("C%d: %.1f%% ", i, cores[i]));
        }

        String coresStr = coresStrBuilder.toString();
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);

        tg.putString(col1, rowStart + 1, coresStr);
        tg.putString(col1, rowStart + 2, String.format("Total CPU: %.1f%%", cpuUsage));
        tg.putString(col1, rowStart + 3, String.format("Temperature: %.1f°C", cpuTemp));

        // MEMORY INFORMATION (Column 2)
        double totalMemory = snapshot.getTotalMemory();
        double memoryUsage = snapshot.getMemoryUsage();

        tg.setForegroundColor(TextColor.ANSI.YELLOW);
        tg.putString(col2, rowStart, "MEMORY INFORMATION");

        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
        tg.putString(col2, rowStart + 1, String.format("Total: %.1f%%", totalMemory));
        tg.putString(col2, rowStart + 2, String.format("Usage: %.1f%%", memoryUsage));


        // SYSTEM INFORMATION (Column 3)
        int numOfThreads = snapshot.getNumThreads();
        int numOfProcess = snapshot.getNumProcesses();

        tg.setForegroundColor(TextColor.ANSI.YELLOW);
        tg.putString(col3, rowStart, "PROCESSES INFORMATION:");

        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
        tg.putString(col3, rowStart + 1, "process: " + numOfProcess);
        tg.putString(col3, rowStart + 2, "threads: " + numOfThreads);


    }

    /**
     * Closes the dashboard and restores the terminal to its original state.
     *
     * @throws IOException if closing the screen fails
     */
    public void close() throws IOException {
        screen.stopScreen();
    }

}

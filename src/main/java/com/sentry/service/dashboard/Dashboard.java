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

        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.putString(1, 1, "SENTRY MONITOR - Press Ctril+Z to exit");

        renderHeader(snapshot);
        renderSeparator(SEPARATOR_COLUMN, SEPARATOR_ROW);
        renderProcessTable(snapshot);
        
        screen.refresh();
    }

    private void renderHeader(SystemSnapShot snapshot) {
        int rowStart = 3;

        int col1 = 1;
        int col2 = 42;
        int col3 = 82;

        // CPU INFORMATION
        tg.setForegroundColor(TextColor.ANSI.YELLOW);
        tg.putString(col1, rowStart, "CPU INFORMATION");
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);

        double[] cores = snapshot.getCoresUsage();
        int coresPerRow = 2;
        int currentRow = rowStart + 1;

        for (int i = 0; i < cores.length; i++) {
            int currentX = col1 + (i % coresPerRow * 18);

            if (i > 0 && i % coresPerRow == 0) currentRow++;
            tg.putString(currentX, currentRow, String.format("C%d: %5.1f%%", i, cores[i]));
        }

        int nextDataRow = currentRow + 1;
        tg.putString(col1, nextDataRow, String.format("Total CPU: %.1f%%", snapshot.getCpuUsage()));
        tg.putString(col1, nextDataRow + 1, String.format("Temp:      %.1f°C", snapshot.getCpuTemp()));

        // MEMORY INFORMATION
        tg.setForegroundColor(TextColor.ANSI.YELLOW);
        tg.putString(col2, rowStart, "MEMORY INFORMATION");
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);

        double totalGB = snapshot.getTotalMemory() / 1024.0 / 1024.0 / 1024.0;
        tg.putString(col2, rowStart + 1, String.format("Total: %.1f GB", totalGB));
        tg.putString(col2, rowStart + 2, String.format("Usage: %.1f%%", snapshot.getMemoryUsage()));

        // SYSTEM INFORMATION
        tg.setForegroundColor(TextColor.ANSI.YELLOW);
        tg.putString(col3, rowStart, "SYSTEM STATUS");
        
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
        tg.putString(col3, rowStart + 1, "Uptime: " + snapshot.getUptime());
        tg.putString(col3, rowStart + 2, "Processes: " + snapshot.getProcesses().size());
        tg.putString(col3, rowStart + 3, "Threads:   " + snapshot.getProcesses().stream().mapToInt(p -> p.getThreadCount()).sum());
    }

    /**
     * Renders a horizontal separator line.
     *
     * @param row the row number to draw the separator at
     */
    public void renderSeparator(int column, int row) {
        tg.setForegroundColor(TextColor.ANSI.BLUE);
        StringBuilder separetor = new StringBuilder();

        for(int i = 0; i < 110; i++) {
            separetor.append("─");
        }
    
        tg.putString(column, row, separetor.toString());
    }

    public void renderProcessTable(SystemSnapShot snapshot) {
        int row = SEPARATOR_ROW + 1;
        int startCol = 1;

        tg.setForegroundColor(TextColor.ANSI.GREEN);
        String header = String.format("%-8s %-15s %-40s %-10s %-12s %-10s", 
            "PID", "PROGRAM", "COMMAND", "THREADS", "MEMORY%", "CPU%");
        tg.putString(startCol, row++, header);

        for (OSProcess p : snapshot.getProcesses()) {
            if (row >= screen.getTerminalSize().getRows() - 1) break; 
            
            String cmd = p.getCommandLine();
            if (cmd == null || cmd.isEmpty()) cmd = p.getName();
            
            double memUsage = (p.getResidentSetSize() / (double) snapshot.getTotalMemory()) * 100;
            double cpuPercent = p.getProcessCpuLoadCumulative() * 100;

            tg.setForegroundColor(TextColor.ANSI.DEFAULT);
            String rowData = String.format("%-8d %-15s %-40s %-10d %-12.1f %-10.1f", 
                p.getProcessID(),
                truncate(p.getName(), 15),
                truncate(cmd, 40), 
                p.getThreadCount(),
                memUsage,
                cpuPercent);
            
            tg.putString(startCol, row++, rowData);
        }
    }

    /**
     * Truncates a string to the specified maximum length.
     *
     * @param str the string to truncate
     * @param maxLength the maximum length
     * @return the truncated string, or original if shorter than maxLength
     */
    private String truncate(String str, int maxLength) {
        if (str.length() > maxLength) {
            return str.substring(0, maxLength - 3) + "...";
        }
        return str;
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

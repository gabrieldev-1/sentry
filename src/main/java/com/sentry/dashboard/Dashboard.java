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

public class Dashboard {
    private Terminal defTerminal;
    private Screen screen;
    private TextGraphics tg;

    public Dashboard() throws IOException, InterruptedException {
        this.defTerminal = new DefaultTerminalFactory().createTerminal();
        this.screen = new TerminalScreen(defTerminal);
        this.tg = screen.newTextGraphics();
    }

    public Screen getScreen() {
        return screen;
    }

    public void generateDashbord(SystemSnapShot snapshot) {
        screen.clear();
        
        
    }
}

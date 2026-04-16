package com.sentry.controller;

import com.sentry.SystemSnapShot;

@RestController
@RequestMapping("/v1/monitor")
public class systemController {
    private static final SystemSnapShot SNAPSHOT = new SystemSnapShot();

    @GetMapping("/memoryUsage")
    public double getMemoryUsage() {
        return SNAPSHOT.getMemoryUsage();
    }

    @GetMapping("/cpuUsage")
    public double getCpuUsage() {
        return SNAPSHOT.getCpuUsage();
    }

    @GetMapping("/cpuTemperature")
    public double getCpuTemp() {
        return SNAPSHOT.getCpuTemp();
    }

    @GetMapping("/uptime")
    public double getUptime() {
        return SNAPSHOT.getUptime();
    }
}

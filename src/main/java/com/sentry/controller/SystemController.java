package com.sentry.controller;

import com.sentry.SystemSnapShot;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for system monitoring endpoints.
 * Provides API access to system snapshots and monitoring data.
 */
@RestController
@RequestMapping("/api/system")
public class SystemController {

    /**
     * Get current system snapshot.
     * @return SystemSnapShot containing current system metrics
     */
    @GetMapping("/snapshot")
    public SystemSnapShot getSystemSnapshot() {
        return new SystemSnapShot();
    }

    /**
     * Health check endpoint.
     * @return Simple health status
     */
    @GetMapping("/health")
    public String health() {
        return "Sentry is running";
    }
}
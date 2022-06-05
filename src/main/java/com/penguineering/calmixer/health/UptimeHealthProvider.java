package com.penguineering.calmixer.health;

import jakarta.inject.Singleton;

import java.time.Duration;
import java.util.Optional;

@Singleton
public class UptimeHealthProvider implements HealthProvider {
    private final long start = System.currentTimeMillis();

    @Override
    public String getKey() {
        return "uptime";
    }

    @Override
    public Optional<Object> getStatus() {
        final long now = System.currentTimeMillis();
        final String duration = Duration.ofMillis(now - this.start).toString();
        return Optional.of(duration);
    }

    @Override
    public boolean isHealthy() {
        return true;
    }
}

package com.penguineering.calmixer.health;

import jakarta.inject.Singleton;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Singleton
public class TimestampHealthProvider implements HealthProvider {
    @Override
    public String getKey() {
        return "timestamp";
    }

    @Override
    public Optional<Object> getStatus() {
        final String timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return Optional.of(timestamp);
    }

    @Override
    public boolean isHealthy() {
        return true;
    }
}

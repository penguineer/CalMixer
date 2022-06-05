package com.penguineering.calmixer.health;

import com.penguineering.calmixer.Application;
import com.penguineering.calmixer.health.HealthProvider;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class VersionHealthProvider implements HealthProvider {
    private final String version = Application.class.getPackage().getImplementationVersion();

    @Override
    public String getKey() {
        return "version";
    }

    @Override
    public Optional<Object> getStatus() {
        if (this.version == null)
           return Optional.empty();

        return Optional.of(version);
    }

    @Override
    public boolean isHealthy() {
        return true;
    }
}

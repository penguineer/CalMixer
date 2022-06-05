package com.penguineering.calmixer.health;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class HealthTest {
    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testHealthEndpoint() {
        HttpRequest<String> request = HttpRequest.GET("/health");
        String body = client.toBlocking().retrieve(request);

        assertNotNull(body);

        final JSONObject status = new JSONObject(body);
        assertTrue((Boolean) status.query("/healthy"));
        assertNotNull(status.query("/uptime"));
    }

    @Inject
    @Named("Version")
    HealthProvider versionHealthProvider;

    @Test
    void testVersionHealthProvider() {
        // return correct key
        assertEquals("version", versionHealthProvider.getKey());
        // in the test context there is no package, so we will not get a version
        assertTrue(versionHealthProvider.getStatus().isEmpty());
        // always healthy
        assertTrue(versionHealthProvider.isHealthy());
    }

    @Inject
    @Named("Uptime")
    HealthProvider uptimeHealthProvider;

    @Test
    void testUptimeHealthProvider() {
        // return correct key
        assertEquals("uptime", uptimeHealthProvider.getKey());
        // must return a correct ISO8601 duration
        Optional<Object> duration = uptimeHealthProvider.getStatus();
        assertTrue(duration.isPresent());
        try {
            Duration.parse((String)duration.get());
        } catch (DateTimeParseException e) {
            fail("Failed to parse duration from UptimeHealthProvider: " + duration.get());
        }
        // always healthy
        assertTrue(uptimeHealthProvider.isHealthy());
    }
}

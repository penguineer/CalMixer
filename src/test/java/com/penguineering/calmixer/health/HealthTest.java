package com.penguineering.calmixer.health;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

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
}

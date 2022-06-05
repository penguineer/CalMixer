package com.penguineering.calmixer;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import jakarta.inject.Inject;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;

@Controller("/health")
public class HealthEndpoint {
    @Inject
    List<HealthProvider> providers;

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    public String health() {
        final JSONObject response = new JSONObject();

        boolean healthy = true;

        for (HealthProvider provider: providers) {
            Optional<Object> status = provider.getStatus();
            status.ifPresent(o -> response.put(provider.getKey(), o));
            healthy &= provider.isHealthy();
        }

        response.put("healthy", healthy);

        return response.toString(2);
    }
}

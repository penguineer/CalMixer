package com.penguineering.calmixer.health;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @ApiResponse(responseCode = "200", description = "Service is healthy")
    @ApiResponse(responseCode = "500", description = "Service is failing")
    public HttpResponse<String> health() {
        final JSONObject response = new JSONObject();

        boolean healthy = true;

        for (HealthProvider provider: providers) {
            Optional<Object> status = provider.getStatus();
            status.ifPresent(o -> response.put(provider.getKey(), o));
            healthy &= provider.isHealthy();
        }

        response.put("healthy", healthy);

        return HttpResponse
                .status(healthy ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR, null)
                .body(response.toString(2));
    }
}

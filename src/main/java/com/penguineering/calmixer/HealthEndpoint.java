package com.penguineering.calmixer;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import org.json.JSONObject;

@Controller("/health")
public class HealthEndpoint {
    @Get
    @Produces(MediaType.APPLICATION_JSON)
    public String health() {
        final JSONObject response = new JSONObject();
        response.put("healthy", true);

        return response.toString();
    }
}

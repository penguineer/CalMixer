package com.penguineering.calmixer.session;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

@Controller("/session")
public class SessionController {
    @Inject
    SessionManager sessionManager;

    @Post("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<String> create(HttpRequest<?> request) {
        final Session s = sessionManager.createSession();

        return HttpResponse.created(URI.create("/session/"+s.getToken()));
    }

    @Post("/{token}/source")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<String> addSource(final String token, @Body final String body) {
        final Session s = sessionManager.retrieveSession(token);

        if (s == null)
            return  HttpResponse.notFound();

        final JSONObject json_body;
        try {
            json_body = new JSONObject(body);
            final URL url = URI.create(json_body.getString("url")).toURL();
            final String label = json_body.getString("label");

            s.addSource(CalSource.withValues(url, label));
        } catch (JSONException | MalformedURLException | IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage());
        }

        return HttpResponse.ok();
    }

    @Get("/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<String> retrieve(final String token) {
        final Session s = sessionManager.retrieveSession(token);

        if (s == null)
            return  HttpResponse.notFound();

        return HttpResponse.ok(s.toJson().toString(2));
    }
}

package com.penguineering.calmixer.calendar;

import com.penguineering.calmixer.session.Session;
import com.penguineering.calmixer.session.SessionManager;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.server.types.CustomizableResponseTypeException;
import io.micronaut.http.server.types.files.StreamedFile;
import jakarta.inject.Inject;


@Controller("/calendar")
public class CalendarController {
    @Inject
    SessionManager sessionManager;

    @Get("/{token}/{label}")
    public MutableHttpResponse<StreamedFile> mix(final String token, final String label) {
        // get the session
        final Session s = sessionManager.retrieveSession(token);
        if (s == null)
            return HttpResponse.notFound();

        CalSource cs = s.getSources().stream().filter(src -> src.getLabel().equals(label)).findFirst().orElse(null);

        if (cs == null)
            return HttpResponse.notFound();

        try {
            return HttpResponse.ok(new StreamedFile(cs.getUrl()));
        } catch (CustomizableResponseTypeException e) {
            return HttpResponse.status(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}

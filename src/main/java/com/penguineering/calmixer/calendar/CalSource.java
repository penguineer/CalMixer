package com.penguineering.calmixer.calendar;

import net.jcip.annotations.Immutable;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Map;

@Immutable
public class CalSource {
    public static CalSource withJson(final JSONObject json) {
        try {
            final URL url = URI.create(json.getString("url")).toURL();
            final String label = json.getString("label");

            CalAccess access = CalAccess.withJson(json);

            return new CalSource(url, label, access);
        } catch (JSONException | MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }

    }

    private final URL url;
    private final String label;
    private final CalAccess access;

    private CalSource(final URL url, final String label, final CalAccess access) {
        this.url = url;
        this.label = label;
        this.access = access;
    }

    public URL getUrl() {
        return url;
    }

    public String getLabel() {
        return label;
    }

    public CalAccess getAccess() {
        return access;
    }

    public JSONObject toJson() {
        return new JSONObject(Map.of(
                "url", url,
                "label", label,
                "access", access.toJson()
        ));
    }
}

package com.penguineering.calmixer.session;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.*;

public class Session {
    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    public static Session create() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        final String token = base64Encoder.encodeToString(randomBytes);

        return createWithToken(token);
    }

    public static Session createWithToken(final String token) {
        return new Session(token);
    }

    private final String token;
    private final List<CalSource> sources;

    private Session(final String token) {
        this.token = token;
        this.sources = new ArrayList<>();
    }

    public String getToken() {
        return token;
    }

    public void addSource(final CalSource source) {
        if (source == null)
            throw new IllegalArgumentException("Source must not be null!");

        this.sources.add(source);
    }

    public List<CalSource> getSources() {
        return Collections.unmodifiableList(this.sources);
    }

    public JSONObject toJson() {
        final JSONObject json_session = new JSONObject(Map.of(
                "token", this.token
        ));

        final JSONArray json_sources = new JSONArray();
        for (final CalSource source : this.sources) {
            final JSONObject json_source = new JSONObject(Map.of(
                    "url", source.getUrl().toString(),
                    "label", source.getLabel()
            ));
            json_sources.put(json_source);
        }
        json_session.put("sources", json_sources);

        final JSONObject json = new JSONObject();
        json.put("session", json_session);

        return json;
    }
}

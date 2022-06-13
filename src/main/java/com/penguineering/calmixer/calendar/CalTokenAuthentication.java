package com.penguineering.calmixer.calendar;

import net.jcip.annotations.Immutable;
import org.json.JSONObject;

import java.util.Map;

@Immutable
public class CalTokenAuthentication implements CalAuthentication{
    public static CalTokenAuthentication withToken(String token) {
        return new CalTokenAuthentication(token);
    }

    private final String token;

    private CalTokenAuthentication(String token) {
        if (token == null)
            throw new IllegalArgumentException("Token must not be null!");

        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public JSONObject toJson() {
        return new JSONObject(Map.of(
                "token", token
        ));
    }
}

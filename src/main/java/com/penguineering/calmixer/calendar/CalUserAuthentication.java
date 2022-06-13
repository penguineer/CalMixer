package com.penguineering.calmixer.calendar;

import net.jcip.annotations.Immutable;
import org.json.JSONObject;

import java.util.Map;

@Immutable
public class CalUserAuthentication implements CalAuthentication {
    public static CalUserAuthentication withCredentials(String user, String passwd) {
        return new CalUserAuthentication(user, passwd);
    }

    private final String user;
    private final String password;

    private CalUserAuthentication(String user, String password) {
        if (user == null)
            throw new IllegalArgumentException("User name must not be null!");
        if (password == null)
            throw new IllegalArgumentException("Password must not be null!");

        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public JSONObject toJson() {
        return new JSONObject(Map.of(
                "user", user,
                "password", password
        ));
    }
}

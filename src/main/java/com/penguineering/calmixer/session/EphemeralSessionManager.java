package com.penguineering.calmixer.session;

import jakarta.inject.Singleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Manage sessions in memory
 */
@Singleton
public class EphemeralSessionManager implements SessionManager {
    private final Map<String, Session> sessions;

    public EphemeralSessionManager() {
        this.sessions = new HashMap<>();
    }


    @Override
    public Session createSession() {
        final Session s = Session.create();

        this.sessions.put(s.getToken(), s);

        return s;
    }

    @Override
    public Session retrieveSession(String token) {
        return this.sessions.getOrDefault(token, null);
    }
}

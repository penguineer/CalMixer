package com.penguineering.calmixer.session;

public interface SessionManager {
    Session createSession();

    Session retrieveSession(final String token);
}

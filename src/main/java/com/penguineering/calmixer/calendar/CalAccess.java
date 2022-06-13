package com.penguineering.calmixer.calendar;

import net.jcip.annotations.Immutable;
import org.json.JSONObject;

import java.util.Map;

@Immutable
public class CalAccess {
    public enum Protocol {
        HTTP,
        DAV
    }

    public static CalAccess withJson(JSONObject json) {
        final String json_proto = json.has("protocol") ? json.getString("protocol") : "http";
        final Protocol proto = Protocol.valueOf(json_proto.toUpperCase());

        final CalAuthentication auth;
        if (json.has("user") && json.has("password"))
            auth = CalUserAuthentication.withCredentials(
                    json.getString("user"),
                    json.getString("password")
            );
        else if (json.has("token"))
            auth = CalTokenAuthentication.withToken(
                    json.getString("token")
            );
        else
            auth = null;

        return new CalAccess(proto, auth);
    }


    private final Protocol protocol;
    private final CalAuthentication auth;

    private CalAccess(Protocol protocol, CalAuthentication auth) {
        this.protocol = protocol;
        this.auth = auth;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public CalAuthentication getAuth() {
        return auth;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject(Map.of(
                "protocol", protocol.toString().toLowerCase()
        ));
        if (auth != null)
            json.put("authentication", auth.toJson());

        return json;
    }
}

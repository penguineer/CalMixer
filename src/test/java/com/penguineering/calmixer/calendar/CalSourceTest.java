package com.penguineering.calmixer.calendar;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class CalSourceTest {
    @Test
    void testCalUserAuthentication() {
        Exception e;

        // test user null
        e = assertThrows(
                IllegalArgumentException.class,
                () -> CalUserAuthentication.withCredentials(null, "")
        );
        assertTrue(e.getMessage().contains("User name must not be null!"));

        // test password null
        e = assertThrows(
                IllegalArgumentException.class,
                () -> CalUserAuthentication.withCredentials("", null)
        );
        assertTrue(e.getMessage().contains("Password must not be null!"));

        CalUserAuthentication cua;

        // empty values
        cua = CalUserAuthentication.withCredentials("", "");
        assertEquals("", cua.getUser());
        assertEquals("", cua.getPassword());

        // some typical values
        cua = CalUserAuthentication.withCredentials("me", "123");
        assertEquals("me", cua.getUser());
        assertEquals("123", cua.getPassword());

        JSONObject json = cua.toJson();
        assertTrue(json.has("user"));
        assertEquals("me", json.getString("user"));
        assertTrue(json.has("password"));
        assertEquals("123", json.getString("password"));
    }

    @Test
    void testCalTokenAuthentication() {
        // test token null
        @SuppressWarnings("ResultOfMethodCallIgnored")
        final Exception e = assertThrows(
                IllegalArgumentException.class,
                () -> CalTokenAuthentication.withToken(null)
        );
        assertTrue(e.getMessage().contains("Token must not be null!"));

        CalTokenAuthentication cta;

        // test empty token
        cta = CalTokenAuthentication.withToken("");
        assertEquals("", cta.getToken());

        // test typical token
        cta = CalTokenAuthentication.withToken("ooxochighuigaYu4ajohvigh3tiebei5");
        assertEquals("ooxochighuigaYu4ajohvigh3tiebei5", cta.getToken());

        JSONObject json = cta.toJson();
        assertTrue(json.has("token"));
        assertEquals("ooxochighuigaYu4ajohvigh3tiebei5", json.getString("token"));
    }

    @Test
    void testCalAccess() {
        CalAccess ca;

        // default access: http and no auth
        ca = CalAccess.withJson(new JSONObject(
                "{}"
                ));
        assertEquals(CalAccess.Protocol.HTTP, ca.getProtocol());
        assertNull(ca.getAuth());

        // http access and no auth
        ca = CalAccess.withJson(new JSONObject(
                """
                        {
                            "protocol": "http"
                        }
                        """
        ));
        assertEquals(CalAccess.Protocol.HTTP, ca.getProtocol());
        assertNull(ca.getAuth());

        // http access, user/pass auth
        ca = CalAccess.withJson(new JSONObject(
                """
                        {
                            "protocol": "http",
                            "user": "me",
                            "password": "123"
                        }
                        """
        ));
        assertEquals(CalAccess.Protocol.HTTP, ca.getProtocol());
        assertNotNull(ca.getAuth());
        assertTrue(ca.getAuth() instanceof CalUserAuthentication);
        assertEquals("me", ((CalUserAuthentication)(ca.getAuth())).getUser());
        assertEquals("123", ((CalUserAuthentication)(ca.getAuth())).getPassword());

        // http access, user but no pass
        ca = CalAccess.withJson(new JSONObject(
                """
                        {
                            "protocol": "http",
                            "user": "me"
                        }
                        """
        ));
        assertEquals(CalAccess.Protocol.HTTP, ca.getProtocol());
        assertNull(ca.getAuth());

        // http access, pass but no user
        ca = CalAccess.withJson(new JSONObject(
                """
                        {
                            "protocol": "http",
                            "password": "123"
                        }
                        """
        ));
        assertEquals(CalAccess.Protocol.HTTP, ca.getProtocol());
        assertNull(ca.getAuth());

        // http access, token auth
        ca = CalAccess.withJson(new JSONObject(
                """
                        {
                            "protocol": "http",
                            "token": "me123"
                        }
                        """
        ));
        assertEquals(CalAccess.Protocol.HTTP, ca.getProtocol());
        assertNotNull(ca.getAuth());
        assertTrue(ca.getAuth() instanceof CalTokenAuthentication);
        assertEquals("me123", ((CalTokenAuthentication)(ca.getAuth())).getToken());

        // dav access, user/pass auth
        ca = CalAccess.withJson(new JSONObject(
                """
                        {
                            "protocol": "dav",
                            "user": "me",
                            "password": "123"
                        }
                        """
        ));
        assertEquals(CalAccess.Protocol.DAV, ca.getProtocol());
        assertNotNull(ca.getAuth());
        assertTrue(ca.getAuth() instanceof CalUserAuthentication);
        assertEquals("me", ((CalUserAuthentication)(ca.getAuth())).getUser());
        assertEquals("123", ((CalUserAuthentication)(ca.getAuth())).getPassword());

        // http access, token auth
        ca = CalAccess.withJson(new JSONObject(
                """
                        {
                            "protocol": "dav",
                            "token": "me123"
                        }
                        """
        ));
        assertEquals(CalAccess.Protocol.DAV, ca.getProtocol());
        assertNotNull(ca.getAuth());
        assertTrue(ca.getAuth() instanceof CalTokenAuthentication);
        assertEquals("me123", ((CalTokenAuthentication)(ca.getAuth())).getToken());

        // invalid protocol
        final Exception e = assertThrows(
                IllegalArgumentException.class,
                () -> CalAccess.withJson(new JSONObject(
                        """
                                {
                                    "protocol": "foo"
                                }
                                """
                ))
        );
        assertTrue(e.getMessage().contains("No enum constant com.penguineering.calmixer.calendar.CalAccess.Protocol.FOO"));
    }

    @Test
    void testCalSource() {
        Exception e;

        // empty
        e = assertThrows(
                IllegalArgumentException.class,
                () -> CalSource.withJson(new JSONObject(
                        """
                                {
                                }
                                """
                ))
        );
        assertTrue(e.getMessage().contains("JSONObject[\"url\"] not found."));

        // valid url only, label missing
        e = assertThrows(
                IllegalArgumentException.class,
                () -> CalSource.withJson(new JSONObject(
                        """
                                {
                                    "url": "http://www.example.com"
                                }
                                """
                ))
        );
        assertTrue(e.getMessage().contains("JSONObject[\"label\"] not found."));

        // empty url
        assertThrows(
                IllegalArgumentException.class,
                () -> CalSource.withJson(new JSONObject(
                        """
                                {
                                    "url": ""
                                }
                                """
                ))
        );
        // the actual exception text is URI specific and will not be tested here

        // invalid url
        assertThrows(
                IllegalArgumentException.class,
                () -> CalSource.withJson(new JSONObject(
                        """
                                {
                                    "url": "+foo"
                                }
                                """
                ))
        );
        // the actual exception text is URI specific and will not be tested here

        // invalid protocol
        e = assertThrows(
                IllegalArgumentException.class,
                () -> CalSource.withJson(new JSONObject(
                        """
                                {
                                    "url": "http://www.example.com",
                                    "label": "test",
                                    "protocol": "foo"
                                }
                                """
                ))
        );
        assertTrue(e.getMessage().contains("No enum constant com.penguineering.calmixer.calendar.CalAccess.Protocol.FOO"));


        CalSource cs;
        JSONObject json;

        // valid url and label
        cs = CalSource.withJson(new JSONObject(
                """
                        {
                            "url": "http://www.example.com",
                            "label": "test"
                        }
                        """
        ));
        assertEquals("http://www.example.com", cs.getUrl().toString());
        assertEquals("test", cs.getLabel());
        assertNotNull(cs.getAccess());
        assertEquals(cs.getAccess().getProtocol(), CalAccess.Protocol.HTTP);
        assertNull(cs.getAccess().getAuth());
        json = cs.toJson();
        assertTrue(json.has("url"));
        assertEquals("http://www.example.com", json.getString("url"));
        assertTrue(json.has("label"));
        assertEquals("test", json.getString("label"));
        assertTrue(json.has("access"));
        // access JSON has been tested

        // http and basic auth
        cs = CalSource.withJson(new JSONObject(
                """
                        {
                            "url": "http://www.example.com",
                            "label": "test",
                            "user": "me",
                            "password": "123"
                        }
                        """
        ));
        assertNotNull(cs.getAccess());
        assertEquals(cs.getAccess().getProtocol(), CalAccess.Protocol.HTTP);
        assertNotNull(cs.getAccess().getAuth());
        assertTrue(cs.getAccess().getAuth() instanceof CalUserAuthentication);
        assertEquals("me", ((CalUserAuthentication)(cs.getAccess().getAuth())).getUser());
        assertEquals("123", ((CalUserAuthentication)(cs.getAccess().getAuth())).getPassword());

        // dav and token auth
        cs = CalSource.withJson(new JSONObject(
                """
                        {
                            "url": "http://www.example.com",
                            "label": "test",
                            "protocol": "dav",
                            "token": "me123"
                        }
                        """
        ));
        assertNotNull(cs.getAccess());
        assertEquals(cs.getAccess().getProtocol(), CalAccess.Protocol.DAV);
        assertNotNull(cs.getAccess().getAuth());
        assertTrue(cs.getAccess().getAuth() instanceof CalTokenAuthentication);
        assertEquals("me123", ((CalTokenAuthentication)(cs.getAccess().getAuth())).getToken());
    }
}

package com.penguineering.calmixer.calendar;

import net.jcip.annotations.Immutable;

import java.net.URL;

@Immutable
public class CalSource {
    public static CalSource withValues(final URL url, final String label) {
        return new CalSource(url, label);
    }

    private final URL url;
    private final String label;

    private CalSource(final URL url, final String label) {
        this.url = url;
        this.label = label;
    }

    public URL getUrl() {
        return url;
    }

    public String getLabel() {
        return label;
    }
}

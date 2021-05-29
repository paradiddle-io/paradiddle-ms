/*
 * Paradiddle MS - A lightweight microservices library with a comprehensible codebase.
 * Copyright (c) Michael Juliano 2020-2021.
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License version 2 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program;
 * if not, write to:
 *
 * Free Software Foundation, Inc.
 * 59 Temple Place, Suite 330
 * Boston, MA 02111-1307 USA
 */
package io.paradiddle.ms.header;

import io.paradiddle.ms.Header;
import java.util.Map;

public enum ResponseHeaders implements ResponseHeaderName {
    ACCESS_CONTROL_ALLOW_ORIGIN("Access-Control-Allow-Origin"),
    ACCESS_CONTROL_ALLOW_CREDENTIALS("Access-Control-Allow-Credentials"),
    ACCESS_CONTROL_EXPOSE_HEADERS("Access-Control-Expose-Headers"),
    ACCESS_CONTROL_MAX_AGE("Access-Control-Max-Age"),
    ACCESS_CONTROL_ALLOW_METHODS("Access-Control-Allow-Methods"),
    ACCESS_CONTROL_ALLOW_HEADERS("Access-Control-Allow-Headers"),
    ACCEPT_PATCH("Accept-Patch"),
    ACCEPT_POST("Accept-Post"),
    ACCEPT_RANGES("Accept-Ranges"),
    AGE("Age"),
    ALT_SVC("Alt-Svc"),
    DELTA_BASE("Delta-Base"),
    ETAG("ETag"),
    EXPIRES("Expires"),
    IM("IM"),
    LAST_MODIFIED("Last-Modified"),
    LINK("Link"),
    LOCATION("Location"),
    P3P("P3P"),
    PRAGMA("Pragma"),
    PROXY_AUTHENTICATE("Proxy-Authenticate"),
    PUBLIC_KEY_PINS("Public-Key-Pins"),
    RETRY_AFTER("Retry-After"),
    SERVER("Server"),
    SET_COOKIE("Set-Cookie"),
    STRICT_TRANSPORT_SECURITY("Strict-Transport-Security"),
    TK("Tk"),
    VARY("Vary"),
    WWW_AUTHENTICATE("WWW-Authenticate");

    private final HeaderName name;

    ResponseHeaders(final String value) {
        this.name = new Basic(value);
    }

    public static HeaderNameCollection all() {
        return new HeaderNameSet(ResponseHeaders.values());
    }

    @Override
    public String value() {
        return name.value();
    }

    @Override
    public boolean matches(final Header header) {
        return name.matches(header);
    }

    @Override
    public boolean matches(final Map.Entry<String, String> header) {
        return name.matches(header);
    }

    @Override
    public boolean doesNotMatch(final Header header) {
        return name.doesNotMatch(header);
    }

    @Override
    public boolean doesNotMatch(final Map.Entry<String, String> header) {
        return name.doesNotMatch(header);
    }
}

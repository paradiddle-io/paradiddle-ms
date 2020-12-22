/*
 * Paradiddle MS - A lightweight microservices library with a comprehensible codebase.
 * Copyright (c) Michael Juliano 2020.
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

public enum RequestHeaders implements RequestHeaderName {
    A_IM("A-IM"),
    ACCEPT("Accept"),
    ACCEPT_CHARSET("Accept-Charset"),
    ACCEPT_DATETIME("Accept-Datetime"),
    ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_LANGUAGE("Accept-Language"),
    ACCESS_CONTROL_REQUEST_METHOD("Access-Control-Request-Method"),
    ACCESS_CONTROL_REQUEST_HEADERS("Access-Control-Request-Headers"),
    AUTHORIZATION("Authorization"),
    COOKIE("Cookie"),
    EXPECT("Expect"),
    FORWARDED("Forwarded"),
    FROM("From"),
    HOST("Host"),
    IF_MATCH("If-Match"),
    IF_MODIFIED_SINCE("If-Modified-Since"),
    IF_NONE_MATCH("If-None-Match"),
    IF_RANGE("If-Range"),
    IF_UNMODIFIED_SINCE("If-Unmodified-Since"),
    MAX_FORWARDS("Max-Forwards"),
    ORIGIN("Origin"),
    PROXY_AUTHORIZATION("Proxy-Authorization"),
    RANGE("Range"),
    REFERER("Referer"),
    TE("TE"),
    USER_AGENT("User-Agent");

    private final HeaderName name;
    
    RequestHeaders(final String value) {
        this.name = new Basic(value);
    }

    @Override
    public String value() {
        return this.name.value();
    }

    @Override
    public boolean matches(final Header header) {
        return this.name.matches(header);
    }

    @Override
    public boolean matches(final Map.Entry<String, String> header) {
        return this.name.matches(header);
    }

    @Override
    public boolean doesNotMatch(final Header header) {
        return this.name.doesNotMatch(header);
    }

    @Override
    public boolean doesNotMatch(final Map.Entry<String, String> header) {
        return this.name.doesNotMatch(header);
    }
}

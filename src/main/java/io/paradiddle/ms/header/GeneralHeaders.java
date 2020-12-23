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

public enum GeneralHeaders implements RequestHeaderName, ResponseHeaderName {
    ALLOW("Allow"),
    CACHE_CONTROL("Cache-Control"),
    CONNECTION("Connection"),
    DATE("Date"),
    PRAGMA("Pragma"),
    TRAILER("Trailer"),
    UPGRADE("Upgrade"),
    VIA("Via"),
    WARNING("Warning");

    private final HeaderName name;

    GeneralHeaders(final String value) {
        this.name = new Basic(value);
    }

    public static HeaderNameCollection all() {
        return new HeaderNameSet(GeneralHeaders.values());
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

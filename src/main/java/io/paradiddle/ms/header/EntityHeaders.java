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

public enum EntityHeaders implements HeaderName {
    CONTENT_DISPOSITION("Content-Disposition"),
    CONTENT_ENCODING("Content-Encoding"),
    CONTENT_LENGTH("Content-Length"),
    CONTENT_MD5("Content-MD5"),
    CONTENT_TYPE("Content-Type"),
    TRANSFER_ENCODING("Transfer-Encoding");

    private final HeaderName name;

    EntityHeaders(final String value) {
        this.name = new Basic(value);
    }

    public static HeaderNameCollection all() {
        return new HeaderNameSet(EntityHeaders.values());
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

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
package io.paradiddle.ms;

public enum StandardMimeTypes implements MimeType {
    MESSAGE_HTTP("message/http"),
    NONE("");

    private final String value;

    StandardMimeTypes(final String value) {
        this.value = value;
    }

    @Override
    public boolean matches(final String value) {
        return this.value.equals(value);
    }

    @Override
    public String asString() {
        return this.value;
    }
}

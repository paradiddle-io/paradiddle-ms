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

public interface HeaderName {
    String value();
    boolean matches(Header header);
    boolean matches(Map.Entry<String, String> header);
    boolean doesNotMatch(Header header);
    boolean doesNotMatch(Map.Entry<String, String> header);

    class Basic implements HeaderName {
        private final String _value;

        public Basic(final String value) {
            this._value = value;
        }

        @Override
        public String value() {
            return this._value;
        }

        @Override
        public boolean matches(final Header header) {
            return header.name().equalsIgnoreCase(this._value);
        }

        @Override
        public boolean matches(final Map.Entry<String, String> header) {
            return header.getKey().equalsIgnoreCase(this._value);
        }

        @Override
        public boolean doesNotMatch(final Header header) {
            return !this.matches(header);
        }

        @Override
        public boolean doesNotMatch(final Map.Entry<String, String> header) {
            return !this.matches(header);
        }
    }
}

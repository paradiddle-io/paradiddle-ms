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

import java.util.Map;
import java.util.Objects;

public interface Header {
    String name();
    String value();

    final class Generic implements Header {
        private final String name;
        private final String value;

        public Generic(final Map.Entry<String, String> entry) {
            this.name = entry.getKey();
            this.value = entry.getValue();
        }

        public Generic(final String name, final String value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String name() {
            return this.name;
        }

        @Override
        public String value() {
            return this.value;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            final Generic generic = (Generic) o;
            return name.equals(generic.name) && Objects.equals(value, generic.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, value);
        }
    }
}

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
package io.paradiddle.ms;

import io.paradiddle.ms.header.HeaderName;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.BiConsumer;

public interface HeaderStore extends Iterable<Header> {
    Optional<Header> fetch(HeaderName name);
    Optional<String> valueOf(HeaderName name);
    HeaderStore minus(HeaderName name);
    void writeAll(BiConsumer<String, String> target);

    final class Empty implements HeaderStore {
        @Override
        public Optional<Header> fetch(final HeaderName name) {
            return Optional.empty();
        }

        @Override
        public Optional<String> valueOf(final HeaderName name) {
            return Optional.empty();
        }

        @Override
        public HeaderStore minus(final HeaderName name) {
            return this;
        }

        @Override
        public void writeAll(final BiConsumer<String, String> target) {
            // intentionally empty
        }

        @Override
        public Iterator<Header> iterator() {
            return Collections.emptyIterator();
        }
    }
}

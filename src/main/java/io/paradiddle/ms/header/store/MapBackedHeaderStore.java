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
package io.paradiddle.ms.header.store;

import io.paradiddle.ms.Header;
import io.paradiddle.ms.HeaderStore;
import io.paradiddle.ms.header.HeaderName;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public final class MapBackedHeaderStore implements HeaderStore {
    private final Map<String, String> headers;

    public MapBackedHeaderStore(final Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Optional<Header> fetch(final HeaderName name) {
        final Optional<Header> header;
        if (this.headers.containsKey(name.value())) {
            header = Optional.of(
                new Header.Generic(name.value(), this.headers.get(name.value()))
            );
        } else {
            header = Optional.empty();
        }
        return header;
    }

    @Override
    public Optional<String> valueOf(final HeaderName name) {
        return this.fetch(name).map(Header::value);
    }

    @Override
    public HeaderStore minus(final HeaderName name) {
        final Map<String, String> backing = new LinkedHashMap<>();
        this.headers.entrySet()
            .stream()
            .filter(name::doesNotMatch)
            .forEachOrdered(entry -> backing.put(entry.getKey(), entry.getValue()));
        return new MapBackedHeaderStore(backing);
    }

    @Override
    public void writeAll(final BiConsumer<String, String> target) {
        this.headers.forEach(target);
    }

    @Override
    public Iterator<Header> iterator() {
        return this.headers.entrySet()
            .stream()
            .map(entry -> (Header) new Header.Generic(entry))
            .collect(Collectors.toUnmodifiableSet())
            .iterator();
    }
}

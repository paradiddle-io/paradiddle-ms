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
package io.paradiddle.ms.httpserver;

import com.sun.net.httpserver.Headers;
import io.paradiddle.ms.Header;
import io.paradiddle.ms.HeaderStore;
import io.paradiddle.ms.header.HeaderName;
import io.paradiddle.ms.util.DelegatedMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HeadersBackedHeaderStore extends DelegatedMap<String, List<String>> implements HeaderStore {
    public HeadersBackedHeaderStore(final Headers headers) {
        super(headers);
    }

    @Override
    public Optional<Header> fetch(final String name) {
        final Optional<Header> header;
        if (this.containsKey(name)) {
            header = Optional.of(
                this.convert(name, this.get(name))
            );
        } else {
            header = Optional.empty();
        }
        return header;
    }

    @Override
    public Optional<Header> fetch(final HeaderName name) {
        return this.fetch(name.name());
    }

    @Override
    public Optional<String> valueOf(final String name) {
        return this.fetch(name).map(Header::value);
    }

    @Override
    public Optional<String> valueOf(final HeaderName name) {
        return this.valueOf(name.name());
    }

    @Override
    public HeaderStore minus(final String name) {
        final Headers backing = new Headers();
        this.entrySet()
            .stream()
            .filter(entry -> entry.getKey().equalsIgnoreCase(name))
            .forEachOrdered(entry -> backing.put(entry.getKey(), entry.getValue()));
        return new HeadersBackedHeaderStore(backing);
    }

    @Override
    public HeaderStore minus(final HeaderName name) {
        return this.minus(name.name());
    }

    @Override
    public Iterator<Header> iterator() {
        return this.entrySet()
            .stream()
            .map(entry -> this.convert(entry.getKey(), entry.getValue()))
            .collect(Collectors.toUnmodifiableList())
            .iterator();
    }

    private Header convert(final String name, List<String> values) {
        return new Header.Generic(
            name,
            String.join(", ", values)
        );
    }
}

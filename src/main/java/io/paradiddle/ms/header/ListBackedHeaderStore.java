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
import io.paradiddle.ms.HeaderStore;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ListBackedHeaderStore implements HeaderStore {
    private final List<Header> headers;

    public ListBackedHeaderStore(final List<Header> headers) {
        this.headers = headers;
    }

    @Override
    public Optional<Header> fetch(final String name) {
        return this.headers
            .stream()
            .filter(header -> header.name().equalsIgnoreCase(name))
            .reduce(
                (header1, header2) -> new Header.Generic(
                    name,
                    String.join(
                        ", ",
                        header1.value(),
                        header2.value()
                    )
                )
            );
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
        return new ListBackedHeaderStore(
            this.headers
                .stream()
                .filter(header -> !header.name().equalsIgnoreCase(name))
                .collect(Collectors.toUnmodifiableList())
        );
    }

    @Override
    public HeaderStore minus(final HeaderName name) {
        return this.minus(name.name());
    }

    @Override
    public List<Header> asList() {
        return List.copyOf(this.headers);
    }
}

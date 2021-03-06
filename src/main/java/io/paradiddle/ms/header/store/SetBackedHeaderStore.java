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
package io.paradiddle.ms.header.store;

import io.paradiddle.ms.Header;
import io.paradiddle.ms.HeaderStore;
import io.paradiddle.ms.header.HeaderName;
import io.paradiddle.ms.header.HeaderNameCollection;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public final class SetBackedHeaderStore implements HeaderStore {
    private final Set<Header> headers;

    public SetBackedHeaderStore(final Set<Header> headers) {
        this.headers = headers;
    }

    @Override
    public Optional<Header> fetch(final HeaderName name) {
        return this.headers.stream()
            .filter(name::matches)
            .findFirst();
    }

    @Override
    public HeaderStore fetch(final HeaderNameCollection names) {
        return new SetBackedHeaderStore(
            this.headers.stream()
                .filter(names::containsMatch)
                .collect(Collectors.toUnmodifiableSet())
        );
    }

    @Override
    public Optional<String> valueOf(final HeaderName name) {
        return this.fetch(name).map(Header::value);
    }

    @Override
    public HeaderStore minus(final HeaderName name) {
        return new SetBackedHeaderStore(
            this.headers.stream()
                .filter(name::doesNotMatch)
                .collect(Collectors.toUnmodifiableSet())
        );
    }

    @Override
    public HeaderStore minus(final HeaderNameCollection names) {
        return new SetBackedHeaderStore(
            this.headers.stream()
                .filter(names::doesNotContainMatch)
                .collect(Collectors.toUnmodifiableSet())
        );
    }

    @Override
    public void consumeAll(final BiConsumer<String, String> target) {
        this.headers.forEach(header -> target.accept(header.name(), header.value()));
    }

    @Override
    public Iterator<Header> iterator() {
        return this.headers.iterator();
    }
}

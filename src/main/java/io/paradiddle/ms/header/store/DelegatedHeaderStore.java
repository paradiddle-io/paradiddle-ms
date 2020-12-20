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
import java.util.Optional;

public abstract class DelegatedHeaderStore implements HeaderStore {
    private final HeaderStore store;
    
    protected DelegatedHeaderStore(final HeaderStore store) {
        this.store = store;
    }

    @Override
    public Optional<Header> fetch(final String name) {
        return this.store.fetch(name);
    }

    @Override
    public Optional<Header> fetch(final HeaderName name) {
        return this.store.fetch(name);
    }

    @Override
    public Optional<String> valueOf(final String name) {
        return this.store.valueOf(name);
    }

    @Override
    public Optional<String> valueOf(final HeaderName name) {
        return this.store.valueOf(name);
    }

    @Override
    public HeaderStore minus(final String name) {
        return this.store.minus(name);
    }

    @Override
    public HeaderStore minus(final HeaderName name) {
        return this.store.minus(name);
    }

    @Override
    public Iterator<Header> iterator() {
        return this.store.iterator();
    }
}

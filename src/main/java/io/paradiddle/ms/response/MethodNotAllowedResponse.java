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

package io.paradiddle.ms.response;

import io.paradiddle.ms.HeaderStore;
import io.paradiddle.ms.header.AllowHeader;
import io.paradiddle.ms.RequestMethod;
import io.paradiddle.ms.Response;
import io.paradiddle.ms.header.store.SetBackedHeaderStore;
import java.io.InputStream;
import java.util.Set;
import java.util.function.Supplier;

public final class MethodNotAllowedResponse implements Response {
    private final Supplier<Set<RequestMethod>> allowed;

    public MethodNotAllowedResponse(final Supplier<Set<RequestMethod>> allowed) {
        this.allowed = allowed;
    }

    @Override
    public int statusCode() {
        return 405;
    }

    @Override
    public HeaderStore headers() {
        return new SetBackedHeaderStore(
            Set.of(new AllowHeader(this.allowed.get()))
        );
    }

    @Override
    public int contentLength() {
        return 0;
    }

    @Override
    public InputStream body() {
        return InputStream.nullInputStream();
    }
}

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

package io.paradiddle.ms.response;

import io.paradiddle.ms.HeaderStore;
import io.paradiddle.ms.Response;
import io.paradiddle.ms.entity.EntityConsumer;
import java.io.IOException;
import java.util.function.BiConsumer;

public final class NoContentResponse implements Response {
    private final HeaderStore store;

    public NoContentResponse() {
        this(new HeaderStore.Empty());
    }

    public NoContentResponse(final HeaderStore store) {
        this.store = store;
    }

    @Override
    public int statusCode() {
        return 204;
    }

    @Override
    public long contentLength() {
        return 0;
    }

    @Override
    public void consumeHeaders(final BiConsumer<String, String> target) {
        this.store.consumeAll(target);
    }

    @Override
    public void consumeEntity(
        final EntityConsumer consumer
    ) throws IOException {
        // Intentionally blank
    }
}

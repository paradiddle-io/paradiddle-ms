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

import io.paradiddle.ms.Response;
import io.paradiddle.ms.entity.EntityConsumer;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.BiConsumer;

public final class HeadResponse implements Response {
    final Response get;

    public HeadResponse(final Response get) {
        this.get = get;
    }

    @Override
    public int statusCode() {
        return this.get.statusCode();
    }

    @Override
    public long contentLength() {
        return this.get.contentLength();
    }

    @Override
    public void consumeHeaders(final BiConsumer<String, String> target) {
        this.get.consumeHeaders(target);
    }

    @Override
    public void consumeEntity(
        final EntityConsumer consumer
    ) throws IOException {
        this.get.consumeEntity(
            (stream, headers) -> consumer.consume(InputStream.nullInputStream(), headers)
        );
    }
}

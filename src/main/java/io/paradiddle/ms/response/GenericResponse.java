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
import io.paradiddle.ms.Response;
import io.paradiddle.ms.ResponseEntity;
import io.paradiddle.ms.entity.EntityConsumer;
import java.io.IOException;
import java.util.function.BiConsumer;

public final class GenericResponse implements Response {
    private final int _statusCode;
    private final HeaderStore headers;
    private final ResponseEntity entity;

    public GenericResponse(
        final int statusCode,
        final HeaderStore headers,
        final ResponseEntity entity
    ) {
        this._statusCode = statusCode;
        this.headers = headers;
        this.entity = entity;
    }

    @Override
    public int statusCode() {
        return this._statusCode;
    }

    @Override
    public long contentLength() {
        return this.entity.size();
    }

    @Override
    public void consumeHeaders(final BiConsumer<String, String> target) {
        this.headers.consumeAll(target);
        this.entity.consumeHeaders(target);
    }

    @Override
    public void consumeEntity(
        final EntityConsumer consumer
    ) throws IOException {
        this.entity.consume(consumer);
    }
}

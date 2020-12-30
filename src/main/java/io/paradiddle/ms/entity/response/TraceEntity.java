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
package io.paradiddle.ms.entity.response;

import io.paradiddle.ms.HeaderStore;
import io.paradiddle.ms.MimeType;
import io.paradiddle.ms.Request;
import io.paradiddle.ms.ResponseEntity;
import io.paradiddle.ms.StandardMimeTypes;
import io.paradiddle.ms.entity.EntityConsumer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class TraceEntity implements ResponseEntity {
    private final Request request;

    public TraceEntity(final Request request) {
        this.request = request;
    }

    @Override
    public long size() {
        return this.request.toString().length();
    }

    @Override
    public MimeType type() {
        return StandardMimeTypes.MESSAGE_HTTP;
    }

    @Override
    public void consume(final EntityConsumer consumer) throws IOException {
        consumer.consume(
            new ByteArrayInputStream(this.request.toString().getBytes(StandardCharsets.UTF_8)),
            new HeaderStore.Empty()
        );
    }
}

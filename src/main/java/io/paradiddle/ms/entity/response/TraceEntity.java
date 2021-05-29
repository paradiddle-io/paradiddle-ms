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
package io.paradiddle.ms.entity.response;

import io.paradiddle.ms.Header;
import io.paradiddle.ms.MimeType;
import io.paradiddle.ms.Request;
import io.paradiddle.ms.ResponseEntity;
import io.paradiddle.ms.StandardMimeTypes;
import io.paradiddle.ms.entity.EntityConsumer;
import io.paradiddle.ms.header.entity.ContentTypeHeader;
import io.paradiddle.ms.header.store.SetBackedHeaderStore;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.function.BiConsumer;

public final class TraceEntity implements ResponseEntity {
    private final Request request;
    private final Header contentType;

    public TraceEntity(final Request request) {
        this.request = request;
        this.contentType = new ContentTypeHeader(StandardMimeTypes.MESSAGE_HTTP);
    }

    @Override
    public long size() {
        return this.request.toString().getBytes(StandardCharsets.UTF_8).length;
    }

    @Override
    public MimeType type() {
        return StandardMimeTypes.MESSAGE_HTTP;
    }

    @Override
    public void consumeHeaders(final BiConsumer<String, String> consumer) {
        consumer.accept(this.contentType.name(), this.contentType.value());
    }

    @Override
    public void consume(final EntityConsumer consumer) throws IOException {
        consumer.consume(
            new ByteArrayInputStream(this.request.toString().getBytes(StandardCharsets.UTF_8)),
            new SetBackedHeaderStore(Set.of(this.contentType))
        );
    }
}

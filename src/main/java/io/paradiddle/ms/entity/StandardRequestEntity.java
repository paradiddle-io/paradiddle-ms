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
package io.paradiddle.ms.entity;

import io.paradiddle.ms.HeaderStore;
import io.paradiddle.ms.MimeType;
import io.paradiddle.ms.RequestEntity;
import io.paradiddle.ms.header.EntityHeaders;
import java.io.IOException;
import java.io.InputStream;

public final class StandardRequestEntity implements RequestEntity {
    private final HeaderStore headers;
    private final InputStream stream;

    public StandardRequestEntity(final HeaderStore headers, final InputStream stream) {
        this.headers = headers;
        this.stream = stream;
    }

    @Override
    public long size() {
        return Long.parseLong(
            this.headers
                .valueOf(EntityHeaders.CONTENT_LENGTH)
                .orElse("-1")
        );
    }

    @Override
    public MimeType type() {
        return null;
    }

    @Override
    public <T> T interpreted(
        final EntityInterpreter<T> interpreter
    ) throws IOException {
        try(this.stream) {
            return interpreter.interpret(this.stream, this.headers);
        }
    }
}

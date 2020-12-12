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

package io.paradiddle.ms.action;

import io.paradiddle.ms.Request;
import io.paradiddle.ms.Response;
import io.paradiddle.ms.response.GenericResponse;
import java.io.InputStream;
import java.util.function.Function;

public final class HeadAction implements Function<Request, Response> {
    private final Function<Request, Response> getAction;

    public HeadAction(final Function<Request, Response> getAction) {
        this.getAction = getAction;
    }

    @Override
    public Response apply(final Request request) {
        final var response = this.getAction.apply(request);
        return new GenericResponse(
            response.statusCode(),
            response.headers(),
            response.contentLength(),
            InputStream.nullInputStream()
        );
    }
}

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

import io.paradiddle.ms.header.HeaderNames;
import io.paradiddle.ms.response.GenericResponse;
import io.paradiddle.ms.Request;
import io.paradiddle.ms.Response;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class TraceAction implements Function<Request, Response> {
    @Override
    public Response apply(final Request request) {
        return new GenericResponse(
            200,
            request.headers()
                .stream()
                .filter(header -> !header.name().equalsIgnoreCase(HeaderNames.CONTENT_LENGTH))
                .collect(Collectors.toList()),
            request.headers()
                .stream()
                .filter(header -> header.name().equalsIgnoreCase(HeaderNames.CONTENT_LENGTH))
                .map(header -> Integer.valueOf(header.value()))
                .findFirst()
                .orElse(0),
            request.body()
        );
    }
}

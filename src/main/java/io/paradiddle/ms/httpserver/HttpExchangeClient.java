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

package io.paradiddle.ms.httpserver;

import com.sun.net.httpserver.HttpExchange;
import io.paradiddle.ms.Client;
import io.paradiddle.ms.Request;
import io.paradiddle.ms.Response;
import java.io.IOException;
import java.io.OutputStream;

final class HttpExchangeClient implements Client {
    private final HttpExchange exchange;

    public HttpExchangeClient(final HttpExchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public Request request() {
        return this.exchange.getAttribute(HttpExchangeAttributes.REQUEST) != null
            ? (Request) exchange.getAttribute(HttpExchangeAttributes.REQUEST)
            : new HttpExchangeRequest(exchange);
    }

    @Override
    public void respond(final Response response) throws IOException {
        response.consumeHeaders(this.exchange.getResponseHeaders()::add);
        this.exchange.sendResponseHeaders(
            response.statusCode(),
            response.contentLength() == 0
                ? -1
                : response.contentLength()
        );
        response.consumeEntity(
            (stream, headers) -> {
                if (response.contentLength() > -1) {
                    try (OutputStream body = this.exchange.getResponseBody()) {
                        final byte[] buffer = new byte[4096];
                        int count = 0;
                        do {
                            body.write(buffer, 0, count);
                            count = stream.read(buffer);
                        } while (count > 0);
                    }
                }
            }
        );
    }
}

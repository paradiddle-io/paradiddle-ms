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
import io.paradiddle.ms.Header;
import io.paradiddle.ms.Request;
import io.paradiddle.ms.RequestMethod;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class HttpExchangeRequest implements Request {
    private final Request request;

    public HttpExchangeRequest(final HttpExchange exchange) {
        this.request = exchange.getAttribute(HttpExchangeAttributes.REQUEST) != null
            ? (Request) exchange.getAttribute(HttpExchangeAttributes.REQUEST)
            : new Default(exchange);
    }

    @Override
    public RequestMethod method() {
        return request.method();
    }

    @Override
    public String path() {
        return request.path();
    }

    @Override
    public List<Header> headers() {
        return request.headers();
    }

    @Override
    public InputStream body() {
        return request.body();
    }

    private static final class Default implements Request {
        private final HttpExchange exchange;

        private Default(final HttpExchange exchange) {
            this.exchange = exchange;
        }

        @Override
        public RequestMethod method() {
            return RequestMethod.valueOf(this.exchange.getRequestMethod());
        }

        @Override
        public String path() {
            return this.exchange.getRequestURI().getPath();
        }

        @Override
        public List<Header> headers() {
            return Collections.unmodifiableList(
                this.exchange.getRequestHeaders()
                    .entrySet()
                    .stream()
                    .map(
                        entry -> new Header.Generic(
                            entry.getKey(),
                            String.join(",", entry.getValue())
                        )
                    )
                    .collect(Collectors.toList())
            );
        }

        @Override
        public InputStream body() {
            return this.exchange.getRequestBody();
        }
    }
}

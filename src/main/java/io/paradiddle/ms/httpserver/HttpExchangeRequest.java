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
import io.paradiddle.ms.HeaderStore;
import io.paradiddle.ms.Request;
import io.paradiddle.ms.RequestMethod;
import io.paradiddle.ms.header.MapBackedHeaderStore;
import java.io.InputStream;

public final class HttpExchangeRequest implements Request {
    private final HttpExchange exchange;

    public HttpExchangeRequest(final HttpExchange exchange) {
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
    public HeaderStore headers() {
        return new HeadersBackedHeaderStore(this.exchange.getRequestHeaders());
    }

    @Override
    public InputStream body() {
        return this.exchange.getRequestBody();
    }
}

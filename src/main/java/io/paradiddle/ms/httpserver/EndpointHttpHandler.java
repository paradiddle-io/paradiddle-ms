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
import com.sun.net.httpserver.HttpHandler;
import io.paradiddle.ms.Response;
import io.paradiddle.ms.Rule;
import io.paradiddle.ms.RuleViolation;
import io.paradiddle.ms.Endpoint;
import java.io.IOException;

public final class EndpointHttpHandler implements HttpHandler {
    private final Endpoint endpoint;
    private final Rule<Response> rule;

    public EndpointHttpHandler(
        final Endpoint endpoint,
        final Rule<Response> rule
    ) {
        this.endpoint = endpoint;
        this.rule = rule;
    }

    @Override
    public void handle(final HttpExchange exchange) throws IOException {
        Response response;
        try {
            response = this.rule.evaluate(this.endpoint.execute(new HttpExchangeRequest(exchange)));
        } catch (final RuleViolation violation) {
            response = violation.response();
        }
        new HttpExchangeResponseConsumer(exchange)
            .consume(response);
    }
}

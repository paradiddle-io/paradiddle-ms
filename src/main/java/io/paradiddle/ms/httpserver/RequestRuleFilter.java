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

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import io.paradiddle.ms.Client;
import io.paradiddle.ms.Request;
import io.paradiddle.ms.Rule;
import io.paradiddle.ms.RuleViolation;
import java.io.IOException;

public final class RequestRuleFilter extends Filter {
    private final Rule<Request> rule;

    public RequestRuleFilter(final Rule<Request> rule) {
        this.rule = rule;
    }

    @Override
    public void doFilter(
        final HttpExchange exchange,
        final Chain chain
    ) throws IOException {
        final Client client = new HttpExchangeClient(exchange);
        try {
            exchange.setAttribute(
                HttpExchangeAttributes.REQUEST,
                this.rule.evaluate(client.request())
            );
            chain.doFilter(exchange);
        } catch (final RuleViolation exception) {
            client.respond(exception.response());
        }
    }

    @Override
    public String description() {
        return "Runs a RequestRule as a Filter.";
    }
}

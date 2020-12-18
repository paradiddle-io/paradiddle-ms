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

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;
import io.paradiddle.ms.Endpoint;
import io.paradiddle.ms.Request;
import io.paradiddle.ms.Response;
import io.paradiddle.ms.Rule;
import io.paradiddle.ms.rule.CompositeRule;
import io.paradiddle.ms.Microservice;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class HttpServerMicroservice implements Microservice {
    private final HttpServer server;

    private HttpServerMicroservice(final HttpServer server) {
        this.server = server;
    }

    @Override
    public void start() throws IOException {
        this.start(
            new InetSocketAddress(
                "localhost",
                this.server instanceof HttpsServer ? 443 : 80
            )
        );
    }

    @Override
    public void start(final InetSocketAddress address) throws IOException {
        this.start(address, 0);
    }

    @Override
    public void start(final InetSocketAddress address, final int backlog) throws IOException {
        this.server.bind(address, backlog);
        this.server.start();
    }

    @Override
    public void stop() {
        this.server.stop(0);
    }

    public static class Builder {
        private final Map<String, Endpoint> endpoints;
        private final List<Rule<Request>> requestRules;
        private final List<Rule<Response>> responseRules;

        public Builder() {
            this.endpoints = new LinkedHashMap<>();
            this.requestRules = new LinkedList<>();
            this.responseRules = new LinkedList<>();
        }

        public Builder addEndpoint(final String path, final Endpoint endpoint) {
            this.endpoints.put(path, endpoint);
            return this;
        }

        public Builder addRequestRule(final Rule<Request> rule) {
            this.requestRules.add(rule);
            return this;
        }

        public Builder addResponseRule(final Rule<Response> rule) {
            this.responseRules.add(rule);
            return this;
        }

        public Microservice buildHttp() throws IOException {
            return this.build(HttpServer.create());
        }

        public Microservice buildHttps() throws IOException {
            return this.build(HttpsServer.create());
        }

        private Microservice build(final HttpServer server) {
            final var filter = new RequestRuleFilter(new CompositeRule<>(this.requestRules));
            final var rule = new CompositeRule<>(this.responseRules);
            for (final var entry : this.endpoints.entrySet()) {
                server.createContext(
                    entry.getKey(),
                    new EndpointHttpHandler(entry.getValue(), rule)
                ).getFilters().add(filter);
            }
            return new HttpServerMicroservice(server);
        }
    }
}

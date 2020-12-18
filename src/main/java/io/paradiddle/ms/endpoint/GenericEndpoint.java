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

package io.paradiddle.ms.endpoint;

import io.paradiddle.ms.Request;
import io.paradiddle.ms.RequestMethod;
import io.paradiddle.ms.Response;
import io.paradiddle.ms.action.CannedResponseAction;
import io.paradiddle.ms.action.HeadAction;
import io.paradiddle.ms.action.OptionsAction;
import io.paradiddle.ms.action.TraceAction;
import io.paradiddle.ms.Endpoint;
import io.paradiddle.ms.response.MethodNotAllowedResponse;
import io.paradiddle.ms.response.NoContentResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public final class GenericEndpoint implements Endpoint {
    private final Function<Request, Response> defaultAction;
    private final Map<RequestMethod, Function<Request, Response>> actions;

    private GenericEndpoint(
        final Function<Request, Response> defaultAction,
        final Map<RequestMethod, Function<Request, Response>> actions
    ) {
        this.defaultAction = defaultAction;
        this.actions = actions;
    }

    @Override
    public Response process(final Request request) {
        return this.actions.getOrDefault(request.method(), this.defaultAction).apply(request);
    }

    public static final class Builder implements Endpoint.Builder {
        private final Map<RequestMethod, Function<Request, Response>> actions;

        public Builder() {
            this.actions = new LinkedHashMap<>();
        }

        Endpoint.Builder addGetAction(final Function<Request, Response> action) {
            this.actions.put(RequestMethod.GET, action);
            return this;
        }

        Endpoint.Builder addPostAction(final Function<Request, Response> action) {
            this.actions.put(RequestMethod.POST, action);
            return this;
        }

        Endpoint.Builder addPutAction(final Function<Request, Response> action) {
            this.actions.put(RequestMethod.PUT, action);
            return this;
        }

        Endpoint.Builder addDeleteAction(final Function<Request, Response> action) {
            this.actions.put(RequestMethod.DELETE, action);
            return this;
        }

        Endpoint.Builder addPatchAction(final Function<Request, Response> action) {
            this.actions.put(RequestMethod.PATCH, action);
            return this;
        }

        @Override
        public Endpoint build() {
            return this.build(
                new CannedResponseAction(
                    () -> new MethodNotAllowedResponse(this.actions::keySet)
                )
            );
        }

        @Override
        public Endpoint build(final Function<Request, Response> defaultAction) {
            this.actions.put(
                RequestMethod.OPTIONS,
                new OptionsAction(this.actions::keySet)
            );
            this.actions.put(RequestMethod.TRACE, new TraceAction());
            if (!this.actions.containsKey(RequestMethod.GET)) {
                this.actions.put(
                    RequestMethod.GET,
                    new CannedResponseAction(NoContentResponse::new)
                );
            }
            this.actions.put(
                RequestMethod.HEAD,
                new HeadAction(this.actions.get(RequestMethod.GET))
            );
            return new GenericEndpoint(defaultAction, this.actions);
        }
    }
}

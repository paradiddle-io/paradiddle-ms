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

import io.paradiddle.ms.Action;
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
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

public final class GenericEndpoint implements Endpoint {
    private final Action defaultAction;
    private final Map<RequestMethod, Action> actions;

    private GenericEndpoint(
        final Action defaultAction,
        final Map<RequestMethod, Action> actions
    ) {
        this.defaultAction = defaultAction;
        this.actions = actions;
    }

    @Override
    public Response process(final Request request) throws IOException {
        return this.actions.getOrDefault(request.method(), this.defaultAction).act(request);
    }

    public static final class Builder {
        private final Map<RequestMethod, Action> actions;

        public Builder() {
            this.actions = new EnumMap<>(RequestMethod.class);
        }

        public Builder addGetAction(final Action action) {
            this.actions.put(RequestMethod.GET, action);
            return this;
        }

        public Builder addPostAction(final Action action) {
            this.actions.put(RequestMethod.POST, action);
            return this;
        }

        public Builder addPutAction(final Action action) {
            this.actions.put(RequestMethod.PUT, action);
            return this;
        }

        public Builder addDeleteAction(final Action action) {
            this.actions.put(RequestMethod.DELETE, action);
            return this;
        }

        public Builder addPatchAction(final Action action) {
            this.actions.put(RequestMethod.PATCH, action);
            return this;
        }

        public Endpoint build() {
            return this.build(
                new CannedResponseAction(
                    () -> new MethodNotAllowedResponse(this.actions::keySet)
                )
            );
        }

        public Endpoint build(final Action defaultAction) {
            this.actions.put(
                RequestMethod.OPTIONS,
                new OptionsAction(this.actions::keySet)
            );
            this.actions.put(RequestMethod.TRACE, new TraceAction());
            if (!this.actions.containsKey(RequestMethod.GET)) {
                this.actions.put(
                    RequestMethod.GET,
                    new CannedResponseAction(() -> new NoContentResponse())
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

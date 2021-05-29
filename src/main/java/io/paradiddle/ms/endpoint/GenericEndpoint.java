/*
 * Paradiddle MS - A lightweight microservices library with a comprehensible codebase.
 * Copyright (c) Michael Juliano 2020-2021.
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
import io.paradiddle.ms.Endpoint;
import io.paradiddle.ms.Request;
import io.paradiddle.ms.RequestMethod;
import io.paradiddle.ms.Response;
import io.paradiddle.ms.action.CannedResponseAction;
import io.paradiddle.ms.action.HeadAction;
import io.paradiddle.ms.action.OptionsAction;
import io.paradiddle.ms.action.TraceAction;
import io.paradiddle.ms.response.MethodNotAllowedResponse;
import io.paradiddle.ms.response.NoContentResponse;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

/**
 * An {@link Endpoint} implementation that is designed to be dynamically constructed with
 * {@link Action}s. This class is marked as final because it is meant to be encapsulated by your
 * {@link Endpoint} implementation rather than used as its base class. This gives you more
 * flexibility for your design by maintaining a clearer separation of concerns. This class is
 * concerned with routing {@link Request}s to their proper handlers so your class can be concerned
 * with providing the handling.
 */
public final class GenericEndpoint implements Endpoint {
    /**
     * The {@link Action} to take when no {@link Action} for a given {@link RequestMethod} is found.
     */
    private final Action defaultAction;

    /**
     * The {@link Action}s to take for various {@link RequestMethod}s.
     */
    private final Map<RequestMethod, Action> actions;

    /**
     * Constructs a {@link GenericEndpoint} with a default {@link Action} and a {@link Map} of
     * {@link Action}s keyed by {@link RequestMethod}.
     * @param defaultAction The {@link Action} to take when no {@link Action} for a given
     *                      {@link RequestMethod} is found.
     * @param actions The {@link Action}s to take for various {@link RequestMethod}s.
     */
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

    /**
     * Exposes a subset of {@link RequestMethod}s for configuration and provides accurate,
     * dynamically generated implementations of the rest.
     */
    public static final class Builder {
        /**
         * The {@link Action}s to take for various {@link RequestMethod}s.
         */
        private final Map<RequestMethod, Action> actions;

        /**
         * Constructor.
         */
        public Builder() {
            this.actions = new EnumMap<>(RequestMethod.class);
        }

        /**
         * Adds an {@link Action} for GET {@link Request}s.
         * @param action The {@link Action} to perform when a GET {@link Request} is received.
         * @return This {@link Builder} instance.
         */
        public Builder addGetAction(final Action action) {
            this.actions.put(RequestMethod.GET, action);
            return this;
        }

        /**
         * Adds an {@link Action} for POST {@link Request}s.
         * @param action The {@link Action} to perform when a POST {@link Request} is received.
         * @return This {@link Builder} instance.
         */
        public Builder addPostAction(final Action action) {
            this.actions.put(RequestMethod.POST, action);
            return this;
        }

        /**
         * Adds an {@link Action} for PUT {@link Request}s.
         * @param action The {@link Action} to perform when a PUT {@link Request} is received.
         * @return This {@link Builder} instance.
         */
        public Builder addPutAction(final Action action) {
            this.actions.put(RequestMethod.PUT, action);
            return this;
        }

        /**
         * Adds an {@link Action} for DELETE {@link Request}s.
         * @param action The {@link Action} to perform when a DELETE {@link Request} is received.
         * @return This {@link Builder} instance.
         */
        public Builder addDeleteAction(final Action action) {
            this.actions.put(RequestMethod.DELETE, action);
            return this;
        }

        /**
         * Adds an {@link Action} for PATCH {@link Request}s.
         * @param action The {@link Action} to perform when a PATCH {@link Request} is received.
         * @return This {@link Builder} instance.
         */
        public Builder addPatchAction(final Action action) {
            this.actions.put(RequestMethod.PATCH, action);
            return this;
        }

        /**
         * Builds a {@link GenericEndpoint} which returns a {@link MethodNotAllowedResponse} by
         * default for unspecified {@link RequestMethod}s.
         * @return The built {@link Endpoint}.
         */
        public Endpoint build() {
            return this.build(
                new CannedResponseAction(
                    () -> new MethodNotAllowedResponse(this.actions::keySet)
                )
            );
        }

        /**
         * Builds a {@link GenericEndpoint} which performs the specified {@link Action} for
         * unspecified {@link RequestMethod}s
         * @param defaultAction
         * @return
         */
        public Endpoint build(final Action defaultAction) {
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

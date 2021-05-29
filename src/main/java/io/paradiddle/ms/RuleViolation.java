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
package io.paradiddle.ms;

import io.paradiddle.ms.response.BadRequestResponse;

/**
 * Exception class that provides a {@link Response} that is used to reply to requesters.
 */
public class RuleViolation extends Exception {
    /**
     * The response to reply to requesters with.
     */
    private final Response _response;

    /**
     * Constructs a {@link RuleViolation} with a default {@link Response} of
     * {@link BadRequestResponse}.
     */
    public RuleViolation() {
        this(new BadRequestResponse());
    }

    /**
     * Constructs a {@link RuleViolation} with the supplied {@link Response}.
     * @param response The {@link Response}.
     */
    public RuleViolation(final Response response) {
        this._response = response;
    }

    /**
     * Returns the {@link Response} for this {@link RuleViolation}.
     * @return The {@link Response}.
     */
    public Response response() {
        return this._response;
    }
}

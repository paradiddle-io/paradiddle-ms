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

package io.paradiddle.ms.response;

import io.paradiddle.ms.Header;
import io.paradiddle.ms.header.AllowHeader;
import io.paradiddle.ms.RequestMethod;
import io.paradiddle.ms.Response;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

public final class OptionsResponse implements Response {
    private final Set<RequestMethod> allowed;

    public OptionsResponse(final Set<RequestMethod> allowed) {
        this.allowed = allowed;
    }

    @Override
    public int statusCode() {
        return 204;
    }

    @Override
    public List<Header> headers() {
        return List.of(new AllowHeader(this.allowed));
    }

    @Override
    public int contentLength() {
        return -1;
    }

    @Override
    public InputStream body() {
        return InputStream.nullInputStream();
    }
}

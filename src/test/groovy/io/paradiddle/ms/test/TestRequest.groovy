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

package io.paradiddle.ms.test

import io.paradiddle.ms.HeaderStore
import io.paradiddle.ms.Request
import io.paradiddle.ms.RequestEntity
import io.paradiddle.ms.RequestMethod
import io.paradiddle.ms.entity.EmptyEntity

class TestRequest implements Request {
    private final RequestMethod _method

    TestRequest(final RequestMethod method) {
        this._method = method
    }

    @Override
    RequestMethod method() {
        return this._method
    }

    @Override
    String path() {
        return '/'
    }

    @Override
    HeaderStore headers() {
        return new HeaderStore.Empty()
    }

    @Override
    RequestEntity entity() {
        return new EmptyEntity()
    }
}

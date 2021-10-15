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

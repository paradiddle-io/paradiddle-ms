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

package io.paradiddle.ms

import io.paradiddle.ms.endpoint.GenericEndpoint
import io.paradiddle.ms.response.NoContentResponse
import spock.lang.Specification

class GenericEndpointSpec extends Specification {
    def 'GET returns NoContentResponse by default'() {
        given: 'a GenericEndpoint built with default values'
        def endpoint = new GenericEndpoint.Builder().build()

        when: 'the endpoint is given a Request to process'
        def response = endpoint.process(new TestRequest(RequestMethod.GET))

        then: 'the Response is a NoContentResponse'
        response instanceof NoContentResponse
    }

    def 'HEAD returns a 204 by default'() {
        given: 'a GenericEndpoint built with default values'
        def endpoint = new GenericEndpoint.Builder().build()

        when: 'the endpoint is given a Request to process'
        def response = endpoint.process(new TestRequest(RequestMethod.HEAD))

        then: 'the Response is a NoContentResponse'
        response.statusCode() == 204
    }

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
        List<Header> headers() {
            return new ArrayList<Header>(0)
        }

        @Override
        InputStream body() {
            return InputStream.nullInputStream()
        }
    }
}

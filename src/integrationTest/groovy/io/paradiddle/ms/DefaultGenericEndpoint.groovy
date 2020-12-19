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

import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import io.paradiddle.ms.endpoint.GenericEndpoint
import io.paradiddle.ms.httpserver.HttpServerMicroservice
import spock.lang.Ignore
import spock.lang.Specification

class DefaultGenericEndpoint extends Specification {
    Microservice microservice
    RESTClient client

    def setup() {
        this.microservice = new HttpServerMicroservice.Builder()
            .addEndpoint(
                '/',
                new GenericEndpoint.Builder().build()
            )
            .buildHttp()
        this.microservice.start(new InetSocketAddress('localhost', 8080))
        this.client = new RESTClient('http://localhost:8080')
    }

    def cleanup() {
        this.microservice.stop()
    }

    def 'DELETE replies with 405'() {
        when: 'a plain DELETE is sent'
        def response
        try {
            this.client.delete(path: '/') as HttpResponseDecorator
        } catch (HttpResponseException exception) {
            response = exception.response
        }

        then: 'the response status to be 405'
        response?.status == 405
    }

    def 'GET replies with 204'() {
        when: 'a plain GET is sent'
        def response = this.client.get(path: '/') as HttpResponseDecorator

        then: 'the response status is 204'
        response.status == 204
    }

    def 'GET ignores request bodies'() {

    }

    def 'HEAD replies with 204'() {
        when: 'a plain HEAD is sent'
        def response = this.client.head(path: '/') as HttpResponseDecorator

        then: 'the response status to be 204'
        response.status == 204
    }

    def 'HEAD ignores request bodies'() {

    }

    def 'OPTIONS replies with GET, HEAD, OPTIONS, TRACE by default'() {
        when: 'a plain GET is sent'
        def response = this.client.options(path: '/') as HttpResponseDecorator

        then: 'the response status is 204'
        response.status == 204
        response.headers['Allow'].value == 'GET, HEAD, OPTIONS, TRACE'
    }

    def 'PATCH replies with 405'() {
        when: 'a plain PATCH is sent'
        def response
        try {
            this.client.patch(path: '/') as HttpResponseDecorator
        } catch (HttpResponseException exception) {
            response = exception.response
        }

        then: 'the response status to be 405'
        response?.status == 405
    }

    def 'POST replies with 405'() {
        when: 'a plain POST is sent'
        def response
        try {
            this.client.post(path: '/') as HttpResponseDecorator
        } catch (HttpResponseException exception) {
            response = exception.response
        }

        then: 'the response status to be 405'
        response?.status == 405
    }

    def 'PUT replies with 405'() {
        when: 'a plain PUT is sent'
        def response
        try {
            this.client.put(path: '/') as HttpResponseDecorator
        } catch (HttpResponseException exception) {
            response = exception.response
        }

        then: 'the response status to be 405'
        response?.status == 405
    }
}

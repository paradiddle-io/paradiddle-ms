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

package io.paradiddle.ms.httpserver

import com.sun.net.httpserver.HttpServer
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import spock.lang.Ignore
import spock.lang.Specification

@Ignore
class HttpServerSpec extends Specification {
    def 'HttpServer works at all'() {
        given: 'an HttpServer'
        def server = HttpServer.create(
            new InetSocketAddress(
                'localhost',
                8080
            ),
            0
        )

        and: 'a basic handler'
        server.createContext(
            '/',
            {
                it.sendResponseHeaders(204, -1)
            }
        )
        server.start()

        when: 'a request is made'
        def response = new RESTClient('http://localhost:8080')
            .get(path: '/') as HttpResponseDecorator

        then: 'the response should have the right stuff'
        response.status == 204
    }
}

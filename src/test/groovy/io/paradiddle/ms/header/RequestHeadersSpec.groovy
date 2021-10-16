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

package io.paradiddle.ms.header

import io.paradiddle.ms.Header
import spock.lang.Specification

class RequestHeadersSpec extends Specification {
    def 'should provide all its header names for manipulating header stores'() {
        expect: 'the collection to have 26 request headers'
        RequestHeaders.all()
            .stream()
            .filter({ it instanceof RequestHeaders })
            .count() == 26
    }

    def 'should match with Headers with the right name'() {
        expect: 'a generic header with the right name irrespective of case to match the standard header'
        requestHeader.matches(new Header.Generic(name, ''))
        requestHeader.matches(new Header.Generic(name.toUpperCase(), ''))
        requestHeader.matches(new Header.Generic(name.toLowerCase(), ''))

        and: 'a Map.Entry with the right key irrespective of case to match the standard header'
        requestHeader.matches(new AbstractMap.SimpleImmutableEntry<>(name, ''))
        requestHeader.matches(new AbstractMap.SimpleImmutableEntry<>(name.toUpperCase(), ''))
        requestHeader.matches(new AbstractMap.SimpleImmutableEntry<>(name.toLowerCase(), ''))

        and: 'the request header\'s value is equal to the name'
        requestHeader.value() == name

        where:
        name                             | requestHeader
        'A-IM'                           | RequestHeaders.A_IM
        'Accept'                         | RequestHeaders.ACCEPT
        'Accept-Charset'                 | RequestHeaders.ACCEPT_CHARSET
        'Accept-Datetime'                | RequestHeaders.ACCEPT_DATETIME
        'Accept-Encoding'                | RequestHeaders.ACCEPT_ENCODING
        'Accept-Language'                | RequestHeaders.ACCEPT_LANGUAGE
        'Access-Control-Request-Method'  | RequestHeaders.ACCESS_CONTROL_REQUEST_METHOD
        'Access-Control-Request-Headers' | RequestHeaders.ACCESS_CONTROL_REQUEST_HEADERS
        'Authorization'                  | RequestHeaders.AUTHORIZATION
        'Cookie'                         | RequestHeaders.COOKIE
        'Expect'                         | RequestHeaders.EXPECT
        'Forwarded'                      | RequestHeaders.FORWARDED
        'From'                           | RequestHeaders.FROM
        'Host'                           | RequestHeaders.HOST
        'If-Match'                       | RequestHeaders.IF_MATCH
        'If-Modified-Since'              | RequestHeaders.IF_MODIFIED_SINCE
        'If-None-Match'                  | RequestHeaders.IF_NONE_MATCH
        'If-Range'                       | RequestHeaders.IF_RANGE
        'If-Unmodified-Since'            | RequestHeaders.IF_UNMODIFIED_SINCE
        'Max-Forwards'                   | RequestHeaders.MAX_FORWARDS
        'Origin'                         | RequestHeaders.ORIGIN
        'Proxy-Authorization'            | RequestHeaders.PROXY_AUTHORIZATION
        'Range'                          | RequestHeaders.RANGE
        'Referer'                        | RequestHeaders.REFERER
        'TE'                             | RequestHeaders.TE
        'User-Agent'                     | RequestHeaders.USER_AGENT
    }

    def 'should only "not match" with the wrong text'() {
        expect: 'a generic header with the right name irrespective of case to not "not match" the standard header'
        !requestHeader.doesNotMatch(new Header.Generic(name, ''))
        !requestHeader.doesNotMatch(new Header.Generic(name.toUpperCase(), ''))
        !requestHeader.doesNotMatch(new Header.Generic(name.toLowerCase(), ''))

        and: 'a Map.Entry with the right key irrespective of case to not "not match" the standard header'
        !requestHeader.doesNotMatch(new AbstractMap.SimpleImmutableEntry<>(name, ''))
        !requestHeader.doesNotMatch(new AbstractMap.SimpleImmutableEntry<>(name.toUpperCase(), ''))
        !requestHeader.doesNotMatch(new AbstractMap.SimpleImmutableEntry<>(name.toLowerCase(), ''))

        and: 'a generic header with the wrong name to "not match" the standard header'
        requestHeader.doesNotMatch(new Header.Generic('wrong value', ''))

        and: 'a Map.Entry with the wrong key to "not match" the standard header'
        requestHeader.doesNotMatch(new AbstractMap.SimpleImmutableEntry<>('wrong value', ''))

        where:
        name                             | requestHeader
        'A-IM'                           | RequestHeaders.A_IM
        'Accept'                         | RequestHeaders.ACCEPT
        'Accept-Charset'                 | RequestHeaders.ACCEPT_CHARSET
        'Accept-Datetime'                | RequestHeaders.ACCEPT_DATETIME
        'Accept-Encoding'                | RequestHeaders.ACCEPT_ENCODING
        'Accept-Language'                | RequestHeaders.ACCEPT_LANGUAGE
        'Access-Control-Request-Method'  | RequestHeaders.ACCESS_CONTROL_REQUEST_METHOD
        'Access-Control-Request-Headers' | RequestHeaders.ACCESS_CONTROL_REQUEST_HEADERS
        'Authorization'                  | RequestHeaders.AUTHORIZATION
        'Cookie'                         | RequestHeaders.COOKIE
        'Expect'                         | RequestHeaders.EXPECT
        'Forwarded'                      | RequestHeaders.FORWARDED
        'From'                           | RequestHeaders.FROM
        'Host'                           | RequestHeaders.HOST
        'If-Match'                       | RequestHeaders.IF_MATCH
        'If-Modified-Since'              | RequestHeaders.IF_MODIFIED_SINCE
        'If-None-Match'                  | RequestHeaders.IF_NONE_MATCH
        'If-Range'                       | RequestHeaders.IF_RANGE
        'If-Unmodified-Since'            | RequestHeaders.IF_UNMODIFIED_SINCE
        'Max-Forwards'                   | RequestHeaders.MAX_FORWARDS
        'Origin'                         | RequestHeaders.ORIGIN
        'Proxy-Authorization'            | RequestHeaders.PROXY_AUTHORIZATION
        'Range'                          | RequestHeaders.RANGE
        'Referer'                        | RequestHeaders.REFERER
        'TE'                             | RequestHeaders.TE
        'User-Agent'                     | RequestHeaders.USER_AGENT
    }
}

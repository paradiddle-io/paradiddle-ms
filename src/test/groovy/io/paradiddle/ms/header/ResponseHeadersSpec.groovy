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

class ResponseHeadersSpec extends Specification {
    def 'should match with Headers with the right name'() {
        expect: 'a generic header with the right name irrespective of case to match the standard header'
        responseHeader.matches(new Header.Generic(name, ''))
        responseHeader.matches(new Header.Generic(name.toUpperCase(), ''))
        responseHeader.matches(new Header.Generic(name.toLowerCase(), ''))

        and: 'a Map.Entry with the right key irrespective of case to match the standard header'
        responseHeader.matches(new AbstractMap.SimpleImmutableEntry<>(name, ''))
        responseHeader.matches(new AbstractMap.SimpleImmutableEntry<>(name.toUpperCase(), ''))
        responseHeader.matches(new AbstractMap.SimpleImmutableEntry<>(name.toLowerCase(), ''))

        where:
        name                               | responseHeader
        'Access-Control-Allow-Origin'      | ResponseHeaders.ACCESS_CONTROL_ALLOW_ORIGIN
        'Access-Control-Allow-Credentials' | ResponseHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS
        'Access-Control-Expose-Headers'    | ResponseHeaders.ACCESS_CONTROL_EXPOSE_HEADERS
        'Access-Control-Max-Age'           | ResponseHeaders.ACCESS_CONTROL_MAX_AGE
        'Access-Control-Allow-Methods'     | ResponseHeaders.ACCESS_CONTROL_ALLOW_METHODS
        'Access-Control-Allow-Headers'     | ResponseHeaders.ACCESS_CONTROL_ALLOW_HEADERS
        'Accept-Patch'                     | ResponseHeaders.ACCEPT_PATCH
        'Accept-Post'                      | ResponseHeaders.ACCEPT_POST
        'Accept-Ranges'                    | ResponseHeaders.ACCEPT_RANGES
        'Age'                              | ResponseHeaders.AGE
        'Alt-Svc'                          | ResponseHeaders.ALT_SVC
        'Delta-Base'                       | ResponseHeaders.DELTA_BASE
        'ETag'                             | ResponseHeaders.ETAG
        'Expires'                          | ResponseHeaders.EXPIRES
        'IM'                               | ResponseHeaders.IM
        'Last-Modified'                    | ResponseHeaders.LAST_MODIFIED
        'Link'                             | ResponseHeaders.LINK
        'Location'                         | ResponseHeaders.LOCATION
        'P3P'                              | ResponseHeaders.P3P
        'Pragma'                           | ResponseHeaders.PRAGMA
        'Proxy-Authenticate'               | ResponseHeaders.PROXY_AUTHENTICATE
        'Public-Key-Pins'                  | ResponseHeaders.PUBLIC_KEY_PINS
        'Retry-After'                      | ResponseHeaders.RETRY_AFTER
        'Server'                           | ResponseHeaders.SERVER
        'Set-Cookie'                       | ResponseHeaders.SET_COOKIE
        'Strict-Transport-Security'        | ResponseHeaders.STRICT_TRANSPORT_SECURITY
        'Tk'                               | ResponseHeaders.TK
        'Vary'                             | ResponseHeaders.VARY
        'WWW-Authenticate'                 | ResponseHeaders.WWW_AUTHENTICATE
    }

    def 'should only "not match" with the wrong text'() {
        expect: 'a generic header with the right name irrespective of case to not "not match" the standard header'
        !responseHeader.doesNotMatch(new Header.Generic(name, ''))
        !responseHeader.doesNotMatch(new Header.Generic(name.toUpperCase(), ''))
        !responseHeader.doesNotMatch(new Header.Generic(name.toLowerCase(), ''))

        and: 'a Map.Entry with the right key irrespective of case to not "not match" the standard header'
        !responseHeader.doesNotMatch(new AbstractMap.SimpleImmutableEntry<>(name, ''))
        !responseHeader.doesNotMatch(new AbstractMap.SimpleImmutableEntry<>(name.toUpperCase(), ''))
        !responseHeader.doesNotMatch(new AbstractMap.SimpleImmutableEntry<>(name.toLowerCase(), ''))

        and: 'a generic header with the wrong name to "not match" the standard header'
        responseHeader.doesNotMatch(new Header.Generic('wrong value', ''))

        and: 'a Map.Entry with the wrong key to "not match" the standard header'
        responseHeader.doesNotMatch(new AbstractMap.SimpleImmutableEntry<>('wrong value', ''))

        where:
        name                               | responseHeader
        'Access-Control-Allow-Origin'      | ResponseHeaders.ACCESS_CONTROL_ALLOW_ORIGIN
        'Access-Control-Allow-Credentials' | ResponseHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS
        'Access-Control-Expose-Headers'    | ResponseHeaders.ACCESS_CONTROL_EXPOSE_HEADERS
        'Access-Control-Max-Age'           | ResponseHeaders.ACCESS_CONTROL_MAX_AGE
        'Access-Control-Allow-Methods'     | ResponseHeaders.ACCESS_CONTROL_ALLOW_METHODS
        'Access-Control-Allow-Headers'     | ResponseHeaders.ACCESS_CONTROL_ALLOW_HEADERS
        'Accept-Patch'                     | ResponseHeaders.ACCEPT_PATCH
        'Accept-Post'                      | ResponseHeaders.ACCEPT_POST
        'Accept-Ranges'                    | ResponseHeaders.ACCEPT_RANGES
        'Age'                              | ResponseHeaders.AGE
        'Alt-Svc'                          | ResponseHeaders.ALT_SVC
        'Delta-Base'                       | ResponseHeaders.DELTA_BASE
        'ETag'                             | ResponseHeaders.ETAG
        'Expires'                          | ResponseHeaders.EXPIRES
        'IM'                               | ResponseHeaders.IM
        'Last-Modified'                    | ResponseHeaders.LAST_MODIFIED
        'Link'                             | ResponseHeaders.LINK
        'Location'                         | ResponseHeaders.LOCATION
        'P3P'                              | ResponseHeaders.P3P
        'Pragma'                           | ResponseHeaders.PRAGMA
        'Proxy-Authenticate'               | ResponseHeaders.PROXY_AUTHENTICATE
        'Public-Key-Pins'                  | ResponseHeaders.PUBLIC_KEY_PINS
        'Retry-After'                      | ResponseHeaders.RETRY_AFTER
        'Server'                           | ResponseHeaders.SERVER
        'Set-Cookie'                       | ResponseHeaders.SET_COOKIE
        'Strict-Transport-Security'        | ResponseHeaders.STRICT_TRANSPORT_SECURITY
        'Tk'                               | ResponseHeaders.TK
        'Vary'                             | ResponseHeaders.VARY
        'WWW-Authenticate'                 | ResponseHeaders.WWW_AUTHENTICATE
    }
}

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

package io.paradiddle.ms

import spock.lang.Specification

class StandardMimeTypesSpec extends Specification {
    def 'should match with the right text'() {
        expect: 'the MIME type to match the text'
        mimeType.matches(text)

        and: 'the string representation to match the text'
        mimeType.asString() == text

        where:
        text           | mimeType
        ''             | StandardMimeTypes.NONE
        'text/plain'   | StandardMimeTypes.TEXT_PLAIN
        'message/http' | StandardMimeTypes.MESSAGE_HTTP
    }
}

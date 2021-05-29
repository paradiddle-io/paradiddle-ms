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

import io.paradiddle.ms.response.BadRequestResponse
import spock.lang.Specification

class RuleViolationSpec extends Specification {
    def 'provides the supplied response'() {
        given: 'a Response'
        def response = new BadRequestResponse()

        when: 'a RuleViolation is constructed with the response'
        def violation = new RuleViolation(response)

        then: 'the RuleViolation provides the expected response'
        violation.response().is(response)
    }

    def 'provides BadRequestResponse by default'() {
        given: 'a RuleViolation'
        def violation = new RuleViolation()

        expect: 'the RuleViolation provides the expected response'
        violation.response() instanceof BadRequestResponse
    }
}

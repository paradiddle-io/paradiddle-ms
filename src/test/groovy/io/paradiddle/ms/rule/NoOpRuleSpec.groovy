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
package io.paradiddle.ms.rule

import spock.lang.Specification

class NoOpRuleSpec extends Specification {
    def 'outputs are identical to inputs'() {
        given: 'a NoOpRule'
        def rule = new NoOpRule()

        and: 'a sample input'
        def input = 42

        when: 'the input is evaluated against the rule'
        def output = rule.evaluate(input)

        then: 'the output is identicial to the input'
        output.is(input)
    }
}

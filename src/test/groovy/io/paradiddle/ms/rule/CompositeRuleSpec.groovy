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

package io.paradiddle.ms.rule

import spock.lang.Specification

class CompositeRuleSpec extends Specification {
    def 'an empty iterable does nothing'() {
        given: 'a CompositeRule with an empty iterable of Rules'
        def rule = new CompositeRule(List.of())

        and: 'a test object'
        def test = 'test'

        when: 'the rule is evaluated on an object'
        def result = rule.evaluate(test)

        then: 'the resulting object has not changed'
        result.is(test)
    }
}

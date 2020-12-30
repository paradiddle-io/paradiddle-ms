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

import io.paradiddle.ms.Rule
import io.paradiddle.ms.RuleViolation
import spock.lang.Specification

class CompositeRuleSpec extends Specification {
    def 'no rules does nothing'() {
        given: 'a CompositeRule with no Rules'
        def rule = new CompositeRule(List.of())

        and: 'a test object'
        def test = 'test'

        when: 'the rule is evaluated on the object'
        def result = rule.evaluate(test)

        then: 'the resulting object has not changed'
        result.is(test)
    }

    def 'one rule is applied'() {
        given: 'a CompositRule with one rule'
        def mutated = 'rule applied'
        def rule = new CompositeRule(List.of({ mutated } as Rule<String>))

        and: 'a test object'
        def test = 'test'

        when: 'the rule is evaluated on the object'
        def result = rule.evaluate(test)

        then: 'the resulting object is the one from the rule'
        result.is(mutated)

        and: 'the original object has not changed'
        test == 'test'
    }

    def 'two rules are applied'() {
        given: 'a CompositeRule with two rules'
        def rule = new CompositeRule(
            List.of(
                { it + 2 } as Rule<Integer>,
                { it + 3 } as Rule<Integer>
            )
        )

        and: 'a test object'
        def test = 0

        when: 'the rules are evaluated on the object'
        def result = rule.evaluate(test)

        then: 'the resulting object has been modified by the rules'
        result == 5

        and: 'the original object has not changed'
        test == 0
    }

    def 'a rule that throws an exception halts rule evaluation'() {
        given: 'a CompositRule with three rules'
        def rule = new CompositeRule(
            List.of(
                { it + 2 } as Rule<Integer>,
                { throw new RuleViolation() } as Rule<Integer>,
                { it + 3 } as Rule<Integer>
            )
        )

        and: 'a test object'
        def test = 0

        when: 'the rules are evaluated on the object'
        rule.evaluate(test)

        then: 'an exception is thrown'
        thrown(RuleViolation)

        and: 'the original object has not changed'
        test == 0
    }
}

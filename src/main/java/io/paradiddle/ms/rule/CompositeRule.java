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

package io.paradiddle.ms.rule;

import io.paradiddle.ms.Rule;
import io.paradiddle.ms.RuleViolation;

public final class CompositeRule<T> implements Rule<T> {
    private final Iterable<Rule<T>> rules;

    public CompositeRule(final Iterable<Rule<T>> rules) {
        this.rules = rules;
    }

    @Override
    public T evaluate(T target) throws RuleViolation {
        for (final var rule : this.rules) {
            target = rule.evaluate(target);
        }
        return target;
    }
}

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
package io.paradiddle.ms.rule;

import io.paradiddle.ms.Rule;
import io.paradiddle.ms.RuleViolation;
import java.util.List;
import java.util.Set;

/**
 * Evaluates inputs against an {@link Iterable} of {@link Rule}s.
 * @param <T> The type of the inputs.
 */
public final class CompositeRule<T> implements Rule<T> {
    /**
     * The {@link Rule}s.
     */
    private final Iterable<Rule<T>> rules;

    /**
     * Constructs a {@link CompositeRule} from an {@link Iterable} of {@link Rule}s.
     * The recommended approach is to construct your argument with
     * {@link List#of(Object[])} if order is important or
     * {@link Set#of(Object[])} if it is not.
     * @param rules The {@link Rule}s.
     */
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

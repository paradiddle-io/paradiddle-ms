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
package io.paradiddle.ms;

/**
 * A {@link Rule} validates an input value optionally transforming it.
 * @param <T> The type of the input value.
 */
@FunctionalInterface
public interface Rule<T> {
    /**
     * Evaluates the input against the rule.
     * @param request The input
     * @return The optionally transformed input
     * @throws RuleViolation If the input did not satisfy this {@link Rule}
     */
    T evaluate(T request) throws RuleViolation;
}

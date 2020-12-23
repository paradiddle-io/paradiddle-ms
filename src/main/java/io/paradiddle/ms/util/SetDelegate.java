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
package io.paradiddle.ms.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;

public abstract class SetDelegate<T> implements Set<T> {
    private final Set<T> set;

    protected SetDelegate(final Set<T> set) {
        this.set = set;
    }

    @Override
    public int size() {
        return this.set.size();
    }

    @Override
    public boolean isEmpty() {
        return this.set.isEmpty();
    }

    @Override
    public boolean contains(final Object o) {
        return this.set.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return this.set.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.set.toArray();
    }

    @Override
    public <T1> T1[] toArray(final T1[] a) {
        return this.set.toArray(a);
    }

    @Override
    public boolean add(final T t) {
        return this.set.add(t);
    }

    @Override
    public boolean remove(final Object o) {
        return this.set.remove(o);
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        return this.set.containsAll(c);
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {
        return this.set.addAll(c);
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        return this.set.retainAll(c);
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        return this.set.removeAll(c);
    }

    @Override
    public void clear() {
        this.set.clear();
    }

    @Override
    public boolean equals(final Object o) {
        return this.set.equals(o);
    }

    @Override
    public int hashCode() {
        return this.set.hashCode();
    }

    @Override
    public Spliterator<T> spliterator() {
        return this.set.spliterator();
    }
}

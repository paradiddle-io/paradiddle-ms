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
package io.paradiddle.ms.header.store

import io.paradiddle.ms.Header
import io.paradiddle.ms.header.HeaderName
import io.paradiddle.ms.header.HeaderNameSet
import spock.lang.Specification

class SetBackedHeaderStoreSpec extends Specification {
    def 'should fetch the requested Header when present'() {
        given: 'a store with a header'
        def header = Set.of(new Header.Generic('test', 'test'))
        def store = new SetBackedHeaderStore(header)

        when: 'the store is asked for the header'
        def result = store.fetch(new HeaderName.Basic('test'))

        then: 'the store returns the header'
        header.contains(result.get())
    }

    def 'should not fetch Headers that are not present'() {
        given: 'a store with no headers'
        def store = new SetBackedHeaderStore(Set.of())

        when: 'the store is asked for a header'
        def result = store.fetch(new HeaderName.Basic('test'))

        then: 'the store returns an empty result'
        !result.isPresent()
    }

    def 'should fetch the list of requested Headers when present'() {
        given: 'a store with a couple headers'
        def headers = Set.of(
            new Header.Generic('test1', 'test'),
            new Header.Generic('test2', 'test')
        )
        def store = new SetBackedHeaderStore(headers)

        when: 'the store is asked for the headers'
        def result = store.fetch(
            new HeaderNameSet(
                headers.stream()
                    .map({ new HeaderName.Basic(it.name()) })
                    .toArray(HeaderName[]::new)
            )
        )

        then: 'the store returns the headers'
        result.fetch(new HeaderName.Basic(headers[0].name())).get().value() == headers[0].value()
        result.fetch(new HeaderName.Basic(headers[1].name())).get().value() == headers[1].value()
    }

    def 'should not fetch a list of Headers that are not present'() {
        given: 'a store with no headers'
        def store = new SetBackedHeaderStore(Set.of())

        when: 'the store is asked for the headers'
        def result = store.fetch(
            new HeaderNameSet(
                new HeaderName.Basic('test1'),
                new HeaderName.Basic('test2')
            )
        )

        then: 'the store returns an empty result'
        result.size() == 0
    }

    def 'should provide the a list of Headers minus the provided one'() {
        given: 'a store with some headers'
        def headers = Set.of(
            new Header.Generic('test1', 'test'),
            new Header.Generic('test2', 'test')
        )

        def store = new SetBackedHeaderStore(headers)

        when: 'the store is asked for to remove a header'
        def result = store.minus(new HeaderName.Basic('test1'))

        then: 'the resulting list should have all but the removed header'
        !result.fetch(new HeaderName.Basic('test1')).isPresent()
        result.fetch(new HeaderName.Basic('test2')).isPresent()
    }

    def 'should provide the a list of Headers minus the provided ones'() {
        given: 'a store with some headers'
        def headers = Set.of(
            new Header.Generic('test1', 'test'),
            new Header.Generic('test2', 'test'),
            new Header.Generic('test3', 'test')
        )
        def store = new SetBackedHeaderStore(headers)

        when: 'the store is asked for to remove some headers'
        def namesToRemove = new HeaderNameSet(
            new HeaderName.Basic('test1'),
            new HeaderName.Basic('test2')
        )
        def result = store.minus(namesToRemove)

        then: 'the resulting list should have all but the removed header'
        !result.fetch(new HeaderName.Basic('test1')).isPresent()
        !result.fetch(new HeaderName.Basic('test2')).isPresent()
        result.fetch(new HeaderName.Basic('test3')).isPresent()
    }

    def 'should provide the correct value'() {
        given: 'a store with some headers'
        def headers = Set.of(
            new Header.Generic('test1', 'thing1'),
            new Header.Generic('test2', 'thing2')
        )
        def store = new SetBackedHeaderStore(headers)

        when: 'the store is asked for a value'
        def result = store.valueOf(new HeaderName.Basic('test2'))

        then: 'the returned value matches that of the header'
        result.get() == 'thing2'
    }

    def 'should be able to iterate the headers'() {
        given: 'a store with some headers'
        def headers = Set.of(
            new Header.Generic('test1', 'test'),
            new Header.Generic('test2', 'test')
        )
        def store = new SetBackedHeaderStore(headers)

        expect: 'iterating through the store yields the correct result'
        store.size() == headers.size()
        for (header in store) {
            headers.contains(header)
        }
    }

    def 'should consume all headers'() {
        given: 'a store with some headers'
        def headers = Set.of(
            new Header.Generic('test1', 'test'),
            new Header.Generic('test2', 'test')
        )
        def store = new SetBackedHeaderStore(headers)

        when: 'the headers are consumed'
        Set<Header> result = new HashSet<>(headers.size())
        store.consumeAll({
            name, value ->
            result.add(new Header.Generic(name, value))
        })

        then: 'all the headers are iterated'
        headers.containsAll(result)
        result.containsAll(headers)
    }
}

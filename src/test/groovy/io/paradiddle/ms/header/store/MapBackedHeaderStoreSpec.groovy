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

import io.paradiddle.ms.header.HeaderName
import io.paradiddle.ms.header.HeaderNameSet
import spock.lang.Specification

class MapBackedHeaderStoreSpec extends Specification {
    def 'should fetch the requested Header when present'() {
        given: 'a store with a header'
        def header = ['test': 'test']
        def store = new MapBackedHeaderStore(header)

        when: 'the store is asked for the header'
        def result = store.fetch(new HeaderName.Basic('test'))

        then: 'the store returns the header'
        header.containsKey(result.get().name())
    }

    def 'should not fetch Headers that are not present'() {
        given: 'a store with no headers'
        def store = new MapBackedHeaderStore([:])

        when: 'the store is asked for a header'
        def result = store.fetch(new HeaderName.Basic('test'))

        then: 'the store returns an empty result'
        !result.isPresent()
    }

    def 'should fetch the list of requested Headers when present'() {
        given: 'a store with a couple headers'
        def headers = ['test1': 'test', 'test2': 'test']
        def store = new MapBackedHeaderStore(headers)

        when: 'the store is asked for the headers'
        def result = store.fetch(
            new HeaderNameSet(
                headers.keySet().stream()
                    .map(HeaderName.Basic::new)
                    .toArray(HeaderName[]::new)
            )
        )

        then: 'the store returns the headers'
        result.fetch(new HeaderName.Basic(headers.keySet()[0])).get().value() == headers.test1
        result.fetch(new HeaderName.Basic(headers.keySet()[1])).get().value() == headers.test2
    }

    def 'should not fetch a list of Headers that are not present'() {
        given: 'a store with no headers'
        def store = new MapBackedHeaderStore([:])

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
}

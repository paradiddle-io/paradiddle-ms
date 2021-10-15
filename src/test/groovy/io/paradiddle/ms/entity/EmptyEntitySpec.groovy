package io.paradiddle.ms.entity

import io.paradiddle.ms.HeaderStore
import io.paradiddle.ms.StandardMimeTypes
import java.util.function.BiConsumer
import spock.lang.Specification

class EmptyEntitySpec extends Specification {
    def 'should provide metadata indicating an empty entity'() {
        given: 'an EmptyEntity'
        def entity = new EmptyEntity()

        expect: 'the entity to have a size of 0'
        entity.size() == 0

        and: 'the MimeType should be NONE'
        entity.type() == StandardMimeTypes.NONE
    }

    def 'should have no content when consumed'() {
        given: 'an EmptyEntity'
        def entity = new EmptyEntity()

        when: 'the entity is consumed'
        def actualSize = 1
        entity.consume(new EntityConsumer() {
            @Override
            void consume(final InputStream stream, final HeaderStore headers) throws IOException {
                actualSize = stream.readAllBytes().size()
            }
        })

        then: 'the content should have 0 bytes'
        actualSize == 0
    }

    def 'should have no content when interpreted'() {
        given: 'an EmptyEntity'
        def entity = new EmptyEntity()

        when: 'the entity is interpreted'
        byte[] result = entity.interpreted(new EntityDecoder<Object>() {
            @Override
            Object interpret(final InputStream stream, final HeaderStore store) throws IOException {
                return stream.readAllBytes()
            }
        })

        then: 'the content should have 0 bytes'
        result.size() == 0
    }

    def 'should have no headers'() {
        given: 'an EmptyEntity'
        def entity = new EmptyEntity()

        when: 'the headers are consumed'
        def results = [:]
        entity.consumeHeaders(new BiConsumer<String, String>() {
            @Override
            void accept(final String name, final String value) {
                results.put(name, value)
            }
        })

        then: 'the results should be empty'
        results.size() == 0
    }
}

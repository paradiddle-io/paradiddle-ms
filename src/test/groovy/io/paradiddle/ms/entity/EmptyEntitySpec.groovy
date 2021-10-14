package io.paradiddle.ms.entity

import io.paradiddle.ms.HeaderStore
import io.paradiddle.ms.StandardMimeTypes
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

    def 'should have no content'() {
        given: 'an EmptyEntity'
        def entity = new EmptyEntity()

        when: 'the entity is consumed'
        def actualSize = 1
        def headerCount = 1
        entity.consume(new EntityConsumer() {
            @Override
            void consume(final InputStream stream, final HeaderStore headers) throws IOException {
                actualSize = stream.readAllBytes().size()
                headerCount = headers.size()
            }
        })

        then: 'the content should have 0 bytes'
        actualSize == 0

        and: 'there should be no headers'
        headerCount == 0
    }
}

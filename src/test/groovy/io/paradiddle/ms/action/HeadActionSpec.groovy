package io.paradiddle.ms.action

import io.paradiddle.ms.HeaderStore
import io.paradiddle.ms.MimeType
import io.paradiddle.ms.RequestMethod
import io.paradiddle.ms.ResponseEntity
import io.paradiddle.ms.StandardMimeTypes
import io.paradiddle.ms.entity.EntityConsumer
import io.paradiddle.ms.response.GenericResponse
import io.paradiddle.ms.test.TestRequest
import java.util.function.BiConsumer
import spock.lang.Specification

class HeadActionSpec extends Specification {
    def 'should swallow entities'() {
        given: 'a GET action that returns an entity'
        def entity = "test".bytes
        def headers = new HeaderStore.Empty()
        def getAction = {
            return new GenericResponse(
                200,
                headers,
                new ResponseEntity() {
                    private final InputStream entityStream = new ByteArrayInputStream(entity);

                    @Override
                    void consumeHeaders(final BiConsumer<String, String> consumer) {
                        headers.consumeAll(consumer)
                    }

                    @Override
                    void consume(final EntityConsumer consumer) throws IOException {
                        consumer.consume(this.entityStream, headers)
                    }

                    @Override
                    long size() {
                        return entity.size()
                    }

                    @Override
                    MimeType type() {
                        return StandardMimeTypes.TEXT_PLAIN
                    }
                }
            )
        }

        when: 'the GET action is wrapped in a HeadAction'
        def headAction = new HeadAction(getAction)

        and: 'a HEAD request is given to the HeadAction'
        def response = headAction.act(new TestRequest(RequestMethod.HEAD))

        and: 'the response is consumed'
        byte[] responseEntity = new byte[1]
        def responseHeaders = new HeaderStore.Empty()
        response.consumeEntity(new EntityConsumer() {
            @Override
            void consume(final InputStream stream, final HeaderStore store) throws IOException {
                responseEntity = stream.readAllBytes()
                responseHeaders = store
            }
        })

        then: 'the response contains no entity'
        responseEntity.size() == 0

        and: 'has the correct Content-Length'
        response.contentLength() == entity.size()
    }
}

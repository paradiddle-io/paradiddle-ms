package io.paradiddle.ms.httpserver;

import com.sun.net.httpserver.HttpExchange;
import io.paradiddle.ms.Client;
import io.paradiddle.ms.Header;
import io.paradiddle.ms.Request;
import io.paradiddle.ms.Response;
import java.io.IOException;
import java.io.OutputStream;

class HttpExchangeClient implements Client {
    private final HttpExchange exchange;

    public HttpExchangeClient(final HttpExchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public Request request() {
        return this.exchange.getAttribute(HttpExchangeAttributes.REQUEST) != null
            ? (Request) exchange.getAttribute(HttpExchangeAttributes.REQUEST)
            : new HttpExchangeRequest(exchange);
    }

    @Override
    public void respond(final Response response) throws IOException {
        for (final Header header : response.headers()) {
            this.exchange.getResponseHeaders().add(header.name(), header.value());
        }
        this.exchange.sendResponseHeaders(response.statusCode(), response.contentLength());
        if (response.contentLength() > -1) {
            try (OutputStream body = this.exchange.getResponseBody()) {
                final byte[] buffer = new byte[4096];
                int count = 0;
                do {
                    body.write(buffer, 0, count);
                    count = response.body().read(buffer);
                } while (count > 0);
            }
        }
    }
}

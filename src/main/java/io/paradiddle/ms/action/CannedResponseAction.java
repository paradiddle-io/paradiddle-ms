package io.paradiddle.ms.action;

import io.paradiddle.ms.Request;
import io.paradiddle.ms.Response;
import java.util.function.Function;
import java.util.function.Supplier;

public final class CannedResponseAction implements Function<Request, Response> {
    private final Supplier<Response> responses;

    public CannedResponseAction(final Supplier<Response> responses) {
        this.responses = responses;
    }

    @Override
    public Response apply(final Request request) {
        return this.responses.get();
    }
}

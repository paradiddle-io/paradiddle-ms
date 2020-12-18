package io.paradiddle.ms;

import java.io.IOException;

public interface Client {
    Request request();
    void respond(Response response) throws IOException;
}

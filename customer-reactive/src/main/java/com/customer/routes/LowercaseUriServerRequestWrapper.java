package com.customer.routes;

import org.springframework.http.server.PathContainer;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.support.ServerRequestWrapper;

import java.net.URI;

public class LowercaseUriServerRequestWrapper extends ServerRequestWrapper {

    public LowercaseUriServerRequestWrapper(final ServerRequest delegate) {
        super(delegate);
    }

    /*
    Lower-case the request URI and return that.
     */
    @Override
    public URI uri() {
        return URI.create(super.uri().toString().toLowerCase());
    }

    @Override
    public String path() {
        return uri().getRawPath();
    }

    @Override
    public PathContainer pathContainer() {
        return PathContainer.parsePath(path());
    }
}

/*
Now, assuming your RequestPredicate implementations all use lower-case Strings, this gives you case-
insensitive request matching. Issue a request to the /greetings/{name} endpoint and confirm it still
works. Uppercase the request and try again. You should see the same result.
 */
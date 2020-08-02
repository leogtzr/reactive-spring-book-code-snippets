package com.customer.routes;

import com.customer.routes.LowercaseUriServerRequestWrapper;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.ServerRequest;

public class CaseInsensitiveRequestPredicate implements RequestPredicate {

    private final RequestPredicate target;

    public static RequestPredicate i(final RequestPredicate rp) {
        return new CaseInsensitiveRequestPredicate(rp);
    }

    public CaseInsensitiveRequestPredicate(final RequestPredicate target) {
        this.target = target;
    }

    @Override
    public boolean test(final ServerRequest request) {
        /*
        Our wrapper RequestPredicate simply wraps the incoming ServerRequest with a
        LowercaseUriServerRequestWrapper and forwards it to the target RequestPredicate
         */
        return this.target.test(new LowercaseUriServerRequestWrapper(request));
    }

    @Override
    public String toString() {
        return this.target.toString();
    }
}

package com.views.controller;

import com.views.util.IntervalMessageProducer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import java.awt.*;

@Controller
public class TickerSseController {

    @GetMapping("/ticker.php")
    public String initialView() {
        return "ticker";
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/ticker-stream")
    public String streamingUpdates(final Model model) {
        final var producer = IntervalMessageProducer.produce();
        // Used to wrap a Publisher<T> ...
        final var updates = new ReactiveDataDriverContextVariable(producer, 1);
        model.addAttribute("updates", updates);
        /*
The return value for the controller is in a special format used by Thymeleaf to signify that it should render a fragment - in
this case, that fragment refers to the div element, updateBlock, containing our loop - found inside a containing view, ticker.
         */
        return "ticker :: #updateBlock";
    }

}

/*
This first controller renders the initial view of the template. It does not provide a value for the
updates model attribute. A client could request /ticker.php and get the template layout. When the
template loads it’ll run the JavaScript and that will in turn stream the updated markup from...
2 ...this server-sent event stream that uses a ReactiveDataDriverContextVariable to wrap the
Publisher<T> and set it as the updates model attribute for the returned view. The return value for the
controller is in a special format used by Thymeleaf to signify that it should render a fragment - in
this case, that fragment refers to the div element, updateBlock, containing our loop - found inside a
containing view, ticker.
 */
package com.chat.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.socket.WebSocketSession;

@Data
// @RequiredArgsConstructor
public class Connection {

    private String id;
    private WebSocketSession session;

    public Connection(String id, WebSocketSession session) {
        this.id = id;
        this.session = session;
    }
}

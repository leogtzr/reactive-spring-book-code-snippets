package com.chat.domain;

import lombok.Data;
// import lombok.RequiredArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
public class Message {

    private String clientId;
    private String text;
    private Date when;

}

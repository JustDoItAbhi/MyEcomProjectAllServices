package com.ecommer.userservices.users.usercontroller;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageResponse {
    private String message;


    public MessageResponse(String message) {
        this.message = message;

    }
}

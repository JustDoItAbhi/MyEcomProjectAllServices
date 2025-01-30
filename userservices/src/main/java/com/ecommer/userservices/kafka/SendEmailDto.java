package com.ecommer.userservices.kafka;

import lombok.Getter;
import lombok.Setter;


public class SendEmailDto {// dto for kafka email notification
private String from;// sender of email
    private String to;// receiver of email
    private String subject;// subject for email
    private String body;// your message in mail body
//getters and setters
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

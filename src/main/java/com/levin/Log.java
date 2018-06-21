package com.levin;

public class Log {

    int content_id;
    String sender;
    String receiver;
    String response_content;
    long moment;

    public Log(int content_id, String sender, String receiver, String response_content, long moment) {
        this.content_id = content_id;
        this.sender = sender;
        this.receiver = receiver;
        this.response_content = response_content;
        this.moment = moment;
    }

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getResponse_content() {
        return response_content;
    }

    public void setResponse_content(String response_content) {
        this.response_content = response_content;
    }

    public long getMoment() {
        return moment;
    }

    public void setMoment(long moment) {
        this.moment = moment;
    }
}

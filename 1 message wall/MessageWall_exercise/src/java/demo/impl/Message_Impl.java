package demo.impl;

import demo.spec.Message;

public class Message_Impl implements Message, java.io.Serializable {

    private String user, message;

    Message_Impl(String user, String msg) {
        this.user = user;
        message = msg;
    }

    @Override
    public String getContent() {
        return message;
    }

    @Override
    public String getOwner() {
        return user;
    }

}

package demo.impl;

import demo.spec.Message;
import demo.spec.MessageWall;
import demo.spec.RemoteLogin;
import demo.spec.UserAccess;
import java.util.ArrayList;
import java.util.List;

public class MessageWall_and_RemoteLogin_Impl implements RemoteLogin, MessageWall {

    private List<Message> messages;
    
    public MessageWall_and_RemoteLogin_Impl() {
        messages = new ArrayList<Message>();
    }

    @Override
    public UserAccess connect(String usr, String passwd) {
        // Check if combination usr, passwd is correct (with DB / list)
        return new UserAccess_Impl(this, usr);
    }

    @Override
    public void put(String user, String msg) {
        Message_Impl message = new Message_Impl(user, msg);
        messages.add(message);
    }

    @Override
    public boolean delete(String user, int index) {
        messages.remove(index);
        return true;
    }

    @Override
    public Message getLast() {
        return messages.get(messages.size() - 1);
    }

    @Override
    public int getNumber() {
        return messages.size();
    }

    @Override
    public List<Message> getAllMessages() {
        return messages;
    }

}

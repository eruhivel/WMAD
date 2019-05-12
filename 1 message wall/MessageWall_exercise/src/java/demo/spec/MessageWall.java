package demo.spec;

import java.util.List;

public interface MessageWall {
    void put(String user, String msg);
    boolean delete(String user, int index);
    Message getLast();
    int getNumber();
    List<Message> getAllMessages();
}

package demo.spec;

import java.util.List;

public interface UserAccess {
    String getUser();
    Message getLast();
    int getNumber();
    void put(String msg);
    boolean delete(int index);
    List<Message> getAllMessages();
}

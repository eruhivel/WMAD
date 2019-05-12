package publisher;

import util.Message;
import javax.websocket.Session;
import subscriber.Subscriber;

public interface Publisher {
    
    void    incPublishers();
    int     decPublishers();
    void    attachSubscriber(Subscriber subscriber);
    boolean detachSubscriber(Subscriber subscriber);
    void    detachAllSubscribers();
    
    void    publish(Message message);
    
    //this is necessary on the remote version:
    Subscriber subscriber(Session sesion);
}

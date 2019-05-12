package subscriber;

import util.Message;
import util.Subscription_close;

public interface Subscriber {
  
    void onMessage(Message message);
    void onClose(Subscription_close subs_close);
}

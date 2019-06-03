package topicmanager;

import entity.Message;
import util.Subscription_check;
import entity.Topic;
import util.Topic_check;
import java.util.*;
import publisher.Publisher;
import subscriber.Subscriber;

public interface TopicManager {

  Publisher     addPublisherToTopic(Topic topic);
  void          removePublisherFromTopic(Topic topic);
  Topic_check   isTopic(Topic topic);
  List<Topic>   topics();
  
  Subscription_check   subscribe(Topic topic, Subscriber subscriber);
  Subscription_check   unsubscribe(Topic topic, Subscriber subscriber);
  
  //to close the websocket session:  
  void close();
    
  //to restore the user profile:  
  Publisher               publisherOf();
  List<entity.Subscriber> mySubscriptions();
  List<Message>           messagesFrom(Topic topic);
}

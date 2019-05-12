package topicmanager;

import util.Subscription_check;
import util.Topic;
import util.Topic_check;
import java.util.List;
import publisher.Publisher;
import subscriber.Subscriber;

public interface TopicManager {

  Publisher     addPublisherToTopic(Topic topic);
  void          removePublisherFromTopic(Topic topic);
  Topic_check   isTopic(Topic topic);
  List<Topic>   topics();
  
  Subscription_check   subscribe(Topic topic, Subscriber subscriber);
  Subscription_check   unsubscribe(Topic topic, Subscriber subscriber);
}
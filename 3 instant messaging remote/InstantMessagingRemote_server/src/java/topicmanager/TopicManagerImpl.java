package topicmanager;

import util.Subscription_check;
import util.Topic;
import util.Topic_check;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import publisher.Publisher;
import publisher.PublisherImpl;
import subscriber.Subscriber;

public class TopicManagerImpl implements TopicManager {

    private Map<Topic, Publisher> topicMap;

    public TopicManagerImpl() {
        topicMap = new HashMap<Topic, Publisher>();
    }

    @Override
    public Publisher addPublisherToTopic(Topic topic) {
        if (!isTopic(topic).isOpen) {
            Publisher publisher = new PublisherImpl(topic);
            topicMap.put(topic, publisher);
            return publisher;

        } else {
            Publisher publisher = topicMap.get(topic);
            publisher.incPublishers();
            return publisher;
        }
    }

    @Override
    public void removePublisherFromTopic(Topic topic) {
        Publisher publisher = topicMap.get(topic);
        int numPublishers = publisher.decPublishers();
        System.out.println(numPublishers);
        if (numPublishers == 0) {
            publisher.detachAllSubscribers();
            topicMap.remove(topic);
        }
    }

    @Override
    public Topic_check isTopic(Topic topic) {
        if (!topicMap.containsKey(topic)) {
            Topic_check check = new Topic_check(topic, false);
            return check;
        }
        Topic_check check = new Topic_check(topic, true);
        return check;
    }

    @Override
    public List<Topic> topics() {
        List<Topic> topics = new ArrayList<Topic>(topicMap.keySet());
        return topics;
    }

    @Override
    public Subscription_check subscribe(Topic topic, Subscriber subscriber) {
        Publisher publisher = topicMap.get(topic);
        if (!isTopic(topic).isOpen) {
            Subscription_check sub_check = new Subscription_check(topic, Subscription_check.Result.NO_TOPIC);
            return sub_check;
        }
        Subscription_check sub_check = new Subscription_check(topic, Subscription_check.Result.OKAY);
        publisher.attachSubscriber(subscriber);
        return sub_check;
    }

    @Override
    public Subscription_check unsubscribe(Topic topic, Subscriber subscriber) {
        Publisher publisher = topicMap.get(topic);
        if (!isTopic(topic).isOpen) {
            Subscription_check sub_check = new Subscription_check(topic, Subscription_check.Result.NO_TOPIC);
            return sub_check;
        }
        publisher.attachSubscriber(subscriber);
        if (publisher.detachSubscriber(subscriber)) {
            Subscription_check sub_check = new Subscription_check(topic, Subscription_check.Result.OKAY);
            return sub_check;
        }
        Subscription_check sub_check = new Subscription_check(topic, Subscription_check.Result.NO_SUBSCRIPTION);
        return sub_check;
    }

    public Publisher publisher(Topic topic) {
        return topicMap.get(topic);
    }

}

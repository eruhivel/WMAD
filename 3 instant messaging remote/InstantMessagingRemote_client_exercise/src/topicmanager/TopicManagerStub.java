package topicmanager;

import apiREST.apiREST_TopicManager;
import util.Subscription_check;
import util.Topic;
import util.Topic_check;
import java.util.List;
import publisher.Publisher;
import publisher.PublisherStub;
import subscriber.Subscriber;
import webSocketService.WebSocketClient;

public class TopicManagerStub implements TopicManager {

    public String user;

    public TopicManagerStub(String user) {
        WebSocketClient.newInstance();
        this.user = user;
    }

    public void close() {
        WebSocketClient.close();
    }

    @Override
    public Publisher addPublisherToTopic(Topic topic) {
        apiREST_TopicManager.addPublisherToTopic(topic);
        Publisher publisher = new PublisherStub(topic);
        return publisher;
    }

    @Override
    public void removePublisherFromTopic(Topic topic) {
        apiREST_TopicManager.removePublisherFromTopic(topic);
    }

    @Override
    public Topic_check isTopic(Topic topic) {
        return apiREST_TopicManager.isTopic(topic);
    }

    @Override
    public List<Topic> topics() {
        return apiREST_TopicManager.topics();
    }

    @Override
    public Subscription_check subscribe(Topic topic, Subscriber subscriber) {
        if (!isTopic(topic).isOpen) {
            Subscription_check sub_check = new Subscription_check(topic, Subscription_check.Result.NO_TOPIC);
            return sub_check;
        }
        WebSocketClient.addSubscriber(topic, subscriber);
        Subscription_check sub_check = new Subscription_check(topic, Subscription_check.Result.OKAY);
        return sub_check;
    }

    @Override
    public Subscription_check unsubscribe(Topic topic, Subscriber subscriber) {
        if (!isTopic(topic).isOpen) {
            Subscription_check sub_check = new Subscription_check(topic, Subscription_check.Result.NO_TOPIC);
            return sub_check;
        }
        // Missing "NO_SUB"
 //       if (!WebSocketClient.checkSubscription(topic, subscriber)) {
 //           Subscription_check sub_check = new Subscription_check(topic, Subscription_check.Result.NO_SUBSCRIPTION);
 //           return sub_check;
 //       }

        WebSocketClient.removeSubscriber(topic);
        Subscription_check sub_check = new Subscription_check(topic, Subscription_check.Result.OKAY);
        return sub_check;
    }

}

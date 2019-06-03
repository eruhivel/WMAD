package topicmanager;

import apiREST.apiREST_Message;
import apiREST.apiREST_Publisher;
import apiREST.apiREST_Subscriber;
import apiREST.apiREST_Topic;
import entity.Message;
import util.Subscription_check;
import entity.Topic;
import util.Topic_check;
import entity.User;
import java.util.List;
import publisher.Publisher;
import publisher.PublisherStub;
import subscriber.Subscriber;
import webSocketService.WebSocketClient;

public class TopicManagerStub implements TopicManager {

    public User user;

    public TopicManagerStub(User user) {
        WebSocketClient.newInstance();
        this.user = user;
    }

    public void close() {
        WebSocketClient.close();
    }

    @Override
    public Publisher addPublisherToTopic(Topic topic) {
        entity.Publisher publisher = new entity.Publisher();
        publisher.setTopic(topic);
        publisher.setUser(user);
        apiREST_Publisher.createPublisher(publisher);
        return new PublisherStub(topic);
    }

    @Override
    public void removePublisherFromTopic(Topic topic) {
        entity.Publisher publisher = new entity.Publisher();
        publisher.setTopic(topic);
        publisher.setUser(user);
        apiREST_Publisher.deletePublisher(publisher);
    }

    @Override
    public Topic_check isTopic(Topic topic) {
        return apiREST_Topic.isTopic(topic);
    }

    @Override
    public List<Topic> topics() {
        return apiREST_Topic.allTopics();
    }

    @Override
    public Subscription_check subscribe(Topic topic, Subscriber subscriber) {
        entity.Subscriber sub = new entity.Subscriber();
        sub.setTopic(topic);
        sub.setUser(user);
        WebSocketClient.addSubscriber(topic, subscriber);
        return apiREST_Subscriber.createSubscriber(sub);
    }

    @Override
    public Subscription_check unsubscribe(Topic topic, Subscriber subscriber) {
        WebSocketClient.removeSubscriber(topic);
        entity.Subscriber sub = new entity.Subscriber();
        sub.setTopic(topic);
        sub.setUser(user);
        return apiREST_Subscriber.deleteSubscriber(sub);
    }

    @Override
    public Publisher publisherOf() {
        entity.Publisher entity_Publisher = apiREST_Publisher.PublisherOf(user);
        if(entity_Publisher != null) {
            return new PublisherStub(entity_Publisher.getTopic());
        }
        return null;
    }

    @Override
    public List<entity.Subscriber> mySubscriptions() {
        return apiREST_Subscriber.mySubscriptions(user);
    }

    @Override
    public List<Message> messagesFrom(Topic topic) {
        return apiREST_Message.messagesFromTopic(topic);
    }

}

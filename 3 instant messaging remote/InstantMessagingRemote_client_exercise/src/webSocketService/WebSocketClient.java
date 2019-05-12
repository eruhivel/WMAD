package webSocketService;

import apiREST.Cons;
import com.google.gson.Gson;
import util.Message;
import util.Topic;
import util.Subscription_close;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import subscriber.Subscriber;
import util.Subscription_request;

@ClientEndpoint
public class WebSocketClient {

    static Map<Topic, Subscriber> subscriberMap;
    static Session session;

    public static void newInstance() {
        subscriberMap = new HashMap<Topic, Subscriber>();
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(WebSocketClient.class,
                    URI.create(Cons.SERVER_WEBSOCKET));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void addSubscriber(Topic topic, Subscriber subscriber) {
        try {
            subscriberMap.put(topic, subscriber);
            Subscription_request sub_req = new Subscription_request(topic, Subscription_request.Type.ADD);
            session.getAsyncRemote().sendText(new Gson().toJson(sub_req));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void removeSubscriber(Topic topic) {
        try {

            subscriberMap.remove(topic);
            Subscription_request sub_req = new Subscription_request(topic, Subscription_request.Type.REMOVE);
            session.getAsyncRemote().sendText(new Gson().toJson(sub_req));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String json) {

        Gson gson = new Gson();
        Subscription_close subs_close = gson.fromJson(json, Subscription_close.class);

        //ordinary message from topic:
        if (subs_close.cause == null) {
            Message message = gson.fromJson(json, Message.class);
            subscriberMap.get(message.topic).onMessage(message);
        } //ending subscription message:
        else {

            //...
        }
    }

    // To check No_SUB case
    public static boolean checkSubscription(Topic topic, Subscriber subscriber) {
        return subscriberMap.get(topic) == subscriber;
    }

}

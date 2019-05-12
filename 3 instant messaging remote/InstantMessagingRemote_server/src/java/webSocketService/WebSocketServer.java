package webSocketService;

import com.google.gson.Gson;
import java.io.IOException;
import java.sql.SQLException;
import javax.inject.Inject;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import subscriber.Subscriber;
import subscriber.SubscriberImpl;
import util.Subscription_request;
import publisher.Publisher;
import util.Global;

@ServerEndpoint("/ws")
public class WebSocketServer {

  @Inject
  Global global;

  @OnMessage
  public void onMessage(String json, Session session)
    throws IOException, SQLException {

    System.out.println("onMessage - Subscription_request: " + json);

    Subscription_request s_req = new Gson().fromJson(json, Subscription_request.class);

    if (s_req.type == Subscription_request.Type.ADD) {
      Publisher publisher = global.getTopicManager().publisher(s_req.topic);
      if (publisher != null) {
        Subscriber subscriber = publisher.subscriber(session);
        if (subscriber == null) {
          subscriber = new SubscriberImpl(session);
          global.getTopicManager().subscribe(s_req.topic, subscriber);
        }
      }
    } else if (s_req.type == Subscription_request.Type.REMOVE) {
      Publisher publisher = global.getTopicManager().publisher(s_req.topic);
      if (publisher != null) {
        Subscriber subscriber = publisher.subscriber(session);
        if (subscriber != null) {
          global.getTopicManager().unsubscribe(s_req.topic, subscriber);
        }
      }
    }
  }

  @OnOpen
  public void onOpen(Session session) {
    System.out.println("new session: " + session.getId());
  }

  @OnClose
  public void onClose(Session session) {
    System.out.println("closed session: " + session.getId());
  }

}

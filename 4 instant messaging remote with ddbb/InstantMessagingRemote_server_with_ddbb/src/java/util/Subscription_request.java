package util;

import entity.Topic;

/**
 *
 * @author juanluis
 */
public class Subscription_request {
  
  public enum Type { ADD, REMOVE };
  
  public Topic topic;
  public Type type;

  public Subscription_request(Topic topic, Type type) {
    this.topic = topic;
    this.type = type;
  }
  
  public Subscription_request(){}
  
}

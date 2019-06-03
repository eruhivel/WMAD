package util;

import entity.Topic;

/**
 *
 * @author juanluis
 */
public class Subscription_close {
  
  public enum Cause { PUBLISHER, SUBSCRIBER };
  
  public Topic topic;
  public Cause cause;

  public Subscription_close(Topic topic, Cause cause) {
    this.topic = topic;
    this.cause = cause;
  }
  
}

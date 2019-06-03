package util;

import entity.Topic;

/**
 *
 * @author juanluis
 */
public class Subscription_check {
  
  public enum Result { OKAY, NO_TOPIC, NO_SUBSCRIPTION };
  
  public Topic topic;
  public Result result;

  public Subscription_check(Topic topic, Result result) {
    this.topic = topic;
    this.result = result;
  }
  
}

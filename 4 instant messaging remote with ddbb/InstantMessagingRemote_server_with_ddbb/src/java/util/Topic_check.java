package util;

import entity.Topic;

/**
 *
 * @author juanluis
 */
public class Topic_check {
  
  public Topic topic;
  public boolean isOpen;

  public Topic_check(Topic topic, boolean isOpen) {
    this.topic = topic;
    this.isOpen = isOpen;
  }
  
  public Topic_check(){}
  
}

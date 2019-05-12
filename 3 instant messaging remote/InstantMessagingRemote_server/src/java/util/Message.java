package util;

/**
 *
 * @author juanluis
 */
public class Message {
  
  public Topic topic;
  public String content;

  public Message(Topic topic, String content) {
    this.topic = topic;
    this.content = content;
  }
  
  public Message(){}
  
}

package publisher;

import entity.Message;
import entity.Topic;

public interface Publisher {

  public void publish(Message message);
  
  //I need this when restoring the user profile:
  public Topic topic();
}

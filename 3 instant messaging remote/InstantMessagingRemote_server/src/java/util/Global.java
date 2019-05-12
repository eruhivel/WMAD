package util;


import javax.enterprise.context.ApplicationScoped;
import topicmanager.TopicManager;
import topicmanager.TopicManagerImpl;

/**
 *
 * @author juanluis
 */
@ApplicationScoped
public class Global {

  TopicManager topicManager;

  public Global() {
    topicManager = new TopicManagerImpl();
  }
  
  public TopicManager getTopicManager(){
    return topicManager;
  }
}

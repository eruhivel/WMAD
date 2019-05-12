package facadeREST;

import util.Topic;
import util.Topic_check;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import util.Global;

/**
 *
 * @author juanluis
 */
//this is necessary to be able to use @Inject,
//only for this, this java class must be a bean:
@Stateless
@Path("topicmanager")
public class TopicManagerFacadeREST {
  
  @Inject
  Global global;

  @POST
  @Path("addtopic")
  @Consumes({"application/xml", "application/json"})
  @Produces({"application/xml", "application/json"})
  public void addPublisherToTopic(Topic topic) {
    global.getTopicManager().addPublisherToTopic(topic);
  }

  @POST
  @Path("removetopic")
  @Consumes({"application/xml", "application/json"})
  @Produces({"application/xml", "application/json"})
  public void removePublisherFromTopic(Topic topic) {
    global.getTopicManager().removePublisherFromTopic(topic);
  }

  @POST
  @Path("istopic")
  @Consumes({"application/xml", "application/json"})
  @Produces({"application/xml", "application/json"})
  public Topic_check isTopic(Topic topic) {
    return global.getTopicManager().isTopic(topic);
  }

  @GET
  @Path("topics")
  @Consumes({"application/xml", "application/json"})
  @Produces({"application/xml", "application/json"})
  public List<Topic> topics() {
    return global.getTopicManager().topics();
  }

}

package facadeREST;

import util.Message;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import publisher.Publisher;
import util.Global;

/**
 *
 * @author juanluis
 */
//this is necessary to be able to use @Inject,
//only for this, this java class must be a bean:
@Stateless
@Path("publisher")
public class PublisherFacadeREST {
  
  @Inject
  Global global;
  
  @POST
  @Path("publish")
  @Consumes({"application/xml", "application/json"})
  @Produces({"application/xml", "application/json"})
  public void publish(Message message) {
    Publisher publisher = global.getTopicManager().publisher(message.topic);
    publisher.publish(message);
  }

}

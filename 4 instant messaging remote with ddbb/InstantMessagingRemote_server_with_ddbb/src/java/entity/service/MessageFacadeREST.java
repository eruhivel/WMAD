/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.service;

import entity.Message;
import entity.Topic;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import webSocketService.WebSocketServer;

/**
 *
 * @author juanluis
 */
@Stateless
@Path("entity.message")
public class MessageFacadeREST extends AbstractFacade<Message> {

  @PersistenceContext(unitName = "PubSubWebServerPU")
  private EntityManager em;

  public MessageFacadeREST() {
    super(Message.class);
  }

  @POST
  @Path("create")
  @Consumes({"application/xml", "application/json"})
  public void create(Message entity) {
    //we check out if the topic exists for this message:
    Query query = em.createQuery("select t from Topic t where t.name=:name");
    query.setParameter("name", entity.getTopic().getName());
    List<Topic> list = query.getResultList();
    if (!list.isEmpty()) {
      //I had to do this beacuse otherwise notifyNewMessage makes a stackOverflow:
      Message good_message = new Message();
      good_message.setTopic(list.get(0));
      good_message.setContent(entity.getContent());
      em.persist(good_message);
      em.flush();
      WebSocketServer.notifyNewMessage(entity);
    }
  }

  @POST
  @Path("messagesFromTopic")
  @Consumes({"application/xml", "application/json"})
  @Produces({"application/xml", "application/json"})
  public List<Message> messagesFrom(Topic entity) {
    Query query = em.createQuery("select t from Topic t where t.name=:name");
    query.setParameter("name", entity.getName());
    List<Topic> list = query.getResultList();
    if (!list.isEmpty()) {
      query = em.createQuery("select m from Message m where m.topic=:topic");
      query.setParameter("topic", list.get(0));
      return query.getResultList();
    }
    else{
      return null;
    }
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

}

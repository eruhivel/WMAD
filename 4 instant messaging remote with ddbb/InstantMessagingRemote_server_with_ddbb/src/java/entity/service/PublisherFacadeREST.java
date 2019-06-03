/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.service;

import entity.Publisher;
import entity.Topic;
import util.Subscription_close;
import entity.User;
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
@Path("entity.publisher")
public class PublisherFacadeREST extends AbstractFacade<Publisher> {
  
  @PersistenceContext(unitName = "PubSubWebServerPU")
  private EntityManager em;

  public PublisherFacadeREST() {
    super(Publisher.class);
  }
  
  @POST
  @Path("create")
  @Consumes({"application/xml", "application/json"})
  @Produces({"application/xml", "application/json"})
  public void create(Publisher entity) {
    //first we create the topic (if necessary):
    Query query = em.createQuery("select t from Topic t where t.name=:name");
    query.setParameter("name", entity.getTopic().getName());
    List list = query.getResultList();
    if(list.isEmpty()){
      em.persist(entity.getTopic());
      em.flush();
    }
    else{
      entity.setTopic((Topic)list.get(0));
    }
    //then we create the publisher of the topic:
    query = em.createQuery("select p from Publisher p where p.user=:user");
    query.setParameter("user", entity.getUser());
    list = query.getResultList();
    if(list.isEmpty()){
      em.persist(entity);
      em.flush();
    }
    else{
      em.merge(entity);
      em.flush();
    }
  }

  @POST
  @Path("delete")
  @Consumes({"application/xml", "application/json"})
  public void delete(Publisher entity) {
    //we check out if the user is really a publisher (not necessary):
    Query query = em.createQuery("select p from Publisher p where p.user=:user");
    query.setParameter("user", entity.getUser());
    List<Publisher> list = query.getResultList();
    if(list.isEmpty()){
      return ;
    }
    else{
      //we get the complete_Publisher to delete it:
      Publisher publisher = list.get(0);
      super.delete(publisher);
      //we get the complete_Topic to see if we have to delete it:
      Topic topic = publisher.getTopic();
      //we check out if he was the last publisher of that topic:
      query = em.createQuery("select p from Publisher p where p.topic=:topic");
      query.setParameter("topic", topic);
      list = query.getResultList();
      if(list.isEmpty()){
        System.out.println("no publisher left for the topic: "+topic.getName());
        query = em.createQuery("delete from Topic t where t.name=:name");
        query.setParameter("name", topic.getName());
        query.executeUpdate();
        //to notify the clients that this topic has been closed:
        Subscription_close subs_close = new Subscription_close(topic, Subscription_close.Cause.PUBLISHER);
        WebSocketServer.notifyTopicClose(subs_close);
      }
    }
  }
  
  @POST
  @Path("publisherOf")
  @Consumes({"application/xml", "application/json"})
  @Produces({"application/xml", "application/json"})
  public Publisher publisherOf(User entity) {    
    Query query = em.createQuery("select p from Publisher p where p.user=:user");
    query.setParameter("user", entity);
    List list = query.getResultList();
    if(list.isEmpty()){
      return null;
    }
    else{
      return (Publisher)list.get(0);
    }
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }
  
}

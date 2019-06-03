/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.service;

import entity.Subscriber;
import util.Subscription_check;
import entity.Topic;
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

/**
 *
 * @author upcnet
 */
@Stateless
@Path("entity.subscriber")
public class SubscriberFacadeREST extends AbstractFacade<Subscriber> {

  @PersistenceContext(unitName = "PubSubWebServerPU")
  private EntityManager em;

  public SubscriberFacadeREST() {
    super(Subscriber.class);
  }

  @POST
  @Path("create")
  @Consumes({"application/xml", "application/json"})
  @Produces({"application/xml", "application/json"})
  public Subscription_check check_to_create(Subscriber entity) {
    //first we check out if topic exists:
    Query query = em.createQuery("select t from Topic t where t.name=:name");
    query.setParameter("name", entity.getTopic().getName());
    List list = query.getResultList();
    if (list.isEmpty()) {
      return new Subscription_check(entity.getTopic(), Subscription_check.Result.NO_TOPIC);
    } else {
      entity.setTopic((Topic)list.get(0));
      query = em.createQuery("select s from Subscriber s where s.user=:user and s.topic=:topic");
      query.setParameter("user", entity.getUser());
      query.setParameter("topic", entity.getTopic());
      list = query.getResultList();
      if (list.isEmpty()) {
        em.persist(entity);
        em.flush();
        return new Subscription_check(entity.getTopic(), Subscription_check.Result.OKAY);
      } else {
        return new Subscription_check(entity.getTopic(), Subscription_check.Result.OKAY);
      }
    }
  }

  @POST
  @Path("delete")
  @Consumes({"application/xml", "application/json"})
  public Subscription_check check_to_delete(Subscriber entity) {
    //first we check out if topic exists:
    Query query = em.createQuery("select t from Topic t where t.name=:name");
    query.setParameter("name", entity.getTopic().getName());
    List list = query.getResultList();
    if (list.isEmpty()) {
      return new Subscription_check(entity.getTopic(), Subscription_check.Result.NO_TOPIC);
    } else {
      entity.setTopic((Topic)list.get(0));
      query = em.createQuery("select s from Subscriber s where s.user=:user and s.topic=:topic");
      query.setParameter("user", entity.getUser());
      query.setParameter("topic", entity.getTopic());
      list = query.getResultList();
      if (list.isEmpty()) {
        return new Subscription_check(entity.getTopic(), Subscription_check.Result.NO_SUBSCRIPTION);
      } else {
        super.delete((Subscriber) list.get(0));
        return new Subscription_check(entity.getTopic(), Subscription_check.Result.OKAY);
      }
    }
  }

  @POST
  @Path("subscriptions")
  @Consumes({"application/xml", "application/json"})
  @Produces({"application/xml", "application/json"})
  public List<Subscriber> subscriptions(User entity) {
    Query query = em.createQuery("select s from Subscriber s where s.user=:user");
    query.setParameter("user", entity);
    return query.getResultList();
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

}

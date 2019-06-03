/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.service;

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
import util.Login_check;

/**
 *
 * @author upcnet
 */
@Stateless
@Path("entity.user")
public class UserFacadeREST extends AbstractFacade<User> {
  @PersistenceContext(unitName = "PubSubWebServerPU")
  private EntityManager em;

  public UserFacadeREST() {
    super(User.class);
  }
  
  @POST
  @Path("create")
  @Consumes({"application/xml", "application/json"})
  @Produces({"application/xml", "application/json"})
  public User create_and_return(User entity) {
    Query query = em.createQuery("select u from User u where u.login=:login");
    query.setParameter("login", entity.getLogin());
    List<User> list = query.getResultList();
    if(list.isEmpty()){
      em.persist(entity);
      em.flush();
      return entity;
    }
    else{
      return list.get(0);
    }
  }
  
  @POST
  @Path("login")
  @Produces({"application/xml", "application/json"})
  @Consumes({"application/xml", "application/json"})
  public User login(Login_check login) {
    System.out.println("login: "+login.login+", password: "+login.password);
    Query query = em.createQuery("select u from User u where u.login=:login AND u.password=:password");
    query.setParameter("login", login.login);
    query.setParameter("password", login.password);
    try{
      return (User)query.getSingleResult();
    }
    catch(Exception e){
      return null;
    }
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }
  
}

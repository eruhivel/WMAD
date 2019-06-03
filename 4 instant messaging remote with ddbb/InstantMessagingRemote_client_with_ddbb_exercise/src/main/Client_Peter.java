/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import apiREST.apiREST_User;
import entity.User;
import topicmanager.TopicManagerStub;
import util.Login_check;

/**
 *
 * @author juanluis
 */
public class Client_Peter {

  public static void main(String[] args) {
    
    //execute one client at a time to open separate windows on Netbeans,
    //and also because otherwise all clients share the same WebSocket connection.

    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        
        //the first time you have to create the user:        
//        User user_peter = new entity.User();
//        user_peter.setLogin("peter");
//        user_peter.setPassword("1234");
//        user_peter = apiREST_User.createUser(user_peter);
        
        //now you only have to log into the system:
        Login_check login = new Login_check();
        login.login = "peter";
        login.password = "1234";
        User user_peter = apiREST_User.loginUser(login);
        
        SwingClient client = new SwingClient(new TopicManagerStub(user_peter));
        client.createAndShowGUI();
      }
    });
    
  }
}

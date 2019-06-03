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
public class Client_John {

  public static void main(String[] args) {
    
    //execute one client at a time to open separate windows on Netbeans,
    //and also because otherwise all clients share the same WebSocket connection.

    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        
        //the first time you have to create the user:
//        User user_john = new entity.User();
//        user_john.setLogin("john");
//        user_john.setPassword("1234");
//        user_john = apiREST_User.createUser(user_john);
        
        //now you only have to log into the system:
        Login_check login = new Login_check();
        login.login = "john";
        login.password = "1234";
        User user_john = apiREST_User.loginUser(login);
        
        SwingClient client = new SwingClient(new TopicManagerStub(user_john));
        client.createAndShowGUI();
      }
    });
    
  }
}

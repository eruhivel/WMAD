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
public class Client_Molly {

  public static void main(String[] args) {
    
    //execute one client at a time to open separate windows on Netbeans,
    //and also because otherwise all clients share the same WebSocket connection.

    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        
        //the first time you have to create the user:        
//        User user_molly = new entity.User();
//        user_molly.setLogin("molly");
//        user_molly.setPassword("1234");
//        user_molly = apiREST_User.createUser(user_molly);
        
        //now you only have to log into the system:
        Login_check login = new Login_check();
        login.login = "molly";
        login.password = "1234";
        User user_molly = apiREST_User.loginUser(login);
        
        SwingClient client = new SwingClient(new TopicManagerStub(user_molly));
        client.createAndShowGUI();
      }
    });
    
  }
}

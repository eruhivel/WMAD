/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import topicmanager.TopicManagerStub;

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
        SwingClient client = new SwingClient(new TopicManagerStub("peter"));
        client.createAndShowGUI();
      }
    });
    
  }
}

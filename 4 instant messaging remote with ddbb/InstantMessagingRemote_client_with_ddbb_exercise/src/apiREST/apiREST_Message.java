package apiREST;

import com.google.gson.Gson;
import entity.Message;
import entity.Topic;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.List;

public class apiREST_Message {
  public static void createMessage(Message message) {
    try {
      URL url = new URL(Cons.SERVER_REST+"/entity.message/create");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();

      ucon.setRequestMethod("POST");
      ucon.setDoInput(true);
      ucon.setDoOutput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");

      PrintWriter out = new PrintWriter(ucon.getOutputStream(), true);
      Gson gson = new Gson();
      String json = gson.toJson(message);
      out.println(json);
      out.flush();
      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      String line;
      while ((line = in.readLine()) != null) {
        System.out.println(line);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static List<Message> messagesFromTopic(Topic topic){
    try {
      URL url = new URL(Cons.SERVER_REST+"/entity.message/messagesFromTopic");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();

      ucon.setRequestMethod("POST");
      ucon.setDoInput(true);
      ucon.setDoOutput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");

      PrintWriter out = new PrintWriter(ucon.getOutputStream(), true);
      Gson gson = new Gson();
      String json = gson.toJson(topic);
      out.println(json);
      out.flush();
      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));     
      Message[] messageArray = gson.fromJson(in, Message[].class);
      return Arrays.asList(messageArray);
      
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}

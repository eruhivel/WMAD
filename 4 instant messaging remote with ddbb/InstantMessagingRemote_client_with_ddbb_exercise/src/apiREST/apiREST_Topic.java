package apiREST;

import com.google.gson.Gson;
import entity.Topic;
import util.Topic_check;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.List;

public class apiREST_Topic {

  public static Topic_check isTopic(Topic topic) {
    try {
      URL url = new URL(Cons.SERVER_REST+"/entity.topic/isTopic");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();

      ucon.setRequestMethod("POST");
      ucon.setDoInput(true);
      ucon.setDoOutput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");
      
      PrintWriter out = new PrintWriter(ucon.getOutputStream(), true);
      Gson gson = new Gson();
      String json = gson.toJson(topic);
      System.out.println(json);
      out.println(json);
      out.flush();
      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      return gson.fromJson(in, Topic_check.class);

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }
  
  public static List<Topic> allTopics(){
    try {
      URL url = new URL(Cons.SERVER_REST+"/entity.topic/allTopics");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();

      ucon.setRequestMethod("GET");
      ucon.setDoInput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");
      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      Topic[] topicArray = new Gson().fromJson(in, Topic[].class);
      return Arrays.asList(topicArray);
      
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

}

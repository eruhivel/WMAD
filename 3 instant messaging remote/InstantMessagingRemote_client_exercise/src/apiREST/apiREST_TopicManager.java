package apiREST;

import com.google.gson.Gson;
import util.Topic;
import util.Topic_check;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.List;

public class apiREST_TopicManager {
  
  public static void addPublisherToTopic(Topic topic) {
    try {
      URL url = new URL(Cons.SERVER_REST + "/topicmanager/addtopic");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
      ucon.setRequestMethod("POST");
      ucon.setDoInput(true);
      ucon.setDoOutput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");

      PrintWriter out = new PrintWriter(ucon.getOutputStream(), true);
      String json = new Gson().toJson(topic);
      System.out.println("addPublisherToTopic: "+json);
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
  public static void removePublisherFromTopic(Topic topic) {
    try {
      URL url = new URL(Cons.SERVER_REST + "/topicmanager/removetopic");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();

      ucon.setRequestMethod("POST");
      ucon.setDoInput(true);
      ucon.setDoOutput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");

      PrintWriter out = new PrintWriter(ucon.getOutputStream(), true);
      String json = new Gson().toJson(topic);
      System.out.println("removePublisherFromTopic: "+json);
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
  public static Topic_check isTopic(Topic topic) {
    try {
      URL url = new URL(Cons.SERVER_REST + "/topicmanager/istopic");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();

      ucon.setRequestMethod("POST");
      ucon.setDoInput(true);
      ucon.setDoOutput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");

      PrintWriter out = new PrintWriter(ucon.getOutputStream(), true);
      String json = new Gson().toJson(topic);
      System.out.println("isTopic: "+json);
      out.println(json);
      out.flush();
      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      return new Gson().fromJson(in, Topic_check.class);

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  public static List<Topic> topics() {
    try {
      URL url = new URL(Cons.SERVER_REST + "/topicmanager/topics");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();

      ucon.setRequestMethod("GET");
      ucon.setDoInput(true);
      ucon.setRequestProperty("Accept", "application/json");
      
      System.out.println("topics()");
      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      Topic[] reply = new Gson().fromJson(in, Topic[].class);
      return Arrays.asList(reply);

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
}

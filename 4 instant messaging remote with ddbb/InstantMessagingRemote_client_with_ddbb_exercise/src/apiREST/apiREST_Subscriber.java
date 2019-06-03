package apiREST;

import com.google.gson.Gson;
import entity.Subscriber;
import util.Subscription_check;
import entity.User;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.List;

public class apiREST_Subscriber {
  public static Subscription_check createSubscriber(Subscriber subscriber) {
    try {
      URL url = new URL(Cons.SERVER_REST+"/entity.subscriber/create");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();

      ucon.setRequestMethod("POST");
      ucon.setDoInput(true);
      ucon.setDoOutput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");

      PrintWriter out = new PrintWriter(ucon.getOutputStream(), true);
      Gson gson = new Gson();
      String json = gson.toJson(subscriber);
      System.out.println(json);
      out.println(json);
      out.flush();
      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      return gson.fromJson(in, Subscription_check.class);

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Subscription_check deleteSubscriber(Subscriber subscriber) {
    try {
      URL url = new URL(Cons.SERVER_REST+"/entity.subscriber/delete");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();

      ucon.setRequestMethod("POST");
      ucon.setDoInput(true);
      ucon.setDoOutput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");
      
      PrintWriter out = new PrintWriter(ucon.getOutputStream(), true);
      Gson gson = new Gson();
      String json = gson.toJson(subscriber);
      System.out.println(json);
      out.println(json);
      out.flush();
      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      return gson.fromJson(in, Subscription_check.class);

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
  public static List<Subscriber> mySubscriptions(User user) {
    try {
      URL url = new URL(Cons.SERVER_REST+"/entity.subscriber/subscriptions");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();

      ucon.setRequestMethod("POST");
      ucon.setDoInput(true);
      ucon.setDoOutput(true);
      ucon.setRequestProperty("Content-Type", "application/json");
      ucon.setRequestProperty("Accept", "application/json");

      PrintWriter out = new PrintWriter(ucon.getOutputStream(), true);
      Gson gson = new Gson();
      String json = gson.toJson(user);
      System.out.println(json);
      out.println(json);
      out.flush();
      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      Subscriber[] subscriberArray = gson.fromJson(in, Subscriber[].class);
      return Arrays.asList(subscriberArray);

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}

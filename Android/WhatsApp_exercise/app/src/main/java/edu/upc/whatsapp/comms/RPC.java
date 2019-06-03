package edu.upc.whatsapp.comms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entity.Message;
import entity.User;
import entity.UserInfo;

import static edu.upc.whatsapp.comms.Comms.gson;
import static edu.upc.whatsapp.comms.Comms.url_rpc;

public class RPC {
  public static final int TIMEOUT = 5000;
  public static UserInfo registration(User user) {
    try {
      URL url = new URL(url_rpc+"/entity.user/create");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
      ucon.setRequestMethod("POST");
      ucon.setDoInput(true);
      ucon.setDoOutput(true);
      ucon.setConnectTimeout(TIMEOUT);
      ucon.setRequestProperty("Content-Type", "application/json; charset=utf-8");
      ucon.setRequestProperty("Accept", "application/json; charset=utf-8");

      PrintWriter out = new PrintWriter(ucon.getOutputStream(), true);
      out.println(gson.toJson(user));

      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      UserInfo userInfo = gson.fromJson(in, UserInfo.class);

      in.close();
      out.close();
      ucon.getInputStream().close();

      return userInfo;

    } catch (Exception e) {
      e.printStackTrace();
      UserInfo userInfo_exception = new UserInfo(-2);
      userInfo_exception.setName(e.getMessage());
      return userInfo_exception;
    }
  }
  public static UserInfo login(User user) {
    try {
      URL url = new URL(url_rpc+"/entity.user/login");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
      ucon.setRequestMethod("POST");
      ucon.setDoInput(true);
      ucon.setDoOutput(true);
      ucon.setConnectTimeout(TIMEOUT);
      ucon.setRequestProperty("Content-Type", "application/json; charset=utf-8");
      ucon.setRequestProperty("Accept", "application/json; charset=utf-8");

      PrintWriter out = new PrintWriter(ucon.getOutputStream(), true);
      out.println(gson.toJson(user));

      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      UserInfo userInfo = gson.fromJson(in, UserInfo.class);

      in.close();
      out.close();
      ucon.getInputStream().close();

      return userInfo;

    } catch (Exception e) {
      e.printStackTrace();
      UserInfo userInfo_exception = new UserInfo(-2);
      userInfo_exception.setName(e.getMessage());
      return userInfo_exception;
    }
  }
  public static List<UserInfo> allUserInfos(){
    try {
      URL url = new URL(url_rpc+"/entity.userinfo");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
      ucon.setRequestMethod("GET");
      ucon.setDoInput(true);
      ucon.setConnectTimeout(TIMEOUT);
      ucon.setRequestProperty("Content-Type", "application/json; charset=utf-8");
      ucon.setRequestProperty("Accept", "application/json; charset=utf-8");

      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      UserInfo[] userArray = gson.fromJson(in, UserInfo[].class);
      List<UserInfo> users = new ArrayList<UserInfo>();
      users.addAll(Arrays.asList(userArray));

      in.close();
      ucon.getInputStream().close();

      return users;

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  public static Message postMessage(Message message) {
    try {
      URL url = new URL(url_rpc+"/entity.message/create");
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
      ucon.setRequestMethod("POST");
      ucon.setDoInput(true);
      ucon.setDoOutput(true);
      ucon.setConnectTimeout(TIMEOUT);
      ucon.setRequestProperty("Content-Type", "application/json; charset=utf-8");
      ucon.setRequestProperty("Accept", "application/json; charset=utf-8");

      PrintWriter out = new PrintWriter(ucon.getOutputStream(), true);
      out.println(gson.toJson(message));

      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      Message message_reply = gson.fromJson(in, Message.class);

      in.close();
      out.close();
      ucon.getInputStream().close();

      return message_reply;

    } catch (Exception e) {
      e.printStackTrace();
      Message error_message = new Message();
      error_message.setId(-2);
      error_message.setContent(e.getMessage());
      return error_message;
    }
  }
  public static List<Message> retrieveMessages(int from, int to) {
    try {
      URL url = new URL(url_rpc+"/entity.message/users/"+from+"/"+to);
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
      ucon.setRequestMethod("GET");
      ucon.setDoInput(true);
      ucon.setConnectTimeout(TIMEOUT);
      ucon.setRequestProperty("Content-Type", "application/json; charset=utf-8");
      ucon.setRequestProperty("Accept", "application/json; charset=utf-8");

      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      Message[] messageArray = gson.fromJson(in, Message[].class);
      List<Message> messages = new ArrayList<Message>();
      messages.addAll(Arrays.asList(messageArray));

      in.close();
      ucon.getInputStream().close();

      return messages;

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  public static List<Message> retrieveNewMessages(int from, int to, Message message) {
    try {
      URL url = new URL(url_rpc+"/entity.message/users/"+from+"/"+to);
      HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
      ucon.setRequestMethod("POST");
      ucon.setDoInput(true);
      ucon.setDoOutput(true);
      ucon.setConnectTimeout(TIMEOUT);
      ucon.setRequestProperty("Content-Type", "application/json; charset=utf-8");
      ucon.setRequestProperty("Accept", "application/json; charset=utf-8");

      PrintWriter out = new PrintWriter(ucon.getOutputStream(), true);
      out.println(gson.toJson(message));

      ucon.connect();

      BufferedReader in = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
      Message[] messageArray = gson.fromJson(in, Message[].class);
      List<Message> messages = new ArrayList<Message>();
      messages.addAll(Arrays.asList(messageArray));

      in.close();
      out.close();
      ucon.getInputStream().close();

      return messages;

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}

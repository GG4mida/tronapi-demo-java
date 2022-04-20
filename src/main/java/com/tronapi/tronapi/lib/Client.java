package com.tronapi.tronapi.lib;

import java.util.HashMap;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Client {

  // 远程服务地址
  private String host = "https://pro.tronapi.com";

  // 账户调用实例
  public Account account = null;

  // 地址调用实例
  public Address address = null;

  // 订单调用实例
  public Transaction transaction = null;

  /**
   * 构造函数，需要提供 public_key & private_key
   * 
   * @param publicKey  商户 public_key
   * @param privateKey 商户 private_key
   * @throws Exception
   */
  public Client(String publicKey, String privateKey) throws Exception {
    if (Helper.isStrEmpty(publicKey)) {
      throw new Exception("public key is required");
    }
    if (Helper.isStrEmpty(privateKey)) {
      throw new Exception("private key is required");
    }

    this.account = new Account(this, publicKey, privateKey);
    this.address = new Address(this, publicKey, privateKey);
    this.transaction = new Transaction(this, publicKey, privateKey);
  }

  /**
   * 设置远程服务地址
   * 
   * @param host 远程服务地址，默认 https://pro.tronapi.com
   */
  public void setHost(String host) {
    this.host = host;
  }

  /**
   * 发送 post 请求
   * 
   * @param path 请求路径
   * @param data 请求数据
   * @return JsonNode
   * @throws Exception
   */
  public JsonNode doPost(String path, HashMap<String, Object> data) throws Exception {
    String url = this.host + "/api/" + path;
    JsonNode res = null;
    try {
      OkHttpClient client = new OkHttpClient();
      FormBody.Builder builder = new FormBody.Builder();
      for (String key : data.keySet()) {
        builder.add(key, data.get(key).toString());
      }
      Request request = new Request.Builder()
          .url(url)
          .post(builder.build())
          .build();
      Response response = client.newCall(request).execute();
      res = this.handleResponse(response);
    } catch (Exception err) {
      throw err;
    }
    return res;
  }

  /**
   * 发送 get 请求
   * 
   * @param path 请求路径
   * @param data 请求数据
   * @return JsonNode
   * @throws Exception
   */
  public JsonNode doGet(String path, HashMap<String, Object> data) throws Exception {
    String url = this.host + "/api/" + path;
    JsonNode res = null;
    try {
      OkHttpClient client = new OkHttpClient();
      HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
      for (String key : data.keySet()) {
        urlBuilder.addQueryParameter(key, data.get(key).toString());
      }
      Request.Builder reqBuilder = new Request.Builder();
      reqBuilder.url(urlBuilder.build());
      Request request = reqBuilder.build();
      Response response = client.newCall(request).execute();
      res = this.handleResponse(response);
    } catch (Exception err) {
      throw err;
    }
    return res;
  }

  /**
   * 返回结果处理
   * 
   * @param response 响应 response
   * @return JsonNode
   * @throws Exception
   */
  private JsonNode handleResponse(Response response) throws Exception {
    JsonNode res = null;
    try {
      if (response.isSuccessful()) {
        ObjectMapper objectMapper = new ObjectMapper();
        res = objectMapper.readValue(response.body().string(), JsonNode.class);
      }
    } catch (Exception err) {
      throw err;
    }
    return res;
  }
}

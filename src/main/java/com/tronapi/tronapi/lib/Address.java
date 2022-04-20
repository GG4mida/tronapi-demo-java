package com.tronapi.tronapi.lib;

import java.util.HashMap;
import com.fasterxml.jackson.databind.JsonNode;

public class Address {

  private Client client = null;
  private String publicKey = "";
  private String privateKey = "";

  public Address(Client client, String publicKey, String privateKey) {
    this.client = client;
    this.publicKey = publicKey;
    this.privateKey = privateKey;
  }

  /**
   * 收款地址查询
   * https://doc.tronapi.com/api/address/list.html
   * 
   * @return
   * @throws Exception
   */
  public JsonNode list() throws Exception {
    String signatureStr = this.publicKey +
        this.privateKey;
    String signature = Helper.md5(signatureStr).toLowerCase();

    HashMap<String, Object> data = new HashMap<String, Object>();
    data.put("public_key", this.publicKey);
    data.put("signature", signature);
    JsonNode res;
    try {
      res = this.client.doGet("address/list", data);
    } catch (Exception err) {
      throw err;
    }
    return res;
  }

  /**
   * 收款地址添加
   * https://doc.tronapi.com/api/address/add.html
   * 
   * @param address    收款地址
   * @return JsonNode
   * @throws Exception
   */
  public JsonNode add(
      String address) throws Exception {

    String signatureStr = 
        this.publicKey +
        address + 
        this.privateKey;
    String signature = Helper.md5(signatureStr).toLowerCase();

    HashMap<String, Object> data = new HashMap<String, Object>();
    data.put("address", address);
    data.put("public_key", publicKey);
    data.put("signature", signature);

    JsonNode res;
    try {
      res = this.client.doPost("address/add", data);
    } catch (Exception err) {
      throw err;
    }
    return res;
  }

  /**
   * 收款地址生成
   * https://doc.tronapi.com/api/address/generate.html
   * 
   * @return
   * @throws Exception
   */
  public JsonNode generate() throws Exception {
    String signatureStr = this.publicKey +
        this.privateKey;
    String signature = Helper.md5(signatureStr).toLowerCase();

    HashMap<String, Object> data = new HashMap<String, Object>();
    data.put("public_key", this.publicKey);
    data.put("signature", signature);
    JsonNode res;
    try {
      res = this.client.doPost("address/generate", data);
    } catch (Exception err) {
      throw err;
    }
    return res;
  }

  /**
   * 收款地址生成 & 替换
   * https://doc.tronapi.com/api/address/generate_add.html
   * 
   * @return
   * @throws Exception
   */
  public JsonNode generate_add() throws Exception {
    String signatureStr = this.publicKey +
        this.privateKey;
    String signature = Helper.md5(signatureStr).toLowerCase();

    HashMap<String, Object> data = new HashMap<String, Object>();
    data.put("public_key", this.publicKey);
    data.put("signature", signature);
    JsonNode res;
    try {
      res = this.client.doPost("address/generate_add", data);
    } catch (Exception err) {
      throw err;
    }
    return res;
  }
}

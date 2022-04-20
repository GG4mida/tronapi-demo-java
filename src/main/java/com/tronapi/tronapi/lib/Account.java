package com.tronapi.tronapi.lib;

import java.util.HashMap;
import com.fasterxml.jackson.databind.JsonNode;

public class Account {

  private Client client = null;
  private String publicKey = "";
  private String privateKey = "";

  public Account(Client client, String publicKey, String privateKey) {
    this.client = client;
    this.publicKey = publicKey;
    this.privateKey = privateKey;
  }

  /**
   * 账户余额查询
   * https://doc.tronapi.com/api/account/balance.html
   * 
   * @return
   * @throws Exception
   */
  public JsonNode balance() throws Exception {
    String signatureStr = this.publicKey +
        this.privateKey;
    String signature = Helper.md5(signatureStr).toLowerCase();

    HashMap<String, Object> data = new HashMap<String, Object>();
    data.put("public_key", this.publicKey);
    data.put("signature", signature);
    JsonNode res;
    try {
      res = this.client.doGet("account/balance", data);
    } catch (Exception err) {
      throw err;
    }
    return res;
  }
}

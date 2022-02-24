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
   * 账户查询
   * https://doc.tronapi.com/api/wallet/query.html
   * 
   * @return
   * @throws Exception
   */
  public JsonNode query() throws Exception {
    String signatureStr = this.publicKey +
        this.privateKey;
    String signature = Helper.md5(signatureStr).toLowerCase();

    HashMap<String, Object> data = new HashMap<String, Object>();
    data.put("public_key", this.publicKey);
    data.put("signature", signature);
    JsonNode res;
    try {
      res = this.client.doGet("wallet/query", data);
    } catch (Exception err) {
      throw err;
    }
    return res;
  }

  /**
   * 提现申请
   * https://doc.tronapi.com/api/wallet/withdrawal_create.html
   * 
   * @param amount    提现金额
   * @param coinCode  提现币种
   * @param address   提现地址
   * @param notifyUrl 订单异步通知地址，可留空
   * @return JsonNode
   * @throws Exception
   */
  public JsonNode withdrawal(
      String amount,
      String coinCode,
      String address,
      String notifyUrl) throws Exception {

    String signatureStr = coinCode +
        amount +
        address +
        notifyUrl +
        this.publicKey +
        this.privateKey;
    String signature = Helper.md5(signatureStr).toLowerCase();

    HashMap<String, Object> data = new HashMap<String, Object>();
    data.put("amount", amount);
    data.put("coin_code", coinCode);
    data.put("address", address);
    data.put("notify_url", notifyUrl);
    data.put("public_key", publicKey);
    data.put("signature", signature);

    JsonNode res;
    try {
      res = this.client.doPost("wallet/withdrawal", data);
    } catch (Exception err) {
      throw err;
    }
    return res;
  }

  /**
   * 提现查询
   * https://doc.tronapi.com/api/wallet/withdrawal_query.html
   * 
   * @param token 提现 token
   * @return JsonNode
   * @throws Exception
   */
  public JsonNode withdrawal_query(String token) throws Exception {
    HashMap<String, Object> data = new HashMap<String, Object>();
    data.put("token", token);
    JsonNode res;
    try {
      res = this.client.doGet("wallet/withdrawal/query", data);
    } catch (Exception err) {
      throw err;
    }
    return res;
  }
}

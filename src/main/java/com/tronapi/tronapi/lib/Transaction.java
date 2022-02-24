package com.tronapi.tronapi.lib;

import java.util.HashMap;
import com.fasterxml.jackson.databind.JsonNode;

public class Transaction {

  private Client client = null;
  private String publicKey = "";
  private String privateKey = "";

  public Transaction(Client client, String publicKey, String privateKey) {
    this.client = client;
    this.publicKey = publicKey;
    this.privateKey = privateKey;
  }

  /**
   * 订单创建
   * https://doc.tronapi.com/api/transaction/create.html
   * 
   * @param amount      订单金额
   * @param currency    订单币种
   * @param coinCode    支付币种
   * @param orderId     商户订单号
   * @param productName 商户产品名称，可留空
   * @param customerId  商户用户编号，可留空
   * @param notifyUrl   订单异步通知地址，可留空
   * @param redirectUrl 订单同步跳转地址，可留空
   * @return JsonNode
   * @throws Exception
   */
  public JsonNode create(
      String amount,
      String currency,
      String coinCode,
      String orderId,
      String productName,
      String customerId,
      String notifyUrl,
      String redirectUrl) throws Exception {

    String signatureStr = amount +
        currency +
        coinCode +
        orderId +
        productName +
        customerId +
        notifyUrl +
        redirectUrl +
        this.publicKey +
        this.privateKey;
    String signature = Helper.md5(signatureStr).toLowerCase();

    HashMap<String, Object> data = new HashMap<String, Object>();
    data.put("amount", amount);
    data.put("currency", currency);
    data.put("coin_code", coinCode);
    data.put("order_id", orderId);
    data.put("product_name", productName);
    data.put("customer_id", customerId);
    data.put("notify_url", notifyUrl);
    data.put("redirect_url", redirectUrl);
    data.put("public_key", publicKey);
    data.put("signature", signature);

    JsonNode res;
    try {
      res = this.client.doPost("transaction/create", data);
    } catch (Exception err) {
      throw err;
    }
    return res;
  }

  /**
   * 订单查询
   * https://doc.tronapi.com/api/transaction/query.html
   * 
   * @param token 订单 token
   * @return
   * @throws Exception
   */
  public JsonNode query(String token) throws Exception {
    HashMap<String, Object> data = new HashMap<String, Object>();
    data.put("token", token);
    JsonNode res;
    try {
      res = this.client.doGet("transaction/query", data);
    } catch (Exception err) {
      throw err;
    }
    return res;
  }
}

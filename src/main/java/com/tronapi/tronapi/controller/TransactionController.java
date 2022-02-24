package com.tronapi.tronapi.controller;

import com.tronapi.tronapi.bean.Tronapi;
import com.tronapi.tronapi.lib.Client;
import com.tronapi.tronapi.lib.Helper;

import java.util.Arrays;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	Tronapi config;

	/**
	 * 订单创建
	 * https://doc.tronapi.com/api/transaction/create.html
	 * 
	 * @return
	 */
	@RequestMapping("/create")
	public JsonNode create() {
		JsonNode res = null;
		try {
			Client client = this.getClient();

			String amount = "100";
			String currency = "CNY";
			String coinCode = "FAU";
			String orderId = Helper.getRandomStr(8);
			String productName = "product name";
			String customerId = "customer id";
			String notifyUrl = "";
			String redirectUrl = "";

			res = client.transaction.create(amount, currency, coinCode, orderId, productName, customerId, notifyUrl,
					redirectUrl);

			Boolean resSucccess = res.get("success").asBoolean();
			if (resSucccess == true) {

				JsonNode resData = res.get("data");

				String transToken = resData.get("token").asText();
				String transAmount = resData.get("amount").asText();
				String transCurrency = resData.get("currency").asText();
				String transCoinCode = resData.get("coin_code").asText();
				String transCoinAmount = resData.get("coin_amount").asText();
				String transCoinAddress = resData.get("coin_address").asText();
				String transQrcodeUrl = resData.get("qrcode_url").asText();
				String transCashierUrl = resData.get("cashier_url").asText();
				int transTimeout = resData.get("timeout").asInt();
				String transSignature = resData.get("signature").asText();

				String signatureStr = transToken +
						transAmount +
						transCurrency +
						transCoinCode +
						transCoinAmount +
						transCoinAddress +
						transTimeout +
						transCashierUrl +
						transQrcodeUrl +
						this.config.getPrivateKey();

				String signatureThis = Helper.md5(signatureStr).toLowerCase();

				if (transSignature.equals(signatureThis)) {
					System.out.println("Congratulations! transaction is ok.");
				} else {
					System.out.println("Careful! transaction maybe hacked.");
				}
			} else {
				String resData = res.get("data").asText();
				System.out.println("Transaction create failed:" + resData);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * 订单查询
	 * https://doc.tronapi.com/api/transaction/query.html
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	public JsonNode query() {
		JsonNode res = null;
		try {
			Client client = this.getClient();
			String token = "efc5a3e9926a44058e2d347b5c17f315";
			res = client.transaction.query(token);

			Boolean resSucccess = res.get("success").asBoolean();
			if (resSucccess == true) {
				JsonNode resData = res.get("data");

				String transStatus = resData.get("status").asText();
				String transMessage = resData.get("message").asText();

				System.out.println("trans status:" + transStatus);
				System.out.println("trans message:" + transMessage);

				// 订单状态参考：https://doc.tronapi.com/api/intro/constant.html
				String[] transResolvedStatus = { "NORMAL_DONE", "MANUAL_DONE", "PARTIAL_DONE", "OVER_DONE",
						"PARTIAL_OVER_DONE" };

				if (Arrays.asList(transResolvedStatus).contains(transStatus)) {
					System.out.println("Transaction has done");
				} else {
					System.out.println("Transaction:" + transMessage);
				}
			} else {
				String resData = res.get("data").asText();
				System.out.println("Transaction query failed:" + resData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	private Client getClient() throws Exception {
		return new Client(this.config.getPublicKey(), this.config.getPrivateKey());
	}
}

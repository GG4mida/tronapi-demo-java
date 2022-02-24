package com.tronapi.tronapi.controller;

import com.tronapi.tronapi.bean.Tronapi;
import com.tronapi.tronapi.lib.Client;
import com.tronapi.tronapi.lib.Helper;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	Tronapi config;

	/**
	 * 账户查询
	 * https://doc.tronapi.com/api/wallet/query.html
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	public JsonNode query() {
		JsonNode res = null;
		try {
			Client client = this.getClient();
			res = client.account.query();
			Boolean resSucccess = res.get("success").asBoolean();
			if (resSucccess == true) {
				JsonNode resData = res.get("data");
				System.out.println("account query success:" + resData);
			} else {
				String resData = res.get("data").asText();
				System.out.println("account query failed:" + resData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 提现申请
	 * https://doc.tronapi.com/api/wallet/withdrawal_create.html
	 * 
	 * @return
	 */
	@RequestMapping("/withdrawal")
	public JsonNode withdrawal() {
		JsonNode res = null;
		try {
			Client client = this.getClient();

			String amount = "200";
			String coinCode = "FAU";
			String address = "your withdrawal address";
			String notifyUrl = "your withdrawal notify url";

			res = client.account.withdrawal(amount, coinCode, address, notifyUrl);
			Boolean resSucccess = res.get("success").asBoolean();
			if (resSucccess == true) {
				JsonNode resData = res.get("data");

				String withdrawalToken = resData.get("token").asText();
				String withdrawalCoinCode = resData.get("coin_code").asText();
				String withdrawalAmountSubmit = resData.get("amount_submit").asText();
				String withdrawalAmountFee = resData.get("amount_fee").asText();
				String withdrawalAmountSend = resData.get("amount_send").asText();
				String withdrawalAddress = resData.get("address").asText();
				String withdrawalRate = resData.get("rate").asText();
				String withdrawalSignature = resData.get("signature").asText();

				String signatureStr = withdrawalToken +
						withdrawalCoinCode +
						withdrawalAmountSubmit +
						withdrawalAmountFee +
						withdrawalAmountSend +
						withdrawalAddress +
						withdrawalRate +
						this.config.getPrivateKey();

				String signatureThis = Helper.md5(signatureStr).toLowerCase();

				if (withdrawalSignature.equals(signatureThis)) {
					System.out.println("Congratulations! withdrawal is ok.");
				} else {
					System.out.println("Careful! withdrawal maybe hacked.");
				}
			} else {
				String resData = res.get("data").asText();
				System.out.println("Withdrawal create failed:" + resData);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 提现查询
	 * https://doc.tronapi.com/api/wallet/withdrawal_query.html
	 * 
	 * @return
	 */
	@RequestMapping("/withdrawal_query")
	public JsonNode withdrawal_query() {
		JsonNode res = null;
		try {
			Client client = new Client(this.config.getPublicKey(), this.config.getPrivateKey());
			String token = "your withdrawal token";
			res = client.account.withdrawal_query(token);
			Boolean resSucccess = res.get("success").asBoolean();
			if (resSucccess == true) {
				Boolean resData = res.get("data").asBoolean();
				if (resData == true) {
					System.out.println("withdrawal has been done.");
				} else {
					System.out.println("withdrawal is processing.");
				}
			} else {
				String resData = res.get("data").asText();
				System.out.println("withdrawal query failed:" + resData);
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

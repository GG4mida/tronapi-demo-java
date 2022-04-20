package com.tronapi.tronapi.controller;

import com.tronapi.tronapi.bean.Tronapi;
import com.tronapi.tronapi.lib.Client;

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
	 * 账户余额查询
	 * https://doc.tronapi.com/api/account/balance.html
	 * 
	 * @return
	 */
	@RequestMapping("/balance")
	public JsonNode balance() {
		JsonNode res = null;
		try {
			Client client = this.getClient();
			res = client.account.balance();
			
			// do your logic
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	private Client getClient() throws Exception {
		return new Client(this.config.getPublicKey(), this.config.getPrivateKey());
	}
}

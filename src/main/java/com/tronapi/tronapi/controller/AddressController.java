package com.tronapi.tronapi.controller;

import com.tronapi.tronapi.bean.Tronapi;
import com.tronapi.tronapi.lib.Client;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	Tronapi config;

	/**
	 * 收款地址查询
	 * https://doc.tronapi.com/api/address/list.html
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public JsonNode list() {
		JsonNode res = null;
		try {
			Client client = this.getClient();
			res = client.address.list();

			// todo: do your logic
      
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 收款地址添加
	 * https://doc.tronapi.com/api/address/add.html
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	public JsonNode add() {
		JsonNode res = null;
		try {
			Client client = this.getClient();
			String address = "TGeztTUmWpcgHp4BCs1j6fdpa3dsqX8gJD";
			res = client.address.add(address);

      // todo: do your logic

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

  /**
	 * 收款地址生成
	 * https://doc.tronapi.com/api/address/generate.html
	 * 
	 * @return
	 */
	@RequestMapping("/generate")
	public JsonNode generate() {
		JsonNode res = null;
		try {
			Client client = this.getClient();
			res = client.address.generate();
			
      // todo: do your logic

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

  /**
	 * 收款地址生成 & 替换
	 * https://doc.tronapi.com/api/address/generate_add.html
	 * 
	 * @return
	 */
	@RequestMapping("/generate_add")
	public JsonNode generate_add() {
		JsonNode res = null;
		try {
			Client client = this.getClient();
			res = client.address.generate_add();
			
      // todo: do your logic

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	private Client getClient() throws Exception {
		return new Client(this.config.getPublicKey(), this.config.getPrivateKey());
	}
}

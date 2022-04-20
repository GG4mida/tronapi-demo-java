package com.tronapi.tronapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@RequestMapping("/")
	public String index() {
		return "Hello tronapi. Please see the TransactionController.java & AddressController.java & AccountController.java";
	}
}

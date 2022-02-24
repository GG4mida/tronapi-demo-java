package com.tronapi.tronapi.lib;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Helper {

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str 字符串
	 * @return 是否为空
	 */
	public static Boolean isStrEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 获取指定长度随机字符串
	 * 
	 * @param len 字符串长度
	 * @return 随机字符串
	 */
	public static String getRandomStr(int len) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			int number = random.nextInt(62);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * md5
	 * 
	 * @param plainText 原始字符串
	 * @return md5 结果
	 */
	public static String md5(String plainText) {
		byte[] secretBytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			secretBytes = md.digest();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("no md5 module find.");
		}
		String md5code = new BigInteger(1, secretBytes).toString(16);
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}
}

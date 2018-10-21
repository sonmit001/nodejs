package com.robo.web.vo;

import java.security.PrivateKey;

public class RsaVo {
	private PrivateKey privateKey;
	private String publicKeyModulus;
	private String publicKeyExponent;
	
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}
	public String getPublicKeyModulus() {
		return publicKeyModulus;
	}
	public void setPublicKeyModulus(String publicKeyModulus) {
		this.publicKeyModulus = publicKeyModulus;
	}
	public String getPublicKeyExponent() {
		return publicKeyExponent;
	}
	public void setPublicKeyExponent(String publicKeyExponent) {
		this.publicKeyExponent = publicKeyExponent;
	}
}

package com.robo.web.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import com.robo.web.vo.RsaVo;



public class EncryptionUtil {
	private static final Logger logger = LoggerFactory.getLogger(EncryptionUtil.class);
	
	private static final String secretKey	= "EMONEYVISION2015";
	
	public static String encodeAESCore(String data, String key) throws Exception {
		byte[] keyData = key.getBytes();
		SecretKey secureKey = new SecretKeySpec(keyData, "AES");
		
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(keyData));
		
		return Base64Utils.encodeToString(c.doFinal(data.getBytes()));
	}
	
	public static String encodeAES(String data) throws Exception {
		return URLEncoder.encode(encodeAESCore(data, "EMONEYVISION2015"), "utf-8");
	}
	
	public static String encodeAESAndUrl(String data) throws Exception {
		return URLEncoder.encode(encodeAESCore(data, "THINKPOOL&EMONEY"), "euc-kr");
	}	
	
	public static String encodeAESLiveSolution(String data) throws Exception {
		return URLEncoder.encode(encodeAESCore(data, "LIVESOLUTION2015"), "euc-kr");
	}
	
	public static String encodeAESMobileLiveSolution(String data, String key) throws Exception {
		//1. 암호화 재생 url에 슬러시(/) 미인식 이슈로 인해 replace 로직 추가
		return URLEncoder.encode(encodeAESCore(data, key).replaceAll("\\/", "\\|"), "utf-8");
	}
	
	public static String decodeAESCore(String data, String key) throws Exception {
		byte[] keyData = key.getBytes();
		SecretKey secureKey = new SecretKeySpec(keyData, "AES");
		
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(keyData));
		
		return new String(c.doFinal(Base64Utils.decodeFromString(data)));
	}
	
	public static String decodeAES(String data) throws Exception {
		return decodeAESCore(URLDecoder.decode(data, "utf-8"), "EMONEYVISION2015");
	}
	
	public static String decodeAESAndUrl(String data) throws Exception {
		return  decodeAESCore(URLDecoder.decode(data, "euc-kr"), "THINKPOOL&EMONEY");
	}
	
	public static String decodeAESLiveSolutionl(String data) throws Exception {
		return  decodeAESCore(URLDecoder.decode(data, "euc-kr"), "LIVESOLUTION2015");
	}
	public static byte[] hexToByteArray(String hex) {
		if( hex == null || hex.length() % 2 != 0 ) {
			return new byte[]{};
		}
		
		byte[] bytes = new byte[hex.length() / 2];
		for( int i = 0; i < hex.length(); i += 2 ) {
		    byte value = (byte)Integer.parseInt(hex.substring(i, i + 2), 16);
		    bytes[(int) Math.floor(i / 2)] = value;
		}
		
		return bytes;
	}
	public static RsaVo getRsaKey() {
		
		RsaVo vo = null;
		
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(1024);
			
			KeyPair keyPair = generator.genKeyPair();
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			
			PublicKey publicKey = keyPair.getPublic();
			PrivateKey privateKey = keyPair.getPrivate();
			
			// 공개키를 문자열로 변환하여 JavaScript RSA 라이브러리 넘겨준다.
			RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
			
			String publicKeyModulus = publicSpec.getModulus().toString(16);
			String publicKeyExponent = publicSpec.getPublicExponent().toString(16);
			
			vo = new RsaVo();
			vo.setPrivateKey(privateKey);
			vo.setPublicKeyExponent(publicKeyExponent);
			vo.setPublicKeyModulus(publicKeyModulus);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return vo;
	}
	
	public static String decodeRsa(PrivateKey privateKey, String securedValue) throws Exception {
		
		String decryptedValue = "";
		
		Cipher cipher = Cipher.getInstance("RSA");
        byte[] encryptedBytes = hexToByteArray(securedValue);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        decryptedValue = new String(decryptedBytes); // 문자 인코딩 주의.
        
        return decryptedValue;
    }
	
	public static String encodeSha256(String str) {
		return DigestUtils.sha256Hex(str);
	}
	
	public static String encodeBase64(String str) {
		return Base64Utils.encodeToString(str.getBytes());
	}
	
	public static String decodeBase64(String str) {
		return new String(Base64Utils.decodeFromString(str));
	}
	
	public static String encode_datasource(String str) throws Exception {
		return encodeAESCore(str, secretKey);
	}
	
	public static String decode_datasource(String str) throws Exception {
		return decodeAESCore(str, secretKey);
	}
}

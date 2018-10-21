package com.robo.web.vo;

import java.security.PrivateKey;

import javax.servlet.http.HttpServletRequest;

import com.robo.web.utils.EncryptionUtil;

public class UserInfo {
	
	private int accnt_id;
	private String nickname;
	private String name;
	private String user_type;
	private String phone;
	private String id;
	private String s_passwd;
	
	public int getAccnt_id() {
		return accnt_id;
	}
	public void setAccnt_id(int accnt_id) {
		this.accnt_id = accnt_id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getS_passwd() {
		return s_passwd;
	}
	public void setS_passwd(String s_passwd) {
		this.s_passwd = s_passwd;
	}
	
	@Override
	public String toString() {
		return "UserInfo [accnt_id=" + accnt_id + ", nickname=" + nickname + ", name=" + name + ", user_type="
				+ user_type + ", phone=" + phone + ", id=" + id + ", s_passwd=" + s_passwd + "]";
	}
	
	public void transJoinMember(HttpServletRequest req) throws Exception {
		PrivateKey pk = (PrivateKey) req.getSession().getAttribute("privateKey");

		if( id != null && !"".equals(id) ) {
			id = EncryptionUtil.decodeRsa(pk, id);			
		}
		if( nickname != null && !"".equals(nickname) ) {
			nickname = EncryptionUtil.decodeRsa(pk, nickname);			
		}
		if( s_passwd != null && !"".equals(s_passwd) ) {
			s_passwd = EncryptionUtil.decodeRsa(pk, s_passwd);
			s_passwd = EncryptionUtil.encodeSha256(s_passwd);			
		}
		if( name != null && !"".equals(name) ) {
			name = EncryptionUtil.decodeRsa(pk, name);			
		}
		if( phone != null && !"".equals(phone) ) {
			phone = EncryptionUtil.decodeRsa(pk, phone);			
		}
	}
	//로그인 시 비번만
	public void transPasswd(HttpServletRequest request) throws Exception {
		s_passwd = EncryptionUtil.decodeRsa((PrivateKey)request.getSession().getAttribute("privateKey"), s_passwd);
		s_passwd = EncryptionUtil.encodeSha256(s_passwd);
	}
	

}

package com.robo.web.dao;

import com.robo.web.vo.UserInfo;

public interface AccountDaoMapper {

	public int chkId(String chkId);

	public int getAccntId();

	public int joinMember(UserInfo userinfo);
	
	public UserInfo getUserInfo(int accnt_id);

	public UserInfo loginMember(UserInfo userinfo);

	public int chkNicName(String nickname);
}

package com.robo.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robo.web.dao.AccountDaoMapper;
import com.robo.web.vo.UserInfo;
@Service
public class AccountImpl implements Account {

	@Autowired
	private AccountDaoMapper acctdao;
	
	@Override
	public int chkId(String chkId) {
		return acctdao.chkId(chkId);
	}

	@Override
	public int joinMember(UserInfo userinfo) {
		int accntid = acctdao.getAccntId();
		userinfo.setAccnt_id(accntid);
		if(acctdao.joinMember(userinfo) == 1){
			return accntid;
		}
		return 0;
	}

	@Override
	public UserInfo getUserInfo(int accnt_id) {
		return acctdao.getUserInfo(accnt_id);
	}

	@Override
	public UserInfo loginMember(UserInfo userinfo) {
		UserInfo ui = acctdao.loginMember(userinfo);
		return ui;
	}

	@Override
	public int chkNicName(String nickname) {
		return acctdao.chkNicName(nickname);
	}

}

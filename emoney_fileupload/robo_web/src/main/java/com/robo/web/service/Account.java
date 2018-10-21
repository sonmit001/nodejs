package com.robo.web.service;

import com.robo.web.vo.UserInfo;

public interface Account {
	
	//id 중복 체크
		public int chkId(String chkId);
		//회원가입
		public int joinMember(UserInfo userinfo);
		//회원 정보 가져오기
		public UserInfo getUserInfo(int accnt_id);
		//로그인
		public UserInfo loginMember(UserInfo userinfo);
		//닉네임 중복체크
		public int chkNicName(String nickname);

}

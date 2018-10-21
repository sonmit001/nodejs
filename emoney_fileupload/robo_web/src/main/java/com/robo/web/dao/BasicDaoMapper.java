package com.robo.web.dao;

import java.util.HashMap;
import java.util.List;

import com.robo.web.vo.UserInfo;


public interface BasicDaoMapper {
	
	public List<HashMap<String, String>> getMemberList(String name);

}

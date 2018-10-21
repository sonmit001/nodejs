package com.robo.web.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robo.web.dao.BasicDaoMapper;
import com.robo.web.vo.UserInfo;

@Service
public class BasicImpl implements Basic {

	@Autowired
	private BasicDaoMapper bdao;

	@Override
	public List<HashMap<String, String>> getMemberList(String name) {
		return bdao.getMemberList(name);
	}


}

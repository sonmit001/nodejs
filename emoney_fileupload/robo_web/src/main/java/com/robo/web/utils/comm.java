package com.robo.web.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.robo.web.vo.UserInfo;

public class comm {

	public static String currentDate(String form){
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat(form);
		
		return format.format(date);
	}
	
	public static void loginProcess(HttpServletRequest req, HttpServletResponse res, UserInfo ui){
		req.getSession().setAttribute("ui", ui);
	}
	
	public static void logoutProcess(HttpServletRequest req, HttpServletResponse res, String name){
		req.getSession().removeAttribute(name);
		req.getSession().invalidate();
	}
	
	public static UserInfo getUser(HttpServletRequest req){
		if(req.getSession().getAttribute("ui") != null){
			return (UserInfo) req.getSession().getAttribute("ui");
		}
		return null;
	}
}

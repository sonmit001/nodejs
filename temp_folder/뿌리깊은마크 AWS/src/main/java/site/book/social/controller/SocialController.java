/*
 * @Project : DeepRoot
 * @FileName : TopService.java
 * @Date : 2018. 6. 14.
 * @Author : 김희준
 */

package site.book.social.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import site.book.admin.dto.NoticeDTO;
import site.book.admin.service.NoticeService;
import site.book.social.dto.TopDTO;
import site.book.social.service.SurfingService;
import site.book.social.service.TopService;
import site.book.team.dto.G_BookDTO;
import site.book.team.dto.G_MyAlarmDTO;
import site.book.team.dto.S_TeamDTO;
import site.book.team.dto.TeamDTO;
import site.book.team.service.G_AlarmService;
import site.book.team.service.G_BookService;
import site.book.team.service.TeamService;
import site.book.user.dto.S_U_BookDTO;
import site.book.user.dto.U_BookDTO;
import site.book.user.service.U_BookService;

/**
 * @Class : SocialController.java
 * @Date : 2018. 6. 14.
 * @Author : 김희준 , 정민재 , 정진수
 */
@Controller
@RequestMapping("/social/")
public class SocialController {
	/* 태웅 파라미터 */
	@Autowired
	G_AlarmService galarmservice;
	
	/* 민재 파라미터 */
	@Autowired
	private TopService top_service;
	
	@Autowired
    private View jsonview;
	
	
	/* 진수햄 파라미터 */
	@Autowired
	private U_BookService u_bookservice;
	@Autowired
	private TeamService teamservice;
	@Autowired
	private SurfingService surfingservice;
	
	/* 희준 파라미터 */
	@Autowired
	private NoticeService notice_service;
	
	/* 민재 & 진수 함수 */
	@RequestMapping("social.do")
	public String social(HttpServletRequest req, Model model) {
		
		List<TopDTO> u_top5 = top_service.getUTop5();
		model.addAttribute("u_top5", u_top5);
		
		List<TopDTO> g_top5 = top_service.getGTop5();
		model.addAttribute("g_top5", g_top5);
		
		/*u_booklist start*/
		List<S_U_BookDTO> u_list= u_bookservice.getSocialBookmarkList();
		model.addAttribute("u_list",u_list);
		
		List<S_TeamDTO> g_list=teamservice.getSocialGroupList();
		model.addAttribute("g_list", g_list);
		
		HttpSession session = req.getSession();
		String uid = (String)session.getAttribute("info_userid");
		
		if(uid != null) {
			List<TeamDTO> headerTeamList = teamservice.getTeamList(uid);
			model.addAttribute("headerTeamList", headerTeamList);
		}
		
		// 그룹 초대/강퇴/완료 알람  쪽지 리스트
		List<G_MyAlarmDTO> headerAlarmList = galarmservice.getAlarmList(uid);
		model.addAttribute("headerAlarmList", headerAlarmList);
		
		List<NoticeDTO> headerNoticeList = notice_service.getNotices();
		model.addAttribute("headerNoticeList", headerNoticeList);
		/*u_booklist end*/
		
		return "social.social";
	}
	
	/*진수 해당 회원 북마크 가져오기 start*/
	//해당 유저의 카테고리를 보내준다.
	@RequestMapping("getCategoryList.do")	
	public void getCategoryList(HttpServletRequest req , HttpServletResponse res, String nname) {
		
		res.setCharacterEncoding("UTF-8");
		
		HttpSession session = req.getSession();
        
		JSONArray jsonArray = new JSONArray();	
		List<U_BookDTO> list = surfingservice.getCategoryList(nname);
		HashMap<String, String> href = new HashMap();

		for(int i =0;i<list.size();i++) {
			
			JSONObject jsonobject = new JSONObject();
			
			String parentid = String.valueOf(list.get(i).getPid());
			
			if(parentid.equals("0") || parentid.equals(""))
				jsonobject.put("parent", "#");
			else
				jsonobject.put("parent", parentid);
			
			if(list.get(i).getUrl() == null)
				jsonobject.put("icon", "fa fa-folder");	//favicon 추가
			else {
				jsonobject.put("icon", "https://www.google.com/s2/favicons?domain="+list.get(i).getUrl());	//favicon 추가
			}
			href.put("href", list.get(i).getUrl());
			jsonobject.put("id", list.get(i).getUbid());
			jsonobject.put("text", list.get(i).getUrlname());
			jsonobject.put("nname",nname);
			jsonobject.put("sname", list.get(i).getSname());
			jsonobject.put("htag", list.get(i).getHtag());
			jsonobject.put("a_attr", href);
			jsonArray.put(jsonobject);
			
		}
		
		try {
			res.getWriter().println(jsonArray);
		}catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
	/*해당 회원 북마크 가져오기 end*/
	
	// 민재 , 개인 공유 북마크 내 카테고리로 가져가기
	@RequestMapping("getmybookmark.do")
	public View getSharemark(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
        String uid = (String)session.getAttribute("info_userid");
        
        JSONArray jarr = new JSONArray();
		jarr = new JSONArray(req.getParameter("obj"));
        
        int result = u_bookservice.insertUrlFromCompletedGroup(jarr,uid);
		if(result > 0) {
			model.addAttribute("result", "success");
		}else {
			model.addAttribute("result", "fail");
		}
		
		return jsonview;
	}
	
	// 민재 , 개인 공유 북마크 내 그룹으로 가져가기
	@RequestMapping("getGroupBook.do")	
	public View addGroupBookmark(HttpServletRequest req, Model model, G_BookDTO g_book) {
        
		HttpSession session = req.getSession();
        String uid = (String)session.getAttribute("info_userid");
        g_book.setUid(uid);
        System.out.println(g_book);
        
        int result = surfingservice.insertGroupBookmark(g_book);
        
		if(result > 0) {
			model.addAttribute("result", "success");
		}else {
			model.addAttribute("result", "fail");
		}
		
		return jsonview;
	}
	
	// 진수, 개인 공유 북마크&파도타기 북마크 배열에 담아 내 그룹으로 가져가기
	@RequestMapping("getGroupBookList.do")	
	public View addGroupBookmarkList(HttpServletRequest req, Model model) {
        
		HttpSession session = req.getSession();
        String uid = (String)session.getAttribute("info_userid");
        
        JSONArray jarr = new JSONArray();
		jarr = new JSONArray(req.getParameter("obj"));
        
        int result = surfingservice.insertGroupBookmarkList(jarr, uid);
        
		if(result > 0) {
			model.addAttribute("result", "success");
		}else {
			model.addAttribute("result", "fail");
		}
		
		return jsonview;
	}
	
	// 진수, 소셜 조회수 증가
	@RequestMapping("indiViewCount.do")	
	public View updateViewCount(String ubid, Model model) {
        
        int result = u_bookservice.updateViewCount(Integer.parseInt(ubid));
        
        if(result > 0) {
			model.addAttribute("result", "success");
		}else {
			model.addAttribute("result", "fail");
		}
        
		return jsonview;
	}
	
	// 진수, 그룹 조회수 증가
	@RequestMapping("groupViewCount.do")	
	public View updateGroupViewCount(String gid, Model model) {
        
        int result = teamservice.updateGroupViewCount(Integer.parseInt(gid));
        
        if(result > 0) {
			model.addAttribute("result", "success");
		}else {
			model.addAttribute("result", "fail");
		}
        
		return jsonview;
	}
	
}

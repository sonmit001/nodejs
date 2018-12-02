package site.book.user.controller;

/**
 * @Class : SocialController.java
 * @Date : 2018. 6. 6.
 * @Author : 김명수, 김희준, 김태웅
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import site.book.admin.dto.NoticeDTO;
import site.book.admin.service.NoticeService;
import site.book.team.dto.G_AlarmDTO;
import site.book.team.dto.G_BookDTO;
import site.book.team.dto.G_MemberDTO;
import site.book.team.dto.G_MyAlarmDTO;
import site.book.team.dto.TeamDTO;
import site.book.team.service.G_AlarmService;
import site.book.team.service.G_BookService;
import site.book.team.service.G_MemberService;
import site.book.team.service.TeamService;
import site.book.user.dto.U_BookDTO;
import site.book.user.service.U_BookService;
import site.book.user.service.UserService;
//user/mycategory.do";
@Controller
@RequestMapping("/user/")
public class UserController {
	
	// 변수 Start
	
	// 태웅
	@Autowired
    private View jsonview;
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	G_AlarmService galarmservice;
	
	// 희준
	@Autowired
	private TeamService teamservice;
	
	@Autowired
	private G_MemberService g_memberservice;
	
	@Autowired
	private NoticeService notice_service;
	
	// 명수
	@Autowired
	U_BookService u_bookservice;
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	G_BookService g_bookservice;
	// 변수 End
	
	// 함수 Start
	
	// 태웅
	@RequestMapping(value="idcheck.do", method = RequestMethod.POST)
	public View userIdCheck(@RequestParam("uid") String uid, Model model) {
		//System.out.println(uid);
		int result = userservice.checkUserID(uid);

		String data = (result > 0) ? "fail" : "pass";
		model.addAttribute("result", data);
		
		return jsonview;
	}
	
	@RequestMapping(value="nnamecheck.do", method = RequestMethod.POST)
	public View userNnameCheck(@RequestParam("nname") String nname, Model model) {
		//System.out.println(nname);
		int result = userservice.checkUserNickname(nname);
		
		String data = (result > 0) ? "fail" : "pass";
		model.addAttribute("result", data);
	
		return jsonview;
	}
	
	// 공유 체크 하지 않은 URL 추가하기
	@RequestMapping("addtomybookmark.do")
	public View addUrlNotShare(U_BookDTO book ,HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
        String uid = (String)session.getAttribute("info_userid");
        book.setUid(uid);
        //System.out.println(book);
        
        int result = u_bookservice.addToMyBookmark(book);
		
		String data = (result > 0) ? "success" : "fail";
		model.addAttribute("result", data);
		
		return jsonview;
	}
	
	// 희준
	
	// 그룹 나가기
	@RequestMapping("leaveGroup.do")
	public View leaveGroup(HttpServletRequest req, G_MemberDTO member, Model model) {
		HttpSession session = req.getSession();
		String uid = (String)session.getAttribute("info_userid");
		member.setUid(uid);
		
		int row = g_memberservice.leaveGroup(member);
		
		String data = (row == 1) ? "성공" : "실패";
		model.addAttribute("data", data);
		
		return jsonview;
	}
	
	// 그룹 추가
	@RequestMapping("addGroup.do")
	public String addGroup(HttpServletRequest req, String gname) {
		//System.out.println("그룹 추가");
		HttpSession session = req.getSession();
		String uid = (String)session.getAttribute("info_userid");
		
		G_MemberDTO member = new G_MemberDTO();
		member.setUid(uid);
		member.setGrid(1);
		
		teamservice.addGroup(gname, member);
		
		return "redirect:mybookmark.do";
	}
	
	// 그룹 완료
	@RequestMapping("completedGroup.do")
	public View completedGroup(HttpServletRequest req, TeamDTO team, G_AlarmDTO alarm, Model model) {
		HttpSession session = req.getSession();
		String uid = (String)session.getAttribute("info_userid");
		alarm.setFromid(uid);
		
		TeamDTO completedGroup = teamservice.completedGroup(team, alarm);
		model.addAttribute("completedGroup", completedGroup);
		
		return jsonview;
	}
	
	// 공유 체크 하지 않은 URL 추가하기
	@RequestMapping("addUrlNotShare.do")
	public void addUrlNotShare(U_BookDTO dto ,HttpServletRequest req, HttpServletResponse res) {
		HttpSession session = req.getSession();
        String uid = (String)session.getAttribute("info_userid");
        
        dto.setUid(uid);
        
		int result = u_bookservice.addFolderOrUrl(dto);
		
		try {
			res.getWriter().println(result);
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	// URL 추가시 타이틀 가져오기
	@RequestMapping("preview.do")
	public View WebCrawling(String url, Model model) {
		Document doc;
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		String[] REQUIRED_META = new String[] { "og:title" };
		try {
			doc = Jsoup.connect(url).userAgent(
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36")
					.referrer("http://www.google.com").get();
			Elements ogElements = doc.select("meta[property^=og], meta[name^=og]");
			for (Element e : ogElements) {
				String target = e.hasAttr("property") ? "property" : "name";

				if (!result.containsKey(e.attr(target))) {
					result.put(e.attr(target), new ArrayList<String>());
				}
				result.get(e.attr(target)).add(e.attr("content"));
			}
			for (String s : REQUIRED_META) {
				if (!(result.containsKey(s) && result.get(s).size() > 0)) {
					if (s.equals(REQUIRED_META[0])) {
						result.put(REQUIRED_META[0], Arrays.asList(new String[] { doc.select("title").eq(0).text() }));
					}
				}
			}
			for (String s : result.keySet()) {
				model.addAttribute(s.substring(3), result.get(s));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonview;
	}
	
	
	
	// 명수
	@RequestMapping("mybookmark.do")
	public String mybookmark(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		String uid = (String)session.getAttribute("info_userid");
		
		List<TeamDTO> teamList = teamservice.getTeamList(uid);
		model.addAttribute("teamList", teamList);
		
		List<TeamDTO> completedTeamList = teamservice.getCompletedTeamList(uid);
		model.addAttribute("completedTeamList", completedTeamList);
		
		if(uid != null) {
			List<TeamDTO> headerTeamList = teamservice.getTeamList(uid);
			model.addAttribute("headerTeamList", headerTeamList);
		}
		
		// 그룹 초대/강퇴/완료 알람  쪽지 리스트
		List<G_MyAlarmDTO> headerAlarmList = galarmservice.getAlarmList(uid);
		model.addAttribute("headerAlarmList", headerAlarmList);
		
		List<NoticeDTO> headerNoticeList = notice_service.getNotices();
		model.addAttribute("headerNoticeList", headerNoticeList);
		
		return "mypage.myCategory";
	}
	
	//해당 유저의 카테고리를 보내준다.
	@RequestMapping("getCategoryList.do")	
	public void getCategoryList(HttpServletRequest req , HttpServletResponse res) {
		
		res.setCharacterEncoding("UTF-8");
		
		HttpSession session = req.getSession();
        String uid = (String)session.getAttribute("info_userid");
        
		JSONArray jsonArray = new JSONArray();	
		List<U_BookDTO> list = u_bookservice.getCategoryList(uid);
		
		if(list.size() ==0) {
			
			JSONObject jsonobject = new JSONObject();
			
			// 처음 가입자는 첫 카테고리를  생성해 준다.
			int ubid = u_bookservice.insertRootFolder(uid);
			
			//처음 가입한 유저일 경우 root폴더 생성해 준다.
				
			jsonobject.put("id", ubid);
			jsonobject.put("parent", "#");
			jsonobject.put("text", "첫 카테고리");
			jsonobject.put("icon", "fa fa-folder");
			jsonobject.put("uid", uid);
			jsonArray.put(jsonobject);
				
		}else {
			
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
				jsonobject.put("id", list.get(i).getUbid());
				jsonobject.put("text", list.get(i).getUrlname());
				jsonobject.put("uid",uid);
				jsonobject.put("sname", list.get(i).getSname());
				jsonobject.put("htag", list.get(i).getHtag());
				
				jsonArray.put(jsonobject);
				
			}
		}
		try {
			res.getWriter().println(jsonArray);
		}catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
	
	//해당 노드의 url 추출
	@RequestMapping("getUrl.do")
	public void getUrl(int ubid , HttpServletResponse res) {	
		
		res.setCharacterEncoding("UTF-8");
		
		List<U_BookDTO> list = u_bookservice.getUrl(ubid);
		//System.out.println(list);
		JSONArray jsonArray = new JSONArray();	
		HashMap<String, String> href = new HashMap();
		
		
		for(int i =0;i<list.size();i++) {
			
			JSONObject jsonobject = new JSONObject();

			href.put("href", list.get(i).getUrl());
			
			jsonobject.put("id", list.get(i).getUbid());
			jsonobject.put("parent", "#");
			jsonobject.put("text", list.get(i).getUrlname());
			jsonobject.put("icon", "https://www.google.com/s2/favicons?domain="+list.get(i).getUrl());	//favicon 추가
			
			String htag = String.valueOf(list.get(i).getHtag());
			
			// 공유 된 url 인지 아닌지 구분함
			if(htag.equals("") || htag.equals("null")) {
				jsonobject.put("sname", "#");
				jsonobject.put("htag", "#");
			}else {
				jsonobject.put("sname", list.get(i).getSname());
				jsonobject.put("htag", list.get(i).getHtag());
			}
				
			jsonobject.put("a_attr", href);
			
			jsonArray.put(jsonobject);
			
		}
		try {
			res.getWriter().println(jsonArray);
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	
	//urlname 수정
	@RequestMapping("updateNodeText.do")	
	public View updateNodeText(@RequestParam HashMap<String, String> param , Model model ) {
		
		int result = u_bookservice.updateNodeText(param);
		model.addAttribute("result",result);
		
		return jsonview;
	}
	
	//폴더 & url & 공유일 경우 공유로 추가
	@RequestMapping("addFolderOrUrl.do")
	public View addFolder(U_BookDTO dto ,HttpServletRequest req, Model model ) {
		
		HttpSession session = req.getSession();
        String uid = (String)session.getAttribute("info_userid");
        
        dto.setUid(uid);
		int result = u_bookservice.addFolderOrUrl(dto);
		model.addAttribute("ubid", result);
		
		return jsonview;
	}
	
	//url 혹은 폴더 삭제
	@RequestMapping("deleteNode.do")	
	public View deleNode(HttpServletRequest req , Model model ) {

		String nodeid = req.getParameter("node");
		int result = u_bookservice.deleteFolderOrUrl(nodeid);
		model.addAttribute("result",result);
		
		return jsonview;
	}
	
	//url update
	@RequestMapping("editUrl.do")	
	public View editUrl(U_BookDTO dto , Model model) {
		
		int result = u_bookservice.editUrl(dto);
		model.addAttribute("result",result);
		
		return jsonview;
	}
	
	//드래그 드랍 했을 경우 부모 id 바꾸기
	@RequestMapping("dropNode.do")	
	public View dropNode( @RequestParam HashMap<String, String> param , Model model) {
		
		int result = u_bookservice.dropNode(param);
		model.addAttribute("result",result);
		
		return jsonview;
		
	}
	
	// email 보내기 받는 사람 주소 변경하기
	@RequestMapping("recommend.do")
	public View recommend( String url , String text , Model model) {	
			// 내용 알 맞게 변경하기
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setSubject("뿌리 깊은 마크 URL 추천 ");
		message.setFrom("bitcamp104@gmail.com");
		message.setText(url +" "+ text);
		message.setTo("bitcamp104@gmail.com");
		mailSender.send(message);
		model.addAttribute("result","메일 전송");
		
		return jsonview;
		
	}
	
	//url 공유하기 눌렀을 경우 & url 공유 취소 했을 경우 & url 공유 수정 했을 경우
	@RequestMapping("shareUrlEdit.do")
	public View shareUrlEdit(U_BookDTO dto , Model model) {	
		
		int result = u_bookservice.shareUrlEdit(dto);
		model.addAttribute("result",result);
		
		return jsonview;
		
	}
	
	//ROOT 카테고리 추가 
	@RequestMapping("addRoot.do")
	public View addRoot(HttpServletRequest req , Model model) {
		
		HttpSession session = req.getSession();
        String uid = (String)session.getAttribute("info_userid");
		int ubid = u_bookservice.insertRootFolder(uid);
		
		model.addAttribute("ubid",ubid);
		
		return jsonview;
	}
	
	//완료된 그룹의 북마크 가져오기
	@RequestMapping("getCompletedTeamBookmark.do")
	public void getCompletedTeamBookmark(HttpServletResponse res, String gid) {
		
		res.setCharacterEncoding("UTF-8");
		
		JSONArray jsonArray = new JSONArray();	
		HashMap<String, String> href = new HashMap();
		List<G_BookDTO> list = g_bookservice.getCompletedTeamBookmark(Integer.parseInt(gid));
		
		for(int i =0; i<list.size(); i++) {
			
			JSONObject jsonobject = new JSONObject();
			
			String parentid = String.valueOf(list.get(i).getPid());
			
			if(parentid.equals("0") || parentid.equals(""))
				jsonobject.put("parent", "#");
			else
				jsonobject.put("parent", parentid);
			
			if(list.get(i).getUrl() == null)
				jsonobject.put("icon", "fa fa-folder");
			else
				jsonobject.put("icon", "https://www.google.com/s2/favicons?domain="+list.get(i).getUrl());
			
			href.put("href", list.get(i).getUrl());
			jsonobject.put("a_attr", href);
			jsonobject.put("id", list.get(i).getGbid());
			jsonobject.put("text", list.get(i).getUrlname());
			
			jsonArray.put(jsonobject);
		}
		
		try {
			res.getWriter().println(jsonArray);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
	}
	
	//완료된 그룹 url 내 것으로 보내기
	@RequestMapping("insertGroupUrl.do")
	public View insertGroupUrl( HttpServletRequest req , Model model) {
		
		HttpSession session = req.getSession();
        String uid = (String)session.getAttribute("info_userid");

        JSONArray jarr = new JSONArray();
		jarr = new JSONArray(req.getParameter("obj"));
		//System.out.println(jarr);
		//System.out.println(jarr.length());
		
		int result = u_bookservice.insertUrlFromCompletedGroup(jarr , uid);
		if(result>0) {
			model.addAttribute("result", "success");
		}else {
			model.addAttribute("result","fail");
		}
		return jsonview;
	}
	// 함수 End
}

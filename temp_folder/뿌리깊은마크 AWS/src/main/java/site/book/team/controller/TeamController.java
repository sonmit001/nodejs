/*
 * @Project : DeepRoot
 * @FileName : TeamController.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준, 김태웅
*/


package site.book.team.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import site.book.admin.dto.NoticeDTO;
import site.book.admin.service.NoticeService;
import site.book.socket.service.OnOffMemberSingleton;
import site.book.team.dto.G_AlarmDTO;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Charsets;
import com.google.gson.Gson;

import site.book.admin.dto.NoticeDTO;
import site.book.admin.service.NoticeService;
import site.book.team.dto.G_BookDTO;
import site.book.team.dto.G_JstreeDTO;
import site.book.team.dto.G_MemberDTO;
import site.book.team.dto.G_MyAlarmDTO;
import site.book.team.dto.G_RoleDTO;
import site.book.team.dto.TeamDTO;
import site.book.team.service.G_AlarmService;
import site.book.team.service.G_BookService;
import site.book.team.service.G_MemberService;
import site.book.team.service.TeamService;
import site.book.user.dto.U_BookDTO;
import site.book.user.dto.UserDTO;
import site.book.user.service.U_BookService;
import site.book.user.service.UserService;

/**
 * @Class : SocialController.java
 * @Date : 2018. 6. 24.
 * @Author : 김태웅
 */
@Controller
@RequestMapping("/team/")
public class TeamController {

	
	//변수 STRAT
	
	//명수
	@Autowired
	private TeamService teamservice;
	
	@Autowired
    private View jsonview;
	
	@Autowired
	private U_BookService u_bookservice;
	
	//희준
	@Autowired
	private UserService userservice;
	
	@Autowired
	private NoticeService notice_service;
	
	//태웅
	@Autowired
	private G_BookService gbookservice;
	@Autowired
	private G_AlarmService galarmservice;
	
	//준석
	@Autowired
	private G_MemberService g_memberservice;
	
	
	
	//변수 END
	
	
	//함수 STRAT
	
	//명수
	// 완료 그룹 리스트
	@RequestMapping("getCompletedTeamList.do")
	public View getCompletedTeamList(String uid ,  Model model) {
		
		List<TeamDTO> teamlist = teamservice.getCompletedTeamList(uid);
		
		model.addAttribute("teamlist",teamlist);
		return jsonview;
	}
	
	// 내 그룹 리스트 가져오기
	@RequestMapping("getTeamList.do")
	public View getTeamList(HttpServletRequest req,  Model model) {
		HttpSession session = req.getSession();
		String uid = (String)session.getAttribute("info_userid");
		
		List<TeamDTO> teamlist = teamservice.getTeamList(uid);

		model.addAttribute("teamlist", teamlist);
		return jsonview;
	}
	
	// 완료 그룹 삭제하기
	@RequestMapping("deleteCompletedTeam.do")
	public View deleteCompletedTeam(String uid, Model model) {
		
		int result = teamservice.deleteCompletedTeam(uid);
		
		model.addAttribute("result", result);
		return jsonview;
	}
	
	//해당 그룹 카테고리 리스트
	@RequestMapping("getTeamJstree.do")
	public void getTeamJstree(HttpServletRequest req,  HttpServletResponse res,String gid) {
		
		res.setCharacterEncoding("UTF-8");
		
		HttpSession session = req.getSession();
        String uid = (String)session.getAttribute("info_userid");
        JSONArray  jsonarray = gbookservice.getTeamJstree(gid,uid);
        
    	try {
			res.getWriter().println(jsonarray);
		}catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
	
	//그룹페이지에서 해당 노드 삭제
	@RequestMapping("deleteTeamNode.do")
	public View deleteTeamNode(Model model, String gbid) {
		
		int result = gbookservice.deleteTeamNode(gbid);
		model.addAttribute("result",result);
		
		return jsonview;
	}

	//그룹페이지에서 해당 노드 제목 수정
	@RequestMapping("updateTeamNodeText.do")
	public View updateTeamNodeText(Model model , @RequestParam HashMap<String, String> param) {
		
		int result = gbookservice.updateTeamNodeText(param);
		model.addAttribute("result",result);
		
		return jsonview;
	}
	
	//그룹페이지에서 폴더 혹은 url 추가
	@RequestMapping("addTeamFolderOrUrl.do")
	public View addTeamFolderOrUrl(HttpServletRequest req, Model model , G_BookDTO g_book) {
		HttpSession session = req.getSession();
        String uid = (String)session.getAttribute("info_userid");
        g_book.setUid(uid);
       /* System.out.println(g_book);*/
		int result = gbookservice.addTeamFolderOrUrl(g_book);
		model.addAttribute("result",result);
		
		return jsonview;
	}
	 
	//그룹 페이지에서 DND
	@RequestMapping("dropTeamNode.do")
	public View dropTeamNode(@RequestParam HashMap<String, String> param , Model model) {
		
		int result = gbookservice.dropTeamNode(param);
		model.addAttribute("result",result);
		
		return jsonview;
	}
	
	//url 수정
	@RequestMapping("editTeamUrl.do")
	public View editTeamUrl(@RequestParam HashMap<String, String> param , Model model) {

		int result = gbookservice.editTeamUrl(param);
		model.addAttribute("result",result);
		
		return jsonview;
	}
	
	@RequestMapping("getMyCategoryList.do")
	public void getMyCategoryList(HttpServletRequest req,  HttpServletResponse res) {
		
		res.setCharacterEncoding("UTF-8");
		
		HttpSession session = req.getSession();
        String uid = (String)session.getAttribute("info_userid");
        
        JSONArray jsonArray = new JSONArray();	
        HashMap<String, String> href = new HashMap();
		List<U_BookDTO> list = gbookservice.getMyCategoryList(uid);
        
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
				href.put("href", list.get(i).getUrl());
				
				jsonobject.put("id", list.get(i).getUbid());
				jsonobject.put("text", list.get(i).getUrlname());
				jsonobject.put("uid",uid);
				jsonobject.put("a_attr", href);
				
				jsonArray.put(jsonobject);
				
			}
		}
		try {
			res.getWriter().println(jsonArray);
		}catch (JSONException | IOException e) {
			e.printStackTrace();
		}
        
	}
	
	
	//태웅
	//해당 유저의 진행중인 그룹의 카테고리만를 보내준다.
	@RequestMapping("getGroupCategoryList.do")	
	public View getGroupCategoryList(HttpServletRequest req, Model model, String gid) {
		HttpSession session = req.getSession();
        String uid = (String)session.getAttribute("info_userid");
        
		List<G_BookDTO> list = gbookservice.getGroupCategoryList(gid);
		JSONArray jsonArray = new JSONArray();
		
		// ROOT Folder가 없을 경우
		if(list.size() == 0) {
			G_JstreeDTO gtree = new G_JstreeDTO();
			// Transacional
			// result[0] : max(ubid) +1 한 값
			// result[1] : 그룹에 카테고리가 없을 경우, 기본 카테고리를  생성해 결과 값
			int result = gbookservice.getMaxIDandInsertRootFolder(gid, uid);
			
			if(result > 0 ) {	
				gtree.setId(result);
				gtree.setParent("#");
				gtree.setText("ROOT");
				gtree.setIcon("fa fa-folder");
				gtree.setUid(uid);

				jsonArray.put(gtree);
			}
		}else {
			for(int i=0; i<list.size(); i++) {
				G_JstreeDTO gtree = new G_JstreeDTO();
				String parentid = String.valueOf(list.get(i).getPid());
				
				//parent category  or child category
				if(parentid.equals("0") || parentid.equals("")) {
					gtree.setParent("#");
				}else {
					gtree.setParent(parentid);
				}
				//Folder favicon 추가 & jsTree Form (JSON data)
				gtree.setId(list.get(i).getGbid());
				gtree.setText(list.get(i).getUrlname());
				gtree.setIcon("fa fa-folder");
				gtree.setUid(uid);

				jsonArray.put(gtree);
			}
		}
		
		model.addAttribute("data", jsonArray);
		return jsonview;
	}
	
	//선택한 그룹으로 해당 URL 추가
	@RequestMapping("addGroupBookmark.do")	
	public View addGroupBookmark(HttpServletRequest req, Model model, G_BookDTO g_book) {
        
		HttpSession session = req.getSession();
        String uid = (String)session.getAttribute("info_userid");
        g_book.setUid(uid);

        int result = gbookservice.insertGroupBookmark(g_book);
		if(result > 0) {
			model.addAttribute("result", "success");
		}else {
			model.addAttribute("result", "fail");
		}
		
		return jsonview;
	}
	
	// 초대 기능: 이메일로 초대 쪽지 보내기
	@RequestMapping("invite.do")	
	public View inviteUser(HttpServletRequest req, Model model, G_AlarmDTO alarm) {
		
		HttpSession session = req.getSession();
        String uid = (String)session.getAttribute("info_userid");
        alarm.setFromid(uid);
        
        G_MemberDTO member = new G_MemberDTO();
        member.setUid(alarm.getToid());
        member.setGid(alarm.getGid());
        
        // 본인에게 보낸 경우
        if( alarm.getToid().equals(uid) ) {
        	model.addAttribute("result", "self");
        	return jsonview;
        }
        // 이미 그룹원인 경우
        else if( teamservice.isGroupMember(member) != null ) {
        	model.addAttribute("result", "member");
        	return jsonview;
        }
        // 이미 초대한 사용자에게 보낸 경우
        else if( galarmservice.alreadySend(alarm, "invite") ) {
        	model.addAttribute("result", "already");
        	return jsonview;
        } 
        
        // 정상적인 경우에 실행
        else {
        	int result = g_memberservice.inviteUser(alarm);
            if(result > 0) {
    			model.addAttribute("result", "success");
    		}else {
    			model.addAttribute("result", "fail");
    		}
            return jsonview;
        }
	}
	
	// 초대 기능: 닉네임 자동완성 기능
	@RequestMapping("allUserNname.do")	
	public View getAllUserNname(HttpServletRequest req, Model model, String nname) {
	
		HttpSession session = req.getSession();
        String uid = (String)session.getAttribute("info_userid");
        
        List<String> result = userservice.getAllUserNname(nname);
        
		model.addAttribute("nname", result);
		
		return jsonview;
	}
	
	// 그룹원 강퇴  & 강퇴 쪽지 보내기
	@RequestMapping("banMember.do")	
	public View banMember(HttpServletRequest req, HttpSession session, Model model, G_MemberDTO member_ban) {
		
		String uid = (String)session.getAttribute("info_userid");
		member_ban.setUid(uid);
		System.out.println(member_ban);
		
		// 강퇴 대상이 그룹장인 경우 return
		if( member_ban.getGrid() == 1 ) {
			model.addAttribute("result", "master");
			return jsonview;
		}
		// 강퇴 대상이 매니저 vs 매니저인 경우 return
		else if( member_ban.getGrid() == member_ban.getMygrid() ) {
			model.addAttribute("result", "manager");
			return jsonview;
		}
		
		// 강퇴 대상이 그룹장->그룹원, 그룹장->매니저, 매니저->그룹원
		int isbaned = g_memberservice.banMember(member_ban);

		if(isbaned > 0) {
			String toid = g_memberservice.getToUid(member_ban.getNname());
			model.addAttribute("result", toid);
		}else if(isbaned < 0) {
			model.addAttribute("result", "empty");
		}else {
			model.addAttribute("result", "fail");
		}
		
		return jsonview;
	}
	
	// 그룹원에게 권한 부여
	@RequestMapping("giveGorupRole.do")	
	public View giveGorupRole(HttpServletRequest req, HttpSession session, Model model, G_MemberDTO member_auth, String key) {
		
		//System.out.println(key + ": " + member_auth);
		// 권한 부여 대상이 그룹장인 경우 return
		if( member_auth.getGrid()  == 1 ) {
			model.addAttribute("result", "master");
			return jsonview;
		}
		// 권한 부여 대상이 이미 매니저인 경우
		else if( member_auth.getGrid() == 2 && key.equals("manager")) {
			model.addAttribute("result", "manager");
			return jsonview;
		}
		// 권한 부여 대상이 이미 그룹원인 경우
		else if( member_auth.getGrid() == 3 && key.equals("member")) {
			model.addAttribute("result", "member");
			return jsonview;
		}
		
		// 권한 부여 대상: 그룹원->매니저, 매니저->그룹원
		int isAuth = (key.equals("manager")) ? g_memberservice.giveManager(member_auth) : g_memberservice.giveMember(member_auth);

		if(isAuth > 0) {
			model.addAttribute("result", "success");
		}else {
			model.addAttribute("result", "fail");
		}
		
		return jsonview;
	}
	
	//준석
	//그룹 페이지  이동
	@RequestMapping("main.do")
	public String movegroup(String gid, String gname, Model model, HttpServletRequest req) {
		
		HttpSession session = req.getSession();
        String uid = (String)session.getAttribute("info_userid");
        String nname = (String)session.getAttribute("info_usernname");

        // 태웅: 사용자가 주소창으로 장난친다면?
        G_MemberDTO temp_member = new G_MemberDTO(uid, Integer.parseInt(gid));
        G_RoleDTO roll_name = teamservice.isGroupMember(temp_member);
        if(roll_name == null) {
        	// 마이 페이지로 이동
        	return "redirect:/user/mybookmark.do";
        }else {
        	// 그룹원이라면, 해당 유저의 그룹 권한 명을 model
        	req.setAttribute("group_auth", roll_name.getGrname());
        	model.addAttribute("gmemberrole", roll_name.getGrname());
        }
        
		List<G_MemberDTO> gmemberlist = g_memberservice.selectGMemberlist(gid);
		
		model.addAttribute("gmemberlist",gmemberlist);
		
		// 현재 접속중인 유저 SEND (Map -> JSON)
		model.addAttribute("onlinelist", OnOffMemberSingleton.returnConvertJson(nname, gid));
		
		model.addAttribute("gid", gid);
        
        UserDTO user = userservice.getMember(uid);
        model.addAttribute("nname", user.getNname());
        model.addAttribute("profile", user.getProfile());
        
        // 희준
		if(uid != null) {
			List<TeamDTO> headerTeamList = teamservice.getTeamList(uid);
			model.addAttribute("headerTeamList", headerTeamList);
		}
		
		model.addAttribute("gname", gname);
		
		// 그룹 초대/강퇴/완료 알람  쪽지 리스트
		List<G_MyAlarmDTO> headerAlarmList = galarmservice.getAlarmList(uid);
		model.addAttribute("headerAlarmList", headerAlarmList);
		
		List<NoticeDTO> headerNoticeList = notice_service.getNotices();
		model.addAttribute("headerNoticeList", headerNoticeList);
        
		model.addAttribute("enabled", user.getEnabled());
		model.addAttribute("uid",user.getUid());
		for(G_MemberDTO dto : gmemberlist) {
			if(uid.equals(dto.getUid())) {
				model.addAttribute("grid", dto.getGrid());
			}
		}
	
		
		return "team.team";
	}
	
	// 파일  읽기
	public List<String> fileRead(String gid) throws IOException{
		List<String> list = new ArrayList<String>();
		
		String spath = this.getClass().getResource("").getPath();
    	spath = spath.substring(1);
		int index = spath.indexOf("WEB-INF");
		spath = spath.substring(0, index);
		spath = spath.replace("/", File.separator);
		spath += "team" + File.separator + "chat" + File.separator + gid +".txt";
    	String fileName = spath;
    	Path path = Paths.get(fileName);
    	
    	if(Files.exists(path)) {
    		byte [] fileBytes = Files.readAllBytes(path);
        	String temp = new String(fileBytes, "UTF-8");
        	list = Arrays.asList(temp.split("⊙"));
    	}
    	
    	/*char singleChar;
    	String str = "";
    	for(byte b : fileBytes) {
        	singleChar = (char) b;
        	if(singleChar != '\n') {
        		str += singleChar;
        	}else {
        		list.add(str);
        		str = "";
        		
        	}
        	System.out.print(singleChar);
        }*/
    	
    	
    	
    	/*if(Files.exists(path)) {
    		FileInputStream fileInputStream = new FileInputStream(fileName);
        	
        	InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
    		
        	try(BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
        		String line;
        		while((line = bufferedReader.readLine()) != null) {
        			list.add(line);
        		}
        	}
    	}*/

    	/*List<String> fileLinesList = Files.readAllLines(path, StandardCharsets.UTF_8);
    	
    	for(String line : fileLinesList) {
    		list.add(line);
    		System.out.println(line);
    	}*/
    	
		return list;
	}
	
	//함수 END
	
}

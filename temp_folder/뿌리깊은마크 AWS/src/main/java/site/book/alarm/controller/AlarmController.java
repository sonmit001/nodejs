/*
 * @Project : DeepRoot
 * @FileName : MainController.java
 * @Date : 2018. 6. 7.
 * @Author : 김희준
*/


package site.book.alarm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import site.book.team.dto.G_AlarmDTO;
import site.book.team.dto.G_MemberDTO;
import site.book.team.service.G_AlarmService;

/**
 * @Class : MainController.java
 * @Date : 2018. 6. 11.
 * @Author : 김희준, 김태웅
 */
@Controller
@RequestMapping("/alarm/")
public class AlarmController {
	
	// 변수 Start
	// 태웅
	@Autowired
	private View jsonview;
	@Autowired
	G_AlarmService galarmservice;
	// 희준
	
	// 변수 End
	
	// 함수 Start
	// 태웅
	/* 초대 쪽지 승인: 그룹 가입 */
	@RequestMapping(value="joinGroup.do")
	public View joinGroup(HttpServletRequest req, HttpSession session, Model model, G_MemberDTO member, String gname) {
		
		String uid = (String)session.getAttribute("info_userid");
		member.setUid(uid);
		
		int result = galarmservice.joinGroup(member);
		if(result > 0) {
			String path_to = "/team/main.do?gid=" + member.getGid() + "&gname=" + gname;
			model.addAttribute("path", path_to);
			model.addAttribute("result", "joined");
		}else if(result < 0){
			model.addAttribute("result", "already");
		}else {
			model.addAttribute("result", "fail");
		}
		
		return jsonview;
	}
	
	/* 내게 온 쪽지 삭제 */
	@RequestMapping(value="deleteMemo.do")
	public View deleteMemo(HttpServletRequest req, HttpSession session, Model model, G_AlarmDTO alarm) {
		
		String uid = (String)session.getAttribute("info_userid");
		alarm.setToid(uid);
		
		int result = galarmservice.deleteMemo(alarm);
		if(result > 0) {
			model.addAttribute("result", "deleted");
		}else {
			model.addAttribute("result", "fail");
		}
		
		return jsonview;
	}
	
	// 희준
	
	
	// 함수 End
}

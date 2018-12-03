/*
 * @Project : DeepRoot
 * @FileName : MainController.java
 * @Date : 2018. 6. 7.
 * @Author : 김희준
*/


package site.book.main.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.api.plus.PlusOperations;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;

import site.book.admin.dto.A_BookDTO;
import site.book.admin.dto.A_CategoryDTO;
import site.book.admin.dto.NoticeDTO;
import site.book.admin.service.A_BookService;
import site.book.admin.service.A_CategoryService;
import site.book.admin.service.NoticeService;
import site.book.team.dto.G_MemberDTO;
import site.book.team.dto.G_MyAlarmDTO;
import site.book.team.dto.TeamDTO;
import site.book.team.service.G_AlarmService;
import site.book.team.service.TeamService;
import site.book.user.dto.EmailAuthDTO;
import site.book.user.dto.UserDTO;
import site.book.user.service.UserService;

/**
 * @Class : MainController.java
 * @Date : 2018. 6. 11.
 * @Author : 김희준, 김태웅
 */
@Controller
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger("member");
	// 태웅
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	/* GoogleLogin */
	@Autowired
	private GoogleConnectionFactory googleConnectionFactory;
	
	@Autowired
	private OAuth2Parameters googleOAuth2Parameters;
	
	@Autowired
	private A_CategoryService a_category_service;
	
	@Autowired
	private A_BookService a_book_service;
	
	@Autowired
	private UserService user_service;
	
	@Autowired
	G_AlarmService galarmservice;
	
	// 희준
	@Autowired
	private View jsonview;
	
	@Autowired
	private TeamService teamservice;
	
	@Autowired
	private NoticeService notice_service;
	
	// 명수
	
	// 변수 End
	
	// 함수 Start
	
	// 태웅
	
	/*메인 화면 데이터 출력*/
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String initMain(HttpServletRequest req, Model model) {
		//System.out.println("홈: 메인 페이지");
		
		List<A_CategoryDTO> categoryList = a_category_service.getCategorys();
		model.addAttribute("categoryList", categoryList);
		
		List<A_BookDTO> bookList = a_book_service.getMainBooks();
		model.addAttribute("bookList", bookList);
		
		HttpSession session = req.getSession();
		String uid = (String)session.getAttribute("info_userid");
		System.out.println(uid);
		if(uid != null) {
			List<TeamDTO> headerTeamList = teamservice.getTeamList(uid);
			model.addAttribute("headerTeamList", headerTeamList);
		}
		
		// 그룹 초대/강퇴/완료 알람  쪽지 리스트
		List<G_MyAlarmDTO> headerAlarmList = galarmservice.getAlarmList(uid);
		model.addAttribute("headerAlarmList", headerAlarmList);
		
		// 관리자 공지사항 쪽지 리스트
		List<NoticeDTO> headerNoticeList = notice_service.getNotices();
		model.addAttribute("headerNoticeList", headerNoticeList);
		
		return "home.index";
	}
	
	/* URL Click Function */
	@RequestMapping(value="/clickurl.do")
	public View clickURL(HttpServletRequest req, Model model, String abid) {
		
		//System.out.println(abid);
		int result = a_book_service.clickURL(abid);
		
		if(result > 0) {
			model.addAttribute("click", "1");
		}else {
			model.addAttribute("click", "0");
		}
		
		return jsonview;
	}
	
	/* 로그인 */
	@RequestMapping(value="/joinus/login.do")
	public View login(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session, Model model, UserDTO user) {
		logger.info("login success or fail : " + request.getAttribute("msg") );
		// process message from Handler and JSON data response
		// 로그인 실패: 아이디 또는 비밀번호 잘못 입력
		try {
			if(request.getAttribute("msg").equals("fail")) {
				model.addAttribute("login", "fail");
			
			// 중복 로그인 처리
			}else if(request.getAttribute("msg").equals("duplicate")) {
				model.addAttribute("login", "duplicate");
				
			}else {
				String userid = (String)request.getAttribute("userid");
				user.setUid(userid);
				user = user_service.getMember(user.getUid());
				model.addAttribute("login", "success");
				
				String role = (String)request.getAttribute("ROLE");
				if(role.equals("ADMIN")) {
					model.addAttribute("path", "admin/main.do");
					logger.info("admin login");
				}else {
					model.addAttribute("path", "/");
					logger.info("user login");
				}
				
				// set info session userid
				session.setAttribute("info_userid", user.getUid());
				session.setAttribute("info_usernname", user.getNname());
				session.setAttribute("info_userprofile", user.getProfile());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return jsonview;
	}
	
	/* Google Login API START */
	/* 구글 로그인 버튼 클릭시 Google+ API 실행 */
	@RequestMapping(value="/joinus/googleLogin", method= { RequestMethod.GET, RequestMethod.POST })
	public View doGoogleSignInActionPage(HttpServletRequest request, Model model) throws Exception{
		logger.info("google login");
		/* 구글code 발행 */
		OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
		  String url = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);
		
		// System.out.println("/joinus/googleLogin, url : " + url);
		model.addAttribute("url",url);
		
		return jsonview;
	}
	
	/* 사용자가 인증 처리시 redirect 되는 함수. 따라서, 여기서 가입 처리와 로그인을 담당 */
	@RequestMapping(value="/joinus/googleSignInCallback", method= { RequestMethod.GET, RequestMethod.POST })
	public String googleRollin(HttpServletRequest request, HttpServletResponse response, HttpSession session,
								Model model, @RequestParam String code) throws ServletException, IOException {
		
		//System.out.println(code);
		logger.info("googlesignincallback");
		OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
		AccessGrant accessGrant = oauthOperations.exchangeForAccess(code , googleOAuth2Parameters.getRedirectUri(), null);
		
		String accessToken = accessGrant.getAccessToken();
		Long expireTime = accessGrant.getExpireTime();
		
		if (expireTime != null && expireTime < System.currentTimeMillis()) {
			accessToken = accessGrant.getRefreshToken();
		    logger.error("accessToken is expired. refresh token = {}", accessToken);
		}
		
		Connection<Google> connection = googleConnectionFactory.createConnection(accessGrant);
		Google google = connection == null ? new GoogleTemplate(accessToken) : connection.getApi();
		
		PlusOperations plusOperations = google.plusOperations();
		Person profile = plusOperations.getGoogleProfile();
		
		//System.out.println(profile.getDisplayName() + "/" + profile.getId() + "/" + profile.getImageUrl());
		//System.out.println(profile.getAccountEmail() + "/" + profile);
		
		//Save into DB
		UserDTO user = new UserDTO();
		user.setUid(profile.getAccountEmail());
		user.setNname(profile.getDisplayName());
		user.setOauth_code(profile.getId());
		
		user_service.rollinUser(user);
		
		// set info session userid
		session.setAttribute("info_userid", profile.getAccountEmail());
		session.setAttribute("info_usernname", profile.getDisplayName());
		session.setAttribute("info_userprofile", profile.getImageUrl());
		session.setAttribute("info_oauth", "google");
		
		return "redirect:/";
	}
	
	/* 네이버 로그인 */
	/*@RequestMapping(value="/joinus/naverLogin")
	*/
	/* 회원가입  */
	@RequestMapping(value="/joinus/rollin.do", method=RequestMethod.POST)
	public View rollin(HttpServletRequest request, HttpServletResponse response, 
			UserDTO user, Model model) {
		
		user.setPwd(this.bCryptPasswordEncoder.encode(user.getPwd()));
		System.out.println("user 들어옴 새로운 가입 시작");
		System.out.println(user);
		
		int result = user_service.rollinUser(user);
		if(result >= 0) {
			model.addAttribute("rollin", "pass");
		}else {
			model.addAttribute("rollin", "fail");
		}
		
		return jsonview;
	}
	
	/* 회원가입 유저에게 이메일 전송과 DB에 해당 유저의 email과 authcode 저장 */
	@RequestMapping(value="/joinus/emailsend.do", method=RequestMethod.POST)
	public View emailConfirm(HttpServletRequest request, HttpServletResponse response, 
			EmailAuthDTO auth, Model model) {
		logger.info("signup emailsend auth : " + auth);
		int result = user_service.confirmEmail(auth);
		if(result > 0) {
			model.addAttribute("email", "pass");
			logger.info("signup emailsend success");
		}else {
			model.addAttribute("email", "fail");
			logger.info("signup emailsend fail");
		}
		return jsonview;
	}
	
	/* check Authcode */
	@RequestMapping(value="/joinus/emailauth.do", method=RequestMethod.POST)
	public View checkAuthcode(HttpServletRequest request, HttpServletResponse response, 
			EmailAuthDTO auth, Model model) {
		logger.info("signup authcheck");
		int result = user_service.checkAuthcode(auth);
		if(result > 0) {
			model.addAttribute("auth", "pass");
		}else {
			model.addAttribute("auth", "fail");
		}
		return jsonview;
	}
	
	/* Check UID */
	@RequestMapping(value="/joinus/checkuid.do", method=RequestMethod.POST)
	public View checkUid(HttpServletRequest request, HttpServletResponse response, 
			UserDTO user, Model model) {
		
		//System.out.println(user);
		int result = user_service.checkUserID(user.getUid());
		if(result > 0) {
			model.addAttribute("result", "fail");
		}else {
			model.addAttribute("result", "pass");
		}
		
		return jsonview;
	}
	
	/* Check Nickname */
	@RequestMapping(value="/joinus/checknname.do", method=RequestMethod.POST)
	public View checkNname(HttpServletRequest request, HttpServletResponse response, 
			UserDTO user, Model model) {

		//System.out.println(user);
		int result = user_service.checkUserNickname(user.getNname());
		if(result > 0) {
			model.addAttribute("result", "fail");
		}else {
			model.addAttribute("result", "pass");
		}
		
		return jsonview;
	}
	
	/* 회원정보 수정 페이지 GET */
	@RequestMapping(value="/myInfo.do", method=RequestMethod.GET)
	public String initMemberInfo(HttpServletRequest req, Model model) {
		
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
		
		return "member.myinfo";
	}
	
	/* 회원정보 수정 페이지 POST */
	@RequestMapping(value="/myInfo.do", method=RequestMethod.POST)
	public String editMemberInfo(HttpServletRequest request, HttpSession session,
			UserDTO user, @RequestParam("uploadFile") MultipartFile file, 
			Model model) {
		
		//비밀번호 재암호화
		user.setPwd(this.bCryptPasswordEncoder.encode(user.getPwd()));
		//result = UID.확장자
		String result = user_service.editMember(request, user, file);
		//System.out.println("회원수정: " + result);
		
		// .확장자명이 있다면 session update
		if(result.split("\\.").length > 1)
			session.setAttribute("info_userprofile", result);
		return "redirect:/";
	}
	
	/* 회원정보 수정 페이지 비밀번호 재확인 */
	@RequestMapping(value="/reconfirm.do", method=RequestMethod.POST)
	public View confirmMemberPWD(Model model, UserDTO input_data) {
		
		//getMember 통해  해당 회원정보 가져옴
		UserDTO member = user_service.getMember(input_data.getUid());
		//DB에서 가져온 암호화된 문자열
		String encodedPassword = member.getPwd();
		
		boolean result = bCryptPasswordEncoder.matches(input_data.getPwd(), encodedPassword);
		if(result) {
			model.addAttribute("result", "pass");
		}else {
			model.addAttribute("result", "fail");
		}
		return jsonview;
	}
	
	/* 회원 탈퇴 */
	@RequestMapping(value="/rollout.do", method=RequestMethod.GET)
	public String rolloutMember(HttpServletRequest request, HttpSession session, Model model) {
		String uid = (String)session.getAttribute("info_userid");
		//System.out.println(uid);
		user_service.deleteMember(uid);

		return "member.logout";
	}
	
	/* 비밀번호 찾기 */
	// 비밀번호 찾기 시, 회원인지 확인
	@RequestMapping(value="/confirmuser.do", method=RequestMethod.POST)
	public View confirmUser(HttpServletRequest request, Model model, EmailAuthDTO user) {

		//System.out.println(user);
		int result = user_service.confirmUser(user);

		if(result > 0) {
			model.addAttribute("result", "member");
		}else {
			model.addAttribute("result", "who");
		}
		return jsonview;
	}
	
	// 회원 여부 확인 후, 임시 비밀번호 전송
	@RequestMapping(value="/findpwd.do", method=RequestMethod.POST)
	public View findUserPwd(HttpServletRequest request, Model model,
			EmailAuthDTO authcode, UserDTO user) {
		logger.info("temp_password send authcode : " + authcode);
		int resultAuth = user_service.checkAuthcode(authcode);
		
		// 인증코드가 정확하다면,
		if(resultAuth > 0) {
			// 회원에게 임시 비밀번호 발급
			logger.info("send temp_password");
			user_service.findUserPwd(user);
			model.addAttribute("result", "success");
			model.addAttribute("path", "/");
		}else {
			model.addAttribute("result", "fail");
			logger.info("temp_password authcode is wrong ");
		}
		return jsonview;
	}
	/* 비밀번호 찾기 END */
	
	// 미리보기 기능 추가 상세정보 웹크롤링(World Ranking, Sub-URL)
	@RequestMapping("previewdetail.do")
	public View WebCrawling2(String abid, Model model) {
		A_BookDTO book = a_book_service.getBook(Integer.parseInt(abid));
		String url = book.getUrl().replace("http://", "").replace("https://", "");
	
		try {
			/* <Woong> Web Crawling - SubURL & Daily Visitor & World Rank */
			String fixed_suburl = "https://www.alexa.com/siteinfo/"; // subURL Top5
			String fixed_visitor = "http://website.informer.com/";	// Daily Visitor & World Rank
			
			// subURL Top5 START
			Document document = Jsoup.connect(fixed_suburl+url).get();
			Element content = document.getElementById("subdomain_table");
			Elements subURL = content.getElementsByClass("word-wrap");
			Elements percent = content.getElementsByClass("text-right");
			
			List<List<String>> url_percent = new ArrayList<>();
			
			try {
				for(int i = 0; i < 5; i++) {
					List<String> temp = new ArrayList<>();
					String sub = subURL.get(i).select("span").text();
					String rate = percent.get(i+1).select("span").text();
					temp.add(sub);
					temp.add(rate);
					url_percent.add(temp);
				}
			}catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			
			model.addAttribute("suburl", url_percent);
			
			// Daily Visitor & World Rank Top5 START
			document = Jsoup.connect(fixed_visitor+url).get();
			content = document.getElementById("whois");
			Element visitor_content = content.getElementById("visitors");
			Element rank_content = content.getElementById("alexa_rank");
			
			String visitor = visitor_content.select("b").text().trim().replace(" ", ",");
			String rank = rank_content.select("b").text().trim();

			model.addAttribute("visitor", visitor);
			model.addAttribute("rank", rank);
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return jsonview;
	}

	// 희준
	// WebCrawling을 통해 해당 사이트에 title, type, imge, url, description 가져오기
	@RequestMapping("preview.do")
	public View WebCrawling(String abid, Model model) {
		
		A_BookDTO book = a_book_service.getBook(Integer.parseInt(abid));
		String url = book.getUrl();
		
		Document doc;
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		String[] REQUIRED_META = new String[] { "og:title", "og:type", "og:image", "og:url", "og:description" };
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
					} else if (s.equals(REQUIRED_META[1])) {
						result.put(REQUIRED_META[1], Arrays.asList(new String[] { "website" }));
					} else if (s.equals(REQUIRED_META[2])) {
						result.put(REQUIRED_META[2],
								Arrays.asList(new String[] { doc.select("img").eq(0).attr("abs:src") }));
					} else if (s.equals(REQUIRED_META[3])) {
						result.put(REQUIRED_META[3], Arrays.asList(new String[] { doc.baseUri() }));
					} else if (s.equals(REQUIRED_META[4])) {
						result.put(REQUIRED_META[4], Arrays.asList(new String[] { doc
								.select("meta[property=description], meta[name=description]").eq(0).attr("content") }));
					}
				}
			}
			for (String s : result.keySet()) {model.addAttribute(s.substring(3), result.get(s));}

		} catch (Exception e) {
			/*e.printStackTrace();*/
		}
		return jsonview;
	}
	
	// 공지사항 최신순으로 3개 가져오기
	@RequestMapping("/getNotices.do")
	public View getNotices(Model model) {
		
		List<NoticeDTO> noticeList = notice_service.getNotices();
		model.addAttribute("noticeList", noticeList);
		
		return jsonview;
	}
	
	// 그룹 추가 / 추가 시 그룹장까지 Transaction으로 처리
	@RequestMapping("/addGroup.do")
	public View addGroup(HttpServletRequest req, String gname, Model model) {
		HttpSession session = req.getSession();
		String uid = (String)session.getAttribute("info_userid");
		
		G_MemberDTO member = new G_MemberDTO();
		member.setUid(uid);
		member.setGrid(1);
		
		TeamDTO newTeam = teamservice.addGroup(gname, member);
		model.addAttribute("newTeam", newTeam);
		
		return jsonview;
	}
	
}

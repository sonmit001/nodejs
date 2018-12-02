/*
 * @Project : DeepRoot
 * @FileName : AdminController.java
 * @Date : 2018. 6. 7.
 * @Author : 김희준
*/

package site.book.admin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import site.book.admin.dto.A_BookDTO;
import site.book.admin.dto.A_CategoryDTO;
import site.book.admin.dto.NoticeDTO;
import site.book.admin.service.A_BookService;
import site.book.admin.service.A_CategoryService;
import site.book.admin.service.NoticeService;
import site.book.admin.service.VisitorService;
import site.book.team.dto.S_TeamDTO;
import site.book.team.dto.TeamDTO;
import site.book.team.service.G_BookService;
import site.book.team.service.TeamService;
import site.book.user.dto.S_U_BookDTO;
import site.book.user.dto.UserDTO;
import site.book.user.service.U_BookService;
import site.book.user.service.UserService;

/**
 * @Class : AdminController.java
 * @Date : 2018. 6. 7.
 * @Author : 김희준, 김태웅
 */
@Controller
@RequestMapping("/admin/")
public class AdminController {

	@Autowired
	private A_CategoryService a_category_service;

	@Autowired
	private A_BookService a_book_service;

	@Autowired
	private G_BookService g_book_service;

	@Autowired
	private UserService user_service;

	@Autowired
	private U_BookService u_book_service;

	@Autowired
	private TeamService team_service;

	@Autowired
	private NoticeService notice_service;

	@Autowired
	private VisitorService visitor_service;

	@Autowired
	private U_BookService u_bookservice;
	
	@Autowired
	private TeamService teamservice;
	
	@Autowired
	private View jsonview;
	
	// 메인 페이지
	@RequestMapping("main.do")
	public String main(HttpServletRequest req, Model model) {
		// System.out.println("관리자 메인 페이지");

		// Visit Chart Data
		JSONArray visit_chartdata = new JSONArray();
		List<HashMap<String, String>> visit_data = visitor_service.numOfVisitorByDate();
		int total_visit = 0;
		for (HashMap<String, String> data : visit_data) {
			total_visit += Integer.parseInt(String.valueOf(data.get("c")));

			JSONObject jsonobject = new JSONObject();
			jsonobject.put("date", data.get("d"));
			jsonobject.put("visit", data.get("c"));
			jsonobject.put("total_visit", total_visit);

			visit_chartdata.put(jsonobject);
		}
		model.addAttribute("visit_chartdata", visit_chartdata);

		// Member Chart Data
		JSONArray member_chartdata = new JSONArray();
		List<HashMap<String, String>> member_data = user_service.getNewUser();
		int total_member = 0;
		for (HashMap<String, String> data : member_data) {
			total_member += Integer.parseInt(String.valueOf(data.get("c")));

			JSONObject jsonobject = new JSONObject();
			jsonobject.put("date", data.get("d"));
			jsonobject.put("new_member", data.get("c"));
			jsonobject.put("total_member", total_member);

			member_chartdata.put(jsonobject);
		}
		model.addAttribute("member_chartdata", member_chartdata);

		// User_Bookmark Chart Data
		JSONArray user_bookmark_chartdata = new JSONArray();
		int total_user_bookmark = 0;
		List<HashMap<String, String>> user_bookmark_data = u_book_service.numOfBookByDate();

		for (HashMap<String, String> data : user_bookmark_data) {
			total_user_bookmark += Integer.parseInt(String.valueOf(data.get("c")));

			JSONObject jsonobject = new JSONObject();
			jsonobject.put("date", data.get("d"));
			jsonobject.put("new_user_bookmark", data.get("c"));
			jsonobject.put("total_user_bookmark", total_user_bookmark);

			user_bookmark_chartdata.put(jsonobject);
		}
		model.addAttribute("user_bookmark_chartdata", user_bookmark_chartdata);

		// Group Bookmark Chart Data
		JSONArray group_bookmark_chartdata = new JSONArray();
		int total_group_bookmark = 0;
		List<HashMap<String, String>> group_bookmark_data = g_book_service.numOfBookByDate();

		for (HashMap<String, String> data : group_bookmark_data) {
			total_group_bookmark += Integer.parseInt(String.valueOf(data.get("c")));

			JSONObject jsonobject = new JSONObject();
			jsonobject.put("date", data.get("d"));
			jsonobject.put("new_group_bookmark", data.get("c"));
			jsonobject.put("total_group_bookmark", total_group_bookmark);

			group_bookmark_chartdata.put(jsonobject);
		}
		model.addAttribute("group_bookmark_chartdata", group_bookmark_chartdata);
		
		// 공지사항 리스트 가져오기
		model.addAttribute("headerNoticeList", getNotices());
		
		return "admin.main";
	}

	// mainBookList 페이지
	@RequestMapping("mainBookList.do")
	public String mainBookList(Model model) {
		List<A_CategoryDTO> categorylist = a_category_service.getCategorys();
		model.addAttribute("categorylist", categorylist);
		
		List<HashMap<A_CategoryDTO, List<A_BookDTO>>> url_by_category = a_category_service.urlByCategory();
		model.addAttribute("url_by_category", url_by_category);
		
		// 공지사항 리스트 가져오기
		model.addAttribute("headerNoticeList", getNotices());
		
		return "admin.mainBookList";
	}
	
	// social 페이지
	@RequestMapping("social.do")
	public String social(Model model) {
		
		List<S_U_BookDTO> u_list= u_bookservice.getSocialBookmarkList();
		model.addAttribute("u_list",u_list);
		
		List<S_TeamDTO> g_list=teamservice.getSocialGroupList();
		model.addAttribute("g_list", g_list);
		
		// 공지사항 리스트 가져오기
		model.addAttribute("headerNoticeList", getNotices());
		
		return "admin.social";
	}
	
	
	// userListTable 페이지
	@RequestMapping("userListTable.do")
	public String userListTable(Model model) {
		List<UserDTO> userlist = user_service.getUserList();
		model.addAttribute("userlist", userlist);
		
		// 공지사항 리스트 가져오기
		model.addAttribute("headerNoticeList", getNotices());
		
		return "admin.userListTable";
	}
	
	// groupListTable 페이지
	@RequestMapping("groupListTable.do")
	public String groupListTable(Model model) {
		List<S_TeamDTO> grouplist = team_service.getSocialGroupList();
		model.addAttribute("grouplist", grouplist);
		
		// 공지사항 리스트 가져오기
		model.addAttribute("headerNoticeList", getNotices());
		
		return "admin.groupListTable";
	}
	
	// 공지사항 리스트로 가져오기
	private List<NoticeDTO> getNotices() {
		List<NoticeDTO> headerNoticeList = notice_service.getNotices();
		return headerNoticeList;
	}
	
	// 그룹 삭제
	@RequestMapping("deleteGroup.do")
	public View deleteSGroup(String gid, Model model) {
		int row = team_service.deleteGroup(Integer.parseInt(gid));
		String data = (row == 1) ? "성공" : "실패";
		model.addAttribute("data", data);
		
		return jsonview;
	}
	
	// 블랙리스트 등록
	@RequestMapping("blacklist.do")
	public View blacklist(String uid, Model model) {
		int row = user_service.blacklist(uid);
		String data = (row == 1) ? "성공" : "실패";
		
		model.addAttribute("data", data);
		
		return jsonview;
	}
	
	// 카테고리 추가
	@RequestMapping("addCategory.do")
	public View addCategory(A_CategoryDTO category, Model model) {
		A_CategoryDTO newCategory = a_category_service.addCategory(category);
		
		model.addAttribute("newCategory", newCategory);
		
		return jsonview;

	}
	
	// 카테고리 삭제
	@RequestMapping("deleteCategory.do")
	public View deleteCategory(String acid, Model model) {
		a_category_service.deleteCategory(Integer.parseInt(acid));

		model.addAttribute("acid", acid);
		
		return jsonview;
	}
	
	// 카테고리 수정
	@RequestMapping("editCategory.do")
	public View updateCategory(A_CategoryDTO category, Model model) {
		A_CategoryDTO editCategory = a_category_service.updateCategory(category);
		model.addAttribute("editCategory", editCategory);
		
		return jsonview;
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
	
	// URL 추가하기
	@RequestMapping("addUrl.do")
	public View addBook(A_BookDTO book, Model model) {
		A_BookDTO newBook = a_book_service.addBook(book);
		model.addAttribute("newBook", newBook);
		
		return jsonview;
	}
	
	// URL 삭제하기
	@RequestMapping("deleteUrl.do")
	public View deleteBook(String abid, Model model) {
		int row = a_book_service.deleteBook(Integer.parseInt(abid));
		String data = (row == 1) ? "성공" : "실패";
		model.addAttribute("data", data);

		return jsonview;
	}
	
	// URL 수정하기
	@RequestMapping("editUrl.do")
	public View updateBook(A_BookDTO book, Model model) {
		a_book_service.updateBook(book);
		model.addAttribute("updateBook", book);
		
		return jsonview;
	}
	
	// 카테고리 색상 변경하기
	@RequestMapping("editCategoryCclor.do")
	public View updateCategoryColor(A_CategoryDTO category, Model model) {
		int row = a_category_service.editCategoryColor(category);
		String data = (row == 1) ? "성공" : "실패";
		model.addAttribute("data", data);
		
		return jsonview;
	}
	
	// 소셜 개인 북마크 삭제하기
	@RequestMapping("deleteSUBook.do")
	public View deleteSUBook(String ubid, Model model) {
		int row = u_book_service.deleteSocialBookmark(Integer.parseInt(ubid));
		String data = (row == 1) ? "성공" : "실패";
		model.addAttribute("data", data);
		
		return jsonview;
	}

	// 공지사항 쓰기
	@RequestMapping("noticeReg.do")
	public View noticeReg(String ncontent, Model model) {
		NoticeDTO newNotice = notice_service.noticeReg(ncontent);
		model.addAttribute("newNotice", newNotice);
		
		return jsonview;
	}

}

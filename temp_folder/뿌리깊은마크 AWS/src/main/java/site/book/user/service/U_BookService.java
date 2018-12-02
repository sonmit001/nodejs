/*
 * @Project : DeepRoot
 * @FileName : U_BookService.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준, 김명수
*/


package site.book.user.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import site.book.user.dao.U_BookDAO;
import site.book.user.dto.S_U_BookDTO;
import site.book.user.dto.U_BookDTO;

/**
 * @Class : U_BookService.java
 * @Date : 2018. 6. 8.
 * @Author : 김희준, 김명수
 */
@Service
public class U_BookService {
	
	// 변수 Start
	
	// 태웅
	
	
	// 희준
	
	
	// 명수
	@Autowired
	private SqlSession sqlsession;

	// 변수 End
	
	// 함수 Start
	
	// 태웅
	public int addToMyBookmark(U_BookDTO book) {
		U_BookDAO bookDAO = sqlsession.getMapper(U_BookDAO.class);
		int result = 0;
		
		try {
			result = bookDAO.insertToMyBookmark(book);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	// 희준
	
	// 개인이 추가한 북마크 수
	public List<HashMap<String, String>> numOfBookByDate() {
		U_BookDAO bookDAO = sqlsession.getMapper(U_BookDAO.class);
		List<HashMap<String, String>> map = null;
		
		try {
			map = bookDAO.numOfBookByDate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	// 소셜 개인 북마크 리스트
	public List<S_U_BookDTO> getSocialBookmarkList() {
		U_BookDAO bookDAO = sqlsession.getMapper(U_BookDAO.class);
		List<S_U_BookDTO> list = null;
		
		try {
			list = bookDAO.socialBookmarkList();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 소셜 개인 북마크 삭제
	public int deleteSocialBookmark(int ubid) {
		U_BookDAO bookDAO = sqlsession.getMapper(U_BookDAO.class);
		int row = 0;
		
		try {
			row = bookDAO.deleteSocialBookmark(ubid);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return row;
	}
	
	// 공유하지 않은 URL 추가하기
	public int addUrlNotShare(U_BookDTO book) {
		U_BookDAO bookDAO = sqlsession.getMapper(U_BookDAO.class);
		int row = 0;
		
		try {
			row = bookDAO.addUrlNotShare(book);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	// 명수
	// 마이북마크 왼쪽 폴더들만 보이는 JSTREE
	public List<U_BookDTO> getCategoryList(String uid) {	//해당
		
		U_BookDAO dao = sqlsession.getMapper(U_BookDAO.class);
		List<U_BookDTO> list = null;
		try {
			list = dao.getCategoryList(uid);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	// 마이북마크 왼쪽 JSTREE에서 root 카테고리 추가
	@Transactional
	public int insertRootFolder(String uid) {
		
		U_BookDAO dao = sqlsession.getMapper(U_BookDAO.class);
		int maxid = 0;
		try {
			dao.insertRootFolder(uid);
			maxid = dao.getMaxId();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return maxid;
	}

	// JSTREE 노드 생성시 db 처리와 노드 뿌리는처리에서 id 값 가져오기
	public int getmaxid() {

		U_BookDAO dao = sqlsession.getMapper(U_BookDAO.class);
		int maxid = 0;
		try {
			maxid = dao.getMaxId();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return maxid;
	}

	// JSTREE 폴더 혹은 URL 추가
	@Transactional
	public int addFolderOrUrl(U_BookDTO dto) {

		U_BookDAO dao = sqlsession.getMapper(U_BookDAO.class);
		int result = 0;
		try {
			dao.addFolderOrUrl(dto);
			result = dao.getMaxId();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	// JSTREE 폴더 혹은 URL 삭제
	public int deleteFolderOrUrl(String str) {
		
		U_BookDAO dao = sqlsession.getMapper(U_BookDAO.class);
		int result = 0;
		try {
			result = dao.deleteFolderOrUrl(str);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		return result;
	}

	public int editUrl(U_BookDTO dto) {

		U_BookDAO dao = sqlsession.getMapper(U_BookDAO.class);
		int result = 0;
		try {
			result = dao.editUrl(dto);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		
		return result;
	}
	
	// 왼쪽 폴더(노드) 클릭시 하위 URL 리스트
	public List<U_BookDTO> getUrl(int ubid) {

		U_BookDAO dao = sqlsession.getMapper(U_BookDAO.class);
		List<U_BookDTO> list = null;
		try {
			list = dao.getUrl(ubid);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	// DND 처리 하기
	public int dropNode(HashMap<String, String> param) {
		U_BookDAO dao = sqlsession.getMapper(U_BookDAO.class);
		int result = 0;
		try {
			result = dao.dropNode(param);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 마이북마크 노드 이름 수정
	public int updateNodeText(HashMap<String, String> param) {
		
		U_BookDAO dao = sqlsession.getMapper(U_BookDAO.class);
		int result = 0;
		try {
			result = dao.updateNodeText(param);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	// URL 공유 수정 삭제 추가
	public int shareUrlEdit(U_BookDTO dto) {

		U_BookDAO dao = sqlsession.getMapper(U_BookDAO.class);
		int result = 0;
		try {
			result = dao.shareUrlEdit(dto);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 완료된 그룹 URL 추가
	public int insertUrlFromCompletedGroup(JSONArray jarr , String uid ) {

		U_BookDAO dao = sqlsession.getMapper(U_BookDAO.class);
		
		List<U_BookDTO> list = new ArrayList<U_BookDTO>();
		Map<String, Object> map = new HashMap<String, Object>();
		int length = jarr.length();
		int result = 0;
		
		for(int i = 0 ; i< length ; i++) {
			String url = (String)jarr.getJSONObject(i).get("url");
			if ( url != null) {
				U_BookDTO dto = new U_BookDTO();
				
				dto.setUrl(url);
				dto.setUrlname((String)jarr.getJSONObject(i).get("urlname"));
				dto.setPid(Integer.valueOf((String)jarr.getJSONObject(i).get("pid")));
				dto.setUid(uid);
				
				list.add(dto);
			}
		}
		//System.out.println(list.toString());
		map.put("list", list);
		
		try {
			if(list.size()!=0) {
				result = dao.insertUrlFromCompletedGroup(map);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	// 소셜 조회수 증가 
	public int updateViewCount(int ubid) {

		U_BookDAO dao = sqlsession.getMapper(U_BookDAO.class);
		int result = 0;
		try {
			result = dao.updateViewCount(ubid);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 함수 End
}

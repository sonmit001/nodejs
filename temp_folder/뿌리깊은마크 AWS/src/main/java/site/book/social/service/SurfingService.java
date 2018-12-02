package site.book.social.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import site.book.social.dao.S_BookmarkDAO;
import site.book.social.dao.TopDAO;
import site.book.team.dto.G_BookDTO;
import site.book.user.dto.U_BookDTO;

/**
 * @Class : SufingService.java
 * @Date : 2018. 6. 22.
 * @Author : 정진수, 정민재
 */

@Service
public class SurfingService {

	@Autowired
	private SqlSession sqlsession;
	
	//해당 회원 북마크 가져오기
	public List<U_BookDTO> getCategoryList(String nname) {
		
		TopDAO dao = sqlsession.getMapper(TopDAO.class);
		List<U_BookDTO> list = dao.getCategoryList(nname);
		
		return list;
	}
	// 여러 URL을 자신의 그룹 북마크에 추가 
	public int insertGroupBookmarkList(JSONArray obj, String uid ) {
		S_BookmarkDAO dao = sqlsession.getMapper(S_BookmarkDAO.class);
		int result = 0;
		
		List<Map<String, Object>> url_data_list = new ArrayList<>();
		
		for (int i = 0 ; i< obj.length() ; i++) { 
			String url = (String)obj.getJSONObject(i).get("url");
			if( url != null ) {
				Map<String, Object> dataList = new HashMap<>();
				
				dataList.put("gid", Integer.valueOf((String)obj.getJSONObject(i).get("gid")));
				dataList.put("pid", Integer.valueOf((String)obj.getJSONObject(i).get("pid")));
				dataList.put("uid", uid);
				dataList.put("url", url);
				dataList.put("urlname", (String)obj.getJSONObject(i).get("urlname"));
				
				url_data_list.add(dataList);
			}
		}
		
		try {
			if(url_data_list.size()!=0) {
				result = dao.insertGroupBookmarkList(url_data_list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	// 민재
	// 한 URL을 자신의 그룹 북마크에 추가 
	public int insertGroupBookmark(G_BookDTO gbook) {
		S_BookmarkDAO dao = sqlsession.getMapper(S_BookmarkDAO.class);
		int result = 0;
		
		try {
			result = dao.insertGroupBookmark(gbook);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}

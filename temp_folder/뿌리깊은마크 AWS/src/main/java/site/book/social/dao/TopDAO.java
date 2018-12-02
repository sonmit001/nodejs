/*
 * @Project : DeepRoot
 * @FileName : TopDAO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.social.dao;

import java.sql.SQLException;
import java.util.List;

import site.book.social.dto.TopDTO;
import site.book.user.dto.U_BookDTO;

/**
 * @Class : TopDAO.java
 * @Date : 2018. 6. 14.
 * @Author : 김희준
 */
public interface TopDAO {
	
	// 개인 Top5 가져오기
	public List<TopDTO> selectUTop5() throws ClassNotFoundException, SQLException;
	
	// 그룹 Top5 가져오기
	public List<TopDTO> selectGTop5() throws ClassNotFoundException, SQLException;
	
	// 작성자 파도타기 카테고리 및 URL 가져오기
	public List<U_BookDTO> getCategoryList(String nname);
	

}

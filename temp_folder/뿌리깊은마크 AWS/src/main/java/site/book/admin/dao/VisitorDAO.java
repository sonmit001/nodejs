/*
 * @Project : DeepRoot
 * @FileName : VisitorDAO.java
 * @Date : 2018. 6. 10.
 * @Author : 김희준
*/


package site.book.admin.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import site.book.admin.dto.VisitorDTO;

/**
 * @Class : VisitorDAO.java
 * @Date : 2018. 6. 10.
 * @Author : 김희준
 */
public interface VisitorDAO {
	
	// 방문자 기록
	public int insertVisitor(VisitorDTO visitor) throws ClassNotFoundException, SQLException;
	
	// 총 방문자 수
	public int selectAllVisitor() throws ClassNotFoundException, SQLException;
	
	// 날짜별 방문자 수
	public List<HashMap<String, String>> numOfVisitorByDate() throws ClassNotFoundException, SQLException;
}

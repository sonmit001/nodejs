/*
 * @Project : DeepRoot
 * @FileName : A_CategoryDAO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.admin.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import site.book.admin.dto.A_CategoryDTO;

/**
 * @Class : A_CategoryDAO.java
 * @Date : 2018. 6. 7.
 * @Author : 김희준
 */
@Repository
public interface A_CategoryDAO {
	
	// 전체 카테고리
	public List<A_CategoryDTO> selectAllCategory() throws ClassNotFoundException, SQLException;
	
	// 카테고리 추가
	public int insertCategory(A_CategoryDTO category) throws ClassNotFoundException, SQLException;
	
	// 카테고리 수정
	public int updateCategory(A_CategoryDTO category) throws ClassNotFoundException, SQLException;
	
	// 카테고리 삭제
	public int deleteCategory(int acid) throws ClassNotFoundException, SQLException;
	
	// 카테고리 색상 변경
	public int updateCategoryColor(A_CategoryDTO category) throws ClassNotFoundException, SQLException;
	
	// 카테고리 마지막 번호 가져오기
	public int selectMaxACID() throws ClassNotFoundException, SQLException;
}

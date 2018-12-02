/*
 * @Project : DeepRoot
 * @FileName : A_CategoryService.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.admin.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import site.book.admin.dao.A_CategoryDAO;
import site.book.admin.dto.A_BookDTO;
import site.book.admin.dto.A_CategoryDTO;

/**
 * @Class : A_CategoryService.java
 * @Date : 2018. 6. 13.
 * @Author : 김희준
 */
@Service
public class A_CategoryService {
	
	@Autowired
	private SqlSession sqlsession;
	
	@Autowired
	private A_BookService a_book_service;
	
	// 전체 카테고리
	public List<A_CategoryDTO> getCategorys(){
		A_CategoryDAO categoryDAO = sqlsession.getMapper(A_CategoryDAO.class);
		List<A_CategoryDTO> list = null;
		
		try {
			list = categoryDAO.selectAllCategory();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 카테고리 추가
	@Transactional
	public A_CategoryDTO addCategory(A_CategoryDTO category) {
		
		A_CategoryDAO categoryDAO = sqlsession.getMapper(A_CategoryDAO.class);
		try {
			categoryDAO.insertCategory(category);
			category.setAcid(categoryDAO.selectMaxACID());
			category.setColor("#000");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return category;
	}
	
	// 카테고리 수정
	public A_CategoryDTO updateCategory(A_CategoryDTO category) {
		A_CategoryDAO categoryDAO = sqlsession.getMapper(A_CategoryDAO.class);
		
		try {
			categoryDAO.updateCategory(category);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return category;
	}
	
	// 카테고리 삭제
	public int deleteCategory(int acid) {
		A_CategoryDAO categoryDAO = sqlsession.getMapper(A_CategoryDAO.class);
		int row = 0;
		
		try {
			row = categoryDAO.deleteCategory(acid);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	// 카테고리 별 URL
	public List<HashMap<A_CategoryDTO, List<A_BookDTO>>> urlByCategory() {
		List<HashMap<A_CategoryDTO, List<A_BookDTO>>> list = new ArrayList<>();
		
		List<A_CategoryDTO> categorylist = getCategorys();
		
		for(A_CategoryDTO category : categorylist) {
			HashMap<A_CategoryDTO, List<A_BookDTO>> map = new HashMap<>();
			List<A_BookDTO> booklist = a_book_service.getCategoryURL(category.getAcid());
			map.put(category, booklist);
			list.add(map);
		}
		return list;
	}
	
	// 카테고리 색상 변경
	public int editCategoryColor(A_CategoryDTO category) {
		A_CategoryDAO categoryDAO = sqlsession.getMapper(A_CategoryDAO.class);
		int row = 0;
		
		try {
			row = categoryDAO.updateCategoryColor(category);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
}

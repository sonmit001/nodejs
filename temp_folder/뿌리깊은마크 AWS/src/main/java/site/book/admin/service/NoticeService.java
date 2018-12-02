/*
 * @Project : DeepRoot
 * @FileName : NoticeService.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.admin.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import site.book.admin.dao.NoticeDAO;
import site.book.admin.dto.NoticeDTO;

/**
 * @Class : NoticeService.java
 * @Date : 2018. 6. 8.
 * @Author : 김희준
 */
@Service
public class NoticeService {
	@Autowired
	private SqlSession sqlsession;
	
	// 공지사항 쓰기
	@Transactional
	public NoticeDTO noticeReg(String ncontent) {
		NoticeDAO noticedao = sqlsession.getMapper(NoticeDAO.class);
		NoticeDTO notice = null;
		
		try {
			noticedao.noticeReg(ncontent);
			notice = noticedao.selectNotice(noticedao.getMaxNID());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return notice;
	}
	
	// 공지사항 가져오기
	public List<NoticeDTO> getNotices() {
		NoticeDAO noticedao = sqlsession.getMapper(NoticeDAO.class);
		List<NoticeDTO> list = null;
		
		try {
			list = noticedao.selectAllNotices();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}

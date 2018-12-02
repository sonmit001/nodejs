/*
 * @Project : DeepRoot
 * @FileName : U_BookDAO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/

package site.book.user.dao;

/**
 * @Class : SocialController.java
 * @Date : 2018. 6. 6.
 * @Author : 김명수, 김희준, 김태웅
 */
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import site.book.user.dto.S_U_BookDTO;
import site.book.user.dto.U_BookDTO;

public interface U_BookDAO {
	
	// 태웅
	// 메인 URL 내 북마크로 추가
	public int insertToMyBookmark(U_BookDTO book) throws ClassNotFoundException, SQLException;

	// 희준
	
	// 개인이 추가한 북마크수
	public List<HashMap<String, String>> numOfBookByDate() throws ClassNotFoundException, SQLException;
	
	// 소셜 개인 북마크 리스트
	public List<S_U_BookDTO> socialBookmarkList() throws ClassNotFoundException, SQLException;
	
	// 소셜 개인 북마크 삭제
	public int deleteSocialBookmark(int ubid) throws ClassNotFoundException, SQLException;
	
	// 공유하지 않는 URL 추가
	public int addUrlNotShare(U_BookDTO book) throws ClassNotFoundException, SQLException;
	
	// 명수
	// 마이북마크 왼쪽 폴더들만 보이는 JSTREE
	public List<U_BookDTO> getCategoryList(String uid) throws ClassNotFoundException, SQLException;

	// 마이북마크 왼쪽 JSTREE에서 root 카테고리 추가
	public int insertRootFolder(String uid) throws ClassNotFoundException, SQLException;

	// JSTREE 노드 생성시 db 처리와 노드 뿌리는처리에서 id 값 가져오기
	public int getMaxId() throws ClassNotFoundException, SQLException;

	// JSTREE 폴더 혹은 URL 추가
	public int addFolderOrUrl(U_BookDTO dto) throws ClassNotFoundException, SQLException;

	// JSTREE 폴더 혹은 URL 삭제
	public int deleteFolderOrUrl(String str) throws ClassNotFoundException, SQLException;

	// 마이북마크 URL 있는 오른쪽 JSTREE URL 수정
	public int editUrl(U_BookDTO dto) throws ClassNotFoundException, SQLException;

	// 왼쪽 폴더(노드) 클릭시 하위 URL 리스트
	public List<U_BookDTO> getUrl(int ubid) throws ClassNotFoundException, SQLException;

	// DND 처리 하기
	public int dropNode(HashMap<String, String> param) throws ClassNotFoundException, SQLException;

	// 마이북마크 노드 이름 수정
	public int updateNodeText(HashMap<String, String> param) throws ClassNotFoundException, SQLException;

	// URL 공유 수정 삭제 추가
	public int shareUrlEdit(U_BookDTO dto)  throws ClassNotFoundException, SQLException;

	// 완료된 그룹 URL 추가
	public int insertUrlFromCompletedGroup(Map<String,Object> map)  throws ClassNotFoundException, SQLException;

	// 소셜 리스트 조회수 증가
	public int updateViewCount(int ubid) throws ClassNotFoundException, SQLException;

}

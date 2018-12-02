package site.book.user.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import site.book.user.dto.EmailAuthDTO;
import site.book.user.dto.EmailAuthDTO;
import site.book.user.dto.UserDTO;

/**
 * @Class : UserDAO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준, 김태웅
 */
public interface UserDAO {
	
	// 태웅
	// 회원가입 ID check
	public int checkUserID(String user) throws ClassNotFoundException, SQLException;
	
	// 회원가입 Nickname check
	public int checkUserNickname(String user) throws ClassNotFoundException, SQLException;
	
	// 회원가입
	public int insertNewUser(Map<String, String> user) throws ClassNotFoundException, SQLException;
	
	// 인증코드 insert to DB(email check)
	public int insertAuthCode(EmailAuthDTO authcode) throws ClassNotFoundException, SQLException;
	
	// 인증코드 일치 확인
	public int checkAuthCode(EmailAuthDTO authcode) throws ClassNotFoundException, SQLException;
	
	// 회원 한명 출력
	public UserDTO getUser(String uid) throws ClassNotFoundException, SQLException;
	
	// 회원 한명 수정
	public int editUser(UserDTO user) throws ClassNotFoundException, SQLException;
	
	// 회원 탈퇴
	public int deleteUser(String uid) throws ClassNotFoundException, SQLException;
	
	// 비밀번호 변경(비밀번호 찾기)
	public int updatePwd(UserDTO user) throws ClassNotFoundException, SQLException;
	
	// 전체 회원 닉네임 GET
	public List<String> getAllUserNname(String nname) throws ClassNotFoundException, SQLException;
	
	// 희준
	
	//전체 회원수
	public int allUser() throws ClassNotFoundException, SQLException;
	
	// 신규 가입자 수
	public List<HashMap<String, String>> newUser() throws ClassNotFoundException, SQLException;
	
	// 블랙리스트 추가
	public int blacklist(String uid) throws ClassNotFoundException, SQLException;
	
	// 유저리스트
	public List<UserDTO> getUserList() throws ClassNotFoundException, SQLException;
	
	// 회원이 작성한 카테고리 및 URL 지우기
	public int deleteUserBook(String uid) throws ClassNotFoundException, SQLException;
	
	
	// 명수
}

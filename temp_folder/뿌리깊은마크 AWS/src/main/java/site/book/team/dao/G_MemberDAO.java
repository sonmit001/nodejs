/*
 * @Project : DeepRoot
 * @FileName : G_MemberDAO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.team.dao;

import java.sql.SQLException;
import java.util.List;

import site.book.team.dto.G_AlarmDTO;
import site.book.team.dto.G_MemberDTO;
import site.book.team.dto.G_RoleDTO;
import site.book.user.dto.UserDTO;

/**
 * @Class : G_MemberDAO.java
 * @Date : 2018. 6. 8.
 * @Author : 김희준
 */
public interface G_MemberDAO {
	
	// 회원이 진행하고 있는 모든 그룹 탈퇴(블랙리스트시 사용)
	public int leaveAllGroup(String uid) throws ClassNotFoundException, SQLException;
	
	// 참여하고 있는 그룹 나가기
	public int leaveGroup(G_MemberDTO member) throws ClassNotFoundException, SQLException;
	
	// 그룹원 추가
	public int insertGMember(G_MemberDTO member) throws ClassNotFoundException, SQLException;
	//준석
	//그룹원 리스트 가져오기
	public List<G_MemberDTO> selectGMemberlist(String gid)throws ClassNotFoundException, SQLException;
	
	// 태웅
	// 해당 유저가 들어오고자 하는 그룹의 그룹원이지 확인
	public G_RoleDTO isGroupMember(G_MemberDTO member) throws ClassNotFoundException, SQLException;
	
	// 특정 유저에게 그룹 초대 쪽지 보내기
	public int sendInviteMemo(G_AlarmDTO alarm) throws ClassNotFoundException, SQLException;
	
	// 특정 그룹원 강퇴 + 강퇴 쪽지 보내기
	public int banMember(G_MemberDTO member) throws ClassNotFoundException, SQLException;
	
	// 닉네임을 통해 상대방 ID 가져오기
	public UserDTO getToUid(String nname) throws ClassNotFoundException, SQLException;
	
	// 그룹원 권한 부여 또는 뺏기 
	public int giveManager(G_MemberDTO member_auth) throws ClassNotFoundException, SQLException;
}

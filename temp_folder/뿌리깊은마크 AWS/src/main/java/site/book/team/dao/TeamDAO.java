/*
 * @Project : DeepRoot
 * @FileName : TeamDAO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.team.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import site.book.team.dto.G_AlarmDTO;
import site.book.team.dto.S_TeamDTO;
import site.book.team.dto.TeamDTO;

/**
 * @Class : TeamDAO.java
 * @Date : 2018. 6. 8.
 * @Author : 김희준, 김명수
 */
public interface TeamDAO {
	
	// 희준
	
	// 소셜 그룹 리스트 가져오기
	public List<S_TeamDTO> socialGroupList() throws ClassNotFoundException, SQLException;
	
	// 소셜 그룹 삭제
	public int deleteSocialGroup(int gid) throws ClassNotFoundException, SQLException;
	
	// 모든 그룹 리스트 가져오기
	public List<TeamDTO> selectGroupList() throws ClassNotFoundException, SQLException;

	// 그룹 삭제하기
	public int deleteGroup(int gid) throws ClassNotFoundException, SQLException;
	
	// 그룹 추가
	public int insertGroup(String gname) throws ClassNotFoundException, SQLException;
	
	// 그룹 마지막 번호 가져오기
	public int selectLastGroupID() throws ClassNotFoundException, SQLException;
	
	// 그룹 완료
	public int completedGroup(TeamDTO team) throws ClassNotFoundException, SQLException;
	
	// 그룹 멤버 리스트 가져오기
	public List<G_AlarmDTO> getGroupMember(G_AlarmDTO alarm) throws ClassNotFoundException, SQLException;
	
	// 그룹 완료 쪽지 보내기
	public int sendCompletedMemo(List<G_AlarmDTO> alarms) throws ClassNotFoundException, SQLException;
	
	// 그룹 하나 가져오기
	public TeamDTO selectGroup(int gid) throws ClassNotFoundException, SQLException;
	
	// 명수
	// 완료 그룹 리스트 가져오기
	public List<TeamDTO> getCompletedTeamList(String uid);
	
	// 완료 되지 않은 내 그룹 리스트 가져오기
	public List<TeamDTO> getTeamList(String uid) throws ClassNotFoundException, SQLException;

	// 완료된 그룹 삭제하기
	public int deleteCompletedTeam(String uid);
	
	// 그룹 리스트 조회수 증가
	public int updateViewCount(int gid) throws ClassNotFoundException, SQLException;
}

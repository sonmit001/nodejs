/*
 * @Project : DeepRoot
 * @FileName : G_AlarmDAO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.team.dao;

import java.sql.SQLException;
import java.util.List;

import site.book.team.dto.G_AlarmDTO;
import site.book.team.dto.G_MemberDTO;
import site.book.team.dto.G_MyAlarmDTO;

/**
 * @Class : G_AlarmDAO.java
 * @Date : 2018. 6. 8.
 * @Author : 김희준
 */
public interface G_AlarmDAO {
	
	// 회원이 보내거나 받은 모든 그룹알림 지우기(블랙리스트시 사용)
	public int deleteAllGroupAlarm(String uid) throws ClassNotFoundException, SQLException;
	
	// 태웅
	// 이미 보낸 알람 쪽지인지 확인
	public int alreadySend(G_AlarmDTO alarm) throws ClassNotFoundException, SQLException;
	
	// 나에게 온 그룹 초대/완료/강퇴 쪽지 리스트 확인
	public List<G_MyAlarmDTO> getAlarmList(String uid) throws ClassNotFoundException, SQLException;
	
	// 쪽지 확인 후 자동 삭제
	public int deleteMemo(G_AlarmDTO alarm) throws ClassNotFoundException, SQLException;
	
	// 그룹 초대 쪽지 승인을 통한 그룹 가입
	public int joinGroup(G_MemberDTO member) throws ClassNotFoundException, SQLException;
	
	// 이미 가입된 그룹이라면? 그룹 초대 쪽지 승인했지만 ...
	public int alreadyJoin(G_MemberDTO member) throws ClassNotFoundException, SQLException;
}

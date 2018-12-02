/*
 * @Project : DeepRoot
 * @FileName : G_AlarmService.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.team.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.book.team.dao.G_AlarmDAO;
import site.book.team.dto.G_AlarmDTO;
import site.book.team.dto.G_MemberDTO;
import site.book.team.dto.G_MyAlarmDTO;

@Service
public class G_AlarmService {
	//태웅
	@Autowired
	private SqlSession sqlsession;
	
	
	// 태웅
	// 쪽지 보내기 전 체크: 중복 초대/강퇴/완료 쪽지 보내기 처리하기
	public boolean alreadySend(G_AlarmDTO alarm, String alarm_kind){
		G_AlarmDAO g_alarmDAO = sqlsession.getMapper(G_AlarmDAO.class);
		String kind = alarm_kind.toUpperCase();
		boolean already_invite = false;
		
		if(kind.equals("FIRE")) { alarm.setGaid(3); } 
		else if(kind.equals("COMPLETE")){ alarm.setGaid(2); } 
		else { alarm.setGaid(1); }
		
		try {
			if( g_alarmDAO.alreadySend(alarm) > 0) {
				already_invite = true;
			}
			
		} catch (Exception e) {
			/*e.printStackTrace();*/
		}
		
		return already_invite;
	}
	
	// 내가 받은 그룹 초대/완료/강퇴 쪽지 리스트 출력
	public List<G_MyAlarmDTO> getAlarmList(String uid){
		G_AlarmDAO g_alarmDAO = sqlsession.getMapper(G_AlarmDAO.class);
		List<G_MyAlarmDTO> alarm_list = new ArrayList<>();
		
		try {
			alarm_list = g_alarmDAO.getAlarmList(uid);
		} catch (Exception e) {
			/*e.printStackTrace();*/
		}
		
		return alarm_list;
	}
	
	// 쪽지 확인 후, 쪽지 자동 삭제
	public int deleteMemo(G_AlarmDTO alarm) {
		G_AlarmDAO g_alarmDAO = sqlsession.getMapper(G_AlarmDAO.class);
		int isDelete = 0;

		try {
			isDelete = g_alarmDAO.deleteMemo(alarm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isDelete;
	}
	
	// 그룹 초대 쪽지 승인, 그룹 가입
	public int joinGroup(G_MemberDTO member) {
		G_AlarmDAO g_alarmDAO = sqlsession.getMapper(G_AlarmDAO.class);
		int isJoined = 0;

		try {
			if(g_alarmDAO.alreadyJoin(member) > 0) {
				isJoined = -1;
			}else {
				isJoined = g_alarmDAO.joinGroup(member);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isJoined;
	}
}

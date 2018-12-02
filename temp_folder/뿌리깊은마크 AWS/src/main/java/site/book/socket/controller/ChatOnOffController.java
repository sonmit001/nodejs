package site.book.socket.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import site.book.socket.dto.OnlineMemberDTO;
import site.book.socket.service.OnOffMemberSingleton;

/**
 * @Class : ChatController.java
 * @Date : 2018. 6. 30.
 * @Author : 김태웅
 */
@Controller
public class ChatOnOffController {
	/*
	// 접속시, 온라인 상태 소켓 전송
    @MessageMapping("/online/{room}")
    @SendTo("/subscribe/online/{room}")
    public OnlineMemberDTO sendOnlineMessage(@DestinationVariable("room") String room, @Payload OnlineMemberDTO member) throws Exception {
		
    	// 온라인 유저 구독하면, Map(gid: [])에 추가
    	Map<String, Map<String, String>> online_list = OnOffMemberSingleton.getInstance();
    	
    	// 그룹 생성후 처음 들어온 경우,
    	if( !online_list.containsKey(room) ) {
    		online_list.put(room, new HashMap<String, String>());
    	}
    	
    	// 해당 당의 온라인 유저에 추가
    	if( member != null ) {
    		// 해당 방의 현재 로그인 중인 리스트 GET
    		Map<String, String> online_users = online_list.get(room);
    		
        	online_users.put(member.getNname(), member.getStatus());
        	
        	online_list.put(room, online_users); // 다시 온라인 Map에 저장
    	}
    	//System.out.println(online_list);
    	
        return member;
    }
    
    // 종료시, 오프라인 상태 소켓 전송
    @MessageMapping("/offline/{room}")
    @SendTo("/subscribe/offline/{room}")
    public OnlineMemberDTO sendOfflineMessage(@DestinationVariable("room") String room, @Payload OnlineMemberDTO member) throws Exception {
    	
    	Map<String, Map<String, String>> online_list = OnOffMemberSingleton.getInstance();
 
    	// 그룹 생성후 처음 들어온 경우,
    	if( !online_list.containsKey(room) ) {
    		online_list.put(room, new HashMap<String, String>());
    	}
    	
    	// 해당 당의 온라인 유저에 추가
    	if( member != null ) {
    		// 해당 방의 현재 로그인 중인 리스트 GET
    		Map<String, String> online_users = online_list.get(room);
    		
			online_users.remove(member.getNname());
			
			if(online_users.size() == 0) {
				online_list.remove(room);
			}else {
				online_list.put(room, online_users); // 다시 온라인 Map에 저장
			}
    	}
    	//System.out.println(online_list);
    	
        return member;
    }*/
    
    
}

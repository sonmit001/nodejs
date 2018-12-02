package site.book.socket.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import site.book.socket.dto.SocketAlarmDTO;

/**
 * @Class : ChatController.java
 * @Date : 2018. 7. 2.
 * @Author : 김태웅
 */
@Controller
public class HeaderAlarmController {
	
	/*// Header, 소켓 통신: 초대/강퇴 쪽지 수신
    @MessageMapping("/alarm/{uid}")
    @SendTo("/subscribe/alarm/{uid}")
    public SocketAlarmDTO sendOnlineMessage(@DestinationVariable("uid") String uid, @Payload SocketAlarmDTO alarm) throws Exception {
    	
    	//System.out.println(alarm);
        return alarm;
    }
    
    // Header, 소켓 통신: 그룹 완료 쪽지 수신
    @MessageMapping("/alarm")
    @SendTo("/subscribe/alarm")
    public SocketAlarmDTO sendCompleteMessage(@Payload SocketAlarmDTO alarm) throws Exception {
    	
    	//System.out.println(alarm);
        return alarm;
    }*/
}

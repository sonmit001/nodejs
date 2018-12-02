/*
 * @Project : DeepRoot
 * @FileName : ChatController.java
 * @Date : 2018. 6. 27.
 * @Author : 김희준
*/


package site.book.socket.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import site.book.socket.JstreeAlarm;

/**
 * @Class : ChatController.java
 * @Date : 2018. 6. 27.
 * @Author : 김희준
 */
@Controller
public class ChatController {
	
	
    //명수
    //jstree 변경 메시지
    @MessageMapping("/JSTREE/{room}")
    @SendTo("/subscribe/JSTREE/{room}")
    public JstreeAlarm sendJstree(@DestinationVariable("room") String room, JstreeAlarm message, SimpMessageHeaderAccessor headerAccessor, Principal principal) {
    	
        return message;
    }
}

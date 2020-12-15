//package vn.yotel.jobsearch247.cms.web.controller;
//
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.stereotype.Controller;
//import vn.yotel.jobsearch247.core.jpa.ChatMessage;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@Controller
//public class ChatController {
//
//    @MessageMapping ( value = "/chat.sendMessage")
//    @SendTo( "/topic/public")
//   public ChatMessage sendMessage (@Payload ChatMessage chatMessage) {
//       return chatMessage;
//   }
//
//   public ChatMessage addUser (@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
//        // add username in web socket session.
//        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
//        return chatMessage;
//   }
//
//}

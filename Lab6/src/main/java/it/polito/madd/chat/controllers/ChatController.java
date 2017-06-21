package it.polito.madd.chat.controllers;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import it.polito.madd.chat.model.ChatMessage;
import it.polito.madd.chat.model.ChatUser;
import it.polito.madd.chat.model.ChatRate;
import it.polito.madd.chat.model.JoinMessage;
import it.polito.madd.chat.model.UserDirectory;
import it.polito.madd.chat.services.ChatService;
import it.polito.madd.entities.User;
import it.polito.madd.services.UserService;

@Controller(value="/app")
public class ChatController implements ApplicationListener<ApplicationEvent> {
	
	@Autowired
	private UserDirectory users;
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private UserService userService;
	
	
	  @MessageMapping("/join") 
	  //@SendTo("/topic/presence")
	  public void join(
			  MessageHeaders hs,
			  @Payload JoinMessage msg, Principal principal) {
		  
	    String sessionId=(String)hs.get("simpSessionId");
	    
	    /*
	     * Cannot access an user from SecurityContext normally
	     * 
	     * http://docs.spring.io/spring/docs/current/spring-framework-reference/html/websocket.html#websocket-stomp-authentication
	     */
	    
	    User u = userService.findByEmail(principal.getName());
	    				
	    ChatUser cuser = new ChatUser(u.getNickname(),msg.getTopicName(),u.getEmail());
	    
	    users.putUser(sessionId,cuser);
	    
	    chatService.updateUsersList(msg.getTopicName());
	    
	    /*for the user to receive last 10 messages*/
	    chatService.retrieveLastMessages(msg.getTopicName(), principal.getName());
	    
	    chatService.retrieveAlerts(principal.getName());
	  }
	  
	  @MessageMapping("/chat") 
	  //@SendTo("/topic/chat")
	  public void sendMessage(
			  MessageHeaders hs, 						
			  ChatMessage chatMessage) {
		  
	    String sessionId=(String)hs.get("simpSessionId");
	    ChatUser u = users.getUser(sessionId);
	    
	    chatMessage.setDate(new Date())
	       .setUsername(u.getEmail())
	       .setNickname(u.getNickname());
	      
	    chatService.sendMessage(users.getUser(sessionId).getTopicname(), chatMessage);
	  }
	  
	  @MessageMapping("/rate") 
	  //@SendTo("/topic/chat")
	  public void rateAlert(
			  MessageHeaders hs, 						
			  ChatRate chatRate) {
		  
	    String sessionId=(String)hs.get("simpSessionId");
	    ChatUser u = users.getUser(sessionId);
	      
	    chatRate.setUsername(u.getEmail());
	    
	    chatService.sendRate(chatRate);
	  }
	
	  
	  @Override
	  public void onApplicationEvent(ApplicationEvent e) {
	    if (e instanceof SessionDisconnectEvent) {
	      SessionDisconnectEvent sde=(SessionDisconnectEvent)e;
	      String userTopic = users.getUser(sde.getSessionId()).getTopicname();
	      users.removeUser(sde.getSessionId());
	      
	      chatService.updateUsersList(userTopic);
	    }
	  }
}

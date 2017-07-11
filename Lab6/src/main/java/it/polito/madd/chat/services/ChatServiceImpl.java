package it.polito.madd.chat.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import it.polito.madd.chat.model.ChatMessage;
import it.polito.madd.chat.model.ChatRate;
import it.polito.madd.chat.model.Roster;
import it.polito.madd.chat.model.UserDirectory;
import it.polito.madd.entities.Alert;
import it.polito.madd.entities.Message;
import it.polito.madd.entities.Rate;
import it.polito.madd.repositories.AlertRepository;
import it.polito.madd.repositories.MessageRepository;

@Service
public class ChatServiceImpl implements ChatService {
	
	@Autowired
    private UserDirectory users;
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	private MessageRepository messageRepo;
	
	@Autowired
	private AlertRepository alertRepository;
	
	@Value("#{'${alerts.expireAfter.minutes}'}")
	private Integer expireAfter;
	
	public void updateUsersList (String topic){
		
		String topicUsersList = "/topic/presence/"+  topic;
	
		/*
		 * Added a filter to select users registered to the topic, before was sending all users to everyone
		 * 'https://www.sitepoint.com/java-8-streams-filter-map-reduce/'
		 */
		
		messagingTemplate.convertAndSend(topicUsersList,new Roster(users.getUsers().stream()
																	.filter(u -> u.getTopicname().equals(topic))
																	.collect(Collectors.toList()))); //Prendo l'elenco degli utenti
					
	}
	
	public void sendMessage (String topic, ChatMessage msg) {
		String chatMessagesList = "/topic/chat/"+ topic;
		String alertTopic = "/topic/chat/alerts";
		
		Alert alert = msg.extractAlert();
		
		if (alert != null){
			if (alert.getId() == null)
			{
				// new alert
				alertRepository.save(alert);
				messagingTemplate.convertAndSend(alertTopic, alert);
			}
			else
				// update alert ref
				alertRepository.updateReferenceTs(alert.getId(), alert.getLastAccessTimestamp());	
		}
	    
	    Message mess = new Message(msg.getMessage(), topic, msg.getUsername(), msg.getNickname(), msg.getDate());
	    
	    if (msg.getAlertId() != null)	// reference to existing alert
	    	mess.setAlertId(msg.getAlertId());
	    else if (alert != null)	// 	new alert
	    	mess.setAlertId(alert.getId());
	    
	    messageRepo.save(mess);
	    
	    messagingTemplate.convertAndSend(chatMessagesList, msg);
	}
	
	public void sendRate (ChatRate chatRate) {
		String ratesList = "/topic/chat/rates/";
		
		Rate rate = new Rate(chatRate.getUsername(), chatRate.getValue());
		
		alertRepository.updateUserRate(chatRate.getAlertId(), rate);
	}
	
	public void retrieveLastMessages (String topic, String user) {
		String chatMessagesList = "/queue/"+ topic;
		List<Message> msgs= new ArrayList<Message>();
		msgs = messageRepo.findTop10ByTopicOrderByTimestampDesc(topic);
		
		Collections.reverse(msgs);
		
		List<ChatMessage> chmsgs = msgs.stream().map(m -> {
			ChatMessage cm = new ChatMessage();
			cm.setDate(m.getTimestamp())
			  .setMessage(m.getText())
			  .setNickname(m.getNickname())
			  .setUsername(m.getUserEmail());
			  return cm;
		}).collect(Collectors.toList());
		
		messagingTemplate.convertAndSendToUser(user, chatMessagesList, chmsgs);
				
	}
    
	
	public void retrieveAlerts(String user){
		String chatMessagesList = "/queue/alerts";
		
		List<Alert> alerts = alertRepository.findAll();
		//check the last access time of every alert and remove the expired
		Iterator<Alert> it = alerts.iterator();
		while (it.hasNext()) {
			Alert alert = it.next();
			// calc the difference in minutes
			Long minutesBetween = ((new Date()).getTime() - alert.getLastAccessTimestamp().getTime()) / (60 * 1000) % 60;
			// TODO remove next comment
			// optional: for debug purpose use the next line to get 5 seconds interval
			//Long minutesBetween = ((new Date()).getTime() - alert.getLastAccessTimestamp().getTime()) / (1000) % 60;
			if (minutesBetween > expireAfter)
			{
				// delete the item from db and then remove it from list
				alertRepository.deleteAlertById(alert.getId());
				it.remove();
			}
			else
			{	// update the lastAccessTimestamp and sync to db
				alert.setLastAccess();
				alertRepository.save(alert);
			}
		}	
		
		messagingTemplate.convertAndSendToUser(user, chatMessagesList, alerts);
	}
}

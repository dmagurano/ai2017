package it.polito.madd.chat.services;

import it.polito.madd.chat.model.ChatMessage;

public interface ChatService {
	
	void updateUsersList (String topic);
	void sendMessage (String topic, ChatMessage msg);
	void retrieveLastMessages (String topic, String user);
	void retrieveAlerts(String name);

}

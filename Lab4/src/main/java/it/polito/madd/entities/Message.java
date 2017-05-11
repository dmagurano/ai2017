package it.polito.madd.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class Message {
	
	@Id
	private String id;
	
	private String text;
	
	private String topic;
	
	@Field("email")
	private String userEmail;
	
	private String nickname;
	
	private Date timestamp;
	
}

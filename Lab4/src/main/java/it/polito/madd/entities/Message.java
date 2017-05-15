package it.polito.madd.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Message {
	
	@Id
	private String id;
	
	private String text;
	
	private String topic;
	
	@Field("email")
	private String userEmail;
	
	private String nickname;
	
	private Date timestamp;

	@JsonIgnore
	public String getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	@JsonIgnore
	public String getTopic() {
		return topic;
	}

	@JsonIgnore
	public String getUserEmail() {
		return userEmail;
	}

	@JsonIgnore
	public String getNickname() {
		return nickname;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
	
	
}

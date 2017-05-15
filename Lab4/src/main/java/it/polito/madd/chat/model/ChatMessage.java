package it.polito.madd.chat.model;

import java.util.Date;

public class ChatMessage {

		private String username;
		private String nickname;
		private String message;
		private Date date;
		
		
		public ChatMessage() {
		}
		
		public ChatMessage(String username, String nickname, String message, Date date){
			this.username = username;
			this.nickname = nickname;
			this.message = message;
			this.date = date;
		}
		
		public Date getDate() {
			return date;
		}
		public ChatMessage setDate(Date date) {
			this.date = date;
			return this;
		}
		public String getUsername() {
			return username;
		}
		
		public String getNickname() {
			return nickname;
		}

		public ChatMessage setNickname(String nickname) {
			this.nickname = nickname;
			return this;
		}

		public ChatMessage setUsername(String username) {
			this.username = username;
			return this;
		}
		public String getMessage() {
			return message;
		}
		public ChatMessage setMessage(String message) {
			this.message = message;
			return this;
		}
		@Override
		public String toString() {
			return "ChatMessage [user=" + username + ", message=" + message + "]";
		}
	

	
}

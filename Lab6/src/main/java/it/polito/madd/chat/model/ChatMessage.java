package it.polito.madd.chat.model;

import java.util.Date;

import it.polito.madd.entities.Alert;

public class ChatMessage {

		private String username;
		private String nickname;
		private String message;
		private Date date;
		// alert params, can be null
		private Double lat;
		private Double lng;
		private String type;
		
		
		public ChatMessage() {
		}
		
		public ChatMessage(String username, String nickname, String message, Date date){
			this.username = username;
			this.nickname = nickname;
			this.message = message;
			this.date = date;
		}
		
		public ChatMessage(String username, String nickname, String message, Date date, Double lat, Double lng,
				String type) {
			this.username = username;
			this.nickname = nickname;
			this.message = message;
			this.date = date;
			this.lat = lat;
			this.lng = lng;
			this.type = type;
		}
		
		public Alert extractAlert() {
			if (lat == null || lng == null || type == null)
				return null;
			else
				return new Alert(lat,lng,date,username,type);
		}

		public Double getLat() {
			return lat;
		}

		public ChatMessage setLat(Double lat) {
			this.lat = lat;
			return this;
		}

		public Double getLng() {
			return lng;
		}

		public ChatMessage setLng(Double lng) {
			this.lng = lng;
			return this;
		}

		public String getType() {
			return type;
		}

		public ChatMessage setType(String type) {
			this.type = type;
			return this;
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

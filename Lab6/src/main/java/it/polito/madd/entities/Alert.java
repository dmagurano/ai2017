package it.polito.madd.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Alert {
	
	@Id
	private String id;

	private Double lat;
	private Double lng;
	private List<Rate> rates;
	private Date recvTimestamp;
	private Date lastAccessTimestamp;
	private String userEmail;
	private String nickname;
	private String address;

	private String type;
	
	public Alert() {
		this.rates = new ArrayList<Rate>();
	}
	
	public Alert(String id, Date ts) {
		this.id = id;
		this.lastAccessTimestamp = ts;
	}
	
	public Alert(Double lat, Double lng, String address, Date timestamp, String userEmail, String nickname, String type) {
		this.lat = lat;
		this.lng = lng;
		this.address = address;
		this.recvTimestamp = timestamp;
		this.lastAccessTimestamp = timestamp;
		this.userEmail = userEmail;
		this.nickname = nickname;
		this.type = type;
		this.rates = new ArrayList<Rate>();
	}
	
	public Alert(Double lat, Double lng, String address, Date start, Date access, String userEmail, String nickname, String type) {
		this.lat = lat;
		this.lng = lng;
		this.address = address;
		this.recvTimestamp = start;
		this.lastAccessTimestamp = access;
		this.userEmail = userEmail;
		this.nickname = nickname;
		this.type = type;
		this.rates = new ArrayList<Rate>();
	}
	
	public Double getLat() {
		return lat;
	}
	
	public void setLat(Double lat) {
		this.lat = lat;
	}
	
	public Double getLng() {
		return lng;
	}
	
	public void setLng(Double lng) {
		this.lng = lng;
	}
	
	public Date getRecvTimestamp() {
		return recvTimestamp;
	}
	
	public void setRecvTimestamp(Date timestamp) {
		this.recvTimestamp = timestamp;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	public Double getRates() {
		if(rates.size() == 0)
			return 0.0;
		
		Double sum = 0.0;
		for (Rate r : rates){
			sum += r.getValue();
		}
		
		return sum/(double)rates.size();
	}

	public void setRate(String email, Integer value) {
		for (int i = 0; i < rates.size(); i++){
			Rate rate = rates.get(i);
			
			if (rate.getEmail().equals(email)){
				rate.setValue(value);
				rates.set(i, rate);
				return;
			}
		}
		
		this.rates.add(new Rate(email, value));
	}

	public Date getLastAccessTimestamp() {
		return lastAccessTimestamp;
	}

	public void setLastAccessTimestamp(Date lastAccess) {
		this.lastAccessTimestamp = lastAccess;
	}
	
	public void setLastAccess() {
		this.lastAccessTimestamp = new Date();
	}
}

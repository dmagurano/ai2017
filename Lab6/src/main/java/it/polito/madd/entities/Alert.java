package it.polito.madd.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Alert {
	
	@Id
	private String id;

	private Double lat;
	private Double lng;
	private Valuation valuation;
	private Date timestamp;
	private String userEmail;
	private String type;
	
	public Alert(Double lat, Double lng, Date timestamp, String userEmail, String type) {
		this.lat = lat;
		this.lng = lng;
		this.timestamp = timestamp;
		this.userEmail = userEmail;
		this.valuation = new Valuation();
		this.type = type;
	}
	
	public void addVote(String email, Integer vote) {
		this.valuation.addVote(email, vote);
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
	public Valuation getValuation() {
		return valuation;
	}
	public void setValuation(Valuation valuation) {
		this.valuation = valuation;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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
	
	
}

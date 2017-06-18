package it.polito.madd.chat.model;

public class ChatValuation {
	private String alertId;
	private Integer rate;
	
	public ChatValuation(String alertId, Integer rate) {
		this.alertId = alertId;
		this.rate = rate;
	}

	public String getAlertId() {
		return alertId;
	}
	
	public void setAlertId(String alertId) {
		this.alertId = alertId;
	}
	
	public Integer getRate() {
		return rate;
	}
	
	public void setRate(Integer rate) {
		this.rate = rate;
	}
}

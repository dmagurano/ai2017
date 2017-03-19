package it.polito.madd.model;

public class CreditCard {
	private String number;
	private String holder;
	private String cvv;
	
	public CreditCard(String number, String holder, String cvv) {
		super();
		this.number = number;
		this.holder = holder;
		this.cvv = cvv;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getHolder() {
		return holder;
	}
	public void setHolder(String holder) {
		this.holder = holder;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	
	
}

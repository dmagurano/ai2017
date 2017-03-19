package it.polito.madd.model;

public class Ticket {
	private TicketType type;
	private float price;
	
	public Ticket(TicketType type) { 
		this.type = type;
		this.price = TicketPrice.getPrice(type);
	}
	
	public TicketType getType() {
		return type;
	}
	public void setType(TicketType type) {
		this.type = type;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	
}

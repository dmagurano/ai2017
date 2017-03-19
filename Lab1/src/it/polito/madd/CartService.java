package it.polito.madd;

import java.util.HashMap;

import it.polito.madd.model.Ticket;

public interface CartService {
	
	public void add(Ticket ticket, int quantity) throws Exception;
	public void remove(Ticket ticket, int quantity) throws Exception;
	public void modify(Ticket ticket, int quantity) throws Exception;
	public float getTotal();
	public HashMap<Ticket,Integer> getItems();
	
}

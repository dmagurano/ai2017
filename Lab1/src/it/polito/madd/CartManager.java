package it.polito.madd;

import java.util.HashMap;
import java.util.Map;

import it.polito.madd.model.Ticket;
import it.polito.madd.model.TicketType;

public class CartManager implements CartService {

	private HashMap<Ticket, Integer> tickets = new HashMap<Ticket, Integer>();
	
	@Override
	public void add(Ticket ticket, int quantity) throws Exception {
		if (quantity < 0)
			throw new Exception("The quantity is negative or 0");
		Integer current = null;
		for(Map.Entry<Ticket, Integer> t : tickets.entrySet()){
			if(t.getKey().getType().equals(ticket.getType()))
			{
				ticket = t.getKey();
				current = t.getValue();
			}
		}
		if (current == null)
			current = new Integer(0);
		tickets.put(ticket, current + quantity);
	}

	@Override
	public void remove(Ticket ticket, int quantity) throws Exception {
		if (quantity <= 0)
			throw new Exception("The quantity is negative or 0");
		if (tickets.isEmpty())
			throw new Exception("The cart is empty");
		Integer current = tickets.get(ticket);
		if (current == null)
			throw new Exception("The item is not in the cart");
		Integer newValue = current - quantity;
		if (newValue < 0)
			throw new Exception("Quantity is greater than the current value");
		if (newValue == 0)
			tickets.remove(ticket);
		else
			tickets.put(ticket, newValue);
	}

	@Override
	public float getTotal() {
		float total = 0;
		for(Map.Entry<Ticket, Integer> item: tickets.entrySet())
			total += item.getKey().getPrice()*item.getValue();
		return total;
	}

	@Override
	public HashMap<Ticket, Integer> getItems() {
		return tickets;
	}

	@Override
	public void modify(TicketType type, int quantity) throws Exception {
		Ticket ticket = null;
		
		for(Map.Entry<Ticket, Integer> m : tickets.entrySet()){
			if(m.getKey().getType().equals(type))
				ticket = m.getKey();
		}
		
		if (ticket == null)
			throw new Exception("The item is not in the cart");
		
		if (quantity < 0)
			throw new Exception("The quantity is negative");
		
		if (quantity == 0)
			tickets.remove(ticket);
		else
			tickets.put(ticket, quantity);
	}

	@Override
	public void clear() {
		tickets.clear();
	}

}

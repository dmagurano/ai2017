package it.polito.madd;

import java.util.HashMap;
import java.util.Map;

import it.polito.madd.model.Ticket;

public class CartManager implements CartService {

	private HashMap<Ticket, Integer> tickets = new HashMap<Ticket, Integer>();
	
	@Override
	public void add(Ticket ticket, int quantity) throws Exception {
		if (quantity < 0)
			throw new Exception("The quantity is negative or 0");
		Integer current = tickets.get(ticket);
		if (current == null)
			current = 0;
		tickets.put(ticket, current + quantity);
	}

	@Override
	public void remove(Ticket ticket, int quantity) throws Exception {
		if (quantity <= 0)
			throw new Exception("The quantity is negative or 0");
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
	public void modify(Ticket ticket, int quantity) throws Exception {
		if (quantity < 0)
			throw new Exception("The quantity is negative");
		if (!tickets.containsKey(ticket))
			throw new Exception("The item is not in the cart");
		if (quantity == 0)
			tickets.remove(ticket);
		else
			tickets.put(ticket, quantity);
	}

}

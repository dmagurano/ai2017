package it.polito.madd.model;

import java.util.HashMap;

public class TicketPrice {
	private static HashMap<TicketType, Float> prices = new HashMap<TicketType, Float>();
	private static Boolean initialized = false;
	
	private static void initialize() {
		float p = 1;
		for (TicketType type: TicketType.values())
		{
			prices.put(type, p);
			p *= 2;
		}
	}
	
	public static float getPrice(TicketType type)
	{
		if (!initialized)
			initialize();
		return prices.get(type);
	}
	
}

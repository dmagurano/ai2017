package it.polito.ai.Util;

import java.util.ArrayList;
import java.util.List;

import it.polito.ai.Lab3.Entities.BusLine;

public class StopManager {
	
	public List<BusLine> getStopBusLines(String busStop){
		System.out.println("busStop: " + busStop);
		
		List<BusLine> busLines = new ArrayList<BusLine>();
				
		for (Object o : HibernateUtil.getSessionFactory().getCurrentSession()
			.createQuery("select distinct bls.busLine from BusLineStop bls where bls.busStop.id = :busStop")
			.setParameter("busStop", busStop)
			.list()){
			busLines.add((BusLine) o);
		}
		
		return busLines;
	}
}
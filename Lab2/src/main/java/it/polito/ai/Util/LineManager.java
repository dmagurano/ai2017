package it.polito.ai.Util;

import java.util.ArrayList;
import java.util.List;

import it.polito.ai.Lab2.Entities.BusLine;
import it.polito.ai.Lab2.Entities.BusStop;

public class LineManager {

	public List<BusLine> getAllLines(){
		// System.out.println("entering LineManager.getAllLines()");
		
		List<BusLine> busLines = new ArrayList<BusLine>();
		
		for (Object bl : HibernateUtil.getSessionFactory().getCurrentSession()
				.createQuery("from BusLine bl").list())
			busLines.add((BusLine)bl);

		// System.out.println("exiting LineManager.getAllLines()");
		
		return busLines;
	}
	
	public List<BusStop> getLineStops(String line){
		// System.out.println("entering LineManager.getLinesStops()");
		System.out.println("line: " + line);
		
		List<BusStop> busStops = new ArrayList<BusStop>();
				
		for (Object o : HibernateUtil.getSessionFactory().getCurrentSession()
			.createQuery("select bls.busStop from BusLineStop bls where bls.busLine.id = :line")
			.setParameter("line", line)
			.list()){
			busStops.add((BusStop) o);
		}

		// System.out.println("exiting LineManager.getLinesStops()");
		
		return busStops;
	}
}
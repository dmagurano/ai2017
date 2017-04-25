package it.polito.ai.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import it.polito.ai.Lab2.Entities.BusLine;
import it.polito.ai.Lab2.Entities.BusStop;

public class LineManager {

	public List<BusLine> getAllLines(){
		// System.out.println("entering LineManager.getAllLines()");
		
		List<BusLine> busLines = new ArrayList<BusLine>();
		
		for (Object bl : HibernateUtil.getSessionFactory().getCurrentSession()
				.createQuery("from BusLine bl order by bl.id").list())
			busLines.add((BusLine)bl);

		// System.out.println("exiting LineManager.getAllLines()");
		
		return busLines;
	}
	
	public List<BusStop> getLineStops(String line){
		// System.out.println("entering LineManager.getLinesStops()");
		//System.out.println("line: " + line);
		
		List<BusStop> busStops = new ArrayList<BusStop>();
				
		for (Object o : HibernateUtil.getSessionFactory().getCurrentSession()
			.createQuery("select bls.busStop from BusLineStop bls where bls.busLine.id = :line order by bls.seqencenumber")
			.setParameter("line", line)
			.list()){
			busStops.add((BusStop) o);
		}

		// System.out.println("exiting LineManager.getLinesStops()");
		
		return busStops;
	}
	
	//Sequence numbers with busStops
	public Map<Short,BusStop> getLineStopsWithSeq(String line){
		// System.out.println("entering LineManager.getLinesStops()");
		//System.out.println("line: " + line);
		
		Map<Short,BusStop> map = new HashMap<Short,BusStop>();
		
		@SuppressWarnings("unchecked")
		List<Object[]> rows = (List<Object[]>) HibernateUtil.getSessionFactory().getCurrentSession()
		.createQuery("select bls.seqencenumber, bls.busStop from BusLineStop bls where bls.busLine.id = :line order by bls.seqencenumber")
		.setParameter("line",line).list();
		
		for(Object[] row : rows){
			
			map.put((Short) row[0], (BusStop) row[1]);
			
		}

		// System.out.println("exiting LineManager.getLinesStops()");
		
		return map;
	}
	
	
}
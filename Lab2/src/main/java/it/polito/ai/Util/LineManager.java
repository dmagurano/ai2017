package it.polito.ai.Util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class LineManager {

	public List<String> getAllLines(){
		
		List<String> lines = new ArrayList<String>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String hql = "select bl.line from BusLine bl";
		Query query = s.createQuery(hql);
		lines = query.list();
		
		return lines;
		
		
	}
	
	public List<String> getAllStopsFromLine(String line){
		
		List<String> stops = new ArrayList<String>();
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		String hql = "select bs.id from BusStop bs join bs.busLines bl where bl.line=:line";
		Query query = s.createQuery(hql);
		stops = query.list();
		
		return stops;
		
	}
}

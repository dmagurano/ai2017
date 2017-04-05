package it.polito.ai.Lab2.Entities;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import it.polito.ai.Util.HibernateUtil;

public class Main {

	private static SessionFactory sf = HibernateUtil.getSessionFactory();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Session s=sf.getCurrentSession();
	    Transaction tx=null;
	    
	    try {
	      tx=s.beginTransaction();
	      
	      /*
	      BusLine bl = s.get(BusLine.class, "3");
	      if(bl == null){System.out.println("Niente linea");}
	      System.out.println("Linea:  " +bl.getLine());
	      */
	      //bl inner join bl.busStops.id
	      List<String> lines = new ArrayList<String>();
			//Session s = HibernateUtil.getSessionFactory().getCurrentSession(); 
	      
			String hql = "select bs.id from BusStop bs join bs.busLines bl where bl.line=:line";
			Query query = s.createQuery(hql);
			query.setParameter("line", "METRO");
			lines = query.list();
			System.out.println("Ecco");
			//for(String i : lines){
				
				//System.out.println(i);
			//}
			System.out.println(lines.get(0));
	      
	      tx.commit();
	      
	    } catch (Throwable ex) {
	    	
	      if (tx!=null) tx.rollback();
	      
	      
	    } finally {
	    	
	      if (s!=null && s.isOpen()) s.close(); 
	      s=null;
	      
	    }

	}

}

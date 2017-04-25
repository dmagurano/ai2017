package it.polito.ai.Lab2.Entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.vividsolutions.jts.geom.Geometry;

import it.polito.ai.Lab3.Edge;
import it.polito.ai.Util.HibernateUtil;
import it.polito.ai.Util.LineManager;
import javassist.bytecode.Descriptor.Iterator;

public class Main {

	private static SessionFactory sf = HibernateUtil.getSessionFactory();
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Session s=sf.getCurrentSession();
	    Transaction tx=null;
	    
	    try {
	      tx=s.beginTransaction();
	      
	      
	      BusLine bl = s.get(BusLine.class, "3");
	      if(bl == null){System.out.println("Niente linea");}
	      System.out.println("Linea:  " +bl.getLine());
	      
	      //Get all lines
	      String hql = "SELECT line FROM BusLine";
	      Query query = s.createQuery(hql);
	      List<String> lines = query.list();
	      for(String i : lines)
	    	  System.out.println(i);
	      
	      LineManager lm = new LineManager();
	      Map<Short,BusStop> map = lm.getLineStopsWithSeq("METRO");
			
			
//			Query q =  
//				s.createQuery("select bls.seqencenumber, bls.busStop from BusLineStop bls where bls.busLine.id = :line order by bls.seqencenumber");
//				q.setParameter("line", "METRO");
//			List<Object[]> lista = (List<Object[]>) q.list();
//			for(Object[] la : lista){
//				map.put((Short) la[0], (BusStop) la[1]);
//				System.out.println(la[0]);
//			}
			System.out.println("SEQUENCEEEEEEE");
			
			for(Map.Entry<Short,BusStop> e : map.entrySet()){
				System.out.println(e.getKey());
				Short i = (short) (e.getKey() + 1);
				
				if(i <= map.size())
				System.out.println(map.get(i).getId());
			}
	      
	      System.out.println("FERMATEEEEEE");
	      
	      List<BusStop> bs = lm.getLineStops("METRO");
	      for(BusStop b : bs){
	    	  
	    	  System.out.println(b.getId());
	    	  
	      }
	      
	      //DISTANCE
	      Float thousand = new Float(1);
	      String hql2 = "SELECT ST_DISTANCE(src.location, dst.location)/"+thousand+" FROM GeoBusStop src, GeoBusStop dst WHERE src.id=:s1 AND dst.id=:s2";
	      Query query1 = s.createQuery(hql2);
	      query1.setParameter("s1", "8210");
	      query1.setParameter("s2", "8211");
	      List l = query1.list();
	      Integer f = new Double((Double) l.get(0)).intValue();
	      System.out.println(f);
	      
	      //Select all stops around 250m with respect to geom point
	      
	      //Get location for the stop
	      GeoBusStop g = (GeoBusStop) s.get(GeoBusStop.class, "1511");
	      //System.out.println(g.getLocation());
	      
	      Geometry scerb = g.getLocation();
	      
	      String hql3 = "SELECT id FROM GeoBusStop WHERE ST_DWithin(location,:geometra,250) = TRUE";
	      Query query2 = s.createQuery(hql3).setParameter("geometra", scerb);
	      
	      List<String> lss = query2.list();
	      for(String stop : lss){
	    	  System.out.println(stop);
	      }
	      
	    
	      String hql4 = "SELECT id FROM BusStop";
	      
	      System.out.println("ALL STOPS");
	      List<String> stops = s.createQuery(hql4).list();
	      for(String stop : stops )
	    	  System.out.println(stop);
	      tx.commit();
	      
	    } catch (Throwable ex) {
	    	
	    	ex.printStackTrace();
	      if (tx!=null) tx.rollback();
	      
	      
	    } finally {
	    	
	      if (s!=null && s.isOpen()) s.close(); 
	      s=null;
	      
	    }
*/
		
		MongoClient mongoClient = null;
		try{
			MongoClientURI connectionStr = new 	MongoClientURI("mongodb://localhost:27017");
			mongoClient = new MongoClient(connectionStr);
			
			//Connect to db
			MongoDatabase database = mongoClient.getDatabase("MinPathsDB"); 
			MongoCollection<Document> collection = database.getCollection("MinPaths");
			Document a = new Document("id_source","1")
	        		   .append("id_destination", "2")
	        		   .append("mode", "0")
	        		   .append("cost", "23");
			Document b = new Document("id_source","7")
	        		   .append("id_destination", "26")
	        		   .append("mode", "1")
	        		   .append("cost", "43");
			List<Document> da = new ArrayList<Document>();
			da.add(a);
			da.add(b);
			
			Document minPath = new Document("id_source","1234")
					           .append("id_destination", "5678")
					           .append("tot_cost", "100")
					           .append("edges", da);
			
			collection.insertOne(minPath);
			//Do operations here
			//Save min path
			System.out.println("FINE");
			   
			
		}catch(Exception e){
			mongoClient.close();
		}finally{
			mongoClient.close();
		}
	}

}

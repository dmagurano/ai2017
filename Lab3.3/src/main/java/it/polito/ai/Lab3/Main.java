package it.polito.ai.Lab3;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.bson.Document;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.vividsolutions.jts.geom.Geometry;

import it.polito.ai.Lab2.Entities.BusStop;
import it.polito.ai.Lab2.Entities.GeoBusStop;
import it.polito.ai.Util.HibernateUtil;
import it.polito.ai.Util.LineManager;


//TODO check follow methods

public class Main {
	
	private static SessionFactory sf = HibernateUtil.getSessionFactory();
	private static double walkOnFoot = 1.4; //The multiplicative consant for 250m near stop
	private static Float meters = new Float(1); //Distance in meters (if I don't put this number query doesn't work)
	private static MultiKeyMap<String,Edge> edges = new MultiKeyMap<String,Edge>(); //Use this for efficently search
	
	static PrintWriter writer;
	static PrintWriter writer1;
	
	private static List<Edge> findEdges(List<String> points) throws Exception{
		
		List<Edge> minPathEdges = new ArrayList<Edge>();
		
		for(int i = 0; i < points.size()-1; i++){
		    
			Edge found = edges.get(points.get(i), points.get(i+1));
		    	
			if(found == null)
				throw new Exception("Edge not found");
			minPathEdges.add(found);
		}
		
	
		return minPathEdges;
		
		
	}


	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		Session s=sf.getCurrentSession();
	    Transaction tx=null;
	    List<String> stops = new ArrayList<String>();
	    
	    
	    try {
	      tx=s.beginTransaction();
	      
	      //Do operation:
	      //1: build edges for each stop
	      //2: calculate min path for each stop to another stop
	      //3: save min path in mongo db
	      
	     
	      
	      
	      //1.1 Build edges interesting bus lines
	      //1.1.1 Get all Lines
	      System.out.println("Building edges...");
	      String hql = "SELECT line FROM BusLine";
	      String hql1 = "SELECT id FROM BusStop";
	      stops.addAll(s.createQuery(hql1).list());
	      
	      //writer = new PrintWriter("ciao.txt");
	      //writer1 = new PrintWriter("stop.txt");
	      
	      List<String> lines = s.createQuery(hql).list();
	      
	      for(String line : lines){
	    	  
	    	  //1.1.2 Get all stops of a specific line
	    	  LineManager lm = new LineManager();
	    	  Map<Short,BusStop> stopsWithSeq = lm.getLineStopsWithSeq(line);
	    	 
	    	  
	       
		      //1.1.3 For each stop belonging to this line, build edge.
		       /*Building edge: look at stop's sequence number, 2 cases:
		       *    - if it is = 1 or = last sequence number, only one edge has to be built with stop(in the same bus line)
		       *      having sequence number = 2 in the first case, i-1 in second case
		       *    - if it is != 1, two edge have to be built, one for the stop having sequence
		       *      number = i+1 and one for the stop having sequence number = i-1 
		      */
	    	  
		      
		      for(Map.Entry<Short, BusStop> entry : stopsWithSeq.entrySet()){
		    	  
		    	  //writer1.println(entry.getValue().getId());
		    	  Short i = (short) (entry.getKey() + 1);
		    	  
		    	  
		    	  if(entry.getKey() != 1){
		    		  
		    		  
		    		  //Two edge
		    		  
		    		  //Edge with the next
		    		  
		    		  if(i <= stopsWithSeq.size()){
		    			  
		    			 
		    			  
		    			  if(entry.getValue().getId().equals(stopsWithSeq.get(i).getId())) //to avoid edge with src = dst
				    		  continue;
		    			  
		    			  Edge edge1 = new Edge();
		    			  String s1 = entry.getValue().getId();
		    			  String d1 = stopsWithSeq.get(i).getId();
		    			  edge1.setIdSource(s1);
		    			  edge1.setIdDestination(d1);
		    		      
		    			  //Set cost of edge
		    			  Double cost = (Double) s.createQuery("SELECT ST_DISTANCE(src.location, dst.location)/"+meters+" FROM GeoBusStop src, GeoBusStop dst WHERE src.id=:s1 AND dst.id=:d1")
		    					  .setParameter("s1",s1).setParameter("d1",d1).list().get(0);
		    			  edge1.setCost(cost.intValue());
		    			  edge1.setMode(false);
		    			  edge1.setEdgeLine(line);
		    			  edges.put(s1,d1,edge1);
		    			  
		    			  //writer.println(s1+" -> "+d1+", ");
		    			  
		    		  }
		    		  
		    		  Short im = (short) (entry.getKey() - 1);
		    		  if(entry.getValue().getId().equals(stopsWithSeq.get(im).getId())) //to avoid edge with src = dst
			    		  continue;
		    		  
		    		  //Edge with the previous
		    		  
		    		  Edge edge2 = new Edge();
	    			  String s2 = entry.getValue().getId();
	    			  String d2 = stopsWithSeq.get(im).getId();
	    			  edge2.setIdSource(s2);
	    			  edge2.setIdDestination(d2);
	    		  
	    			  //Set cost of edge
	    			  Double cost = (Double) s.createQuery("SELECT ST_DISTANCE(src.location, dst.location)/"+meters+" FROM GeoBusStop src, GeoBusStop dst WHERE src.id=:s2 AND dst.id=:d2")
	    					  .setParameter("s2",s2).setParameter("d2",d2).list().get(0);
	    			  edge2.setCost(cost.intValue());
	    			  edge2.setMode(false);
	    			  edge2.setEdgeLine(line);
	    			  edges.put(s2,d2,edge2);
	    			  
	    			  //writer.println(s2+" -> "+d2+", ");
		    		  
		    	  }else{
		    		  //Only one edge
		    		  
		    		  Edge edge = new Edge();
		    		  String source = entry.getValue().getId();
		    		  String destination = stopsWithSeq.get(i).getId();
		    		  edge.setIdSource(source);
		    		  edge.setIdDestination(destination);
		    		  
		    		  //Set cost of edge
		    		  Double cost = (Double) s.createQuery("SELECT ST_DISTANCE(src.location, dst.location)/"+meters+" FROM GeoBusStop src, GeoBusStop dst WHERE src.id=:source AND dst.id=:destination")
		    		  .setParameter("source",source).setParameter("destination",destination).list().get(0);
		    		  edge.setCost(cost.intValue());
		    		  edge.setMode(false);
		    		  edge.setEdgeLine(line);
		    		  edges.put(source,destination,edge);
		    		  
		    		  //writer.println(source+" -> "+destination+", ");
		    	  }
		      }
	    	  
	      }
	      
	      //1.2 Build edges not interesting bus lines but having less 250m around stop
	      
	      
	     
	      
	      //System.out.println(stops.size());
	      for(String stop : stops ){
	    	  
	    	  //1.2.1 Get location for the stop
		      GeoBusStop gbs = (GeoBusStop) s.get(GeoBusStop.class, stop);
		      Geometry stopLocation = gbs.getLocation();
		      List<String> aroundStops = s.createQuery("SELECT id FROM GeoBusStop WHERE ST_DWithin(location,:stopLocation,250) = TRUE")
		       .setParameter("stopLocation", stopLocation)
		       .list();
		      
		      //1.2.2 Building edges
		      for(String aroundStop : aroundStops){
		    	  
		    	  if(aroundStop.compareTo(stop) != 0){ //The query return also the stop with stopLocation
		    		  
		    		  Edge edge = new Edge();
		    		  
		    		  edge.setIdSource(stop);
		    		  edge.setIdDestination(aroundStop);
		    		  
		    		  //Set cost of edge
		    		  Double cost = (Double) s.createQuery("SELECT ST_DISTANCE(src.location, dst.location)/"+meters+" FROM GeoBusStop src, GeoBusStop dst WHERE src.id=:source AND dst.id=:destination")
		    		  .setParameter("source",stop).setParameter("destination",aroundStop).list().get(0);
		    		  cost *= walkOnFoot;
		    		  edge.setCost(cost.intValue());
		    		  edge.setMode(true);
		    		  edge.setEdgeLine(null);
		    		  edges.put(stop,aroundStop,edge);
		    		  
		    		  
		    		  //writer.println("near: "+stop+" -> "+aroundStop+", ");
		    	  }
		      }
	      }
	    	  
	      tx.commit();
	      
	      
	        
	    } catch (Throwable ex) {
	    	
	    	ex.printStackTrace();
	      if (tx!=null) tx.rollback();
	      
	      
	    } finally {
	    	
	      if (s!=null && s.isOpen()) s.close(); 
	      s=null;
	      
	    }
	    
	    
	    System.out.println("Edges built...");
	      
	      //2.1 Create graph
	      System.out.println("Creating graph...");      
	      Graph graph = new Graph(edges);
	      System.out.println("Graph created.");
	      
	      System.out.println("Calculating minPaths...");
	      
	      MongoClient mongoClient = null;
	      
	      try{
	    	  
	    	  MongoClientURI connectionStr = new 	MongoClientURI("mongodb://localhost:27017");
			  mongoClient = new MongoClient(connectionStr);
			  
              //Connect to db
			  MongoDatabase database = mongoClient.getDatabase("MinPathsDB"); 
				
			  //Do operations here
				
			  //Create collection
			  MongoCollection<Document> collection = database.getCollection("MinPaths");
				
				
	      
			  //2.2 Calculate for each stop the min path toward the other stops 
			  //List<Document> minPaths = new ArrayList<Document>();
			 
			  List<Document> chunk = new ArrayList<Document>();
			  //for(String stop : stops )
			  for(int i=0; i != stops.size(); i++) 
				  //ciclo normale perchè ogni tanto si bloccava e bisognava ripartire da un certo i
			  {
				  //System.out.println("for stop "+stop);
				  String stop = stops.get(i);
				  graph.dijkstra(stop);
				  chunk = graph.printAllPaths(stop);
				  collection.insertMany(chunk);
				  System.out.println(i+"/3722"+" stop "+stop);
			  }
	      
			  System.out.println("MinPaths calculated");
	      	
		}catch(Exception e){
				e.printStackTrace();
				mongoClient.close();
		}finally{
				mongoClient.close();
		}
			
	      System.out.println("MinPaths stored.");
	}
	
	

}

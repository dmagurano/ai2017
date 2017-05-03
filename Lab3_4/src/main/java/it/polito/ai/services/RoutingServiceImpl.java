package it.polito.ai.services;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.hibernate.Session;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import it.polito.ai.entities.BusStop;
import it.polito.ai.entities.Edge;
import it.polito.ai.entities.GeoBusStop;

import it.polito.ai.util.HibernateUtil;
import it.polito.ai.util.MongoDbUtil;

public class RoutingServiceImpl implements RoutingService {
	
	private static int ratio = 250;
	
	/* remember to create MongoDb index:
	 *  db.MinPaths.createIndex( { "idSource": 1, "idDestination": 1 } )
	 */
	
	public List<Edge> getPath(Double sLat, Double sLng, Double dLat, Double dLng) {
		// 0) check the validity of the params
		if (sLng < -180.0 || sLng > 180.0 || sLat < -90.0 || sLat > 90.0 
				|| dLng < -180.0 || dLng > 180.0 || dLat < -90.0 || dLat > 90.0)
			return null;
		
		// 1) search the stops around the src
		// build the point in wkt format POINT(lng lat)
		String wktPoint = new String("POINT(" + sLng + " " + sLat + ")");
		Geometry point = wktToGeometry(wktPoint);
		List<GeoBusStop> src_gstops = new ArrayList<GeoBusStop>();
		// ask the db for every stop near the src
	    for(Object o: HibernateUtil.getSessionFactory().getCurrentSession()
	    		.createQuery("from GeoBusStop bs where dwithin(bs.location,:point," + ratio + ") = true")
	    		.setParameter("point", point).list())
	    	src_gstops.add((GeoBusStop) o);
	    if(src_gstops.isEmpty()) // no path can be found
	    	return null;
	    
	    // 2) search the stops around the dst
 		// build the point in wkt format POINT(lng lat)
 		wktPoint = new String("POINT(" + dLng + " " + dLat + ")");
 		point = wktToGeometry(wktPoint);
 		List<GeoBusStop> dst_gstops = new ArrayList<GeoBusStop>();
 		
 		// ask the db for every stop near the dst
 	    for(Object o: HibernateUtil.getSessionFactory().getCurrentSession()
 	    		.createQuery("from GeoBusStop bs where dwithin(bs.location,:point," + ratio + ") = true")
 	    		.setParameter("point", point).list())
 	    	dst_gstops.add((GeoBusStop) o);
 	   if(dst_gstops.isEmpty()) // no path can be found
	    	return null;
 	   
	    // 3) search the best path
 	    // query example
 	    /*
 	       db.MinPaths.find( 
			{ $and:
			[	{$or: [ {idSource: "10"}, {idSource: "15"} ]},
				{$or: [{idDestination: "2306"}, {idDestination: "2307"}]}
			]
			}).sort({totalCost:1}).limit(1);
 	     */
 	    // get the paths collection
 	    MongoCollection<Document> c = MongoDbUtil.getDb().getCollection("MinPaths");
 	    // build the BSON filters
 	    Bson sort = Sorts.ascending("totalCost");
 	    // add the src conditions
 	    List<Bson> sFilters = new ArrayList<Bson>();
 	    for (GeoBusStop s: src_gstops)
 	    	sFilters.add(Filters.eq("idSource", "" + s.getId()));
 	    // add the dst conditions
 	    List<Bson> dFilters = new ArrayList<Bson>();
 	    for (GeoBusStop d: dst_gstops)
 	    	dFilters.add(Filters.eq("idDestination", "" + d.getId()));
		// compose the final filter
 	    Bson resFilter = Filters.and(Filters.or(sFilters), Filters.or(dFilters));
 	    // execute the query
 	    long start = System.currentTimeMillis();
 	    List<Document> query_res = c.find(resFilter).sort(sort).limit(1).into(new ArrayList<Document>());
 	    long end = System.currentTimeMillis();
 	    System.out.println("Mongo query took " + (end - start) + " ms");
 	    
 	    if (query_res.isEmpty())
 	    	return null; // no path found!
 	    Document best_doc = query_res.get(0);
 	    // buid the MinPath object
 	    // NOTE: no auto mode found, the only (easy) sol was spring based!
 	    
 	    @SuppressWarnings("unchecked")
		List<Document> edges_docs = (List<Document>) best_doc.get("edges");
 	    List<Edge> edges = new ArrayList<Edge>();
 	    for(Document d: edges_docs)
 	    {
 	    	
 	    	String idDst = d.getString("idDestination");
 	    	String idSrc = d.getString("idSource");
 	    	
 	    	BusStop bsSrc = (BusStop) HibernateUtil.getSessionFactory().getCurrentSession().load(BusStop.class, idSrc);
 	    	BusStop bsDst = (BusStop) HibernateUtil.getSessionFactory().getCurrentSession().load(BusStop.class, idDst);
 	    	
 	        Edge edge = new Edge();
 	    	edge.setCost(d.getInteger("cost"));
 	    	edge.setEdgeLine(d.getString("line"));
 	    	edge.setIdDestination(idDst);
 	    	edge.setIdSource(idSrc);
 	    	edge.setLatSrc(bsSrc.getLat());
 	    	edge.setLonSrc(bsSrc.getLng());
 	    	edge.setLatDst(bsDst.getLat());
 	    	edge.setLonDst(bsDst.getLng());
 	    	edge.setMode(d.getBoolean("mode"));
 	    	edges.add(edge);
 	    }
 	    // done
		return edges;
	}
	
	private Geometry wktToGeometry(String wktPoint) {
        WKTReader fromText = new WKTReader();
        Geometry geom = null;
        try {
            geom = fromText.read(wktPoint);
        } catch (ParseException e) {
            throw new RuntimeException("Not a WKT string:" + wktPoint);
        }
        return geom;
    }
	
}

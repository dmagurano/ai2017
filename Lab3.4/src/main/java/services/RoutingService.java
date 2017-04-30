package services;

import java.util.List;

import it.polito.ai.Lab3.Entities.Edge;

public interface RoutingService {
	
	public List<Edge> getPath(Double sLat, Double sLng, Double dLat, Double dLng); 

}

package it.polito.ai.services;

import java.util.List;

import it.polito.ai.entities.Edge;



public interface RoutingService {
	
	public List<Edge> getPath(Double sLat, Double sLng, Double dLat, Double dLng); 

}

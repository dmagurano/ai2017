package it.polito.ai.Lab2.Entities;

import java.util.ArrayList;

import javax.persistence.*;


@Entity
public class BusLine {

	
	private String line;
	private String description;
	
	
	private ArrayList<BusStop> busStops = new ArrayList<BusStop>();
	
	@Id
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToMany(mappedBy = "busLine")
	public ArrayList<BusStop> getBusstops() {
		return busStops;
	}
	public void setBusstops(ArrayList<BusStop> busstops) {
		this.busStops = busstops;
	}

	
	
}

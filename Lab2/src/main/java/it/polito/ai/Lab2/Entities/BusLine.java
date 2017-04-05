package it.polito.ai.Lab2.Entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
public class BusLine {

	
	private String line;
	private String description;
	
	@OneToMany(mappedBy = "busStop")
	private List<BusLineStop> busStops = new ArrayList<BusLineStop>();
	
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
	
	
	public List<BusLineStop> getBusstops() {
		return busStops;
	}
	public void setBusstops(ArrayList<BusLineStop> busstops) {
		this.busStops = busstops;
	}

	
	
}

package it.polito.ai.Lab2.Entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name="busline")
public class BusLine {

	@Id
	private String line;
	private String description;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "busLine")
	private List<BusLineStop> busStops = new ArrayList<BusLineStop>();
	
	
	public String getLine() {
		try{
			return line;
		} catch (Exception e){
			e.printStackTrace();
			return line;
		}
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
	
	public List<BusLineStop> getBusStops() {
		return busStops;
	}
	public void setBusStops(List<BusLineStop> busstops) {
		this.busStops = busstops;
	}

	
	
}

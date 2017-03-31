package it.polito.ai.Lab2.Entities;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class BusStop {
	
	@Id
	private String id;
	
	@Column(nullable=false)
	private String name;
	
	@Column
	private double lat;
	
	@Column
	private double lng;
	
	@OneToMany(mappedBy="busStop")
	private ArrayList<BusLineStop> busLines = new ArrayList<BusLineStop>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}

	public ArrayList<BusLineStop> getBusLines() {
		return busLines;
	}
	
	public void setBusLines(ArrayList<BusLineStop> busLines) {
		this.busLines = busLines;
	}
}

package it.polito.ai.Lab2.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.vividsolutions.jts.geom.Geometry;


@Entity
public class GeoBusStop {
	
	@Id
	private String id;
	
	@Column
	private String name;
	
	@Column
	private Geometry location;

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

	public Geometry getLocation() {
		return location;
	}

	public void setLocation(Geometry location) {
		this.location = location;
	}

}

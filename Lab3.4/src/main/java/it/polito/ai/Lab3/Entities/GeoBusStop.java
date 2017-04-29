package it.polito.ai.Lab3.Entities;

import java.io.Serializable;

import javax.persistence.*;

import com.vividsolutions.jts.geom.Geometry;

@Entity
@Table(name="busstop")
public class GeoBusStop implements Serializable{
	
	// NOTE: no foreign key depency in the db
	
	@Id
	private String id;
	
	@Column(nullable=false)
	private String name;
	
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

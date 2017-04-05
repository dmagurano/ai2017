package it.polito.ai.Lab2.Entities;

import javax.persistence.*;

@Entity
public class BusLineStop {
	
	@Id
	private BusLine busLine;
	
	@Id
	private BusStop busStop;
	
	@Column(nullable=false)
	private Integer seqenceNumber;
	
	@ManyToOne
	@JoinColumn(name="lineId")
	public BusLine getBusLine() {
		return busLine;
	}

	public void setBusLine(BusLine busLine) {
		this.busLine = busLine;
	}

	@ManyToOne
	@JoinColumn(name="stopId")
	public BusStop getBusStop() {
		return busStop;
	}

	public void setBusStop(BusStop busStop) {
		this.busStop = busStop;
	}

	public Integer getSeqenceNumber() {
		return seqenceNumber;
	}

	public void setSeqenceNumber(Integer seqenceNumber) {
		this.seqenceNumber = seqenceNumber;
	}
	

}

package it.polito.ai.Lab2.Entities;

import javax.persistence.*;

public class BusLineStop {
	
	@ManyToOne
	@JoinColumn(name="lineId")
	@Id
	private BusLine busLine;
	
	@ManyToOne
	@JoinColumn(name="stopId")
	@Id
	private BusStop busStop;
	
	@Column(nullable=false)
	private Integer seqenceNumber;

	public BusLine getBusLine() {
		return busLine;
	}

	public void setBusLine(BusLine busLine) {
		this.busLine = busLine;
	}

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

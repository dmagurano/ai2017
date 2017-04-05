package it.polito.ai.Lab2.Entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="buslinestop")
public class BusLineStop implements Serializable {
	
//	@Id
//	private String busL;
//	
//	@Id
//	private String busS;
	
	@Column(nullable=false)
	private Short seqencenumber;
	
	@ManyToOne
	@Id
	@JoinColumn(name="lineId")
	private BusLine busLine;
	
	@Id
	@ManyToOne
	@JoinColumn(name="stopId")
	private BusStop busStop;
	
	
	
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

	public Short getSeqenceNumber() {
		return seqencenumber;
	}

	public void setSeqenceNumber(Short seqenceNumber) {
		this.seqencenumber = seqenceNumber;
	}
	

}

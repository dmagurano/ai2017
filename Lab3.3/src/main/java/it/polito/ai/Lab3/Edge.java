package it.polito.ai.Lab3;

/**
 * Questa classe non rappresenta l'arco di un grafo,
 * ma una tratta di un percorso.
 * */
public class Edge {
	
	/**
	 * Id della fermata (BusStop) di partenza
	 * */
	private String idSource;
	
	/**
	 * Id della fermata (BusStop) di arrivo
	 * */
	private String idDestination;
	
	/**
	 * Modo di spostamento
	 * 0 -> bus
	 * 1 -> a piedi
	 * */
	private boolean mode;
	
	/**
	 * Costo dell'arco
	 * */
	private int cost;
	
	private String edgeLine;
	
	
	public String getIdSource() {
		return idSource;
	}
	public void setIdSource(String idSource) {
		this.idSource = idSource;
	}
	public String getIdDestination() {
		return idDestination;
	}
	public void setIdDestination(String idDestination) {
		this.idDestination = idDestination;
	}
	public boolean isMode() {
		return mode;
	}
	public void setMode(boolean mode) {
		this.mode = mode;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String getEdgeLine() {
		return edgeLine;
	}
	public void setEdgeLine(String edgeLine) {
		this.edgeLine = edgeLine;
	}
}
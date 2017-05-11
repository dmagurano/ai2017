package it.polito.madd.entities;

public class Bike {
	
	public Bike(boolean owned, boolean sharing){
		this.owned = owned;
		this.sharing = sharing;
	}
	
	private boolean owned;
	
	private boolean sharing;

	public boolean isOwned() {
		return owned;
	}

	public void setOwned(boolean owned) {
		this.owned = owned;
	}

	public boolean isSharing() {
		return sharing;
	}

	public void setSharing(boolean sharing) {
		this.sharing = sharing;
	}
	
	

}

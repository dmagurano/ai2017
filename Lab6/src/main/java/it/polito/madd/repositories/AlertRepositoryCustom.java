package it.polito.madd.repositories;

import it.polito.madd.entities.Rate;

public interface AlertRepositoryCustom {
	public void updateUserRate(String alertId, Rate rate);
}

package it.polito.madd.repositories;

import java.util.Date;

import it.polito.madd.entities.Rate;

public interface AlertRepositoryCustom {
	public void updateUserRate(String alertId, Rate rate);
	public void updateReferenceTs(String alertId, Date ts);
}

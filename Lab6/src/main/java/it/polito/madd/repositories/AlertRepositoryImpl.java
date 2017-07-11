package it.polito.madd.repositories;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.polito.madd.entities.Alert;
import it.polito.madd.entities.Rate;

public class AlertRepositoryImpl implements AlertRepositoryCustom {

	// two phase commit
	// https://docs.mongodb.com/manual/tutorial/perform-two-phase-commits/
	
	@Autowired
	AlertRepository alertRepository;

	@Transactional
	@Override
	public void updateUserRate(String alertId, Rate rate) {
		
		Alert alert = alertRepository.findById(alertId);
		
		alert.setRate(rate.getEmail(), rate.getValue());
		
		alertRepository.save(alert);
	}
	
	@Transactional
	@Override
	public void updateReferenceTs(String alertId, Date ts) {
		Alert alert = alertRepository.findById(alertId);
		if(alert == null)
			return;
		alert.setLastAccessTimestamp(ts);
		alertRepository.save(alert);
	}

}

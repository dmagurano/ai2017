package it.polito.madd.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.transaction.annotation.Transactional;

import it.polito.madd.entities.Rate;

public class AlertRepositoryImpl implements AlertRepositoryCustom {
	@Autowired
	MongoOperations mongoOperations;

	@Transactional
	@Override
	public void updateUserRate(String alertId, Rate rate) {
		// TODO
		// two phase commit		https://docs.mongodb.com/manual/tutorial/perform-two-phase-commits/
		// or use external collection for rates
		
	}

}

package it.polito.madd;

import java.util.ArrayList;

import it.polito.madd.model.CreditCard;

public class PaymentManager implements PaymentService {
	// TODO make a meaningful class?
	private ArrayList<CreditCard> wallet;

	@Override
	public Boolean makePayment(float total, CreditCard cc) {
		return true;
	}

	@Override
	public Boolean addCardAndCheckDisponibility(CreditCard cc, float amount) {
		if (!wallet.contains(cc))
			wallet.add(cc);
		// simulated bank checking
		return true;
	}

}

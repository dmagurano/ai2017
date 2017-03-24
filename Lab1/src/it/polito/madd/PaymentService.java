package it.polito.madd;

import it.polito.madd.model.CreditCard;

public interface PaymentService {
	public Boolean makePayment();
	public Boolean addCardAndCheckDisponibility(CreditCard cc, float amount);
	public CreditCard getMostRecentCard();
}

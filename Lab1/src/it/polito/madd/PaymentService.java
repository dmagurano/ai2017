package it.polito.madd;

import it.polito.madd.model.CreditCard;

public interface PaymentService {
	public Boolean makePayment(float total, CreditCard cc);
	public Boolean addCardAndCheckDisponibility(CreditCard cc, float amount);
}

package it.polito.madd;

import java.util.ArrayList;

import it.polito.madd.model.CreditCard;

public class PaymentManager implements PaymentService {
	// TODO make a meaningful class?
	private ArrayList<CreditCard> wallet = new ArrayList<CreditCard>();

	@Override
	public Boolean makePayment() {
		return true;
	}

	@Override
	public Boolean addCardAndCheckDisponibility(CreditCard cc, float amount) {
		Boolean exists = false;
		for (CreditCard a_cc: wallet)
		{
			if (a_cc.getNumber().equals(cc.getNumber()))
				exists = true;
		}
		if (!exists)
			wallet.add(cc);
		// simulated bank checking [........]
		return true;
	}
	
	@Override
	public CreditCard getMostRecentCard()
	{
		if (wallet.isEmpty())
			return null;
		return wallet.get(wallet.size() - 1);
	}

}

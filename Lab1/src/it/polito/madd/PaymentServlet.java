package it.polito.madd;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.polito.madd.model.CreditCard;

//TODO map the correct url
@WebServlet("/pay")
public class PaymentServlet extends HttpServlet {
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		
		PaymentManager pm = (PaymentManager) session.getAttribute("PaymentManager");
		CartManager cm = (CartManager) session.getAttribute("CartManager");
		
		String number, holder, cvv;
		number = request.getParameter("number");
		holder = request.getParameter("holder");
		cvv = request.getParameter("cvv");
		if (number == null || holder == null || cvv == null)
		{
			int i;
			// TODO check for null/invalid value and raise exception
		}		
		synchronized (session)
		{
			// check if the user has the funds (and add the cc to wallet)
			if (!pm.addCardAndCheckDisponibility(new CreditCard(number, holder, cvv), cm.getTotal()))
			{
				// TODO error
				int i;
			}
		}
		
		// send the request to the jsp page
		// TODO setup the correct jsp page
		try {
			session.getServletContext().getRequestDispatcher("/pay.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

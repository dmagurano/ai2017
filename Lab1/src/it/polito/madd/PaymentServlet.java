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
@WebServlet("/private/payment")
public class PaymentServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
			
			HttpSession session = request.getSession();
			// simply forward the request to the jsp page
			// TODO setup the correct jsp page
			try {
				session.getServletContext().getRequestDispatcher("/private/checkout.jsp").forward(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		
		String number, holder, cvv;
		number = request.getParameter("number");
		holder = request.getParameter("holder");
		cvv = request.getParameter("cvv");
		
		if (number == null || holder == null || cvv == null || number.length() == 0 || holder.length() == 0 || cvv.length() == 0)
		{
			// the request is not well formed, return 400
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
			// TODO check also for invalid value and raise exception
		}
		
		PaymentManager pm = (PaymentManager) session.getAttribute("PaymentService");
		CartManager cm = (CartManager) session.getAttribute("CartService");
		if (cm == null || pm == null)
		{
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		synchronized (session)
		{
			// check if the user has the funds (and add the cc to wallet)
			if (!pm.addCardAndCheckDisponibility(new CreditCard(number, holder, cvv), cm.getTotal()))
				// card rejected
				request.setAttribute("TRANSACTION_RESULT", "rejected");
			else{
				// card accepted
				request.setAttribute("TRANSACTION_RESULT", "accepted");
				request.getSession().removeAttribute("cart_is_full");
			}
			
		}
		
		// send the request to the jsp page
		// TODO setup the correct jsp page
		try {
			session.getServletContext().getRequestDispatcher("/private/payment.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

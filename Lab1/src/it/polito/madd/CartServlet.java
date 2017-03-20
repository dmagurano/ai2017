package it.polito.madd;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.polito.madd.model.Ticket;
import it.polito.madd.model.TicketType;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		// simply forward the request to the jsp page
		// TODO setup the correct jsp page
		try {
			session.getServletContext().getRequestDispatcher("/cart.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		int op = 0;
		if (request.getParameter("METHOD").equals("modify"))
			op = 1;

		// fill up the request with the params passed
		CartManager cm = (CartManager) session.getAttribute("CartService");
		int value = 0;
		synchronized (session)
		{
			// check the value of every item
			for (TicketType type: TicketType.values())
			{
				if (request.getParameter(type.toString()) != null)
				{
					try {
						value = Integer.parseInt(request.getParameter(type.toString()));
					}
					catch (NumberFormatException e)
					{
						// TODO wrong param value passed
						e.printStackTrace();
						continue;
					}
					try {
						// discriminate the requested operation
						if (op == 1)
							cm.modify(new Ticket(type), value);
						else
							cm.add(new Ticket(type), value);
					} catch (Exception e) {
						// TODO a value is invalid
						e.printStackTrace();
					}
				}
			}
		}
		
		// forward the request to the jsp page
		// TODO setup the correct jsp page
		try {
			session.getServletContext().getRequestDispatcher("/cart.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

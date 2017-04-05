package it.polito.ai.Util;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import it.polito.ai.Lab2.Entities.BusLine;

/**
 * Servlet implementation class LineRequest
 */
@WebServlet("/LineRequest")
public class LineRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LineRequest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		session.getServletContext().getRequestDispatcher("/map.jsp").forward(request, response);
		
		System.out.println("Linea selezionata "+request.getParameter("selected_line"));
		Session s = (Session) request.getAttribute("Session");
		
		BusLine bl = s.get(BusLine.class, "3");
	      if(bl == null){System.out.println("Niente linea");}
	      System.out.println("Linea:  " +bl.getLine());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

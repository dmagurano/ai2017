package it.polito.madd;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GET /login");
		
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST /login");
		String email = request.getParameter("email"); 
		String password = request.getParameter("password"); 
		
		HttpSession session = request.getSession();
		
		LoginManager lm = (LoginManager) session.getAttribute("LoginService");
		
		System.out.println(lm);
		
		lm.login(email, password);
		
		System.out.println(email);
		System.out.println(password);
		System.out.println(lm.isLogged());
		
		if (lm.isLogged())
			session.setAttribute("username", email);
		else
			session.removeAttribute("username");
		
		request.getRequestDispatcher("/cart.jsp").forward(request, response);
	}

}

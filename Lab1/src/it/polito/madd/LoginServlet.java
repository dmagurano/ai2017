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
		
		String next = "/login.jsp";

		HttpSession session = request.getSession(false);
		
		if (session != null){			
			LoginManager lm = (LoginManager) session.getAttribute("LoginService");
			
			if (lm != null)
				if (lm.isLogged())
					next = "/";
		}
		
		request.getRequestDispatcher(next).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST /login ");
		
		String next = "/login.jsp";
		String email = request.getParameter("email"); 
		String password = request.getParameter("password"); 
		
		/*
			HttpSession#getSession(boolean create) - create - true to create a new session for this request if necessary;
		  	false to return null if there's no current session.
		 */
		HttpSession session = request.getSession(false);
		
		if (session != null){
			LoginManager lm = (LoginManager) session.getAttribute("LoginService");
			
			if (lm != null){
				lm.login(email, password);
			
				if (lm.isLogged()){
					session.setAttribute("username", email);
					
					if (session.getAttribute("next") != null){
						/* going to previuosly set page in session */
						next = (String) session.getAttribute("next");					
						session.removeAttribute("next");
						next = "/" + next.concat(".jsp");
					}
					else{
						/* going to origin page */					
						String referer = ((HttpServletRequest) request).getHeader("Referer");

						String tmp = referer.substring(referer.lastIndexOf("/") + 1, referer.length());

						if (tmp.equals("login") || tmp.equals("logout") || tmp.equals(""))
							next = "/index.jsp";
						else
							next = "/" + tmp.concat(".jsp");
					}
					
					/*
						success -> green
						info -> blue
						warning -> orange
						danger -> red
					 */
					/*
					session.setAttribute("message-type", "info");
					session.setAttribute("message", "Welcome " + lm.getUsername() + ".");
					*/
				}
			}
		}
		
		request.getRequestDispatcher(next).forward(request, response);
	}

}

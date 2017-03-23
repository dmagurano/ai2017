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
		
		String next = new String("/");
		String email = request.getParameter("email"); 
		String password = request.getParameter("password"); 
		
		HttpSession session = request.getSession(false);
		
		if (session != null){			
			LoginManager lm = (LoginManager) session.getAttribute("LoginService");
			
			lm.login(email, password);
			
			System.out.println(email);
			
			if (lm.isLogged()){
				session.setAttribute("username", email);
				
				if (session.getAttribute("next") != null){
					System.out.println("next attribute present");
					/* going to previuosly set page */
					System.out.println("next: " + next);
					next = (String) session.getAttribute("next");					
					session.removeAttribute("next");
					next = "/" + next.concat(".jsp");
				}
				else{
					System.out.println("next attribute absent");
					/* going to origin page */					
					String referer = ((HttpServletRequest) request).getHeader("Referer");
					System.out.println("from: " + referer);
					String tmp = referer.substring(referer.lastIndexOf("/") + 1, referer.length());
					if (tmp.equals("login") || tmp.equals("logout"))
						next = "/";
					else if (tmp.length() != 0) /* going to index */
						next = "/" + tmp.concat(".jsp");
				}
			}
			else
				// TODO insert filed authentication message
				session.invalidate();
		}
		else
			request.getSession(true);

		System.out.println("to: " + next);
		
		request.getRequestDispatcher(next).forward(request, response);
	}

}

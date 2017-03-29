package it.polito.madd;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class CheckSessionValidate
 */
@WebFilter("/*")
public class CheckSessionValidate implements Filter {

    /**
     * Default constructor. 
     */
    public CheckSessionValidate() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		if ( request instanceof HttpServletRequest ) {
			
			/* see LogotServlet.doGet */
			HttpSession session = ( (HttpServletRequest)request ).getSession(false);
			
			//Check if object in session are null. To fix the problem (session is serialized but the objects didn't)
			//when the container restarts.
			
			if(session.getAttribute("LoginService") == null || session.getAttribute("PaymentService") == null || session.getAttribute("CartService")== null  )
				session.invalidate();
		    
		    
		    	
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

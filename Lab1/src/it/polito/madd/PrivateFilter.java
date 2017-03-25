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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

/**
 * Servlet Filter implementation class PrivateFilter
 */
@WebFilter("/private/*")
public class PrivateFilter implements Filter {
       
    /**
     * @see Filter#Filter()
     */
    public PrivateFilter() {
        super();
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
		System.out.println("FILTER /private");
		// get the LoginManager from the session
		if ( request instanceof HttpServletRequest ) {
			
			/* see LogotServlet.doGet */
			HttpSession session = ( (HttpServletRequest)request ).getSession(false);
			
			if (session != null){
				LoginManager lm = (LoginManager) session.getAttribute("LoginService");
				
				// checking if the user is logged
				if( !lm.isLogged() ){
					String referer = ((HttpServletRequest) request).getHeader("Referer");
					
					String origin = referer.substring(referer.lastIndexOf("/") + 1, referer.length());
					
					if ( origin.equals("cart") )
						session.setAttribute("next", "private/checkout");
					
					System.out.println("FILTER -> GET /login");
					(  (HttpServletResponse)response ).sendRedirect("/Lab1/login.jsp");
				} else {
					// pass the request along the filter chain
										
					chain.doFilter(request, response);
				}
			}
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

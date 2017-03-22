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
		
		// get the LoginManager from the session
		if ( request instanceof HttpServletRequest ) {
			HttpSession session = ( (HttpServletRequest)request ).getSession();
			LoginManager lm = (LoginManager) session.getAttribute("LoginService");
			
			// checking if the user is logged
			if( !lm.isLogged() ){
				(  (HttpServletResponse)response ).sendRedirect("/Lab1/login.jsp");
			} else {
				// pass the request along the filter chain
				chain.doFilter(request, response);
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

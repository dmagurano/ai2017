package it.polito.ai.Util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import it.polito.ai.Lab2.Entities.BusLine;

/**
 * Servlet Filter implementation class HibernateSessionRequestFilter
 */
@WebFilter("/*")
public class HibernateSessionRequestFilter implements Filter {

	private static SessionFactory sf = HibernateUtil.getSessionFactory();
    /**
     * Default constructor. 
     */
    public HibernateSessionRequestFilter() {
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
		
		Session s=sf.getCurrentSession();
	    Transaction tx=null;
	    
	    try {
	      tx=s.beginTransaction();
	      request.setAttribute("Session", s);
	      chain.doFilter(request, response);
	      
	      //Do your operation here
	      
	      
	      tx.commit();
	      
	    } catch (Throwable ex) {
	    	
	      if (tx!=null) tx.rollback();
	      throw new ServletException(ex);
	      
	    } finally {
	    	
	      if (s!=null && s.isOpen()) s.close(); 
	      s=null;
	      
	    }

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

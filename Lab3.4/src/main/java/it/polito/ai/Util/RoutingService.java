package it.polito.ai.Util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import it.polito.ai.Lab3.Entities.BusStop;
import it.polito.ai.Lab3.Entities.Edge;
import it.polito.ai.Lab3.Entities.MinPath;

/**
 * Servlet implementation class LineRequest
 */
@WebServlet("/RoutingRequest")
public class RoutingService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RoutingService() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Edge> edges = new ArrayList<Edge>();
		int i;
		for(i=0; i < 7; i++)
		{
			Edge e = new Edge();
			e.setMode(true);
			e.setEdgeLine("13");
			e.setIdSource("" + i);
			e.setIdDestination("" + i);
			//45.07, 7.69
			e.setLatSrc(45.07 + i*0.05);
			e.setLonSrc(7.69 + i*0.05);
			e.setLatDst(45.07 + i*0.1);
			e.setLonDst(7.69 + i*0.1);
			edges.add(e);
		}
		Edge e = new Edge();
		e.setMode(false);
		e.setEdgeLine(null);
		e.setIdSource("" + i);
		e.setIdDestination("" + i);
		//45.07, 7.69
		e.setLatSrc(45.07 + i*0.1);
		e.setLonSrc(7.69 + i*0.1);
		i++;
		e.setLatDst(45.07 + i*0.13);
		e.setLonDst(7.69 + i*0.13);
		edges.add(e);
		
		for(; i < 7; i++)
		{
			Edge ex = new Edge();
			ex.setMode(true);
			ex.setEdgeLine("17");
			ex.setIdSource("" + i);
			ex.setIdDestination("" + i);
			//45.07, 7.69
			ex.setLatSrc(45.07 + i*0.05);
			ex.setLonSrc(7.69 + i*0.05);
			ex.setLatDst(45.07 + i*0.1);
			ex.setLonDst(7.69 + i*0.1);
			edges.add(ex);
		}
		
		
		
		
		Gson gson = new Gson();
		
		String busStopsJson = gson.toJson(edges);
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();  
		out.print(busStopsJson);
		out.flush();
	
		System.out.println("Path request served");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

package it.polito.ai.Util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import it.polito.ai.Lab2.Entities.BusStop;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("entering LineRequest.doGet()");
		
		String selectedLine = request.getParameter("selected_line");
		
		LineManager lm = new LineManager();
		
		ArrayList<BusStop> busStops = (ArrayList<BusStop>) lm.getLineStops(selectedLine);
		
		System.out.println("stops:");
		for (BusStop bs : busStops){
			bs.setBusLines(null);
		}
		
		Gson gson = new Gson();
		
		String busStopsJson = gson.toJson(busStops);
		
		// System.out.println(busStopsJson);
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();  
		out.print(busStopsJson);
		out.flush();
	
		System.out.println("exiting LineRequest.doGet()");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

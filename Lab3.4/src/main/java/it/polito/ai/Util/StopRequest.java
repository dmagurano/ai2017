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

import it.polito.ai.Lab3.Entities.BusLine;

/**
 * Servlet implementation class LineRequest
 */
@WebServlet("/StopRequest")
public class StopRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StopRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("entering StopRequest.doGet()");
		
		String selectedStop = request.getParameter("selected_stop");
		
		StopManager sm = new StopManager();
		
		ArrayList<BusLine> busLines = (ArrayList<BusLine>) sm.getStopBusLines(selectedStop);
		
		for (BusLine bl : busLines)
			bl.setBusStops(null);
		
		Gson gson = new Gson();
		
		String busLinesJson = gson.toJson(busLines);
		
		// System.out.println(busStopsJson);
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();  
		out.print(busLinesJson);
		out.flush();
	
		System.out.println("exiting StopRequest.doGet()");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

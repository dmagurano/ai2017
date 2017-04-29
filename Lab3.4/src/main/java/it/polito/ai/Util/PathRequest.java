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

import it.polito.ai.Lab3.Entities.BusStop;
import it.polito.ai.Lab3.Entities.MinPath;

/**
 * Servlet implementation class LineRequest
 */
@WebServlet("/PathRequest")
public class PathRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PathRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO params check
		// supposed parameters: src="lat,lng"&dst="lat,lng"
		// src[0] -> sLat
		// src[1] -> sLng
		// dst[0] -> dLat
		// dst[1] -> dLng
		String[] src = request.getParameter("src").split(",");
		String[] dst = request.getParameter("dst").split(",");
		PathManager pm = new PathManager();
		
		// get the path 
		MinPath bestPath = pm.getPath(
						Double.parseDouble(src[0]), Double.parseDouble(src[1]),
						Double.parseDouble(dst[0]), Double.parseDouble(dst[1]));
		
		// ---------------------------------------------------------------------------------
		// ---------------------------------------------------------------------------------
		// ---------------------------------------------------------------------------------
		// ---------------------------------------------------------------------------------
		// ---------------------------------------------------------------------------------
		/*
		for (BusStop bs : busStops){
			bs.setBusLines(null);
		}
		
		Gson gson = new Gson();
		
		String busStopsJson = gson.toJson(busStops);
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();  
		out.print(busStopsJson);
		out.flush();
	
		System.out.println("Path request served");*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

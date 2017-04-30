package it.polito.ai.controllers;

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

import it.polito.ai.entities.Edge;
import it.polito.ai.services.RoutingService;
import it.polito.ai.services.RoutingServiceImpl;


/**
 * Servlet implementation class CalculatePath
 */
@WebServlet("/CalculatePath")
public class CalculatePath extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RoutingService rs = new RoutingServiceImpl();
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// supposed parameters: src="lat,lng"&dst="lat,lng"
		String[] src = request.getParameter("src").split(",");
		String[] dst = request.getParameter("dst").split(",");
	
		// get the path 
		List<Edge> edges = rs.getPath(
						Double.parseDouble(src[0]), Double.parseDouble(src[1]),
						Double.parseDouble(dst[0]), Double.parseDouble(dst[1]));
		
		

		
		Gson gson = new Gson();
		if(edges == null)
			edges = new ArrayList<Edge>();
		String busStopsJson = gson.toJson(edges);
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();  
		out.print(busStopsJson);
		out.flush();
	
		System.out.println("Path request served");
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

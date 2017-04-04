package it.polito.ai.Lab2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.sql.*;

public class Main {
	private static PreparedStatement preparedStatementStop = null;
	private static PreparedStatement preparedStatementLine = null;
	private static PreparedStatement preparedStatementStopLine = null;
	
	static {
		try{
			Class.forName("org.postgresql.Driver"); // TODO
		}
		catch (ClassNotFoundException e){
			System.exit(1);
		}
	}
	
	/*
	 * https://www.postgresql.org/docs/8.2/static/errcodes-appendix.html
	 * postgres error code meaning
	 */
	public static Connection setupAndConnect() throws SQLException {
		
		Connection c = null;
		try {
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "ai-user-password");
		}
		catch (org.postgresql.util.PSQLException e)
		{
			c = DriverManager.getConnection("jdbc:postgresql://192.168.99.100:5432/", "postgres", "ai-user-password");
		}
		String create_db = "CREATE DATABASE trasporti;";
		String create_tables = " create table if not exists BusLine (line varchar(20) not null, description varchar(255), primary key (line));" +
				"create table if not exists BusStop (id varchar(20) not null, name varchar(255) not null, lat double precision, lng double precision, primary key (id));" + 
				"create table if not exists BusLineStop (stopId varchar(20) not null, lineId varchar(20) not null, seqenceNumber smallint not null, primary key(stopId, lineId), foreign key (stopId) references BusStop(id), foreign key (lineId) references BusLine(line));";
		
		Statement s1 = c.createStatement();
		
		try{ 
			s1.executeUpdate(create_db);
		}
		catch (org.postgresql.util.PSQLException e)
		{
			if (e.getSQLState().equals("42P04") == false)
				throw e;
			System.out.println("Database already exists.");
			return connect();
		}
		finally
		{
			c.close();
		}
		Connection cdb = connect();
		Statement s2 = cdb.createStatement();
		s2.executeUpdate(create_tables);
		System.out.println("Database created.");
		return cdb;
	}
	
	public static Connection connect() throws SQLException{
		String url = "jdbc:postgresql://localhost:5432/trasporti"; // to specify in -p
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "postgres", "ai-user-password");
		}
		catch (org.postgresql.util.PSQLException e)
		{
			// probably mattia's pc? :)
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/trasporti", "postgres", "ai-user-password");				
		}
		return conn;
	}
	
	public static void executeQueryStop(Object s) throws SQLException{
		JSONObject jsonStop = (JSONObject) s;

		JSONArray latLng = (JSONArray) jsonStop.get("latLng");

		preparedStatementStop.setString(1, (String) jsonStop.get("id"));
		preparedStatementStop.setString(2, (String) jsonStop.get("name"));
		preparedStatementStop.setDouble(3, (double) latLng.get(0));
		preparedStatementStop.setDouble(4, (double) latLng.get(1));
		
		try {
			preparedStatementStop.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void executeQueryLine(Object l) throws SQLException{
		JSONObject jsonLine = (JSONObject) l;
		preparedStatementLine.setString(1, (String) jsonLine.get("line"));
		preparedStatementLine.setString(2, (String) jsonLine.get("desc"));

		try {
			preparedStatementLine.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void executeQueryStopLine(Object l) throws SQLException{
		JSONObject jsonLine = (JSONObject) l;
		/*
		 * 1: bus stop
		 * 2: line id
		 * 3: seq(u)ence number
		 * 
		 */
		preparedStatementStopLine.setString(2, (String) jsonLine.get("line"));
		System.out.println("################# DEBUG: " + jsonLine.get("line").toString());
		JSONArray stops = (JSONArray) jsonLine.get("stops");
		short seq = 0;
		for (Object stop: stops)
		{
			seq++;
			String jsonStop = (String) stop;
			System.out.println("# stop " + jsonStop + " " + seq);
			preparedStatementStopLine.setString(1, jsonStop);
			preparedStatementStopLine.setShort(3, seq);
			try {
				preparedStatementStopLine.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String args[]) throws FileNotFoundException, IOException, ParseException, SQLException{
		
		Connection conn = setupAndConnect();
//		conn.close();
//		System.exit(0);
		
		String queryStop = "insert into BusStop(id, name, lat, lng) values(?, ?, ?, ?)";
		preparedStatementStop = conn.prepareStatement(queryStop);
		
		String queryLine = "insert into BusLine(line, description) values(?, ?)";
		preparedStatementLine = conn.prepareStatement(queryLine);
		
		String queryStopLine = "insert into BusLineStop(stopId, lineId, seqenceNumber) values(?, ?, ?)";
		preparedStatementStopLine = conn.prepareStatement(queryStopLine);
		
		JSONParser parser = new JSONParser();
		
		Object obj = parser.parse(new FileReader("linee.json"));
		
		JSONObject jsonObject = (JSONObject) obj;

		JSONArray lines = (JSONArray) jsonObject.get("lines");
		JSONArray stops = (JSONArray) jsonObject.get("stops");
		// insert every stop into the db
		try {
			for(Object stop : stops)
				executeQueryStop(stop);
		}
		catch (Exception e)
		{
			if (conn != null)
				conn.close();
			System.out.println("Abort. " + e);
			System.exit(2);
		}
		// insert every line into the db
		try {
			for(Object line : lines)
				executeQueryLine(line);
		}
		catch (Exception e)
		{
			if (conn != null)
				conn.close();
			System.out.println("Abort. " + e);
			System.exit(3);
		}
		// finally insert the line stop sequence
		try {
			for(Object line : lines)
			{
				// TODO solve this
				// executeQueryStopLine(line);
				System.out.println("Too much.");
				break;
			}
				
		}
		catch(Exception e)
		{
			System.out.println("Abort. " + e);
		}
		finally
		{
			conn.close();
		}
		System.out.println("End.");
		
		/*
		for (Object line : lines){
			JSONObject jsonLine = (JSONObject) line;
			String l = (String) jsonLine.get("line");
			String d = (String) jsonLine.get("desc");
			
			
			System.out.println(l + " " + d);
		}
		*/
	}
}

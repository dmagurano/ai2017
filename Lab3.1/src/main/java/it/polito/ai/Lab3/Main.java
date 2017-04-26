package it.polito.ai.Lab3;

import java.sql.*;

import org.postgresql.util.PSQLException;


public class Main {
	private static PreparedStatement preparedStatementBusStop = null;
	private static PreparedStatement preparedStatementGeoBusStop = null;
	
	static {
		try{
			Class.forName("org.postgresql.Driver"); // TODO check parallel in Lab2.3
		}
		catch (ClassNotFoundException e){
			System.exit(1);
		}
	}
	
	private static Connection connect() throws SQLException{
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/trasporti", "postgres", "ai-user-password");
		}
		catch (PSQLException e){
			// probably mattia's pc? :)
			conn = DriverManager.getConnection("jdbc:postgresql://192.168.99.100:5432/trasporti", "postgres", "ai-user-password");				
		}
		
		return conn;
	}
	
	/*
	 * https://www.postgresql.org/docs/8.2/static/errcodes-appendix.html
	 * postgres error code meaning
	 */
	public static Connection setupAndConnect() throws SQLException {
		Connection c = connect();
		
		String create_table = "create table if not exists GeoBusStop ("
				+ "id varchar(20) not null, "
				+ "name varchar(255) not null, "
				+ "location geography(POINT, 4326),"
				+ "primary key (id));";
		
		Statement s1 = c.createStatement();

		System.out.println("Creating GeoBusStop table ...");
		s1.executeUpdate(create_table);
		System.out.println("GeoBusStop table created.");
		
		return c;
	}
	
	public static int insertGeoBusSop(Connection c, String id, String name, Double lat, Double lng) throws SQLException{
		// TODO should I avoid string concat as in 07-jdbc slide 19?
		String queryGeoBusStop = "insert into GeoBusStop(id, name, location) "
				+ "values (?, ?, ST_GeographyFromText('SRID=4326; POINT ("+ lng + " " + lat +")'))";
		preparedStatementGeoBusStop = c.prepareStatement(queryGeoBusStop);
		
		try{
			preparedStatementGeoBusStop.setString(1, id);
			preparedStatementGeoBusStop.setString(2, name);
			
			return preparedStatementGeoBusStop.executeUpdate();
		}
		finally{
			try{
				if (preparedStatementGeoBusStop != null)
					preparedStatementGeoBusStop.close();
			}
			catch(Exception e){
				System.out.println(e);
			}
		}
	}
	
	public static void readAndProcessData(Connection c, ResultSet rs) throws SQLException{
		System.out.println("Inserting data in GeoBusStop ...");
		
		try{
			while(rs.next()){
				String id = rs.getString("id");
				String name = rs.getString("name");
				Double lat = rs.getDouble("lat");
				Double lng = rs.getDouble("lng");
//				System.out.println(id + " " + name + " " + lat + " " + lng);
				
				insertGeoBusSop(c, id, name, lat, lng);
			}
		} finally {
			
			try{
				rs.close();
			}
			catch (Exception e){
				System.out.println(e);
			}
		}
		
		System.out.println("Data inserted in GeoBusStop.");
	}
			
	public static void main(String args[]) throws SQLException{
		// in your postgresql bash run
		// psql> CREATE EXTENSION Postgis;
		
		Connection conn = setupAndConnect();
		
		try{
			String queryBusStop = "select * from BusStop";
			preparedStatementBusStop = conn.prepareStatement(queryBusStop);
			ResultSet rs = preparedStatementBusStop.executeQuery();
			
			readAndProcessData(conn, rs);
		}
		finally{
			
			try{
				if (preparedStatementGeoBusStop != null)
					preparedStatementGeoBusStop.close();
			}
			catch (Exception e){
				System.out.println(e);
			}
			
		}
	}
}

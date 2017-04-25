package it.polito.ai.Lab3;


import java.util.*;
 
public class Dijkstra {
	
   
   
   private static List<Edge> setEdges(){
	   
	   //Read from class Edge to create Edges of graph
	   List<Edge> edges = new ArrayList<Edge>();
	   Edge e1 = new Edge();
	   Edge e2 = new Edge();
	   Edge e3 = new Edge();
	   Edge e4 = new Edge();
	   Edge e5 = new Edge();
	   Edge e6 = new Edge();
	   Edge e7 = new Edge();
	   Edge e8 = new Edge();
	   Edge e9 = new Edge();
	   e1.setIdSource("a");
	   e1.setIdDestination("b");
	   e1.setCost(7);
	   e2.setIdSource("a");
	   e2.setIdDestination("c");
	   e2.setCost(9);
	   e3.setIdSource("a");
	   e3.setIdDestination("f");
	   e3.setCost(14);
	   e4.setIdSource("b");
	   e4.setIdDestination("c");
	   e4.setCost(10);
	   e5.setIdSource("b");
	   e5.setIdDestination("d");
	   e5.setCost(15);
	   e6.setIdSource("c");
	   e6.setIdDestination("d");
	   e6.setCost(11);
	   e7.setIdSource("c");
	   e7.setIdDestination("f");
	   e7.setCost(2);
	   e8.setIdSource("d");
	   e8.setIdDestination("e");
	   e8.setCost(6);
	   e9.setIdSource("e");
	   e9.setIdDestination("f");
	   e9.setCost(9);
	   
	   edges.add(e1);
	   edges.add(e2);
	   edges.add(e3);
	   edges.add(e4);
	   edges.add(e5);
	   edges.add(e6);
	   edges.add(e7);
	   edges.add(e8);
	   edges.add(e9);
	   return edges;
	   
	   
   }
   
   private static final String START = "a";
   private static final String END = "d";
 
   public static void main(String[] args) {
      /*Graph g = new Graph(setEdges());
      g.dijkstra(START);
      g.printPath(END);
      //g.printAllPaths();
      System.out.println("Cost " + g.getCost() );
      for(String s : g.getPath()){
    	  System.out.println(s);
      }
      g.clearCost();
	  g.clearMinPath();
      g.dijkstra("b");
      g.printPath("f");
      System.out.println("Cost " + g.getCost() );
      for(String s : g.getPath()){
    	  System.out.println(s);
      }
      g.clearCost();
	  g.clearMinPath();
      g.dijkstra(START);
      g.printPath(END);
      System.out.println("Cost " + g.getCost() );
      for(String s : g.getPath()){
    	  System.out.println(s);
      }
      
      */
      
      
   }
}
 


/*
public class Dijkstra {

	public int dist = Integer.MAX_VALUE; // MAX_VALUE assumed to be infinity
	public Node previous = null;
	
	public NavigableSet<Node> minPath(Node srcNode, Node dstNode){
		
		  Node source = Graph.graph.get(srcNode);
		  if(source == null){
			  throw new Exception("source node doesn't exist");
		  }
	      NavigableSet<Node> path = new TreeSet<Node>();
	 
	      // set-up vertices
	      for (Node v : Graph.graph.values()) {
	         v.previous = v == source ? source : null;
	         v.dist = v == source ? 0 : Integer.MAX_VALUE;
	         q.add(v);
	      }
	 
	      dijkstra(q);
		
		
		return path;
		
		
	}
	
}*/

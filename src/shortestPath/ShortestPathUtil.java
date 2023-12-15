package shortestPath;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import database.DBUtil;

public class ShortestPathUtil {
	private static EdgeWeightedDiagraph graph = generateDiagraph();
	
	private static final String URL_OF_DB = "jdbc:sqlite:src/database/identifier.sqlite";
	
	private static  EdgeWeightedDiagraph generateDiagraph() {
		//ResultSet numOfCities=  DBUtil.executeQuery("Select count (*) from City");
		int numCity = getNumOfCities();
		EdgeWeightedDiagraph graph = new EdgeWeightedDiagraph(numCity);
		List<DirectedEdge> edges = getEdges();
		for(DirectedEdge e : edges) {
			graph.addEdge(e);
		}
		
		return graph;
		
	}
	
	private static int getNumOfCities() {
		int res = 0;
		String sql = "select count (*) from City";
        try (Connection c = DriverManager.getConnection(URL_OF_DB);
        		Statement stmt = c.createStatement();
        		ResultSet rs = stmt.executeQuery(sql)){
        			res = rs.getInt(1);
        	} catch ( Exception e ) {
        		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        		System.exit(0);
        	}
        return res;
	}
	
	private static List<DirectedEdge> getEdges(){
		List<DirectedEdge> res = new ArrayList<>();
		String sql = "select * from edges";
		try (Connection c = DriverManager.getConnection(URL_OF_DB);
		        Statement stmt = c.createStatement();
		        ResultSet rs = stmt.executeQuery(sql)){
		        	while(rs.next()) {
		        		int from = rs.getInt("From");
		        		int to = rs.getInt("To");
		        		double weight = rs.getDouble("Distance");
		        		res.add(new DirectedEdge(from, to, weight));
		        	}
		        } catch ( Exception e ) {
		          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		          System.exit(0);
		        }
		       
		return res;
		
	}
	
	private static List<Double> getShortestRoute(int src, int dest){
		//得到一个double数列，第一个是距离，后面是 终点->起点 的序号顺序
		double[] dist = new double[graph.getNumVertex()];  //6
		int[] prev = new int[graph.getNumVertex()];
		boolean[] visited = new boolean[graph.getNumVertex()];
		//boolean[] isInQueue = new boolean[graph.getNumVertex()];
		
		for(int i = 0; i < graph.getNumVertex(); i++) {
			if(i == src) {
				dist[i] = 0;
				prev[i] = -1;
			}else {
				dist[i] = Double.MAX_VALUE;
				prev[i] = -1;
			}
			
		}
		
		
		//Queue<Integer> q = new ArrayDeque<>();
		PriorityQueue<DirectedEdge> q = new PriorityQueue<>((o1, o2) ->  {return o1.weight() < o2.weight() ? -1 : 1;});
		q.offer(new DirectedEdge(src,src,0));
		while(!q.isEmpty()) {
			DirectedEdge currEdge = q.poll();
			int currVertex = currEdge.to();
			if(!visited[currVertex]) {
				visited[currVertex] = true;
				
				for(DirectedEdge edge : graph.getAdj(currVertex)) {
					if(!visited[edge.to()]) {
						double newDist = dist[currVertex] + edge.weight();
						if(newDist < dist[edge.to()]) {
							dist[edge.to()] = newDist;
							prev[edge.to()] = edge.from();
							q.add(new DirectedEdge(currVertex, edge.to(), newDist));
						}
					}
				}
			}
			
//			List<DirectedEdge> edges = graph.getAdj(curr);
//			for(DirectedEdge e : edges) {
//				//System.out.println(e);
//				//System.out.println(visited[e.to()]);
//				
//				if(visited[e.to()] == true) continue;
//				
//				double currDistance = dist[e.from()] + e.weight();
//				
//				//System.out.println(currDistance);
//				if(currDistance < dist[e.to()]) {
//					dist[e.to()] =  currDistance;
//					prev[e.to()] =  e.from();
//				}
//				
//				if(isInQueue[e.to()] == false) {
//					q.offer(e.to());
//					isInQueue[e.to()] = true;
//				}				
//			}
//			
//			visited[curr]  = true;
		}
		
		
		List<Double> res = new ArrayList<>();
		if(dist[dest] == Double.MAX_VALUE) {
			res.add(Double.MAX_VALUE);
			return res;
		}
			
		res.add(dist[dest]);
		int currCity = dest;
		int prevCity = prev[dest];
		
		while(prevCity != -1) {
			res.add((double)currCity);
			currCity = prevCity;
			prevCity = prev[prevCity];
		}
		
		res.add((double) src);
		
		return res;
	}
	
	private static List<Double> getShortestRoute(String srcCity, String destCity) {
		String sql1 = "select * from City where City_Name = '" + srcCity + "'";
		String sql2 = "select * from City where City_Name = '" + destCity + "'";
		int src = -1;
		int dest = -1;
	
        try {
        	 Connection c = DriverManager.getConnection(URL_OF_DB);
    		 Statement stmt = c.createStatement();
    		 ResultSet rs = stmt.executeQuery(sql1);
    		 
    		 src = rs.getInt("City_ID");
    		 //System.out.println("src = "+src);
    		 //System.out.println(rs.getString("City_Name"));
    		 rs = stmt.executeQuery(sql2);
			 dest = rs.getInt("City_ID");	 
			 //System.out.println("dest = "+dest);
			 //System.out.println(rs.getString("City_Name"));
			 c.close();
			 stmt.close();
			 rs.close();
        	} catch ( Exception e ) {
        		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        		System.exit(0);
        	}
        if(src == -1 || dest == -1) {
        	System.exit(0);
        }
        
        return getShortestRoute(src, dest);
        
	}
	
	public static double getShortestDistance(String srcCity, String destCity) {
		return getShortestRoute(srcCity, destCity).get(0);
	}
	
	public static List<String> getPath(String srcCity, String destCity){
		List<String> path = new ArrayList<>();
		List<Double> list = getShortestRoute(srcCity, destCity);
		
		try {
       	 	Connection c = DriverManager.getConnection(URL_OF_DB);
       	 	Statement stmt = c.createStatement();
       	 	ResultSet rs = null;
       	 	String sql = null;
       	 	for(int i = list.size() - 1; i > 0; i--) {
       	 		sql = "select * from City where City_ID = '" + list.get(i) + "'";	
       	 		rs = stmt.executeQuery(sql);
       	 		path.add(rs.getString("City_Name"));
       	 	}
   	 		c.close();
   	 		stmt.close();
   	 		rs.close();
       	} catch ( Exception e ) {
       		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
       		System.exit(0);
       	}
		
		return path;
	}
	
//	public static void main(String[] args) throws SQLException {
//		// TODO Auto-generated method stub
//		System.out.println(graph);
//		
//		System.out.println(getShortestDistance("Chicago", "New York"));
//		
//		System.out.println(getPath("Chicago", "New York"));
//	}
}

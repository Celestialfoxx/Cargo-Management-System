package shortestPath;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetShippingWareHouseUtil {
	
	private static final String URL_OF_DB = "jdbc:sqlite:src/database/identifier.sqlite";
	
	public static String getShippingWarehouse(String destCity) {
		String res = null;
		double dist = Double.MAX_VALUE;
		List<String> wareHouse = getWarehouse();
		//System.out.println(wareHouse);
		for(String w : wareHouse) {
			double currDist = ShortestPathUtil.getShortestDistance(w, destCity);
			//System.out.println(currDist);
			if(currDist < dist) {
				res = w;
				dist = currDist;
			}
		}
		
		return res;
	}

	private static  List<String> getWarehouse(){
		List<String> res = new ArrayList<>();
		String sql = "select * from City where isRepository = 1";
        try (Connection c = DriverManager.getConnection(URL_OF_DB);
        		Statement stmt = c.createStatement();
        		ResultSet rs = stmt.executeQuery(sql)){
        			
        		while(rs.next()) {
        			res.add(rs.getString("City_Name"));
        		}
        	} catch ( Exception e ) {
        		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        		System.exit(0);
        	}
        return res;
	}
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		System.out.println(getShippingWarehouse("San Jose"));
//	}

}

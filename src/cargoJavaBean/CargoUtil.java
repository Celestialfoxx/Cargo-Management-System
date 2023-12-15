package cargoJavaBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import database.DBUtil;
import javafx.scene.control.cell.PropertyValueFactory;
import shortestPath.GetShippingWareHouseUtil;

public class CargoUtil {
	private static final String URL_OF_DB = "jdbc:sqlite:src/database/identifier.sqlite";
	
	// id是货物单独的id， 例如sofa 1号， 那么id就是sofa数据库中的sofa_id = 1, trade是String，你卖sofa就填sofa，卖laptop就填laptop
//	public static boolean sell(int id, String trade) {
//		// check whether the No.id good in trade is Returned
//		// if this good is not returned good, and there is any returned good in repository
//		// then sell the returned one
//		if(!isReturned(id, trade)) {
//			int newID = getReturned(trade);
//			System.out.println("Sell the returned product first. The new ID is " + newID);
//			if(newID != -1) {
//				return sell(newID, trade);
//			}
//		}
//		
//		
//		
//		//String trade = "Sofa";
//		String selectSql = "Select * from "+ trade +" where " + trade +"_ID = " + id;
//		String deleteSql = "Delete from " + trade + " where " + trade +"_ID = " + id;
//		String getVolumeSql = "Select Volume from cargo where Trade_Name = '" + trade + "'";
//		double newVolume = -1;
//		
//		String updateStatusSql = "Update Cargo set Status = 'Low' where Trade_Name = '" + trade + "'";
//		
//		 try {
//     		Connection c = DriverManager.getConnection(URL_OF_DB);
//     		Statement stmt = c.createStatement();
//     		//System.out.println(selectSql);
//     		ResultSet rs = stmt.executeQuery(selectSql);
//     		//ResultSet rr = null;
//     		if(rs.next()) {
//     			stmt.executeUpdate(deleteSql);
//     			newVolume = stmt.executeQuery(getVolumeSql).getDouble("Volume") - 1;
//     			String updateVolumeSql = "Update Cargo set Volume = " + newVolume + " where Trade_Name = '" + trade + "'";
//     			stmt.executeUpdate(updateVolumeSql);
//     			if(newVolume <= 5) {
//     				stmt.executeUpdate(updateStatusSql);
//     			}
//     		}else {
//     			c.close();
//     			stmt.close();
//     			rs.close();
//     			//rr.close();
//     			System.out.println("Not exist");
//     			return false;
//     		}
//     		c.close();
// 			stmt.close();
// 			rs.close();
//     		//rr.close();
//     		
//     	} catch ( Exception e ) {
//     		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//     		System.exit(0);
//     	}
//		return true;
//	}
	
	public static boolean sell(int id, String trade, int customerID) {
		if(!isReturned(id, trade)) {
			int newID = getReturned(trade);
			System.out.println("Sell the returned product first. The new ID is " + newID);
			if(newID != -1) {
				return sell(newID, trade, customerID);
			}
		}
		
		Connection conn = DBUtil.connect();
		
    	try(Statement stmt = conn.createStatement()){
    		//check if trade_id exists
    		ResultSet rs = stmt.executeQuery("Select * from "+ trade +" where " + trade +"_ID = " + id + " and Status = 'In Stock'");
    		if(!rs.next()) {
    			rs.close();
    			System.out.println("Item doesn't exist");
    			return false;
    		}
    		
    		double price = rs.getDouble("Price");
    		
    		//check if customer exists
    		rs = stmt.executeQuery("Select * from Customer where CustomerID = " + customerID);
    		if(!rs.next()) {
    			rs.close();
    			System.out.println("Customer doesn't exist");
    			return false;
    		}
    		String dest = rs.getString("Address");
    		String src = GetShippingWareHouseUtil.getShippingWarehouse(dest);
    		
    		
    		//更新sofa中的卖出时间，status信息
			String  sellSql = "Update " + trade + " set TimeOutOfStorage = current_timestamp, Status = 'Sold' where " +trade+ "_id = " + id;
    		stmt.executeUpdate(sellSql);
    		
    		//更新cargo中的volume
    		String getVolumeSql = "Select * from Cargo where Trade_Name = '" + trade + "'";
    		rs = stmt.executeQuery(getVolumeSql);
    		int cargoID = rs.getInt("CargoID");
    		double newVolume = rs.getDouble("Volume") - 1;
    		String updateVolumeSql = "Update Cargo set Volume = " + newVolume + " where Trade_Name = '" + trade + "'";
 			stmt.executeUpdate(updateVolumeSql);
 			//更新cargo中的status
 			if(newVolume <= 5) {
 				stmt.executeUpdate("Update Cargo set Status = 'Low' where Trade_Name = '" + trade + "'");	
    		}
 			
 			//更新shipment
 			stmt.executeUpdate("Insert into Shipment (Origin, Destination, CargoID, ItemID) Values ('" + src + "', '" + dest + "', " + cargoID + ", " + id+")");
 			int shipmentID = stmt.executeQuery("select * from Shipment where ItemID = " + id).getInt("ShipmentID");
    		
 			//更新Invoice
 			int numRecords = stmt.executeQuery("select count(*) from Invoice where CustomerID = " + customerID).getInt(1);
 			
 				//如果是新顾客					
			if(numRecords == 0) {
				double newCustomerPrice = price - 20 <= 0 ? 0 : price - 20; 
				stmt.executeUpdate("Insert into Invoice (CustomerID, ShipmentID, Amount, DateIssued, PaymentStatus) Values( "
	    				+ customerID +", " +shipmentID + ", " + newCustomerPrice + ", current_date, 'Paid')");
			}else {
				//如果老顾客
				stmt.executeUpdate("Insert into Invoice (CustomerID, ShipmentID, Amount, DateIssued, PaymentStatus) Values( "
	    				+ customerID +", " +shipmentID + ", " + price + ", current_date, 'Paid')");
			}
 			
 			
 			
 			
    		
    				
    	}catch (SQLException e) {
			//System.out.println("Database error in executeAdd.");
            e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		return true;
	}
	
	//returned代表是否是退回商品，如果是，该商品加入数据库的价格是原来的80%
//    public static boolean store(int id, String trade, boolean returned) {
//    	
//    	String getVolumeSql = "Select Volume from Cargo where Trade_Name = '" + trade + "'";
//    	String getNormalPrice = "Select Original_Price From Cargo where Trade_Name = '" + trade + "'";
//		double newVolume = -1;
//		double newPrice = -1;
//		int newReturned = 0;
//		
//		
//  		
//    	 try {
//      		Connection c = DriverManager.getConnection(URL_OF_DB);
//      		Statement stmt = c.createStatement();
//      		if(stmt.executeQuery("Select count(*) From " + trade + " where " + trade +"_ID = " + id).getInt(1) == 1) {
//      			System.out.println("ID duplicate");
//      			return false;
//      		}
//      		System.out.println(getVolumeSql);
//      		//check if volume > 50
//      		double originalVolume = stmt.executeQuery(getVolumeSql).getDouble("Volume");
//      		//System.out.println(originalVolume);
//      		newVolume = originalVolume + 1;
//      		String updateVolumeSql = "Update Cargo set Volume = " + newVolume + " where Trade_Name = '" + trade + "'";
//      		//System.out.println(newVolume);
//  			if(newVolume > 50) {
//  				System.out.println("Exceed limit");
//  				return false;
//  			}else {
//  				System.out.println(updateVolumeSql);
//  				stmt.executeUpdate(updateVolumeSql);
//  			}
//  			//System.out.println(newVolume);
//
//  			//if returned, reduce the price
//      		double normalPrice = stmt.executeQuery(getNormalPrice).getDouble(1);    		
//      		if(returned) {
//      			newPrice = normalPrice * 0.8;
//      			newReturned = 1;
//      		}else {
//      			newPrice = normalPrice;
//      		}
//      		String insertSql = "Insert into " + trade + " (" + trade + "_ID, Price, Returned) "
//      				+ "Values(" + id + ", " + newPrice + "," + newReturned + ")";
//      		System.out.println(insertSql);
//      		stmt.executeUpdate(insertSql);
//  			
//  			
//  			      		
//      		c.close();
//  			stmt.close();
//  			
//      		
//      		
//      	} catch ( Exception e ) {
//      		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//      		System.exit(0);
//      	}
// 		return true;
//    }
    
    public static boolean store(String trade, boolean returned) {
    	String getVolumeSql = "Select Volume from Cargo where Trade_Name = '" + trade + "'";
    	String getNormalPrice = "Select Original_Price From Cargo where Trade_Name = '" + trade + "'";
		double newVolume = -1;
		double newPrice = -1;
		int newReturned = 0;
		
		
  		
    	 try {
      		Connection c = DriverManager.getConnection(URL_OF_DB);
      		Statement stmt = c.createStatement();
      		
      		
      		System.out.println(getVolumeSql);
      		//check if volume > 50
      		double originalVolume = stmt.executeQuery(getVolumeSql).getDouble("Volume");
      		//System.out.println(originalVolume);
      		newVolume = originalVolume + 1;
      		String updateVolumeSql = "Update Cargo set Volume = " + newVolume + " where Trade_Name = '" + trade + "'";
      		//System.out.println(newVolume);
      		if(newVolume > 5) {
      			stmt.executeUpdate("Update Cargo set Status = 'In Stock' where Trade_Name = '" + trade + "'");
      		}
  			if(newVolume > 50) {
  				System.out.println("Exceed limit");
  				return false;
  			}else {
  				System.out.println(updateVolumeSql);
  				stmt.executeUpdate(updateVolumeSql);
  			}
  			//System.out.println(newVolume);

  			//if returned, reduce the price
      		double normalPrice = stmt.executeQuery(getNormalPrice).getDouble(1); 
      		System.out.println("normalPrice: " + normalPrice);
      		if(returned) {
      			newPrice = normalPrice * 0.8;
      			newReturned = 1;
      		}else {
      			newPrice = normalPrice;
      		}
      		String insertSql = "Insert into " + trade + " (Price, Returned, TimeInStorage, Status) "
      				+ "Values(" + newPrice + "," + newReturned + ", current_timestamp, 'In Stock')";
      		System.out.println(insertSql);
      		stmt.executeUpdate(insertSql);
  			
  			
  			      		
      		c.close();
  			stmt.close();
  			
      		
      		
      	} catch ( Exception e ) {
      		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      		System.exit(0);
      	}
 		return true;
    }

    private static int getReturned(String trade) {
    	 String query = "select * from " + trade + " where Returned = 1 and Status = 'In Stock'";
    	 try (Connection conn = DriverManager.getConnection(URL_OF_DB);
 	            Statement stmt = conn.createStatement();
 	            ResultSet rs = stmt.executeQuery(query)) {
 	           if (rs.next()) {	         
 	               return rs.getInt(trade + "_ID");
 	           }
	
 	    } catch (SQLException e) {
 	    	 System.out.println(e.getMessage());
 	    }
    	 return -1;
    }
    
    private static boolean isReturned(int id, String trade) {
    	 String query = "select * from " + trade + " where " + trade + "_id = " + id;
    	 try (Connection conn = DriverManager.getConnection(URL_OF_DB);
 	            Statement stmt = conn.createStatement();
 	            ResultSet rs = stmt.executeQuery(query)) {
 	           if (rs.next()) {	         
 	               return rs.getBoolean("Returned");
 	           }
	
 	    } catch (SQLException e) {
 	    	 System.out.println(e.getMessage());
 	    }
    	 return false;
    	
    }
    
    


}

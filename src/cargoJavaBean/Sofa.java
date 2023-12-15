package cargoJavaBean;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.Deque;

public class Sofa {
	private int sofa_id;
	private double price;
	private boolean returned;
	private Date time_in_storage;
	private Date time_out_of_storage;
	private String status;
	private static final String URL_OF_DB = "jdbc:sqlite:src/database/identifier.sqlite";
	public static Deque<Sofa> sofaDeque;
	public final static double NORMAL_PRICE = 200;
	
	public Sofa() {}
	
	
	public Sofa(int sofa_id, double price, boolean returned, Date time_in_storage, Date time_out_of_storage, String status) {
		if(sofaDeque == null) {
			initDeque();
		}
		this.sofa_id = sofa_id;
		this.price = price;
		this.returned = returned;
		this.time_in_storage = time_in_storage;
		this.time_out_of_storage = time_out_of_storage;
		this.status = status;
	}


	public int getSofa_id() {
		return sofa_id;
	}


	public void setSofa_id(int sofa_id) {
		this.sofa_id = sofa_id;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public boolean isReturned() {
		return returned;
	}


	public void setReturned(boolean returned) {
		this.returned = returned;
	}
	
	public Date getTime_in_storage() {
		return time_in_storage;
	}


	public void setTime_in_storage(Date time_in_storage) {
		this.time_in_storage = time_in_storage;
	}
	
	public Date getTime_out_of_storage() {
		return time_out_of_storage;
	}


	public void setTime_out_of_storage(Date time_out_of_storage) {
		this.time_out_of_storage = time_out_of_storage;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	private void initDeque(){
		String sql1 = "select * from Sofa where Returned = 0";
		String sql2 = "select * from Sofa where Returned = 1";
        try {
        		Connection c = DriverManager.getConnection(URL_OF_DB);
        		Statement stmt = c.createStatement();
        		ResultSet rs = stmt.executeQuery(sql1);
        			
        		while(rs.next()) {
        			int sofaID = rs.getInt("sofaID");
        			double price = rs.getDouble("Price");
        			Date d1 = rs.getDate("TimeInStorage");
        			Date d2 = rs.getDate("TimeOutOfStorage");
        			String st = rs.getString("Status");
        			
        			sofaDeque.offerFirst(new Sofa(sofaID, price, false, d1, d2, st ));
        		}
        		
        		rs = stmt.executeQuery(sql2);
        		while(rs.next()) {
        			int sofaID = rs.getInt("sofaID");
        			double price = rs.getDouble("Price");
        			Date d1 = rs.getDate("TimeInStorage");
        			Date d2 = rs.getDate("TimeOutOfStorage");
        			String st = rs.getString("Status");
        			
        			sofaDeque.offerFirst(new Sofa(sofaID, price, true, d1, d2, st ));
        		}
        		c.close();
        		stmt.close();
        		rs.close();
        	} catch ( Exception e ) {
        		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        		System.exit(0);
        }
        return;
	}

//	public static boolean sell(int id) {
//		String trade = "Sofa";
//		String selectSql = "Select * from Sofa where " + trade +"_id = " + id;
//		String deleteSql = "Delete from " + trade + " where " + trade +"_id = " + id;
//		String getVolumeSql = "Select Volume from cargo where Trade_Name = '" + trade + "'";
//		double newVolume = -1;
//		String updateVolumeSql = "Update Cargo set Volume = " + newVolume + " where Trade_Name = '" + trade + "'";
//		String updateStatusSql = "Update Cargo set Status = 'Low' where Trade_Name = '" + trade + "'";
//		
//		 try {
//     		Connection c = DriverManager.getConnection(URL_OF_DB);
//     		Statement stmt = c.createStatement();
//     		ResultSet rs = stmt.executeQuery(selectSql);
//     		
//     		if(rs.next()) {
//     			stmt.executeQuery(deleteSql);
//     			newVolume = stmt.executeQuery(getVolumeSql).getDouble("Volume") - 1;
//     			stmt.executeQuery(updateVolumeSql);
//     			if(newVolume <= 5) {
//     				stmt.executeQuery(updateStatusSql);
//     			}
//     		}else {
//     			c.close();
//     			stmt.close();
//     			rs.close();
//     			System.out.println("Not exist");
//     			return false;
//     		}
//     		c.close();
// 			stmt.close();
// 			rs.close();
//     		
//     		
//     	} catch ( Exception e ) {
//     		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//     		System.exit(0);
//     	}
//		return true;
//	}
//
//	public static boolean store(int id, boolean returned) {
////		String trade = "Sofa";
////		String selectSql = "Select * from Sofa where " + trade +"_id = " + id;
////		String deleteSql = "Delete from " + trade + " where " + trade +"_id = " + id;
////		String getVolumeSql = "Select from cargo where Trade_Name = " + trade;
////		double newVolume = -1;
////		String updateVolumeSql = "Update Cargo set Volume = " + newVolume + "where Trade_Name = " + trade;
//		
//		
////		
////		 try {
////     		Connection c = DriverManager.getConnection(URL_OF_DB);
////     		Statement stmt = c.createStatement();
////     		ResultSet rs = stmt.executeQuery(selectSql);
////     		
////     		if(rs.next()) {
////     			stmt.executeQuery(deleteSql);
////     			newVolume = stmt.executeQuery(getVolumeSql).getDouble("Volume") - 1;
////     			stmt.executeQuery(updateVolumeSql);
////     		}else {
////     			c.close();
////     			stmt.close();
////     			rs.close();
////     			System.out.println("Not exist");
////     			return false;
////     		}
////     		c.close();
//// 			stmt.close();
//// 			rs.close();
////     		
////     		
////     	} catch ( Exception e ) {
////     		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
////     		System.exit(0);
////     	}
//		return true;
//	}
	
	
	
	
	

}

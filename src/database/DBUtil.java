package database;
import java.sql.*;

public class DBUtil {
	// 改各自的路径
	private static final String URL_OF_DB = "jdbc:sqlite:src/database/identifier.sqlite";
	
	public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL_OF_DB);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
	
//	public void executeAdd(String insertStmt) {
//        try (Connection conn = DBUtil.connect();
//        	Statement stmt = conn.createStatement()) {
//            int rowsAffected = stmt.executeUpdate(insertStmt);
//            System.out.println("Rows inserted: " + rowsAffected);
//        } catch (SQLException e) {
//            System.out.println("Database error in executeAdd.");
//            e.printStackTrace();
//        }
//    }
//	
//	public static ResultSet executeQuery(String query) {
//		ResultSet rr = null;
//        try (Connection conn = DBUtil.connect();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(query)) {
////            while (rs.next()) {
////                // process each row
////                System.out.println(rs.getInt("ShipmentID"));
////            }
//        	rr = rs;
//        } catch (SQLException e) {
//            System.out.println("Database error in executeQuery.");
//            e.printStackTrace();
//        }
//        return rr;
//    }
	
	public static void main(String[] args) {
		
//		executeQuery("SELECT * FROM Cargo;");
//        Connection connection = null;
//        try {
//            // build connection to database
//            Class.forName("org.sqlite.JDBC");
//            
//            connection = DriverManager.getConnection(URL_OF_DB);
//            Statement stmt = null;
//            System.out.println("Connect to SQLite database successfully！");
//
//            stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM customers;");
//
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                System.out.println("ID = " + id);
//                System.out.println("NAME = " + name);
//                System.out.println();
//            }
//            rs.close();
//            stmt.close();
//            connection.close();
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } finally {
//            try {
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
//            }
//        }
    }
}
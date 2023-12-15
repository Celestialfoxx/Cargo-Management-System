package cargoJavaBean;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.Deque;

public class Item {
	private int item_id;
	private double price;
	private boolean returned;
	private Date time_in_storage;
	private Date time_out_of_storage;
	private String status;
	private static final String URL_OF_DB = "jdbc:sqlite:src/database/identifier.sqlite";
	public final static double NORMAL_PRICE = 200;
	
	public Item() {}
	
	
	public Item(int item_id, double price, boolean returned, Date time_in_storage, Date time_out_of_storage, String status) {
		this.item_id = item_id;
		this.price = price;
		this.returned = returned;
		this.time_in_storage = time_in_storage;
		this.time_out_of_storage = time_out_of_storage;
		this.status = status;
	}



	public int getItem_id() {
		return item_id;
	}


	public void setItem_id(int item_id) {
		this.item_id = item_id;
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
	
	
	

}

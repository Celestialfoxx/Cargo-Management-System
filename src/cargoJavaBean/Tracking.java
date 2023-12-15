package cargoJavaBean;

import java.time.LocalDate;

public class Tracking {
	private int trackingID;
    private int cargoID;
    private String location;
    private LocalDate date;
    private String statusUpdate;
    
    public Tracking() {
    	
    }
	public Tracking(int trackingID, int cargoID, LocalDate date, String location, String statusUpdate) {
		super();
		this.trackingID = trackingID;
		this.cargoID = cargoID;
		this.date = date;
		this.location = location;
		this.statusUpdate = statusUpdate;
	}
	public int getTrackingID() {
		return trackingID;
	}
	public void setTrackingID(int trackingID) {
		this.trackingID = trackingID;
	}
	public int getCargoID() {
		return cargoID;
	}
	public void setCargoID(int cargoID) {
		this.cargoID = cargoID;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getStatusUpdate() {
		return statusUpdate;
	}
	public void setStatusUpdate(String statusUpdate) {
		this.statusUpdate = statusUpdate;
	}
    
    
}

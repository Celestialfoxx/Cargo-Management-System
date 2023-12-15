package cargoJavaBean;

public class Transport {
	private int transportID;
    private String transportType;
    private double capacity;
    private String availabilityStatus;
    
	public Transport(int transportID, String transportType, double capacity, String availabilityStatus) {
		super();
		this.transportID = transportID;
		this.transportType = transportType;
		this.capacity = capacity;
		this.availabilityStatus = availabilityStatus;
	}
	
	public int getTransportID() {
		return transportID;
	}
	public void setTransportID(int transportID) {
		this.transportID = transportID;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	public double getCapacity() {
		return capacity;
	}
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
	public String getAvailabilityStatus() {
		return availabilityStatus;
	}
	public void setAvailabilityStatus(String availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}
    
    
}

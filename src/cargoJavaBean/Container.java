package cargoJavaBean;

public class Container {
	private int containerID;
    private double capacity;
    private String containerType;
    private String location;
    
	public Container(int containerID, double capacity, String containerType, String location) {
		super();
		this.containerID = containerID;
		this.capacity = capacity;
		this.containerType = containerType;
		this.location = location;
	}

	public int getContainerID() {
		return containerID;
	}

	public void setContainerID(int containerID) {
		this.containerID = containerID;
	}

	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
    
    
}

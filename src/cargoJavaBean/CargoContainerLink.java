package cargoJavaBean;

public class CargoContainerLink {
	private int cargoID;
    private int containerID;
	public CargoContainerLink(int cargoID, int containerID) {
		super();
		this.cargoID = cargoID;
		this.containerID = containerID;
	}
	public int getCargoID() {
		return cargoID;
	}
	public void setCargoID(int cargoID) {
		this.cargoID = cargoID;
	}
	public int getContainerID() {
		return containerID;
	}
	public void setContainerID(int containerID) {
		this.containerID = containerID;
	}
    
    
}

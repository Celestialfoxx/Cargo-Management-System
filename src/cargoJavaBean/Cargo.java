package cargoJavaBean;

public class Cargo {
    private int cargoID;
    private String description;
    private double weight;
    private double volume;
    private String type;
    private String status;
    private String tradeName;
    private double originalPrice;
    
    
    
	public Cargo(int cargoID, String type, String status) {
		super();
		this.cargoID = cargoID;
		this.type = type;
		this.status = status;
	}

	public Cargo(int cargoID, String description, double weight, double volume, String type, String status) {
		super();
		this.cargoID = cargoID;
		this.description = description;
		this.weight = weight;
		this.volume = volume;
		this.type = type;
		this.status = status;
	}

	public Cargo(int cargoID, String description, double weight, double volume, String type, String status,
			String tradeName, double originalPrice) {
		super();
		this.cargoID = cargoID;
		this.description = description;
		this.weight = weight;
		this.volume = volume;
		this.type = type;
		this.status = status;
		this.tradeName = tradeName;
		this.originalPrice = originalPrice;
	}

	public int getCargoID() {
		return cargoID;
	}

	public void setCargoID(int cargoID) {
		this.cargoID = cargoID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}
    

    
    
}

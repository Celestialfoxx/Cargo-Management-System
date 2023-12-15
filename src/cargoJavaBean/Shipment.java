package cargoJavaBean;

import java.time.LocalDate;

public class Shipment {
    private int shipmentID;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private String origin;
    private String destination;
    private int cargoID;
    private String location;
    private int transportID;
    private int itemID;
    
	public Shipment(int shipmentID, LocalDate departureDate, LocalDate arrivalDate, String origin, String destination, int cargoID, String location, 
			int transportID, int itemID) {
		super();
		this.shipmentID = shipmentID;
		this.departureDate = departureDate;
		this.arrivalDate = arrivalDate;
		this.origin = origin;
		this.destination = destination;
		this.cargoID = cargoID;
		this.location = location;
		this.transportID = transportID;
		this.itemID = itemID;
	}

	public Shipment() {
	}

	public int getShipmentID() {
		return shipmentID;
	}

	public void setShipmentID(int shipmentID) {
		this.shipmentID = shipmentID;
	}

	public LocalDate getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}

	public LocalDate getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getTransportID() {
		return transportID;
	}

	public void setTransportID(int transportID) {
		this.transportID = transportID;
	}
	
	public int getCargoID() {
		return cargoID;
	}

	public void setCargoID(int cargoID) {
		this.cargoID = cargoID;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public void SetItemID(int itemID) {
		this.itemID = itemID;
	}
	
	public int getItemID() {
		return itemID;
	}
    
	
    
}

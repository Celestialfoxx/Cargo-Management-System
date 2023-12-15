package cargoJavaBean;

import java.time.LocalDate;

public class Invoice {
    private int invoiceID;
    private int customerID;
    private int shipmentID;
    private double amount;
    private LocalDate dateIssued;
    private String paymentStatus;
    public Invoice() {}
	public Invoice(int invoiceID, int customerID, int shipmentID, double amount, LocalDate dateIssued,
			String paymentStatus) {
		super();
		this.invoiceID = invoiceID;
		this.customerID = customerID;
		this.shipmentID = shipmentID;
		this.amount = amount;
		this.dateIssued = dateIssued;
		this.paymentStatus = paymentStatus;
	}
	public int getInvoiceID() {
		return invoiceID;
	}
	public void setInvoiceID(int invoiceID) {
		this.invoiceID = invoiceID;
	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public int getShipmentID() {
		return shipmentID;
	}
	public void setShipmentID(int shipmentID) {
		this.shipmentID = shipmentID;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public LocalDate getDateIssued() {
		return dateIssued;
	}
	public void setDateIssued(LocalDate dateIssued) {
		this.dateIssued = dateIssued;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
    
	
    
}

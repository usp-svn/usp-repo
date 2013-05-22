package net.webHMI.pli.client;

import java.io.Serializable;


public class PLIData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pipeno;
	private String partNumber;
	private String grade;
	private String customer;
	private String jobnumber;
	private double od;
	private double length;
	private double gauge;
	private double weight;
	private String currentWarehouse;
	private String currentBin;
	private String newBin;
	private String description;
	private int status;
	
	public String getPipeno() {
		return pipeno;
	}
	public void setPipeno(String pipeno) {
		this.pipeno = pipeno;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getJobnumber() {
		return jobnumber;
	}
	public void setJobnumber(String jobnumber) {
		this.jobnumber = jobnumber;
	}
	
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public double getGauge() {
		return gauge;
	}
	public void setGauge(double gauge) {
		this.gauge = gauge;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getCurrentBin() {
		return currentBin;
	}
	public void setCurrentBin(String currentBin) {
		this.currentBin = currentBin;
	}
	public String getNewBin() {
		return newBin;
	}
	public void setNewBin(String newBin) {
		this.newBin = newBin;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public String getCurrentWarehouse() {
		return currentWarehouse;
	}
	public void setCurrentWarehouse(String currentWarehouse) {
		this.currentWarehouse = currentWarehouse;
	}
	public double getOd() {
		return od;
	}
	public void setOd(double od) {
		this.od = od;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}

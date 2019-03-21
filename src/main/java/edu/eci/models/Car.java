package edu.eci.models;

import java.io.Serializable;
import java.util.UUID;

public class Car implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String licencePlate;
    private String brand;
    private UUID id;
    
    public Car(String licencePLate,String brand,UUID id) {
    	this.brand=brand;
    	this.licencePlate=licencePLate;
    	this.id=id;
		// TODO Auto-generated constructor stub
	}
    public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public Car() {
		// TODO Auto-generated constructor stub
	}

	public String getLicencePlate() {
		return licencePlate;
	}

	public void setLicencePlate(String licencePlate) {
		this.licencePlate = licencePlate;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

}

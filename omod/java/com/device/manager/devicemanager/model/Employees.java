package com.device.manager.devicemanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "employees")
public class Employees {

	@Id
	@Column(name="employes_id")
	private int employeeId;
	
	private String name;
	
	@Column(name="cnic")
	private String nationalIdentity;

	@Column(name = "mobile_number")
	private String mobileNumber;
	
	public Employees() {
		// TODO Auto-generated constructor stub
	}

	
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNationalIdentity() {
		return nationalIdentity;
	}

	public void setNationalIdentity(String nationalIdentity) {
		this.nationalIdentity = nationalIdentity;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
		
}

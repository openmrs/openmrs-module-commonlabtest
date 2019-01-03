package com.device.manager.devicemanager.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name= "devices_request")
public class DevicesRequests {
	
	@Id
	@Column(name= "devices_request_id")
	private int devicesRequestId;
	
	@Column(name ="request_date")
	private Date requestDate;
    
	@ManyToOne
	@JoinColumn(name="devices_id", nullable =false)
	private Devices deviceId;
	
	@ManyToOne
	@JoinColumn(name="employees_id")
	private Employees employeeId;

	private String status;
	
	
	public DevicesRequests() {
		// TODO Auto-generated constructor stub
	}


	public int getDevicesRequestId() {
		return devicesRequestId;
	}



	public void setDevicesRequestId(int devicesRequestId) {
		this.devicesRequestId = devicesRequestId;
	}



	public Date getRequestDate() {
		return requestDate;
	}



	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}



	public Devices getDeviceId() {
		return deviceId;
	}



	public void setDeviceId(Devices deviceId) {
		this.deviceId = deviceId;
	}



	public Employees getEmployeeId() {
		return employeeId;
	}



	public void setEmployeeId(Employees employeeId) {
		this.employeeId = employeeId;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}
	
}

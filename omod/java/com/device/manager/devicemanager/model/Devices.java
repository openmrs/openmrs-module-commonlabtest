package com.device.manager.devicemanager.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "devices")
public class Devices {
	
	@Id
	@Column(name = "devices_id")
	private int devicesId;
	
	private String name;
	
	private String description;
	
	@Column(name = "register_date")
	private Date registerDate;
	
	@ManyToOne
	@JoinColumn(name = "register_by", nullable = false)
	private Employees registerBy;
	
	@ManyToOne
	@JoinColumn(name = "devices_type_id", nullable = false)
	private DevicesType devicesType;
	
	// constructor
	public Devices() {
	}
	
	public int getDevicesId() {
		return devicesId;
	}
	
	public void setDevicesId(int devicesId) {
		this.devicesId = devicesId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getRegisterDate() {
		return registerDate;
	}
	
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	public Employees getRegisterBy() {
		return registerBy;
	}
	
	public void setRegisterBy(Employees registerBy) {
		this.registerBy = registerBy;
	}
	
	public DevicesType getDevicesType() {
		return devicesType;
	}
	
	public void setDevicesType(DevicesType devicesType) {
		this.devicesType = devicesType;
	}
	
}

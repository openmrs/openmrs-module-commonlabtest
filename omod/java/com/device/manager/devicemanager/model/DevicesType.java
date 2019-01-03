package com.device.manager.devicemanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="devices_type")
public class DevicesType {
	
	@Id
	@Column(name="devices_type_id")
	private int deviceTypeId;
	private String model;
	private String manufacturer;
	@Column(name ="operating_system")
	private String os;
	
	public DevicesType() {
		// TODO Auto-generated constructor stub
	}
	

	public int getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(int deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	

}

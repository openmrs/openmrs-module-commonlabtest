package com.device.manager.devicemanager.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "devices_received")
public class ReceivedDevices {
	
	@Id
	@Column(name= "received_id")
	private int id;
	
	@Column(name = "received_from")
	private int  devicesSubmitter;
	
	@ManyToOne
	@JoinColumn(name = "devices_id")
	private Devices devicesId;
	
	@ManyToOne
	@JoinColumn(name = "received_by")
	private Employees collector;
	
	@Column(name ="received_date")
	private Date receivedDate;
	
	
	public ReceivedDevices() {
		// TODO Auto-generated constructor stub
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getDevicesSubmitter() {
		return devicesSubmitter;
	}


	public void setDevicesSubmitter(int devicesSubmitter) {
		this.devicesSubmitter = devicesSubmitter;
	}


	public Devices getDevicesId() {
		return devicesId;
	}


	public void setDevicesId(Devices devicesId) {
		this.devicesId = devicesId;
	}


	public Employees getCollector() {
		return collector;
	}


	public void setCollector(Employees collector) {
		this.collector = collector;
	}


	public Date getReceivedDate() {
		return receivedDate;
	}


	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	
}

package com.device.manager.devicemanager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.device.manager.devicemanager.model.Devices;
import com.device.manager.devicemanager.services.DevicesServices;

@RestController
public class DevicesController {
	
	@Autowired
	DevicesServices devicesServices;
	
	@RequestMapping("/devices")
	public List<Devices> getDevices() {
		return devicesServices.getAllDevices();
	}
	
	@RequestMapping("/devices/{id}")
	public Devices getDevices(@PathVariable int id) {
		return devicesServices.getDevicesById(id);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/devices")
	public void getDevices(@RequestBody Devices devices) {
		devicesServices.saveDevices(devices);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/devices/{id}")
	public void deleteDevices(@PathVariable int id) {
		devicesServices.deleteDevicesById(id);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/devices")
	public void deleteDevices(@RequestBody Devices devices) {
		devicesServices.deleteDevices(devices);
	}
	
}

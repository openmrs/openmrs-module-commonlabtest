package com.device.manager.devicemanager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.device.manager.devicemanager.model.Devices;
import com.device.manager.devicemanager.respository.DevicesRepository;


@Service
public class DevicesServices {

	
	@Autowired
	DevicesRepository devicesRepository;
	
	public List<Devices> getAllDevices(){
	 return devicesRepository.findAll();
  	}
	
	public Devices getDevicesById(int id){
		return devicesRepository.findById(id).get();
	}
	
	public void saveDevices(Devices devices){
		devicesRepository.save(devices); //save method do both save and update 
	}
	
	
	public void deleteDevicesById(int id){
		devicesRepository.deleteById(id);
	}
	
	public void deleteDevices(Devices devices){
		devicesRepository.delete(devices);
	}

	
}

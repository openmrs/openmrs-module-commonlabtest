package com.device.manager.devicemanager.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.device.manager.devicemanager.model.Devices;


@Repository
public interface  DevicesRepository  extends JpaRepository<Devices, Integer>{

}

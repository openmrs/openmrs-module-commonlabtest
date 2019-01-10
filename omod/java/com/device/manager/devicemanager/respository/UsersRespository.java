package com.device.manager.devicemanager.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.device.manager.devicemanager.model.Users;

@Repository
public interface UsersRespository extends JpaRepository<Users, Integer> {
	
	Users findByUsername(String username, String password);
}

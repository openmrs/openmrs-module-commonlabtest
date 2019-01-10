package com.device.manager.devicemanager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.device.manager.devicemanager.model.Users;
import com.device.manager.devicemanager.respository.UsersRespository;

@Service
public class UsersServices {
	
	@Autowired
	UsersRespository loginRespository;
	
	public Boolean getUserLogin(String username, String password) {
		if (loginRespository.findByUsername(username, password) != null) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public List<Users> getAllUsers() {
		return loginRespository.findAll();
	}
	
	public Users getUsersById(int id) {
		return loginRespository.findById(id).get();
	}
	
	public void saveUsers(Users users) {
		loginRespository.save(users);
	}
	
	public void deleteUsersById(int id) {
		loginRespository.deleteById(id);
	}
	
	public void deleteUsers(Users users) {
		loginRespository.delete(users);
	}
	
	public void deleteAllUsers() {
		loginRespository.deleteAll();
	}
	
}

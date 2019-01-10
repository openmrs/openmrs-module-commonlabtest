package com.device.manager.devicemanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.device.manager.devicemanager.services.UsersServices;

@RestController
public class UsersController {
	
	@Autowired
	UsersServices usersServices;
	
	/*
	 * @RequestMapping("/users") public Boolean getLogin(@RequestBody String
	 * username,@RequestBody String password){
	 * 
	 * return usersServices.getUserLogin(username, password); }
	 */
	
}

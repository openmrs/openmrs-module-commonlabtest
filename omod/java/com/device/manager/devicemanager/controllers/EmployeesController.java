
package com.device.manager.devicemanager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.device.manager.devicemanager.model.Employees;
import com.device.manager.devicemanager.services.EmployeesServices;


@RestController
public class EmployeesController {
	
	@Autowired
	EmployeesServices employeesServices;
	
	@RequestMapping("/employees")
	public List<Employees> getEmployees(){
		return employeesServices.getAllEmployees();
	}
	
	@RequestMapping("/employees/{id}")
	public Employees getEmployees(@PathVariable int id){
		return employeesServices.getEmployeesById(id);
	}
	
	@RequestMapping(method =RequestMethod.POST,value = "/employees")
	public void getEmployees(@RequestBody Employees employees){
		 employeesServices.saveEmployees(employees);
	}
	
	@RequestMapping(method =RequestMethod.DELETE,value ="/employees/{id}")
	public void deleteEmployees(@PathVariable int id){
		employeesServices.deleteEmployeesById(id);
	}
	
	@RequestMapping(method =RequestMethod.DELETE,value ="/employees")
	public void deleteEmployees(@RequestBody Employees employees){
		employeesServices.deleteEmployees(employees);
	}
	

}

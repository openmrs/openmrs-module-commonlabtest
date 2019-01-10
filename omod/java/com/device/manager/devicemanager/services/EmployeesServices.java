package com.device.manager.devicemanager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.device.manager.devicemanager.model.Employees;
import com.device.manager.devicemanager.respository.EmployeesRepository;

@Service
public class EmployeesServices {
	
	@Autowired
	EmployeesRepository employeesRepository;
	
	public List<Employees> getAllEmployees() {
		return employeesRepository.findAll();
	}
	
	public Employees getEmployeesById(int id) {
		return employeesRepository.findById(id).get();
	}
	
	public void saveEmployees(Employees employees) {
		employeesRepository.save(employees);
	}
	
	public void deleteEmployeesById(int id) {
		employeesRepository.deleteById(id);
	}
	
	public void deleteEmployees(Employees employees) {
		employeesRepository.delete(employees);
	}
	
	public void deleteAllEmployees() {
		employeesRepository.deleteAll();
	}
	
}

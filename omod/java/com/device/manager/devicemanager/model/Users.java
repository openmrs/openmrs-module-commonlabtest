package com.device.manager.devicemanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="login_info")
public class Users {
	
	@Id
	@Column(name="login_id")
	private int id;
	
	@Column(name = "username")
	private String userName;
	
	private String password;
	
	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employees employees;
	
	private String salt;
	
	
	public Users() {
	}


	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public Employees getEmployees() {
		return employees;
	}



	public void setEmployees(Employees employees) {
		this.employees = employees;
	}



	public String getSalt() {
		return salt;
	}



	public void setSalt(String salt) {
		this.salt = salt;
	}

}

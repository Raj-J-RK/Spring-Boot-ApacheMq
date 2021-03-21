package com.rk.apache.domain;

import java.io.Serializable;

public class Employee implements Serializable {
	
	private String name;
	private int age;
	private int empId;
	private String location;
	
	public Employee(String name, int age, int empId, String location) {
		super();
		this.name = name;
		this.age = age;
		this.empId = empId;
		this.location = location;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Override
	public String toString() {
		return "Employee [name=" + name + ", age=" + age + ", empId=" + empId + ", location=" + location + "]";
	}

}

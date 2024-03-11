package com.feuji.employeeservice.service;

import java.util.List;

import com.feuji.employeeservice.bean.EmployeeBean;
import com.feuji.employeeservice.entity.EmployeeEntity;

public interface EmployeeService {
	
   public EmployeeEntity saveEmployee(EmployeeBean employeeBean);
   
   public EmployeeEntity getById(Integer Id);

	public List<EmployeeEntity> getAllEmployees() ;

	public void updateEmployeeDetails(EmployeeEntity updateEmpolyee, Integer id) throws Throwable; 
	
	public EmployeeBean findByEmail(String email);
}

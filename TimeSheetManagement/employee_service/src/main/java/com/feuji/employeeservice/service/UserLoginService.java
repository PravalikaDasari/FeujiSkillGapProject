package com.feuji.employeeservice.service;

import org.springframework.http.ResponseEntity;

import com.feuji.employeeservice.bean.UserLoginBean;
import com.feuji.employeeservice.entity.UserLoginEntity;

public interface UserLoginService {
	
	 public UserLoginEntity loginUser(String userEmail, String userPassword);
	 
//	 public boolean forgotPassword(String userEmail);

}

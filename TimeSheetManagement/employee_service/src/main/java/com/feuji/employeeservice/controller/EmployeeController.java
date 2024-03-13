package com.feuji.employeeservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.employeeservice.bean.EmployeeBean;
import com.feuji.employeeservice.entity.EmployeeEntity;
import com.feuji.employeeservice.exception.RecordsNotFoundException;
import com.feuji.employeeservice.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/employee")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/save")
	public ResponseEntity<EmployeeEntity> saveEmployee(@RequestBody EmployeeBean employeeBean)
			throws NullPointerException {
		log.info("Save Start:Saving Employee Details");
		try {
			EmployeeEntity saveEmployeeEntity = employeeService.saveEmployee(employeeBean);
			log.info("Save End:Saved Employee Details");
			return new ResponseEntity<>(saveEmployeeEntity, HttpStatus.OK);
		} catch (NullPointerException e) {
			log.info("Error occurred while saving employee details" + e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<EmployeeEntity>> getAllEmployees() throws RecordsNotFoundException {
		log.info("GetAll Start: Fetching All Employee Details");
		try {
			List<EmployeeEntity> employeeEntities = employeeService.getAllEmployees();
				log.info("GetAll End: Fetching All Employee Details");
				return new ResponseEntity<>(employeeEntities, HttpStatus.OK);
		}
		catch(RecordsNotFoundException e) {
			log.error("Error occurred while fetching all employee details", e.getMessage());
			throw new RecordsNotFoundException("Failed to get all employee records");
		}
	}

	@GetMapping("/getByEmail/{employeeEmail}")
	public ResponseEntity<EmployeeBean> getByEmail(@PathVariable String employeeEmail) {
		log.info("GetByEmail Start:Fetching employee Details" + employeeEmail);
		EmployeeBean employeebean =null;
		try {
			employeebean = employeeService.findByEmail(employeeEmail);
			log.info("GetByEmail End:Fetching employee Details");
			return new ResponseEntity<EmployeeBean>(employeebean, HttpStatus.OK);
		} catch (RecordsNotFoundException e) {
			log.info("No records found for email: " + e.getMessage());
			return new ResponseEntity<EmployeeBean>(employeebean, HttpStatus.NOT_FOUND);
		}
	}
}

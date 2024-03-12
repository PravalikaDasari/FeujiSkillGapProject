package com.feuji.employeeskillservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.employeeskillservice.bean.EmployeeSkillBean;
import com.feuji.employeeskillservice.bean.EmployeeSkillGet;
import com.feuji.employeeskillservice.bean.EmployeeUiBean;
import com.feuji.employeeskillservice.exception.NoRecordFoundException;
import com.feuji.employeeskillservice.service.EmployeeSkillService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/employeeskill")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeSkillController {
	@Autowired
	private EmployeeSkillService employeeSkillservice;
	
	/**
	Inserts a list of employee UI beans into the database as employee skill beans.
	@param employeeUiBeans The list of employee UI beans to be saved.
	@return ResponseEntity<List<EmployeeSkillBean>> A ResponseEntity containing the saved employee skill beans and HTTP status OK if successful.
	 * @throws Exception 
	*/
	@PostMapping("/saverecord")
	public ResponseEntity<List<EmployeeSkillBean>> saveEmployeeSkillBean(@RequestBody List<EmployeeUiBean> employeeUiBeans)
	{
		log.info("insertEmployeeSkillBean() Start:Saving Employee Details");
		List<EmployeeSkillBean> employeeSkillBeansList=employeeSkillservice.convertUiBeanToSkillBean(employeeUiBeans);
		List<EmployeeSkillBean> savedEmployeeSkillBeansList=new ArrayList<>();
		try {
			savedEmployeeSkillBeansList = employeeSkillservice.saveAll(employeeSkillBeansList);
			 return new ResponseEntity<>(savedEmployeeSkillBeansList,HttpStatus.OK);
		}catch(Exception e)
		{
			throw new IllegalArgumentException("failed to save records");
		}
	}		
	/**
	Retrieves the list of employee skill beans associated with the given employee ID.
	@param employeeId The ID of the employee whose skills are to be retrieved.
	@return ResponseEntity<List<EmployeeSkillBean>> A ResponseEntity containing the employee skill beans associated with the given employee ID and HTTP status FOUND if successful.
	@throws NoRecordFoundException If there are no records found for the specified employee ID, this exception is thrown.
	*/
	@GetMapping("/getEmployeeSkillById/{employeeId}")
	public ResponseEntity<List<EmployeeSkillBean>> getEmployeeSkillById(@PathVariable Long employeeId)
			throws NoRecordFoundException {
		log.info("getEmployeeSkill() Start:Fetching EmployeeSkills");
		List<EmployeeSkillBean> list = null ;
		try {
			list= employeeSkillservice.getEmployeeSkillById(employeeId);
			if(list.size()!=0) {
				log.info("getEmployeeSkillById End:Fetched EmployeeSkills");
				return new ResponseEntity<List<EmployeeSkillBean>>(list, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<List<EmployeeSkillBean>>(list, HttpStatus.NOT_FOUND);
			}
		} catch (NoRecordFoundException e) {
//			throw new NoRecordFoundException("failed to fetch records");
			return new ResponseEntity<List<EmployeeSkillBean>>(list, HttpStatus.NOT_FOUND);
		}
	}

	/**
	Retrieves the list of employee skill beans associated with the given skill ID.
	@param skillId The ID of the skill for which employee skills are to be retrieved.
	@return ResponseEntity<List<EmployeeSkillBean>> A ResponseEntity containing the employee skill beans associated with the given skill ID and HTTP status CREATED if successful.
	@throws NoRecordFoundException If there are no records found for the specified skill ID, this exception is thrown.
	*/
	@GetMapping("/getBySkillId/{skillId}")
	public ResponseEntity<List<EmployeeSkillBean>> getBySkillId(@PathVariable int skillId) throws NoRecordFoundException
	{
		log.info("getBySkillId Start:Fetching Id Details");
		try {
			List<EmployeeSkillBean> bean = employeeSkillservice.findBySkillId(skillId);
			log.info("getBySkillId End:Fetched Id Details");
			return new ResponseEntity<List<EmployeeSkillBean>>(bean, HttpStatus.CREATED);
		} catch (Exception e) {
			throw new NoRecordFoundException("failed to fetch records");
		}
	}

	/**
	Retrieves all employee skill records associated with the given email.
	@param email The email of the employee for which skill records are to be retrieved.
	@return ResponseEntity<List<EmployeeSkillGet>> A ResponseEntity containing all employee skill records associated with the given email and HTTP status OK if successful.
	@throws NoRecordFoundException If no records are found for the specified email, this exception is thrown.
	*/
	@GetMapping("/getAll/{email}")
	public ResponseEntity<List<EmployeeSkillGet>> getAllEmployeeSkills(@PathVariable String email) throws NoRecordFoundException 
	{
		log.info("getAll Start:Fetching All EmployeeSkills");
		try {
			List<EmployeeSkillGet> allEmployeeSkills = employeeSkillservice.getAllEmployeeSkills(email);
			log.info("getAll End:Fetched All EmployeeSkills");
			return new ResponseEntity<List<EmployeeSkillGet>>(allEmployeeSkills,HttpStatus.OK);

		} catch (Exception e) {
			throw new NoRecordFoundException(" no records found with this email: "+email);
		}	
	}

	/**
	Updates the delete status of an employee skill record identified by the given employee skill ID.
	@param employeeSkillId The ID of the employee skill record to be deleted.
	@return ResponseEntity<String> A ResponseEntity containing a message indicating the result of the deletion operation and HTTP status OK if successful.
	@throws Exception If there is an error during the deletion process, this exception is thrown.
	*/
	@PutMapping("/delete/{employeeSkillId}")
	public ResponseEntity<String> deleteEmployeeSkill(@PathVariable Long employeeSkillId) throws Exception
	{
		log.info("delete Start:Deleting EmployeeSkills");
		String result=null;
		try {
			result = employeeSkillservice.updateDeleteStatus(employeeSkillId);
			log.info("delete End:Deleted EmployeeSkills");
			return new ResponseEntity<String>(result,HttpStatus.OK);
		} catch (Exception e) {
			throw new Exception("failed to delete records");
		}
	}
	
	/**
	Updates the details of an employee skill record identified by the given employee skill ID.
	@param set The updated details of the employee skill record.
	@param employeeSkillId The ID of the employee skill record to be updated.
	@return ResponseEntity<EmployeeSkillBean> A ResponseEntity containing the updated employee skill bean and HTTP status OK if successful.
	@throws Exception If there is an error during the update process, this exception is thrown.
	*/	
	@PutMapping("/update/{employeeSkillId}")
	public ResponseEntity<EmployeeSkillBean> updateEmployeeSkill(@RequestBody EmployeeSkillGet  set , @PathVariable Long employeeSkillId) throws Exception
	{
		log.info("update Start:Updating EmployeeSkills");
		try {
			EmployeeSkillBean bean =employeeSkillservice.updateEmployeeSkill(set,employeeSkillId);
			return new ResponseEntity<EmployeeSkillBean>(bean,HttpStatus.OK);
		}catch(Exception e)
		{
			throw new Exception("failed to update record with id: "+employeeSkillId);
		}
	}
	
}

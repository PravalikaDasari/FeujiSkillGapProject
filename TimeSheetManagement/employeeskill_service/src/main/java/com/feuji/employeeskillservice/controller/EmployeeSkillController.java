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

import com.feuji.employeeskillservice.bean.EmployeeBean;
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
	private EmployeeSkillService service;
	

	@GetMapping("/check/{email}")
	public ResponseEntity<EmployeeBean> checkUp(@PathVariable String email) {
		log.info("checkUp method started");
		EmployeeBean employee = service.getEmployeeByEmail(email);
		return new ResponseEntity<>(employee, HttpStatus.CREATED);
	}

	@PostMapping("/saverecord")
	public ResponseEntity<List<EmployeeSkillBean>> insert(@RequestBody List<EmployeeUiBean> beans) {
		log.info("insert () start");
		List<EmployeeSkillBean> listBeans=new ArrayList<>();
		for(EmployeeUiBean bean:beans)
		{
			log.info(bean.toString());
			EmployeeBean employee = service.getEmployeeByEmail(bean.getEmployeeMail());
			log.info(employee.toString());
			EmployeeSkillBean empSkillBean = new EmployeeSkillBean();
			empSkillBean.setEmployeeId(employee.getEmployeeId());
			empSkillBean.setEmployeeCode(employee.getEmployeeCode());
			empSkillBean.setSkillId(Integer.parseInt(bean.getSkillId()));
			empSkillBean.setComments(bean.getComments());
			empSkillBean.setDescription(bean.getDescription());
			empSkillBean
					.setCreatedBy(employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName());
			empSkillBean
					.setModifiedBy(employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName());
			empSkillBean.setCompetencyLevelId(Integer.parseInt(bean.getCompetencyLevelId()));
			empSkillBean.setSkillTypeId(Integer.parseInt(bean.getSkillTypeId()));
			empSkillBean.setYearsOfExp(Integer.parseInt(bean.getYearsOfExp()));
			empSkillBean.setIsDeleted(Byte.parseByte(bean.getIsDeleted()));
			if(bean.getCertification().equals("Yes"))
				empSkillBean.setCertification((byte)1);
			else
				empSkillBean.setCertification((byte)0);
			listBeans.add(empSkillBean);
		}
		List<EmployeeSkillBean> saveAll=new ArrayList<>();
		try {
			 saveAll = service.saveAll(listBeans);
		}catch(Exception e)
		{
			log.error("Error occurred while saving employee: {}", e.getMessage());
		}
		return new ResponseEntity<>(saveAll,HttpStatus.OK);

	}		
	@GetMapping("/getEmployeeSkillById/{employeeId}")
	public ResponseEntity<List<EmployeeSkillBean>> getEmployeeSkill(@PathVariable Long employeeId)
			throws NoRecordFoundException {
		log.info("getEmployeeSkill() in controller start");
		log.info("EmployeeId-" + employeeId);
		try {
			List<EmployeeSkillBean> list = service.getEmployeeSkillById(employeeId);
			log.info("insert() in controller end");
			return new ResponseEntity<List<EmployeeSkillBean>>(list, HttpStatus.FOUND);
		} catch (NoRecordFoundException e) {
			throw new NoRecordFoundException(e.getMessage());
		}
	}

	@PostMapping("/updateEmployeeSkillByUUID")
	public ResponseEntity<EmployeeSkillBean> updateEmployeeSkill(@RequestBody EmployeeSkillBean employeeSkillBean) {
		log.info("updateEmployeeSkill() in controller start");
		log.info("EmployeeSkillbean object" + employeeSkillBean);
		try {
			EmployeeSkillBean updateEmployeeSkill = service.updateEmployeeSkill(employeeSkillBean);
			log.info("insert() in controller end");
			return new ResponseEntity<EmployeeSkillBean>(updateEmployeeSkill, HttpStatus.OK);
		} catch (Exception e) {
			throw new NullPointerException("employee object is null");
		}
	}
	

//	@PostMapping("/saveall")
//	public String saveAllRecords(@RequestBody List<EmployeeSkillBean> beanList) {
//		log.info("saveAllRecords() in controller started");
//		service.saveAll(beanList);
//		return "saved successfully";
//	}

	@GetMapping("/getBySkillId/{skillId}")
	public ResponseEntity<List<EmployeeSkillBean>> getBySkillId(@PathVariable int skillId) {
		log.info("get() in service");
		List<EmployeeSkillBean> bean = service.findBySkillId(skillId);
		return new ResponseEntity<List<EmployeeSkillBean>>(bean, HttpStatus.CREATED);
	}

	@GetMapping("/getAll/{email}")
	public ResponseEntity<List<EmployeeSkillGet>> getAllEmployeeSkillRecords(@PathVariable String email) throws NoRecordFoundException 
	{
		log.info("email is+++++++++"+email);
		try {
			List<EmployeeSkillGet> allEmployeeSkills = service.getAllEmployeeSkills(email);
			log.info("getAll() in controller ended");

			return new ResponseEntity<List<EmployeeSkillGet>>(allEmployeeSkills,HttpStatus.OK);

		} catch (Exception e) {
			throw new NoRecordFoundException(" no records found");
		}	
	}

	@PutMapping("/delete/{employeeSkillId}")
	public ResponseEntity<String> deleteEmpSkill(@PathVariable Long employeeSkillId)
	{
		log.info("deleteEmpSkill() started");
		String result=null;
		try {
			result = service.updateDeleteStatus(employeeSkillId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("deleteEmpSkill() ended");
		return new ResponseEntity<String>(result,HttpStatus.OK);
	}
	
	@PutMapping("/update/{empId}")
	public ResponseEntity<EmployeeSkillBean> updateEmpSkill(@RequestBody EmployeeSkillGet  set , @PathVariable Long empId)
	{
		EmployeeSkillBean bean =service.update(set,empId);
		return new ResponseEntity<EmployeeSkillBean>(bean,HttpStatus.OK);
	}
	
	
}

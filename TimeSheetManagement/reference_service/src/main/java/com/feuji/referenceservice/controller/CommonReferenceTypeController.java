package com.feuji.referenceservice.controller;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.referenceservice.bean.CommonReferenceTypeBean;
import com.feuji.referenceservice.entity.CommonReferenceTypeEntity;
import com.feuji.referenceservice.repository.CommonReferenceTypeRepo;
import com.feuji.referenceservice.service.CommonReferenceDetails;
import com.feuji.referenceservice.service.CommonReferenceType;
import com.feuji.referenceservice.serviceImpl.CommonReferenceTypeImpl;



@RestController
@RequestMapping("/referencetype")
public class CommonReferenceTypeController {

	private static Logger log = LoggerFactory.getLogger(CommonReferenceTypeImpl.class);
	
	@Autowired
	CommonReferenceTypeRepo commonReferenceTypeRepo;
	@Autowired
	CommonReferenceType commonReferenceType;
	
	@PostMapping("/save")
	public ResponseEntity<CommonReferenceTypeEntity> saveTimesheetWeek(@RequestBody CommonReferenceTypeBean commonReferenceTypeBean) {
		try {
			log.info("timesheet week controller", commonReferenceTypeBean);
			CommonReferenceTypeEntity save = commonReferenceType.save(commonReferenceTypeBean);
			return new ResponseEntity<>(save, HttpStatus.CREATED);

		} catch (Exception e) {

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
	@GetMapping("/getref/{name}")
	public ResponseEntity<CommonReferenceTypeEntity> getReferenceTypeByName(@PathVariable String name)
	{
		
	try{
		log.info("getting timesheet",name);
		CommonReferenceTypeEntity commonReferenceTypeEntity=commonReferenceType.getByTypeName(name);
		return new ResponseEntity<>(commonReferenceTypeEntity, HttpStatus.CREATED);

		
	}
	catch (Exception e) {
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
}

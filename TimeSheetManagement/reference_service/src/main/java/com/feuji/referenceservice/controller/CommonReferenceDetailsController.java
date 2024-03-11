package com.feuji.referenceservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.referenceservice.bean.TechnicalSkillsBean;
import com.feuji.referenceservice.repository.CommonReferenceDetailsRepo;
import com.feuji.referenceservice.service.CommonReferenceDetails;
import com.feuji.referenceservice.serviceImpl.CommonReferenceTypeImpl;

@RestController
@RequestMapping("/referencedetails")
@CrossOrigin(origins = "http://localhost:4200")

public class CommonReferenceDetailsController {
	private static Logger log = LoggerFactory.getLogger(CommonReferenceTypeImpl.class);

	@Autowired
	CommonReferenceDetailsRepo commonReferenceDetailsRepo;
	@Autowired
	CommonReferenceDetails commonReferenceDetails;

	@Autowired
	CommonReferenceDetailsRepo commonReferenceDetailsRepo2;

	@GetMapping("/getref/{typeName}")
//	public ResponseEntity<List<TechnicalSkillsBean>> getReferenceTypeByName(@PathVariable String typeName)
	public ResponseEntity<List<TechnicalSkillsBean>> getReferenceTypeByName(@PathVariable String typeName)
	{
	try{
		log.info(typeName);
		List<TechnicalSkillsBean> getbyreferenceType = commonReferenceDetails.getDetailsByTypeId(typeName);
		log.info(getbyreferenceType+"output");
		return new ResponseEntity<>(getbyreferenceType, HttpStatus.OK) ;	
	}
	catch (Exception e) {
    return null;
	}
	}
	
	
	@GetMapping("/getCategories/{category}")
	public ResponseEntity<List<String>> getCategories(@PathVariable String category)
	{
		List<String> categories = commonReferenceDetails.getCategories(category);
		log.info(categories+"output");
		return new ResponseEntity<>(categories, HttpStatus.OK) ;	
	
	}

//	public List<String> getReferenceTypeByName(@PathVariable String typeName) {
//
//		try {
//			log.info("type Name", typeName);
//
//			List<String> getbyreferenceType = commonReferenceDetails.getDetailsByTypeId(typeName);
//			return getbyreferenceType;
//
//		} catch (Exception e) {
//			return null;
//		}
//
//	}
	
	@GetMapping("/getByName/{name}")
	public ResponseEntity<String> getByName(@PathVariable String name)
	{
		log.info("controller-getByName() started");
		int idByName = commonReferenceDetails.getIdByName(name);
		String result = idByName+"";
		return new ResponseEntity<String>(result,HttpStatus.OK);

	}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<String> getById(@PathVariable int id)
	{
		log.info("controller-getById() started");
		String name = commonReferenceDetails.getByid(id);
		log.info(name+"----===!!!!");
		return new ResponseEntity<String>(name,HttpStatus.OK);
	}
	
}

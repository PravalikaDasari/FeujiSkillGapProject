package com.skillset.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.skillset.dto.GapDto;
import com.skillset.servic.SkillSetService;

@RestController
public class SkillSetController {
	
	@Autowired
	private SkillSetService skillSetservice;
	
	@GetMapping("/fetch/{email}/{skillCategoryId}")
	public ResponseEntity<List<GapDto>> fetchSkillDetails(@PathVariable String email, @PathVariable Integer skillCategoryId) {
	   
		List<GapDto> list = skillSetservice.fetchSkillDto(email,skillCategoryId);
	    if (list.isEmpty()) {
	        return new ResponseEntity<>(list,HttpStatus.NOT_FOUND); 
	    }
	    return new ResponseEntity<>(list, HttpStatus.OK);
	}



}

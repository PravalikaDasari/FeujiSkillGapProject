package com.feuji.skillgapservice.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.skillgapservice.bean.SkillBean;
import com.feuji.skillgapservice.dto.SkillNamesDto;
import com.feuji.skillgapservice.exception.SkillNotFoundException;
import com.feuji.skillgapservice.service.SkillService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/skill")
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")

public class SkillController {
	@Autowired
	private SkillService skillService;

	// to insert values
	@PostMapping("/insert")
	public ResponseEntity<SkillBean> saveSkillData(@RequestBody SkillBean bean) {
		log.info("save controller start");
		System.out.println(bean);
		try {
			SkillBean skill = skillService.saveSkill(bean);

			log.info("save controller end");
			return new ResponseEntity<SkillBean>(skill, HttpStatus.CREATED);
		} catch (NullPointerException exception) {
			throw new NullPointerException(exception.getMessage());
		}
	}

	@PutMapping("/updateSkill")
	private ResponseEntity<SkillBean> updateDetails(@RequestParam String uuid, @RequestBody SkillBean skillBean) {
		skillBean.setUuid(uuid);
		SkillBean updatedSkill = skillService.updateDetails(skillBean);
		return ResponseEntity.ok(updatedSkill);
	}

//-----------------------fetch by uuid---------------------------
	@GetMapping(path = "/getByUuid")
	public ResponseEntity<SkillBean> getAdmin(@RequestParam String uuid) throws SkillNotFoundException {

		SkillBean bean = null;
		try {
			bean = skillService.getByUuid(uuid);
		} catch (SkillNotFoundException e) {
			throw new SkillNotFoundException(e.getMessage());
		}

		return new ResponseEntity<>(bean, HttpStatus.FOUND);
	}

	@GetMapping(path = "/getAll/{categoryId}")
	public ResponseEntity<List<SkillBean>> getAllSkillsByTechnicalCategory(@PathVariable int categoryId) {
		log.info("controller-getAllSkillsByTechnicalCategory");
		List<SkillBean> list = skillService.getSkillsByTechCategoryId(categoryId);
		return new ResponseEntity<>(list, HttpStatus.OK);

	}

	@GetMapping(path="/getBySkillId/{skillId}")
	public ResponseEntity<SkillBean> getBySkillId(@PathVariable int skillId)
	{
		SkillBean bean = null;
		try {
			bean=skillService.getBySkillId(skillId);
		}catch(SkillNotFoundException e)
		{
			throw new SkillNotFoundException(e.getMessage());
		}
		return new ResponseEntity<>(bean, HttpStatus.FOUND);
	}
	
	@GetMapping(path="/getSkillNames/{skillIds}")
	public ResponseEntity<List<SkillNamesDto>> getSkillNames(@PathVariable int[] skillIds)
	{
		List<SkillNamesDto> skills =new ArrayList<>();
		try {
			skills =skillService.getSkillNames(skillIds);
		}catch(Exception e)
		{
			return new ResponseEntity<List<SkillNamesDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return  new ResponseEntity<List<SkillNamesDto>>(skills,HttpStatus.OK);
	}
	


}

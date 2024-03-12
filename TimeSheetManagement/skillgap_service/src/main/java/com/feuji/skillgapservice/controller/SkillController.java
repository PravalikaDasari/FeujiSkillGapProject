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
import com.feuji.skillgapservice.exception.RecordNotFoundException;
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


	
	/**
	 * Saves a new SkillBean record.
	 *
	 * @param bean The SkillBean object to save.
	 * @return A ResponseEntity containing the saved SkillBean object and HTTP status CREATED.
	 * @throws IllegalArgumentException If the save operation fails.
	 */
	@PostMapping("/insert")
	public ResponseEntity<SkillBean> saveSkill(@RequestBody SkillBean bean) {
		log.info("Save Start:Save saveSkillData Details");
		try {
			SkillBean skill = skillService.saveSkill(bean);
			log.info("Save End:Saved saveSkillData Details");
			return new ResponseEntity<SkillBean>(skill, HttpStatus.CREATED);
		} catch (NullPointerException exception) {
			throw new IllegalArgumentException("failed to save records");
		}
	}

	
	
	/**
	 * Updates the details of a SkillBean record.
	 *
	 * @param uuid The UUID of the SkillBean record to update.
	 * @param skillBean The updated SkillBean object.
	 * @return A ResponseEntity containing the updated SkillBean object and HTTP status OK.
	 * @throws SkillNotFoundException If the SkillBean record with the provided UUID is not found.
	 */
	@PutMapping("/updateSkill")
	public ResponseEntity<SkillBean> updateSkillDetails(@RequestParam String uuid, @RequestBody SkillBean skillBean) {
		log.info("Update Start:Update Skills Details");
		try {
			skillBean.setUuid(uuid);
			SkillBean updatedSkill = skillService.updateSkillDetails(skillBean);
			log.info("Update End:Updated Skills Details");
			return ResponseEntity.ok(updatedSkill);
		} catch (Exception e) {
			throw new SkillNotFoundException("failed to update records");
		}
	}

	
	/**
	 * Retrieves a SkillBean record from the database based on its UUID.
	 *
	 * @param uuid The UUID of the SkillBean record to retrieve.
	 * @return A ResponseEntity containing the retrieved SkillBean object and HTTP status FOUND.
	 * @throws SkillNotFoundException If the SkillBean record with the provided UUID is not found.
	 */
	@GetMapping(path = "/getByUuid")
	public ResponseEntity<SkillBean> getAdmin(@RequestParam String uuid) {
		log.info("getByUuid Start:Fetching Uuid");
		SkillBean bean = null;
		try {
			bean = skillService.getSkillByUuid(uuid);
			log.info("getByUuid End:Fetched Uuid");
			return new ResponseEntity<>(bean, HttpStatus.FOUND);
		} catch (SkillNotFoundException e) {
			throw new SkillNotFoundException("faild to fetch Uuid records");
		}
	}
	

	/**
	 * Retrieves a list of SkillBean records from the database based on a technical category ID.
	 *
	 * @param categoryId The ID of the technical category to filter the SkillBean records.
	 * @return A ResponseEntity containing a list of SkillBean objects and HTTP status OK.
	 * @throws SkillNotFoundException If no SkillBean records are found for the provided technical category ID.
	 */
	@GetMapping(path = "/getAll/{categoryId}")
	public ResponseEntity<List<SkillBean>> getSkillsByTechCategoryId(@PathVariable int categoryId) {
		log.info("GetAll Start:Fetching All Skills");
		try {
			List<SkillBean> list = skillService.getSkillsByTechCategoryId(categoryId);
			log.info("GetAll End:Fetched All Skills");
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			throw new SkillNotFoundException("failed to fetch Skills records");
		}
	}

	
	/**
	 * Retrieves a SkillBean record from the database based on its ID.
	 *
	 * @param skillId The ID of the SkillBean record to retrieve.
	 * @return A ResponseEntity containing the retrieved SkillBean object and HTTP status FOUND.
	 * @throws SkillNotFoundException If the SkillBean record with the provided ID is not found.
	 */
	@GetMapping(path = "/getBySkillId/{skillId}")
	public ResponseEntity<SkillBean> getSkillBySkillId(@PathVariable int skillId) {
		log.info("GetBySkillId Start:Fetching Id Details");
		SkillBean bean = null;
		try {
			bean = skillService.getSkillBySkillId(skillId);
			log.info("GetBySkillId End:Fetched Id Details");
			return new ResponseEntity<>(bean, HttpStatus.FOUND);
		} catch (SkillNotFoundException e) {
			throw new SkillNotFoundException("faild to fetch Id records");
		}
	}
	
	
	
	/**
	 * Retrieves a list of SkillNamesDto objects from the database based on an array of skill IDs.
	 *
	 * @param skillIds An array of skill IDs to retrieve SkillNamesDto objects.
	 * @return A ResponseEntity containing a list of SkillNamesDto objects and HTTP status OK.
	 * @throws RecordNotFoundException If no records are found for the provided skill IDs.
	 */
	@GetMapping(path = "/getSkillNames/{skillIds}")
	public ResponseEntity<List<SkillNamesDto>> getSkillNamesBySkillId(@PathVariable int[] skillIds)
			throws RecordNotFoundException {
		log.info("GetSkillNames Start:Fetching SkillNames");
		List<SkillNamesDto> skills = new ArrayList<>();
		try {
			skills = skillService.getSkillNamesBySkillId(skillIds);
			log.info("GetSkillNames End:Fetched SkillNames");
			return new ResponseEntity<List<SkillNamesDto>>(skills, HttpStatus.OK);
		} catch (Exception e) {
			throw new RecordNotFoundException("failed to fetch record");
		}
	}


}

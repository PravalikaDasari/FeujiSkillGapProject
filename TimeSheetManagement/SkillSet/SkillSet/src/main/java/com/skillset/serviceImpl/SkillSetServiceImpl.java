package com.skillset.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.skillset.exception.RecordNotFoundException;
import com.skillset.dto.GapDto;
import com.skillset.repo.SkillSetRepository;
import com.skillset.servic.SkillSetService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class SkillSetServiceImpl implements SkillSetService {

	@Autowired
	private SkillSetRepository repository;
	
	/**
	 * Retrieves a list of GapDto objects containing skill gap details for a specific employee and skill category.
	 * @param email The email address of the employee.
	 * @param skillCategoryId The ID of the skill category.
	 * @return A list of GapDto objects representing the skill gap details.
	 * @throws RecordNotFoundException 
	 */

	public List<GapDto> fetchSkillDto(String email, Integer skillCategoryId) throws RecordNotFoundException {
		List<GapDto> queryResult = repository.findEmployeeDetailsByEmail(email, skillCategoryId);
		if(queryResult!= null)
		{
			return queryResult;
		}else {
			throw new RecordNotFoundException("no records found");
		}
	}

}

package com.skillset.servic;

import java.util.List;

import com.skillset.dto.GapDto;

public interface SkillSetService 
{
//	 Map<String, EmployeeSkillDetailsDto> fetchSkills();

	List<GapDto> fetchSkillDto(String email,Integer skillCategoryId);
}
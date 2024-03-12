package com.skillset.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillset.dto.GapDto;
import com.skillset.repo.SkillSetRepository;
import com.skillset.servic.SkillSetService;

@Service
public class SkillSetServiceImpl implements SkillSetService {

	@Autowired
	private SkillSetRepository repository;

	public List<GapDto> fetchSkillDto(String email, Integer skillCategoryId) {
		List<Object[]> queryResult = repository.findEmployeeDetailsByEmail(email, skillCategoryId);
		List<GapDto> gapDtos = new ArrayList<>();

		for (Object[] result : queryResult) {
			GapDto dto = new GapDto();
			dto.setEmployeeName((String) result[0]); // Assuming employee_name is at index 0
			dto.setEmployeeId((Integer) result[1]); // Assuming employee_id is at index 1
			dto.setEmail((String) result[2]); // Assuming email is at index 2
			dto.setDesignation((String) result[3]); // Assuming designation is at index 3
			dto.setEmployeeEmail((String) result[4]); // Assuming employee_email is at index 4
			dto.setSkillName((String) result[5]); // Assuming skill_name is at index 5
			dto.setDescription((String) result[6]);
			dto.setExCompetencyLevelId((Integer) result[7]); // Assuming skill_competency_level_id is at index 6
			dto.setAcCompetencyLevelId((Integer) result[8]); // Assuming employee_competency_level_id is at index 7
			dto.setExReferenceDetailsValues((String) result[9]); // Assuming skillReferenceValue is at index 8
			dto.setAcReferenceDetailsValues((String) result[10]); // Assuming employeeReferenceValue is at index 9
			gapDtos.add(dto);
		}

		return gapDtos;
	}

	private Integer parseInteger(Object value) {
		if (value instanceof String) {
			try {
				return Integer.parseInt((String) value);
			} catch (NumberFormatException e) {
				return null; // or handle
			}
		} else if (value instanceof Integer) {
			return (Integer) value;
		}
		return null;
	}
}

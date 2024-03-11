package com.feuji.skillgapservice.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.feuji.skillgapservice.dto.EmployeeEntityDto;
import com.feuji.skillgapservice.entity.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer>{

	Optional<EmployeeEntity> findByEmail(String email);
	@Query("select DISTINCT new com.feuji.skillgapservice.dto.EmployeeEntityDto(e.employeeId,e.employeeCode,e.firstName, e.middleName, e.lastName, " +
	        "  e.designation, e.email ) "	    
	        + " FROM EmployeeEntity e "
	        
	        +"INNER JOIN EmployeeSkillEntity es ON e.employeeId = es.employeeId " +
	        
	        "INNER JOIN SkillEntity s ON s.skillId = es.skillId " +
	        "INNER JOIN SkillCompetencyEntity sc ON s.skillId = sc.skillId " +
			"WHERE s.skillId IN :skillId "
			 )
	Page<EmployeeEntityDto> findEmployeesBySkillId(int[] skillId,Pageable pageable);
	
}

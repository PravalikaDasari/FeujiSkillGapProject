package com.skillset.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.skillset.entity.EmployeeEntity;

import jakarta.transaction.Transactional;

@Transactional
public interface SkillSetRepository extends JpaRepository<EmployeeEntity, Integer> {

	
	
//	---------------------------------------------------------------
	@Query(value = "SELECT " + 
			"CONCAT(e.first_name, ' ', e.middle_name, ' ', e.last_name) employee_name, "+
			"e.employee_id AS employee_id, " +
			"e.email AS email, " +
			"e.designation AS designation, " +
		    "e.email AS employee_email, " +
		    "s.skill_name AS skill_name, " + 
		    "s.description AS description, "+
		    "sc.competency_level_id AS skill_competency_level_id, " +
		    "es.competency_level_id AS employee_competency_level_id, " +
		    "sr.reference_details_values AS skillReferenceValue, " +
		    "er.reference_details_values AS employeeReferenceValue " +
		"FROM " + "Skills s " +
		"JOIN " + "skill_competency sc ON s.skill_id = sc.skill_id " +
		"JOIN " + "employee_skills es ON sc.skill_id = es.skill_id " +
		"JOIN " + "employee e ON e.employee_id=es.employee_id " +
		"JOIN common_reference_details sr ON sc.competency_level_id = sr.reference_details_id " +
		"JOIN common_reference_details er ON es.competency_level_id = er.reference_details_id " +
		"WHERE " +
		"e.email = :email  and es.is_deleted=0 and s.skill_category_id= :skillCategoryId", nativeQuery = true)
List<Object[]> findEmployeeDetailsByEmail(String email,Integer skillCategoryId);

//	---------------------------------------------------------------

}

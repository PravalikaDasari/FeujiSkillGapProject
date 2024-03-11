package com.feuji.skillgapservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.feuji.skillgapservice.entity.SkillEntity;


public interface SkillRepository extends JpaRepository<SkillEntity, Integer> {
	
	Optional<SkillEntity>findByUuid(String uuid);

	@Query(value="select * from skills where techinical_category_id =:categoryId",nativeQuery=true)
	List<SkillEntity> findByTechinicalCategoryId(int categoryId);

	@Query(value="select s.skill_name ,"
			+ "sc.skill_type_id,"
			+ "common_reference_details_skill_type.reference_details_values as skill_type "
			+ "from skills s "
			+ "inner join skill_competency sc "
			+ "on s.skill_id = sc.skill_id "
			+ "inner join "
			+ "	timesheet_entry_system_db.common_reference_details as common_reference_details_skill_type on "
			+ "    sc.skill_type_id = common_reference_details_skill_type.reference_details_id"
			+ " where s.skill_id in (:skillIds)", nativeQuery=true)
	List<Object[]> getSkills(int[] skillIds);


}

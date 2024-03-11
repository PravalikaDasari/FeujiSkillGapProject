package com.feuji.skillgapservice.service;

import java.util.List;

import com.feuji.skillgapservice.bean.SkillCompetencyBean;
import com.feuji.skillgapservice.dto.PaginationDto;

public interface SkillCompetencyService {
	public void save(SkillCompetencyBean SkillCompetency);

	public SkillCompetencyBean updateAllRecords(SkillCompetencyBean skillCompetencyBean);

	public SkillCompetencyBean findByUuid(String uuid);

	public SkillCompetencyBean getBySkillId(int skillId);

	public List<SkillCompetencyBean> findByTechId(int technicalCatId);

	public PaginationDto getAllEmployeeSkillsBySkillIds(int[] skillId,int page ,int size);

}

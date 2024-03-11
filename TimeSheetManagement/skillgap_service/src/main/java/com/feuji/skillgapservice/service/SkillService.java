package com.feuji.skillgapservice.service;

import java.util.List;

import com.feuji.skillgapservice.bean.SkillBean;
import com.feuji.skillgapservice.dto.SkillNamesDto;

public interface SkillService {
	public SkillBean saveSkill(SkillBean skillBean);

	public SkillBean getByUuid(String uuid);

	public SkillBean updateDetails(SkillBean skillBean);

	public List<SkillBean> getAllSkills();

	public List<SkillBean> getSkillsByTechCategoryId(int categoryId);

	public SkillBean getBySkillId(int skillId);

	public List<SkillNamesDto> getSkillNames(int[] skillIds);


}

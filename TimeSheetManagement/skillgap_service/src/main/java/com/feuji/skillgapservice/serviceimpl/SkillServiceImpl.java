package com.feuji.skillgapservice.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.skillgapservice.bean.SkillBean;
import com.feuji.skillgapservice.commonconstants.CommonConstants;
import com.feuji.skillgapservice.dto.SkillNamesDto;
import com.feuji.skillgapservice.entity.SkillEntity;
import com.feuji.skillgapservice.exception.SkillNotFoundException;
import com.feuji.skillgapservice.repository.SkillRepository;
import com.feuji.skillgapservice.service.SkillService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SkillServiceImpl implements SkillService {

	@Autowired
	public SkillRepository skillRepository;

	@Override
	public SkillBean saveSkill(SkillBean skillBean) {
		log.info("Service saveSkill Method Start");
		if (skillBean != null) {
			SkillEntity entity = beanToEntity(skillBean);
			SkillEntity saveresult = skillRepository.save(entity);
			if (saveresult != null) {
				log.info("Service saveSkill Method End");
				return entityToBean(saveresult);
			} else {
				throw new SkillNotFoundException("Skill is not found");
			}
		} else {
			throw new NullPointerException("skillBean is null");
		}

	}

	@Override
	public SkillBean getSkillByUuid(String uuid) throws SkillNotFoundException {
		log.info("Service getByUuid Method Start");
		if (uuid != null) {
			SkillEntity skillEntity = skillRepository.findByUuid(uuid)
					.orElseThrow(() -> new SkillNotFoundException("Skill not found with uuid-" + uuid));
			log.info("Service getByUuid Method End");
			return entityToBean(skillEntity);

		} else {
			throw new NullPointerException("skillBean uuid is null");
		}

	}

	@Override
	public SkillBean updateSkillDetails(SkillBean skillBean) {
		log.info("Service updateDetails Method Start");
		if (skillBean != null) {
			Optional<SkillEntity> optionalEntity = skillRepository.findByUuid(skillBean.getUuid());
			if (optionalEntity.isPresent()) {
				SkillEntity entity = optionalEntity.get();
				entity.setDescription(skillBean.getDescription());
				entity.setModifiedBy(skillBean.getModifiedBy());
				entity.setIsDeleted(skillBean.getIsDeleted());
				SkillEntity savedEntity = skillRepository.save(entity);
				log.info("Service updateDetails Method End");
				return entityToBean(savedEntity);
			} else {
				throw new SkillNotFoundException("Skill not found with UUID: " + skillBean.getUuid());
			}
		} else {
			throw new NullPointerException("skillBean  is null");
		}

	}

	@Override
	public List<SkillBean> getAllSkills() {
		log.info("Service getAllSkills Method Start");

		List<SkillEntity> skillEntities = skillRepository.findAll();
		List<SkillBean> skillBeans = new ArrayList<>();
		if (skillEntities != null) {
			for (SkillEntity entity : skillEntities) {
				skillBeans.add(entityToBean(entity));
			}
			log.info("Service getAllSkills Method End");
			return skillBeans;
		} else {
			throw new SkillNotFoundException("Skills not found");
		}

	}

	@Override
	public List<SkillBean> getSkillsByTechCategoryId(int categoryId) {
		log.info("Service getSkillsByTechCategoryId Method Start");
		if (categoryId != CommonConstants.FALSE) {
			List<SkillEntity> entityList = skillRepository.findByTechinicalCategoryId(categoryId);
			if (entityList != null) {
				List<SkillBean> beanList = new ArrayList<>();
				for (SkillEntity e : entityList) {
					beanList.add(entityToBean(e));
				}
				log.info("Service getSkillsByTechCategoryId Method End");
				return beanList;
			} else {
				throw new SkillNotFoundException("Skills not found with this categoryId");
			}
		} else {
			throw new NullPointerException("skillBean categoryId is null");
		}

	}

	@Override
	public SkillBean getSkillBySkillId(int skillId) {
		log.info("Service getBySkillId Method Start");
		if (skillId != CommonConstants.FALSE) {
			SkillEntity entity = skillRepository.findById(skillId)
					.orElseThrow(() -> new SkillNotFoundException("Skill not found with id-" + skillId));
			log.info("Service getBySkillId Method End");
			return entityToBean(entity);
		} else {
			throw new NullPointerException("skillBean skillId is null");

		}
	}

	@Override
	public List<SkillNamesDto> getSkillNamesBySkillId(int[] skillIds) {
		log.info("Service getSkillNames Method Start");
		if (skillIds != null) {
			List<SkillNamesDto> skillNamesDtosList = skillRepository.getSkills(skillIds);
			if(skillNamesDtosList!=null)
			{
				for(SkillNamesDto dto :skillNamesDtosList )
				{
					if(dto.getSkillType().equals(CommonConstants.PRIMARY))
						dto.setSkillType(CommonConstants.SKILLTYPEONE);
					else
						dto.setSkillType(CommonConstants.SKILLTYPETWO);
				}
				log.info("Service getSkillNames Method End");
				return skillNamesDtosList;
			}else {
				throw new SkillNotFoundException("no record found with this id's: "+skillIds);
			}
		} else {
			throw new NullPointerException("skillBean skillIds are null");
		}

	}

	public SkillBean entityToBean(SkillEntity entity) {
		SkillBean skillBean = new SkillBean();

		skillBean.setSkillId(entity.getSkillId());
		skillBean.setSkillName(entity.getSkillName());
		skillBean.setTechinicalCategoryId(entity.getTechinicalCategoryId());
		skillBean.setSkillCategoryId(entity.getSkillCategoryId());
		skillBean.setDescription(entity.getDescription());
		skillBean.setIsDeleted(entity.getIsDeleted());
		skillBean.setUuid(entity.getUuid());
		skillBean.setCreatedBy(entity.getCreatedBy());
		skillBean.setCreatedOn(entity.getCreatedOn());
		skillBean.setModifiedBy(entity.getModifiedBy());
		skillBean.setModifiedOn(entity.getModifiedOn());

		return skillBean;
	}

	public SkillEntity beanToEntity(SkillBean bean) {
		SkillEntity entity = new SkillEntity();

		entity.setSkillId(bean.getSkillId());
		entity.setSkillName(bean.getSkillName());
		entity.setTechinicalCategoryId(bean.getTechinicalCategoryId());
		entity.setSkillCategoryId(bean.getSkillCategoryId());
		entity.setDescription(bean.getDescription());
		entity.setIsDeleted(bean.getIsDeleted());
		entity.setUuid(bean.getUuid());
		entity.setCreatedBy(bean.getCreatedBy());
		entity.setCreatedOn(bean.getCreatedOn());
		entity.setModifiedBy(bean.getModifiedBy());
		entity.setModifiedOn(bean.getModifiedOn());

		return entity;
	}
}

package com.feuji.skillgapservice.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.skillgapservice.bean.SkillBean;
import com.feuji.skillgapservice.dto.SkillNamesDto;
import com.feuji.skillgapservice.entity.SkillEntity;
import com.feuji.skillgapservice.exception.RecordNotFoundException;
import com.feuji.skillgapservice.exception.SkillNotFoundException;
import com.feuji.skillgapservice.repository.SkillRepository;
import com.feuji.skillgapservice.service.SkillService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SkillServiceImpl implements SkillService {

	@Autowired
	public SkillRepository skillRepository;

	
	/**
	 * Converts a SkillEntity object to a SkillBean object.
	 *
	 * @param entity The SkillEntity object to convert.
	 * @return The converted SkillBean object.
	 */
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
	
	/**
	 * Converts a SkillBean object to a SkillEntity object.
	 *
	 * @param bean The SkillBean object to convert.
	 * @return The converted SkillEntity object.
	 */
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
	
	
	

	/**
	 * Saves a SkillBean object into the database.
	 * @param skillBean The SkillBean object to save.
	 * @return The saved SkillBean object.
	 * @throws IllegalArgumentException If the provided SkillBean object is null.
	 */
	@Override
	public SkillBean saveSkill(SkillBean skillBean) {
		log.info("Service saveSkill Method Start");
		try {
			SkillEntity entity = beanToEntity(skillBean);
			SkillEntity save = skillRepository.save(entity);
			log.info("Service saveSkill Method End");
			return entityToBean(save);
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("failed to save records");
		}
	}
	
	
	
	/**
	 * Retrieves a SkillBean object from the database based on its UUID.
	 * @param uuid The UUID of the SkillBean object to retrieve.
	 * @return The retrieved SkillBean object.
	 * @throws SkillNotFoundException If the SkillBean object with the provided UUID is not found.
	 */
	@Override
	public SkillBean getSkillByUuid(String uuid) throws SkillNotFoundException {
		log.info("Service getByUuid Method Start");
		try {
			SkillEntity skillEntity = skillRepository.findByUuid(uuid)
					.orElseThrow(() -> new IllegalArgumentException("Skill not found with id-" + uuid));
			log.info("Service getByUuid Method End");
			return entityToBean(skillEntity);
		} catch (Exception e) {
			throw new SkillNotFoundException("failed to fetch records");
		}
	}
	
	
	

	/**
	 * Updates the details of a SkillBean object in the database.
	 *
	 * @param skillBean The SkillBean object containing the updated details.
	 * @return The updated SkillBean object.
	 * @throws SkillNotFoundException If the SkillBean object with the provided UUID is not found.
	 */
	@Override
	public SkillBean updateSkillDetails(SkillBean skillBean) {
		log.info("Service updateDetails Method Start");
		try {
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
		} catch (SkillNotFoundException e) {
			throw new SkillNotFoundException("failed to update records");
		}
	}
	
	
	

	/**
	 * Retrieves a list of all SkillBean objects from the database.
	 *
	 * @return A list of SkillBean objects.
	 * @throws RecordNotFoundException If no records are found in the database.
	 */
	@Override
	public List<SkillBean> getAllSkills() throws RecordNotFoundException {
		log.info("Service getAllSkills Method Start");
		try {
			List<SkillEntity> skillEntities = skillRepository.findAll();
			List<SkillBean> skillBeans = new ArrayList<>();
			for (SkillEntity entity : skillEntities) {
				skillBeans.add(entityToBean(entity));
			}
			log.info("Service getAllSkills Method End");
			return skillBeans;
		} catch (Exception e) {
			throw new RecordNotFoundException("failed to fetch records");
		}
	}

	
	/**
	 * Retrieves a list of SkillBean objects from the database based on the technical category ID.
	 *
	 * @param categoryId The ID of the technical category.
	 * @return A list of SkillBean objects.
	 * @throws SkillNotFoundException If no skills are found for the provided technical category ID.
	 */
	@Override
	public List<SkillBean> getSkillsByTechCategoryId(int categoryId) {
		log.info("Service getSkillsByTechCategoryId Method Start");
		try {
			List<SkillEntity> entityList = skillRepository.findByTechinicalCategoryId(categoryId);
			List<SkillBean> beanList = new ArrayList<>();
			for (SkillEntity e : entityList) {
				beanList.add(entityToBean(e));
			}
			log.info("Service getSkillsByTechCategoryId Method End");
			return beanList;
		} catch (Exception e) {
			throw new SkillNotFoundException("failed to fetch records");
		}
	}

	/**
	 * Retrieves a SkillBean object from the database based on its skill ID.
	 *
	 * @param skillId The ID of the skill.
	 * @return The retrieved SkillBean object.
	 * @throws SkillNotFoundException If the SkillBean object with the provided ID is not found.
	 */
	@Override
	public SkillBean getSkillBySkillId(int skillId) {
		log.info("Service getBySkillId Method Start");
		try {
			SkillEntity entity = skillRepository.findById(skillId)
					.orElseThrow(() -> new IllegalArgumentException("Skill not found with id-" + skillId));
			log.info("Service getBySkillId Method End");
			return entityToBean(entity);
		} catch (Exception e) {
			throw new SkillNotFoundException("failed to fetch records");
		}
	}
	
	/**
	 * Retrieves a list of SkillNamesDto objects from the database based on an array of skill IDs.
	 *
	 * @param skillIds An array of skill IDs.
	 * @return A list of SkillNamesDto objects.
	 * @throws RecordNotFoundException If no records are found for the provided skill IDs.
	 */
	@Override
	public List<SkillNamesDto> getSkillNamesBySkillId(int[] skillIds) throws RecordNotFoundException {
		log.info("Service getSkillNames Method Start");
		try {
			List<Object[]> skills = skillRepository.getSkills(skillIds);
			List<SkillNamesDto> skillNamesDtos = new ArrayList<>();
			for (Object[] dto : skills) {
				SkillNamesDto nameDto = new SkillNamesDto();
				nameDto.setSkillName(dto[0].toString());
				nameDto.setSkillTypeId(parseInteger(dto[1]));
				if (dto[2].toString().equals("Primary")) {
					nameDto.setSkillType("P");
				} else {
					nameDto.setSkillType("S");
				}
				skillNamesDtos.add(nameDto);
			}
			log.info("Service getSkillNames Method End");
			return skillNamesDtos;
		} catch (Exception e) {
			throw new RecordNotFoundException("failed to fetch records");
		}
	}

	
	/**
	 * Parses an Object into an Integer.
	 *
	 * @param value The Object to parse.
	 * @return The parsed Integer value.
	 * @throws NumberFormatException If the Object cannot be parsed into an Integer.
	 */
	private Integer parseInteger(Object value) {
		log.info("parseInteger Method Start");
		if (value instanceof String) {
			try {
				return Integer.parseInt((String) value);
			} catch (NumberFormatException e) {
				throw new NumberFormatException("failed to convert method");
			}
		} else if (value instanceof Integer) {
			return (Integer) value;
		}
		log.info("parseInteger Method End");
		return null;
	}

}

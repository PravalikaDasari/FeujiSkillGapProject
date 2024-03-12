package com.feuji.skillgapservice.serviceimpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.feuji.skillgapservice.bean.SkillCompetencyBean;
import com.feuji.skillgapservice.dto.EmployeeEntityDto;
import com.feuji.skillgapservice.dto.EmployeesSkillsListDto;
import com.feuji.skillgapservice.dto.PaginationDto;
import com.feuji.skillgapservice.dto.SkillsBean;
import com.feuji.skillgapservice.entity.SkillCompetencyEntity;
import com.feuji.skillgapservice.exception.RecordNotFoundException;
import com.feuji.skillgapservice.exception.SkillNotFoundException;
import com.feuji.skillgapservice.repository.EmployeeRepository;
import com.feuji.skillgapservice.repository.SkillCompetencyRepository;
import com.feuji.skillgapservice.service.SkillCompetencyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SkillCompetencyServiceImpl implements SkillCompetencyService {

	@Autowired
	SkillCompetencyRepository competencyRepository;

	@Autowired
	EmployeeRepository employeeRepository;
	
	

	/**
	 * Converts a SkillCompetencyBean object to a SkillCompetencyEntity object.
	 *
	 * @param skillCompetencyBean The SkillCompetencyBean object to convert.
	 * @return The converted SkillCompetencyEntity object.
	 */	
	public SkillCompetencyEntity convertBeanToEntity(SkillCompetencyBean skillCompetencyBean) {
		try {
			log.info("convert bean to entity method started");
			SkillCompetencyEntity skillCompetencyEntity = new SkillCompetencyEntity();
			skillCompetencyEntity.setRoleId(skillCompetencyBean.getRoleId());
			skillCompetencyEntity.setRoleName(skillCompetencyBean.getRoleName());
			skillCompetencyEntity.setSkillId(skillCompetencyBean.getSkillId());
			skillCompetencyEntity.setSkillTypeId(skillCompetencyBean.getSkillTypeId());
			skillCompetencyEntity.setCompetencyLevelId(skillCompetencyBean.getCompetencyLevelId());
			skillCompetencyEntity.setYearsOfExperiance(skillCompetencyBean.getYearsOfExperiance());
			skillCompetencyEntity.setCertification(skillCompetencyBean.getCertification());
			skillCompetencyEntity.setDescription(skillCompetencyBean.getDescription());
			skillCompetencyEntity.setComments(skillCompetencyBean.getComments());
			skillCompetencyEntity.setIsDeleted(skillCompetencyBean.getIsDeleted());
			skillCompetencyEntity.setUuid(skillCompetencyBean.getUuid());
			skillCompetencyEntity.setCreatedBy(skillCompetencyBean.getCreatedBy());
			skillCompetencyEntity.setCreatedOn(skillCompetencyBean.getCreatedOn());
			skillCompetencyEntity.setModifiedBy(skillCompetencyBean.getModifiedBy());
			return skillCompetencyEntity;
		}
		catch (Exception e) {
	        throw new IllegalArgumentException("Failed to convert bean to entity", e);
		}
		
	}

	
	
	
	/**
	 * Converts a SkillCompetencyEntity object to a SkillCompetencyBean object.
	 *
	 * @param skillCompetencyEntity The SkillCompetencyEntity object to convert.
	 * @return The converted SkillCompetencyBean object.
	 */	
	public SkillCompetencyBean convertEntityToBean(SkillCompetencyEntity skillCompetencyEntity) {
		try {
			SkillCompetencyBean skillCompetencyBean = new SkillCompetencyBean();
			skillCompetencyBean.setRoleId(skillCompetencyEntity.getRoleId());
			skillCompetencyBean.setRoleName(skillCompetencyEntity.getRoleName());
			skillCompetencyBean.setSkillId(skillCompetencyEntity.getSkillId());
			skillCompetencyBean.setSkillTypeId(skillCompetencyEntity.getSkillTypeId());
			skillCompetencyBean.setCompetencyLevelId(skillCompetencyEntity.getCompetencyLevelId());
			skillCompetencyBean.setYearsOfExperiance(skillCompetencyEntity.getYearsOfExperiance());
			skillCompetencyBean.setCertification(skillCompetencyEntity.getCertification());
			skillCompetencyBean.setDescription(skillCompetencyEntity.getDescription());
			skillCompetencyBean.setComments(skillCompetencyEntity.getComments());
			skillCompetencyBean.setIsDeleted(skillCompetencyEntity.getIsDeleted());
			skillCompetencyBean.setUuid(skillCompetencyEntity.getUuid());
			skillCompetencyBean.setCreatedBy(skillCompetencyEntity.getCreatedBy());
			skillCompetencyBean.setCreatedOn(skillCompetencyEntity.getCreatedOn());
			skillCompetencyBean.setModifiedBy(skillCompetencyEntity.getModifiedBy());
			return skillCompetencyBean;
		}
		catch (Exception e) {
	        throw new IllegalArgumentException("Failed to convert bean to entity", e);
		}
		
	}

	
	
	/**
	 * Saves a SkillCompetencyBean object into the database.
	 *
	 * @param skillCompetencyBean The SkillCompetencyBean object to save.
	 * @throws IllegalArgumentException If the provided SkillCompetencyBean object is null.
	 */
	@Override
	public void saveSkillCompetency(SkillCompetencyBean skillCompetencyBean) throws IllegalArgumentException {
		log.info("Service save Method Start");
		try {
			if (skillCompetencyBean != null) {
				SkillCompetencyEntity convertBeanToEntity = convertBeanToEntity(skillCompetencyBean);

				if (convertBeanToEntity.getModifiedOn() == null) {
					convertBeanToEntity.setModifiedOn(new Timestamp(System.currentTimeMillis()));
				}
				if (convertBeanToEntity.getCreatedOn() == null) {
					convertBeanToEntity.setCreatedOn(new Timestamp(System.currentTimeMillis()));
				}
				competencyRepository.save(convertBeanToEntity);
				log.info("Service save Method End");
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("failed to save records");
		}
	}

	
	
	/**
	 * Updates all SkillCompetencyBean records in the database with the provided SkillCompetencyBean object.
	 *
	 * @param skillCompetencyBean The SkillCompetencyBean object containing the updated records.
	 * @return The updated SkillCompetencyBean object.
	 * @throws RecordNotFoundException If no records are found in the database.
	 */
	@Override
	public SkillCompetencyBean updateAllSkillCompetencyRecords(SkillCompetencyBean skillCompetencyBean)
			throws RecordNotFoundException {
		log.info("Service updateAllRecords Method Start");
		try {
			SkillCompetencyEntity beanToEntity = convertBeanToEntity(skillCompetencyBean);
			if (beanToEntity.getModifiedOn() == null) {
				beanToEntity.setModifiedOn(new Timestamp(System.currentTimeMillis()));
			}
			SkillCompetencyEntity save = competencyRepository.save(beanToEntity);
			SkillCompetencyBean entityToBean = convertEntityToBean(save);
			log.info("Service updateAllRecords Method End");
			return entityToBean;
		} catch (Exception e) {
			throw new RecordNotFoundException("failed to update records");
		}
	}

	
	
	/**
	 * Retrieves a SkillCompetencyBean object from the database based on its UUID.
	 *
	 * @param uuid The UUID of the SkillCompetencyBean object to retrieve.
	 * @return The retrieved SkillCompetencyBean object.
	 * @throws RecordNotFoundException If the SkillCompetencyBean object with the provided UUID is not found.
	 */
	@Override
	public SkillCompetencyBean findSkillCompetencyByUuid(String uuid) throws RecordNotFoundException {
		log.info("Service findByUuid Method Start");
		try {
			SkillCompetencyEntity skillEntity = competencyRepository.findByUuid(uuid)
					.orElseThrow(() -> new IllegalArgumentException("Skill not found with id-" + uuid));
			log.info("Service findByUuid Method End");
			return convertEntityToBean(skillEntity);
		} catch (Exception e) {
			throw new RecordNotFoundException("failed to fetch records");
		}

	}

	
	
	/**
	 * Retrieves a SkillCompetencyBean object from the database based on its skill ID.
	 *
	 * @param skillId The ID of the skill.
	 * @return The retrieved SkillCompetencyBean object.
	 * @throws SkillNotFoundException If the SkillCompetencyBean object with the provided skill ID is not found.
	 */
	@Override
	public SkillCompetencyBean getSkillCompetencyBySkillId(int skillId) {
		log.info("Service getBySkillId Method Start");
		try {
			SkillCompetencyEntity entity = competencyRepository.findBySkillId(skillId);
			log.info("Service getBySkillId Method End");
			return convertEntityToBean(entity);
		} catch (Exception e) {
			throw new SkillNotFoundException("failed to fetch records");
		}
	}
	
	
	

	/**
	 * Retrieves a list of SkillCompetencyBean objects from the database based on a technical category ID.
	 *
	 * @param technicalCatId The ID of the technical category.
	 * @return A list of SkillCompetencyBean objects.
	 * @throws RecordNotFoundException If no records are found for the provided technical category ID.
	 */
	@Override
	public List<SkillCompetencyBean> findSkillCompetencyByTechId(int technicalCatId) throws RecordNotFoundException {
		log.info("Service findByTechId Method Start");
		try {
			List<SkillCompetencyEntity> entities = competencyRepository.getByTechId(technicalCatId);
			List<SkillCompetencyBean> list = new ArrayList<>();
			for (SkillCompetencyEntity entity : entities) {
				SkillCompetencyBean bean = convertEntityToBean(entity);
				list.add(bean);
			}
			log.info("Service findByTechId Method End");
			return list;
		} catch (Exception e) {
			throw new RecordNotFoundException("failed to fetch records");
		}
	}

	
	
	/**
	 * Retrieves a paginated list of employee skills based on an array of skill IDs.
	 *
	 * @param skillId An array of skill IDs.
	 * @param page The page number.
	 * @param size The page size.
	 * @return A PaginationDto containing the paginated list of employee skills.
	 * @throws RecordNotFoundException If no records are found for the provided skill IDs.
	 */
	@Override
	public PaginationDto getAllEmployeeSkillsBySkillIds(int[] skillId, int page, int size) throws RecordNotFoundException {
		try {

			Pageable pageable = PageRequest.of(page, size);

			List<EmployeesSkillsListDto> employeeSkillList = new ArrayList<EmployeesSkillsListDto>();

			Page<EmployeeEntityDto> findEmployeesBySkillId = employeeRepository.findEmployeesBySkillId(skillId,
					pageable);
			for (EmployeeEntityDto employee : findEmployeesBySkillId.getContent()) {
				List<SkillsBean> findSkillsByEmployeeId = competencyRepository
						.findSkillsByEmployeeId(employee.getEmployeeId(), skillId);
				EmployeesSkillsListDto dto = new EmployeesSkillsListDto();
				dto.setEmployeeName(
						employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName());
				dto.setEmployeeId(employee.getEmployeeId());
				dto.setEmployeeCode(employee.getEmployeeCode());
				dto.setDesignition(employee.getDesignition());
				dto.setEmail(employee.getEmail());
				dto.setSkillLists(findSkillsByEmployeeId);

				employeeSkillList.add(dto);

			}
			PaginationDto paginationDto = PaginationDto.builder().pageNo(page).pageSize(size)
					.first(findEmployeesBySkillId.isFirst()).last(findEmployeesBySkillId.isLast())
					.totalRecords(findEmployeesBySkillId.getTotalElements())
					.totalPages(findEmployeesBySkillId.getTotalPages()).skillList(employeeSkillList).build();
			return paginationDto;
		} catch (Exception e) {
			throw new RecordNotFoundException("failed to fetch records");
		}

	}

}

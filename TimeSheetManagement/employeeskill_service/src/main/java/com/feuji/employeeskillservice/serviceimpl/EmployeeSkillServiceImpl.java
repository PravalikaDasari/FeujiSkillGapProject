package com.feuji.employeeskillservice.serviceimpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.feuji.employeeskillservice.bean.EmployeeBean;
import com.feuji.employeeskillservice.bean.EmployeeSkillBean;
import com.feuji.employeeskillservice.bean.EmployeeSkillGet;
import com.feuji.employeeskillservice.bean.EmployeeUiBean;
import com.feuji.employeeskillservice.bean.SkillBean;
import com.feuji.employeeskillservice.constants.CommonConstants;
import com.feuji.employeeskillservice.entity.EmployeeSkillEntity;
import com.feuji.employeeskillservice.exception.NoRecordFoundException;
import com.feuji.employeeskillservice.repository.EmployeeSkillRepository;
import com.feuji.employeeskillservice.service.EmployeeSkillService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeSkillServiceImpl implements EmployeeSkillService {
	@Autowired
	private EmployeeSkillRepository repository;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Converts a list of employee UI beans to employee skill beans.
	 * 
	 * @param employeeUiBeans The list of employee UI beans to be converted.
	 * @return List<EmployeeSkillBean> The list of employee skill beans converted
	 *         from the provided UI beans.
	 * @throws Exception
	 */
	@Override
	public List<EmployeeSkillBean> convertUiBeanToSkillBean(List<EmployeeUiBean> employeeUiBeans)
			throws IllegalArgumentException {
//		if(employeeUiBeans!=null) {
//			
//		}
		try {
			log.info("convertUiBeanToSkillBean() start:Converts a list of employee UI beans to employee skill beans");
			List<EmployeeSkillBean> listBeans = new ArrayList<>();
			for (EmployeeUiBean bean : employeeUiBeans) {
				EmployeeBean employee = getEmployeeByEmail(bean.getEmployeeMail());
				EmployeeSkillBean empSkillBean = new EmployeeSkillBean();
				empSkillBean.setEmployeeId(employee.getEmployeeId());
				empSkillBean.setEmployeeCode(employee.getEmployeeCode());
				empSkillBean.setSkillId(Integer.parseInt(bean.getSkillId()));
				empSkillBean.setComments(bean.getComments());
				empSkillBean.setDescription(bean.getDescription());
				empSkillBean.setCreatedBy(
						employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName());
				empSkillBean.setModifiedBy(
						employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName());
				empSkillBean.setCompetencyLevelId(Integer.parseInt(bean.getCompetencyLevelId()));
				empSkillBean.setSkillTypeId(Integer.parseInt(bean.getSkillTypeId()));
				empSkillBean.setYearsOfExp(Integer.parseInt(bean.getYearsOfExp()));
				empSkillBean.setIsDeleted(Byte.parseByte(bean.getIsDeleted()));
				if (bean.getCertification().equals(CommonConstants.YES))
					empSkillBean.setCertification((byte) 1);
				else
					empSkillBean.setCertification((byte) 0);
				listBeans.add(empSkillBean);
			}
			log.info("convertUiBeanToSkillBean() end:Converts a list of employee UI beans to employee skill beans");
			return listBeans;
		} catch (Exception e) {
			throw new IllegalArgumentException("failed to convert employee UI beans to employee skill beans ");
		}
	}

	/**
	 * Saves an employee skill bean.
	 * 
	 * @param bean The employee skill bean to be saved.
	 * @return EmployeeSkillBean The saved employee skill bean.
	 * @throws Exception
	 */
	@Override
	public EmployeeSkillBean saveEmployeeSkill(EmployeeSkillBean bean) throws Exception {
		log.info("saveEmployeeSkill() start:Saves an employee skill bean");
		EmployeeSkillEntity skillEntity = null;
		EmployeeSkillBean skillBean = null;
		try {
			EmployeeSkillEntity entity = beanToEntityConvertion(bean);
			skillEntity = repository.save(entity);
			skillBean = entityToBeanCovertion(skillEntity);
			log.info("saveEmployeeSkill() ended:Saves an employee skill bean");
			return skillBean;
		} catch (Exception e) {
			throw new Exception("failed to Save an employee skill bean");
		}
	}

	public EmployeeSkillBean entityToBeanCovertion(EmployeeSkillEntity entity) {
		log.info("entityToBeanCovertion() started");
		EmployeeSkillBean bean = new EmployeeSkillBean();
		bean.setEmployeeSkillId(entity.getEmployeeSkillId());
		bean.setEmployeeId(entity.getEmployeeId());
		bean.setEmployeeCode(entity.getEmployeeCode());
		bean.setSkillId(entity.getSkillId());
		bean.setCompetencyLevelId(entity.getCompetencyLevelId());
		bean.setSkillTypeId(entity.getSkillTypeId());
		bean.setYearsOfExp(entity.getYearsOfExp());
		bean.setCertification(entity.getCertification());
		bean.setDescription(entity.getDescription());
		bean.setComments(entity.getComments());
		bean.setIsDeleted(entity.getIsDeleted());
		bean.setUuid(entity.getUuid());
		bean.setCreatedBy(entity.getCreatedBy());
		bean.setCreatedOn(entity.getCreatedOn());
		bean.setModifiedBy(entity.getModifiedBy());
		bean.setModifiedOn(entity.getModifiedOn());
		log.info("entityToBeanCovertion() ended");
		return bean;
	}

	public EmployeeSkillEntity beanToEntityConvertion(EmployeeSkillBean bean) {
		log.info("beanToEntityConvertion() started");
		EmployeeSkillEntity entity = new EmployeeSkillEntity();
		entity.setEmployeeId(bean.getEmployeeId());
		entity.setEmployeeCode(bean.getEmployeeCode());
		entity.setSkillId(bean.getSkillId());
		entity.setSkillTypeId(bean.getSkillTypeId());
		entity.setCompetencyLevelId(bean.getCompetencyLevelId());
		entity.setYearsOfExp(bean.getYearsOfExp());
		entity.setIsDeleted(bean.getIsDeleted());
		entity.setCertification(bean.getCertification());
		entity.setDescription(bean.getDescription());
		entity.setComments(bean.getComments());
		entity.setCreatedBy(bean.getCreatedBy());
		entity.setModifiedBy(bean.getModifiedBy());
		log.info("beanToEntityConvertion() ended");
		return entity;
	}

	/**
	 * Updates an employee skill bean.
	 * 
	 * @param employeeSkillBean The employee skill bean to be updated.
	 * @return EmployeeSkillBean The updated employee skill bean.
	 * @throws Exception
	 * @throws NullPointerException If the employee skill bean object is null, this
	 *                              exception is thrown.
	 */
	@Override
	public EmployeeSkillBean updateEmployeeSkill(EmployeeSkillBean employeeSkillBean) throws Exception {
		log.info("updateEmployeeSkill() start:Saves an employee skill bean");
		try {
			EmployeeSkillEntity entity = beanToEntityConvertion(employeeSkillBean);
			entity.setModifiedOn(new Timestamp(System.currentTimeMillis()));
			EmployeeSkillEntity save = repository.save(entity);
			EmployeeSkillBean entityToBeanCovertion = entityToBeanCovertion(save);
			log.info("updateEmployeeSkill() start:Saves an employee skill bean");
			return entityToBeanCovertion;
		} catch (Exception e) {
			throw new Exception("failed to update employee skill bean");
		}
	}

	/**
	 * Retrieves a list of employee skill beans associated with the given employee
	 * ID.
	 * 
	 * @param employeeId The ID of the employee whose skills are to be retrieved.
	 * @return List<EmployeeSkillBean> A list containing the employee skill beans
	 *         associated with the given employee ID.
	 * @throws NoRecordFoundException If no records are found for the specified
	 *                                employee ID, this exception is thrown.
	 */
	@Override
	public List<EmployeeSkillBean> getEmployeeSkillById(Long employeeId) throws NoRecordFoundException {
		try {
			log.info("getEmployeeSkillById() start: in EmployeeSkillServiceImpl");
			List<EmployeeSkillEntity> list = repository.findByEmployeeId(employeeId);
			
			List<EmployeeSkillBean> beans = new ArrayList<>();
			for (EmployeeSkillEntity entity : list) {
				beans.add(entityToBeanCovertion(entity));
			}
			log.info("getEmployeeSkillById() ended: in EmployeeSkillServiceImpl");
			return beans;
		} catch (Exception e) {
			throw new NoRecordFoundException("failed to fetch records");
		}
	}

	/**
	 * 
	 * Saves a list of employee skill beans.
	 * 
	 * @param beanList The list of employee skill beans to be saved.
	 * @return List<EmployeeSkillBean> A list containing the saved employee skill
	 *         beans.
	 */
	@Override
	public List<EmployeeSkillBean> saveAll(List<EmployeeSkillBean> beanList) {
		log.info("saveAll() started:in EmployeeSkillServiceImpl ");
		try {
			List<EmployeeSkillEntity> entityList = new ArrayList<EmployeeSkillEntity>();
			for (EmployeeSkillBean bean : beanList) {
				entityList.add(beanToEntityConvertion(bean));
			}
			List<EmployeeSkillEntity> saveAll = repository.saveAll(entityList);
			List<EmployeeSkillBean> list = new ArrayList<>();
			for (EmployeeSkillEntity e : saveAll) {
				EmployeeSkillBean bean = entityToBeanCovertion(e);
				list.add(bean);
			}
			log.info("saveAll() ended:in EmployeeSkillServiceImpl ");
			return list;
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("failed to save");
		}
	}

	/**
	 * Retrieves a list of employee skill beans associated with the given skill ID.
	 * 
	 * @param skillId The ID of the skill for which employee skills are to be
	 *                retrieved.
	 * @return List<EmployeeSkillBean> A list containing the employee skill beans
	 *         associated with the given skill ID.
	 * @throws NoRecordFoundException
	 */
	@Override
	public List<EmployeeSkillBean> findBySkillId(int skillId) throws NoRecordFoundException {
		try {
			log.info("findBySkillId() started:  in EmployeeSkillServiceImpl");
			List<EmployeeSkillEntity> entity = repository.findBySkillId(skillId);
			List<EmployeeSkillBean> beans = new ArrayList<>();
			for (EmployeeSkillEntity e : entity) {
				EmployeeSkillBean bean = entityToBeanCovertion(e);
				beans.add(bean);
			}
			log.info("findBySkillId() started:  in EmployeeSkillServiceImpl");
			return beans;
		} catch (Exception e) {
			throw new NoRecordFoundException("Failed to fetch EmployeeSkillBean");
		}
	}

	/**
	 * 
	 * Retrieves employee details by email from another server.
	 * 
	 * @param email The email of the employee to be retrieved.
	 * 
	 * @return EmployeeBean The employee details retrieved by email.
	 * @throws Exception
	 */
	@Override
	public EmployeeBean getEmployeeByEmail(String email) throws Exception {
		try {
			log.info("getEmployeeByEmail() start: in EmployeeSkillServiceImpl");
			String url = "http://localhost:8091/api/employee/getByEmail/" + email;

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> httpEntity = new HttpEntity<>(headers);

			ResponseEntity<EmployeeBean> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
					EmployeeBean.class);
			EmployeeBean employeeBean = responseEntity.getBody();
			log.info("getEmployeeByEmail() ended:in EmployeeSkillServiceImpl");
			return employeeBean;
		} catch (Exception e) {
			throw new Exception("failed to fetch Employee");
		}
	}

	/**
	 * 
	 * Retrieves all employee skills associated with the given email.
	 * 
	 * @param email The email of the employee for which skill records are to be
	 *              retrieved.
	 * @return List<EmployeeSkillGet> A list containing all employee skills
	 *         associated with the given email.
	 * @throws Exception If there is an error during the retrieval process, this
	 *                   exception is thrown.
	 */
	@Override
	public List<EmployeeSkillGet> getAllEmployeeSkills(String email) throws Exception {

		log.info(" getAllEmployeeSkills() started: in EmployeeSkillServiceImpl");
		try {
			EmployeeBean employee = getEmployeeByEmail(email);
			List<EmployeeSkillGet> list = new ArrayList<>();
			List<EmployeeSkillBean> findAll = getEmployeeSkillById(employee.getEmployeeId());
			for (EmployeeSkillBean bean : findAll) {
				if (bean.getIsDeleted() == CommonConstants.FALSE) {
					EmployeeSkillGet skillget = entityToSkill(bean);
					list.add(skillget);
				}
			}
			log.info(" getAllEmployeeSkills() ended: in EmployeeSkillServiceImpl");
			return list;
		} catch (Exception e) {
			throw new NoRecordFoundException("failed to fetch EmployeeSkillGet records");
		}
//list.stream().filter(a->a.getIsDeleted() == CommonConstants.FALSE).collect(toList());try to implement 8 features
	}

	/**
	 * 
	 * Retrieves a skill bean by its ID from another server.
	 * 
	 * @param skillId The ID of the skill to be retrieved.
	 * 
	 * @return SkillBean The skill bean retrieved by ID.
	 * @throws NoRecordFoundException
	 */
	public SkillBean getSkillbeanById(int skillId) throws NoRecordFoundException {
		log.info("getSkillbeanById() started: in EmployeeSkillServiceImpl");
		try {
			String url = "http://localhost:8087/api/skill/getBySkillId/" + skillId;

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> httpEntity = new HttpEntity<>(headers);

			ResponseEntity<SkillBean> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
					SkillBean.class);
			log.info("getSkillbeanById() ended:in EmployeeSkillServiceImpl");
			return responseEntity.getBody();
		} catch (Exception e) {
			throw new NoRecordFoundException("failed to fetch SkillBean");
		}
	}

	/**
	 * 
	 * Retrieves the type name by its ID from another server.
	 * 
	 * @param id The ID of the type for which the name is to be retrieved.
	 * 
	 * @return String The name of the type retrieved by ID.
	 * @throws NoRecordFoundException
	 */
	public String getTypeName(int id) throws NoRecordFoundException {
		log.info("getTypeName() started: in EmployeeSkillServiceImpl");
		try {
			String url = "http://localhost:8081/api/referencedetails/getById/" + id;

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> httpEntity = new HttpEntity<>(headers);

			ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
					String.class);
			log.info("getTypeName() ended:in EmployeeSkillServiceImpl");
			return responseEntity.getBody();
		} catch (Exception e) {
			throw new NoRecordFoundException("failed to fetch skill name");
		}
	}

	/**
	 * 
	 * Retrieves the ID by its name from another server.
	 * 
	 * @param name The name of the item for which the ID is to be retrieved.
	 * 
	 * @return int The ID of the item retrieved by name.
	 * @throws Exception
	 */
	public int GetIdByName(String name) throws Exception {
		log.info("GetIdByName() started: in EmployeeSkillServiceImpl");
		try {
			String url = "http://localhost:8081/api/referencedetails/getByName/" + name;

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> httpEntity = new HttpEntity<>(headers);

			ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
					String.class);
			log.info("GetIdByName() ended:in EmployeeSkillServiceImpl");
			return Integer.parseInt(responseEntity.getBody());
		} catch (Exception e) {
			throw new Exception("Failed to fetch id");
		}
	}

	/**
	 * 
	 * Converts an employee skill bean to an employee skill get bean.
	 * 
	 * @param entity The employee skill bean to be converted.
	 * @return EmployeeSkillGet The employee skill get bean converted from the
	 *         provided employee skill bean.
	 * @throws Exception If there is an error during the conversion process, this
	 *                   exception is thrown.
	 */
	public EmployeeSkillGet entityToSkill(EmployeeSkillBean entity) throws Exception {
		log.info("entityToSkill() started: in EmployeeSkillServiceImpl");
		try {
			SkillBean skillBean = getSkillbeanById(entity.getSkillId());
			EmployeeSkillGet skill = new EmployeeSkillGet();
			skill.setEmployeeSkillId(entity.getEmployeeSkillId());
			skill.setSkillCategory(getTypeName(skillBean.getSkillCategoryId()));
			skill.setTechnicalCategory(getTypeName(skillBean.getTechinicalCategoryId()));
			skill.setSkillTypeId(getTypeName(entity.getSkillTypeId()));
			skill.setSkillId(skillBean.getSkillName());
			skill.setCompetencyLevelId(getTypeName(entity.getCompetencyLevelId()));
			skill.setYearsOfExp(entity.getYearsOfExp());
			String getCertification = (entity.getCertification() == (byte) 1) ? "Yes" : "No";
			skill.setCertification(getCertification);
			skill.setDescription(entity.getDescription());
			skill.setComments(entity.getComments());
			skill.setIsDeleted(entity.getIsDeleted());
			log.info("entityToSkill() ended: in EmployeeSkillServiceImpl");
			return skill;
		} catch (Exception e) {
			throw new Exception("failed to convert EmployeeSkillBean to EmployeeSkillGet " + e.getMessage());
		}
	}

	/**
	 * 
	 * Updates the delete status of an employee skill record identified by the given
	 * employee skill ID.
	 * 
	 * @param employeeSkillId The ID of the employee skill record to be updated.
	 * @return String A message indicating the result of the update operation.
	 * @throws Exception If there is an error during the update process, this
	 *                   exception is thrown.
	 */
	@Override
	public String updateDeleteStatus(Long employeeSkillId) throws Exception {
		log.info("updateDeleteStatus() started: in EmployeeSkillServiceImpl");
		try {
			repository.updateIsDeleted(employeeSkillId);
			log.info("updateDeleteStatus() ended: in EmployeeSkillServiceImpl");
			return "updated Successfully";
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 
	 * Updates an employee skill record with the details provided in the employee
	 * skill get object.
	 * 
	 * @param set The employee skill get object containing the updated details.
	 * 
	 * @param id  The ID of the employee skill record to be updated.
	 * 
	 * @return EmployeeSkillBean The updated employee skill bean.
	 * @throws Exception
	 */
	@Override
	public EmployeeSkillBean updateEmployeeSkill(EmployeeSkillGet set, Long id) throws Exception {
		log.info("update() started: in EmployeeSkillServiceImpl");
		try {
			EmployeeSkillEntity entity = repository.findByEmployeeSkillId(id);
			try {
				entity.setCompetencyLevelId(GetIdByName(set.getCompetencyLevelId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			entity.setYearsOfExp(set.getYearsOfExp());
			if (set.getCertification().equals("Yes"))
				entity.setCertification((byte) 1);
			else
				entity.setCertification((byte) 0);
			entity.setDescription(set.getDescription());
			entity.setComments(set.getComments());

			long currentTimeMillis = System.currentTimeMillis();
			Timestamp timestamp = new Timestamp(currentTimeMillis);
			entity.setModifiedOn(timestamp);

			EmployeeSkillEntity save = repository.save(entity);
			log.info("update() ended: in EmployeeSkillServiceImpl");
			return entityToBeanCovertion(save);
		} catch (Exception e) {
			throw new Exception("failed to update");
		}
	}
}

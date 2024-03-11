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
import com.feuji.employeeskillservice.bean.SkillBean;
import com.feuji.employeeskillservice.entity.EmployeeSkillEntity;
import com.feuji.employeeskillservice.exception.NoRecordFoundException;
import com.feuji.employeeskillservice.repository.EmployeeSkillRepository;
import com.feuji.employeeskillservice.service.EmployeeSkillService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeSkillServiceImpl implements EmployeeSkillService 
{
	@Autowired
	private EmployeeSkillRepository repository;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public EmployeeSkillBean saveEmployeeSkill(EmployeeSkillBean bean) {
		log.info("save() in EmployeeSkillServiceImpl started");
		EmployeeSkillEntity skillEntity = null;
		EmployeeSkillBean skillBean = null;
		try {
			EmployeeSkillEntity entity = beanToEntityConvertion(bean);
			skillEntity = repository.save(entity);
			skillBean = entityToBeanCovertion(skillEntity);

		} catch (Exception e) {
			log.info("Error occurred while saving employee in implementation: {}", e.getMessage());
		}
		log.info("save() in EmployeeSkillServiceImpl ended");
		return skillBean;
	}

	private EmployeeSkillBean entityToBeanCovertion(EmployeeSkillEntity entity) {
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

	private EmployeeSkillEntity beanToEntityConvertion(EmployeeSkillBean bean) {
		log.info("beanToEntityConvertion() started");
		EmployeeSkillEntity entity = new EmployeeSkillEntity();

//		entity.setEmployeeSkillId(bean.getEmployeeSkillId());
		entity.setEmployeeId(bean.getEmployeeId());
		entity.setEmployeeCode(bean.getEmployeeCode());
		entity.setSkillId(bean.getSkillId());
		entity.setSkillTypeId(bean.getSkillTypeId());
		entity.setCompetencyLevelId(bean.getCompetencyLevelId());
		entity.setYearsOfExp(bean.getYearsOfExp());
//		entity.setUuid(bean.getUuid());
		entity.setIsDeleted(bean.getIsDeleted());
		entity.setCertification(bean.getCertification());
		entity.setDescription(bean.getDescription());
		entity.setComments(bean.getComments());
		entity.setCreatedBy(bean.getCreatedBy());
		entity.setModifiedBy(bean.getModifiedBy());
		log.info("beanToEntityConvertion() ended");
		return entity;
	}

//	@Override
//	public List<EmployeeSkillBean> getEmployeeSkillById(Long employeeId) throws NoRecordFoundException
//	{
//		log.info("getEmployeeSkill() in implementation started");
//			List<Optional<EmployeeSkillEntity>> findByEmployeeId = repository.findByEmployeeId(employeeId);
//			
//			List<EmployeeSkillBean> beanList = new ArrayList<>();
//			if(beanList.size()==0)
//				throw new NoRecordFoundException("no record found with id: "+employeeId);
//			
//			for (Optional<EmployeeSkillEntity> employeeSkillBean : findByEmployeeId) {
//				EmployeeSkillEntity employeeSkillEntity = employeeSkillBean.get();
//				beanList.add(entityToBeanCovertion(employeeSkillEntity));
//			}
//			log.info("getEmployeeSkill() in implementation ended");
//			return beanList;
//	}

	@Override
	public EmployeeSkillBean updateEmployeeSkill(EmployeeSkillBean employeeSkillBean) {
		try {
			EmployeeSkillEntity entity = beanToEntityConvertion(employeeSkillBean);
			entity.setModifiedOn(new Timestamp(System.currentTimeMillis()));
			EmployeeSkillEntity save = repository.save(entity);

			EmployeeSkillBean entityToBeanCovertion = entityToBeanCovertion(save);
			return entityToBeanCovertion;
		} catch (Exception e) {
			throw new NullPointerException("employee skill bean object is null");
		}
	}

	@Override
	public List<EmployeeSkillBean> getEmployeeSkillById(Long employeeId) throws NoRecordFoundException {
		List<EmployeeSkillEntity> list = repository.findByEmployeeId(employeeId);
		List<EmployeeSkillBean> beans = new ArrayList<>();
		for (EmployeeSkillEntity entity : list) {
			beans.add(entityToBeanCovertion(entity));
		}
		return beans;
	}

	@Override
	public List<EmployeeSkillBean> saveAll(List<EmployeeSkillBean> beanList) {
		List<EmployeeSkillEntity> entityList = new ArrayList<EmployeeSkillEntity>();
		for (EmployeeSkillBean bean : beanList) {
			entityList.add(beanToEntityConvertion(bean));
		}
		List<EmployeeSkillEntity> saveAll = repository.saveAll(entityList);
		List<EmployeeSkillBean> list = new ArrayList<>();
		for(EmployeeSkillEntity e:saveAll)
		{
			EmployeeSkillBean bean = entityToBeanCovertion(e);
			list.add(bean);
		}
		return list;
	}

	@Override
	public List<EmployeeSkillBean> findBySkillId(int skillId) {
		log.info("get() in service");
		List<EmployeeSkillEntity> entity = repository.findBySkillId(skillId);
		List<EmployeeSkillBean> beans = new ArrayList<>();
		for(EmployeeSkillEntity e:entity)
		{
			EmployeeSkillBean bean = entityToBeanCovertion(e);
			beans.add(bean);
		}
		return beans;
	}

	@Override
	public EmployeeBean getEmployeeByEmail(String email) {
		log.info("connecting to other server...");
		String url = "http://localhost:8091/api/employee/getByEmail/" + email;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Input entity
		HttpEntity<String> httpEntity = new HttpEntity<>(headers);

		ResponseEntity<EmployeeBean> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
				EmployeeBean.class);
		EmployeeBean employeeBean = responseEntity.getBody();
		return employeeBean;
	}

	@Override
	public List<EmployeeSkillGet> getAllEmployeeSkills(String email) throws NoRecordFoundException {
		
		log.info("email before getting employee bean"+email);
		EmployeeBean employee = getEmployeeByEmail(email);
		log.info("email is ----- "+email+"---- emp id-- "+employee.getEmployeeId());
		List<EmployeeSkillGet> list = new ArrayList<>();
		List<EmployeeSkillBean> findAll = getEmployeeSkillById(employee.getEmployeeId());
		log.info(findAll.size()+"size of EmployeeSkillBean ----=======");
		for (EmployeeSkillBean bean : findAll) {
			// check this
			log.info("first bean is'''''");
			log.info(bean.toString());
			if (bean.getIsDeleted() == 0) {
				EmployeeSkillGet skillget = entityToSkill(bean);
				list.add(skillget);
			}
		}
		log.info(list.size()+" get beans list size");
		return list;
		
	}

	public SkillBean getSkillbeanById(int id)
	{
		String url = "http://localhost:8087/api/skill/getBySkillId/"+id ;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<>(headers);

		ResponseEntity<SkillBean> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
				SkillBean.class);
		return responseEntity.getBody();
	}
	
	public String getTypeName(int id)
	{
		String url = "http://localhost:8081/api/referencedetails/getById/"+id;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<>(headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
				String.class);
		log.info(responseEntity.getBody());
		log.info(responseEntity.toString());
		return responseEntity.getBody();
	}
	public int GetIdByName(String name)
	{
		String url = "http://localhost:8081/api/referencedetails/getByName/"+name;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<>(headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
				String.class);
		return Integer.parseInt(responseEntity.getBody());
	}
	public EmployeeSkillGet entityToSkill(EmployeeSkillBean entity) 
	{
		log.info("!!!!!entity to skill convertionstarted");
		SkillBean skillBean = getSkillbeanById(entity.getSkillId());
		EmployeeSkillGet skill = new EmployeeSkillGet();
		skill.setEmployeeSkillId(entity.getEmployeeSkillId());
		skill.setSkillCategory(getTypeName(skillBean.getSkillCategoryId()));
		skill.setTechnicalCategory(getTypeName(skillBean.getTechinicalCategoryId()));
		skill.setSkillTypeId(getTypeName(entity.getSkillTypeId()));
		skill.setSkillId(skillBean.getSkillName());
		skill.setCompetencyLevelId(getTypeName(entity.getCompetencyLevelId()));
		skill.setYearsOfExp(entity.getYearsOfExp());
		if(entity.getCertification()==(byte)1)
			skill.setCertification("Yes");
		else
			skill.setCertification("No");
		skill.setDescription(entity.getDescription());
		skill.setComments(entity.getComments());
		skill.setIsDeleted(entity.getIsDeleted());
		log.info("!!!!!entity to skill convertionended");
		return skill;
	}

	@Override
	public String updateDeleteStatus(Long employeeSkillId) throws Exception 
	{
		log.info("updateDeleteStatus() started");
		try {
			repository.updateIsDeleted(employeeSkillId);
			return "updated Successfully";
		}
		catch(Exception e)
		{
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public EmployeeSkillBean update(EmployeeSkillGet set,Long id)
	{
		EmployeeSkillEntity entity = repository.findByEmployeeSkillId(id);
		
		entity.setCompetencyLevelId(GetIdByName(set.getCompetencyLevelId()));
		entity.setYearsOfExp(set.getYearsOfExp());
		if(set.getCertification().equals("Yes"))
			entity.setCertification((byte)1);
		else
			entity.setCertification((byte)0);
		entity.setDescription(set.getDescription());
		entity.setComments(set.getComments());
		
		long currentTimeMillis = System.currentTimeMillis();
		Timestamp timestamp = new Timestamp(currentTimeMillis);
		entity.setModifiedOn(timestamp);
		
		EmployeeSkillEntity save = repository.save(entity);
		return entityToBeanCovertion(save);
	}

}

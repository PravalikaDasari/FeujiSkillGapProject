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
	
	public SkillCompetencyEntity convertBeanToEntity(SkillCompetencyBean skillCompetencyBean) {
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

	public SkillCompetencyBean convertEntityToBean(SkillCompetencyEntity skillCompetencyEntity) {
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

	@Override
	public void save(SkillCompetencyBean skillCompetencyBean) {

		if (skillCompetencyBean != null) {
			SkillCompetencyEntity convertBeanToEntity = convertBeanToEntity(skillCompetencyBean);

			if (convertBeanToEntity.getModifiedOn() == null) {
				convertBeanToEntity.setModifiedOn(new Timestamp(System.currentTimeMillis()));
			}
			if (convertBeanToEntity.getCreatedOn() == null) {
				convertBeanToEntity.setCreatedOn(new Timestamp(System.currentTimeMillis()));
			}
			competencyRepository.save(convertBeanToEntity);
		}

	}

	@Override
	public SkillCompetencyBean updateAllRecords(SkillCompetencyBean skillCompetencyBean) {
		SkillCompetencyEntity beanToEntity = convertBeanToEntity(skillCompetencyBean);
		if (beanToEntity.getModifiedOn() == null) {
			beanToEntity.setModifiedOn(new Timestamp(System.currentTimeMillis()));
		}
		SkillCompetencyEntity save = competencyRepository.save(beanToEntity);
		SkillCompetencyBean entityToBean = convertEntityToBean(save);
		return entityToBean;
	}

	@Override
	public SkillCompetencyBean findByUuid(String uuid) {
		SkillCompetencyEntity skillEntity = competencyRepository.findByUuid(uuid)
				.orElseThrow(() -> new IllegalArgumentException("Skill not found with id-" + uuid));
		return convertEntityToBean(skillEntity);

	}

	@Override
	public SkillCompetencyBean getBySkillId(int skillId) {
		SkillCompetencyEntity entity = competencyRepository.findBySkillId(skillId);

		return convertEntityToBean(entity);
	}

	@Override
	public List<SkillCompetencyBean> findByTechId(int technicalCatId) {
		List<SkillCompetencyEntity> entities = competencyRepository.getByTechId(technicalCatId);
		List<SkillCompetencyBean> list = new ArrayList<>();
		for (SkillCompetencyEntity entity : entities) {
			SkillCompetencyBean bean = convertEntityToBean(entity);
			list.add(bean);
		}
		return list;
	}


	@Override
	public PaginationDto getAllEmployeeSkillsBySkillIds(int[] skillId,int page ,int size) 
	{
		 Pageable pageable = PageRequest.of(page, size);
		 
		 List<EmployeesSkillsListDto> employeeSkillList = new ArrayList<EmployeesSkillsListDto>();
		
		Page<EmployeeEntityDto> findEmployeesBySkillId = employeeRepository.findEmployeesBySkillId(skillId, pageable);
		//page=0&size=10&sort=employeeId,asc
		System.out.println(findEmployeesBySkillId.getContent());
		for(EmployeeEntityDto employee:findEmployeesBySkillId.getContent())
		{
			 List<SkillsBean> findSkillsByEmployeeId = competencyRepository.findSkillsByEmployeeId(employee.getEmployeeId(),skillId);
			 System.out.println("employee id : "+findEmployeesBySkillId.getContent().get(0).getEmployeeId());
			 System.out.println(findSkillsByEmployeeId);
			 System.out.println("---------------------------");
			 
			 EmployeesSkillsListDto dto = new EmployeesSkillsListDto();
			 dto.setEmployeeName(employee.getFirstName()+" "+employee.getMiddleName()+" "+employee.getLastName());
			 dto.setEmployeeId(employee.getEmployeeId());
			 dto.setEmployeeCode(employee.getEmployeeCode());
			 dto.setDesignition(employee.getDesignition());
			 dto.setEmail(employee.getEmail());
			 dto.setSkillLists(findSkillsByEmployeeId);
			 
			 employeeSkillList.add(dto);
	
		}
		PaginationDto paginationDto=PaginationDto.builder()
		.pageNo(page)
		.pageSize(size)
		.first(findEmployeesBySkillId.isFirst())
		.last(findEmployeesBySkillId.isLast())
		.totalRecords(findEmployeesBySkillId.getTotalElements())
		.totalPages(findEmployeesBySkillId.getTotalPages())
		.skillList(employeeSkillList)
		.build();
		
		return paginationDto;
		
	}
	
}

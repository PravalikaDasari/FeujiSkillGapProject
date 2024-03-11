package com.feuji.employeeskillservice.service;

import java.util.List;

import com.feuji.employeeskillservice.bean.EmployeeBean;
import com.feuji.employeeskillservice.bean.EmployeeSkillBean;
import com.feuji.employeeskillservice.bean.EmployeeSkillGet;
import com.feuji.employeeskillservice.exception.NoRecordFoundException;

public interface EmployeeSkillService 
{
	EmployeeSkillBean saveEmployeeSkill(EmployeeSkillBean bean);

	public List<EmployeeSkillBean> getEmployeeSkillById(Long employeeId) throws NoRecordFoundException;
	
	public EmployeeSkillBean updateEmployeeSkill(EmployeeSkillBean employeeSkillBean) ;

	public List<EmployeeSkillBean> saveAll(List<EmployeeSkillBean> beanList);

	public List<EmployeeSkillBean> findBySkillId(int skillId);

	EmployeeBean getEmployeeByEmail(String email);
	
	public List<EmployeeSkillGet> getAllEmployeeSkills(String email) throws NoRecordFoundException ;

	String updateDeleteStatus(Long employeeSkillId) throws Exception;

	EmployeeSkillBean update(EmployeeSkillGet set,Long id);

}

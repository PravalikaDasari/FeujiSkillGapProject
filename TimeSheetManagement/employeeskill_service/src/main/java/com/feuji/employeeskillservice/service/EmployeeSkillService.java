package com.feuji.employeeskillservice.service;

import java.util.List;

import com.feuji.employeeskillservice.bean.EmployeeBean;
import com.feuji.employeeskillservice.bean.EmployeeSkillBean;
import com.feuji.employeeskillservice.bean.EmployeeSkillGet;
import com.feuji.employeeskillservice.bean.EmployeeUiBean;
import com.feuji.employeeskillservice.exception.NoRecordFoundException;

public interface EmployeeSkillService {
	EmployeeSkillBean saveEmployeeSkill(EmployeeSkillBean bean) throws Exception;

	public List<EmployeeSkillBean> getEmployeeSkillById(Long employeeId) throws NoRecordFoundException;

	public EmployeeSkillBean updateEmployeeSkill(EmployeeSkillBean employeeSkillBean) throws Exception;

	public List<EmployeeSkillBean> saveAll(List<EmployeeSkillBean> beanList);

	public List<EmployeeSkillBean> findBySkillId(int skillId) throws NoRecordFoundException;

	EmployeeBean getEmployeeByEmail(String email) throws Exception;

	public List<EmployeeSkillGet> getAllEmployeeSkills(String email) throws NoRecordFoundException, Exception;

	String updateDeleteStatus(Long employeeSkillId) throws Exception;

	EmployeeSkillBean updateEmployeeSkill(EmployeeSkillGet set, Long id) throws Exception;

	List<EmployeeSkillBean> convertUiBeanToSkillBean(List<EmployeeUiBean> employeeUiBeans)
			throws IllegalArgumentException;

}

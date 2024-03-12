package com.feuji.employeeskillservice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.feuji.employeeskillservice.bean.EmployeeSkillBean;
import com.feuji.employeeskillservice.bean.EmployeeSkillGet;
import com.feuji.employeeskillservice.bean.EmployeeUiBean;
import com.feuji.employeeskillservice.controller.EmployeeSkillController;
import com.feuji.employeeskillservice.exception.NoRecordFoundException;
import com.feuji.employeeskillservice.service.EmployeeSkillService;

public class EmployeeSkillControllerTest {

	@Mock
	private EmployeeSkillService employeeSkillService;

	@InjectMocks
	private EmployeeSkillController employeeSkillController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	// Successful case for saveEmployeeSkillBean
	@Test
	public void testSaveEmployeeSkillBean_Success() throws IllegalArgumentException, NoRecordFoundException {
		List<EmployeeUiBean> employeeUiBeans = new ArrayList<>();
		List<EmployeeSkillBean> savedEmployeeSkillBeansList = new ArrayList<>();

		when(employeeSkillService.convertUiBeanToSkillBean(employeeUiBeans)).thenReturn(savedEmployeeSkillBeansList);
		when(employeeSkillService.saveAll(savedEmployeeSkillBeansList)).thenReturn(savedEmployeeSkillBeansList);

		ResponseEntity<List<EmployeeSkillBean>> responseEntity = employeeSkillController
				.saveEmployeeSkillBean(employeeUiBeans);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(savedEmployeeSkillBeansList, responseEntity.getBody());
	}

	// Failure case for saveEmployeeSkillBean
	@Test
	public void testSaveEmployeeSkillBean_Failure() throws IllegalArgumentException, NoRecordFoundException {
		List<EmployeeUiBean> employeeUiBeans = new ArrayList<>();

		when(employeeSkillService.convertUiBeanToSkillBean(employeeUiBeans))
				.thenThrow(new IllegalArgumentException("failed to save records"));

		assertThrows(IllegalArgumentException.class,
				() -> employeeSkillController.saveEmployeeSkillBean(employeeUiBeans));
	}

	@Test
	public void testUpdateEmployeeSkill_Success() throws Exception {
		Long employeeSkillId = 1L;
		EmployeeSkillGet set = new EmployeeSkillGet();
		EmployeeSkillBean updatedEmployeeSkillBean = new EmployeeSkillBean();

		when(employeeSkillService.updateEmployeeSkill(set, employeeSkillId)).thenReturn(updatedEmployeeSkillBean);

		ResponseEntity<EmployeeSkillBean> responseEntity = employeeSkillController.updateEmployeeSkill(set,
				employeeSkillId);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(updatedEmployeeSkillBean, responseEntity.getBody());
	}

	// Failure case for updateEmployeeSkill
	@Test
	public void testUpdateEmployeeSkill_Failure() throws Exception {
		Long employeeSkillId = 1L;
		EmployeeSkillGet set = new EmployeeSkillGet();

		when(employeeSkillService.updateEmployeeSkill(set, employeeSkillId))
				.thenThrow(new Exception("failed to update record with id: " + employeeSkillId));

		assertThrows(Exception.class, () -> employeeSkillController.updateEmployeeSkill(set, employeeSkillId));
	}
}

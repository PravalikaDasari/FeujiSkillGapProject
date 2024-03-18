package com.feuji.referenceservice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.feuji.referenceservice.bean.TechnicalSkillsBean;
import com.feuji.referenceservice.controller.CommonReferenceDetailsController;
import com.feuji.referenceservice.exception.CategoryNotFoundException;
import com.feuji.referenceservice.exception.ReferenceNotFoundException;
import com.feuji.referenceservice.exception.TechnicalSkillsNotFoundException;
import com.feuji.referenceservice.service.CommonReferenceDetailsService;

public class CommonReferenceDetailsControllerTest {

	@Mock
	private CommonReferenceDetailsService commonReferenceDetailsService;

	@InjectMocks
	private CommonReferenceDetailsController commonReferenceDetailsController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * 
	 * Test case for successfully retrieving a list of technical skills beans by
	 * type ID. This test case mocks the service method to return a list of skills
	 * beans and then verifies that the controller method returns the correct HTTP
	 * status code and the expected body.
	 */
	@Test
	public void testGetReferenceTypeByName_Success() {
		String typeName = "typeName";
		List<TechnicalSkillsBean> skills = Arrays.asList(new TechnicalSkillsBean(1, "Skill1"),
				new TechnicalSkillsBean(2, "Skill2"));

		when(commonReferenceDetailsService.getDetailsByTypeId(typeName)).thenReturn(skills);

		ResponseEntity<List<TechnicalSkillsBean>> responseEntity = commonReferenceDetailsController
				.getReferenceTypeByName(typeName);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(skills, responseEntity.getBody());
		verify(commonReferenceDetailsService, times(1)).getDetailsByTypeId(typeName);
	}

	/**
	 * Test case to verify the behavior of getReferenceTypeByName method in
	 * CommonReferenceDetailsController when TechnicalSkillsNotFoundException is
	 * thrown by the service.
	 */
	@Test
	public void testGetReferenceTypeByName_TechnicalSkillsNotFoundException() {
		String typeName = "typeName";

		when(commonReferenceDetailsService.getDetailsByTypeId(typeName))
				.thenThrow(new TechnicalSkillsNotFoundException("Skills not found"));

		ResponseEntity<List<TechnicalSkillsBean>> responseEntity = commonReferenceDetailsController
				.getReferenceTypeByName(typeName);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		if (responseEntity.getBody() != null) {
			assertTrue(responseEntity.getBody().isEmpty());
		}
		verify(commonReferenceDetailsService, times(1)).getDetailsByTypeId(typeName);
	}

	/**
	 * Test case to verify the behavior of getCategories method in
	 * CommonReferenceDetailsController when sub-categories are successfully
	 * retrieved for a given category.
	 */
	@Test
	public void testGetCategories_Success() {
		String category = "someCategory";
		List<String> subCategories = Arrays.asList("SubCategory1", "SubCategory2");

		when(commonReferenceDetailsService.getCategories(category)).thenReturn(subCategories);

		ResponseEntity<List<String>> responseEntity = commonReferenceDetailsController.getCategories(category);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(subCategories, responseEntity.getBody());
		verify(commonReferenceDetailsService, times(1)).getCategories(category);
	}

	/**
	 * Test case to verify the behavior of getCategories method in
	 * CommonReferenceDetailsController when a CategoryNotFoundException is thrown
	 * for a non-existing category.
	 */
	@Test
	public void testGetCategories_CategoryNotFoundException() {
		String category = "nonExistingCategory";

		when(commonReferenceDetailsService.getCategories(category))
				.thenThrow(new CategoryNotFoundException("Category not found"));

		ResponseEntity<List<String>> responseEntity = commonReferenceDetailsController.getCategories(category);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertTrue(responseEntity.getBody().isEmpty());
		verify(commonReferenceDetailsService, times(1)).getCategories(category);
	}

	/**
	 * Test case to verify the behavior of getByName method in
	 * CommonReferenceDetailsController when it successfully retrieves an identifier
	 * by name.
	 */
	@Test
	public void testGetByName_Success() {
		String name = "testName";
		int id = 123;

		when(commonReferenceDetailsService.getIdByName(name)).thenReturn(id);

		ResponseEntity<String> responseEntity = commonReferenceDetailsController.getByName(name);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(String.valueOf(id), responseEntity.getBody());
		verify(commonReferenceDetailsService, times(1)).getIdByName(name);
	}

	/**
	 * Test case to verify the behavior of getByName method in
	 * CommonReferenceDetailsController when it encounters a NullPointerException
	 * while trying to retrieve an identifier by name.
	 */
	@Test
	public void testGetByName_NullPointerException() {
		String name = "testName";

		when(commonReferenceDetailsService.getIdByName(name)).thenThrow(new NullPointerException());

		ResponseEntity<String> responseEntity = commonReferenceDetailsController.getByName(name);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNull(responseEntity.getBody());
		verify(commonReferenceDetailsService, times(1)).getIdByName(name);
	}


	/**
	 * Test case to verify the behavior of getById method in
	 * CommonReferenceDetailsController when it successfully retrieves a name by its
	 * corresponding identifier.
	 */
	@Test
	public void testGetById_Success() {
		int id = 123;
		String name = "testName";

		when(commonReferenceDetailsService.getByid(id)).thenReturn(name);

		ResponseEntity<String> responseEntity = commonReferenceDetailsController.getById(id);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(name, responseEntity.getBody());
		verify(commonReferenceDetailsService, times(1)).getByid(id);
	}

	/**
	 * Test case to verify the behavior of getById method in
	 * CommonReferenceDetailsController when a ReferenceNotFoundException is thrown.
	 */
	@Test
	public void testGetById_ReferenceNotFoundException() {
		int id = 123;

		when(commonReferenceDetailsService.getByid(id))
				.thenThrow(new ReferenceNotFoundException("Reference not found"));

		ResponseEntity<String> responseEntity = commonReferenceDetailsController.getById(id);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNull(responseEntity.getBody());
		verify(commonReferenceDetailsService, times(1)).getByid(id);
	}
}

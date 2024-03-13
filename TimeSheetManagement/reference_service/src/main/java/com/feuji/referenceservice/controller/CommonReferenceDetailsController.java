package com.feuji.referenceservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.referenceservice.bean.TechnicalSkillsBean;
import com.feuji.referenceservice.exception.CategoryNotFoundException;
import com.feuji.referenceservice.exception.ReferenceNotFoundException;
import com.feuji.referenceservice.exception.TechnicalSkillsNotFoundException;
import com.feuji.referenceservice.service.CommonReferenceDetailsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/referencedetails")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class CommonReferenceDetailsController {

	@Autowired
	CommonReferenceDetailsService commonReferenceDetailsServiceService;

	/**
	 * Retrieves a list of technical skills beans based on the specified type name.
	 *
	 * @param typeName The name of the type to retrieve technical skills for.
	 * @return ResponseEntity<List<TechnicalSkillsBean>> A ResponseEntity containing the list of TechnicalSkillsBean objects.
	 *         If successful, HttpStatus.OK is returned with the list of technical skills beans.
	 *         If the specified technical skills are not found, HttpStatus.NOT_FOUND is returned with an empty list.
	 */
	@GetMapping("/getref/{typeName}")
	public ResponseEntity<List<TechnicalSkillsBean>> getReferenceTypeByName(@PathVariable String typeName) {
		log.info("getReferenceTypeByName start");
		List<TechnicalSkillsBean> getbyreferenceType = null;
		try {
			getbyreferenceType = commonReferenceDetailsServiceService.getDetailsByTypeId(typeName);
			log.info("getReferenceTypeByName end");
			return new ResponseEntity<>(getbyreferenceType, HttpStatus.OK);
		} catch (TechnicalSkillsNotFoundException e) {
			log.info(e.getMessage());
			return new ResponseEntity<>(getbyreferenceType, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Retrieves a list of categories based on the provided category.
	 * 
	 * This method is used to fetch sub-categories for the given category.
	 * 
	 * @param category The category for which to retrieve sub-categories.
	 * @return ResponseEntity<List<String>> A ResponseEntity containing the list of categories if found, 
	 *         or an empty list if no categories are found for the provided category.
	 *         Returns HttpStatus.OK if categories are found, HttpStatus.NOT_FOUND if the provided category is not found.
	 * @throws CategoryNotFoundException If the provided category is not found.
	 */
	@GetMapping("/getCategories/{category}")
	public ResponseEntity<List<String>> getCategories(@PathVariable String category) {
		log.info("getCategories start");
		List<String> categories = null;
		try {
			categories = commonReferenceDetailsServiceService.getCategories(category);
			log.info("getCategories end");
			return new ResponseEntity<>(categories, HttpStatus.OK);
		} catch (CategoryNotFoundException e) {
			log.info(e.getMessage());
			return new ResponseEntity<>(categories, HttpStatus.NOT_FOUND);
		}

	}
	
	
	/**
	 * Retrieves an identifier by the provided name.
	 * 
	 * This method is used to fetch an identifier associated with the provided name.
	 * 
	 * @param name The name for which to retrieve an identifier.
	 * @return ResponseEntity<String> A ResponseEntity containing the identifier if found, 
	 *         or null if no identifier is found for the provided name.
	 *         Returns HttpStatus.OK if an identifier is found, HttpStatus.NOT_FOUND if the provided name is not found.
	 * @throws NullPointerException If the identifier retrieved by the provided name is null.
	 */
	@GetMapping("/getByName/{name}")
	public ResponseEntity<String> getByName(@PathVariable String name) {
		log.info("controller-getByName() started");
		String result = null;
		try {
			int idByName = commonReferenceDetailsServiceService.getIdByName(name);
			result = idByName + "";
			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (NullPointerException e) {
			log.info(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}

	}

	
	/**
	 * Retrieves a name by the provided identifier.
	 * 
	 * This method is used to fetch a name associated with the provided identifier.
	 * 
	 * @param id The identifier for which to retrieve a name.
	 * @return ResponseEntity<String> A ResponseEntity containing the name if found, 
	 *         or null if no name is found for the provided identifier.
	 *         Returns HttpStatus.OK if a name is found, HttpStatus.NOT_FOUND if the provided identifier is not found.
	 * @throws ReferenceNotFoundException If the name retrieved by the provided identifier is not found.
	 */
	@GetMapping("/getById/{id}")
	public ResponseEntity<String> getById(@PathVariable int id) {
		log.info("getById() started");
		String name = null;
		try {
			name = commonReferenceDetailsServiceService.getByid(id);
			log.info("getById() ended");
			return new ResponseEntity<String>(name, HttpStatus.OK);
		} catch (ReferenceNotFoundException e) {
			log.info(e.getMessage());
			return new ResponseEntity<String>(name, HttpStatus.NOT_FOUND);

		}
	}

}
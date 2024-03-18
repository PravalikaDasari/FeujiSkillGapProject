package com.feuji.referenceservice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.feuji.referenceservice.bean.CommonReferenceTypeBean;
import com.feuji.referenceservice.controller.CommonReferenceTypeController;
import com.feuji.referenceservice.entity.CommonReferenceTypeEntity;
import com.feuji.referenceservice.exception.ReferenceNotFoundException;
import com.feuji.referenceservice.service.CommonReferenceTypeService;

public class CommonReferenceTypeTest {

	@Mock
    private CommonReferenceTypeService commonReferenceTypeService;

    @InjectMocks
    private CommonReferenceTypeController commonReferenceTypeController;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case to verify the behavior of saveTimesheetWeek method in CommonReferenceTypeController
     * when it successfully saves a CommonReferenceTypeBean.
     */
    @Test
    public void testSaveTimesheetWeek_Success() {
        CommonReferenceTypeBean commonReferenceTypeBean = new CommonReferenceTypeBean();
        commonReferenceTypeBean.setName("Test");

        CommonReferenceTypeEntity savedEntity = new CommonReferenceTypeEntity();
        savedEntity.setReferenceTypeId(1L);
        savedEntity.setReferenceTypeName("Test");

        when(commonReferenceTypeService.saveTimesheetWeek(commonReferenceTypeBean)).thenReturn(savedEntity);

        ResponseEntity<CommonReferenceTypeEntity> responseEntity = commonReferenceTypeController.saveTimesheetWeek(commonReferenceTypeBean);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(savedEntity, responseEntity.getBody());
        verify(commonReferenceTypeService, times(1)).saveTimesheetWeek(commonReferenceTypeBean);
    }

    /**
     * Test case to verify the behavior of saveTimesheetWeek method in CommonReferenceTypeController
     * when it throws a ReferenceNotFoundException while attempting to save a CommonReferenceTypeBean.
     */
  @Test
    public void testSaveTimesheetWeek_ReferenceNotFoundException() {
        CommonReferenceTypeBean commonReferenceTypeBean = new CommonReferenceTypeBean();
        commonReferenceTypeBean.setName("Test");

        when(commonReferenceTypeService.saveTimesheetWeek(commonReferenceTypeBean)).thenThrow(new ReferenceNotFoundException("Reference not found"));

        ResponseEntity<CommonReferenceTypeEntity> responseEntity = commonReferenceTypeController.saveTimesheetWeek(commonReferenceTypeBean);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(commonReferenceTypeService, times(1)).saveTimesheetWeek(commonReferenceTypeBean);
    }
    
  /**
   * Test case to verify the behavior of getReferenceTypeByName method in CommonReferenceTypeController
   * when it successfully retrieves a CommonReferenceTypeEntity by type name.
   */
    @Test
    public void testGetReferenceTypeByName_Success() {
        String typeName = "Test";
        CommonReferenceTypeEntity commonReferenceTypeEntity = new CommonReferenceTypeEntity();
        commonReferenceTypeEntity.setReferenceTypeId(1L);
        commonReferenceTypeEntity.setReferenceTypeName(typeName);

        when(commonReferenceTypeService.getByTypeName(typeName)).thenReturn(commonReferenceTypeEntity);

        ResponseEntity<CommonReferenceTypeEntity> responseEntity = commonReferenceTypeController.getReferenceTypeByName(typeName);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(commonReferenceTypeEntity, responseEntity.getBody());
        verify(commonReferenceTypeService, times(1)).getByTypeName(typeName);
    }

    
    /**
     * Test case to verify the behavior of getReferenceTypeByName method in CommonReferenceTypeController
     * when it encounters a ReferenceNotFoundException while trying to retrieve a CommonReferenceTypeEntity by type name.
     */
    @Test
    public void testGetReferenceTypeByName_ReferenceNotFoundException() {
        String typeName = "NonExistingType";

        when(commonReferenceTypeService.getByTypeName(typeName)).thenThrow(new ReferenceNotFoundException("Reference not found"));

        ResponseEntity<CommonReferenceTypeEntity> responseEntity = commonReferenceTypeController.getReferenceTypeByName(typeName);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(commonReferenceTypeService, times(1)).getByTypeName(typeName);
    }
}

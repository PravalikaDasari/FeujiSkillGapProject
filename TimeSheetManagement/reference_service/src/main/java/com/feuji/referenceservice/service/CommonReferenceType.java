package com.feuji.referenceservice.service;

import com.feuji.referenceservice.bean.CommonReferenceTypeBean;
import com.feuji.referenceservice.entity.CommonReferenceTypeEntity;

public interface CommonReferenceType {
	
	public CommonReferenceTypeEntity getByTypeName(String typeName);
	
	//public CommonReferenceTypeEntity save(CommonReferenceTypeEntity commonReferenceTypeEntity);

	public CommonReferenceTypeEntity save(CommonReferenceTypeBean commonReferenceTypeBean);

}

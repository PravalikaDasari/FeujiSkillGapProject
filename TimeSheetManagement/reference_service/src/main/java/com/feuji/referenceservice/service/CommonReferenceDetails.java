package com.feuji.referenceservice.service;

import java.util.List;

import com.feuji.referenceservice.bean.TechnicalSkillsBean;

public interface CommonReferenceDetails {
//	public List<String> getbyreferenceType(CommonReferenceTypeBean referenceTypeId);

	public List<TechnicalSkillsBean> getDetailsByTypeId(String typeName);

	public int getIdByName(String name);

	public List<String> getCategories(String category);

	public String getByid(int id);
}

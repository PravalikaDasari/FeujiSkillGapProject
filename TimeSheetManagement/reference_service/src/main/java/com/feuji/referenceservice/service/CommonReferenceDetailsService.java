package com.feuji.referenceservice.service;

import java.util.List;

import com.feuji.referenceservice.bean.TechnicalSkillsBean;

public interface CommonReferenceDetailsService {

	public List<TechnicalSkillsBean> getDetailsByTypeId(String typeName);

	public List<String> getCategories(String category);

	public int getIdByName(String name);

	public String getByid(int id);
}

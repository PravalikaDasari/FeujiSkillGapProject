package com.feuji.referenceservice.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.referenceservice.bean.TechnicalSkillsBean;
import com.feuji.referenceservice.repository.CommonReferenceDetailsRepo;
import com.feuji.referenceservice.service.CommonReferenceDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommonReferenceDetailsImpl implements CommonReferenceDetails {

	@Autowired
	CommonReferenceDetailsRepo commonReferenceDetailsRepo;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<TechnicalSkillsBean> getDetailsByTypeId(String typeName) {
		log.info("result" + typeName);

		List<String> detailsByTypeName = commonReferenceDetailsRepo.getDetailsByTypeName(typeName);
		log.info("result" + detailsByTypeName);

		List<TechnicalSkillsBean> list = new ArrayList<>();
		for (String item : detailsByTypeName) {
			TechnicalSkillsBean bean = new TechnicalSkillsBean();
			String[] split = item.split(",");
			bean.setReferenceDetailValue(split[0]);
			bean.setReferenceDetailId(Integer.parseInt(split[1]));
			list.add(bean);
		}
		return list;
	}

	@Override
	public int getIdByName(String name) {
		log.info("service-getByName() started");
		int id = commonReferenceDetailsRepo.getByName(name);
		log.info("service-getByName() ended");
		return id;
	}

	@Override
	public List<String> getCategories(String category) {
		List<String> list = commonReferenceDetailsRepo.getCategories(category);
		return list;
	}

	@Override
	public String getByid(int id) {
		log.info("service impl id= " + id);
		String nameById = commonReferenceDetailsRepo.getNameById(id);
		log.info(nameById + " service imppl");
		return nameById;
	}

}

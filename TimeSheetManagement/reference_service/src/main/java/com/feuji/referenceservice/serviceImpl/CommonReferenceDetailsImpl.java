package com.feuji.referenceservice.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.referenceservice.bean.TechnicalSkillsBean;
import com.feuji.referenceservice.constants.CommonConstants;
import com.feuji.referenceservice.exception.CategoryNotFoundException;
import com.feuji.referenceservice.exception.ReferenceNotFoundException;
import com.feuji.referenceservice.exception.TechnicalSkillsNotFoundException;
import com.feuji.referenceservice.repository.CommonReferenceDetailsRepo;
import com.feuji.referenceservice.service.CommonReferenceDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommonReferenceDetailsImpl implements CommonReferenceDetailsService {

	@Autowired
	CommonReferenceDetailsRepo commonReferenceDetailsRepo;

	@Override
	public List<TechnicalSkillsBean> getDetailsByTypeId(String typeName) {
		log.info("getDetailsByTypeId start");
		List<String> detailsByTypeName = commonReferenceDetailsRepo.getDetailsByTypeName(typeName);
		if (detailsByTypeName != null) {
			List<TechnicalSkillsBean> list = new ArrayList<>();
			for (String item : detailsByTypeName) {
				TechnicalSkillsBean bean = new TechnicalSkillsBean();
				String[] split = item.split(",");
				bean.setReferenceDetailValue(split[CommonConstants.FALSE]);
				bean.setReferenceDetailId(Integer.parseInt(split[CommonConstants.TRUE]));
				list.add(bean);
			}
			log.info("getDetailsByTypeId end");
			return list;
		} else {
			throw new TechnicalSkillsNotFoundException("no record found with type name: " + typeName);
		}
	}

	@Override
	public List<String> getCategories(String categoryName) {
		log.info("getCategories() start");
		List<String> categoryList = commonReferenceDetailsRepo.getCategories(categoryName);
		if (categoryList != null) {
			log.info("getCategories() end");
			return categoryList;
		} else {
			throw new CategoryNotFoundException("no categories found with this " + categoryName);
		}

	}

	@Override
	public int getIdByName(String name) {
		log.info("getByName() started");
		if (name != null) {
			int id = commonReferenceDetailsRepo.getByName(name);
			log.info("getByName() ended");
			return id;
		} else {
			throw new NullPointerException("name is null");
		}

	}

	@Override
	public String getByid(int id) {
		log.info("getByid() started");
		String nameById = commonReferenceDetailsRepo.getNameById(id);
		if (nameById != null) {
			log.info("getByid() ended");
			return nameById;
		} else {
			throw new ReferenceNotFoundException("no suitable name found for this id: " + id);
		}
	}
}

package com.feuji.referenceservice.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feuji.referenceservice.bean.CommonReferenceTypeBean;
import com.feuji.referenceservice.entity.CommonReferenceTypeEntity;
import com.feuji.referenceservice.repository.CommonReferenceTypeRepo;
import com.feuji.referenceservice.service.CommonReferenceType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommonReferenceTypeImpl implements CommonReferenceType {

//	private static Logger log = LoggerFactory.getLogger(CommonReferenceTypeImpl.class);

	@Autowired
	CommonReferenceTypeRepo commonReferenceTypeRepo;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public CommonReferenceTypeEntity getByTypeName(String typeName) {
		log.info(typeName);
		CommonReferenceTypeEntity byTypeName = commonReferenceTypeRepo.getByTypeName(typeName);
		log.info("getting reference", byTypeName);
		return byTypeName;
	}

	@Override
	public CommonReferenceTypeEntity save(CommonReferenceTypeBean commonReferenceTypeBean) {
		CommonReferenceTypeEntity commonReferenceTypeEntity = modelMapper.map(commonReferenceTypeBean,
				CommonReferenceTypeEntity.class);
		log.info("saving timesheet entity " + commonReferenceTypeEntity);
		commonReferenceTypeEntity = commonReferenceTypeRepo.save(commonReferenceTypeEntity);
		return commonReferenceTypeEntity;
	}

}

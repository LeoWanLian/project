package com.leowan.pss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leowan.pss.domain.SystemDictionaryDetail;
import com.leowan.pss.domain.SystemDictionaryType;
import com.leowan.pss.repository.SystemDictionaryDetailRepository;
import com.leowan.pss.service.ISystemDictionaryDetailService;

@Service
public class SystemDictionaryDetailServiceImpl extends BaseServiceImpl<SystemDictionaryDetail, Long> implements ISystemDictionaryDetailService {
	@Autowired
	SystemDictionaryDetailRepository systemDictionaryDetailRepository;

	String jpql= "select o from SystemDictionaryDetail o where o.types.sn=?";
	@Override
	public List<SystemDictionaryDetail> getBrands() {
		return findByJpql(jpql, SystemDictionaryType.PRODUCT_BRAND);
	}

	@Override
	public List<SystemDictionaryDetail> getUnits() {
		return findByJpql(jpql, SystemDictionaryType.PRODUCT_UNIT);
	}

}

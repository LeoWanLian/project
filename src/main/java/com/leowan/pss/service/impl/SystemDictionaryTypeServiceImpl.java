package com.leowan.pss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leowan.pss.domain.SystemDictionaryType;
import com.leowan.pss.repository.SystemDictionaryTypeRepository;
import com.leowan.pss.service.ISystemDictionaryTypeService;

@Service
public class SystemDictionaryTypeServiceImpl extends BaseServiceImpl<SystemDictionaryType, Long> implements ISystemDictionaryTypeService {
	@Autowired
	SystemDictionaryTypeRepository systemDictionaryTypeRepository;

}

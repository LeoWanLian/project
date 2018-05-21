package com.leowan.pss.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.leowan.pss.repository.SystemDictionaryTypeRepository;

public class SystemDictionaryTypeServiceTest extends BaseServiceTest{
	@Autowired
	ISystemDictionaryTypeService systemDictionaryTypeService;
	
	@Test
	public void test() {
		System.out.println(systemDictionaryTypeService);
		System.out.println(systemDictionaryTypeService.getClass());
		

		// 父接口提供的方法
		System.out.println(systemDictionaryTypeService.getAll().size());

	}

}

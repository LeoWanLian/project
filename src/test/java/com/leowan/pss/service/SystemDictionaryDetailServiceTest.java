package com.leowan.pss.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.leowan.pss.repository.SystemDictionaryDetailRepository;

public class SystemDictionaryDetailServiceTest extends BaseServiceTest{
	@Autowired
	ISystemDictionaryDetailService systemDictionaryDetailService;
	
	@Test
	public void test() {
		System.out.println(systemDictionaryDetailService);
		System.out.println(systemDictionaryDetailService.getClass());
		

		// 父接口提供的方法
		System.out.println(systemDictionaryDetailService.getAll().size());

	}

}

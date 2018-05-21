package com.leowan.pss.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.leowan.pss.repository.${entityDomain}Repository;

public class ${entityDomain}ServiceTest extends BaseServiceTest{
	@Autowired
	I${entityDomain}Service ${lowerEntityDomain}Service;
	
	@Test
	public void test() {
		System.out.println(${lowerEntityDomain}Service);
		System.out.println(${lowerEntityDomain}Service.getClass());
		

		// 父接口提供的方法
		System.out.println(${lowerEntityDomain}Service.getAll().size());

	}

}

package com.leowan.pss.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.leowan.pss.repository.ProductTypeRepository;

public class ProductTypeServiceTest extends BaseServiceTest{
	@Autowired
	IProductTypeService productTypeService;
	
	@Test
	public void test() {
		System.out.println(productTypeService);
		System.out.println(productTypeService.getClass());
		

		// 父接口提供的方法
		System.out.println(productTypeService.getAll().size());

	}

}

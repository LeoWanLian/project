package com.leowan.pss.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.leowan.pss.repository.ProductRepository;

public class ProductServiceTest extends BaseServiceTest{
	@Autowired
	IProductService productService;
	
	@Test
	public void test() {
		System.out.println(productService);
		System.out.println(productService.getClass());
		

		// 父接口提供的方法
		System.out.println(productService.getAll().size());

	}

}

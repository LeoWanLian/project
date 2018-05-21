package com.leowan.pss.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.leowan.pss.repository.ProductStockRepository;

public class ProductStockServiceTest extends BaseServiceTest{
	@Autowired
	IProductStockService productStockService;
	
	@Test
	public void test() {
		System.out.println(productStockService);
		System.out.println(productStockService.getClass());
		

		// 父接口提供的方法
		System.out.println(productStockService.getAll().size());

	}

}

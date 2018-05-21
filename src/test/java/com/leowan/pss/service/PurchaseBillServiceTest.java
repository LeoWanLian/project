package com.leowan.pss.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.leowan.pss.repository.PurchaseBillRepository;

public class PurchaseBillServiceTest extends BaseServiceTest{
	@Autowired
	IPurchaseBillService purchaseBillService;
	
	@Test
	public void test() {
		System.out.println(purchaseBillService);
		System.out.println(purchaseBillService.getClass());
		

		// 父接口提供的方法
		System.out.println(purchaseBillService.getAll().size());

	}

}

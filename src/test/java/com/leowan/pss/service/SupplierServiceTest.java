package com.leowan.pss.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.leowan.pss.repository.SupplierRepository;

public class SupplierServiceTest extends BaseServiceTest{
	@Autowired
	ISupplierService supplierService;
	
	@Test
	public void test() {
		System.out.println(supplierService);
		System.out.println(supplierService.getClass());
		

		// 父接口提供的方法
		System.out.println(supplierService.getAll().size());

	}

}

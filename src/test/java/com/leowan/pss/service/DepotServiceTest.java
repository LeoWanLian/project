package com.leowan.pss.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.leowan.pss.repository.DepotRepository;

public class DepotServiceTest extends BaseServiceTest{
	@Autowired
	IDepotService depotService;
	
	@Test
	public void test() {
		System.out.println(depotService);
		System.out.println(depotService.getClass());
		

		// 父接口提供的方法
		System.out.println(depotService.getAll().size());

	}

}

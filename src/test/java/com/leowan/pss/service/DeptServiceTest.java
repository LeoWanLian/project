package com.leowan.pss.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.leowan.pss.repository.DeptRepository;

public class DeptServiceTest extends BaseServiceTest{
	@Autowired
	IDeptService deptService;
	
	@Test
	public void test() {
		System.out.println(deptService);
		System.out.println(deptService.getClass());
		

		// 父接口提供的方法
		System.out.println(deptService.getAll().size());

	}

}

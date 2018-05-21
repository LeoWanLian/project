package com.leowan.pss.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.leowan.pss.repository.DepartmentRepository;

public class DepartmentServiceTest extends BaseServiceTest{
	
	@Autowired
	IDepartmentService departmentService;
	
	@Test
	public void test() {
		System.out.println(departmentService);
		System.out.println(departmentService.getClass());
		

		// 父接口提供的方法
		System.out.println(departmentService.getAll().size());

	}

}

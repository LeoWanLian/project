package com.leowan.pss.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.leowan.pss.repository.RoleRepository;

public class RoleServiceTest extends BaseServiceTest{
	@Autowired
	IRoleService roleService;
	
	@Test
	public void test() {
		System.out.println(roleService);
		System.out.println(roleService.getClass());
		

		// 父接口提供的方法
		System.out.println(roleService.getAll().size());

	}

}

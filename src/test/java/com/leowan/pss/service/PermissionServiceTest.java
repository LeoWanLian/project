package com.leowan.pss.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.leowan.pss.repository.PermissionRepository;

public class PermissionServiceTest extends BaseServiceTest{
	@Autowired
	IPermissionService permissionService;
	
	@Test
	public void test() {
		System.out.println(permissionService);
		System.out.println(permissionService.getClass());
		

		// 父接口提供的方法
		System.out.println(permissionService.getAll().size());

	}

}

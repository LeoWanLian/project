package com.leowan.pss.service;

import java.util.List;

import com.leowan.pss.domain.Permission;

/**
 * service层的公共父接口
 * 
 * @author LeoWan
 *
 * @param <T>
 *            domain
 * @param <ID>
 *            主键
 */
public interface IPermissionService extends IBaseService<Permission, Long> {
	// 获取所有的私有资源，在permission表里面的method定义的就是私有资源
	List<String> getAllMethods();

	// 判断当前登录用户是否具有访问该私有资源的权限
	List<String> findMethodsByLoginUserId(Long loginUserId);
}

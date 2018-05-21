package com.leowan.pss.service;

import java.util.List;

import com.leowan.pss.domain.Employee;

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
public interface IEmployeeService extends IBaseService<Employee, Long> {
	boolean findByUsername(String username);

	// 按照名称和密码查询
	Employee findByUsernameAndPassword(String username, String password);
	
	List<Employee> getBuyers();
}

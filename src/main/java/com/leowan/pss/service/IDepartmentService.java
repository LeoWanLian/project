package com.leowan.pss.service;

import com.leowan.pss.domain.Department;
import com.leowan.pss.domain.Employee;

/**
 * service层的公共父接口
 * @author LeoWan
 *
 * @param <T> domain
 * @param <ID> 主键
 */
public interface IDepartmentService extends IBaseService<Department, Long> {
	Department findByName(String name);
}

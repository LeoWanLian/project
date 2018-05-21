package com.leowan.pss.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.leowan.pss.domain.Department;
import com.leowan.pss.service.IDepartmentService;

@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department, Long> implements IDepartmentService {

	@Override
	public Department findByName(String name) {
		String hql = "select o from Department o where o.name=?";
		List<Department> list = findByJpql(hql, name);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}

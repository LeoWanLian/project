package com.leowan.pss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leowan.pss.domain.Employee;
import com.leowan.pss.repository.EmployeeRepository;
import com.leowan.pss.service.IEmployeeService;

@Service
public class EmployeeServiceImpl extends BaseServiceImpl<Employee, Long> implements IEmployeeService {
	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	// 如果查询为空,说明此用户名没有被使用.返回前台为true
	public boolean findByUsername(String username) {
		return employeeRepository.findByUsername(username) == null;
	}

	@Override
	public Employee findByUsernameAndPassword(String username, String password) {
		return employeeRepository.findByUsernameAndPassword(username, password);
	}

	@Override
	public List<Employee> getBuyers() {
		return employeeRepository.findByDepartmentName("采购部");
	}

}

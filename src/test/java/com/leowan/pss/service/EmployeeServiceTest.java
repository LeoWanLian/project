package com.leowan.pss.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.leowan.pss.domain.Employee;
import com.leowan.pss.query.EmployeeQuery;
import com.leowan.pss.query.PageList;
import com.leowan.pss.repository.EmployeeRepository;

public class EmployeeServiceTest extends BaseServiceTest{
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	IEmployeeService employeeService;
	
	@Test
	public void test() {
		//查询一条记录
//		Employee employee = employeeRepository.findOne(1L);
//		System.out.println(employee);
//		System.out.println(employeeRepository);//repository.support.SimpleJpaRepository@3fa76c61
//		System.out.println(employeeRepository.getClass());
		

		// 父接口提供的方法
//		System.out.println(employeeRepository.findAll().size());

		// 自己接口提供的方法
		System.out.println(employeeRepository.findByUsername("admin1"));
//		System.out.println(employeeRepository.findByUsernameAndPassword("admin", "admin"));
		
//		System.out.println(employeeRepository.findByDepartmentName("IT部").size());
		
		// 自己写的父接口提供的方法
//		System.out.println(employeeRepository.findByJpql("select o from Employee o where o.username=?", "admin"));
	}

	@Test
	public void test1() {
		System.out.println(employeeService);
		System.out.println(employeeService.getAll().size());
		System.out.println(employeeService.getClass());
	}
	
	@Test
	public void findByQuery() {
		EmployeeQuery query = new EmployeeQuery();
		query.setUsername("admin1");
		query.setEmail("a");
		
//		// 1 传递正常的当前页与每页条数
//		query.setCurrentPage(2);
//		query.setPageSize(20);
//		// 2 传递当前页与每页条数为负数的情况
//		query.setCurrentPage(-5);
//		query.setPageSize(-20);
//		// 3 传递当前页大于总的页数的情况
//		query.setCurrentPage(20);
		
		
		PageList<Employee> findByQuery = employeeService.findByQuery(query);
		System.out.println(findByQuery);
	}
}

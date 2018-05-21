package com.leowan.pss.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.leowan.pss.domain.Employee;
import com.leowan.pss.query.EmployeeQuery;
import com.leowan.pss.query.PageList;
import com.leowan.pss.service.IEmployeeService;

/**
 * 员工列表的aciton
 * 
 * @author LeoWan
 *
 */
@Controller
@Scope("prototype")
public class EmployeeAction2 extends BaseAction {

	@Autowired
	private IEmployeeService employeeService;

	// 又struts2管理baseQuery需要提供getter/setter方法
	private EmployeeQuery baseQuery = new EmployeeQuery();

	// 需要提供getter方法
	private PageList pageList;

	private Employee employee;

	@Override
	public String execute() throws Exception {
		pageList = employeeService.findByQuery(baseQuery);
		// System.out.println("------------" + pageList.getCurrentPage());
		return SUCCESS;
	}

	// 跳转到input页面
	@Override
	public String input() throws Exception {
		if (employee != null && employee.getId() != null) {
			this.employee = employeeService.get(employee.getId());
		}
		return INPUT;
	}

	// 修改保存的方法,由spring data jpa来判断是update还是save
	public String save() throws Exception {
		/*
		 * 解决数据丢失的第二种方法
		 * 把数据库里的值拿出来,给它设置前台的username和email,密码不设置,还是原来的,就不会丢失了
		 */
		if (employee.getId() != null) {
			//数据库里的值
			Employee tempEmployee = employeeService.get(employee.getId());
			// 需要修改的值就从页面里面的employee放入tempEmployee
			tempEmployee.setUsername(employee.getUsername());
			tempEmployee.setEmail(employee.getEmail());
			employeeService.save(tempEmployee);
		} else {
			employeeService.save(employee);
		}
		return RELOAD;// 在BaseAction中定义一个常量字段的结果视图,配置重定向提交到展示列表. 防止重复提交
	}

	// 删除的方法
	public String delete() throws Exception {
		if (employee != null && employee.getId() != null) {
			employeeService.delete(employee.getId());
		}
		return RELOAD;// 在BaseAction中定义一个常量字段的结果视图,配置重定向提交到展示列表. 防止重复提交
	}

	public EmployeeQuery getBaseQuery() {
		return baseQuery;
	}

	public void setBaseQuery(EmployeeQuery baseQuery) {
		this.baseQuery = baseQuery;
	}

	public PageList getPageList() {
		return pageList;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}

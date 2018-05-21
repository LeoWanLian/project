package com.leowan.pss.web.action;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.leowan.pss.domain.Employee;
import com.leowan.pss.service.IEmployeeService;
import com.leowan.pss.service.IMenuService;

/**
 * 后台主页的action
 * 
 * @author LeoWan
 *
 */
@Controller
@Scope("prototype")
public class Main2Action extends BaseAction {
	@Autowired
	IMenuService menuService;
	@Autowired
	IEmployeeService employeeService;

	Employee employee = (Employee) ServletActionContext.getRequest().getSession().getAttribute(USER_IN_SESSION);

	@Override
	public String execute() throws Exception {
		// putContext("menus",
		// menuService.findMenusByLoginUserId(employee.getId()));
		return SUCCESS;
	}

	public String left() throws Exception {
		putContext("map", menuService.findMenusByLoginUserId(employee.getId()));

		return "json";
	}

}

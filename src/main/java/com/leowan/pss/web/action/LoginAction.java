package com.leowan.pss.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.leowan.pss.domain.Employee;
import com.leowan.pss.service.IEmployeeService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

@Controller
@Scope("prototype")
public class LoginAction extends BaseAction {
	@Autowired
	private IEmployeeService employeeService;

	private String username;
	private String password;

	@Override
	public String execute() throws Exception {

		return LOGIN;
	}

	// 验证用户名非空
	public void validateCheck() {
		if (StringUtils.isBlank(username)) {
			addFieldError("username", "用户名必须填写");
		}
	}

	/**
	 * 检查用户名是否匹配的方法
	 * 
	 * @return
	 * @throws Exception
	 */
	// 处理登录请求
	@InputConfig(resultName = "login")
	public String check() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();

		// 调用service层的匹配用户名密码的方法,
		Employee employee = employeeService.findByUsernameAndPassword(username, password);
		if (employee != null) {
			req.getSession().setAttribute(USER_IN_SESSION, employee);
			return "main";
		}
		addActionError("登录失败");
		return LOGIN;
	}

	/**
	 * 注销session的方法
	 * 
	 * @return
	 */
	public String logout() {
		ServletActionContext.getRequest().getSession().removeAttribute(USER_IN_SESSION);

		return LOGIN;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

package com.leowan.pss.web.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.leowan.pss.domain.Employee;
import com.leowan.pss.service.IEmployeeService;
import com.leowan.pss.service.IMenuService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 后台主页的action
 * 
 * @author LeoWan
 *
 */
@Controller
@Scope("prototype")
public class MainAction extends BaseAction {
	@Autowired
	IMenuService menuService;
	@Autowired
	IEmployeeService employeeService;

	private String oldPwd;
	private String newPwd;

	Employee employee = (Employee) ServletActionContext.getRequest().getSession().getAttribute(USER_IN_SESSION);

	@Override
	public String execute() throws Exception {
		putContext("menus", menuService.findMenusByLoginUserId(employee.getId()));
		return SUCCESS;
	}

	public String right() throws Exception {
		return "right";
	}

	public String changePwd() throws Exception {
		// 获取页面输出流,输出页面需要的json格式
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		System.out.println(oldPwd);
		System.out.println("数据库" + employee.getPassword());
		System.out.println(employee.getPassword().equals(oldPwd)+"---------");
		if (employee != null && !employee.getPassword().equals(oldPwd)) {
			System.out.println("密码错误");
			out.print("{\"success\":false,\"msg\":\"旧密码输入错误\"}");
		} else {
			try {
				employee.setPassword(newPwd);
				employeeService.save(employee);
				// 拼接完整的Json格式
				out.print("{\"success\":true,\"msg\":\"修改成功,跳转到登录页面\"}");
			} catch (Exception e) {
				e.printStackTrace();
				// 拼接完整的Json格式 - 并把错误信息打印到前台
				out.print("{\"success\":false,\"msg\":\"" + e.getMessage() + "\"}");
			}

		}
		return null;// 返回给json的,不需要视图
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

}

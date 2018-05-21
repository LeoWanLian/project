package com.leowan.pss.web.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.leowan.pss.domain.Department;
import com.leowan.pss.query.DepartmentQuery;
import com.leowan.pss.service.IDepartmentService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

/**
 * 员工列表的aciton
 * 
 * @author LeoWan
 *
 */
@Controller
@Scope("prototype")
public class DepartmentAction extends CRUDAction<Department> {

	@Autowired
	private IDepartmentService departmentService;

	// 由struts2管理baseQuery需要提供getter/setter方法
	private DepartmentQuery baseQuery = new DepartmentQuery();

	private Department department;

	/*
	 * @Override public String execute() throws Exception {
	 * System.out.println("execute"); pageList =
	 * departmentService.findByQuery(baseQuery); //
	 * System.out.println("------------" + pageList.getCurrentPage()); return
	 * SUCCESS; }
	 */

	// 代替了execute方法. 因为父类里面有execute方法.里面会调用list方法.
	@Override
	protected void list() throws Exception {
		pageList = departmentService.findByQuery(baseQuery);
		// putContext("allDeps", departmentService.getAll());
		// System.out.println(pageList.getData());
	}

	// 跳转到input页面
	@Override
	public String input() throws Exception {
		// System.out.println("input");
		// putContext("allDeps", departmentService.getAll());
		return INPUT;
	}

	// 修改保存的方法,由spring data jpa来判断是update还是save
	// 出现转换异常或者验证异常，跳转到input方法
	@InputConfig(methodName = INPUT)
	public String save() throws Exception {
		if (id == null) {
			// 新增后的保存自动跳转到最后一页
			baseQuery.setCurrentPage(Integer.MAX_VALUE);
		}

		departmentService.save(department);
		return RELOAD;// 在BaseAction中定义一个常量字段的结果视图,配置重定向提交到展示列表. 防止重复提交
	}

	// ajax删除的方法
	// {"success":true,"msg":"删除成功"}
	// {"success":false,"msg":"删除失败"}
	public String delete() throws Exception {
		// 获取页面输出流,输出页面需要的json格式
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			// 如果删除成功,返回json 为true
			if (id != null) {
				departmentService.delete(id);
				// 拼接完整的Json格式
				out.print("{\"success\":true,\"msg\":\"删除成功\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 拼接完整的Json格式 - 并把错误信息打印到前台
			out.print("{\"success\":false,\"msg\":\"" + e.getMessage() + "\"}");
		}
		return null;// 返回给json的,不需要视图
	}

	// 会自动在struts访问方法之前执行
	@Override
	public void prepare() throws Exception {
		// System.out.println("prepare");
	}

	public void prepareSave() throws Exception {

		// 如果id为空,那么就实例化
		if (id == null) {
			this.department = new Department();// 压栈
		} else {
			// 如果id不为空,就从数据库里面查询department,那么数据就不会丢失
			this.department = departmentService.get(id);
			// 处理修改部门名称的问题
			// jpa中不能修改持久状态对象的主键. 需要将其设为null再由spring new出来
			// department.setDepartment(null);
		}
	}

	// 回显
	public void prepareInput() throws Exception {
		// System.out.println("prepareInput");
		if (id != null) {
			this.department = departmentService.get(id); // 通过获取到的id查询回显.department就到栈顶了
			// System.out.println(department);
		}
	}

	public DepartmentQuery getBaseQuery() {
		return baseQuery;
	}

	public void setBaseQuery(DepartmentQuery baseQuery) {
		this.baseQuery = baseQuery;
	}

	@Override
	public Department getModel() {
		return department;
	}
}

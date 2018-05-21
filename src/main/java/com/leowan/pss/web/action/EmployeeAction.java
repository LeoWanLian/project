package com.leowan.pss.web.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.leowan.pss.domain.Department;
import com.leowan.pss.domain.Employee;
import com.leowan.pss.query.EmployeeQuery;
import com.leowan.pss.query.PageList;
import com.leowan.pss.service.IDepartmentService;
import com.leowan.pss.service.IEmployeeService;
import com.leowan.pss.service.IRoleService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

/**
 * 员工列表的aciton
 * 
 * @author LeoWan
 *
 */
@Controller
@Scope("prototype")
public class EmployeeAction extends CRUDAction<Employee> {

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private IDepartmentService departmentService;

	@Autowired
	private IRoleService roleService;

	private Long[] ids;

	// 由struts2管理baseQuery需要提供getter/setter方法
	private EmployeeQuery baseQuery = new EmployeeQuery();

	private Employee employee;

	/*
	 * @Override public String execute() throws Exception {
	 * System.out.println("execute"); pageList =
	 * employeeService.findByQuery(baseQuery); //
	 * System.out.println("------------" + pageList.getCurrentPage()); return
	 * SUCCESS; }
	 */

	// 代替了execute方法. 因为父类里面有execute方法.里面会调用list方法.
	@Override
	protected void list() throws Exception {
		pageList = employeeService.findByQuery(baseQuery);
		putContext("allDeps", departmentService.getAll());
		// System.out.println(pageList.getData());
	}

	// 跳转到input页面
	@Override
	public String input() throws Exception {
		System.out.println("input");
		putContext("allDeps", departmentService.getAll());
		// input方法:添加角色列表
		putContext("allRoles", roleService.getAll());
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

		// 选择部门id==-1 选择的是--请选择--
		Department department = employee.getDepartment();
		if (department != null && department.getId() == -1L) {
			employee.setDepartment(null);
		}
		employeeService.save(employee);
		System.out.println("----------------------save");
		return RELOAD;// 在BaseAction中定义一个常量字段的结果视图,配置重定向提交到展示列表. 防止重复提交
	}

	// 普通删除的方法
	// public String delete() throws Exception {
	// // System.out.println("delete");
	// if (id != null) {
	// employeeService.delete(id);
	// }
	// return RELOAD;// 在BaseAction中定义一个常量字段的结果视图,配置重定向提交到展示列表. 防止重复提交
	// }

	// ajax删除的方法
	// {"success":true,"msg":"删除成功"}
	// {"success":false,"msg":"删除失败"}
	public String delete() throws Exception {
		// 获取页面输出流,输出页面需要的json格式
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			// 如果删除成功,返回json 为true
			if (id != null) {
				employeeService.delete(id);
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
		System.out.println("prepare");
	}

	public void prepareSave() throws Exception {
		System.out.println("prepare save");
		// 如果id为空,那么就实例化
		if (id == null) {
			this.employee = new Employee();// 压栈
		} else {
			// 如果id不为空,就从数据库里面查询employee,那么数据就不会丢失
			this.employee = employeeService.get(id);
			// 处理修改部门名称的问题
			// jpa中不能修改持久状态对象的主键. 需要将其设为null再由spring new出来
			employee.setDepartment(null);
			// prepareSave方法：集合清空
			employee.getRoles().clear();
		}
	}

	// 回显
	public void prepareInput() throws Exception {
		// System.out.println("prepareInput");
		if (id != null) {
			this.employee = employeeService.get(id); // 通过获取到的id查询回显.employee就到栈顶了
			// System.out.println(employee);
		}
	}

	// 对所有方法进行验证
	// @Override
	// public void validate() {
	// super.validate();
	// }
	/*
	 * 对save方法进行验证.提交的时候用户名不能为空
	 */
	public void validateSave() throws Exception {
		// 用户名没有输入
		if (StringUtils.isBlank(employee.getUsername())) {
			addFieldError("username", "用户名不能为空");
		}
	}

	// ajax 验证用户名是否重复
	/*
	 * {\"valid\":true} 表示验证通过 {\"valid\":false} 表示验证不通过，表示用户名已经存在
	 */
	public String checkName() throws Exception {
		// 获取页面输出流,输出页面需要的json格式
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		if (id == null) {
			// 如果id为空,则是新增.查询此用户名是否已经存在
			out.print("{\"valid\":" + employeeService.findByUsername(username) + "}");// 返回的Json
		} else {
			// 如果是修改,通过id查询到员工姓名
			Employee oldEmployee = employeeService.get(id);
			// 将查询到的姓名和输入的用户名匹配.
			if (oldEmployee.getUsername().equals(username)) {
				// 如果用户名没有改变,直接返回true
				out.print("{\"valid\":true}");//
			} else {
				// 如果用户名更改,就去检查用户名是否已经存在.
				out.print("{\"valid\":" + employeeService.findByUsername(username) + "}");// 返回的Json
			}
		}
		return NONE;
	}

	/**
	 * 下载文件的方法
	 * 
	 * @return
	 * @throws Exception
	 */
	private InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}

	String[] heads = { "编号", "用户名", "邮箱", "年龄", "部门" };

	public String download() throws Exception {
		baseQuery.setPageSize(Integer.MAX_VALUE);
		pageList = employeeService.findByQuery(baseQuery);
		// 获取到没有分页效果的数据
		List<Employee> data = pageList.getData();
		// System.out.println(data.size());
		List<String[]> list = new ArrayList<String[]>();
		if (data != null && data.size() != 0) {
			for (Employee employee : data) {
				String[] strings = new String[heads.length];
				strings[0] = employee.getId().toString();
				strings[1] = employee.getUsername();
				strings[2] = employee.getEmail();
				strings[3] = employee.getAge() == null ? "" : employee.getAge().toString();
				Department department = employee.getDepartment();
				strings[4] = department == null ? "" : department.getName();
				list.add(strings);

			}
		}
		this.inputStream = employeeService.download(list, heads);
		return "download";
	}

	public String deleteAll() throws Exception {
		System.out.println("xxxxxxxxxxxxxx" + Arrays.toString(ids));
		for (Long id : ids) {
			employeeService.delete(id);
		}
		return RELOAD;
	}

	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	@Override
	public Employee getModel() {
		return employee;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

}

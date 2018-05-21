package com.leowan.pss.web.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.leowan.pss.domain.Employee;
import com.leowan.pss.query.EmployeeQuery;
import com.leowan.pss.service.IDepartmentService;
import com.leowan.pss.service.IEmployeeService;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

@Controller
@Scope("prototype")
public class DatagridAction extends BaseAction implements ModelDriven<Employee>, Preparable {

	@Autowired
	private IEmployeeService employeeService;
	@Autowired
	private IDepartmentService departmentService;

	// 由struts2管理baseQuery需要提供getter/setter方法
	private EmployeeQuery baseQuery = new EmployeeQuery();

	private Long id;
	private Employee employee;
	// 匹配EasyUI的参数
	private int page;
	private int rows;

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String json() throws Exception {
		baseQuery.setCurrentPage(page);
		baseQuery.setPageSize(rows);
		putContext("map", employeeService.findByQuery(baseQuery));
//		System.out.println(baseQuery);
		return "json";
	}

	public String dept() throws Exception {
		putContext("map", departmentService.getAll());
		return "json";
	}

	public void prepareSave() throws Exception {
		// 如果id为空,那么就实例化
		if (id == null) {
			this.employee = new Employee();// 压栈
		} else {
			// 如果id不为空,就从数据库里面查询employee,那么数据就不会丢失
			this.employee = employeeService.get(id);
			employee.setDepartment(null);
		}
	}

	public String save() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {// 如果保存成功
			employeeService.save(employee);
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			// 拼接完整的Json格式 - 并把错误信息打印到前台
			map.put("success", false);
			map.put("msg", "删除失败" + e.getMessage());
		}
		putContext("map", map);

		return "json";// 不能返回结果视图,返回json格式的数据,即为上面的map
	}

	// 删除
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
			out.print("{\"success\":false,\"msg\":\"删除失败" + e.getMessage() + "\"}");
		}
		return null;// 返回给json的,不需要视图
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EmployeeQuery getBaseQuery() {
		return baseQuery;
	}

	public void setBaseQuery(EmployeeQuery baseQuery) {
		this.baseQuery = baseQuery;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Employee getModel() {
		return employee;
	}

}

package com.leowan.pss.web.action;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.leowan.pss.domain.Employee;
import com.leowan.pss.service.IDepartmentService;
import com.leowan.pss.service.IEmployeeService;

/**
 * 导入文件的action
 * 
 * @author LeoWan
 *
 */
@Controller
@Scope("prototype")
public class ImportAction extends BaseAction {

	private String contentType;
	private File upload;// 上传文件的名称，下面叫前缀
	private String fileName;

	@Autowired
	private IEmployeeService employeeService;
	@Autowired
	private IDepartmentService departmentService;

	// 既显示导入页面也处理导入的请求
	@Override
	public String execute() throws Exception {
		// 有上传xlsx
		if (upload != null) {
			// 把解析后的list变成一个个的Employee对象插入数据库
			List<String[]> list = employeeService.importXlsx(upload);
			for (String[] strings : list) {
				// strings==编号 用户名 邮箱 年龄 部门名称
				// 把strings变成Employee对象
				Employee employee = new Employee();
				// strings[0]不要，因为是编号的值
				// 希望用户名不重复,价格随机字符串
				employee.setUsername(strings[1] + UUID.randomUUID().toString().substring(0, 6));
				// 添加固定密码
				employee.setPassword("123456");
				employee.setEmail(strings[2]);
				employee.setAge(strings[3] == null ? null : Integer.parseInt(strings[3]));
				String deptName = strings[4];
				employee.setDepartment(departmentService.findByName(deptName));
				// 持久化到数据库
				employeeService.save(employee);
			}
			// 添加一个提示信息
			putContext("count", "导入" + list.size() + "条数据成功");
			
		}
		return SUCCESS;

	}

	public String getUploadFileName() {
		return fileName;
	}

	public void setUploadFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUploadContentType() {
		return contentType;
	}

	public void setUploadContentType(String contentType) {
		this.contentType = contentType;
	}

	// since we are using <s:file name="upload" ... /> the File itself will be
	// obtained through getter/setter of <file-tag-name>
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}
}

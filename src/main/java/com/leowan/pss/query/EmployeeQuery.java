package com.leowan.pss.query;

import org.apache.commons.lang.StringUtils;

import com.leowan.pss.domain.Employee;

/**
 * 处理子类的高级查询条件 ,涉及到domain类的字段
 *
 */
public class EmployeeQuery extends BaseQuery {
	// 查询条件为username
	private String username;
	// 查询条件为email
	private String email;
	// 查询条件为部门名称
	private Long deptId;

	// 年龄滑块核心初始化值
	private String age = "18,130";

	// 年龄滑块回显核心代码
	public String[] getAges() {
		if (StringUtils.isNotBlank(age)) {
			String[] strings = age.split(",");
			if (strings != null && strings.length == 2) {
				return strings;
			}
		}
		return new String[2];
	}

	// 通过子类的构造方法调用父类的构造方法,将entityClass实体传给父类,父类就能够拼接jpql
	public EmployeeQuery() {
		super(Employee.class);
	}

	@Override
	protected void addCondition() {
		if (StringUtils.isNotBlank(username)) {
			addWhere(" o.username like ? ", "%" + username + "%");
		}
		if (StringUtils.isNotBlank(email)) {
			addWhere(" o.email like ? ", "%" + email + "%");
		}
		if (deptId != null && deptId != -1L) {
			addWhere(" o.department.id=? ", deptId);
		}
		if (StringUtils.isNotBlank(age)) {
			String[] strings = age.split(",");
			if (strings != null && strings.length == 2) {
				addWhere("o.age between ? and ?", Integer.parseInt(strings[0]), Integer.parseInt(strings[1]));
			}
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "EmployeeQuery [username=" + username + ", email=" + email + ", deptId=" + deptId + "]";
	}

}

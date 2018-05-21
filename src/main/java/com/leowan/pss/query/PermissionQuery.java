package com.leowan.pss.query;

import org.apache.commons.lang.StringUtils;

import com.leowan.pss.domain.Permission;

/**
 * 处理子类的高级查询条件 ,涉及到domain类的字段
 *
 */
public class PermissionQuery extends BaseQuery {
	// 查询条件为rname
	private String name;
	// 增加的查询条件为method
	private String method;

	// 通过子类的构造方法调用父类的构造方法,将entityClass实体传给父类,父类就能够拼接jpql
	public PermissionQuery() {
		super(Permission.class);
	}

	@Override
	protected void addCondition() {
		if (StringUtils.isNotBlank(name)) {
			addWhere(" o.name like ? ", "%" + name.trim() + "%");
		}
		if (StringUtils.isNotBlank(method)) {
			addWhere(" o.method like ? ", "%" + method.trim() + "%");
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}

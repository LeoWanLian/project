package com.leowan.pss.query;

import org.apache.commons.lang.StringUtils;

import com.leowan.pss.domain.Role;

/**
 * 处理子类的高级查询条件 ,涉及到domain类的字段
 *
 */
public class RoleQuery extends BaseQuery{
	// 查询条件为username
	private String name;

	// 通过子类的构造方法调用父类的构造方法,将entityClass实体传给父类,父类就能够拼接jpql
	public RoleQuery() {
		super(Role.class);
	}

	@Override
	protected void addCondition() {
		if (StringUtils.isNotBlank(name)) {
			addWhere(" o.name like ? ", "%" + name + "%");
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

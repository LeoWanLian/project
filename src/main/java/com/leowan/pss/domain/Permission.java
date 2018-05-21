package com.leowan.pss.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 权限的domain类
 *
 */
@Entity
@Table(name = "permission")
public class Permission extends BaseDomain {
	private String name;
	// 控制的是action的方法
	// 规定1：action的类名+.+方法名称
	// 规定2：action的类名+.ALL 所有方法
	private String method;
	// 关于权限方法的描述
	@Column(name = "descs")
	private String desc;

	public Permission(Long pid) {
		this.id = pid;
	}
	
	public Permission(){
		
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}

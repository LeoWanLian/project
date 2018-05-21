package com.leowan.pss.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
// 在JPA里面就表示是父类，不持久化到表
public class BaseDomain {

	@Id
	@GeneratedValue
	// 子类可以直接获取id字段
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}

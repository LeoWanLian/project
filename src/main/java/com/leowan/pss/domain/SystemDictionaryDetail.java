package com.leowan.pss.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * 数据字典明细：存放具体domain的数据
 * 
 */
@Entity
@Table(name = "systemdictionarydetail")
public class SystemDictionaryDetail extends BaseDomain {
	private String name;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "types_id")
	private SystemDictionaryType types;

	public SystemDictionaryDetail() {

	}

	public SystemDictionaryDetail(Long id) {
		super();
		this.id = id;
	}

	public SystemDictionaryDetail(String name, SystemDictionaryType types) {
		super();
		this.name = name;
		this.types = types;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SystemDictionaryType getTypes() {
		return types;
	}

	public void setTypes(SystemDictionaryType types) {
		this.types = types;
	}

}

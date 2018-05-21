package com.leowan.pss.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * 数据字典类型：有多少个类似domain(id,name)就有几行数据
 * 
 */
@Entity
@Table(name = "systemdictionarytype")
public class SystemDictionaryType extends BaseDomain {
	// 定义2个常量：系统初始化的时候
	public static final String PRODUCT_BRAND = "productBrand";// 产品品牌
	public static final String PRODUCT_UNIT = "productUnit";// 产品单位

	private String sn;// 唯一,不能修改
	private String name;// domain的名称

	public SystemDictionaryType() {

	}

	public SystemDictionaryType(Long id) {
		super();
		this.id = id;
	}

	public SystemDictionaryType(String sn, String name) {
		super();
		this.sn = sn;
		this.name = name;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

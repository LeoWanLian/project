package com.leowan.pss.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;
/**
 * 产品类型
 *
 */
@Entity
@Table(name = "producttype")
public class ProductType extends BaseDomain {
	private String name;
	private String descs;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private ProductType parent;

	public ProductType() {

	}

	public ProductType(Long id) {
		super();
		this.id = id;
	}

	public ProductType(String name, ProductType parent) {
		super();
		this.name = name;
		this.descs = name;
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JSON(serialize = false)
	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}

	@JSON(serialize = false)
	public ProductType getParent() {
		return parent;
	}

	public void setParent(ProductType parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "ProductType [name=" + name + ", descs=" + descs + ", parent=" + parent + "]";
	}
	
	
}

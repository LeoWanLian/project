package com.leowan.pss.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "depot")
public class Depot extends BaseDomain {
	private String name;
	private BigDecimal maxCapacity;// 最大数量，参考值
	private BigDecimal currentCapacity;// 当前数量，参考值
	private BigDecimal totalAmount;// 当前仓库里面的产品值多少钱

	public Depot(Long id) {
		this.id = id;
	}

	public Depot() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(BigDecimal maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public BigDecimal getCurrentCapacity() {
		return currentCapacity;
	}

	public void setCurrentCapacity(BigDecimal currentCapacity) {
		this.currentCapacity = currentCapacity;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

}

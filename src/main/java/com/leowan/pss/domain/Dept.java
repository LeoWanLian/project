package com.leowan.pss.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dept")
public class Dept extends BaseDomain {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

package com.leowan.pss.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;

@Entity
@Table(name = "menu")
public class Menu extends BaseDomain {
	private String name;
	private String url;
	private String icon;

	// 自关联也是单向多对一
	@JoinColumn(name = "parent_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Menu parent;
	// 添加一个属性,装二级菜单,但是此属性不持久化，使用的List，需要有序
	// @Transient配置JPA此字段不进行持久化
	@Transient
	// @OneToMany(mappedBy = "parent")为什么不配置双向一对多，因为拥有一级菜单，不希望默认拥有所有的二级菜单
	private List<Menu> children = new ArrayList<Menu>();
	
	@JSON(name="text")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	@JSON(name="iconCls")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@JSON(serialize = false)
	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ",name=" + name + ", url=" + url + ", icon=" + icon + "]";
	}

}

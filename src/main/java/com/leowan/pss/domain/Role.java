package com.leowan.pss.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 角色的domain类
 *
 */
@Entity
@Table(name = "role")
@Cacheable(true)
public class Role extends BaseDomain {
	private String name;
	// 当前角色拥有的资源列表
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "permission_id")
	@JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
	private Set<Permission> permissions = new HashSet<Permission>();

	// 单向多对多:当前角色拥有的菜单列表
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "role_menu", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "menu_id"))
	private Set<Menu> menus = new HashSet<Menu>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return name;
	}

	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

}

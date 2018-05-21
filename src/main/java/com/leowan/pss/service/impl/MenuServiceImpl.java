package com.leowan.pss.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leowan.pss.domain.Menu;
import com.leowan.pss.repository.MenuRepository;
import com.leowan.pss.service.IMenuService;

@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, Long> implements IMenuService {

	@Override
	public List<Menu> findMenusByLoginUserId(Long loginUserId) {
		String jpql = "select distinct m from Employee e join e.roles r join r.menus m where e.id = ?";
		List<Menu> list = findByJpql(jpql, loginUserId);
		// 定义一个一级菜单的list
		List<Menu> parents = new ArrayList<Menu>();

		for (Menu menu : list) {
			// 父菜单为空代表是一级菜单
			if (menu.getParent() == null) {
				parents.add(menu);
			}
		}

		for (Menu menu : list) {
			// 如果这个menu的parent为null那么说明是一级菜单,就放入到一级菜单集合中
			Menu parent = menu.getParent();
			if (parent != null) {
				// 遍历一级菜单
				for (Menu menu2 : parents) {
					// 如果父菜单相等
					if (parent.getId().equals(menu2.getId())) {
						menu2.getChildren().add(menu);
					}
				}
			}

		}

		return parents;
	}

}

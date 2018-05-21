package com.leowan.pss.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.leowan.pss.domain.Menu;
import com.leowan.pss.repository.MenuRepository;

public class MenuServiceTest extends BaseServiceTest {
	@Autowired
	IMenuService menuService;

	@Test
	public void test() {
		// System.out.println(menuService);
		// System.out.println(menuService.getClass());
		//
		// // 父接口提供的方法
		// System.out.println(menuService.getAll().size());
		List<Menu> list = menuService.findMenusByLoginUserId(1L);
		for (Menu menu : list) {
			System.out.println("一级菜单=====" + menu);
			List<Menu> children = menu.getChildren();
			for (Menu menu2 : children) {
				System.out.println("二级" + menu2);
			}
			// System.out.println(menu);
		}
	}

}

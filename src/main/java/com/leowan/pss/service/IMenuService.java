package com.leowan.pss.service;

import java.util.List;

import com.leowan.pss.domain.Menu;

/**
 * service层的公共父接口
 * @author LeoWan
 *
 * @param <T> domain
 * @param <ID> 主键
 */
public interface IMenuService extends IBaseService<Menu, Long> {
	List<Menu> findMenusByLoginUserId(Long longinUserId);
}

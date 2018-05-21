package com.leowan.pss.service;

import java.util.List;

import com.leowan.pss.domain.ProductType;

/**
 * service层的公共父接口
 * @author LeoWan
 *
 * @param <T> domain
 * @param <ID> 主键
 */
public interface IProductTypeService extends IBaseService<ProductType, Long> {
	List<ProductType> findChildren(Long id);
	List<ProductType> getParents();
}

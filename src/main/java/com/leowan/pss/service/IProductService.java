package com.leowan.pss.service;

import com.leowan.pss.domain.Product;

/**
 * service层的公共父接口
 * @author LeoWan
 *
 * @param <T> domain
 * @param <ID> 主键
 */
public interface IProductService extends IBaseService<Product, Long> {

}

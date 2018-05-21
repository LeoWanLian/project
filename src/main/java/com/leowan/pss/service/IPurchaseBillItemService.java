package com.leowan.pss.service;

import java.util.List;

import com.leowan.pss.domain.PurchaseBillItem;
import com.leowan.pss.query.PurchaseBillItemQuery;

/**
 * service层的公共父接口
 * @author LeoWan
 *
 * @param <T> domain
 * @param <ID> 主键
 */
public interface IPurchaseBillItemService extends IBaseService<PurchaseBillItem, Long> {
	//分组查询,得到的是分组结果
	List<Object[]> findByGroupBy(PurchaseBillItemQuery itemQuery); 
	//在分组查询的基础上,上面的分组条件作为明细的查询条件.
	List<PurchaseBillItem> findItems(PurchaseBillItemQuery itemQuery,Object groupByValue);
	
	//查询饼图所需要的数据
	List<Object[]> findByGroupBy2(PurchaseBillItemQuery itemQuery);
	
}

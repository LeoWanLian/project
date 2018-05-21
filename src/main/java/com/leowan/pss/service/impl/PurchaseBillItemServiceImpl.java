package com.leowan.pss.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leowan.pss.domain.PurchaseBillItem;
import com.leowan.pss.query.PurchaseBillItemQuery;
import com.leowan.pss.repository.PurchaseBillItemRepository;
import com.leowan.pss.service.IPurchaseBillItemService;

@Service
public class PurchaseBillItemServiceImpl extends BaseServiceImpl<PurchaseBillItem, Long>
		implements IPurchaseBillItemService {
	@Autowired
	PurchaseBillItemRepository purchaseBillItemRepository;

	// 报表的分组查询
	@Override
	public List<Object[]> findByGroupBy(PurchaseBillItemQuery itemQuery) {
		// 获取到分组条件
		String groupBy = itemQuery.getGroupBy();
		// 获取到查询条件
		String where = itemQuery.getWhereJpql();
		// 根据有没有查询条件,拼接不同的jpql
		List paramList = itemQuery.getParamList();
		// 如果没有查询条件
		if (paramList.size() == 0) {
			String jpql = "select" + groupBy + ",count(o.id) from PurchaseBillItem o group by" + groupBy;
			return purchaseBillItemRepository.findByJpql(jpql);
		} else {// 如果有查询条件
			String jpql = "select " + groupBy + ",count(o.id) from PurchaseBillItem o " + where + "group by " + groupBy;
			return purchaseBillItemRepository.findByJpql(jpql, paramList.toArray());
		}
	}

	@Override
	public List<PurchaseBillItem> findItems(PurchaseBillItemQuery itemQuery, Object groupByValue) {
		// 获取到分组条件
		String groupBy = itemQuery.getGroupBy();
		// 获取到查询条件
		String where = itemQuery.getWhereJpql();
		// 根据有没有查询条件,拼接不同的jpql
		// parmList装的是时间,状态,分页等条件的
		List paramList = itemQuery.getParamList();
		// 如果没有查询条件
		if (paramList.size() == 0) {
			String jpql = "select o from PurchaseBillItem o where" + groupBy + "=?";
			return purchaseBillItemRepository.findByJpql(jpql, groupByValue);
		} else {
			String jpql = jpql = "select o from PurchaseBillItem o where " + groupBy + "=?"
					+ where.replaceAll("where", "and");
			List list = new ArrayList();
			list.add(groupByValue);
			list.addAll(paramList);
			return purchaseBillItemRepository.findByJpql(jpql, list.toArray());
		}
	}

	// 查询饼图所需要的数据 ,饼图需要按供应商来分,需要的是小计 就是amount的sum
	@Override
	public List<Object[]> findByGroupBy2(PurchaseBillItemQuery itemQuery) {
		// 获取到分组条件
		String groupBy = itemQuery.getGroupBy();
		// 获取到查询条件
		String where = itemQuery.getWhereJpql();
		// 根据有没有查询条件,拼接不同的jpql
		List paramList = itemQuery.getParamList();
		// 如果没有查询条件
		if (paramList.size() == 0) {
			String jpql = "select" + groupBy + ",sum(o.amount) from PurchaseBillItem o group by" + groupBy;
			return purchaseBillItemRepository.findByJpql(jpql);
		} else {// 如果有查询条件
			String jpql = "select " + groupBy + ",sum(o.amount) from PurchaseBillItem o " + where + "group by " + groupBy;
			return purchaseBillItemRepository.findByJpql(jpql, paramList.toArray());
		}
	}

}

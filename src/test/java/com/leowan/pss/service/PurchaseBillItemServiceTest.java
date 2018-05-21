package com.leowan.pss.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.leowan.pss.domain.PurchaseBillItem;
import com.leowan.pss.query.PurchaseBillItemQuery;

public class PurchaseBillItemServiceTest extends BaseServiceTest {
	@Autowired
	IPurchaseBillItemService purchaseBillItemService;

	// 第一条,没有查询条件,只有分组条件的查询
	@Test
	public void test() {
		// 按照供应商分组
		String groupBy = " o.bill.supplier.name ";
		// 按照采购员分组
		// String groupBy = " o.bill.buyer.username ";
		// 按照月份分组
		// String groupBy = " month(o.bill.vdate) ";
		// 通过订单类型→订单→供应商→供应商名字
		String jpql = "select" + groupBy + ",count(o.id) from PurchaseBillItem o group by" + groupBy;
		// 分组查询到供应商的订单
		List<Object[]> list = purchaseBillItemService.findByJpql(jpql);
		for (Object[] objects : list) {
			System.out.println(Arrays.toString(objects));
			jpql = "select o from PurchaseBillItem o where" + groupBy + "=?";
			// 不增加额外的查询条件,增加分组条件
			List<PurchaseBillItem> list2 = purchaseBillItemService.findByJpql(jpql, objects[0]);
			for (PurchaseBillItem purchaseBillItem : list2) {
				System.out.println(purchaseBillItem);
			}
			System.out.println("==================");
		}
	}

	/**
	 * 第二条有分组条件,而且增加额外的查询条件
	 */
	@Test
	public void test2() {
		// 按照供应商分组
		String groupBy = " o.bill.supplier.name ";
		// 按照采购员分组
		// String groupBy = " o.bill.buyer.username ";
		// 按照月份分组
		// String groupBy = " month(o.bill.vdate) ";
		// 通过订单类型→订单→供应商→供应商名字
		String where = " where o.bill.status=1 ";
		String jpql = "select " + groupBy + ",count(o.id) from PurchaseBillItem o " + where + "group by " + groupBy;
		// 增加了查询条件
		List<Object[]> list = purchaseBillItemService.findByJpql(jpql);
		for (Object[] objects : list) {
			System.out.println(Arrays.toString(objects));
			jpql = "select o from PurchaseBillItem o where " + groupBy + "=?" + where.replaceAll("where", "and");
			List<PurchaseBillItem> list2 = purchaseBillItemService.findByJpql(jpql, objects[0]);
			for (PurchaseBillItem purchaseBillItem : list2) {
				System.out.println(purchaseBillItem);
			}
			System.out.println("==================");
		}
	}

	@Test
	public void test3() {
		PurchaseBillItemQuery itemQuery = new PurchaseBillItemQuery();

//		itemQuery.setStatus(1);
		purchaseBillItemService.findByGroupBy(itemQuery);

		// // 按照供应商分组
		// String groupBy = " o.bill.supplier.name ";
		// 通过订单类型→订单→供应商→供应商名字
		// String where = " where o.bill.status=1 ";
		// 增加了查询条件
		List<Object[]> list = purchaseBillItemService.findByGroupBy(itemQuery);
		for (Object[] objects : list) {
			System.out.println(Arrays.toString(objects));
//			jpql = "select o from PurchaseBillItem o where " + groupBy + "=?" + where.replaceAll("where", "and");
			List<PurchaseBillItem> list2 = purchaseBillItemService.findItems(itemQuery, objects[0]);
			for (PurchaseBillItem purchaseBillItem : list2) {
				System.out.println(purchaseBillItem);
			}
			System.out.println(list2.size());
			System.out.println("==================");
		}
		System.out.println(list.size());
		
	}
}

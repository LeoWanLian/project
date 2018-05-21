package com.leowan.pss.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.leowan.pss.domain.Depot;
import com.leowan.pss.domain.Employee;
import com.leowan.pss.domain.Product;
import com.leowan.pss.domain.StockIncomeBill;
import com.leowan.pss.domain.StockIncomeBillItem;
import com.leowan.pss.domain.Supplier;
import com.leowan.pss.repository.StockIncomeBillRepository;

public class StockIncomeBillServiceTest extends BaseServiceTest {
	@Autowired
	IStockIncomeBillService stockIncomeBillService;

	@Test
	public void test() {
		// 创建一个订单
		StockIncomeBill bill = new StockIncomeBill();
		bill.setDepot(new Depot(1L));
		bill.setInputUser(new Employee(1L));
		bill.setKeeper(new Employee(2L));
		bill.setSupplier(new Supplier(1L));
		bill.setVdate(new Date());

		List<StockIncomeBillItem> items = new ArrayList<StockIncomeBillItem>();
		// 创建2个入库明细
		// 第一个入明细
		StockIncomeBillItem billItem1 = new StockIncomeBillItem();
		billItem1.setNum(new BigDecimal(2));
		billItem1.setPrice(new BigDecimal(2));
		billItem1.setProduct(new Product(1L));
		billItem1.setDescs("入库订单明细1");
		items.add(billItem1);

		// 第二个明细
		StockIncomeBillItem billItem2 = new StockIncomeBillItem();
		billItem2.setNum(new BigDecimal(2));
		billItem2.setPrice(new BigDecimal(2));
		billItem2.setProduct(new Product(2L));
		billItem2.setDescs("入库订单明细2");
		items.add(billItem2);

		// 设置订单里面的数量和小计
		BigDecimal totalAmount = new BigDecimal(0);// 总金额
		BigDecimal totalNum = new BigDecimal(0);// 总数量
		// 获取当前采购明细集合(从一方获取多方,已经建立关系)
		for (StockIncomeBillItem item : items) {
			// 设置多方到一方的关系
			// not-null property references a null or transient value:
			// cn.itsource.pss.domain.PurchaseBillItem.bill
			item.setBill(bill);
			// 计算小计
			item.setAmount(item.getPrice().multiply(item.getNum()));
			// 累加
			totalAmount = totalAmount.add(item.getAmount());
			totalNum = totalNum.add(item.getNum());
		}
		// 设置总金额,总数量
		bill.setTotalAmount(totalAmount);
		bill.setTotalNum(totalNum);

		bill.setItems(items);

		stockIncomeBillService.save(bill);

		// System.out.println(stockIncomeBillService);
		// System.out.println(stockIncomeBillService.getClass());
		// // 父接口提供的方法
		// System.out.println(stockIncomeBillService.getAll().size());
	}
	@Test
	public void test2() {
		Employee employee = new Employee(2L);
		stockIncomeBillService.auding(2L, employee);
	}

}

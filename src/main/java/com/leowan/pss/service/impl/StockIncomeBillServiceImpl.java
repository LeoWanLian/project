package com.leowan.pss.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leowan.pss.domain.Depot;
import com.leowan.pss.domain.Employee;
import com.leowan.pss.domain.ProductStock;
import com.leowan.pss.domain.StockIncomeBill;
import com.leowan.pss.domain.StockIncomeBillItem;
import com.leowan.pss.repository.StockIncomeBillRepository;
import com.leowan.pss.service.IDepotService;
import com.leowan.pss.service.IProductStockService;
import com.leowan.pss.service.IStockIncomeBillService;

@Service
public class StockIncomeBillServiceImpl extends BaseServiceImpl<StockIncomeBill, Long>
		implements IStockIncomeBillService {
	@Autowired
	StockIncomeBillRepository stockIncomeBillRepository;

	@Autowired
	IDepotService depotService;

	@Autowired
	IProductStockService productStockService;

	@Override
	@Transactional
	// 订单审核需要事务,所以配上Transactional注解
	public void auding(Long billId, Employee auditor) {
		// 1.判断入库单是否存在,是否可以审核等
		// 0待审,1已审，-1作废
		StockIncomeBill bill = get(billId);
		if (bill == null) {
			throw new RuntimeException("此入库单不存在");
		}
		if (bill.getStatus() == 1) {
			throw new RuntimeException("此入库单已经审核");
		}

		if (bill.getStatus() == -1) {
			throw new RuntimeException("此入库单已经作废");
		}
		// 2.修改状态，审核人，审核时间
		bill.setStatus(1);
		bill.setAuditor(auditor);
		bill.setVdate(new Date());
		// 修改完入库单属性之后保存, 这里的保存不写也可以,因为上面 get(billId);拿到的是持久对象,会有脏数据更新
		save(bill);

		// 3.在循环外面更新仓库总金额totalAmount、当前数量 currentCapacity
		Depot depot = bill.getDepot();
		// 先拿到depot仓库里面的金额和数量,然后添加这个订单里面的金额和数量
		// 新的数量
		BigDecimal depotlNum = depot.getCurrentCapacity().add(bill.getTotalNum());
		// 新的金额
		BigDecimal depotAmount = depot.getTotalAmount().add(bill.getTotalAmount());
		// 更新仓库的数量和金额
		depot.setCurrentCapacity(depotlNum);
		depot.setTotalAmount(depotAmount);
		depotService.save(depot);

		// 4.添加或者更新即时库存表ProductStock
		// 即时库存表里面的product和depot必须同时唯一
		// ProductStock里面需要更新num,amount,price,入库时间,产品的id,仓库id
		List<StockIncomeBillItem> items = bill.getItems();
		String jpql = "select o from ProductStock o where o.product=? and o.depot=?";
		for (StockIncomeBillItem billItem : items) {
			// 从明细里面拿到这个入库单里面的产品和仓库对应的及时库存
			List<ProductStock> list = findByJpql(jpql, billItem.getProduct(), bill.getDepot());
			// 判断,如果库存里面没有记录,那么就是新增库存
			if (list.size() == 0) {
				ProductStock productStock = new ProductStock();
				// 设置库存的数量
				productStock.setNum(billItem.getNum());
				// 设置库存的金额
				productStock.setAmount(billItem.getAmount());
				// 设置库存的价格
				productStock.setPrice(billItem.getPrice());
				// 设置入库时间
				productStock.setIncomeDate(new Date());
				// 设置产品
				productStock.setProduct(billItem.getProduct());
				// 设置仓库
				productStock.setDepot(depot);
				// 保存
				productStockService.save(productStock);
			} else if (list.size() == 1) { // 如果list里面有一条数据,说明是对的,就是修改
				ProductStock productStock = list.get(0);
				// 先拿到即时库存里面的金额和数量,然后添加这个入库订单里面的金额和数量
				BigDecimal totalAmount = productStock.getAmount().add(billItem.getAmount());
				// 设置库存的金额
				productStock.setAmount(totalAmount);
				BigDecimal totalNum = productStock.getNum().add(billItem.getNum());
				// 设置库存的数量
				productStock.setNum(totalNum);
				// 如果是修改,那么就需要用到加权平均法来计算平均价格
				productStock.setPrice(totalAmount.divide(totalNum, 2, BigDecimal.ROUND_HALF_EVEN));
				// 设置入库时间
				productStock.setIncomeDate(new Date());
				// 保存库存
				productStockService.save(productStock);
			} else if (list.size() > 1) {
				throw new RuntimeException("即时库存表里面的product和depot不是唯一的");
			}
		}
	}
}

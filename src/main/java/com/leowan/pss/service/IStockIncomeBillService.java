package com.leowan.pss.service;

import com.leowan.pss.domain.Employee;
import com.leowan.pss.domain.StockIncomeBill;

/**
 * service层的公共父接口
 * @author LeoWan
 *
 * @param <T> domain
 * @param <ID> 主键
 */
public interface IStockIncomeBillService extends IBaseService<StockIncomeBill, Long> {
	public void auding(Long billId,Employee auditor);
}

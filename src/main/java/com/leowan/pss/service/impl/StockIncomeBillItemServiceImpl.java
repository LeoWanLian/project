package com.leowan.pss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leowan.pss.domain.StockIncomeBillItem;
import com.leowan.pss.repository.StockIncomeBillItemRepository;
import com.leowan.pss.service.IStockIncomeBillItemService;

@Service
public class StockIncomeBillItemServiceImpl extends BaseServiceImpl<StockIncomeBillItem, Long> implements IStockIncomeBillItemService {
	@Autowired
	StockIncomeBillItemRepository stockIncomeBillItemRepository;

}

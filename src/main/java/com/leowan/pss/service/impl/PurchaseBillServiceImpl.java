package com.leowan.pss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leowan.pss.domain.PurchaseBill;
import com.leowan.pss.repository.PurchaseBillRepository;
import com.leowan.pss.service.IPurchaseBillService;

@Service
public class PurchaseBillServiceImpl extends BaseServiceImpl<PurchaseBill, Long> implements IPurchaseBillService {
	@Autowired
	PurchaseBillRepository purchaseBillRepository;

}

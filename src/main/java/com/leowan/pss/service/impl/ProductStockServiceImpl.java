package com.leowan.pss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leowan.pss.domain.ProductStock;
import com.leowan.pss.repository.ProductStockRepository;
import com.leowan.pss.service.IProductStockService;

@Service
public class ProductStockServiceImpl extends BaseServiceImpl<ProductStock, Long> implements IProductStockService {
	@Autowired
	ProductStockRepository productStockRepository;

}

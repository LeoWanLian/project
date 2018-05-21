package com.leowan.pss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leowan.pss.domain.Product;
import com.leowan.pss.repository.ProductRepository;
import com.leowan.pss.service.IProductService;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, Long> implements IProductService {
	@Autowired
	ProductRepository productRepository;

}

package com.leowan.pss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leowan.pss.domain.ProductType;
import com.leowan.pss.repository.ProductTypeRepository;
import com.leowan.pss.service.IProductTypeService;

@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductType, Long> implements IProductTypeService {
	@Autowired
	ProductTypeRepository productTypeRepository;
	
	@Override
	public List<ProductType> findChildren(Long parentId) {
		return productTypeRepository.findByJpql("select o from ProductType o where o.parent.id=?", parentId);
	}

	@Override
	public List<ProductType> getParents() {
		return productTypeRepository.findByJpql("select o from ProductType o where o.parent is null");
	}

}

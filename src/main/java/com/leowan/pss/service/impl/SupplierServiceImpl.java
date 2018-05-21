package com.leowan.pss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leowan.pss.domain.Supplier;
import com.leowan.pss.repository.SupplierRepository;
import com.leowan.pss.service.ISupplierService;

@Service
public class SupplierServiceImpl extends BaseServiceImpl<Supplier, Long> implements ISupplierService {
	@Autowired
	SupplierRepository supplierRepository;

}

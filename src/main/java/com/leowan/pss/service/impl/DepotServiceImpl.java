package com.leowan.pss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leowan.pss.domain.Depot;
import com.leowan.pss.repository.DepotRepository;
import com.leowan.pss.service.IDepotService;

@Service
public class DepotServiceImpl extends BaseServiceImpl<Depot, Long> implements IDepotService {
	@Autowired
	DepotRepository depotRepository;

}

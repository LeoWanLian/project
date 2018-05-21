package com.leowan.pss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leowan.pss.domain.Dept;
import com.leowan.pss.repository.DeptRepository;
import com.leowan.pss.service.IDeptService;

@Service
public class DeptServiceImpl extends BaseServiceImpl<Dept, Long> implements IDeptService {
	@Autowired
	DeptRepository deptRepository;

}

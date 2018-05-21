package com.leowan.pss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leowan.pss.domain.Role;
import com.leowan.pss.repository.RoleRepository;
import com.leowan.pss.service.IRoleService;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements IRoleService {
	@Autowired
	RoleRepository roleRepository;

}

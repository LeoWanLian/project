package com.leowan.pss.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.jpa.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leowan.pss.domain.Permission;
import com.leowan.pss.repository.PermissionRepository;
import com.leowan.pss.service.IPermissionService;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission, Long> implements IPermissionService {
	@Autowired
	PermissionRepository permissionRepository;

	@Override
	public List<String> getAllMethods() {
		String jpql = "select o.method from Permission o";
		return findCacheByJpql(jpql);
	}

	@Override
	public List<String> findMethodsByLoginUserId(Long loginUserId) {
		String jpql = "select distinct p.method from Employee e join e.roles r join r.permissions p where e.id=?";
		return findByJpql(jpql, loginUserId);
	}

}

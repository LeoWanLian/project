package com.leowan.pss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leowan.pss.domain.${entityDomain};
import com.leowan.pss.repository.${entityDomain}Repository;
import com.leowan.pss.service.I${entityDomain}Service;

@Service
public class ${entityDomain}ServiceImpl extends BaseServiceImpl<${entityDomain}, Long> implements I${entityDomain}Service {
	@Autowired
	${entityDomain}Repository ${lowerEntityDomain}Repository;

}

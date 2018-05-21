package com.leowan.pss.service;

import java.util.List;

import com.leowan.pss.domain.SystemDictionaryDetail;

/**
 * service层的公共父接口
 * @author LeoWan
 *
 * @param <T> domain
 * @param <ID> 主键
 */
public interface ISystemDictionaryDetailService extends IBaseService<SystemDictionaryDetail, Long> {
	List<SystemDictionaryDetail> getBrands();
	List<SystemDictionaryDetail> getUnits();
}

package com.leowan.pss.service;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import com.leowan.pss.query.BaseQuery;
import com.leowan.pss.query.PageList;

/**
 * service层的公共父接口
 * @author LeoWan
 *
 * @param <T> domain
 * @param <ID> 主键
 */
public interface IBaseService<T, ID extends Serializable> {
	void save(T t);

	void delete(ID id);

	T get(ID id);

	List<T> getAll();

	List findByJpql(final String jpql, Object... values);

	List findCacheByJpql(final String jpql, Object... values);
	
	PageList findByQuery(BaseQuery baseQuery);
	
	InputStream download(List<String[]> data,String[] heads ) throws Exception;
	
	List<String[]> importXlsx(File upload) throws Exception;
}

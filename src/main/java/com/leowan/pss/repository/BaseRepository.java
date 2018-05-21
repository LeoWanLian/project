package com.leowan.pss.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.leowan.pss.query.BaseQuery;
import com.leowan.pss.query.PageList;

/**
 * 持久层基类的父接口
 * 在这个接口中,提供进行重新实现或者添加公共的方法
 * 继承JpaRepository接口
 * 
 * @author LeoWan
 *
 * @param <T>
 *            domain
 * @param <ID>
 *            主键
 */
@NoRepositoryBean
// 需要注意的是@NoRepositoryBean，这个表示该接口不会创建这个接口的实例
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

	// 常规jpql查询
	List findByJpql(String jpql, Object... values);

	// 查询缓存
	List findCacheByJpql(String cacheJpql, Object... values);

	PageList<T> findByQuery(BaseQuery baseQuery);
}

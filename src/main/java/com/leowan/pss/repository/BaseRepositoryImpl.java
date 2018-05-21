package com.leowan.pss.repository;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.jpa.QueryHints;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.leowan.pss.query.BaseQuery;
import com.leowan.pss.query.PageList;

/**
 * 公共方法接口的实现类 能完成自定义的方法
 * 
 * @author LeoWan
 *
 * @param <T>
 *            domain
 * @param <ID>
 *            主键
 */
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
		implements BaseRepository<T, ID> {

	private final EntityManager entityManager;

	// 父类没有不带参数的构造方法，这里手动构造父类
	public BaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {

		super(domainClass, entityManager);
		this.entityManager = entityManager;
	}

	@Override
	public List<T> findByJpql(String jpql, Object... values) {
		Query query = entityManager.createQuery(jpql);
		System.out.println("jpql语句:" + jpql);
		System.out.println(values.length + "个参数,values问号的参数:" + Arrays.toString(values));
		builderJpaParameter(query, values);
		return query.getResultList();
	}

	@Override
	public List<T> findCacheByJpql(String cacheJpql, Object... values) {

		Query query = entityManager.createQuery(cacheJpql);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		// System.out.println("------------------------------======================getAllMethod");
		return query.getResultList();
	}

	// 设置查询参数
	private void builderJpaParameter(Query query, Object... values) {
		if (values != null) {
			// jpa索引从1开始
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i + 1, values[i]);
			}
		}
	}

	@Override
	public PageList<T> findByQuery(BaseQuery baseQuery) {
		// System.out.println("countJpql=" + baseQuery.getCountJpql());
		// System.out.println("limitJpql=" + baseQuery.getLimitJpql());
		// System.out.println("paramList=" + baseQuery.getParamList());
		// System.out.println("baseQuery=" + baseQuery);
		// 查询count
		Query query = entityManager.createQuery(baseQuery.getCountJpql());
		// 拼接条件
		// 获取拼接的参数
		List paramList = baseQuery.getParamList();
//		System.out.println("paramList"+paramList);
		// 设置查询参数
		builderJpaParameter(query, paramList.toArray());
		// 执行一个SELECT查询，该查询返回一个未输入的结果。
		Long countLong = (Long) query.getSingleResult();
		System.out.println("countLong=" + countLong);

		// 如果查询结果为0,那么就返回一个新new的空的结果集合
		if (countLong.intValue() == 0) {
			return new PageList<T>();
		}

		///////////////////////////// limit查询///////////////////////////////////////////////
		PageList<T> pageList = new PageList<>(baseQuery.getCurrentPage(), baseQuery.getPageSize(),
				countLong.intValue());
		String limitJpql = baseQuery.getLimitJpql();
		// 查询limit
		query = entityManager.createQuery(limitJpql);
		// 设置查询参数
		builderJpaParameter(query, paramList.toArray());
		// 分页,必须使用经过处理后的PageList
		// 还要处理分页
		// 设置从哪个位置开始取数据,索引是从0开始
		// 计算的公式：(当前页码-1)*一页的条数
		int first = (pageList.getCurrentPage() - 1) * pageList.getPageSize();
		query.setFirstResult(first);
		int max = pageList.getPageSize();
		// 设置取多少条
		query.setMaxResults(pageList.getPageSize());
		List<T> list = query.getResultList();
		pageList.setData(list);
		// System.out.println(pageList.getData());
		return pageList;
	}

}

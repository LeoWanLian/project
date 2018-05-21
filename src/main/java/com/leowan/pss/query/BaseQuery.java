package com.leowan.pss.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 处理公共的查询前条件:分页
 * 
 * @author LeoWan
 *
 */
public abstract class BaseQuery {
	// 当前页
	private int currentPage = 1;
	// 每页显示的条数
	private int pageSize = 10;
	// 用来装count查询的jpql语句
	private StringBuilder countJpql;
	// 用来装limit查询的jpql语句
	private StringBuilder limitJpql;

	// 额外提供一个对象给报表使用:只有后面的查询条件
	private StringBuilder whereJpql;

	// 专门用来装jpql的？,因为参数的类型不确定，所有不添加泛型
	private List paramList;

	// 通过构造方法让repository在new子类query的时候就能创建countJpql和limitJpql
	// 这里的jpql都是没有条件的
	public BaseQuery(Class entityClass) {
		countJpql = new StringBuilder("select count(o) from " + entityClass.getSimpleName() + " o");
		limitJpql = new StringBuilder("select o from " + entityClass.getSimpleName() + " o ");
		paramList = new ArrayList();
		whereJpql = new StringBuilder();
	}

	// 提供一个给子类使用的拼接查询条件的方法 钩子方法,子类来调用
	protected abstract void addCondition();

	// 提供一个方法来给countJpql和limitJpql添加子类的查询条件 参数和值
	// 在提供一个方法给子类直接使用：传入jpql和参数值
	// select count(o) from Employee o where o.salary between ? and ?
	protected void addWhere(String jpql, Object... objects) {
		// 如果parmaList 为空,说明第一次加入查询条件,就加上where
		if (paramList.size() == 0) {
			countJpql.append(" where ").append(jpql);
			limitJpql.append(" where ").append(jpql);
			whereJpql.append(" where ").append(jpql);
		} else {
			countJpql.append(" and ").append(jpql);
			limitJpql.append(" and ").append(jpql);
			whereJpql.append(" and ").append(jpql);
		}
		// 添加查询条件的参数 如%xxx%
		// paramList.add(obj);
		// 添加查询的参数值
		// paramList.add(objects);//error
		paramList.addAll(Arrays.asList(objects));
	}

	private boolean flag = false;

	private void buildWhere() {
		// 让子类只能调用一次addCondition()方法
		if (!flag) {
			addCondition();
			flag = true;
		}
	}

	public String getWhereJpql() {
		// 调用上面的buildWhere方法
		buildWhere();
		return whereJpql.toString();
	}

	// 只需要提供get方法就可以了,提供给repository调用
	public String getCountJpql() {
		// 获取count查询时,调用上面的buildWhere方法
		buildWhere();
		return countJpql.toString();
	}

	public String getLimitJpql() {
		// 获取分页语句的时候,调用一次addCondition
		buildWhere();
		return limitJpql.toString();
	}

	public List getParamList() {
		// 获取参数列表的时候,调用一次addCondition
		buildWhere();
		return paramList;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "BaseQuery [currentPage=" + currentPage + ", pageSize=" + pageSize + ", countJpql=" + countJpql
				+ ", limitJpql=" + limitJpql + ", paramList=" + paramList + ", flag=" + flag + "]";
	}

}

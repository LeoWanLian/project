package com.leowan.pss.query;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.leowan.pss.domain.PurchaseBillItem;

/**
 * 处理子类的高级查询条件 ,涉及到domain类的字段
 *
 */
public class PurchaseBillItemQuery extends BaseQuery{

	// 查询条件为审核状态
	private Integer status;

	private Date beginDate;

	private Date endDate;
	
	//分组的条件
	private String groupBy=" o.bill.supplier.name ";

	// 通过子类的构造方法调用父类的构造方法,将entityClass实体传给父类,父类就能够拼接jpql
	public PurchaseBillItemQuery() {
		super(PurchaseBillItem.class);
	}

	@Override
	protected void addCondition() {
		if (status != null && status != -2) {
			addWhere(" o.bill.status = ?", status);
		}
		if (beginDate != null) {
			addWhere("o.bill.vdate>=?", beginDate);
		}
		if (endDate != null) {
			 Date date = DateUtils.addDays(endDate, 1);
//			 System.out.println("date:" + date.toLocaleString());
			addWhere("o.bill.vdate<=?", date);
		}
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	

}

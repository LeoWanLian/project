package com.leowan.pss.web.action;

import com.leowan.pss.domain.Employee;
import com.leowan.pss.query.PageList;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

public abstract class CRUDAction<T> extends BaseAction implements ModelDriven<T>, Preparable {
	// 接收前台页面传递的id
	protected Long id;

	// 需要提供getter方法
	protected PageList pageList;

	@InputConfig(methodName = "execute")
	@Override
	public String execute() throws Exception {
		list();
		return SUCCESS;
	}

	// 这里设置成 protected 是为了不让前端进行访问
	protected abstract void list() throws Exception;

	// 保存的方法
	public abstract String save() throws Exception;

	// 删除方法
	public abstract String delete() throws Exception;

	public abstract void prepareSave() throws Exception;

	public abstract void prepareInput() throws Exception;

	@Override
	public void prepare() throws Exception {
	}

	public PageList getPageList() {
		return pageList;
	}

	public void setPageList(PageList pageList) {
		this.pageList = pageList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}

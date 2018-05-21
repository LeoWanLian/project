package com.leowan.pss.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 抽取的父类action,提供公共的结果视图字段和压栈方法
 * @author LeoWan
 *
 */
public class BaseAction extends ActionSupport {
	// 重定向视图
	public static final String RELOAD = "reload";
	public static final String USER_IN_SESSION = "userInSession";

	// 将数据放到值栈的Map中
	public void putContext(String key, Object value) {
		ActionContext.getContext().put(key, value);
	}
}

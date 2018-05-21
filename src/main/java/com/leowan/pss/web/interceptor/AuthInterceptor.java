package com.leowan.pss.web.interceptor;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.leowan.pss.domain.Employee;
import com.leowan.pss.service.IPermissionService;
import com.leowan.pss.web.action.BaseAction;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthInterceptor extends AbstractInterceptor {
	// 不需要拦截的action
	private String excludeActions;
	@Autowired
	private IPermissionService permissionService;

	public AuthInterceptor() {
//		System.out.println("自定义拦截器的构造方法被执行");
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
//		System.out.println("访问的action:" + action);

		// 获取类名
		String className = action.getClass().getSimpleName();
		// 1.放行:不需要拦截的Action都放行
		// System.out.println("excludeActions:" + excludeActions);
		if (excludeActions.contains(className)) {
			return invocation.invoke();// 放行
		}

		// 2.没有登录用户拦截
		Employee employee = (Employee) ActionContext.getContext().getSession().get(BaseAction.USER_IN_SESSION);
		if (employee == null) {
			// <!-- 全局返回视图 -->
			// <global-results>
			// <!-- 视图名称login，显示登录页面 -->
			// <result name="login">/WEB-INF/views/login.jsp</result>
			// </global-results>
			return Action.LOGIN;
		}

		// 3.拦截已经登录成功的用户是否具有访问当前资源的权限
		String actionName = action.getClass().getSimpleName();
		String methodName = invocation.getProxy().getMethod();
		String classMethodName = actionName + "." + methodName;
		String allClassMethodName = actionName + ".ALL";
		// 当前访问的资源
//		System.out.println("actionMethodName:" + classMethodName);
//		System.out.println("allActionMethodName:" + allClassMethodName);
		// 拦截
		// 只要在permission权限表method字段出现的字符串匹配当前访问的资源,拦截
		List<String> allMethods = permissionService.getAllMethods();
//		System.out.println("拦截器调用------------------permission");
		// 判断当前访问的资源是否在私有资源里面
		if (allMethods.contains(allClassMethodName) || allMethods.contains(allClassMethodName)) {
			System.out.println("需要拦截.......");
			// 写一个业务方法：判断当前登录用户是否具有访问该私有资源的权限
			List<String> list = permissionService.findMethodsByLoginUserId(employee.getId());
			if (list.contains(classMethodName) || list.contains(allClassMethodName)) {
				System.out.println("有权限访问++++++++++++");
			} else {
				System.out.println("没有权限访问-----------");
				// 判断是否是ajax请求:X-Requested-With :XMLHttpRequest
				HttpServletRequest request = ServletActionContext.getRequest();
				String xhr = request.getHeader("X-Requested-With");
				System.out.println("------------" + xhr);
				if ("XMLHttpRequest".equals(xhr)) {
					HttpServletResponse response = ServletActionContext.getResponse();
					// 必须在获取流之前设置编码格式
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.print("{\"success\":false,\"msg\":\"没有权限\"}");
					return null;
				}
				return "notAuth";
			}
		} else {
//			System.out.println("不需要拦截的");
		}
		// 放行
		return invocation.invoke();
	}

	public void setExcludeActions(String excludeActions) {
		this.excludeActions = excludeActions;
		System.out.println("不需要拦截的action" + excludeActions);
	}

}

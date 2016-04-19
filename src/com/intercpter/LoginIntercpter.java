package com.intercpter;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginIntercpter extends AbstractInterceptor {

	public String intercept(ActionInvocation invocation) throws Exception {

		Map map = invocation.getInvocationContext().getSession();

		if (map.get("user") == null) {
			return "noLogin";
		}
		
		return invocation.invoke();
		
	}

}

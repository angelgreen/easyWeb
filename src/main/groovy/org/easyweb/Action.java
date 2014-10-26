package org.easyweb;

import org.easyweb.aop.Interceptor;
import org.easyweb.controller.AbstractController;

import java.lang.reflect.Method;
import java.util.List;

public class Action {

	private final Method method;
	private final Class<? extends AbstractController> clazz;
	private final List<Interceptor> interceptorList;

	public Action(final Class<? extends AbstractController> clazz, final Method method, List<Interceptor> interceptorList) {
	this.method =  method;
	this.clazz = clazz;
	this.interceptorList = interceptorList;	
	}

	public Method getMethod() {
		return this.method;
	}

	public Class<? extends AbstractController> getControllerClass() {
		return this.clazz;
	}

	public List<Interceptor> getInterceptorList() {
		return this.interceptorList;
	}
}

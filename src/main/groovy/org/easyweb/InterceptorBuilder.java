package org.easyweb;

import org.easyweb.aop.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.easyweb.utils.Validate;

public class InterceptorBuilder {


	public static List<Interceptor> getClassInterceptors(Class<?> clazz) {
		Validate.requireNotNull(clazz, "class not be null");

		Before before = clazz.getAnnotation(Before.class);
	
		List<Interceptor> classInterceptorList = new ArrayList<Interceptor>();

		if(null != before) {
			Class<? extends Interceptor>[] clazzes = before.value();
			
			for(Class<? extends Interceptor> clazz1: clazzes) {
				try{

					Interceptor in = clazz1.cast(clazz1.newInstance());
					classInterceptorList.add(in);
				}catch(Exception e) {
					//ignore ...
				}
			}

		}

		return classInterceptorList;
	}

	public static List<Interceptor> getMethodInterceptors(Method method) {
		Validate.requireNotNull(method, "method not be null");

		Before before = method.getAnnotation(Before.class);
	
		List<Interceptor> methodInterceptorList = new ArrayList<Interceptor>();

		if(null != before) {
			Class<? extends Interceptor>[] clazzes = before.value();
			
			for(Class<? extends Interceptor> clazz: clazzes) {
				try{

					Interceptor in = clazz.cast(clazz.newInstance());
					methodInterceptorList.add(in);
				}catch(Exception e) {
					//ignore ...
				}
			}

		}

		return methodInterceptorList;
	}

	public static boolean clearLayer(Method method) {
		Validate.requireNotNull(method, "method not be null");
		ClearLayer clearLayer = method.getAnnotation(ClearLayer.class);
		return null != clearLayer;
	}
}

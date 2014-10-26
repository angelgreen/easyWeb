package org.easyweb.aop;

import org.easyweb.controller.AbstractController;

import java.util.*;
import java.lang.reflect.*;

public class ActionInvocation {

	private final List<Interceptor> interceptorList;

	private final AbstractController controller;

	private final Object[] NIL_OBJ = new Object[0];

	public final Method method;

	private int index  = 0;

	public ActionInvocation(final AbstractController controller, final Method method , final List<Interceptor> interceptorList) {
		super();
		this.method = method;	
		this.controller = controller;
		this.interceptorList = interceptorList;
 	}

    public AbstractController getController() {
        return this.controller;
    }

    //call the invoke
	public void invoke() {
		if(index < interceptorList.size() ) {

			interceptorList.get(index++).intercept(this);

		}else if(index++ == interceptorList.size()) {
            try {
                method.invoke(controller,NIL_OBJ);
            } catch (IllegalAccessException e){
                //return
                throw new RuntimeException(e);
			}catch(InvocationTargetException e) {
                //return
                throw new RuntimeException(e);
            }
        }
	}
}

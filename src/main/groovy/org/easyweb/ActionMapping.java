package org.easyweb;

import java.util.*;
import java.lang.reflect.*;
import java.util.logging.Logger;

import org.easyweb.controller.AbstractController;
import org.easyweb.utils.Validate;
import org.easyweb.aop.*;

public class ActionMapping {

    private final Logger LOG = Logger.getLogger(ActionMapping.class.getName());

    private final Route route;

	private final TST<Action> actionTries = new TST<Action>(); //the action tries 


    public ActionMapping(final JConfig configuration) {
        Validate.requireNotNull(configuration, "the configure class not null");

        this.route = configuration.getRoute();
        //more ...
        buildAction();
    }

    private void buildAction() {


        final List<Method> parentMethodList = getParentMethodList();

        for (String controllerName : this.route.getMap().keySet()) {
            Class<? extends AbstractController> clazz = route.getMap().get(controllerName);

            if (null != clazz) {
                //only use public method
                List<Method> methodList = new ArrayList<Method>();

                for (Method method : clazz.getMethods()) {

                    if (method.getParameterTypes().length == 0 &&
                            method.getReturnType() == void.class &&
                            !parentMethodList.contains(method))

                        methodList.add(method);
                }

                //get the class interceptor list
                List<Interceptor> classInterceptors = InterceptorBuilder.getClassInterceptors(clazz);

                for (Method method : methodList) {


                    String methodName = method.getName();

                    String key = controllerName + "/" + methodName;

                    List<Interceptor> methodInterceptors = InterceptorBuilder.getMethodInterceptors(method);


                    boolean clear = InterceptorBuilder.clearLayer(method);


                    List<Interceptor> realInterceptors;

                    if (clear) {
                        //intersect
                        realInterceptors = methodInterceptors;
                    } else {
                        realInterceptors = doUnion(classInterceptors, methodInterceptors);
                        //union
                    }

                    Action action = new Action(clazz, method, realInterceptors);

                    actionTries.put(key, action);
                }

            }
        }
    }

    private List<Interceptor> doUnion(List<Interceptor> a, List<Interceptor> b) {
        Validate.requireNotNull(a);
        Validate.requireNotNull(b);
        List<Interceptor> m = new ArrayList<Interceptor>(a.size() + b.size());

        for (Interceptor t1 : a)
            m.add(t1);
        for (Interceptor t2 : b)
            m.add(t2);
        return m;
    }

    private List<Method> getParentMethodList() {
        Class<?> clazz = AbstractController.class;

        Validate.requireNotNull(clazz, "abstract class not null");
        List<Method> methodList = new ArrayList<Method>();
        Collections.addAll(methodList, clazz.getMethods());
        return methodList;
    }

    public Action getAction(String key) {
        Validate.requireNoBlank(key, "the actionMapping query key not blank ");

        //for example ... org.easyweb.controller/method/
        if (key.lastIndexOf("/") != -1) key = key.substring(0, key.length());

        Action action = actionTries.get(key);

        if (null == action) {
            action = actionTries.get("error/do404Error");
        }
        //may be null
        return action;
    }
}

package org.easyweb.aop;

import org.easyweb.controller.AbstractController;

import java.util.Map;

/**
 * use to acl visit the org.easyweb.restful api ..
 */
public class TokenInterceptor implements Interceptor {

    //TODO .., may some service ,,,

    @Override
    public void intercept(ActionInvocation ai) {

        AbstractController controller = ai.getController();

        String token = controller.getToken();

        if (token != null && authorize(token)) {
            ai.invoke();
        } else {
            //TODO may return ...
//            throw new ValidateException("eror not token");
            Map<String,Object> model = controller.model();
            model.put("code",403);
            model.put("msg","not privilege to visit !");
            controller.errorRender();
        }
    }

    private boolean authorize(final String token) {
        if (token != null && token.equalsIgnoreCase("100")) return true;
        return false;
    }
}

package org.easyweb;

import org.easyweb.aop.ActionInvocation;
import org.easyweb.aop.Interceptor;
import org.easyweb.controller.AbstractController;
import freemarker.template.Configuration;
import org.easyweb.render.Render;
import org.easyweb.render.RenderFactory;
import org.easyweb.utils.MarkerUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DispatcherServlet extends HttpServlet {

    private final Logger LOG = Logger.getLogger(DispatcherServlet.class.getName());

	private ActionMapping actionMapping;


    @Override
    public void init(ServletConfig config) throws ServletException {
		//init marker
		Configuration cfg = MarkerUtils.getConfiguration();
		cfg.setServletContextForTemplateLoading(config.getServletContext(),"/html");

		//load framework config
        String name = config.getInitParameter("config");
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Class<?> clazz = Class.forName(name, false, loader);
            @SuppressWarnings("unchecked")
            JConfig configuration = (JConfig) clazz.newInstance();

			actionMapping = new ActionMapping(configuration);

        } catch (Exception e) {
//            LOG.warning("init dispatcher servlet error");
            LOG.log(Level.SEVERE, "init dispatcher serlvet error", e);
        }
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        //init the render
        Render view = null;

        String url = request.getPathInfo();
		String key = formatURL(url);

        Action action = actionMapping.getAction(key);

        try {

			Class<? extends AbstractController> clazz = action.getControllerClass();

			AbstractController controller = clazz.cast(clazz.newInstance());

			controller.init(request, response);


			Method method = action.getMethod();

			List<Interceptor> interceptorList = action.getInterceptorList();

			ActionInvocation actionInvocation = new ActionInvocation(controller, method, interceptorList);

			actionInvocation.invoke();

			//get the render 
			view = controller.getRender();

         }catch(Throwable t) {
			t.printStackTrace();

			view = RenderFactory.getErrorRender();

			System.out.println("error render");
            //get the cause ...
            Throwable prev = null;

            while( t != null) {
                prev = t;
                t = t.getCause();
            }

            Map<String,Object> model = new HashMap<String,Object>();
			model.put("code", 500);
			model.put("msg", prev.getMessage());
			//this may server error
			//TODO handle error msg ...
			view.init(model,"");
		}finally{
		//render it ...
			if(null != view)
				view.render(request, response);
		}
    }

	private String formatURL(String url) {
		String newURL = "";

		int i = url.indexOf("/");
		if(i != -1) {
			url = url.substring(i+1);
			int j = url.indexOf("/");
			if(j != -1) {
				String controllerName = url.substring(0,j);
				int k = !url.contains("?")?url.length(): url.indexOf("?");
				String methodName = url.substring(j + 1,k);
				newURL = controllerName + "/" + methodName;
			}
		}
		return newURL;
	}
}

package org.easyweb.controller;

import org.easyweb.render.Render;
import org.easyweb.render.RenderFactory;
import org.easyweb.utils.Convert;
import org.easyweb.utils.Validate;

import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * a abstract class
 */
public abstract class AbstractController {

    private HttpServletRequest request;
    private HttpServletResponse response;

    protected Render render;
    protected final Map<String, Object> model = new HashMap<String, Object>();

    public AbstractController() {
        super();
    }

    public void init(final HttpServletRequest request, final HttpServletResponse response) {
        this.response = response;
        this.request = request;
    }


    protected void jsonRender(final String path) {
        this.render = RenderFactory.getJsonRender();
        this.render.init(this.model, path);
    }

    protected void jsonRender() {
        jsonRender("");
    }

    public Map<String,Object> model() {
        return this.model;
    }

	protected void freemarkerRender(final String path) {
		this.render = RenderFactory.getFreeMarkerRender();
		this.render.init(this.model,path);
	}

    protected void pictureRender(final String path) {
        this.render = RenderFactory.getPictureRender();
        this.render.init(this.model, path);
    }

    protected void pictureRender() {
        pictureRender("");
    }

    protected void errorRender(final String path) {
        this.render = RenderFactory.getErrorRender();
        this.render.init(this.model, path);
    }

    public void errorRender() {
        errorRender("");
    }

    public Render getRender() {
        return this.render;
    }

    protected void jspRender(final String path) {
        this.render = RenderFactory.getJspRender();
        this.render.init(this.model, path);
    }

    protected <T> T getObject(Class<T> clazz) {
        //guard
        Validate.requireNotNull(clazz, "class mot null");
        StringBuilder buffer = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            String s;

            while ((s = reader.readLine()) != null) {
                buffer.append(s);
            }
        } catch (IOException e) {
            //ignore ...
        }

        //this convert json -> object
        return Convert.convertO(buffer.toString(), clazz);
    }

    protected boolean success(final String arg) {
        return null != arg && arg.trim().equalsIgnoreCase("success");
    }

    protected boolean error(final String arg) {
        return null != arg && arg.trim().equalsIgnoreCase("error");
    }

    // get the parameter
    protected <T> T getPara(String name, Class<T> clazz) {
        //guard
        Validate.requireNoBlank(name);
        Validate.requireNotNull(clazz);

        String value = request.getParameter(name);

        Validate.requireNotNull(value);

        return Convert.convertT(value, clazz);
    }

    public String getToken() {

        return request.getParameter("token");

    }

    //get the attribute
    protected <T> T getAttr(String name, Class<T> clazz) {

        Validate.requireNoBlank(name);
        Validate.requireNotNull(name);

        Object v = request.getAttribute(name);

        return Convert.convertT(v, clazz);
    }


    protected <T> void setAtrr(String name, T t) {
        request.setAttribute(name, t);
    }
}

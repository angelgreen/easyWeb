
package org.easyweb.render;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;
import java.io.*;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.easyweb.utils.MarkerUtils;

public class RenderFactory {

    private RenderFactory() {
        super();
    }

    //return the errorRender
    public static Render getErrorRender() {
        return new ErrorRender();
    }

    //return the jsonRender
    public static Render getJsonRender() {
        return new JsonRender();
    }

    public static Render getPictureRender() {
        return new PictureRender();
    }

    public static Render getJspRender() {
        return new JspRender();
    }

    public static Render getFreeMarkerRender() {
        return new FreemarkerRender();
    }
}

class JspRender extends Render {

    //TODO ...
    @Override
    public void init(Map<String, Object> model, String resourcePath) {

    }

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response) {

    }
}

//the error render
class ErrorRender extends Render {

    public ErrorRender() {
        super();
    }

    private Map<String, Object> model;
    private String resourcePath;

    @Override
    public void init(final Map<String, Object> model, final String resourcePath) {
        //empty ...
        this.model = model;
        this.resourcePath = resourcePath;
    }

    @Override
    public void render(final HttpServletRequest request, final HttpServletResponse response) {
        try {
            response.sendError((Integer) model.get("code"), (String) model.get("msg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//the json render
class JsonRender extends Render {

    private Gson gson;
    private Map<String, Object> model;
    private String resourcePath;

    public JsonRender() {
        super();
    }

    @Override
    public void init(final Map<String, Object> model, final String resourcePath) {
        this.model = model;
        this.resourcePath = resourcePath;
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Override
    public void render(final HttpServletRequest request, final HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
            String json = gson.toJson(model);
            response.getWriter().print(json);
            response.getWriter().flush();
        } catch (IOException e) {
            //ignore it ...
        }
    }
}

//the text render
class TextRender extends Render {

	private String resourcePath;

    public TextRender() {
        super();
    }

    @Override
    public void init(final Map<String, Object> model, final String resourcePath) {

		this.resourcePath = resourcePath;
    }

    @Override
    public void render(final HttpServletRequest request, final HttpServletResponse response) {
    }
}


//the fressmarker render

class FreemarkerRender extends Render {

    private Map<String, Object> model;
    private String resourcePath;

    public FreemarkerRender() {
        super();
    }

    @Override
    public void init(final Map<String, Object> model, final String resourcePath) {
        this.model = model;
        this.resourcePath = resourcePath;
    }

    @Override
    public void render(final HttpServletRequest request, final HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        try {
            //for template bug fix ...
            Template template = MarkerUtils.getTemplate(URLEncoder.encode(resourcePath, "UTF-8"));

            template.process(this.model, response.getWriter());
            response.getWriter().flush();
        } catch (IOException e) {
            //ignore it ...D
        } catch (TemplateException e) {
            e.printStackTrace();
            //igore it ....
        }
    }
}

//the picture render
class PictureRender extends Render {

    public PictureRender() {
        super();
    }

    @Override
    public void init(final Map<String, Object> model, final String resourcePath) {

    }

    @Override
    public void render(final HttpServletRequest request, final HttpServletResponse response) {

    }
}


package org.easyweb.utils;


import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.SimpleObjectWrapper;
import freemarker.template.Template;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.Locale;

public class MarkerUtils {

	private static Configuration cfg = createMarker();


	private static Configuration createMarker() {
		Configuration cfg = new Configuration();
		cfg.setObjectWrapper(new BeansWrapper());
        cfg.setDefaultEncoding("utf-8");
        cfg.setStrictSyntaxMode(false);
        cfg.setTemplateUpdateDelay(1);
        cfg.setClassicCompatible(true);
        return cfg;
	}

	public static Configuration getConfiguration() {
		return cfg;
	}

	public static void setServletContextForTemplateLoading(ServletContext context, String path) {

        //bug fix for freemarker
        if(path.length() > 0 && path.charAt(0) != '/') {
            path = "/" + path;
        }
		cfg.setServletContextForTemplateLoading(context, path);
	}

	public static Template getTemplate(final String name) throws IOException{
		return cfg.getTemplate(name);
	}
}

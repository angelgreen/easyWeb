package org.easyweb.render;

import java.util.*;
import javax.servlet.http.*;

public abstract class Render {

	public Render() {
	}

	public abstract void init(final Map<String,Object> model, String resourcePath);

	public abstract void render(final HttpServletRequest request,final HttpServletResponse response);

}

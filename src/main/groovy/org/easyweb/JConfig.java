package org.easyweb;

import org.easyweb.controller.*;

public abstract class JConfig {

	private final Route route = new Route();

	public JConfig() {
		route.put("error",ErrorController.class);
		configure(route);
	}
	//configure route
	public abstract void configure(final Route route);

	public Route getRoute() {
		return this.route;
	}
}

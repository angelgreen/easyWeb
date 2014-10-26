package org.easyweb;

import org.easyweb.controller.*;
import org.easyweb.utils.Validate;

import java.util.HashMap;
import java.util.Map;

public class Route {

	private final Map<String,Class<? extends AbstractController>> map =
	new HashMap<String,Class<? extends AbstractController>>();

	public <T extends AbstractController> void put(String name, Class<T> clazz)  {
		Validate.requireNoBlank(name, "org.easyweb.controller name not be blank or null ");
		Validate.requireNotNull(clazz, "org.easyweb.controller class not be null");
		map.put(name, clazz);
	} 

	public Map<String, Class<? extends AbstractController>> getMap() {
		return this.map;
	}
}

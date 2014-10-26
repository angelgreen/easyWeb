package org.easyweb.utils;

/**
* use to guard programming
*
**/
public final class Validate {

	public static void requireNoBlank(String value) {
		if(null == value || value.trim().equals("") )
			throw new ValidateException("the string is blank");
	}

	public static void requireNoBlank(String value, String msg) {
		if(null == value || value.trim().equals(""))
			throw new ValidateException(msg);
	}
	
	public static <T> void requireNotNull(T t) {
		if(null == t)
			throw new ValidateException("the object is null");
	}

	public static <T> void requireNotNull(T t, final String msg) {
		if(null == t)
			throw new ValidateException(msg);
	}	

	public static <T> boolean isNull(T t){
		return null != t;
	}

}

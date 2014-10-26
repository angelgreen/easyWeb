package org.easyweb.utils;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
* use to convert string to it values
**/
public final class Convert {


	public static <T> T	convertT(Object t, Class<T> clazz) {
		
		if(!clazz.isInstance(t)) throw new ConvertException(" ");
	
		return clazz.cast(t);
	}

	public static <T> T convertO(final String json, Class<T> clazz) {

        Validate.requireNoBlank(json,"json should not be blank or null");
        Validate.requireNotNull(clazz,"class should not be null");
        Gson gson = new Gson();

        return gson.fromJson(json, clazz);
	}

	public static Integer convertInt(String value) {
		try{
            return Integer.parseInt(value);
		}catch(NumberFormatException e){
			throw new ConvertException(e.getMessage());
		}
	}
	
	public static Long convertLong(String value) {
		try{
            return Long.parseLong(value);
		}catch(NumberFormatException e){
			throw new ConvertException(e.getMessage());
		}
	}

	public static Float convertFloat(String value) {
		try{
            return Float.parseFloat(value);
		}catch(NumberFormatException e){
			throw new ConvertException(e.getMessage());
		}
	}

	public static Date convertDate(String value, String format) {
		try{
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.parse(value);

		} catch (ParseException e) {
            throw new ConvertException(e.getMessage());
        }
    }

	public static Date convertDate(String value) {
		String format = "YYYY-MM-DD HH:MM:SS";
		return convertDate(value, format);
	}
}

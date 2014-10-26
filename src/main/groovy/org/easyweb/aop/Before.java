package org.easyweb.aop;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Before{
	Class<? extends Interceptor>[] value();
}

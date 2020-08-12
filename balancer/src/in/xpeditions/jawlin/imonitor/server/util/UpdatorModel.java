/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.util;

import java.lang.reflect.Method;

/**
 * @author Coladi
 * @param <T>
 *
 */
public class UpdatorModel<T> {
	private Class<?> className;
	private Method method;
	
	public UpdatorModel(Class<?> classRep, Method method) {
		this.className = classRep;
		this.method = method;
	}
	
	public Class<?> getClassName() {
		return className;
	}
	public void setClassName(Class<?> className) {
		this.className = className;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
}

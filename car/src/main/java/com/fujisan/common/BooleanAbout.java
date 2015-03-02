package com.fujisan.common;
/**
 * boolean值
 * @author siyaomin
 *
 */
public class BooleanAbout {
	public static final String y="y";
	public static final String n="n";
	/**
	 * y返回true,其他返回false
	 * @param value
	 * @return
	 */
	public static final boolean isTrue(String value){
		return y.equals(value)?true:false;
	}
}

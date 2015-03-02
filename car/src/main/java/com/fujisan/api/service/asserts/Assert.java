package com.fujisan.api.service.asserts;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.fujisan.api.service.asserts.exception.AssertException;
/**
 * 基础断言
 * @author siyaomin
 *
 */
public class Assert {
	/**
	 * 不为空
	 * @param id
	 */
	public static void notNull(Object target){
		if(target==null){
			throw new AssertException("参数为空",StringUtils.EMPTY);
		}
	}
	/**
	 * 不为空
	 * @param id
	 */
	public static void notNull(Serializable target,String msg){
		if(target==null){
			throw new AssertException( msg,StringUtils.EMPTY);
		}
	}
	/**
	 * 不为空且有元素
	 * @param coll
	 */
	public static void notEmpty(Collection<?> coll){
		if(CollectionUtils.isEmpty(coll)){
			throw new AssertException("参数为空",StringUtils.EMPTY);
		}
	}
	/**
	 * 不为空且有元素
	 * @param coll
	 */
	public static void notEmpty(Collection<?> coll,String msg){
		if(CollectionUtils.isEmpty(coll)){
			throw new AssertException("列表为空",StringUtils.EMPTY);
		}
	}
	public static void exists(String msg){
		throw new AssertException( msg,StringUtils.EMPTY);
	}
	public static void notBlank(String str, String msg) {
		if (StringUtils.isBlank(str)) {
			throw new AssertException( msg,msg);
		}
	}
}

package com.fujisan.common;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.fujisan.api.service.asserts.Assert;
/**
 * bean工具
 * @author siyaomin
 *
 */
public class BeanHelper {
	private static Logger log=Logger.getLogger(BeanHelper.class);
	/**
	 * 字段不为空的验证
	 * @param seri
	 * @param fields
	 * @return
	 */
	public static String checkFieldsNotEmpty(Serializable seri,List<String> fields){
		Assert.notNull(seri);
		Assert.notEmpty(fields);
		StringBuilder res=new StringBuilder();
		try {
			for(String field:fields){
				Object fr = PropertyUtils.getProperty(seri, field);
				if(fr==null||(fr instanceof String &&StringUtils.isBlank(fr.toString()))){
					res.append(field).append(",");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return res.toString();
	}
	public static Object get(Object o,String field){
		try {
			return PropertyUtils.getProperty(o, field);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return null;
		}
		
	}
}

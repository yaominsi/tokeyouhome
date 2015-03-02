package com.fujisan.repository;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.fujisan.api.service.asserts.Assert;
import com.fujisan.common.BooleanAbout;
import com.fujisan.model.BaseModel;
import com.google.common.collect.Lists;

/**
 * DAO层的辅助类
 * 
 * @author siyaomin
 *
 */
public class DaoUtil {
	private static final Logger log=Logger.getLogger(DaoUtil.class);
	private static final List<String> NEVER_UPDATE_FIELDS=Lists.newArrayList(
			"class","_id","gmtCreate","creator","id"
			);
	/**
	 * 生成查询
	 * @param target
	 * @param properties
	 * @param query
	 */
	public static Query genQuery(Object target, List<String> properties) {
		
		Assert.notNull(target);
		Query query=new Query();
		query.addCriteria(Criteria.where(BaseModel.final_isDeleted).is(BooleanAbout.n));
		if (target == null) {
			return query;
		}
		if (CollectionUtils.isEmpty(properties)) {
			return query;
		}
		
		try {
			for (String p : properties) {
				if (StringUtils.isBlank(p)) {
					continue;
				}
				Object fr;
				fr = PropertyUtils.getProperty(target, p);
				query.addCriteria(Criteria.where(p).is(fr));
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return query;
	}
	public static Update genUpdate(Object target) {
		Update update=new Update();
		Assert.notNull(target);
		if (target == null) {
			return update;
		}
		
		try {
			PropertyDescriptor[] list = PropertyUtils.getPropertyDescriptors(target.getClass());
			for(PropertyDescriptor desc:list){
				if (NEVER_UPDATE_FIELDS.contains(desc.getName())){
					continue;
				}
				Method read = desc.getReadMethod();
				boolean readFlag=read.isAccessible();
				read.setAccessible(true);
				Object val = read.invoke(target);
				if (val!=null) {
					if (val instanceof Collection<?> &&CollectionUtils.isEmpty((Collection<?>) val)) {
						continue;
					}
					update.set(desc.getName(), val);
				}
				read.setAccessible(readFlag);
			}
		} catch (Exception  e) {
			log.error(e.getMessage(),e);
		}
		return update;
	}
}

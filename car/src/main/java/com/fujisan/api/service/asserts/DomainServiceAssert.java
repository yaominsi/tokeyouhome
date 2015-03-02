package com.fujisan.api.service.asserts;

import com.fujisan.api.RequestContext;
import com.fujisan.common.BusiTypeEnum;
/**
 * 领域服务断言
 * @author siyaomin
 *
 */
public interface DomainServiceAssert<T> {
	/**
	 * 是否存在
	 * @param id
	 */
	public abstract void exists(RequestContext requestContext,String id);
	/**
	 * 操作是否可行
	 * @param requestContext
	 * @param id
	 * @param busiTypeEnum
	 */
	public abstract void enable(RequestContext requestContext,T model,BusiTypeEnum busiTypeEnum);
	/**
	 * 参数校验
	 * @param requestContext
	 * @param obj
	 * @param busiTypeEnum
	 */
	public abstract void checkParams(RequestContext requestContext,T target,
			BusiTypeEnum busiTypeEnum);
}

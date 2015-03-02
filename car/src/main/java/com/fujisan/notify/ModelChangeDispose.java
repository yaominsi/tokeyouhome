package com.fujisan.notify;

import com.fujisan.api.RequestContext;
import com.fujisan.model.BaseModel;

/**
 * 模型变更处理接口
 * 
 * @author siyaomin
 *
 * @param <T>
 */
public interface ModelChangeDispose<T extends BaseModel> {
	/**
	 * 模型变更的处理
	 * @param requestContext
	 * @param from
	 * @param to
	 */
	void dispose(RequestContext requestContext,Object[] args );
}

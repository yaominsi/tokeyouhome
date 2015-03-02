  package com.fujisan.api.service.smart;

import com.fujisan.api.RequestContext;
import com.fujisan.api.Response;
/**
 * 推荐服务
 * @author siyaomin
 *
 */
public interface RecommendService {
	/**
	 * 获取推荐的区域
	 * @param requestContext
	 * @return
	 */
	Response<Boolean> getRecommendScopes(RequestContext requestContext);
	
}

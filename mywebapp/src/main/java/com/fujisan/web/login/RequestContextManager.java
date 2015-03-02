package com.fujisan.web.login;

import com.fujisan.api.RequestContext;
import com.fujisan.common.LogSequence;
import com.fujisan.model.UserModel;
/**
 * 请求上下文管理员
 * @author siyaomin
 *
 */
public class RequestContextManager {
	private static final ThreadLocal<RequestContext> context = new ThreadLocal<RequestContext>();

	public static void init(UserModel userModel) {
		RequestContext requestContext = new RequestContext();
		requestContext.setUserModel(userModel);
		requestContext.setSeq(LogSequence.seq());
		if (userModel==null) {
			userModel=new UserModel();
		}
		requestContext.setUserModel(userModel);
		
		context.set(requestContext);
	}
	public static RequestContext current(){
		RequestContext result = context.get();
		if (result==null) {
			init(null);
		}
		return context.get();
	}
}

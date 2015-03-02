package com.fujisan.api.service;

import java.util.List;

import com.fujisan.api.RequestContext;
import com.fujisan.api.Response;
import com.fujisan.model.TagModel;
import com.fujisan.model.UserModel;
/**
 * 用户服务
 * @author siyaomin
 *
 */
public interface UserService {
	/**
	 * 注册
	 * @param requestContext
	 * @param userModel
	 * @return
	 */
	Response<UserModel> registry(RequestContext requestContext,UserModel userModel);
	/**
	 * 更新
	 * @param requestContext
	 * @param userModel
	 * @return
	 */
	Response<Boolean> update(RequestContext requestContext,UserModel userModel);
	/**
	 * 更新标签，添加或移除
	 * @param requestContext
	 * @param uesrId
	 * @param tags
	 * @return
	 */
	Response<Boolean> updateTages(RequestContext requestContext,String id,List<TagModel> tags);
	/**
	 * 获取用户信息
	 * @param requestContext
	 * @param uesrId
	 * @return
	 */
	Response<UserModel> getUserByUserId(RequestContext requestContext,String id);
	/**
	 * 获取用户信息
	 * @param requestContext//tartgetId,source
	 * @param uesrId
	 * @return
	 */
	Response<UserModel> getSingle(RequestContext requestContext,UserModel userModel);
}

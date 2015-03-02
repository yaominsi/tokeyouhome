package com.fujisan.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fujisan.api.RequestContext;
import com.fujisan.api.Response;
import com.fujisan.api.service.UserService;
import com.fujisan.common.BooleanAbout;
import com.fujisan.model.TagModel;
import com.fujisan.model.UserModel;
/**
 * 用户测试
 * @author siyaomin
 *
 */
public class UserServiceTest extends BaseTest{
	private UserService service;
	
	@Test
	public void testRegistry() {
		service=context.getBean(UserService.class);
		RequestContext requestContext=getContext("userId", "userName", new double[]{1d,1d});
		UserModel userModel=new UserModel();
		userModel.setTargetId("68959xww");
		userModel.setSource("alibaba");
		userModel.setName("司要民");
		userModel.setNickName("六冲");
		userModel.setPlateNo("浙A68959");
		userModel.setSignature("我有车");
		userModel.setAllowInvited("y");
		List<TagModel> tags=new ArrayList<TagModel>();
		
		TagModel e=new TagModel("68959","user","content");
		tags.add(e);
		userModel.setTags(tags);
		service.registry(requestContext, userModel);
	}

	public void testUpdate(){
		service=context.getBean(UserService.class);
		RequestContext requestContext=getContext("userId", "userName", new double[]{1d,1d});
		UserModel userModel=new UserModel();
		userModel.setId("54ce2a56a826b56bba1fa60a");
//		userModel.setId("54ce2a56a826b56bba1fa60a1");
//		userModel.setTargetId("68959");
//		userModel.setSource("alibaba");
//		userModel.setName("司要民");
//		userModel.setNickName("六冲");
//		userModel.setPlateNo("浙A68959");
//		userModel.setSignature("我有车");
		userModel.setAllowInvited("y");
		List<TagModel> tags=new ArrayList<TagModel>();
		
		TagModel e=new TagModel("68959","user","content");
		tags.add(e);
		userModel.setTags(tags);
		service.update(requestContext, userModel);
		Response<UserModel> res = service.getUserByUserId(requestContext, userModel.getId());
		System.out.println(res.getValue());
		TagModel t=new TagModel("68959","user","content");
		t.setIsDeleted(BooleanAbout.y);
		tags.add(t);
		service.updateTages(requestContext, userModel.getId(), tags);
		res = service.getUserByUserId(requestContext, userModel.getId());
		System.out.println(res);
	}
}

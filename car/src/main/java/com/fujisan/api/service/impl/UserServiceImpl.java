package com.fujisan.api.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fujisan.api.RequestContext;
import com.fujisan.api.Response;
import com.fujisan.api.service.UserService;
import com.fujisan.api.service.asserts.Assert;
import com.fujisan.api.service.asserts.DomainServiceAssert;
import com.fujisan.common.BooleanAbout;
import com.fujisan.common.BusiTypeEnum;
import com.fujisan.model.TagModel;
import com.fujisan.model.UserModel;
import com.fujisan.repository.TagRepository;
import com.fujisan.repository.UserRepository;
import com.google.common.collect.Lists;

/**
 * 用户信息
 * 
 * @author siyaomin
 *
 */
@Service
public class UserServiceImpl implements UserService {
	private static final Logger log = Logger.getLogger(UserServiceImpl.class);
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private DomainServiceAssert<UserModel> userAssert;
	@Autowired
	private DomainServiceAssert<TagModel> tagAssert;

	@Override
	public Response<UserModel> registry(RequestContext requestContext, UserModel userModel) {
		String seq=requestContext.getSeq();
		log.info("[user] registry start "+seq);
		Assert.notNull(userModel);
		Assert.notNull(userModel.getTargetId());
		Assert.notNull(userModel.getTargetId());
		UserModel user = userRepository.findByTarget(userModel.getTargetId(),userModel.getSource());
		if (user != null) {
			Assert.exists("该用户已经注册过了");
		}
		userAssert.checkParams(requestContext, userModel, BusiTypeEnum.create);
		userModel.setCreator(requestContext.getUserModel().getName());
		userModel.setGmtCreate(new Date());
		userModel.setModifier(requestContext.getUserModel().getName());
		userModel.setIsDeleted(BooleanAbout.n);
		userRepository.save(userModel);
		if(CollectionUtils.isNotEmpty(userModel.getTags())){
			for(TagModel t:userModel.getTags()){
				tagAssert.checkParams(requestContext, t, BusiTypeEnum.create);
				t.setTargetId(userModel.getId());
				t.setTargetType("user");
				tagRepository.save(t);
			}
		}
		log.info("[user] registry ok "+seq);
		Response<UserModel> result = new Response<UserModel>(true, "注册成功");
		result.setValue(userModel);
		return result;
	}

	@Override
	public Response<Boolean> update(RequestContext requestContext, UserModel userModel) {
		String seq=requestContext.getSeq();
		log.info("[user] update start "+seq);
		userAssert.exists(requestContext, userModel.getId());
		userAssert.enable(requestContext, userModel, BusiTypeEnum.update);
		userModel.setGmtModified(new Date());
		userModel.setModifier(requestContext.getUserModel().getName());
		userRepository.save(userModel);
		log.info("[user] update ok "+seq);
		return new Response<Boolean>(true, StringUtils.EMPTY);
	}

	@Override
	public Response<Boolean> updateTages(RequestContext requestContext, String id, List<TagModel> tags) {
		String seq=requestContext.getSeq();
		log.info("[user] updateTages start "+seq);
		Assert.notEmpty(tags);
		userAssert.exists(requestContext, id);

		UserModel userModel = new UserModel();
		userModel.setId(id);
		userAssert.enable(requestContext, userModel, BusiTypeEnum.update);
		for (TagModel t : tags) {
			if (t.getId() == null) {
				t.setGmtCreate(new Date());
				t.setCreator(requestContext.getUserModel().getName());
				t.setIsDeleted(BooleanAbout.n);
				t.setCreatorId(requestContext.getUserModel().getId());
				t.setGmtModified(new Date());
				t.setModifier(requestContext.getUserModel().getName());
				tagAssert.checkParams(requestContext, t, BusiTypeEnum.create);
			}else{
				t.setGmtModified(new Date());
				t.setModifier(requestContext.getUserModel().getName());
			}
		}
		for (TagModel t : tags) {
			tagRepository.save(t);
		}
		log.info("[user] updateTages ok "+seq);
		return new Response<Boolean>(true, StringUtils.EMPTY);
	}

	@Override
	public Response<UserModel> getUserByUserId(RequestContext requestContext, String id) {
		UserModel user = userRepository.findOne(id,UserModel.class);
		if (user != null) {
			user.setTags(tagRepository.getTagsByUserId(id));
		}
		return new Response<UserModel>(true, user, StringUtils.EMPTY);
	}

	@Override
	public Response<UserModel> getSingle(RequestContext requestContext, UserModel userModel) {
		UserModel user = userRepository.findOne(userModel, Lists.newArrayList("targetId","source"), UserModel.class);
		Response<UserModel> result=new Response<UserModel>(true,"获取成功");
		result.setValue(user);
		return result;
	}
}
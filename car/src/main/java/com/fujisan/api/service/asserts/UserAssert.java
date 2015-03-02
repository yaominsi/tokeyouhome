package com.fujisan.api.service.asserts;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fujisan.api.RequestContext;
import com.fujisan.api.service.asserts.exception.AssertException;
import com.fujisan.common.BeanHelper;
import com.fujisan.common.BusiTypeEnum;
import com.fujisan.common.OperateTypeEnum;
import com.fujisan.model.UserModel;
import com.fujisan.repository.UserRepository;

/**
 * 司机邀请的断言
 * 
 * @author siyaomin
 *
 */
@Component
public class UserAssert implements DomainServiceAssert<UserModel> {
	@Autowired
	private UserRepository userRepository;
	private final BusiTypeEnum scene = BusiTypeEnum.user;

	// #set
	@Override
	public void checkParams(RequestContext requestContext, UserModel model, BusiTypeEnum busiTypeEnum) {
		Assert.notNull(model);
		if (OperateTypeEnum.create.equals(busiTypeEnum)) {
			String result = BeanHelper.checkFieldsNotEmpty(model, model.needFieldsForCreate());
			if (StringUtils.isNotBlank(result)) {
				throw new AssertException(scene, OperateTypeEnum.create, "字段中有空值", model);
			}
			return;
		}

	}

	@Override
	public void exists(RequestContext requestContext, String id) {
		UserModel res = userRepository.findOne(id,UserModel.class);
		if (res == null) {
			throw new AssertException(scene, OperateTypeEnum.find, "找不到指定的用户", id);
		}
	}

	@Override
	public void enable(RequestContext requestContext, UserModel model, BusiTypeEnum busiTypeEnum) {
		Assert.notNull(model);
		if (BusiTypeEnum.update.equals(busiTypeEnum)) {
			UserModel res = userRepository.findOne(model.getId(),UserModel.class);
			if (res == null) {
				throw new AssertException(scene, OperateTypeEnum.update, "找不到指定的用户", model.getId());
			}
		}
	}

}

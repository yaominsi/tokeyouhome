package com.fujisan.api.service.asserts;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fujisan.api.RequestContext;
import com.fujisan.api.service.asserts.exception.AssertException;
import com.fujisan.common.BeanHelper;
import com.fujisan.common.BusiTypeEnum;
import com.fujisan.common.OperateTypeEnum;
import com.fujisan.model.BaseModel;
import com.fujisan.model.ScopeModel;
import com.fujisan.repository.ScopeRepository;
import com.google.common.collect.Lists;

/**
 * 司机邀请的断言
 * 
 * @author siyaomin
 *
 */
@Component("scopeAssert")
public class ScopeAssert implements DomainServiceAssert<ScopeModel> {
	@Autowired
	private ScopeRepository scopeRepository;
	private final BusiTypeEnum scene = BusiTypeEnum.scope;

	// #set
	@Override
	public void checkParams(RequestContext requestContext, ScopeModel model, BusiTypeEnum busiTypeEnum) {
		Assert.notNull(model);
		if (OperateTypeEnum.create.equals(busiTypeEnum)) {
			Assert.notEmpty(model.getNodes(),"结点为空");
			String result = BeanHelper.checkFieldsNotEmpty(model, model.needFieldsForCreate());
			if (StringUtils.isNotBlank(result)) {
				throw new AssertException(scene, OperateTypeEnum.create, "字段中有空值", model);
			}
			return;
		}

	}

	@Override
	public void exists(RequestContext requestContext, String id) {
		ScopeModel res = scopeRepository.findOne(id,ScopeModel.class);
		if (res == null) {
			throw new AssertException(scene, OperateTypeEnum.find, "找不到指定的区域", id);
		}
	}

	@Override
	public void enable(RequestContext requestContext, ScopeModel model, BusiTypeEnum busiTypeEnum) {
		Assert.notNull(model);
		if (BusiTypeEnum.update.equals(busiTypeEnum)) {
			ScopeModel res = scopeRepository.findOne(model.getId(),ScopeModel.class);
			if (res == null) {
				throw new AssertException(scene, OperateTypeEnum.update, "找不到指定的区域", model.getId());
			}
			if (!res.getCreatorId().equals(requestContext.getUserModel().getId())) {
				throw new AssertException(scene, OperateTypeEnum.update, "当前操作人不是创建人", res.getCreatorId());
			}
		}
		if (BusiTypeEnum.create.equals(busiTypeEnum)) {
			ScopeModel res = scopeRepository.findOne(model, Lists.newArrayList(BaseModel.final_creatorId,"name"),ScopeModel.class);
			if (res!=null){
				throw new AssertException(scene, OperateTypeEnum.update, "已创建同名的区域", model.getId());
			}
		}
	}

}

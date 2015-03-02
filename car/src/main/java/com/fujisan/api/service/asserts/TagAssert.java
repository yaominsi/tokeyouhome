package com.fujisan.api.service.asserts;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fujisan.api.RequestContext;
import com.fujisan.api.service.asserts.exception.AssertException;
import com.fujisan.common.BeanHelper;
import com.fujisan.common.BusiTypeEnum;
import com.fujisan.common.OperateTypeEnum;
import com.fujisan.model.TagModel;
import com.fujisan.repository.TagRepository;

/**
 * 司机邀请的断言
 * 
 * @author siyaomin
 *
 */
@Component
public class TagAssert implements DomainServiceAssert<TagModel> {
	@Autowired
	private TagRepository tagRepository;
	private final BusiTypeEnum scene = BusiTypeEnum.tag;

	// #set
	@Override
	public void checkParams(RequestContext requestContext, TagModel model, BusiTypeEnum busiTypeEnum) {
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
		TagModel res = tagRepository.findOne(id,TagModel.class);
		if (res == null) {
			throw new AssertException(scene, OperateTypeEnum.find, "找不到指定的标签", id);
		}
	}

	@Override
	public void enable(RequestContext requestContext, TagModel model, BusiTypeEnum busiTypeEnum) {
		Assert.notNull(model);
		if (BusiTypeEnum.update.equals(busiTypeEnum)) {
			TagModel res = tagRepository.findOne(model.getId(),TagModel.class);
			if (res == null) {
				throw new AssertException(scene, OperateTypeEnum.update, "找不到指定的区域", model.getId());
			}
			if (!res.getCreatorId().equals(requestContext.getUserModel().getId())) {
				throw new AssertException(scene, OperateTypeEnum.update, "当前操作人不是创建人", res.getCreatorId());
			}
		}
	}

}

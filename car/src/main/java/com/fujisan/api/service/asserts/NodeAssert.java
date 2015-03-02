package com.fujisan.api.service.asserts;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fujisan.api.RequestContext;
import com.fujisan.api.service.asserts.exception.AssertException;
import com.fujisan.common.BeanHelper;
import com.fujisan.common.BusiTypeEnum;
import com.fujisan.common.OperateTypeEnum;
import com.fujisan.model.NodeModel;
import com.fujisan.repository.NodeRepository;

/**
 * 司机邀请的断言
 * 
 * @author siyaomin
 *
 */
@Component
public class NodeAssert implements DomainServiceAssert<NodeModel> {
	@Autowired
	private NodeRepository nodeRepository;
	private final BusiTypeEnum scene = BusiTypeEnum.scope;

	// #set
	@Override
	public void checkParams(RequestContext requestContext, NodeModel model, BusiTypeEnum busiTypeEnum) {
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
		NodeModel res = nodeRepository.findOne(id,NodeModel.class);
		if (res == null) {
			throw new AssertException(scene, OperateTypeEnum.find, "找不到指定的区域", id);
		}
	}

	@Override
	public void enable(RequestContext requestContext, NodeModel model, BusiTypeEnum busiTypeEnum) {
		Assert.notNull(model);
		if (BusiTypeEnum.update.equals(busiTypeEnum)) {
			NodeModel res = nodeRepository.findOne(model.getId(),NodeModel.class);
			if (res == null) {
				throw new AssertException(scene, OperateTypeEnum.update, "找不到指定的区域", model.getId());
			}
			if (!res.getCreatorId().equals(requestContext.getUserModel().getId())) {
				throw new AssertException(scene, OperateTypeEnum.update, "当前操作人不是创建人", res.getCreatorId());
			}
		}
	}

}

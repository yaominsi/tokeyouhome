package com.fujisan.api.service.asserts;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fujisan.api.RequestContext;
import com.fujisan.api.service.asserts.exception.AssertException;
import com.fujisan.common.BeanHelper;
import com.fujisan.common.BooleanAbout;
import com.fujisan.common.BusiTypeEnum;
import com.fujisan.common.OperateTypeEnum;
import com.fujisan.model.LightUpModel;
import com.fujisan.repository.LightUpRepository;
/**
 * 司机邀请的断言
 * @author siyaomin
 *
 */
@Component
public class LightUpAssert implements DomainServiceAssert<LightUpModel> {
	@Autowired
	private LightUpRepository lightUpRepository;
	private final BusiTypeEnum scene=BusiTypeEnum.lightup;
	// #set

	@Override
	public void checkParams(RequestContext requestContext, LightUpModel model, BusiTypeEnum busiTypeEnum) {
		Assert.notNull(model);
		if (BusiTypeEnum.create.equals(busiTypeEnum)) {
			String result = BeanHelper.checkFieldsNotEmpty(model, model.needFieldsForCreate());
			if (StringUtils.isNotBlank(result)) {
				throw new AssertException(scene,OperateTypeEnum.create, "字段中有空值", model);
			}
			return;
		}
		
	}

	@Override
	public void exists(RequestContext requestContext, String id) {
			LightUpModel res = lightUpRepository.findOne(id,LightUpModel.class);
			if (res==null) {
				throw new AssertException(scene,OperateTypeEnum.find, "找不到指定的对象", id);
			}
	}

	@Override
	public void enable(RequestContext requestContext, LightUpModel model, BusiTypeEnum busiTypeEnum) {
		Assert.notNull(model);
		LightUpModel res =null;
		if (model.getId()!=null) {
			res = lightUpRepository.findOne(model.getId(),LightUpModel.class);
			if (res==null) {
				throw new AssertException(scene,OperateTypeEnum.update, "找不到指定的对象", model.getId());
			}
			if (!res.getCreatorId().equals(requestContext.getUserModel().getId())) {
				throw new AssertException(scene,OperateTypeEnum.update, "当前操作人不是创建人", res.getCreatorId());
			}
		}
		if (BusiTypeEnum.update.equals(busiTypeEnum)) {
			Assert.notNull(model.getId());
		}else if (BusiTypeEnum.lightoff.equals(busiTypeEnum)){
			Assert.notNull(model.getId());
			if (!BooleanAbout.isTrue(res.getIsLightUp())) {
				throw new AssertException(scene,OperateTypeEnum.update, "已是关闭的状态", model.getId());
			}
		}else if (BusiTypeEnum.lightup.equals(busiTypeEnum)){
			Assert.notNull(model.getId());
			if (BooleanAbout.isTrue(res.getIsLightUp())) {
				throw new AssertException(scene,OperateTypeEnum.update, "已是点亮的状态", model.getId());
			}
		}
	}

//setter
}

package com.fujisan.api.service.asserts;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.fujisan.api.RequestContext;
import com.fujisan.api.service.asserts.exception.AssertException;
import com.fujisan.common.BeanHelper;
import com.fujisan.common.BooleanAbout;
import com.fujisan.common.BusiTypeEnum;
import com.fujisan.common.NumberAbout;
import com.fujisan.common.OperateTypeEnum;
import com.fujisan.common.OrderStatusEnum;
import com.fujisan.common.TimeUtil;
import com.fujisan.model.BaseModel;
import com.fujisan.model.LightUpModel;
import com.fujisan.model.OrderModel;
import com.fujisan.model.UserModel;
import com.fujisan.repository.LightUpRepository;
import com.fujisan.repository.OrderRepository;
import com.fujisan.repository.UserRepository;
import com.google.common.collect.Lists;

/**
 * 邀请的断言
 * 
 * @author siyaomin
 *
 */
@Component("orderAssert")
public class OrderAssert implements DomainServiceAssert<OrderModel> {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LightUpRepository lightUpRepository;
	private final BusiTypeEnum scene=BusiTypeEnum.order;
	/**
	 * 参数 验证
	 */
	public void checkParams(RequestContext requestContext, OrderModel model, BusiTypeEnum busiTypeEnum) {
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
			OrderModel res = orderRepository.findOne(id,OrderModel.class);
			requestContext.setChangeFrom(res);
			if (res==null) {
				throw new AssertException(scene,OperateTypeEnum.find, "找不到指定的预约单", id);
			}
	}
	
	@Override
	public void enable(RequestContext requestContext, OrderModel model, BusiTypeEnum busiTypeEnum) {
		Assert.notNull(requestContext.getUserModel().getId(),"当前操作人为空");
		//创建
		if(BusiTypeEnum.create.equals(busiTypeEnum)){
			//被邀请人是否打开开关
			UserModel userModel = userRepository.findOne(model.getToUserId(),UserModel.class);
			if (userModel==null) {
				throw new AssertException(scene,OperateTypeEnum.create, "找不到指定的发起人", model.getToUserId());
			}
			if(!BooleanAbout.isTrue(userModel.getAllowInvited())){
				throw new AssertException(scene,OperateTypeEnum.create, "该同学不接收预约信息", model.getToUserId());
			}
			//结点是否点亮
			LightUpModel lightUpModel=lightUpRepository.findOne(model.getLightUpId(),LightUpModel.class);
			if(lightUpModel==null){
				throw new AssertException(scene,OperateTypeEnum.create, "找不到点亮信息", model.getLightUpId());
			}
			if(!BooleanAbout.isTrue(lightUpModel.getIsLightUp())){
				throw new AssertException(scene,OperateTypeEnum.create, "未点亮", model.getLightUpId());
			}
			if(lightUpModel.getGmtTill()!=null&&
					System.currentTimeMillis()>lightUpModel.getGmtTill().getTime()
					){
				throw new AssertException(scene,OperateTypeEnum.create, "信息已过期", lightUpModel.getGmtTill());
			}
			//时间限制，防骚扰
			List<String> qfileds = Lists.newArrayList("fromUserId","toUserId");
			OrderModel last=orderRepository.findLast(model,qfileds,Direction.DESC,Lists.newArrayList(BaseModel.final_gmtCreate));
			if (last!=null&&TimeUtil.isInMins(last.getGmtCreate(), NumberAbout.INVITE_SAME_PERSON_INTERVEL_MINITUS)) {
				throw new AssertException(scene,OperateTypeEnum.create, "参加同一个活动至少间隔"+NumberAbout.INVITE_SAME_PERSON_INTERVEL_MINITUS+"分钟", last.getGmtCreate());
			}
		}else if(BusiTypeEnum.reject.equals(busiTypeEnum)){
			//有没有被邀请
			OrderModel res = orderRepository.findOne(model.getId(),OrderModel.class);
			Assert.notNull(requestContext.getUserModel().getId());
			if(requestContext.getUserModel().getId()!=null&&!requestContext.getUserModel().getId().equals(res.getToUserId())){
				throw new AssertException(scene,OperateTypeEnum.update, "被预约人不是当前操作人", model.getToUserId());
			}
			//创建，已读可以拒绝
			if(!OrderStatusEnum.created.equals(res.getStatus())
					&&!OrderStatusEnum.read.equals(res.getStatus())){
				throw new AssertException(scene,OperateTypeEnum.update, "当前状态，不能拒绝。", res.getStatus());
			}
		}else if(BusiTypeEnum.accept.equals(busiTypeEnum)){
			//有没有被邀请
			OrderModel res = orderRepository.findOne(model.getId(),OrderModel.class);
			Assert.notNull(res, "找不到预约单");
			if(requestContext.getUserModel().getId()!=null&&!requestContext.getUserModel().getId().equals(res.getToUserId())){
				throw new AssertException(scene,OperateTypeEnum.update, "当前操作人不是活动发起人", model.getToUserId());
			}
			//创建，已读可以拒绝
			if(!OrderStatusEnum.created.equals(res.getStatus())
					&&!OrderStatusEnum.read.equals(res.getStatus())){
				throw new AssertException(scene,OperateTypeEnum.update, "当前状态，不能接受。", res.getStatus());
			}
		}else if(BusiTypeEnum.start.equals(busiTypeEnum)){
			//有没有被邀请
			OrderModel res = orderRepository.findOne(model.getId(),OrderModel.class);
			if(requestContext.getUserModel().getId()!=null&&!requestContext.getUserModel().getId().equals(res.getToUserId())){
				throw new AssertException(scene,OperateTypeEnum.update, "当前操作人不是活动发起人", model.getToUserId());
			}
			//创建，已读可以拒绝
			if(!OrderStatusEnum.accept.equals(res.getStatus())){
				throw new AssertException(scene,OperateTypeEnum.update, "请先接受预约。", res.getStatus());
			}
		}else if(BusiTypeEnum.cancel.equals(busiTypeEnum)){
			//有没有被邀请
			OrderModel res = orderRepository.findOne(model.getId(),OrderModel.class);
			
			if(!requestContext.getUserModel().getId().equals(res.getFromUserId())&&!requestContext.getUserModel().getId().equals(res.getToUserId())){
				throw new AssertException(scene,OperateTypeEnum.update, "当前操作人不是预约发起,也不是活动发起人，不能做取消操作",res);
			}
			//上车后不能取消
			if(OrderStatusEnum.gotOn.equals(res.getStatus())
					||OrderStatusEnum.finish.equals(res.getStatus())){
				throw new AssertException(scene,OperateTypeEnum.update, "已开始不能取消。", res.getStatus());
			}
			if(OrderStatusEnum.cancel.equals(res.getStatus())){
				throw new AssertException(scene,OperateTypeEnum.update, "已经取消过了。", res.getStatus());
			}
		}else if(BusiTypeEnum.finish.equals(busiTypeEnum)){
			//有没有被邀请
			OrderModel res = orderRepository.findOne(model.getId(),OrderModel.class);
			if(!requestContext.getUserModel().getId().equals(res.getToUserId())){
				throw new AssertException(scene,OperateTypeEnum.update, "当前操作人不是预约人，不能做完成操作", res.getToUserId());
			}
			//上车后不能取消
			if(!OrderStatusEnum.gotOn.equals(res.getStatus())
			 &&!OrderStatusEnum.accept.equals(res.getStatus())
					){
				throw new AssertException(scene,OperateTypeEnum.update, "当前状态不能完结预约单。", res.getStatus());
			}
		}
	}
	
	// #
}

package com.fujisan.notify;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fujisan.api.RequestContext;
import com.fujisan.api.service.NotifyService;
import com.fujisan.api.service.asserts.Assert;
import com.fujisan.common.NotifyTypeEnum;
import com.fujisan.common.OrderStatusEnum;
import com.fujisan.model.NotifyModel;
import com.fujisan.model.OrderModel;
import com.fujisan.model.UserModel;
import com.fujisan.repository.UserRepository;

/**
 * 模型变更处理接口
 * 
 * @author siyaomin
 *
 * @param <T>
 */
@Component
public class OrderChangeDispose implements ModelChangeDispose<OrderModel> {
	private static final Logger log = Logger.getLogger(OrderChangeDispose.class);
	@Autowired
	private NotifyService notifyService;
	@Autowired
	private UserRepository userRepository;

	@Override
	public void dispose(RequestContext requestContext, Object[] args) {
		notify(requestContext, (OrderModel) requestContext.getChangeFrom(), (OrderModel) requestContext.getChangeTo());
	}

	private void notify(RequestContext requestContext,OrderModel from, OrderModel to){
		Assert.notNull(to,"order change but not set changeTo property of requestContext");
		NotifyModel notify=new NotifyModel();
		if (from!=null&&to.getStatus().equals(from.getStatus())) {
			log.info("状态没有变更"+requestContext.getSeq());
			return ;
		}
		notify.setTargetId(to.getId());
		notify.setType(NotifyTypeEnum.order);
		notify.setFromUserId(to.getOperatorId());
		UserModel optUser = userRepository.findOne(to.getOperatorId(), UserModel.class);
		//新创建,通知有预约
		if(OrderStatusEnum.created.equals(to.getStatus())){
			notify.setToUserId(to.getToUserId());
			notify.setContent("收到一个来自"+optUser.getName()+"的预约");
			notifyService.create(requestContext,notify);
			log.info(notify.getToUserId()+"收到一个来自"+notify.getFromUserId()+"的预约,seq:"+requestContext.getSeq());
			//接受预约要通知活动发起人
		}else if(to.getStatus().equals(OrderStatusEnum.accept)){
			notify.setToUserId(to.getFromUserId());
			notify.setContent(optUser.getName()+"接受了你的预约");
			notifyService.create(requestContext,notify);
			log.info(optUser.getName()+"接受了"+notify.getFromUserId()+"的预约,seq:"+requestContext.getSeq());
			//接受预约要通知活动发起人
		}else if(to.getStatus().equals(OrderStatusEnum.cancel)){
			notify.setToUserId(to.getFromUserId());
			notify.setContent(optUser.getName()+"取消了预约");
			notifyService.create(requestContext,notify);
			log.info(optUser.getName()+"取消了预约,seq:"+requestContext.getSeq());
			//接受预约要通知活动发起人
		}else if(to.getStatus().equals(OrderStatusEnum.finish)){
			//TODO 如果收到赞就告诉对方
			notify.setToUserId(to.getFromUserId());
			notify.setContent(to.getOperatorId()+"完结了一个预约");
			notifyService.create(requestContext,notify);
			log.info(optUser.getName()+"完结了一个预约seq:"+requestContext.getSeq());
			//拒绝
		}else if(to.getStatus().equals(OrderStatusEnum.rejected)){
			notify.setToUserId(to.getFromUserId());
			notify.setContent(optUser.getName()+"拒绝了你的预约");
			notifyService.create(requestContext,notify);
			log.info(optUser.getName()+"拒绝了你的预约,seq:"+requestContext.getSeq());
		}
	}
}

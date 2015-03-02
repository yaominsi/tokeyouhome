package com.fujisan.api.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fujisan.api.OrderDetail;
import com.fujisan.api.RequestContext;
import com.fujisan.api.Response;
import com.fujisan.api.service.OrderService;
import com.fujisan.api.service.asserts.Assert;
import com.fujisan.api.service.asserts.DomainServiceAssert;
import com.fujisan.api.service.asserts.exception.AssertException;
import com.fujisan.common.BooleanAbout;
import com.fujisan.common.BusiTypeEnum;
import com.fujisan.common.NotifyTypeEnum;
import com.fujisan.common.OrderStatusEnum;
import com.fujisan.model.LightUpModel;
import com.fujisan.model.OrderModel;
import com.fujisan.model.ScopeModel;
import com.fujisan.model.UserModel;
import com.fujisan.notify.NeedNotify;
import com.fujisan.repository.LightUpRepository;
import com.fujisan.repository.OrderRepository;
import com.fujisan.repository.ScopeRepository;
import com.fujisan.repository.UserRepository;

/**
 * 乘客预约 司机接受订单上限
 * 
 * @author siyaomin
 *
 */
@Service
public class OrderServiceImpl implements OrderService {
	private Logger log = Logger.getLogger(OrderServiceImpl.class);
	@Autowired
	private DomainServiceAssert<OrderModel> orderAssert;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ScopeRepository scopeRepository;
	@Autowired
	private LightUpRepository lightUpRepository;
	@Autowired
	private UserRepository userRepository;
	@Override
	@NeedNotify(NotifyTypeEnum.order)
	public Response<Boolean> create(RequestContext requestContext, OrderModel orderModel) {
		String seq = requestContext.getSeq();
		log.info("[order]create " + seq);
		// checkParams
		orderModel.setCreator(requestContext.getUserModel().getName());
		orderModel.setCreatorId(requestContext.getUserModel().getId());
		orderModel.setModifier(requestContext.getUserModel().getName());
		orderModel.setGmtCreate(new Date());
		orderModel.setGmtModified(new Date());
		orderModel.setStatus(OrderStatusEnum.created);
		orderModel.setGmtStatusTime(new Date());
		orderModel.setOperatorId(requestContext.getUserModel().getId());
		orderModel.setIsDeleted(BooleanAbout.n);
		//
		Assert.notNull(orderModel.getLightUpId(),"请指定点亮区");
		LightUpModel lightUp = lightUpRepository.findOne(orderModel.getLightUpId(), LightUpModel.class);
		Assert.notNull(lightUp,"找不到指定的区域");
		orderModel.setToUserId(lightUp.getCreatorId());
		orderAssert.checkParams(requestContext, orderModel, BusiTypeEnum.create);
		// 判断是否已经存在，如预约一个人15分钟后才可以再预约同一个人
		orderAssert.enable(requestContext, orderModel, BusiTypeEnum.create);
		orderRepository.save(orderModel);
		
		//更新lightup数值信息
		lightUpRepository.findAndInc(orderModel.getLightUpId(), LightUpModel.SIGNUPS, orderModel.getSeeds(), LightUpModel.class);
		requestContext.setChangeTo(orderModel);
		log.info("[order]create ok" + seq);
		return new Response<Boolean>(true, "预约已发出");
	}

	@Override
	@NeedNotify(NotifyTypeEnum.order)
	public Response<Boolean> accept(RequestContext requestContext, String orderId, String note) {
		String seq = requestContext.getSeq();
		log.info("[order] accept " + seq);
		orderAssert.exists(requestContext, orderId);
		OrderModel model = new OrderModel();
		model.setId(orderId);
		model.setNote(note);
		//
		orderAssert.enable(requestContext, model, BusiTypeEnum.accept);
		// 接受邀请
		model.setStatus(OrderStatusEnum.accept);
		model.setModifier(requestContext.getUserModel().getName());
		model.setGmtModified(new Date());
		model.setToUserId(requestContext.getUserModel().getId());
		model.setGmtStatusTime(new Date());
		model.setOperatorId(requestContext.getUserModel().getId());
		orderRepository.save(model);
		//更新lightup数值信息
		model=orderRepository.findOne(orderId, OrderModel.class);
		lightUpRepository.findAndInc(model.getLightUpId(), LightUpModel.ACCEPTS, model.getSeeds(), LightUpModel.class);
		requestContext.setChangeTo(model);
		log.info("[order] accept ok " + seq);
		return new Response<Boolean>(true, "接收预约");
	}

	@Override
	@NeedNotify(NotifyTypeEnum.order)
	public Response<Boolean> finish(RequestContext requestContext, OrderModel orderModel) {
		String seq = requestContext.getSeq();
		log.info("[order]finish " + seq);
		orderAssert.exists(requestContext, orderModel.getId());
		// 能否finish
		orderAssert.enable(requestContext, orderModel, BusiTypeEnum.finish);
		// finish
		// 其他操作
		orderModel.setStatus(OrderStatusEnum.finish);
		orderModel.setModifier(requestContext.getUserModel().getName());
		orderModel.setGmtModified(new Date());
		orderModel.setGmtStatusTime(new Date());
		orderModel.setOperatorId(requestContext.getUserModel().getId());
		orderRepository.save(orderModel);
		requestContext.setChangeTo(orderModel);
		log.info("[order]finish ok " + seq);
		return new Response<Boolean>(true, "预约已完成");
	}

	@Override
	@NeedNotify(NotifyTypeEnum.order)
	public Response<Boolean> reject(RequestContext requestContext, String orderId, String note) {
		String seq = requestContext.getSeq();
		log.info("[order]reject " + seq);
		orderAssert.exists(requestContext, orderId);
		// 能否reject
		OrderModel model = new OrderModel();
		model.setNote(note);
		model.setId(orderId);
		orderAssert.enable(requestContext, model, BusiTypeEnum.reject);
		// finish
		// 其他操作
		model.setModifier(requestContext.getUserModel().getName());
		model.setGmtModified(new Date());
		model.setStatus(OrderStatusEnum.rejected);
		model.setGmtStatusTime(new Date());
		model.setOperatorId(requestContext.getUserModel().getId());
		orderRepository.save(model);
		requestContext.setChangeTo(model);
		log.info("[order]reject ok " + seq);
		return new Response<Boolean>(true, "已拒绝");
	}
	@NeedNotify(NotifyTypeEnum.order)
	public Response<Boolean> startOff(RequestContext requestContext, String orderId, String note) {
		String seq = requestContext.getSeq();
		log.info("[order]gotOn " + seq);
		orderAssert.exists(requestContext, orderId);
		OrderModel model = new OrderModel();
		model.setId(orderId);
		model.setNote(note);
		//
		orderAssert.enable(requestContext, model, BusiTypeEnum.start);
		// 接受邀请
		model.setStatus(OrderStatusEnum.gotOn);
		model.setModifier(requestContext.getUserModel().getName());
		model.setGmtModified(new Date());
		model.setGmtStatusTime(new Date());
		model.setOperatorId(requestContext.getUserModel().getId());
		orderRepository.save(model);
		requestContext.setChangeTo(model);
		log.info("[order]gotOn ok " + seq);
		return new Response<Boolean>(true, "已开始");
	}

	// 司机取消邀请
	@NeedNotify(NotifyTypeEnum.order)
	public Response<Boolean> cancel(RequestContext requestContext, String orderId, String note) {
		String seq = requestContext.getSeq();
		log.info("[order]cancel " + seq);
		orderAssert.exists(requestContext, orderId);
		OrderModel model = new OrderModel();
		model.setId(orderId);
		model.setNote(note);
		//
		orderAssert.enable(requestContext, model, BusiTypeEnum.cancel);
		// 接受邀请
		model.setStatus(OrderStatusEnum.cancel);
		model.setModifier(requestContext.getUserModel().getName());
		model.setGmtModified(new Date());
		model.setGmtStatusTime(new Date());
		model.setOperatorId(requestContext.getUserModel().getId());
		orderRepository.save(model);
		requestContext.setChangeTo(model);
		model=orderRepository.findOne(orderId, OrderModel.class);
		lightUpRepository.findAndInc(model.getLightUpId(), LightUpModel.ACCEPTS, model.getSeeds()*-1, LightUpModel.class);
		log.info("[order]cancel ok " + seq);
		return new Response<Boolean>(true, "已取消");
	}

	@Override
	public Page<OrderModel> find(RequestContext requestContext, OrderModel orderModel, List<String> properties,
			Direction direction, List<String> sortProperties, Pageable pageable) {
		return orderRepository.findByPage(orderModel, properties, direction, sortProperties, pageable,OrderModel.class);

	}
	@Override
	public Response<OrderDetail> detail(RequestContext requestContext, String id) {
		String seq = requestContext.getSeq();
		log.info("start query detail order for id="+id+",seq:" + seq);
		Response<OrderDetail> result=new Response<OrderDetail>();
		try {
			OrderModel order = orderRepository.findOne(id, OrderModel.class);
			OrderDetail detail=new OrderDetail();
			detail.setOrder(order);
			if (order!=null) {
				if(StringUtils.isNotBlank(order.getFromUserId()))
					detail.setFromUser(userRepository.findOne(order.getFromUserId(),UserModel.class));
				if(StringUtils.isNotBlank(order.getToUserId()))
					detail.setToUser(userRepository.findOne(order.getToUserId(),UserModel.class));
				if(StringUtils.isNotBlank(order.getLightUpId()));
					detail.setLightUp(lightUpRepository.findOne(order.getLightUpId(), LightUpModel.class));
				if(detail.getLightUp()!=null&&StringUtils.isNotBlank(detail.getLightUp().getScopeId()));
					detail.setScope(scopeRepository.findOne(detail.getLightUp().getScopeId(), ScopeModel.class));
			}
			result.setValue(detail);
			log.info("end query detail order for id="+id+",seq:" + seq);
		} catch (AssertException e) {
			result.setDesc(e.getMessage());
			result.setSuccess(false);
			log.error(e.getMessage(),e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setDesc("获取失败");
			log.error(e.getMessage()+",seq:"+seq,e);
		}
		return result;
	}
}

package com.fujisan.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fujisan.api.OrderDetail;
import com.fujisan.api.RequestContext;
import com.fujisan.api.Response;
import com.fujisan.api.service.OrderService;
import com.fujisan.api.service.asserts.exception.AssertException;
import com.fujisan.common.OrderStatusEnum;
import com.fujisan.model.BaseModel;
import com.fujisan.model.OrderModel;
import com.fujisan.request.SimplePageRequest;
import com.fujisan.web.login.NeedLogin;
import com.fujisan.web.login.RequestContextManager;
import com.google.common.collect.Lists;

/**
 * 用户action，登录注册
 * 
 * @author siyaomin
 *
 */
@Controller
public class OrderAction {
	private static final Logger log = Logger.getLogger(OrderAction.class);
	@Autowired
	private OrderService orderService;
	/**
	 * 下单
	 * @param orderModel
	 * @return
	 */
	@NeedLogin
	@RequestMapping(value = "/order/create", method = RequestMethod.POST)
	public @ResponseBody Response<Boolean> create(@RequestBody OrderModel orderModel) {
		Response<Boolean> result = new Response<Boolean>();
		try {
			orderModel.setFromUserId(RequestContextManager.current().getUserModel().getId());
			Response<Boolean> order = orderService.create(RequestContextManager.current(), orderModel);
			if (order.isSuccess()) {
				result.setDesc("订单创建成功");
			} else {
				result.setDesc("订单创建失败");
			}
		} catch (AssertException e) {
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setDesc(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setDesc("订单创建失败！");
		}
		return result;
	}
	/**
	 * 我发起的预约
	 * @return
	 */
	@RequestMapping(value = "/order/listFromMe", method = RequestMethod.POST)
	@NeedLogin
	public @ResponseBody Response<Page<OrderDetail>> listFromMe() {
		log.info("[scope] list :" + RequestContextManager.current().getUserModel().getName());
		Response<Page<OrderDetail>> result = new Response<Page<OrderDetail>>();
		try {
			Pageable pageable = new PageRequest(0, 10);
			OrderModel model = new OrderModel();
			model.setCreatorId(RequestContextManager.current().getUserModel().getId());
			Page<OrderModel> page = orderService.find(RequestContextManager.current(), model,
					Lists.newArrayList(BaseModel.final_creatorId), Direction.DESC,
					Lists.newArrayList(BaseModel.final_gmtModified), pageable);
			if (CollectionUtils.isNotEmpty(page.getContent())) {
				List<OrderDetail> content=new ArrayList<OrderDetail>();
				for(OrderModel e:page.getContent()){
					Response<OrderDetail> dr = orderService.detail(RequestContextManager.current(), e.getId());
					if(dr!=null)
						content.add(dr.getValue());
				}
				Page<OrderDetail> dpage= new PageImpl<OrderDetail>(content, pageable, page.getTotalElements());
				result.setValue(dpage);
			}
			result.setDesc("加载成功");
		} catch (AssertException e) {
			result.setSuccess(false);
			result.setDesc("加载失败," + e.getMessage() + "。");
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setDesc("加载失败！");
			log.error(e.getMessage(), e);
		}
		
		return result;
	}
	/**
	 * 邀请我的
	 * @param orderModel
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/order/listToMe", method = RequestMethod.POST)
	public @ResponseBody Response<Page<OrderModel>> listToMe(@RequestBody OrderModel orderModel,
			HttpServletResponse response) {
		log.info("[scope] list :" + RequestContextManager.current().getUserModel().getName());
		Response<Page<OrderModel>> result = new Response<Page<OrderModel>>();
		try {
			Pageable pageable = new PageRequest(0, 10);
			
			OrderModel model = new OrderModel();
			model.setCreatorId(RequestContextManager.current().getUserModel().getId());
			Page<OrderModel> page = orderService.find(RequestContextManager.current(), model,
					Lists.newArrayList(BaseModel.final_creatorId), Direction.DESC,
					Lists.newArrayList(BaseModel.final_gmtModified), pageable);
			result.setValue(page);
			result.setDesc("加载成功");
		} catch (AssertException e) {
			result.setSuccess(false);
			result.setDesc("加载失败," + e.getMessage() + "。");
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setDesc("加载失败！");
			log.error(e.getMessage(), e);
		}
		
		return result;
	}
	@NeedLogin
	@RequestMapping(value = "/order/listWithLightUpId", method = RequestMethod.POST)
	public @ResponseBody Response<Page<OrderDetail>> listWithLightUpId(@RequestBody SimplePageRequest<OrderModel> orderModel,
			HttpServletResponse response) {
		RequestContext requestContext = RequestContextManager.current();
		log.info("[scope] list :" + requestContext.getUserModel().getName());
		Response<Page<OrderDetail>> result = new Response<Page<OrderDetail>>();
		try {
			Pageable pageable = new PageRequest(0, 10);

			OrderModel model = orderModel.getValue();
			model.setCreatorId(requestContext.getUserModel().getId());
			Page<OrderModel> page = orderService.find(requestContext, model,
					Lists.newArrayList(BaseModel.final_lightUpId), Direction.DESC,
					Lists.newArrayList(BaseModel.final_gmtModified), pageable);
			
			if(CollectionUtils.isNotEmpty(page.getContent())){
				List<OrderDetail> content=new ArrayList<OrderDetail>();
				for(OrderModel e:page.getContent()){
					Response<OrderDetail> od = orderService.detail(requestContext, e.getId());
					if(od!=null)
						content.add(od.getValue());
				}
				Page<OrderDetail> detailPage=new PageImpl<OrderDetail>(content, pageable, page.getTotalElements());
				result.setValue(detailPage);
			}
			result.setDesc("加载成功");
		} catch (AssertException e) {
			result.setSuccess(false);
			result.setDesc("加载失败," + e.getMessage() + "。");
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setDesc("加载失败！");
			log.error(e.getMessage(), e);
		}

		return result;
	}
	@NeedLogin
	@RequestMapping(value = "/order/{id}/reject", method = RequestMethod.POST)
	public @ResponseBody Response<Boolean> reject(@PathVariable String id) {
		Response<Boolean> result = new Response<Boolean>();
		try {
			RequestContext requestContext=RequestContextManager.current();
			Response<Boolean> resp = orderService.reject(requestContext, id, "");//TODO note
			if (resp.isSuccess()) {
				result.setDesc("处理成功");
			} else {
				result.setDesc("处理失败");
			}
		} catch (AssertException e) {
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setDesc(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setDesc("处理失败！");
		}
		return result;
	}
	@NeedLogin
	@RequestMapping(value = "/order/{id}/accept", method = RequestMethod.POST)
	public @ResponseBody Response<Boolean> accept(@PathVariable String id) {
		Response<Boolean> result = new Response<Boolean>();
		try {
			RequestContext requestContext=RequestContextManager.current();
			Response<Boolean> resp = orderService.accept(requestContext, id, "");//TODO note
			if (resp.isSuccess()) {
				result.setDesc("处理成功");
			} else {
				result.setDesc("处理失败");
			}
		} catch (AssertException e) {
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setDesc(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setDesc("处理失败！");
		}
		return result;
	}
}

package com.fujisan.test.service;

import org.junit.Test;

import com.fujisan.api.RequestContext;
import com.fujisan.api.service.OrderService;
import com.fujisan.common.OrderStatusEnum;
import com.fujisan.model.OrderModel;
/**
 * ”√ªß≤‚ ‘
 * @author siyaomin
 *
 */
public class OrderServiceTest extends BaseTest{
	private OrderService service;
	
	@Test
	public void testCreate() {
		service=context.getBean(OrderService.class);
		RequestContext requestContext=getContext("54ce5c93a826aaf930e8297d", "userName", new double[]{1d,1d});
		OrderModel orderModel=new OrderModel();
		orderModel.setFromUserId("54ce5c93a826aaf930e8297d");
		orderModel.setLightUpId("54d780bba8268df34d087aa3");
		orderModel.setLightUpNodeId("lightUpNodeId");
		orderModel.setSeeds(12);
		orderModel.setStatus(OrderStatusEnum.created);
		service.create(requestContext, orderModel);
	}
	@Test
	public void testAccept() {
		service=context.getBean(OrderService.class);
		RequestContext requestContext=getContext("54ce5b47a82626475ab91584", "userName", new double[]{1d,1d});
		service.accept(requestContext, "54cf2bcca8261bfef07307aa", "notexxx");
	}
	@Test
	public void testReject() {
		service=context.getBean(OrderService.class);
		RequestContext requestContext=getContext("54ce5b47a82626475ab91584", "userName", new double[]{1d,1d});
		service.reject(requestContext, "54cf2bcca8261bfef07307aa", "notexxx");
	}
	@Test
	public void testFinish() {
		service=context.getBean(OrderService.class);
		RequestContext requestContext=getContext("54ce5b47a82626475ab91584", "userName", new double[]{1d,1d});
		OrderModel orderModel=new OrderModel();
		orderModel.setId("54cf2bcca8261bfef07307aa");
		orderModel.setNote("xxxxxxxx");
		orderModel.setScore("10");
		service.finish(requestContext, orderModel);
	}
	@Test
	public void testCancel() {
		service=context.getBean(OrderService.class);
		RequestContext requestContext=getContext("54ce5b47a82626475ab91584", "userName", new double[]{1d,1d});
		service.cancel(requestContext, "54cf2bcca8261bfef07307aa","note");
	}
}

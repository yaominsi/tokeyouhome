package com.fujisan.test.service;

import org.junit.Test;

import com.fujisan.api.RequestContext;
import com.fujisan.api.service.NotifyService;
import com.fujisan.common.NotifyTypeEnum;
import com.fujisan.model.NotifyModel;
/**
 * ”√ªß≤‚ ‘
 * @author siyaomin
 *
 */
public class NotifyServiceTest extends BaseTest{
	private NotifyService service;
	
	@Test
	public void testRegistry() {
		service=context.getBean(NotifyService.class);
		RequestContext requestContext=getContext("userId", "userName", new double[]{1d,1d});
		NotifyModel notifyModel=new NotifyModel();
		notifyModel.setContent("content");
		notifyModel.setFromUserId("fromUserId");
		notifyModel.setToUserId("toUserId");
		notifyModel.setTargetId("targetId");
		notifyModel.setType(NotifyTypeEnum.order);
		service.create(requestContext, notifyModel);
	}

}

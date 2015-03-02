package com.fujisan.test.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.fujisan.api.RequestContext;
import com.fujisan.api.service.LightUpService;
import com.fujisan.common.BooleanAbout;
import com.fujisan.common.DurationUnitEnum;
import com.fujisan.model.LightUpModel;
import com.fujisan.model.ScopeModel;
/**
 * ”√ªß≤‚ ‘
 * @author siyaomin
 *
 */
public class LightUpServiceTest extends BaseTest{
	private static final Logger log=Logger.getLogger(LightUpServiceTest.class);
	private LightUpService service;
	
	@Test
	public void testCreate() {
		service=context.getBean(LightUpService.class);
		RequestContext requestContext=getContext("54ce5b47a82626475ab91584", "userName", new double[]{1d,1d});
		ScopeModel scopeModel=new ScopeModel();
		scopeModel.setName("name");
		LightUpModel model=new LightUpModel("54d24360a8260fe5537e57f0","title",new Date(),new Date());
		model.setDuration(12);
		model.setIsLightUp(BooleanAbout.y);
		model.setDurationUnit(DurationUnitEnum.d);
		service.save(requestContext, model);
		log.info(model.getId());
	}
	@Test
	public void testLightUp() {
		service=context.getBean(LightUpService.class);
		RequestContext requestContext=getContext("54ce5b47a82626475ab91584", "userName", new double[]{1d,1d});
		service.lightUp(requestContext, "54d77da1a8262de711cbc3eb");
	}
	@Test
	public void testLightOff() {
		service=context.getBean(LightUpService.class);
		RequestContext requestContext=getContext("userId", "userName", new double[]{1d,1d});
		service.lightOff(requestContext, "54cf0d7ba8264a4d2b7d8ac9");
	}

}

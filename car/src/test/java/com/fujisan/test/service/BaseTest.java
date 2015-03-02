package com.fujisan.test.service;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fujisan.api.RequestContext;
import com.fujisan.common.LogSequence;
import com.fujisan.model.UserModel;

public class BaseTest extends TestCase {
	private static Logger log = Logger.getLogger(BaseTest.class);
	protected static ApplicationContext context =null;

	static{
		context= new ClassPathXmlApplicationContext("test_all.xml");
		log.info("context inited");
	}
	
	protected RequestContext getContext(String userId,String userName,double[] point) {
		UserModel userModel=new UserModel(userId,userName);
		RequestContext request=new RequestContext(userModel);
		userModel.setPoint(point);
		request.setSeq(LogSequence.seq());
		return request;
	}
}

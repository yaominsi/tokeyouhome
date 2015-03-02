package com.fujisan.web;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fujisan.api.RequestContext;
import com.fujisan.api.Response;
import com.fujisan.api.service.ScopeService;
import com.fujisan.api.service.UserService;
import com.fujisan.api.service.asserts.exception.AssertException;
import com.fujisan.api.service.smart.RecommendService;
import com.fujisan.model.BaseModel;
import com.fujisan.model.ScopeModel;
import com.fujisan.web.login.NeedLogin;
import com.fujisan.web.login.RequestContextManager;
import com.google.common.collect.Lists;

@Controller()
public class ScopeAction {
	private static Logger log = Logger.getLogger(ScopeAction.class);
	@Autowired
	private UserService UserService;
	@Autowired
	private ScopeService scopeService;
	@Autowired
	private RecommendService recommendService;

	@RequestMapping(value = "/scope/save", method = RequestMethod.POST)
	@NeedLogin
	public @ResponseBody Response<Boolean> save(@RequestBody ScopeModel scopeModel, HttpServletResponse response) {
		log.info("[scope] save request name:" + scopeModel.getName());
		Response<Boolean> result = new Response<Boolean>();
		if (scopeModel.getId() == null) {
			RequestContext requestContext = RequestContextManager.current();
			try {
				result = scopeService.create(requestContext, scopeModel);
			} catch (AssertException e) {
				result.setSuccess(false);
				result.setDesc("±£¥Ê ß∞‹," + e.getMessage() + "°£");
				log.error(e.getMessage(), e);
			} catch (Exception e) {
				result.setSuccess(false);
				result.setDesc("±£¥Ê ß∞‹£°");
				log.error(e.getMessage(), e);
			}
		}

		return result;
	}
	
	@RequestMapping(value = "/scope/list", method = RequestMethod.POST)
	@NeedLogin
	public @ResponseBody Response<Page<ScopeModel>> list(HttpServletResponse response) {
		log.info("[scope] list :" + RequestContextManager.current().getUserModel().getName());
		Response<Page<ScopeModel>> result = new Response<Page<ScopeModel>>();
		try {
			Pageable pageable=new PageRequest(0, 10);
			
			ScopeModel model=new ScopeModel();
			model.setCreatorId(RequestContextManager.current().getUserModel().getId());
			Page<ScopeModel> page = scopeService.find(RequestContextManager.current(),model, Lists.newArrayList
					(BaseModel.final_creatorId), Direction.DESC, Lists.newArrayList(BaseModel.final_gmtModified), pageable);
			result.setValue(page);
			result.setDesc("º”‘ÿ≥…π¶");
		} catch (AssertException e) {
			result.setSuccess(false);
			result.setDesc("º”‘ÿ ß∞‹," + e.getMessage() + "°£");
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setDesc("º”‘ÿ ß∞‹£°");
			log.error(e.getMessage(), e);
		}
		
		return result;
	}

	@RequestMapping(value = "/scope/recommends", method = RequestMethod.POST)
	@NeedLogin
	public @ResponseBody Response<Page<ScopeModel>> recommends(HttpServletResponse response) {
		log.info("[scope] list :" + RequestContextManager.current().getUserModel().getName());
		Response<Page<ScopeModel>> result = new Response<Page<ScopeModel>>();
		try {
			Pageable pageable=new PageRequest(0, 10);
			
			ScopeModel model=new ScopeModel();
			model.setCreatorId(RequestContextManager.current().getUserModel().getId());
			Page<ScopeModel> page = scopeService.find(RequestContextManager.current(),model, Lists.newArrayList
					(BaseModel.final_creatorId), Direction.DESC, Lists.newArrayList(BaseModel.final_gmtModified), pageable);
			result.setValue(page);
			result.setDesc("º”‘ÿ≥…π¶");
		} catch (AssertException e) {
			result.setSuccess(false);
			result.setDesc("º”‘ÿ ß∞‹," + e.getMessage() + "°£");
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setDesc("º”‘ÿ ß∞‹£°");
			log.error(e.getMessage(), e);
		}
		
		return result;
	}
}

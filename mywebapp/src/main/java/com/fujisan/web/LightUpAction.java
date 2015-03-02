package com.fujisan.web;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fujisan.api.LightUpDetail;
import com.fujisan.api.Response;
import com.fujisan.api.service.LightUpService;
import com.fujisan.api.service.asserts.exception.AssertException;
import com.fujisan.model.BaseModel;
import com.fujisan.model.LightUpModel;
import com.fujisan.web.login.NeedLogin;
import com.fujisan.web.login.RequestContextManager;
import com.google.common.collect.Lists;

/**
 * ”√ªßaction£¨µ«¬º◊¢≤·
 * 
 * @author siyaomin
 *
 */
@Controller
public class LightUpAction {
	private static final Logger log = Logger.getLogger(LightUpAction.class);
	@Autowired
	private LightUpService lightUpService;
	@NeedLogin
	@RequestMapping(value = "/lightup/save", method = RequestMethod.POST)
	public @ResponseBody Response<Boolean> save(@RequestBody LightUpModel lightUpModel,
			HttpServletResponse response) {
		Response<Boolean> result = new Response<Boolean>();
		try {
			lightUpModel.setPics(Lists.newArrayList("http://file20.mafengwo.net/M00/B1/56/wKgB3FDUd1CAbVnwADLlFW4nDdw05.groupinfo.w600.jpeg"));
			lightUpModel.setPic("/pic/demo.jpg");
			result = lightUpService.save(RequestContextManager.current(), lightUpModel);
		} catch (AssertException e) {
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setDesc(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setDesc("±£¥Ê ß∞‹£°");
		}
		return result;
	}
	@RequestMapping(value = "/lightup/{id}/detail", method = RequestMethod.POST)
	public @ResponseBody Response<LightUpDetail> detail(@PathVariable String id,
			HttpServletResponse response) {
		Response<LightUpDetail> result = new Response<LightUpDetail>();
		try {
			result = lightUpService.detail(RequestContextManager.current(), id);
		} catch (AssertException e) {
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setDesc(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setDesc("±£¥Ê ß∞‹£°");
		}
		return result;
	}

	/**
	 * ◊¢≤·
	 * 
	 * @param userModel
	 * @param response
	 * @return
	 */
	@NeedLogin
	@RequestMapping(value = "/lightup/lightUp/{id}", method = RequestMethod.POST)
	public @ResponseBody Response<Boolean> lightUp(@PathVariable String id,
			HttpServletResponse response) {
		
		Response<Boolean> result = new Response<Boolean>();
		try {
			result = lightUpService.lightUp(RequestContextManager.current(), id);
		} catch (AssertException e) {
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setDesc(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setDesc("±£¥Ê ß∞‹£°");
		}
		return result;
	}
	/**
	 * load
	 * 
	 * @param userModel
	 * @param response
	 * @return
	 */
	@NeedLogin
	@RequestMapping(value = "/lightup/lightOff/{id}", method = RequestMethod.POST)
	public @ResponseBody Response<Boolean> lightOff(@PathVariable String id,
			HttpServletResponse response) {

		Response<Boolean> result = new Response<Boolean>();
		try {
			result = lightUpService.lightOff(RequestContextManager.current(), id);
		} catch (AssertException e) {
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setDesc(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setDesc("±£¥Ê ß∞‹£°");
		}
		return result;
	}
	@RequestMapping(value = "/lightup/list", method = RequestMethod.POST)
	@NeedLogin
	public @ResponseBody Response<Page<LightUpModel>> list() {
		log.info("[scope] list :" + RequestContextManager.current().getUserModel().getName());
		Response<Page<LightUpModel>> result = new Response<Page<LightUpModel>>();
		try {
			Pageable pageable=new PageRequest(0, 10);
			
			LightUpModel model=new LightUpModel();
			model.setCreatorId(RequestContextManager.current().getUserModel().getId());
			Page<LightUpModel> page = lightUpService.find(RequestContextManager.current(),model, Lists.newArrayList
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

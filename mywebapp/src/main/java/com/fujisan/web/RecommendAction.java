package com.fujisan.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
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
 * 推荐action
 * 
 * @author siyaomin
 *
 */
@Controller
public class RecommendAction {
	private static final Logger log = Logger.getLogger(RecommendAction.class);
	@Autowired
	private LightUpService lightUpService;
	
	@RequestMapping(value = "/recommend/list", method = RequestMethod.POST)
	@NeedLogin
	public @ResponseBody Response<Page<LightUpDetail>> list() {
		log.info("[scope] list :" + RequestContextManager.current().getUserModel().getName());
		Response<Page<LightUpDetail>> result = new Response<Page<LightUpDetail>>();
		try {
			Pageable pageable=new PageRequest(0, 100);
			
			LightUpModel model=new LightUpModel();
			model.setCreatorId(RequestContextManager.current().getUserModel().getId());
			Page<LightUpDetail> page = lightUpService.findWithDetail(RequestContextManager.current(),new LightUpModel(), null, Direction.DESC, Lists.newArrayList(BaseModel.final_gmtModified), pageable);
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
}

package com.fujisan.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.fujisan.api.NotifyDetail;
import com.fujisan.api.Response;
import com.fujisan.api.service.NotifyService;
import com.fujisan.api.service.UserService;
import com.fujisan.api.service.asserts.exception.AssertException;
import com.fujisan.api.service.smart.RecommendService;
import com.fujisan.common.NotifyStatusEnum;
import com.fujisan.model.BaseModel;
import com.fujisan.model.NotifyModel;
import com.fujisan.model.UserModel;
import com.fujisan.web.login.NeedLogin;
import com.fujisan.web.login.RequestContextManager;
import com.google.common.collect.Lists;
/**
 * 通知类消息处理
 * @author siyaomin
 *
 */
@Controller()
public class NotifyAction {
	private static Logger log = Logger.getLogger(NotifyAction.class);
	@Autowired
	private UserService UserService;
	@Autowired
	private NotifyService notifyService;
	@Autowired
	private RecommendService recommendService;

	
	@RequestMapping(value = "/notify/list", method = RequestMethod.POST)
	@NeedLogin
	public @ResponseBody Response<Page<NotifyDetail>> list(HttpServletResponse response) {
		log.info("[scope] list :" + RequestContextManager.current().getUserModel().getName());
		Response<Page<NotifyDetail>> result = new Response<Page<NotifyDetail>>();
		try {
			Pageable pageable=new PageRequest(0, 10);
			
			NotifyModel model=new NotifyModel();
			model.setToUserId(RequestContextManager.current().getUserModel().getId());
			Page<NotifyModel> page = notifyService.find(RequestContextManager.current(),model, Lists.newArrayList
					(BaseModel.final_toUserId), Direction.DESC, Lists.newArrayList(BaseModel.final_gmtModified), pageable);
			if (page!=null&&CollectionUtils.isNotEmpty(page.getContent())) {
				List<NotifyDetail> content=new ArrayList<NotifyDetail>();
				for(NotifyModel e:page.getContent()){
					Response<NotifyDetail> detail = notifyService.detail(RequestContextManager.current(),e.getId());
					if (detail!=null) {
						content.add(detail.getValue());
					}
				}
				Page<NotifyDetail> dpage=new PageImpl<NotifyDetail>(content,pageable,page.getTotalElements());
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
	@RequestMapping(value = "/notify/todoList", method = RequestMethod.POST)
	@NeedLogin
	public @ResponseBody Response<Page<NotifyDetail>> todoList(HttpServletResponse response) {
		log.info("[scope] list :" + RequestContextManager.current().getUserModel().getName());
		Response<Page<NotifyDetail>> result = new Response<Page<NotifyDetail>>();
		try {
			Pageable pageable=new PageRequest(0, 10);
			
			NotifyModel model=new NotifyModel();
			model.setToUserId(RequestContextManager.current().getUserModel().getId());
			model.setStatus(NotifyStatusEnum.todo);
			Page<NotifyModel> page = notifyService.find(RequestContextManager.current(),model, Lists.newArrayList
					(BaseModel.final_toUserId,BaseModel.final_status), Direction.DESC, Lists.newArrayList(BaseModel.final_gmtModified), pageable);
			if (page!=null&&CollectionUtils.isNotEmpty(page.getContent())) {
				List<NotifyDetail> content=new ArrayList<NotifyDetail>();
				for(NotifyModel e:page.getContent()){
					Response<NotifyDetail> detail = notifyService.detail(RequestContextManager.current(),e.getId());
					if (detail!=null) {
						content.add(detail.getValue());
					}
				}
				Page<NotifyDetail> dpage=new PageImpl<NotifyDetail>(content,pageable,page.getTotalElements());
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
	@RequestMapping(value = "/notify/created", method = RequestMethod.POST)
	@NeedLogin
	public @ResponseBody Response<Page<NotifyDetail>> created(HttpServletResponse response) {
		log.info("[scope] list :" + RequestContextManager.current().getUserModel().getName());
		Response<Page<NotifyDetail>> result = new Response<Page<NotifyDetail>>();
		try {
			Pageable pageable=new PageRequest(0, 10);
			
			NotifyModel model=new NotifyModel();
			model.setToUserId(RequestContextManager.current().getUserModel().getId());
			model.setStatus(NotifyStatusEnum.created);
			Page<NotifyModel> page = notifyService.find(RequestContextManager.current(),model, Lists.newArrayList
					(BaseModel.final_toUserId,BaseModel.final_status), Direction.DESC, Lists.newArrayList(BaseModel.final_gmtModified), pageable);
			if (page!=null&&CollectionUtils.isNotEmpty(page.getContent())) {
				List<NotifyDetail> content=new ArrayList<NotifyDetail>();
				for(NotifyModel e:page.getContent()){
					Response<NotifyDetail> detail = notifyService.detail(RequestContextManager.current(),e.getId());
					if (detail!=null) {
						content.add(detail.getValue());
					}
				}
				Page<NotifyDetail> dpage=new PageImpl<NotifyDetail>(content,pageable,page.getTotalElements());
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
	@RequestMapping(value = "/notify/counts", method = RequestMethod.POST)
	@NeedLogin
	public @ResponseBody Response<Long> counts(@RequestBody List<NotifyStatusEnum> status) {
		UserModel user=RequestContextManager.current().getUserModel();
		log.info("[scope] list :" + user.getName());
		Response<Long> result = new Response<Long>();
		try {
			Response<Map<NotifyStatusEnum, Long>> resp = notifyService.counts(RequestContextManager.current(), user.getId(), Lists.newArrayList(status));
			if (resp!=null) {
				result.setValue(resp.getValue().get(NotifyStatusEnum.created));
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
	@RequestMapping(value = "/notify/{notifyId}/todo", method = RequestMethod.POST)
	@NeedLogin
	public @ResponseBody Response<Boolean> todo(@PathVariable String notifyId) {
		UserModel user=RequestContextManager.current().getUserModel();
		log.info("[scope] list :" + user.getName());
		Response<Boolean> result = new Response<Boolean>();
		try {
			notifyService.addToDo(RequestContextManager.current(), notifyId);
			result.setDesc("处理成功");
		} catch (AssertException e) {
			result.setSuccess(false);
			result.setDesc("处理失败," + e.getMessage() + "。");
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setDesc("处理失败！");
			log.error(e.getMessage(), e);
		}
		
		return result;
	}
	@RequestMapping(value = "/notify/{notifyId}/finish", method = RequestMethod.POST)
	@NeedLogin
	public @ResponseBody Response<Boolean> finish(@PathVariable String notifyId) {
		UserModel user=RequestContextManager.current().getUserModel();
		log.info("[scope] list :" + user.getName());
		Response<Boolean> result = new Response<Boolean>();
		try {
			notifyService.finish(RequestContextManager.current(),notifyId);
			result.setDesc("处理成功");
		} catch (AssertException e) {
			result.setSuccess(false);
			result.setDesc("处理失败," + e.getMessage() + "。");
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setDesc("处理失败！");
			log.error(e.getMessage(), e);
		}
		
		return result;
	}

}

package com.fujisan.api.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fujisan.api.RequestContext;
import com.fujisan.api.Response;
import com.fujisan.api.ScopeDetail;
import com.fujisan.api.service.ScopeService;
import com.fujisan.api.service.asserts.Assert;
import com.fujisan.api.service.asserts.DomainServiceAssert;
import com.fujisan.api.service.asserts.exception.AssertException;
import com.fujisan.common.BooleanAbout;
import com.fujisan.common.BusiTypeEnum;
import com.fujisan.model.BaseModel;
import com.fujisan.model.NodeModel;
import com.fujisan.model.ScopeModel;
import com.fujisan.model.UserModel;
import com.fujisan.repository.NodeRepository;
import com.fujisan.repository.ScopeRepository;
import com.fujisan.repository.UserRepository;
import com.google.common.collect.Lists;

/**
 * 区域服务
 * 
 * @author siyaomin
 *
 */
@Service
public class ScopeServiceImpl implements ScopeService {
	private Logger log = Logger.getLogger(ScopeServiceImpl.class);
	@Autowired
	private ScopeRepository scopeRepository;
	@Autowired
	private NodeRepository nodeRepository;
	@Autowired
	private DomainServiceAssert<ScopeModel> scopeAssert;
	@Autowired
	private DomainServiceAssert<NodeModel> nodeAssert;
	@Autowired
	private UserRepository userRepository;

	/**
	 * 创建新区域
	 */
	@Override
	public Response<Boolean> create(RequestContext requestContext, ScopeModel scopeModel) {
		log.info("[scope] create start " + requestContext.getSeq());
		scopeAssert.checkParams(requestContext, scopeModel, BusiTypeEnum.create);
		scopeModel.setCreator(requestContext.getUserModel().getName());
		scopeModel.setGmtCreate(new Date());
		scopeModel.setModifier(requestContext.getUserModel().getName());
		scopeModel.setGmtModified(new Date());
		scopeModel.setCreatorId(requestContext.getUserModel().getId());
		scopeModel.setIsDeleted(BooleanAbout.n);
		scopeAssert.enable(requestContext, scopeModel, BusiTypeEnum.create);
		scopeRepository.save(scopeModel);
		if (CollectionUtils.isNotEmpty(scopeModel.getNodes())) {
			// setup nodes
			int order = 0;
			for (NodeModel node : scopeModel.getNodes()) {
				node.setCreator(requestContext.getUserModel().getName());
				node.setGmtCreate(new Date());
				node.setModifier(requestContext.getUserModel().getName());
				node.setGmtModified(new Date());
				node.setCreatorId(requestContext.getUserModel().getId());
				node.setIsDeleted(BooleanAbout.n);
				node.setScopeId(scopeModel.getId());
				if (node.getOrder() == null) {
					node.setOrder(order);
					order += 10;
				}
				nodeRepository.save(node);
			}
		}
		log.info("[scope] create ok " + requestContext.getSeq());
		return new Response<Boolean>(true, "区域创建成功");
	}

	@Override
	public Response<Boolean> updateNode(RequestContext requestContext, String id, List<NodeModel> nodeModels) {
		log.info("[scope] update node start " + requestContext.getSeq());
		Assert.notEmpty(nodeModels);
		scopeAssert.exists(requestContext, id);
		ScopeModel scopeModel = new ScopeModel();
		scopeModel.setId(id);
		scopeAssert.enable(requestContext, scopeModel, BusiTypeEnum.update);
		scopeModel.setModifier(requestContext.getUserModel().getName());
		scopeModel.setGmtModified(new Date());
		scopeRepository.save(scopeModel);
		// check
		for (NodeModel node : nodeModels) {
			if (node.getId() == null)
				nodeAssert.checkParams(requestContext, node, BusiTypeEnum.create);
		}
		//
		// 更新结点
		//
		for (NodeModel node : nodeModels) {
			nodeRepository.save(node);
		}
		log.info("[scope] update node ok " + requestContext.getSeq());
		return new Response<Boolean>(true, "区域结点已更新");
	}

	@Override
	public Response<Boolean> update(RequestContext requestContext, ScopeModel scopeModel) {
		log.info("[scope] update start " + requestContext.getSeq());
		Assert.notNull(scopeModel);
		scopeAssert.exists(requestContext, scopeModel.getId());
		scopeAssert.enable(requestContext, scopeModel, BusiTypeEnum.update);
		scopeModel.setGmtModified(new Date());
		scopeModel.setModifier(requestContext.getUserModel().getName());
		scopeRepository.save(scopeModel);
		log.info("[scope] update ok " + requestContext.getSeq());
		return new Response<Boolean>(true, "更新线路");
	}

	@Override
	public Response<Boolean> delete(RequestContext requestContext, String id) {
		log.info("[scope] delete start " + requestContext.getSeq());
		scopeAssert.exists(requestContext, id);
		ScopeModel scopeModel = new ScopeModel();
		scopeModel.setId(id);
		scopeAssert.enable(requestContext, scopeModel, BusiTypeEnum.update);
		scopeModel.setGmtCreate(new Date());
		scopeModel.setModifier(requestContext.getUserModel().getName());
		scopeModel.setIsDeleted(BooleanAbout.y);
		scopeRepository.save(scopeModel);
		log.info("[scope] delete ok " + requestContext.getSeq());
		return new Response<Boolean>(true, "移除线路");
	}

	@Override
	public Page<ScopeModel> find(RequestContext requestContext, ScopeModel model, List<String> properties,
			Direction direction, List<String> sortProperties, Pageable pageable) {
		return scopeRepository.findByPage(model, properties, direction, sortProperties, pageable, ScopeModel.class);
	}

	@Override
	public Response<ScopeDetail> detail(RequestContext requestContext, String id) {
		String seq = requestContext.getSeq();
		log.info("start query detail order for id=" + id + ",seq:" + seq);
		Response<ScopeDetail> result = new Response<ScopeDetail>();
		try {
			ScopeModel scope = scopeRepository.findOne(id, ScopeModel.class);
			ScopeDetail detail = new ScopeDetail();
			detail.setScope(scope);
			if (scope != null) {
				if (StringUtils.isNotBlank(scope.getCreatorId()))
					detail.setFromUser(userRepository.findOne(scope.getCreatorId(), UserModel.class));
				scope.setNodes(nodeRepository.findList(new NodeModel(scope.getId()), Lists.newArrayList("creatorId"),
						Direction.DESC, Lists.newArrayList(BaseModel.final_gmtModified), NodeModel.class));
			}
			result.setValue(detail);
			log.info("end query detail order for id=" + id + ",seq:" + seq);
		} catch (AssertException e) {
			result.setDesc(e.getMessage());
			result.setSuccess(false);
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setDesc("获取失败");
			log.error(e.getMessage() + ",seq:" + seq, e);
		}
		return result;
	}
}

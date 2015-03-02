package com.fujisan.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.fujisan.api.RequestContext;
import com.fujisan.api.service.ScopeService;
import com.fujisan.model.NodeModel;
import com.fujisan.model.ScopeModel;
import com.google.common.collect.Lists;
/**
 * ”√ªß≤‚ ‘
 * @author siyaomin
 *
 */
public class ScopeServiceTest extends BaseTest{
	private ScopeService service;
	
	@Test
	public void testCreate() {
		service=context.getBean(ScopeService.class);
		RequestContext requestContext=getContext("54ce5b47a82626475ab91584", "userName", new double[]{1d,1d});
		ScopeModel scopeModel=new ScopeModel();
		scopeModel.setName("name");
		service.create(requestContext, scopeModel);
	}
	@Test
	public void testCreateWithNodes() {
		service=context.getBean(ScopeService.class);
		RequestContext requestContext=getContext("userId", "userName", new double[]{1d,1d});
		ScopeModel scopeModel=new ScopeModel();
		scopeModel.setName("namexx");
		List<NodeModel> nodes=new ArrayList<NodeModel>();
		nodes.add(new NodeModel(new double[]{1d,1d},"name"));
		scopeModel.setNodes(nodes);
		service.create(requestContext, scopeModel);
	}
	
	@Test
	public void testQuery(){
		service=context.getBean(ScopeService.class);
		RequestContext requestContext=getContext("userId", "userName", new double[]{1d,1d});
		ScopeModel model=new ScopeModel();
		model.setName("name");
		Pageable pageable=new PageRequest(1, 4);
		Page<ScopeModel> list = service.find(requestContext,model, Lists.newArrayList("name"), Direction.DESC, Lists.newArrayList("gmtCreate"), pageable);
		System.out.println(list);
	}

}

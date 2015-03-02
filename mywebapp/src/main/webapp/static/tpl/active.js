//
define(function(require,exports){
	var active_detail= function(id){
		$.ajax(
            {
              url:"/lightup/"+id+"/detail.action", 
              type: "POST", 
              data: null,
              success: function(data){
              				if(data.success&&data.value){
              					var str=nano($('#tpl_active_detail').html(),data.value);
								$('#detail_'+id).html(str);
              				}
              			}, 
              dataType: "json",
              contentType: "application/json"
            } );  
	};
	function bind(){
		 $('#btn_load_active').click(function () {
	    	push_div('left_bar_active');
		 	$('#div_my_active').html($('#tpl_my_active').html());
		 	$.ajax(
	            {
	              url:"/lightup/list.action", 
	              type: "POST", 
	              data: null,
	              success: function(data){
	              				if(data.success&&data.value&&data.value.content){
	              					$(data.value.content).each(function(){
	              						$('<li style="cursor:pointer" onclick=showMyLightUpDetail("'+this.id+'")>'+
	              						this.title+'</li><div id="detail_my_active_'+this.id+'"></div>').appendTo($('#ul_my_active_list'));
	              					});
	              				}
	              			}, 
	              dataType: "json",
	              contentType: "application/json"
	            } );  
	    	
		});
		$('#btn_find_active').click(function () {
	    	push_div('left_find_active');
		 	$('#left_find_active').html($('#tpl_find_active').html());
		 	$.ajax(
	            {
	              url:"/recommend/list.action", 
	              type: "POST", 
	              data: null,
	              success: function(data){
	              				if(data.success&&data.value&&data.value.content){
	              					$(data.value.content).each(function(){
	              							var str=nano($('#tpl_active_detail').html(),this);
											$(str).appendTo($('#ul_find_active_list'));
	              					});
	              					//初始化
	              					$('#ul_find_active_list li').click(
	              						function(){
	              							active_detail($(this).attr('val'));
	              						}
	              					);
	              				}
	              			}, 
	              dataType: "json",
	              contentType: "application/json"
	            } );  
	    	
		});
		var submitLightup= function(){
			var lightUpModel={};
			lightUpModel.scopeId=$('#lightup_select_scopeId').attr('val');
			lightUpModel.title=$('#lightup_title').val();
			lightUpModel.note=$('#lightup_note').val();
			lightUpModel.gmtStartOff=$('#lightup_gmtStartOff').val();
			lightUpModel.seeds=$('#lightup_seeds').val();
			lightUpModel.duration=$('#lightup_duration').val();
			var unit=$("input[name='lightup_durationUnit']").filter(function(){ return this.checked});
			if(!unit){
				alert('请选择时间单位')
			}
	
			lightUpModel.durationUnit=unit.val();
			lightUpModel.isLightUp=$('#lightup_isLightUp').val()==='on'?'y':'n';
			
			if(lightUpModel.scopeId){
				$.ajax(
	            {
	              url:"/lightup/save.action", 
	              type: "POST", 
	              data: JSON.stringify( lightUpModel ), 
	              success: function(data){if(data.success){alert(data.desc);}else{alert(data.desc);}}, 
	              dataType: "json",
	              contentType: "application/json"
	            } ); 
			}else{
				alert('请选择区域');
			}
		};
		$('#btn_new_active').click(function () {
	    	push_div('left_bar_new_active');
	    	$('#div_new_active').html($('#tpl_new_active').html());
	    	$('#submit_lightup').click(submitLightup);
	    	$('#lightup_scopeId').click(function(){
				$('#left_scope_list').toggle(150);
					$.ajax(
			            {
			              url:"/scope/recommends.action", 
			              type: "POST", 
			              data: null,
			              success: function(data){
	              				if(data.success&&data.value&&data.value.content){
	              					$('#ul_scope_list').html('');
	              					$(data.value.content).each(function(){
	              						$('<li name="scope_list" title="点击选择" style="cursor:pointer" val='+this.id+'>'+this.name+'</li>').appendTo($('#ul_scope_list'));
	              					});
	              				}
	              				$('[name="scope_list"]').click(function(){
	              					var s=$('#lightup_select_scopeId');
	              					var $this=$(this);
	              					s.html($this.html());
	              					s.attr('val',$this.attr('val'));
	              					$('#left_scope_list').hide(150);
	              				});
	              			}, 
	              dataType: "json",
	              contentType: "application/json"
	            } );  
			});
		//
		});
	
	}
	var showMyLightUpDetail=function (lightUpId){
	  $('#ul_lightup_orders_list').html('');
	  $('#detail_my_active_'+lightUpId).html('');
		$.ajax(
		    {
		      url:"/lightup/"+lightUpId+"/detail.action", 
		      type: "POST", 
		      data: null, 
		      success: function(data){
		      	if(data.success){
		      		var str=nano($('#tpl_my_active_detail').html(),data.value);
					$(str).appendTo($('#detail_my_active_'+lightUpId));
		      	}else{
		      		alert(data.desc);
		      	}
		      }, 
		      dataType: "json",
		      contentType: "application/json"
	    } ); 
		
	}
	
	var showLightUpOrders=function (lightUpId){
		push_div('left_bar_lightUp_orders');
		$('#left_lightUp_order_list').html('');
		$('#ul_lightup_orders_list').html('');
		$.ajax(
		    {
		      url:"/order/listWithLightUpId.action", 
		      type: "POST", 
		      data: JSON.stringify({page:0,size:10,value:{lightUpId:lightUpId}}), 
		      success: function(data){
		      	if(data.success&&data.value&&data.value.content){
		      		$(data.value.content).each(function(){
		      			this['optEnable']=this.order.status=='created'?'':'none';
		      			this.order.status=='created'?this.order.status='新':this;
			      		var str=nano($('#tpl_lightUp_order_detail').html(),this);
						$(str).appendTo($('#ul_lightup_orders_list'));
		      		});
		      	}else{
		      		alert(data.desc);
		      	}
		      }, 
		      dataType: "json",
		      contentType: "application/json"
	    } ); 
		
	};
	exports.bind=bind;
	window.showLightUpOrders=showLightUpOrders;
	window.showMyLightUpDetail=showMyLightUpDetail;
});
define(function(require,exports){
	function bind(){
		$('#btn_load_notify').click(function () {
	    	push_div('left_bar_notify');
	    	$('#div_notify_ol').html('');
	    	$.ajax(
	            {
	              url:"/notify/list.action", 
	              type: "POST", 
	              data: null,
	              success: function(data){
	              				if(data.success&&data.value&&data.value.content){
	              					$(data.value.content).each(function(){
	              						this['todoEnable']=this.notify.status=='todo'?'none':'';
	              						this['finishEnable']=this.notify.status=='todo'?'':'none';
	              						var str=nano($('#tpl_notify').html(),this);
	              						$(str).appendTo($('#div_notify_ol'));
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
		$('#btn_notify_todo').click(function () {
	    	$('#div_notify_ol').html('');
	    	$.ajax(
	            {
	              url:"/notify/todoList.action", 
	              type: "POST", 
	              data: null,
	              success: function(data){
	              				if(data.success&&data.value&&data.value.content){
	              					$(data.value.content).each(function(){ 
	              						this['todoEnable']=this.notify.status=='todo'?'none':'';
	              						this['finishEnable']=this.notify.status=='todo'?'':'none';
	              						var str=nano($('#tpl_notify').html(),this);
	              						$(str).appendTo($('#div_notify_ol'));
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
	 	$('#btn_notify_all').click(function () {
	    	$('#div_notify_ol').html('');
	    	$.ajax(
	            {
	              url:"/notify/list.action", 
	              type: "POST", 
	              data: null,
	              success: function(data){
	              				if(data.success&&data.value&&data.value.content){
	              					$(data.value.content).each(function(){
	              						this['todoEnable']=this.notify.status=='todo'?'none':'';
	              						this['finishEnable']=this.notify.status=='todo'?'':'none';
	              						var str=nano($('#tpl_notify').html(),this);
	              						$(str).appendTo($('#div_notify_ol'));
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
		 $('#btn_notify_created').click(function () {
	    	$('#div_notify_ol').html('');
	    	$.ajax(
	            {
	              url:"/notify/created.action", 
	              type: "POST", 
	              data: null,
	              success: function(data){
	              				if(data.success&&data.value&&data.value.content){
	              					$(data.value.content).each(function(){
	              						var str=nano($('#tpl_notify').html(),this);
	              						$(str).appendTo($('#div_notify_ol'));
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
	}
	function disposeNotify(notifyId,status){
		$.ajax(
		    {
		      url:"/notify/"+notifyId+"/"+status+".action", 
		      type: "POST", 
		      data: null, 
		      success: function(data){
		      	if(data.success){
		      		//隐藏操作
		      		$('#span_notify_opt_'+notifyId).hide();
		      	}else{
		      		alert(data.desc);
		      	}
		      }, 
		      dataType: "json",
		      contentType: "application/json"
	    } ); 
	};
	//bind to window
	window.disposeNotify=disposeNotify;
	exports.bind=bind;
});
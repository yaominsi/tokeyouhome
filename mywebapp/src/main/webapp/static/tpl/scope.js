
define(function(require,exports){
	function bind(){
		$('#btn_load_scope').click(function () {
	    	push_div('left_bar_scope');
	    	$('#div_my_scope').html($('#tpl_my_scope').html());
	    	$.ajax(
	            {
	              url:"/scope/list.action", 
	              type: "POST", 
	              data: null,
	              success: function(data){
	              				if(data.success&&data.value&&data.value.content){
	              					$(data.value.content).each(function(){
	              						$('<li style="cursor:pointer" val='+this.id+'>'+this.name+$('#select_lightup').html()+'</li>').appendTo($('#scope_list_ul_li'));
	              					});
	              				}
	              			}, 
	              dataType: "json",
	              contentType: "application/json"
	            } );  
		});
		$('#btn_new_scope').click(function () {
	    	push_div('left_bar_new_scope');
	    	$('#div_new_scope').html($('#tpl_new_scope').html());
	    	
	    	//加载地图
	    	$('#container').show();
	    	//initmap();TODO 加载地图
	    	$('#submit_scope').click(function(){
				if(cpois&&cpois.length>0){
					var formModel={};
					formModel.name=$('#scopeName').val();
					formModel.nodes=[];
					formModel.nodes.push({point:[12,123]});
					if(!$.trim(formModel.name)){
						alert('请输入区域名字');
						return false;
					}
					$.ajax(
		            {
		              url:"/scope/save.action", 
		              type: "POST", 
		              data: JSON.stringify( formModel ), 
		              success: function(data){alert(data.desc);}, 
		              dataType: "json",
		              contentType: "application/json"
		            } );  
				}else{
					alert("请选择结点");
				}
			});
		});
		$('#notice_select_scope').click(function(){
			$('#left_scope_list').toggle(150);
		});
	}
	exports.bind=bind;
});
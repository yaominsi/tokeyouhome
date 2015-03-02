	//事件初始化
var initactions=function(){
	//left_bar_new_scope
	//span_created_counts
	$.ajax(
	{
	  url:"/notify/counts.action", 
	  type: "POST", 
	  data: JSON.stringify(['created']),
	  success: function(data){
	  				if(data.success){
	  					$('#span_created_counts').html(data.value);
	  				}
	  			}, 
	  dataType: "json",
	  contentType: "application/json"
	} );
	
}
var hide=function(id){
	$('#'+id).hide();
}

var cur_div_id='';
var push_div = function(id){
	$('#'+cur_div_id).hide();
	$('#'+id).show();
	cur_div_id=id;
}


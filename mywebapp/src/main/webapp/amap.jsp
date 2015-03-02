<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>Hello,world</title>
<!--map api start-->
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=85863634c83416cfe95c66d439bf5e80"></script>

<script src="static/sea-modules/sea.js"></script>
<script src="static/sea-modules/jquery.min.js"></script>
<script src="static/sea-modules/nano.js"></script>

<!--<script src="static/appjs/amapx.js"></script>-->
<script src="static/appjs/token_checker.js"></script>
<script src="static/appjs/acts.js"></script>

<!-- 第三方样式 -->
<link rel="alternate icon" type="image/png" href="http://a.static.amazeui.org/assets/2.x/i/favicon.png"><link rel="apple-touch-icon-precomposed" href="http://a.static.amazeui.org/assets/2.x/i/app-icon72x72@2x.png"><meta name="apple-mobile-web-app-title" content="Amaze UI"/><meta name="apple-mobile-web-app-capable" content="yes"><meta name="apple-mobile-web-app-status-bar-style" content="black"><link rel="stylesheet" href="http://a.static.amazeui.org/assets/2.x/css/amazeui.min.css?v=i5hjtsmo"><link rel="stylesheet" href="http://a.static.amazeui.org/assets/2.x/css/amaze.min.css?v=i5hjtsmo"><!--[if (gte IE 9)|!(IE)]><!--><script src="http://a.static.amazeui.org/assets/2.x/js/jquery.min.js"></script><!--<![endif]--><!--[if lt IE 9]><script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="http://a.static.amazeui.org/assets/2.x/js/polyfill/rem.min.js"></script>
<script src="http://a.static.amazeui.org/assets/2.x/js/polyfill/respond.min.js"></script>
<script src="http://a.static.amazeui.org/assets/2.x/js/amazeui.legacy.js"></script><![endif]--><!--[if (gte IE 9)|!(IE)]><!--><script src="http://a.static.amazeui.org/assets/2.x/js/amazeui.min.js?v=i5hjtsmo"></script><!--<![endif]--><link rel="canonical" href="http://amazeui.org/javascript/dropdown?_ver=2.x"/></head><body class="" data-amui="2.x"><!--[if lte IE 9 ]><div class="am-alert am-alert-danger ie-warning" data-am-alert>
<!-- end -->
<script type="text/javascript">
$(function(){
	initactions();
	 check_login();
});
seajs.config({
	base:"/",
	alias:{
		jquery:"static/sea-modules/jquery.min",
		active:"static/tpl/active",
		notify:"static/tpl/notify",
		scope:"static/tpl/scope",
		user:"static/tpl/user",
		order:"static/tpl/order"
	}
});
seajs.use('active',function(active){
	active.bind();
});
seajs.use('order',function(order){
	order.bind();
});
seajs.use('notify',function(notify){
	notify.bind();
});
seajs.use('scope',function(scope){
	scope.bind();
});
seajs.use('user',function(user){
	user.bind();
});
</script>
<!---->
<style type="text/css">

html{height:100%}
body{height:100%;margin:0px;padding:0px;font-size:14px;min-width:350px}
#container{height:92%;margin:5px;}

.leftbar{
	display:none;
	position:absolute;
	z-index:100;
	width:100%;
	height:90%;
	background-color: #EEE;
	border-right:1px red solid;
	overflow:auto;
	margin:0;
}

li{
font-size:15px;
margin:15px;
}
a{
border:0px red solid;
}

.bd{
border:1px red solid;
}
.cs{
	cursor:pointer;
	font-size:18px;
}
.bluebg{
	color:#0e90d2;
}
</style>



<!--map api end-->
</head>
 
<body>

<!-- top start-->
<div style="background-color: #F60;height:48px;position:absolute；margin-left:0px;z-index:1111;text-align:center;padding:0">
	<div style="height:39px;text-align:left;padding:10px;display:block;float:left;">
		<span id="btn_new_active" style="margin-right:20px;color:#FFF"  class="cs">组织</span>
		<span id="btn_find_active" style="margin-right:20px;color:#FFF" class="cs">发现</span>
		<span id="btn_load_notify" style="color:#FFF" class="cs">通知 <span id="span_created_counts"></span></span>
	</div>
	<div class="am-dropdown" style="float:right;display:inline-block;" data-am-dropdown>
	  <button class="am-btn am-btn-primary am-dropdown-toggle" data-am-dropdown-toggle>我的 <span class="am-icon-caret-down"></span></button>
	  <ul class="am-dropdown-content">
	    <li id="btn_load_order"><a>预约</a></li>
		<li id="btn_load_active"><a>活动</a></li>
		<li id="btn_load_scope"><a>常用地点</a></li>
		<li id="btn_new_scope"><a>新地点</a></li>
		<li ><a id="btn_load_login">登录</a></li>
	  </ul>
	</div>
</div>
<!-- top end-->
	<!-- 左侧区域 -->
	<div id="left_bar_scope" class="leftbar">
		<div style="text-align:center">
			<div id="div_my_scope">
				
			</div>
		</div>
	</div>
	<!-- 左侧区域 -->
	<div id="left_bar_new_scope" class="leftbar">
		<div style="text-align:center">
			<a id="btn_new_scope" href="#">新区域></a>
			<div id="div_new_scope">
			</div>
		</div>
	</div>
	<!-- 左侧区域 -->
	<div id="left_bar_order" class="leftbar">
		<div style="text-align:center">
			<a href="#">我的预约></a>
			<div id="div_my_order">
				
			</div>
		</div>
	</div>
	<!-- 左侧区域 -->
	<div id="left_bar_notify" class="leftbar">
		<div>
			<a id="btn_notify_created" href="#">未读></a>
			<a id="btn_notify_todo" href="#">TODO></a>
			<a id="btn_notify_all" href="#">所有></a>
			<div id="div_notify">
				<ol id="div_notify_ol"></ol>
			</div>
			<div id="div_notify_todo">
				
			</div>
			<div id="div_notify_created">
				
			</div>
		</div>
	</div>
	<!-- 新活动 -->
	<div id="left_bar_new_active" class="leftbar">
		<a id="btn_new_active" href="#">新活动></a>
		<div id="div_new_active" style="text-align:center">		
		</div>
	</div>
	<!-- 左侧活动区域 -->
	<div id="left_bar_active" class="leftbar">
		<div style="text-align:center">
			<div id="div_my_active">		
			</div>
		</div>
	</div>
	<!-- 左侧登录区 -->
	<div id="left_login" class="leftbar">
	</div>
	<!-- 发现活动 -->
	<div id="left_find_active" class="leftbar">
		
	</div>
	
	<!-- 左侧选择区域 -->
	<div id="left_scope_list" class="leftbar" style="text-align:center">
		<a id="notice_select_scope">请选择一个区域></a>
		<ul id="ul_scope_list">
		</ul>
	</div>
	<!-- 活动报名列表 -->
	<div id="left_bar_lightUp_orders" class="leftbar" style="text-align:center">
		<a id="notice_select_scope" onclick="hide('left_bar_lightUp_orders')">报名列表></a>
		<div style="margin:5px">
				<span>名字</span>
				<span>>>预约时间</span>
				<span>>>状态</span>
				<span>>>处理时间</span>
		</div>
		<ol id="ul_lightup_orders_list">
		
		</ol>
	</div>
	<!-- 编辑区域模板 -->
	<script type="text/template" id="tpl_new_scope">
		<div width="600px" style="text_align:center">
			<input id="scopeName" placeholder="区域名称，如公司-滨江园区" style="width:70%;margin-top:20px"/>
			<br/>
			<从地图上选取>
			<br/>
			采集类型：
			<select id="scope_gather_type">
				<option value="">请选择</option>
				<option>公司</option>
				<option>酒店</option>
				<option>小区</option>
				<option>路</option>
				<option>街</option>
				<option>山</option>
				<option>公司</option>
				<option>市</option>
				<option>镇</option>
				<option>乡</option>
				<option>村</option>
			</select>
			<input id="scope_gather_type_txt" placeholder="其他关键字" />
			<br/>
			<ol id="scope_ul_li" style="text-align:left;margin-left:200px;">
			</ol>
			<br/>
			<div id="container" style="width:700px;height:500px"></div>
			<button id="submit_scope" style="width:30%;margin-top:20px">保存</button>
		</div>
	</script>
	<!-- 我的区域列表模板 -->
	<script type="text/template" id="tpl_my_scope" >
		<ol id="scope_list_ul_li" style="text-align:left" style="width:90%;margin-top:20px">
		</ol>
	</script>
	<!-- 登录模板 -->
	<script type="text/template" id="tpl_user_login">
		<div style="text-align:center">
			<a id="btn_login" href="#">login></a>
			<div style="margin:15px auto;">
			<input id="login_name" placeholder="输入登录帐号"/>
			</div><div  style="margin:15px auto;">
			<input id="login_passwd" placeholder="输入密码"/>
			</div>
			<button id="submit_login" style="width:30%;margin-top:20px">登录</button>
		</div>
	</script>
	<script type="text/template" id="tpl_my_active">
	
		<ol id="ul_my_active_list"  style="width:90%;margin-top:20px;text-align:left">
		</ol>
	</script>
	<!---发现活动-->
	<script type="text/template" id="tpl_find_active">
		<div id="div_find_active">
			<ul id="ul_find_active_list" type="none" style="width:90%;margin-top:20px;text-align:left">
			</ul>
		</div>
	</script>
	<!-- 活动编辑模板 -->
	<script type="text/template" id="tpl_new_active">
		<div id="div_new_active">
			<div style="margin:15px auto;" title='输入活动名字'><input id="lightup_title" placeholder="输入活动名字,如拼车"/></div>
			<div style="margin:15px auto;" title="选择活动区域"><a id="lightup_scopeId" style="cursor:pointer" >选择活动区域</a></div>
			<div style="margin:15px auto;" title="已选择区域"><a id="lightup_select_scopeId" style="cursor:pointer" ></a></div>
			<div style="margin:15px auto;text-align:center" title="活动标签">
			</div>
			<div style="margin:15px auto;" title="开始时间" name="datepicker">
				<input id="lightup_gmtStartOff" data-am-datepicker="{format: 'yyyy-mm-dd'}" placeholder="开始时间"/>
				<br>
				####-##-## ##:##:##
			</div>
			<div style="margin:15px auto;" title="有效时长">
				<input id="lightup_duration" placeholder="有效时长" value="15"/>
			</div>
			<div style="margin:15px auto;">
				<label for="m">分</label><input name="lightup_durationUnit" type="radio" value="m" id="m"/> 
				<label for="h">时</label><input name="lightup_durationUnit" type="radio" value="h" id="h"/> 
				<label for="d">天</label><input name="lightup_durationUnit" type="radio" value="d" id="d"/> 
				<label for="w">周</label><input name="lightup_durationUnit" type="radio" value="w" id="w"/> 
			</div>
			<div style="margin:15px auto;"><input id="lightup_seeds" style=";" placeholder="报名人数上限" value="15"/></div>
			<div style="margin:15px auto;"><label for="lightup_isLightUp" title="点亮，让更多人发现">点亮</label><input id="lightup_isLightUp" type="checkbox"/></div>
			<div style="margin:15px auto;"><textarea id="lightup_note" placeholder="描述信息"></textarea></div>
			<div><button id="submit_lightup" style="margin-top:20px;padding:5px 20px">创建</button></div>
		</div>
	</script>
	<!---发现活动明细-->
	<script type="text/template" id="tpl_active_detail">
		<div style="border-bottom:3px #F60 dotted;padding:15px;">
			<div style="display:inline-block;width:100%">
				<img src="{lightUp.pic}" style="height:200px;display:{hasPic};border:7px #FFF solid;margin-right:15px"></img>
				<div style="font:blob">{lightUp.title}</div>
				<div>地点：{scope.name}</div>
				<div>时间：{lightUp.gmtStartOff}>{lightUp.gmtTill}</div>
				<div>总人数：{lightUp.accepts}/{lightUp.seeds}</div>
				<a href="#" onclick="createOrder('{lightUp.id}')" id="createOrder">我要参与</a>
				<div>{lightUp.note}</div>
			<div>
		</div>
	</script>
	<!---我的订单列表-->
	<script type="text/template" id="tpl_order_list">
		<div id="div_order_list">
			<ol id="ul_order_list"  style="width:90%;margin-top:20px;text-align:left">
			</ol>
		</div>
	<!---订单明细-->
	<script type="text/template" id="tpl_order_detail">
		<li>
			<div>活动发起人：{toUser.name}</div>
			<div>区域：{scope.name}</div>
			<div>主题：{lightUp.title}</div>
			<div>开始：{lightUp.gmtStartOff}</div>
			<div>结束：{lightUp.gmtTill}</div>
			<div>结束：{order.status}</div>
		</li>
	</script>
	<!---我的活动明细-->
	<script type="text/template" id="tpl_my_active_detail">
		<div>发起人：{fromUser.name}</div>
		<div>区域：{scope.name}</div>
		<div>开始：{lightUp.gmtStartOff}</div>
		<div>结束：{lightUp.gmtTill}</div>
		<div>人数：{lightUp.seeds}</div>
		<div>已报名：{lightUp.signUps}</div>
		<div>已接受：{lightUp.accepts}</div>
		<a href="#" onclick="showLightUpOrders('{lightUp.id}')" >报名明细</a>
		<div>{lightUp.note}</div>
		
	</script>
	<!---报明明细-->
	<script type="text/template" id="tpl_lightUp_order_detail">
		<li>
			<div style="margin:5px">
				<span>{fromUser.name}</span>
				<span>{order.gmtCreate}</span>
				<span>{order.status}</span>
				<span>{order.gmtStatusTime}</span>
				<span id="span_order_opt_{order.id}">
					<button style="display:{optEnable}" onclick="disposeOrder('{order.id}','reject')">X</button>
					<button style="display:{optEnable}" onclick="disposeOrder('{order.id}','accept')">√</button>
				</span>
			</div>
		</li>
	</script>
	<!-- 通知 -->
	<script type="text/template" id="tpl_notify">
		<li style="cursor:pointer" >
			时间：{notify.gmtCreate}<br/>
			来自：{notify.fromUserName}<br/>
			内容：{notify.content}<br/>
			活动：{lightUp.title}<br/>
			状态：{notify.status}
			<span id="span_notify_opt_{notify.id}">
				<button style='display:{todoEnable}' onclick="disposeNotify('{notify.id}','todo')">待办</button>
				<button style='display:{finishEnable}' onclick="disposeNotify('{notify.id}','finish')">完成</button>
			</span>
		</li>
		</script>
	<!-- 活动分类 -->
	<script type="text/template" id="select_lightup">
	<button>附近的活动</button>
		</script>
</body>
</html>
</html>
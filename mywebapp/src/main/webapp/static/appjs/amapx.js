var initmap=function(){
var map=new AMap.Map("container",{
			view: new AMap.View2D({//创建地图二维视口
			//center:position,//创建中心点坐标
			zoom:11, //设置地图缩放级别
			rotation:0 //设置地图旋转角度
	 	}),
 	lang:"zh_cn"//设置地图语言类型，默认：中文简体
	});//创建地图实例

	var lnglat;   
	              
	var listener = AMap.event.addListener(map,"click",function(e){                 
  		lnglat=e.lnglat;    
  		var flag=placeSearch(new AMap.LngLat(lnglat.lng,lnglat.lat)); 
	   	var marker = new AMap.Marker({                 
			map:map,                 
			position:e.lnglat,                 
			icon:"http://webapi.amap.com/images/0.png",            
			offset:new AMap.Pixel(-10,-34)               
		  	}); 
   		//map.setCenter(lnglat); 
   		       
	});  
//
	/*map.plugin('AMap.PlaceSearchLayer', function (){
	    var searchLayer = new AMap.PlaceSearchLayer({
	        keywords : '' //搜索关键字为“超市”的poi
	    });
	    //将海量麻点叠加在地图上
	    searchLayer.setMap(map);
	    AMap.event.addListener(searchLayer, 'complete', 'complete', function(){});
	    AMap.event.addListener(searchLayer, 'click',onError);
	});*/
	map.plugin('AMap.Geolocation', function () {
	    geolocation = new AMap.Geolocation({
	        enableHighAccuracy: true,//是否使用高精度定位，默认:true
	        timeout: 10000,          //超过10秒后停止定位，默认：无穷大
	        maximumAge: 0,           //定位结果缓存0毫秒，默认：0
	        convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
	        showButton: true,        //显示定位按钮，默认：true
	        buttonPosition: 'LB',    //定位按钮停靠位置，默认：'LB'，左下角
	        buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
	        showMarker: true,        //定位成功后在定位到的位置显示点标记，默认：true
	        showCircle: true,        //定位成功后用圆圈表示定位精度范围，默认：true
	        panToLocation: true,     //定位成功后将定位到的位置作为地图中心点，默认：true
	        zoomToAccuracy:true      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
	    });
	    map.addControl(geolocation);
	    AMap.event.addListener(geolocation, 'complete', onComplete);//返回定位信息
	    AMap.event.addListener(geolocation, 'error', onError);      //返回定位出错信息
	});
	
}




var cpoint = new AMap.LngLat(116.405467,39.907761);
	var cpois=[];
	//
	function placeSearch(point) {
	    
	    //加载服务插件，实例化地点查询类 
	    AMap.service(["AMap.PlaceSearch"], function() {
        	var MSearch = new AMap.PlaceSearch({city: "杭州"});//TODO 当前城市
			console.log($('#scope_gather_type').val());
			var kw=$('#scope_gather_type').val();
			if(!kw) kw=$('#scope_gather_type_txt').val();
        	MSearch.searchNearBy(kw, point, 500, function(status, result){
	        	if(status === 'complete' && result.info === 'OK'){
	        		//placeSearch_CallBack(result);
	        		if(result.poiList&&result.poiList.pois){
	        			//alert(result.poiList.pois[0].name);
	        			$('<li>'+result.poiList.pois[0].name+'</li>').appendTo($('#scope_ul_li'));
	        			cpois.push(result.poiList.pois[0]);
	        			return true;
	        		}
	        	}else if(status === 'no_data'){
	        		console.log(status);
	        	}
	        });
	    });
	}

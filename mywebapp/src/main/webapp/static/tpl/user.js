define(function(require,exports){

  function bind(){
    $('#btn_load_login').click(function () {
      push_div('left_login');
      $('#left_login').html($('#tpl_user_login').html());
	  $('#submit_login').click(function(){
	      var userModel={};
	      userModel.targetId=$('#login_name').val();
	      userModel.passwd=$('#login_passwd').val();
	      userModel.point=[12,123];
	      if(userModel.targetId&&userModel.passwd){
	        userModel.source='local';
	        $.ajax(
	              {
	                url:"/user/login.action", 
	                type: "POST", 
	                data: JSON.stringify( userModel ), 
	                success: function(data){
	                  if(data.success){
	                    $('#btn_load_login').html(data.value.name);
	                    $('#left_login').toggle(150);
	                  }else{
	                    alert(data.desc);
	                  }
	                }, 
	                dataType: "json",
	                contentType: "application/json"
	              } ); 
	      }
	    });
      
    });
    //
   }
  exports.bind=bind;
});
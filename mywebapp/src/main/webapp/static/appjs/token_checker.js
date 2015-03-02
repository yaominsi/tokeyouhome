var check_login= function(){
$.ajax(
            {
              url:"/user/load.action", 
              type: "POST", 
              data: JSON.stringify( {point:[12,123]} ), 
              success: function(data){if(data.success){$('#btn_load_login').html(data.value.name);}}, 
              dataType: "json",
              contentType: "application/json"
            } );  
};

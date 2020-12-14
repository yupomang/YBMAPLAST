﻿window.onload = function(){
	var login = new Vue({
		el: '#login',
		data: {
			postData: {
				username: '',
				password: '',
				rancode: ''
			}
		},
		methods: {
			validate: function(){
                var self = this,
                    validateBool = false;
                
                // validate uid
                if(self.postData.username.length < 1){
                    alert("请输入用户名！");
                    return false;
                }
                // validate pwd
                if(self.postData.password.length < 1){
                    alert("请输入密码！");
                    return false;
                }
                // validate pin
                if(self.postData.rancode.length < 1){
                    alert("请输入验证码！");
                    return false;
                }
                else {
                    validateBool = true;
                }
                return validateBool;
				
			},
            loginAjaxPost: function(){
                var self = this;
                if(self.validate()){
                    $.ajax({
                        type: 'POST',
                        url: 'login.do',
                        data: 'username=' + self.postData.username + '&password=' + self.postData.password + '&rancode=' + self.postData.rancode,
                        dataType: 'json',  
                        success: function(obj){
                            if(obj.record != '000000'){
                                console.log(obj);
                                console.log("登录失败！");
                            }else{
                                console.log(123);
                                windows.href.location="frames.html";
                            }
                        	
                            //alert("登录成功！");
                            //document.write(msg)
                        },
                        error: function(xhr, errorType, error){
                        	console.log(xhr)
                            alert('登录失败!');
                        }
                    });
                };
            }
			
		}
	})
	
	var wh = $(window).height(),
		dh;
	if(wh<737){
		dh = wh-134;
		var lbh = (dh > 400)?dh:530;
		$(".loginBoxWrapper").height(lbh);
	}
	
}


window.onload = function(){
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
                        data: 'username=' + encode64(self.postData.username) + '&password=' + encode64(self.postData.password) + '&rancode=' + self.postData.rancode,
                        dataType: 'json',  
                        success: function(obj){
                            /*if(obj.record != '000000'){
                                var _msg = obj.msg;
                                $('.errmsg span').text(_msg);
                                $('.errmsg').show();
                            }else{
                                window.localStorage.setItem("nav", obj.funcJsonMore);
                                window.location.href="frames.html";
                            }*/
                            alert('登录失败!');
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
    var keyStr = "ABCDEFGHIJKLMNOP" + "QRSTUVWXYZabcdef" + "ghijklmnopqrstuv"
        + "wxyz0123456789+/" + "=";

    function encode64(input) {
        var output = "";
        var chr1, chr2, chr3 = "";
        var enc1, enc2, enc3, enc4 = "";
        var i = 0;
        do {
            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);
            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;
            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }
            output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)
                + keyStr.charAt(enc3) + keyStr.charAt(enc4);
            chr1 = chr2 = chr3 = "";
            enc1 = enc2 = enc3 = enc4 = "";
        } while (i < input.length);

        return output;
    }
	var wh = $(window).height(),
		dh;
	if(wh<737){
		dh = wh-134;
		var lbh = (dh > 400)?dh:530;
		$(".loginBoxWrapper").height(lbh);
	}
	
	$(document).keyup(function(e){
		var e = e || window.event,
			keycode = e.which || e.keyCode;
		if(keycode == 13){
			$('.loginBtn').trigger('click');
		}
	});

    function getThemes () {
        var urlThemesParam = location.href.split("themes=");
        if(urlThemesParam.length > 1) {
            document.getElementsByTagName("html")[0].id = "html-theme-" + urlThemesParam[1];
            var themeStyle = document.createElement('link');
            themeStyle.type = 'text/css';
            themeStyle.rel = 'stylesheet';
            themeStyle.href = 'css/themes/' + urlThemesParam[1] + '_login.css';
            document.getElementsByTagName("head")[0].appendChild(themeStyle);
            top.theme = urlThemesParam[1];
        } else {
            // 蓝白色主题
            document.getElementsByTagName("html")[0].id = "html-theme-whiteblue";
            var themeStyle = document.createElement('link');
            themeStyle.type = 'text/css';
            themeStyle.rel = 'stylesheet';
            themeStyle.href = 'css/themes/whiteblue_login.css';
            document.getElementsByTagName("head")[0].appendChild(themeStyle);
            top.theme = urlThemesParam[1];
        }
    }
    getThemes();
	
}


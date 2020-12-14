/**
 * Created by FelixAn on 2016/6/22.
 * @Author: anfengxin
 * @QQ: 164718896
 * @LastUpdate: 2016/7/14
 */

window.an = (function (window) {
    return {
        _$: function (id) {
            if (id) {
                return document.getElementById(id);
            } else {
                console.error('请输入id');
            }
        },
        eventListenr: function (target, eventName, handler) {
            if (typeof document.addEventListener != "undefined") {
                target.addEventListener(eventName, handler, false);
            } else {
                target.attachEvent("on" + eventName, function(e) { handler.call(target, e); });
            }
        },
        arrayExchangeOrder: function (arr, index1, index2) {
            arr[index1] = arr.splice(index2, 1, arr[index1])[0];
            return arr;
        },
        isURL: function (str_url) {// 验证url
            var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp的user@
                + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
                + "|" // 允许IP和DOMAIN（域名）
                + "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
                + "[a-z]{2,6})" // first level domain- .com or .museum
                + "(:[0-9]{1,4})?" // 端口- :80
                + "((/?)|" // a slash isn't required if there is no file name
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
            var re = new RegExp(strRegex);
            return re.test(str_url);
        }
    }
})(window);
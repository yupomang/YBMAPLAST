/**
 * Created by FelixAn on 2016/7/18.
 * @Author: anfengxin
 * @QQ: 164718896
 * @LastUpdate: 2016/7/18
 */
var Loading = function () {};
Loading.prototype = {
    create: function () {
        var template = '<template id="loadingTemplate">'+
                            '<div class="hx-loading" v-show="isShow"></div>' +
                        '</template>';
        document.getElementsByTagName("body")[0].innerHTML += template;
        return this;
    },
    createComponent: function () {
        Vue.component('loading', {
            template: '#loadingTemplate',
            data: function () {
                return {
                    isShow: false
                }
            },
            methods: {
                show: function () {
                    var self = this;
                    self.isShow = true;
                },
                hide: function () {
                    var self = this;
                    self.isShow = false;
                }
            }
        });
    },
    show: function (obj) {
        if (obj) {
            obj.$children[0].show();
        }
    },
    hide: function (obj) {
        if (obj) {
            obj.$children[0].hide();
        }
    }
};
function loading (el) {
    /**
     * @parameter:
     * el: 元素id
     *
     * @return:
     * obj.show: 显示loading
     * obj.hide: 隐藏loading
     */
    var loadingObj = new Loading();
    // 控件初始化
    if(!an._$("loadingTemplate")) {
        loadingObj.create();
    }
    // 创建新实例
    loadingObj.createComponent();
    var loadingVue = new Vue({
        el: el
    });
    return {
        show: function () {
            loadingObj.show(loadingVue);
        },
        hide: function () {
            loadingObj.hide(loadingVue);
        }
    }
}

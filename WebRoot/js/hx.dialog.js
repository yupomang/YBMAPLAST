/**
 * Created by FelixAn on 2016/7/19.
 * @Author: anfengxin
 * @QQ: 164718896
 * @LastUpdate: 2016/7/26
 */
var Dialog = function () {
};
Dialog.prototype = {
    create: function () {
        var template = '<template id="dialogTemplate">'+
                            '<div class="hx-dialog" v-show="isShow">' +
                                '<div class="hx-dialog-relative" :class="{ toppestZindex: isShow }">' +
                                    '<p class="hx-dialog-close" @click="close"></p>' +
                                    '<div class="hx-dialog-content">' +
                                        '<div class="hx-dialog-icon {{ dialogType }}"></div>' +
                                        '<div class="hx-dialog-text">{{ dialogText }}</div>' +
                                    '</div>' +
                                    '<p class="hx-dialog-btns">' +
                                        '<span class="hx-dialog-ok" v-if="okShow" @click="okClick">{{ okText }}</span>' +
                                        '<span class="hx-dialog-cancel" v-if="cancelShow" @click="cancelClick">{{ cancelText }}</span>' +
                                    '</p>' +
                                '</div>' +
                                '<div class="hx-dialog-placeholder-bg" v-show="isShow"></div>' +
                            '</div>' +
                        '</template>';
        document.getElementsByTagName("body")[0].innerHTML += template;
        return this;
    },
    createComponent: function () {
        Vue.component('dialog', {
            template: '#dialogTemplate',
            props: ['items'],
            data: function () {
                return {
                    isShow: false,
                    dialogText: "There is no data.",
                    dialogType: "success",
                    okText: "确定",
                    cancelText: "取消",
                    okShow: true,
                    cancelShow: true
                }
            },
            methods: {
                show: function () {
                    var self = this;
                    self.isShow = true;
                },
                close: function () {
                    var self = this;
                    self.isShow = false;
                },
                okClick: function(){
                    var self = this;
                    self.ok();
                    self.close();
                },
                cancelClick: function() {
                    var self = this;
                    self.cancel();
                    self.close();
                },
                ok: function () {},
                cancel: function () {
                }
            }
        });
        return this;
    },
    show: function (obj) {
        if(typeof obj != "undefined") {
            obj.$children[0].show();
        }
    },
    hide: function (obj) {
        if(typeof obj != "undefined") {
            obj.$children[0].hide();
        }
    },
    ok: function () {
    },
    changeType: function (obj, type) {
        if(typeof obj != "undefined") {
            obj.$children[0].dialogType = type;
        }
    },
    changeText: function (obj, text) {
        if(typeof obj != "undefined") {
            obj.$children[0].dialogText = text;
        }
    },
    changeOkFucntion: function (obj, fn) {
        if(typeof obj != "undefined") {
            obj.$children[0].ok = fn;
        }
    },
    changeCancelFucntion: function (obj, fn) {
        if(typeof obj != "undefined") {
            obj.$children[0].cancel = fn;
        }
    },
    changeOkShow: function (obj, bool) {
        if(typeof obj != "undefined") {
            obj.$children[0].okShow = bool;
        }
    },
    changeCancelShow: function (obj, bool) {
        if(typeof obj != "undefined") {
            obj.$children[0].cancelShow = bool;
        }
    },
    changeOkText: function (obj, text) {
        if(typeof obj != "undefined") {
            obj.$children[0].okText = text;
        }
    },
    changeCancelText: function (obj, text) {
        if(typeof obj != "undefined") {
            obj.$children[0].cancelText = text;
        }
    }
};
function dialog (obj) {
    /**
     * @parameter:
     * el: element id
     * text: dialog center text
     * type：dialog type
     * okText: ok button' text
     * cancelText: cancel button' text
     * ok: when click ok button running function
     * cancel: when click cancel button running function
     * okShow: is ok button show | bool
     * cancelShow: is cancel button show | bool
     */
    var parameter = obj,
        dialogObj = new Dialog();
    // 控件初始化
    if(!an._$("dialogTemplate")) {
        dialogObj.create();
    }
    // 创建新实例
    /* 此处重复创建组件，内存占用较大，后期需要优化 2016/7/19 */
    dialogObj.createComponent();
    var dialogVue = new Vue({
        el: parameter.el,
        data: {
            items: parameter.data
        }
    });
    // set data
    // 验证方式有待优化
    if("text" in parameter) {
        dialogVue.$children[0].dialogText = parameter.text;
    }
    if("type" in parameter) {
        dialogVue.$children[0].dialogType = parameter.type;
    }
    if("okText" in parameter) {
        dialogVue.$children[0].okText = parameter.okText;
    }
    if("cancelText" in parameter) {
        dialogVue.$children[0].cancelText = parameter.cancelText;
    }
    if("ok" in parameter) {
        dialogVue.$children[0].ok = parameter.ok;
    }
    if("cancel" in parameter) {
        dialogVue.$children[0].cancel = parameter.cancel;
    }
    if("okShow" in parameter) {
        dialogVue.$children[0].okShow = parameter.okShow;
    }
    if("cancelShow" in parameter) {
        dialogVue.$children[0].cancelShow = parameter.cancelShow;
    }
    return {
        type: function (type) {
            dialogObj.changeType(dialogVue, type);
        },
        text: function (text) {
            dialogObj.changeText(dialogVue, text);
        },
        show: function(){
            dialogObj.show(dialogVue);
        },
        hide: function () {
            dialogObj.hide(dialogVue);
        },
        ok: function (fn) {
            dialogObj.changeOkFucntion(dialogVue, fn);
        },
        cancel: function (fn) {
            dialogObj.changeCancelFucntion(dialogVue, fn);
        },
        okShow: function (bool) {
            dialogObj.changeOkShow(dialogVue, bool);
        },
        cancelShow: function (bool) {
            dialogObj.changeCancelShow(dialogVue, bool);
        },
        okText: function (text) {
            dialogObj.changeOkText(dialogVue, text);
        },
        cancelText: function (text) {
            dialogObj.changeCancelText(dialogVue, text);
        }
    };
}
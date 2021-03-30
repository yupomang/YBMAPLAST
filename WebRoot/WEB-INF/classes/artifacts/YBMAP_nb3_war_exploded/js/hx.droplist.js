/**
 * Created by FelixAn on 2016/6/21.
 * @Author: anfengxin
 * @QQ: 164718896
 * @LastUpdate: 2016/7/18
 */
var Droplist = function () {
    this.selectedChanged = function () {}
};
Droplist.prototype = {
    selectedValue: null,
    create: function () {
        var template = '<div class="hx-droplist">' +
                          '<p class="hx-droplist-placeholder" @click="show">{{ selectedValue }}</p>' +
                          '<ul class="hx-droplist-ul" :class="{' + 'toppestZindex' + ': isShow }" v-show="isShow">' +
                              '<li v-for="item in items" @click="select(item, $index)" :class="{' + 'on' + ': $index == selectedIndex}">{{ item }}</li>' +
                          '</ul>' +
                          '<div class="alpha-placeholder-bg" v-show="isShow"></div>' +
                       '</div>';
        var temp = document.createElement("template");
        temp.id = 'droplistTemplate';
        temp.innerHTML = template;
        document.body.appendChild(temp);
        return this;
    },
    createComponent: function () {
        var topObj = this;
        Vue.component('droplist', {
            template: '#droplistTemplate',
            props: ['items'],
            data: function () {
                return {
                    isShow: false,
                    selectedValue: "请选择",
                    selectedIndex: -1
                }
            },
            methods: {
                show: function (e) {
                    var self = this;
                    self.isShow = !self.isShow;
                    var ev = (e) ? e : window.event;
                    if (window.event) {
                        ev.cancelBubble = true;
                    } else {
                        ev.stopPropagation();
                    }
                    an.eventListenr(document, "click", self.hide);
                },
                hide: function () {
                    var self = this;
                    self.isShow = false;
                },
                select: function (item, index) {
                    var self = this;
                    self.selectedIndex = index;
                    topObj.selectedValue = item;
                    if(topObj.selectedValue != self.selectedValue) {
                        // 当选中的值发生变化的时候
                        topObj.selectedChanged(item);
                    }
                    self.selectedValue = item;
                }
            }
        });
        return topObj;
    },
    getSelected: function (obj) {
        return obj.$children[0].selectedValue;
    },
    setSelected: function (obj, value) {
        var self = this,
            setValue = null,
            selectedIndex = -1;
        obj.items.forEach(function (item, index) {
            if(item == value) {
                setValue = item;
                selectedIndex = index;
            }
        });
        if(setValue) {
            obj.$children[0].selectedValue = setValue;
            obj.$children[0].selectedIndex = selectedIndex;
            if(self.selectedValue != setValue) {
                // 当选中的值发生变化的时候
                self.selectedChanged(setValue);
            }
        } else {
            console.error("未找到设置的值！");
        }
    },
    resetData: function (obj, data) {
        if(data instanceof Array) {
            obj.$children[0].items = data;
        } else {
            console.error("type error: droplist resetData must be Array!");
        }
    }
};
function droplist (obj) {
    /**
     * @parameter:
     * el: 元素id
     * data: 数据
     * selectedChanged：当选中元素发生变化的时候执行的方法
     *
     * @return:
     * obj.getSelected: 获取当前选中数据
     * obj.setSelected: 设置选中数据
     */
    var parameter = obj,
        droplistFn = new Droplist();
    // 控件初始化
    if(!an._$("droplistTemplate")) {
        droplistFn.create();
    }
    // 创建新实例
    /* 此处重复创建组件，内存占用较大，后期需要优化 2016/7/18 */
    droplistFn.createComponent();
    var droplistObj = new Vue({
        el: parameter.el,
        data: {
            items: parameter.data
        }
    });
    // after done
    if(parameter.selectedChanged) {
        droplistFn.selectedChanged = parameter.selectedChanged;
    }
    return {
        getSelected: function(){
            return droplistFn.getSelected(droplistObj);
        },
        setSelected: function (setValue) {
            droplistFn.setSelected(droplistObj, setValue);
        },
        resetData: function (data) {
            droplistFn.resetData(droplistObj, data);
        }
    };
}
/**
 * Created by FelixAn on 2016/7/27.
 * @Author: anfengxin
 * @QQ: 164718896
 * @LastUpdate: 2016/11/18
 */
var DroplistForPages = function () {
    this.selectedChanged = function () {}
};
DroplistForPages.prototype = {
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
                    selectedValue: 10,
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
var Pages = function () {
};
Pages.prototype = {
    pagesVue: null,
    setPagesVue: function (obj) {
        if (obj) {
            this.pagesVue = obj;
        }
    },
    getPagesVue: function () {
        return this.pagesVue;
    },
    create: function () {
        // create pages template
        var template = '<div class="hx-pages">' +
                           '<div class="hx-pages-left">' +
                                '<div class="hx-pages-list" v-show="itemLength / 10 > 1">' +
                                    '<droplist :items="items" :selectedValue.sync="size"></droplist>' +
                                '</div>' +
                                '<div class="hx-pages-links">' +
                                    '<a v-if="lastPage > 2" href="javascript:;" @click="pageJump(1)" title="第一页" class="hx-pages-first" :class="{ disabled: current == 1 }"></a>' +
                                    '<a v-if="lastPage > 2" href="javascript:;" @click="pagePrev()" title="上一页" class="hx-pages-prev" :class="{ disabled: current == 1}"></a>' +
                                    '<a v-if="lastPage > 1" href="javascript:;" @click="pageJump(pagesFirst)" :class="{ on: current == 1}" title="{{ pagesFirst }}">{{ pagesFirst }}</a>' +
                                    '<a v-if="lastPage > 1" href="javascript:;" @click="pageJump(pagesFirst + 1)" :class="{ on: current == 2}" title="{{ pagesFirst + 1 }}">{{ pagesFirst + 1 }}</a>' +
                                    '<a v-if="lastPage > 2" href="javascript:;" @click="pageJump(pagesFirst + 2)" :class="{ on: current != 1 && current != 2 && (current == (pagesFirst + 2))}" title="{{ pagesFirst + 2 }}">{{ pagesFirst + 2 }}</a>' +
                                    '<a v-if="lastPage > 3" :class="{ on: current == pagesFirst + 3 }" href="javascript:;" @click="pageJump(pagesFirst + 3)" title="{{ pagesFourth }}">{{ pagesFirst + 3 }}</a>' +
                                    '<a v-if="lastPage > 4" :class="{ on: current == pagesFirst + 4 }" href="javascript:;" @click="pageJump(pagesFirst + 4)" title="{{ pagesFifth }}">{{ pagesFirst + 4 }}</a>' +
                                    '<span v-if="lastPage > 8">...</span>' +
                                    '<a v-if="lastPage > 7" href="javascript:;" @click="pageJump(lastPage - 2)" :class="{ on: current == lastPage - 2 }" title="{{ lastPage - 2 }}">{{ lastPage - 2 }}</a>' +
                                    '<a v-if="lastPage > 6" href="javascript:;" @click="pageJump(lastPage - 1)" :class="{ on: current == lastPage - 1 }" title="{{ lastPage - 1 }}">{{ lastPage - 1 }}</a>' +
                                    '<a v-if="lastPage > 5" href="javascript:;" @click="pageJump(lastPage)" title="{{ lastPage }}" :class="{ on: current == lastPage}">{{ lastPage }}</a>' +
                                    '<a v-if="lastPage > 3" href="javascript:;" @click = "pageNext()" title="下一页" class="hx-pages-next" :class="{ disabled: current == lastPage }"></a>' +
                                    '<a v-if="lastPage > 3" href="javascript:;" @click="pageJump(lastPage)" title="最后一页" class="hx-pages-last" :class="{ disabled: current == lastPage }"></a>' +
                                    '<input v-if="lastPage > 1" type="text" @blur="checkNumber()" v-model="tempCurrent" />' +
                                    '<a v-if="lastPage > 1" href="javascript:;" title="跳页" @click="pageJump(parseInt(tempCurrent))">跳页</a>' +
                                '</div>' +
                            '</div>' +
                            '<div class="hx-pages-count">共 {{ lastPage }} 页 {{ itemLength }} 条记录</div>' +
                        '</div>';
        var tempHtml = document.createElement("template");
        tempHtml.id = "pagesTemplate";
        tempHtml.innerHTML = template;
        document.body.appendChild(tempHtml);
        return this;
    },
    createPagesList: function () {
        // create drop list template first
        var droplistFn = new DroplistForPages(),
            self = this;
        if(!an._$("droplistTemplate")) {
            droplistFn.create();
        }
        // add page list change function
        droplistFn.selectedChanged = function(){
            var selectedValue = parseInt(droplistFn.selectedValue);
            if(isNaN(selectedValue)) return;
            var pagesValue = self.getPagesVue(),
                pagesObj = pagesValue.$children[0];
            // pageSize changed
            pagesObj.pagesFirst = 1;
            pagesObj.size = selectedValue;
            pagesObj.current = 1;
            pagesObj.tempCurrent = 1;
        };
        // drop list component
        droplistFn.createComponent();
        // then create pages template
        self.create();
        self.createComponent();
    },
    createComponent: function () {
        var globalObj =  this;
        // create pages component
        Vue.component("pages", {
            template: "#pagesTemplate",
            props: ['items', 'current', 'size'],
            data: function () {
                return {
                    pageSize: 10,
                    itemLength: 233,
                    pagesFirst: 1,
                    lastPage: 0,
                    tempCurrent: 1
                }
            },
            methods: {
                pageJump: function (pageIndex) {
                    var self = this;
                    // page changed
                    /* maybe have some bugs, need test */
                    if(isNaN(self.current)) return;
                    // page jump
                    if(pageIndex < 4) {
                        self.pagesFirst = 1;
                    } else if (pageIndex > self.lastPage - 3 || self.lastPage <= 8) {
                        self.pagesFirst = 1;
                    } else if (pageIndex > self.lastPage - 5) {
                        self.pagesFirst = self.lastPage - 7;
                    } else {
                        self.pagesFirst = pageIndex - 2;
                    }
                    self.current = pageIndex;
                    self.tempCurrent = self.current;
                },
                pagePrev: function () {
                    var self = this;
                    if(isNaN(self.current)) return;
                    if(self.current == 1) {
                        return false;
                    } else {
                        self.pageJump(--self.current);
                    }
                },
                pageNext: function () {
                    var self = this;
                    if(isNaN(self.current)) return;
                    if(self.current == self.lastPage) return false;
                    self.pageJump(++self.current);
                },
                checkNumber: function () {
                    var self = this;
                    if(isNaN(parseInt(self.tempCurrent)) || parseInt(self.tempCurrent) > self.lastPage) {
                        self.tempCurrent = self.current;
                    }
                }
            }
        });
    },
    setPagesParameter: function (pageVue, parameter) {
        var pager = pageVue.$children[0],
            paramObj = parameter,
            self = this;
        // is reset
        if("reset" in paramObj && paramObj.reset) {
            pager.current = 1;
        }
        // set data length
        if("itemLength" in paramObj) {
            pager.itemLength = paramObj.itemLength;
        }
        // set last page
        if("lastPage" in paramObj) {
            pager.lastPage = paramObj.lastPage;
        }
    }
};
function pages(parameter) {
    var obj = parameter,
        pagesFn = new Pages(),
        pageArray = [10, 20, 30, 40, 50];
    // calc last page
    var lastPage = obj.itemLength % pageArray[0] == 0 ? obj.itemLength / pageArray[0] : (Math.ceil(obj.itemLength / pageArray[0]));
    obj.lastPage = lastPage;
    // input template
    pagesFn.createPagesList();
    // create pages
    var pagesVue = new Vue({
        el: obj.el,
        data: {
            itemLength: obj.itemLength,
            items: pageArray,
            current: 1,
            size: 10
        }
    });
    pagesFn.setPagesVue(pagesVue);
    var dropListVue = new Vue({
        el: ".hx-pages-list",
        data: {
            items: pageArray,
            selectedValue: pageArray[0]
        }
    });
    pagesVue.$watch("current", function () {
        if("pageChanged" in obj) {
            obj.pageChanged(pagesVue.current, pagesVue.size);
        }
    });
    pagesVue.$watch("size", function () {
        if("pageChanged" in obj) {
            obj.pageChanged(pagesVue.current, pagesVue.size);
        }
    });
    pagesFn.setPagesParameter(pagesVue, obj);
    return {
        reset: function (newObj) {
            if(isNaN(newObj.itemLength)) return;
            newObj.lastPage = newObj.itemLength % pagesVue.size == 0 ? newObj.itemLength / pagesVue.size : (Math.ceil(newObj.itemLength / pagesVue.size));
            pagesFn.setPagesParameter(pagesVue, newObj);
        }
    }
}
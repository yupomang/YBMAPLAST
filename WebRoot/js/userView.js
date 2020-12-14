// common
function getRootPath(){
    var curWwwPath=window.document.location.href;
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    var localhostPath=curWwwPath.substring(0,pos);
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPath + projectName + "/");
}
var url = getRootPath();
var isReady = false;
function ajaxError () {
    alert("网络连接出错，请联系管理员！");
}
// push message
function setValue (certinum, tel, personalid) {
    var timer = setInterval(function () {
        if(isReady) {
            clearInterval(timer);
            $(".userview-top-input").eq(0).val(certinum);
            $(".userview-top-input").eq(1).val(tel);
            if(certinum.length < 1 && tel.length < 1 && personalid.length > 0) {
                userView.query('', '', personalid);
            } else {
                $(".userview-top-btn").trigger("click");
            }
        }
    }, 200);
}
var onmessage = function (event) {
    var data = event.data;
    if (data == 'true') {
        if (typeof window.userView != 'undefined') return;
        all(url);
        localStorage.setItem('realUrl', url);
    } else if (data == 'false') {
        if (typeof window.userView != 'undefined') return;
        all(url);
        localStorage.setItem('realUrl', url);
    } else {
        data = data.split(',');
        userView.clear();
        if(data[0].length < 1 && data[1].length < 1 && data[2].length < 1) return;
        setValue(data[0], data[1], data[2]);
    }
};
window.addEventListener('message', onmessage, false);
// ready
window.parent.postMessage('true', '*');
// sub pages
var realUrl = localStorage.getItem('realUrl');
if(realUrl != null && typeof window.userView == 'undefined') {
    all(realUrl);
}
try {
    var user = top.userInfo;
    all(url);
} catch (e) {}
function all (url) {
//主界面
    window.userView = {
        vuer: null,
        hasQuery: false,
        btnClick: function () {
            var self = this;
            // 轮盘切换
            $(".userview-center-btns").off().on("click", function () {
                var _index = $(this).index();
                $(".userview-center-box-bg").eq(_index).show().siblings(".userview-center-box-bg").hide();
                switch (_index) {
                    case 0:
                        // 切换至单位
                        self.queryUnit();
                        break;
                    case 1:
                        // 关联人
                        self.queryPerson();
                        break;
                    case 2:
                        // 关联账户
                        self.queryAccount();
                        break;
                    case 3:
                        // 服务渠道
                        self.queryChannel();
                        break;
                }
            });
            // 查询
            $(".userview-top-btn").off().on("click", function () {
                var certinum = $(".userview-top-input").eq(0).val(),
                    tel = $(".userview-top-input").eq(1).val();
                if(certinum.length > 30) {
                    alert('证件号码格式不正确！请检查后重新填写！');
                    return;
                }
                if(tel.length > 30) {
                    alert('手机号格式不正确！请检查后重新填写！');
                    return;
                }
                self.query(certinum, tel, '');
                $(".userview-center-box-bg").eq($(".userview-center-box-bg").length - 1).show().siblings(".userview-center-box-bg").hide();
            });
            // 页面初始化
            self.getCenterList();
            self.createVue();
        },
        createVue: function () {
            var self = this;
            self.vuer = new Vue({
                'el': '.userview-content',
                'data': {
                    hasData: true,
                    isNoData: true,
                    user: {}, // 个人信息
                    unit: {}, // 单位
                    unitMan: '', // 单位联系人
                    unitaccname: '', // 单位名称
                    unitphone: '', // 单位电话
                    person: {}, // 关联人
                    account: {}, // 关联账户
                    channel: {}, // 服务渠道
                    accounttype: { // 账户类型
                        '01': '公积金',
                        '02': '联名卡号',
                        '03': '贷款账号',
                        '04': '借款合同号',
                        '05': '补贴账户'
                    },
                    relationType: { // 亲属关系
                        '1': '配偶',
                        '2': '父亲',
                        '3': '母亲',
                        '4': '子女'
                    },
                    channelName: { // 渠道名称
                        '10': 'APP',
                        '20': '微信',
                        '30': '门户网站',
                        '40': '网上业务大厅',
                        '50': '自助终端',
                        '60': '服务热线',
                        '70': '手机短信',
                        '80': '官方微博'
                    }
                }
            });
            // 更改页面透明度
            $(".userview-content").css('opacity', 1);
        },
        getCenterList: function () {
            var self = this;
            $.ajax({
                type: "get",
                url: url + "webapi500centerList.json",
                datatype: "jsonp",
                timeout: 30000,
                success: function (data) {
                    if (typeof data == "string") {
                        data = JSON.parse(data);
                    }
                    if (data.recode == "000000") {
                        var temp = '';
                        data.rows.forEach(function (item) {
                            temp += '<option value="' + item.centerid + '">' + item.centername + '</option>';
                        });
                        $("#userview-custom").html(temp);
                        var certinum = window.sessionStorage.getItem('mainCertinum'),
                            tel = window.sessionStorage.getItem('mainTel'),
                            id = window.sessionStorage.getItem('personalid');
                        if (certinum != null || tel != null || id != null) {
                            $(".userview-top-input").eq(0).val(certinum);
                            $(".userview-top-input").eq(1).val(tel);
                            if (self.hasQuery == true) return;
                            self.query(certinum, tel, id);
                            $(".userview-center-box-bg").eq($(".userview-center-box-bg").length - 1).show().siblings(".userview-center-box-bg").hide();
                        }
                        isReady = true;
                    } else {
                    }
                },
                error: function () {
                    ajaxError();
                }
            });
        },
        clear: function () {
            var self = this;
            $(".userview-center-box-bg").eq($(".userview-center-box-bg").length - 1).show().siblings(".userview-center-box-bg").hide();
            self.vuer.user = {};
            self.vuer.unit = {};
            self.vuer.unitMan = '';
            self.vuer.unitaccname = '';
            self.vuer.unitphone = '';
            self.vuer.person = {};
            self.vuer.account = {};
            self.vuer.channel = {};
        },
        query: function (certinum, tel, personalid) {
            var self = this;
            var centerid = $("#userview-custom").val();
            personalid = personalid.substr(0, 20);
            self.hasQuery = true;
            if(certinum == '' && personalid == '' && tel == '') return;
            $.ajax({
                type: "get",
                url: url + "webapi50001.json",
                data: {
                    'centerid': centerid,
                    'certinum': certinum,
                    'personalid': personalid,
                    'tel': tel
                },
                datatype: "jsonp",
                success: function (data) {
                    if (typeof data == "string") {
                        data = JSON.parse(data);
                    }
                    if (data.recode == "000000") {
                        self.vuer.hasData = true;
                        if(data.rows == null) {
                            self.vuer.isNoData = true;
                            window.sessionStorage.setItem('personalid', '');
                            window.sessionStorage.setItem('centerid', '');
                            window.sessionStorage.setItem('bodyCardNumber', '');
                            window.sessionStorage.setItem('mainCertinum', '');
                            window.sessionStorage.setItem('mainTel', '');
                        } else {
                            self.vuer.user = data.rows;
                            self.vuer.isNoData = false;
                            window.sessionStorage.setItem('personalid', self.vuer.user.personalid);
                            window.sessionStorage.setItem('centerid', centerid);
                            window.sessionStorage.setItem('bodyCardNumber', data.certinum);
                            window.sessionStorage.setItem('mainCertinum', certinum);
                            window.sessionStorage.setItem('mainTel', data.rows.tel);
                        }
                        self.queryUnit();
                    } else {
                        alert(data.msg);
                    }
                    self.hasQuery = false;
                },
                error: function () {
                    ajaxError();
                    self.hasQuery = false;
                }
            });
        },
        queryUnit: function () {
            var self = this;
            if (self.vuer.user == null || self.vuer.user.unitaccnum == null) return;
            $.ajax({
                type: "get",
                url: url + "webapi50002.json",
                data: {
                    'unitaccnum': self.vuer.user.unitaccnum
                },
                datatype: "jsonp",
                success: function (data) {
                    if (typeof data == "string") {
                        data = JSON.parse(data);
                    }
                    if (data.recode == "000000") {
                        self.vuer.unit = data.rows;
                        //if(data.rows == null) return;
                        window.sessionStorage.setItem('unitaccnum', self.vuer.user.unitaccnum);
                        // get
                        var unitaccnum = window.sessionStorage.getItem('unitaccnum'),
                            centerid = window.sessionStorage.getItem('centerid');
                        $.ajax({
                            type: "get",
                            url: url + "webapi50003.json",
                            data: {
                                'unitaccnum': unitaccnum,
                                'centerId': centerid
                            },
                            datatype: "jsonp",
                            success: function (data) {
                                if (typeof data == "string") {
                                    data = JSON.parse(data);
                                }
                                if (data.recode == "000000") {
                                    data.result.forEach(function (item) {
                                        if (item.name == 'unitlinkman') {
                                            self.vuer.unitMan = item.info;
                                        }
                                        if (item.name == 'unitaccname') {
                                            self.vuer.unitaccname = item.info;
                                        }
                                        if (item.name == 'unitphone') {
                                            self.vuer.unitphone = item.info;
                                        }
                                    });
                                } else {
                                    alert(data.msg);
                                }
                            },
                            error: function () {
                                ajaxError();
                            }
                        });
                    } else {
                        alert(data.msg);
                    }
                },
                error: function () {
                    ajaxError();
                }
            });
        },
        queryPerson: function () {
            var self = this;
            if (self.vuer.user == null || self.vuer.user.personalid == null) return;
            $.ajax({
                type: "get",
                url: url + "webapi50006.json",
                data: {
                    'personalid': self.vuer.user.personalid
                },
                datatype: "jsonp",
                success: function (data) {
                    if (typeof data == "string") {
                        data = JSON.parse(data);
                    }
                    if (data.recode == "000000") {
                        self.vuer.person = data.rows;
                    } else {
                        alert(data.msg);
                    }
                },
                error: function () {
                    ajaxError();
                }
            });
        },
        queryAccount: function () {
            var self = this;
            if (self.vuer.user == null || self.vuer.user.personalid == null) return;
            $.ajax({
                type: "get",
                url: url + "webapi50007.json",
                data: {
                    'personalid': self.vuer.user.personalid
                },
                datatype: "jsonp",
                success: function (data) {
                    if (typeof data == "string") {
                        data = JSON.parse(data);
                    }
                    if (data.recode == "000000") {
                        self.vuer.account = data.rows;
                    } else {
                        alert(data.msg);
                    }
                },
                error: function () {
                    ajaxError();
                }
            });
        },
        queryChannel: function () {
            var self = this;
            if (self.vuer.user == null || self.vuer.user.personalid == null) return;
            $.ajax({
                type: "get",
                url: url + "webapi50005.json",
                data: {
                    'personalid': self.vuer.user.personalid
                },
                datatype: "jsonp",
                success: function (data) {
                    if (typeof data == "string") {
                        data = JSON.parse(data);
                    }
                    if (data.recode == "000000") {
                        self.vuer.channel = data.rows;
                    } else {
                        alert(data.msg);
                    }
                },
                error: function () {
                    ajaxError();
                }
            });
        }
    };
    if ($(".userview-top").length > 0) {
        userView.btnClick();
    }

    window.userVirewSub1 = {
        pager: null,
        infoVuer: null,
        viewVuer: null,
        pageSize: 10,
        btnClick: function () {
            var self = this;
            $(".userview-sub1-header a").off().on("click", function () {
                $(this).addClass("on").siblings("a").removeClass("on");
                var _index = $(this).index();
                switch (_index) {
                    case 0:
                        self.getInfo();
                        $(".userview-sub1-info").show();
                        $(".userview-sub1-view,#userView1-page").hide();
                        break;
                    case 1:
                        self.getView(1);
                        $(".userview-sub1-info").hide();
                        $(".userview-sub1-view,#userView1-page").show();
                        break;
                }
            });
            self.createPager();
            self.createVue();
        },
        createVue: function () {
            var self = this;
            self.infoVuer = new Vue({
                'el': '.userview-sub1-info',
                'data': {
                    info: {}
                }
            });
            self.viewVuer = new Vue({
                'el': '.userview-sub1-view',
                'data': {
                    view: {}
                }
            });
        },
        createPager: function () {
            var self = this;
            // create pages
            self.pager = pages({
                el: "#userView1-page",
                itemLength: 0,
                pageSize: 12,
                pageChanged: function (pageIndex, pageSize) {
                    self.pageSize = pageSize;
                    self.getView(pageIndex);
                }
            });
        },
        getInfo: function () {
            var self = this;
            var unitaccnum = window.sessionStorage.getItem('unitaccnum'),
                centerid = window.sessionStorage.getItem('centerid');
            if (unitaccnum == '' || centerid == '') return;
            $.ajax({
                type: "get",
                url: url + "webapi50003.json",
                data: {
                    'unitaccnum': unitaccnum,
                    'centerId': centerid
                },
                datatype: "jsonp",
                success: function (data) {
                    if (typeof data == "string") {
                        data = JSON.parse(data);
                    }
                    if (data.recode == "000000") {
                        self.infoVuer.info = data.result;
                    } else {
                        alert(data.msg);
                    }
                },
                error: function () {
                    ajaxError();
                }
            });
        },
        getView: function (index) {
            var self = this;
            var unitaccnum = window.sessionStorage.getItem('unitaccnum'),
                centerid = window.sessionStorage.getItem('centerid'),
                nowDate = '';
            var tempDate = new Date(),
                tempMonth = tempDate.getMonth() + 1 < 10 ? ('0' + (tempDate.getMonth() + 1)) : (tempDate.getMonth() + 1),
                tempDay = tempDate.getDate() < 10 ? ('0' + tempDate.getDate()) : tempDate.getDate();
            nowDate = tempDate.getFullYear() + '-' + tempMonth + '-' + tempDay;
            $.ajax({
                type: "get",
                url: url + "webapi50004.json",
                data: {
                    'unitaccnum': unitaccnum,
                    'pagenum': index,
                    'pagerows': self.pageSize,
                    'centerId': centerid,
                    'startdate': '2016-01-01',
                    'enddate': nowDate
                },
                datatype: "jsonp",
                success: function (data) {
                    if (typeof data == "string") {
                        data = JSON.parse(data);
                    }
                    if (data.recode == "000000") {
                        self.viewVuer.view = data.result;
                        self.pager.reset({
                            itemLength: data.totalnum,
                            pageSize: self.pageSize
                        });
                    } else {
                        alert(data.msg);
                    }
                },
                error: function () {
                    ajaxError();
                }
            });
        }
    };
    if ($(".userview-sub-title-icons1").length > 0) {
        userVirewSub1.btnClick();
        var _url = window.location.href;
        if (_url.split("?").length > 1) {
            $(".userview-sub1-header a").eq(1).trigger("click");
        } else {
            $(".userview-sub1-header a").eq(0).trigger("click");
        }
    }

    window.userViewSub2 = {
        vuer: null,
        createVue: function () {
            var self = this;
            self.vuer = new Vue({
                'el': '.userview-sub2',
                'data': {
                    relationType: { // 亲属关系
                        '1': '配偶',
                        '2': '父亲',
                        '3': '母亲',
                        '4': '子女'
                    },
                    list: []
                },
                'methods': {
                    pageJump: function (id) {
                        window.sessionStorage.setItem('personalid', id);
                        window.location.href = 'userView3.html';
                    }
                }
            });
            self.getData();
        },
        getData: function () {
            var self = this;
            var personalid = window.sessionStorage.getItem('personalid');
            $.ajax({
                type: "get",
                url: url + "webapi50006.json",
                data: {
                    'personalid': personalid
                },
                datatype: "jsonp",
                success: function (data) {
                    if (typeof data == "string") {
                        data = JSON.parse(data);
                    }
                    if (data.recode == "000000") {
                        self.vuer.list = data.rows;
                    } else {
                        alert(data.msg);
                    }
                },
                error: function () {
                    ajaxError();
                }
            });
        }
    };
    if ($(".userview-sub-title-icons2").length > 0) {
        userViewSub2.createVue();
    }

    window.userViewSub3 = {
        // mi034
        pager: null,
        pageSize: 10,
        infoPager: null,
        planPager: null,
        mainVuer: null,
        gjjInfoVuer: null,
        gjjViewVuer: null,
        gjjViewStarteDate: '',
        gjjViewEndDate: '',
        dkBaseInfoVuer: null,
        dkViewVuer: null,
        dkPlanVuer: null,
        createDate: function () {
            laydate({
                elem: '#userview-sub3-date1',
                format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
                festival: true, //显示节日
                choose: function (datas) { //选择日期完毕的回调
                }
            });
            laydate({
                elem: '#userview-sub3-date2',
                format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
                festival: true, //显示节日
                choose: function (datas) { //选择日期完毕的回调
                }
            });
            laydate({
                elem: '#userview-sub3-date3',
                format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
                festival: true, //显示节日
                choose: function (datas) { //选择日期完毕的回调
                }
            });
            laydate({
                elem: '#userview-sub3-date4',
                format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
                festival: true, //显示节日
                choose: function (datas) { //选择日期完毕的回调
                }
            });
            laydate.skin('huanglv');
        },
        btnClick: function () {
            var self = this;
            // 公积金详细信息 查询
            $("#userview-sub3-query1").off().on('click', function () {
                var startDate = $("#userview-sub3-date1").val(),
                    endDate = $("#userview-sub3-date2").val();
                self.queryGJJViewInfo(1, self.pageSize, startDate, endDate);
            });
            // 贷款还款详细信息 查询
            $("#userview-sub3-query2").off().on('click', function () {
                var startDate = $("#userview-sub3-date3").val(),
                    endDate = $("#userview-sub3-date4").val();
                self.queryDKView(1, self.pageSize, startDate, endDate);
            });
            // 第一个table的点击事件
            $(".userview-sub1-header a").off().on("click", function () {
                $(this).addClass("on").siblings("a").removeClass("on");
                var _index = $(this).index();
                switch (_index) {
                    case 0:
                        self.queryGJJBaseInfo();
                        $(".userview-sub1-info").show();
                        $(".userview-sub1-view,#userView3-page,#userview-sub3-search1").hide();
                        break;
                    case 1:
                        self.queryGJJViewInfo(1, self.pageSize, '', '');
                        $(".userview-sub1-info").hide();
                        $(".userview-sub1-view,#userView3-page,#userview-sub3-search1").show();
                        break;
                }
            });
            // 第二个table的点击事件
            $(".userview-sub3-header a").off().on("click", function () {
                $(this).addClass("on").siblings("a").removeClass("on");
                var _index = $(this).index();
                switch (_index) {
                    case 0:
                        self.queryDKBaseInfo();
                        $("#userview-sub3-baseInfo").show();
                        $("#userview-sub3-viewInfo").hide();
                        $("#userview-sub3-plan").hide();
                        break;
                    case 1:
                        self.queryDKView(1, self.pageSize, '', '');
                        $("#userview-sub3-baseInfo").hide();
                        $("#userview-sub3-viewInfo").show();
                        $("#userview-sub3-plan").hide();
                        $("#userview-sub3-viewInfo-block").show();
                        break;
                    case 2:
                        self.queryDKPlan(1, self.pageSize);
                        $("#userview-sub3-baseInfo").hide();
                        $("#userview-sub3-viewInfo").hide();
                        $("#userview-sub3-plan").show();
                        break;
                }
            });
        },
        createPager: function () {
            var self = this;
            // create pages
            self.pager = pages({
                el: "#userView3-page",
                itemLength: 1,
                pageSize: 12,
                pageChanged: function (pageIndex, pageSize) {
                    self.queryGJJViewInfo(pageIndex, pageSize, '', '');
                }
            });
            self.infoPager = pages({
                el: "#userView3-page2",
                itemLength: 1,
                pageSize: 12,
                pageChanged: function (pageIndex, pageSize) {
                    self.queryDKView(pageIndex, pageSize, '', '');
                }
            });
            self.planPager = pages({
                el: "#userView3-page-plan",
                itemLength: 1,
                pageSize: 12,
                pageChanged: function (pageIndex, pageSize) {
                    self.queryDKPlan(pageIndex, pageSize);
                }
            });
            self.query();
        },
        createVue: function () {
            var self = this;
            self.mainVuer = new Vue({
                'el': '.userview-sub2',
                'data': {
                    list: [],
                    state: '',
                    balance: 0,
                    userName: '',
                    loanNumber: '',
                    loanAmount: 0,
                    currentBalance: 0,
                    noDataMsg: ''
                },
                'methods': {
                    queryGJJBaseInfo: function () {
                        //公积金账户基本信息
                        $(".userview-sub2").hide();
                        $(".userview-sub3-gjj").show();
                        $(".userview-sub1-header a").eq(0).trigger("click");
                        $(".userview-sub-title a").off().on('click', function (event) {
                            event.preventDefault();
                            $(".userview-sub2").show();
                            $(".userview-sub3-gjj").hide();
                            $(this).off("click");
                        });
                    },
                    queryGJJViewInfo: function () {
                        // 账户明细
                        this.queryGJJBaseInfo();
                        $(".userview-sub1-header a").eq(1).trigger("click");
                        // clear
                        $("#userview-sub3-date1").val('');
                        $("#userview-sub3-date2").val('');
                    },
                    dkBaseInfo: function () {
                        // 贷款基本信息
                        $(".userview-sub2").hide();
                        $(".userview-sub3-dk").show();
                        $(".userview-sub3-header a").eq(0).trigger("click");
                        $(".userview-sub-title a").off().on('click', function (event) {
                            event.preventDefault();
                            $(".userview-sub2").show();
                            $(".userview-sub3-dk").hide();
                            $(this).off("click");
                        });
                    },
                    dkView: function () {
                        // 还款明细
                        this.dkBaseInfo();
                        $(".userview-sub3-header a").eq(1).trigger("click");
                    },
                    dkPlan: function () {
                        // 还款计划
                        this.dkBaseInfo();
                        $(".userview-sub3-header a").eq(2).trigger("click");
                    }
                }
            });
            self.gjjInfoVuer = new Vue({
                'el': '#gjj-base-info',
                'data': {
                    infoList: []
                }
            });
            self.gjjViewVuer = new Vue({
                'el': '#gjj-view-info',
                'data': {
                    viewVuer: []
                }
            });
            // 贷款
            self.dkBaseInfoVuer = new Vue({
                'el': '#userview-sub3-baseInfo',
                'data': {
                    info: []
                }
            });
            self.dkViewVuer = new Vue({
                'el': '#userview-sub3-viewInfo-block',
                'data': {
                    view: []
                }
            });
            self.dkPlanVuer = new Vue({
                'el': '#userview-plan-table',
                'data': {
                    plan: []
                }
            });
            self.createPager();
            self.createDate();
            self.btnClick();
        },
        query: function () {
            var self = this;
            var personalid = window.sessionStorage.getItem('personalid');
            $.ajax({
                type: "get",
                url: url + "webapi50007.json",
                data: {
                    'personalid': personalid
                },
                datatype: "jsonp",
                success: function (data) {
                    if (typeof data == "string") {
                        data = JSON.parse(data);
                    }
                    if (data.recode == "000000") {
                        self.mainVuer.list = data.rows;
                    } else {
                        alert(data.msg);
                    }
                },
                error: function () {
                    ajaxError();
                }
            });
            self.queryGJJBaseInfo();
            self.queryDKBaseInfo();
        },
        queryGJJBaseInfo: function () {
            //公积金账户基本信息
            var self = this;
            var bodyCardNumber = window.sessionStorage.getItem('bodyCardNumber'),
                centerid = window.sessionStorage.getItem('centerid');
            $.ajax({
                type: "get",
                url: url + "webapi50008.json",
                data: {
                    'bodyCardNumber': bodyCardNumber,
                    'centerId': centerid
                },
                datatype: "jsonp",
                success: function (data) {
                    if (typeof data == "string") {
                        data = JSON.parse(data);
                    }
                    if (data.recode == "000000") {
                        self.gjjInfoVuer.infoList = data.result;
                        data.result.forEach(function (item) {
                            if (item.name == 'peraccstate') {
                                self.mainVuer.state = item.info;
                            }
                            if (item.name == 'balance') {
                                self.mainVuer.balance = item.info;
                            }
                            if (item.name == 'accname') {
                                self.mainVuer.userName = item.info;
                            }
                        });
                    } else {
                        alert(data.msg);
                    }
                },
                error: function () {
                    ajaxError();
                }
            });
        },
        queryGJJViewInfo: function (pageIndex, pagerows, startdate, enddate) {
            //公积金账户基本信息
            var self = this;
            var bodyCardNumber = window.sessionStorage.getItem('bodyCardNumber'),
                centerid = window.sessionStorage.getItem('centerid');
            $.ajax({
                type: "get",
                url: url + "webapi50009.json",
                data: {
                    'bodyCardNumber': bodyCardNumber,
                    'centerId': centerid,
                    'pagenum': pageIndex,
                    'pagerows': pagerows,
                    'startdate': startdate,
                    'enddate': enddate
                },
                datatype: "jsonp",
                success: function (data) {
                    if (typeof data == "string") {
                        data = JSON.parse(data);
                    }
                    if (data.recode == "000000") {
                        self.gjjViewVuer.viewVuer = data.result;
                        self.pager.reset({
                            itemLength: data.totalnum,
                            pageSize: pagerows
                        });
                    } else {
                        alert(data.msg);
                    }
                },
                error: function () {
                    ajaxError();
                }
            });
        },
        queryDKBaseInfo: function () {
            // 贷款基本信息
            var self = this;
            var bodyCardNumber = window.sessionStorage.getItem('bodyCardNumber'),
                centerid = window.sessionStorage.getItem('centerid');
            $.ajax({
                type: "get",
                url: url + "webapi50010.json",
                data: {
                    'bodyCardNumber': bodyCardNumber,
                    'centerId': centerid
                },
                datatype: "jsonp",
                success: function (data) {
                    if (typeof data == "string") {
                        data = JSON.parse(data);
                    }
                    if (data.recode == "000000") {
                        self.dkBaseInfoVuer.info = data.result;
                        data.result.forEach(function (item) {
                            if (item.name == 'loancontrnum') {
                                self.mainVuer.loanNumber = item.info;
                            }
                            if (item.name == 'loansum') {
                                self.mainVuer.loanAmount = item.info;
                            }
                            if (item.name == 'curdaybal') {
                                self.mainVuer.currentBalance = item.info;
                            }
                        });
                        self.mainVuer.noDataMsg = '';
                    } else {
                        //alert(data.msg);
                        self.mainVuer.noDataMsg = data.msg;
                    }
                },
                error: function () {
                    ajaxError();
                }
            });
        },
        queryDKView: function (pageIndex, pagerows, startdate, enddate) {
            // 贷款还款明细
            var self = this;
            var bodyCardNumber = window.sessionStorage.getItem('bodyCardNumber'),
                centerid = window.sessionStorage.getItem('centerid');
            $.ajax({
                type: "get",
                url: url + "webapi50011.json",
                data: {
                    'bodyCardNumber': bodyCardNumber,
                    'centerId': centerid,
                    'pagenum': pageIndex,
                    'pagerows': pagerows,
                    'startdate': startdate,
                    'enddate': enddate
                },
                datatype: "jsonp",
                success: function (data) {
                    if (typeof data == "string") {
                        data = JSON.parse(data);
                    }
                    if (data.recode == "000000") {
                        self.dkViewVuer.view = data.result;
                        self.infoPager.reset({
                            itemLength: data.totalnum,
                            pageSize: pagerows
                        });
                    } else {
                        alert(data.msg);
                    }
                },
                error: function () {
                    ajaxError();
                }
            });
        },
        queryDKPlan: function (pageIndex, pagerows) {
            // 贷款基本信息
            var self = this;
            var bodyCardNumber = window.sessionStorage.getItem('bodyCardNumber'),
                centerid = window.sessionStorage.getItem('centerid');
            var tempDate = new Date(),
                tempMonth = tempDate.getMonth() + 1 < 10 ? ('0' + (tempDate.getMonth() + 1)) : (tempDate.getMonth() + 1),
                tempDay = tempDate.getDate() < 10 ? ('0' + tempDate.getDate()) : tempDate.getDate();
            var nowDate = tempDate.getFullYear() + '-' + tempMonth + '-' + tempDay;
            $.ajax({
                type: "get",
                url: url + "webapi50012.json",
                data: {
                    'bodyCardNumber': bodyCardNumber,
                    'centerId': centerid,
                    'pagenum': pageIndex,
                    'pagerows': pagerows,
                    'startdate': '2016-01-01',
                    'enddate': nowDate
                },
                datatype: "jsonp",
                success: function (data) {
                    if (typeof data == "string") {
                        data = JSON.parse(data);
                    }
                    if (data.recode == "000000") {
                        self.dkPlanVuer.plan = data.result;
                        self.planPager.reset({
                            itemLength: data.totalnum,
                            pageSize: pagerows
                        });
                    } else {
                        alert(data.msg);
                    }
                },
                error: function () {
                    ajaxError();
                }
            });
        }
    };
    if ($(".userview-sub-title-icons3").length > 0) {
        userViewSub3.createVue();
    }

    window.userViewSub4 = {
        pager: null,
        pageSize: 10,
        vuer: null,
        createPage: function () {
            var self = this;
            self.pager = pages({
                el: "#userView4-page",
                itemLength: 0,
                pageSize: 12,
                pageChanged: function (pageIndex, pageSize) {
                    self.planPager.reset({
                        itemLength: data.totalnum,
                        pageSize: pagerows
                    });
                }
            });
        },
        createVue: function () {
            var self = this;
            self.vuer = new Vue({
                'el': '.userview-sub1-view',
                'data': {
                    list: []
                }
            });
            self.getData(1, self.pageSize);
        },
        getData: function () {
            var self = this;
            var personalid = window.sessionStorage.getItem('personalid');
            $.ajax({
                type: "get",
                url: url + "webapi50005.json",
                data: {
                    'personalid': personalid
                },
                datatype: "jsonp",
                success: function (data) {
                    if (typeof data == "string") {
                        data = JSON.parse(data);
                    }
                    if (data.recode == "000000") {
                        self.vuer.list = data.rows;
                    } else {
                        alert(data.msg);
                    }
                },
                error: function () {
                    ajaxError();
                }
            });
        }
    };
    if ($(".userview-sub-title-icons4").length > 0) {
        userViewSub4.createVue();
    }

    window.userViewSub5 = {
        pager: null,
        pageSize: 10,
        listVuer: null,
        tableVuer: null,
        chatVuer: null,
        tempTel: '',
        createPage: function () {
            var self = this;
            self.pager = pages({
                el: "#userView5-page",
                itemLength: 0,
                pageSize: 12,
                pageChanged: function (pageIndex, pageSize) {
                    self.pageSize = pageSize;
                    self.query(pageIndex, self.tempTel);
                }
            });
            self.btnClick();
            self.createVue();
        },
        btnClick: function () {
            var self = this;
            $(".userview-sub5-header a").off().on("click", function () {
                $(this).addClass("on").siblings("a").removeClass("on");
                var _index = $(this).index();
                switch (_index) {
                    case 0:
                        self.query(1, '');
                        $(".userview-sub5-box1").show();
                        $(".userview-sub5-box2").hide();
                        break;
                    case 1:
                        self.getData(1, self.pageSize, '', '');
                        $(".userview-sub5-box1").hide();
                        $(".userview-sub5-box2").show();
                        break;
                }
            });
            $("#userview-sub5-query").off().on("click", function () {
                var tel = $("#tel-number").val();
                self.query(1, tel);
            });
        },
        createVue: function () {
            var self = this;
            self.listVuer = new Vue({
                el: '.userview-sub5-box2',
                data: {
                    lists: [],
                    select: '',
                    contents: [],
                    imgUrl: url
                },
                methods: {
                    getHistory: function (id) {
                        this.select = id;
                        self.queryHistory(id);
                    }
                }
            });
            self.tableVuer = new Vue({
                el: '.userview-sub1-view',
                data: {
                    knowlist: [],
                    phonelist: [],
                    currentView: '',
                    tel: '',
                    callTime: '',
                    pickUp: ''
                },
                methods: {
                    showView: function (_index, item) {
                        var that = this;
                        self.viewVuer.currentView = that.knowlist[_index];
                        self.viewVuer.tel = item['电话号码'];
                        self.viewVuer.callTime = item['呼入时间'];
                        self.viewVuer.pickUp = item['接听分机'];
                        $(".userview-sub5-view").show();
                        $(".userview-sub5-header,.userview-sub5-box1").hide();
                        $(".userview-sub5-box2").hide();
                        $(".userview-sub-title a").off().on('click', function (event) {
                            event.preventDefault();
                            $(".userview-sub5-header,.userview-sub5-box1").show();
                            $(".userview-sub5-view").hide();
                            $(this).off("click");
                        });
                    }
                }
            });
            self.viewVuer = new Vue({
                el: '.userview-sub5-view',
                data: {
                    currentView: '',
                    tel: '',
                    callTime: '',
                    pickUp: ''
                }
            });
        },
        getData: function (pageIndex, pageSize, startDate, endDate) {
            var self = this;
            var personalid = window.sessionStorage.getItem('personalid'),
                centerid = window.sessionStorage.getItem('centerid');
            if(personalid == '' && centerid == '') return;
            $.ajax({
                type: "get",
                url: url + "webapi50016.json",
                data: {
                    'personalid': personalid,
                    'centerId': centerid,
                    'page': pageIndex,
                    'rows': pageSize,
                    'startdate': startDate,
                    'enddate': endDate
                },
                datatype: "jsonp",
                success: function (data) {
                    if (typeof data == "string") {
                        data = JSON.parse(data);
                    }
                    if (data.recode == "000000") {
                        self.listVuer.lists = data.result.datas;
                        self.listVuer.select = data.result.datas[0].chat_id;
                        self.queryHistory(data.result.datas[0].chat_id);
                    } else {
                        alert(data.msg);
                    }
                },
                error: function () {
                    ajaxError();
                }
            });
        },
        queryHistory: function (id) {
            var self = this;
            var personalid = window.sessionStorage.getItem('personalid'),
                centerid = window.sessionStorage.getItem('centerid');
            if(personalid == '' && centerid == '') return;
            $.ajax({
                type: "get",
                url: url + "webapi50017.json",
                data: {
                    'personalid': personalid,
                    'centerId': centerid,
                    'chat_id': id,
                    'page': 1,
                    'rows': 999,
                    'startdate': '',
                    'enddate': ''
                },
                datatype: "jsonp",
                success: function (data) {
                    if (typeof data == "string") {
                        data = JSON.parse(data);
                    }
                    if (data.recode == "000000") {
                        self.listVuer.contents = data.result.datas;
                    } else {
                        alert(data.msg);
                    }
                },
                error: function () {
                    ajaxError();
                }
            });
        },
        query: function (pageIndex, phone) {
            var self = this;
            var tel = '';
            if (phone.length > 0) {
                tel = phone;
            } else {
                tel = window.sessionStorage.getItem('mainTel');
            }
            $.ajax({
                type: "get",
                url: url + "webapi50013.json",
                data: {
                    'tel': tel,
                    'page': pageIndex,
                    'rows': self.pageSize
                },
                datatype: "jsonp",
                success: function (data) {
                    if (typeof data == "string") {
                        data = JSON.parse(data);
                    }
                    if (data.result == null) return;
                    if (data.result.recode == "000000") {
                        var tempList = [],
                            numb = 0;
                        data.result.phonelist.forEach(function (item) {
                            if(item['通话时长'] != '0') {
                                item.smallIndex = numb;
                                tempList.push(item);
                                numb++;
                            } else {
                                item.smallIndex = "-1";
                                tempList.push(item);
                            }
                        });
                        self.tableVuer.knowlist = data.result.knowlist;
                        self.tableVuer.phonelist = tempList;
                        self.tableVuer.tel = tel;
                        self.pager.reset({
                            itemLength: data.result.icount
                        });
                    } else {
                        alert(data.result.msg);
                    }
                },
                error: function () {
                    ajaxError();
                }
            });
        }
    };
    if ($(".userview-sub-title-icons5").length > 0) {
        userViewSub5.createPage();
        var _url = window.location.href;
        if (_url.split("?").length > 1) {
            $(".userview-sub5-header a").eq(1).trigger("click");
        } else {
            $(".userview-sub5-header a").eq(0).trigger("click");
        }
    }

    window.userViewSub6 = {
        stepVuer1: null,
        stepVuer2: null,
        btnClick: function () {
            var self = this;
            $(".userview-sub6-header a").off().on("click", function () {
                $(this).addClass("on").siblings("a").removeClass("on");
                var _index = $(this).index();
                switch (_index) {
                    case 0:
                        self.getData();
                        $(".userview-sub6-box1").show();
                        $(".userview-sub6-box2").hide();
                        break;
                    case 1:
                        self.getDKData();
                        $(".userview-sub6-box1").hide();
                        $(".userview-sub6-box2").show();
                        break;
                }
            });
            self.createVue();
        },
        createVue: function () {
            var self = this;
            self.stepVuer1 = new Vue({
                'el': '.userview-sub6-box1',
                'data': {
                    step: [],
                    select: '',
                    selectValue: ''
                }
            });
            self.stepVuer2 = new Vue({
                'el': '.userview-sub6-box2',
                'data': {
                    step: [],
                    select: '',
                    selectValue: ''
                }
            });
        },
        getData: function () {
            var self = this;
            var bodyCardNumber = window.sessionStorage.getItem('bodyCardNumber'),
                centerid = window.sessionStorage.getItem('centerid');
            var tempDate = new Date(),
                tempMonth = tempDate.getMonth() + 1 < 10 ? ('0' + (tempDate.getMonth() + 1)) : (tempDate.getMonth() + 1),
                tempDay = tempDate.getDate() < 10 ? ('0' + tempDate.getDate()) : tempDate.getDate();
            var nowDate = tempDate.getFullYear() + '-' + tempMonth + '-' + tempDay;
            if(centerid == '' || bodyCardNumber == '') return;
            $.ajax({
                type: "get",
                url: url + "webapi50014.json",
                data: {
                    'bodyCardNumber': bodyCardNumber,
                    'centerId': centerid,
                    'startdate': '2016-01-01',
                    'enddate': nowDate
                },
                datatype: "jsonp",
                success: function (data) {
                    if (typeof data == "string") {
                        data = JSON.parse(data);
                    }
                    if (data.recode == "000000") {
                        self.stepVuer.step = data.result;
                        self.stepVuer.select = data.apprflag;
                        self.stepVuer.selectValue = data.apprmsg;
                    } else {
                        alert(data.msg);
                    }
                },
                error: function () {
                    ajaxError();
                }
            });
        },
        getDKData: function () {
            var self = this;
            var bodyCardNumber = window.sessionStorage.getItem('bodyCardNumber'),
                centerid = window.sessionStorage.getItem('centerid');
            var tempDate = new Date(),
                tempMonth = tempDate.getMonth() + 1 < 10 ? ('0' + (tempDate.getMonth() + 1)) : (tempDate.getMonth() + 1),
                tempDay = tempDate.getDate() < 10 ? ('0' + tempDate.getDate()) : tempDate.getDate();
            var nowDate = tempDate.getFullYear() + '-' + tempMonth + '-' + tempDay;
            if(centerid == '' || bodyCardNumber == '') return;
            $.ajax({
                type: "get",
                url: url + "webapi50015.json",
                data: {
                    'bodyCardNumber': bodyCardNumber,
                    'centerId': centerid,
                    'startdate': '2016-01-01',
                    'enddate': nowDate
                },
                datatype: "jsonp",
                success: function (data) {
                    if (typeof data == "string") {
                        data = JSON.parse(data);
                    }
                    if (data.recode == "000000") {
                        self.stepVuer2.step = data.result;
                        self.stepVuer2.select = data.apprflag;
                        self.stepVuer2.selectValue = data.apprmsg;
                    } else {
                        alert(data.msg);
                    }
                },
                error: function () {
                    ajaxError();
                }
            });
        }
    };
    if ($(".userview-sub-title-icons6").length > 0) {
        userViewSub6.btnClick();
        var _url = window.location.href;
        if (_url.split("?").length > 1) {
            $(".userview-sub6-header a").eq(1).trigger("click");
        } else {
            $(".userview-sub6-header a").eq(0).trigger("click");
        }
    }
    window.userViewSub7 = {
        pager1: null,
        pager2: null,
        pager3: null,
        pageSize: 10,
        vuer: null,
        btnClick: function () {
            var self = this;
            $(".userview-sub7-header a").off().on("click", function () {
                $(this).addClass("on").siblings("a").removeClass("on");
                var _index = $(this).index();
                switch (_index) {
                    case 0:
                        self.getData(1, self.pageSize, '01');
                        $(".userview-sub7-box1").show();
                        $(".userview-sub7-box2").hide();
                        $(".userview-sub7-box3").hide();
                        break;
                    case 1:
                        self.getData(1, self.pageSize, '04');
                        break;
                    case 2:
                        self.getData(1, self.pageSize, '08');
                        break;
                }
            });
            self.createPager();
            self.createVuer();
            self.getData(1, self.pageSize, '01');
        },
        createPager: function () {
            var self = this;
            // create pages
            self.pager1 = pages({
                el: "#userView7-page1",
                itemLength: 0,
                pageSize: 12,
                pageChanged: function (pageIndex, pageSize) {
                    self.pageSize = pageSize;
                    self.getData(pageIndex, pageSize);
                }
            });
            self.pager2 = pages({
                el: "#userView7-page2",
                itemLength: 0,
                pageSize: 12,
                pageChanged: function (pageIndex) {
                }
            });
            self.pager3 = pages({
                el: "#userView7-page3",
                itemLength: 0,
                pageSize: 12,
                pageChanged: function (pageIndex) {
                }
            });
        },
        createVuer: function () {
            var self = this;
            self.vuer = new Vue({
                'el': '.userview-sub1-view',
                'data': {
                    list: [],
                    states: {
                        '01': '已预约',
                        '04': '已办结',
                        '08': '已撤销'
                    }
                }
            });
        },
        getData: function (pageIndex, pageSize, appostate) {
            var self = this;
            var personalid = window.sessionStorage.getItem('personalid'),
                centerid = window.sessionStorage.getItem('centerid');
            if(personalid == '' || centerid == '') return;
            $.ajax({
                type: "get",
                url: url + "webapi50018.json",
                data: {
                    'personalid': personalid,
                    'centerid': centerid,
                    'appostate': appostate,
                    'page': pageIndex,
                    'rows': pageSize
                },
                datatype: "jsonp",
                success: function (data) {
                    if (typeof data == "string") {
                        data = JSON.parse(data);
                    }
                    if (data.recode == "000000") {
                        self.vuer.list = data.rows;
                        self.pager1.reset({
                            itemLength: data.total,
                            pageSize: self.pageSize
                        });
                    } else {
                        alert(data.msg);
                    }
                },
                error: function () {
                    ajaxError();
                }
            });
        },
    };
    if ($(".userview-sub-title-icons7").length > 0) {
        userViewSub7.btnClick();
    }
}
/**
 * Created by FelixAn on 2016/8/11.
 */
var messageTemplate = {
    pager: null,
    createPager: function () {
        var self = this;
        // create pages
        self.pager = pages({
            el: "#serMgmt-messageTemplate-templateTable-pages",
            itemLength: 2300,
            pageSize: 12,
            pageChanged: function (pageIndex) {
                //self.getTableData(pageIndex, false);
            }
        });
    },
    createMsgTempTable: function (data) {
        var self = this;
        data = {"pageSize":15,"pageNumber":1,"recode":"000000","rows":[{"templateId":"1","channel":"10,20","centerid":"00063100","templateCode":"1001","templateContent":"尊敬的用户，您的账号{grzh}，账户余额发生变化。账户类型：{zhlx}，业务日期：{fsrq}，业务类型：{ywlx}，发生额：{fse}，当前余额：{grzhyw}，备注：如有问题请与12329联系！","templateName":"结息对账单","batchkeynum":"2","batchsplit":"\\t","batchstartnum":"1","validflag":"1","datemodified":"2016-08-11 17:15:14.974","datecreated":null,"freeuse1":"GJ0005,GJ0006,GJ0001,WTGJ01,GJ0002,WTGJ02,TQ0001,TQ0002,TQ0003,TQ0004,TQ0005,TQ0006,TQ0007,WTTQ01,WTTQ02,TQ0008","freeuse2":null,"freeuse3":null,"freeuse4":null},{"templateId":"10","channel":"10,20","centerid":"00063100","templateCode":"1017","templateContent":"尊敬{grxm}，您的公积金贷款本月扣款成功。提示类别：{ywlx}，贷款账号：{dkzh}，还款金额：{fse}，业务时间：{ywfsrq}如有问题请与12329联系！","templateName":"代扣成功","batchkeynum":"2","batchsplit":"\\t","batchstartnum":"1","validflag":"1","datemodified":null,"datecreated":"2016-05-30 11:09:33.170","freeuse1":"DK0010,DK0013","freeuse2":null,"freeuse3":null,"freeuse4":null},{"templateId":"11","channel":"10,20","centerid":"00063100","templateCode":"10181","templateContent":"尊敬的用户，您手机信息发生变动。消息类型：{ywlx},时间：{ywdate}，公积金账号：{zh}，如有问题请与12329联系！","templateName":"手机绑定","batchkeynum":"5","batchsplit":"\\t","batchstartnum":"1","validflag":"1","datemodified":"2016-05-31 14:36:37.525","datecreated":null,"freeuse1":"GJ0007,GJ0008,GJ0009","freeuse2":null,"freeuse3":null,"freeuse4":null},{"templateId":"12","channel":"10,20","centerid":"00063100","templateCode":"10182","templateContent":"尊敬的用户，您签约信息发生变动。\n消息类型：{ywlx}，时间：{bhrq}，如有问题请与12329联系！","templateName":"短信签约(柜面)","batchkeynum":"2","batchsplit":"\\t","batchstartnum":"1","validflag":"1","datemodified":null,"datecreated":"2016-05-30 11:23:59.511","freeuse1":"GJ0013","freeuse2":null,"freeuse3":null,"freeuse4":null},{"templateId":"13","channel":"10,20","centerid":"00063100","templateCode":"1019","templateContent":"尊敬的用户，您银行卡绑定信息发生变动。\n消息类型：{ywlx}，时间：{bhrq}，单位名称：{jcdwmc}，如有问题请与12329联系！","templateName":"卡绑定","batchkeynum":"2","batchsplit":"\\t","batchstartnum":"1","validflag":"1","datemodified":null,"datecreated":"2016-05-30 11:32:46.747","freeuse1":"GJ0010,GJ0011,GJ0012","freeuse2":null,"freeuse3":null,"freeuse4":null},{"templateId":"14","channel":"10,20","centerid":"00063100","templateCode":"1020","templateContent":"尊敬的{grxm}，您的公积金贷款将于本月20日后进行扣款。提示类别：{ywlx}，贷款账号：{dkzh}，请确保账户金额充足，采用委托还款的用户可忽略此信息。","templateName":"还款提醒短信","batchkeynum":"2","batchsplit":"\\t","batchstartnum":"1","validflag":"1","datemodified":"2016-05-31 14:48:04.106","datecreated":null,"freeuse1":"DK0011","freeuse2":null,"freeuse3":null,"freeuse4":null},{"templateId":"15","channel":"10,20","centerid":"00063100","templateCode":"1021","templateContent":"尊敬的用户：{username}。消息类型：{ywlx}，时间：{date1}，奖品描述：恭喜您中得{jpmx}，我们将在{date2}前进行派奖，感谢您对公积金微信的关注，如有问题请与12329联系！","templateName":"中奖通知","batchkeynum":"1","batchsplit":"\\t","batchstartnum":"1","validflag":"1","datemodified":null,"datecreated":"2016-07-04 17:20:20.119","freeuse1":"ZJ9999","freeuse2":null,"freeuse3":null,"freeuse4":null},{"templateId":"16","channel":"10,20","centerid":"00000001","templateCode":"test","templateContent":"test{heh2e}","templateName":"test","batchkeynum":"test","batchsplit":"\\t","batchstartnum":"test","validflag":"1","datemodified":"2016-08-11 17:28:25.179","datecreated":null,"freeuse1":"test","freeuse2":null,"freeuse3":null,"freeuse4":null},{"templateId":"2","channel":"10,20","centerid":"00063100","templateCode":"1005","templateContent":"尊敬的用户，公积金账号{grzh}，您账户信息发生变动。\n消息类型：{ywlx}，时间：{bhrq}，账户余额：{grhyw}，单位名称：{jcdwmc}，如有问题请与12329联系！","templateName":"职工封存","batchkeynum":"2","batchsplit":"\\t","batchstartnum":"1","validflag":"1","datemodified":null,"datecreated":"2016-05-30 09:44:08.065","freeuse1":"GJ0003,WTGJ03,GJ0004,WTGJ04","freeuse2":null,"freeuse3":null,"freeuse4":null},{"templateId":"3","channel":"10,20","centerid":"00063100","templateCode":"1007","templateContent":"尊敬的用户，您的公积金贷款余额变动！职工姓名：{grxm}，归还本金：{bjje}，归还利息：{lxje}，罚息：{fxje}，贷款余额：{sydke}，业务时间：{ywfsrq}，如有问题请与12329联系！","templateName":"临柜还款(正常)","batchkeynum":"2","batchsplit":"\\t","batchstartnum":"1","validflag":"1","datemodified":null,"datecreated":"2016-05-30 10:04:12.575","freeuse1":"DK0001,WTDK01,DK0002,WTDK02,DK0003,WTDK03,DK0004,WTDK04","freeuse2":null,"freeuse3":null,"freeuse4":null},{"templateId":"4","channel":"10,20","centerid":"00063100","templateCode":"1011","templateContent":"尊敬的{grxm}，您的公积金贷款可以办理合同签订，提示类别：{ywlx}，贷款账号：{dkzh}，请2个工作日后，携带({grzfdkzhltsxx})及相关费用来办理。","templateName":"贷款合同签订","batchkeynum":"2","batchsplit":"\\t","batchstartnum":"1","validflag":"1","datemodified":"2016-05-30 10:27:08.527","datecreated":"2016-05-30 09:35:08.065","freeuse1":"DK0005","freeuse2":null,"freeuse3":null,"freeuse4":null},{"templateId":"5","channel":"10,20","centerid":"00063100","templateCode":"1012","templateContent":"尊敬的{grxm}，您的公积金贷款已发放。提示类别：{ywlx}，贷款账号：{dkzh}，请2个工作日后，携本人身份证来柜台领取合同。","templateName":"贷款发放","batchkeynum":"2","batchsplit":"\\t","batchstartnum":"1","validflag":"1","datemodified":null,"datecreated":"2016-05-30 10:24:38.793","freeuse1":"DK0012","freeuse2":null,"freeuse3":null,"freeuse4":null},{"templateId":"6","channel":"10,20","centerid":"00063100","templateCode":"1013","templateContent":"尊敬的{grxm}，您的公积金贷款可办理抵押手续。提示类别：{ywlx}，贷款账号：{dkzh}，请2个工作日后，抵押人携本人身份证来柜台领取合同后到房管部门办理。","templateName":"担保办理","batchkeynum":"2","batchsplit":"\\t","batchstartnum":"1","validflag":"1","datemodified":"2016-05-31 14:36:27.665","datecreated":null,"freeuse1":"DK0006","freeuse2":null,"freeuse3":null,"freeuse4":null},{"templateId":"7","channel":"10,20","centerid":"00063100","templateCode":"1014","templateContent":"尊敬{grxm}，您的委托逐月提取还贷业务已暂停。提示类别：{ywlx}，贷款账号：{dkzh}，还款金额：{fse}，业务时间：{ywfsrq}，如有问题请与12329联系！","templateName":"委托提取还贷到期","batchkeynum":"2","batchsplit":"\\t","batchstartnum":"1","validflag":"1","datemodified":null,"datecreated":"2016-05-30 10:51:02.865","freeuse1":"DK0007","freeuse2":null,"freeuse3":null,"freeuse4":null},{"templateId":"8","channel":"10,20","centerid":"00063100","templateCode":"1015","templateContent":"尊敬{grxm}，您的委托逐月提取还贷业务已恢复。提示类别：{ywlx}，贷款账号：{dkzh}，还款金额：{fse}，业务时间：{ywfsrq}，如有问题请与12329联系！","templateName":"委托提取还贷恢复","batchkeynum":"2","batchsplit":"\\t","batchstartnum":"1","validflag":"1","datemodified":null,"datecreated":"2016-05-30 10:55:52.627","freeuse1":"DK0008","freeuse2":null,"freeuse3":null,"freeuse4":null}],"total":16};
        var cols = [
            { title:'微信模板号', name:'templateCode', width:'236', align: 'center' },
            { title:'模板名称', name:'templateName', width:'292', align: 'center'},
            { title:'渠道名称', name:'channel', width:'326', align: 'center', renderer: function () {
                return "APP，微信";
            }},
            { title:'操作', name:'centerid', width:'166', align: 'center', renderer: function (val, item) {
                return '<a href="javascript:;" title="编辑" onclick=\'messageTemplate.edit('+ JSON.stringify(item) +');\'>编辑</a>';
            }}
        ];
        $('#serMgmt-messageTemplate-templateTable').mmGrid({
            multiSelect: true,
            checkCol: true,
            height: '420px',
            cols: cols,
            items: data.rows,
            loadingText: "loading...",
            noDataText: "暂无数据。",
            loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
            sortable: false
        });
        self.btnClick();
    },
    edit: function (item) {
        // page jump
        var self = this;
        $(".messageTemplate-add-and-edit").show();
        $(".serMgmt-messageTemplate-box").hide();
        $(".messageTemplate-add-and-edit-title").eq(0).find("span").text("编辑信息模板");
        self.createInfoTable();
    },
    createInfoTable: function (data) {
        var self = this;
        data = {"recode":"000000","rows":[{"templateDetailId":"77","templateId":"1","templateDetailName":"grzh","serialNumber":"3","templateDetailRemark":"个人账户","validflag":"1","datemodified":"2016-05-31 09:10:44.648","datecreated":"2016-05-31 09:08:54.015","freeuse1":null,"freeuse2":null,"freeuse3":null,"freeuse4":0},{"templateDetailId":"78","templateId":"1","templateDetailName":"zhlx","serialNumber":"18","templateDetailRemark":"账户类型","validflag":"1","datemodified":"2016-05-31 09:11:11.760","datecreated":"2016-05-31 09:08:54.475","freeuse1":null,"freeuse2":null,"freeuse3":null,"freeuse4":1},{"templateDetailId":"79","templateId":"1","templateDetailName":"fsrq","serialNumber":"5","templateDetailRemark":"发生日期","validflag":"1","datemodified":"2016-05-31 09:11:43.710","datecreated":"2016-05-31 09:08:54.894","freeuse1":null,"freeuse2":null,"freeuse3":null,"freeuse4":2},{"templateDetailId":"80","templateId":"1","templateDetailName":"ywlx","serialNumber":"17","templateDetailRemark":"业务类型","validflag":"1","datemodified":"2016-05-31 09:16:29.184","datecreated":"2016-05-31 09:08:54.995","freeuse1":null,"freeuse2":null,"freeuse3":null,"freeuse4":3},{"templateDetailId":"81","templateId":"1","templateDetailName":"fse","serialNumber":"7","templateDetailRemark":"发生额","validflag":"1","datemodified":"2016-05-31 09:12:09.438","datecreated":"2016-05-31 09:08:55.157","freeuse1":null,"freeuse2":null,"freeuse3":null,"freeuse4":4},{"templateDetailId":"82","templateId":"1","templateDetailName":"grzhyw","serialNumber":"9","templateDetailRemark":"个人账户余额","validflag":"1","datemodified":"2016-05-31 09:12:29.406","datecreated":"2016-05-31 09:08:55.371","freeuse1":null,"freeuse2":null,"freeuse3":null,"freeuse4":5}]};
        var cols = [
            { title:'要素序号', name:'serialNumber', width:'186', align: 'center' },
            { title:'要素占位符', name:'templateDetailName', width:'152', align: 'center'},
            { title:'要素名称', name:'templateDetailRemark', width:'226', align: 'center'},
            { title:'创建时间', name:'datecreated', width:'292', align: 'center'},
            { title:'操作', name:'templateId', width:'166', align: 'center', renderer: function (val, item) {
                return '<a href="javascript:;" title="编辑" onclick=\'messageTemplate.editInfo('+ JSON.stringify(item) +');\'>编辑</a>';
            }}
        ];
        var infoListTable = $('#messageTemplate-infoList').mmGrid({
            multiSelect: false,
            checkCol: false,
            height: '220px',
            cols: cols,
            items: data.rows,
            loadingText: "loading...",
            noDataText: "暂无数据。",
            loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
            sortable: false
        });
        self.btnClick(infoListTable);
    },
    editInfo: function (item) {
        var editHTML = $(".messageTemplate-edit").html();
        parent.Common.popupShow(editHTML);
    },
    btnClick:function (table) {
        var self = this,
            table = table;
        $("#generatingElements").on("click", function () {
            parent.Common.dialog({
                type: "tips",
                text: "生成要素会覆盖之前的，确定生成吗？",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    //parent.Common.loading(true);
                }
            });
        });
        $("#messageTemplate-save, #messageTemplate-back").on("click", function () {
            $(".messageTemplate-add-and-edit").hide();
            $(".serMgmt-messageTemplate-box").show();
        });
        $("#sysMgmt-btn-add").on("click", function () {
            $(".messageTemplate-add-and-edit-title").eq(0).find("span").text("添加信息模板");
            $(".messageTemplate-add-and-edit"). show();
            $(".serMgmt-messageTemplate-box").hide();
            self.createInfoTable();
        });
        $("#sysMgmt-btn-del").on("click", function () {
            parent.Common.dialog({
                type: "tips",
                text: "是否确认删除？",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    //parent.Common.loading(true);
                }
            });
        });
    },
    createDroplist: function () {
        var topOrgDroplist = droplist({
            el: "#opera-messageTemplate-droplist",
            data: ["移动互联应用服务管理平台测试服务器", "昆明市住房公积金管理中心", "深圳市住房公积金管理中心", "哈尔滨住房公积金管理中心农垦分中心", "济南住房公积金管理中心", "威海市住房公积金管理中心"]
        });
    }
};
$(document).ready(function(){
    if($("#serMgmt-messageTemplate-templateTable").length > 0) {
        messageTemplate.createPager();
        messageTemplate.createMsgTempTable();
        messageTemplate.createDroplist();
    }
});
/**
 * Created by M on 2016/8/24.
 * ModifyDate: 2016/9/10
 */
var channelInterface = {
    pageSize: 10,
    pager: null,
    spager:null,
    tabler:null,
    stabler: null,
    spageSize: 10,
    createPager: function () {
        var self = this;
        // create pages
        self.pager = pages({
            el: "#orgManage-table-pages",
            itemLength: 2300,
            pageSize: 10,
            pageChanged: function (pageIndex, pageSize) {
                self.pageSize = pageSize;
                self.getTableData(pageIndex, pageSize, false);
            }
        });
        self.getTableData( 1, self.pageSize, true);
    },
    getTableData: function (page, rows, pageRestBool) {
        var self = this,
        apiname = $("#txtapiname").val(),
        page = page,
        rows = rows;
        $.ajax({
            type:'POST',
            url:'./webapi05004.json',
            data: { 'apiname': apiname, 'page': page, 'rows': rows },
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.createTable(data.rows);
                    self.pager.reset({
                        itemLength: data.total,
                        pageSize: self.pageSize,
                        reset: pageRestBool
                    });
                } else {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "error",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {}
                    });
                }
            }, 
            error:function(){
               parent.Common.ajaxError();
            }
                        
        });
    }, 
    createTable: function (data) {
        var self = this;
        var cols = [
           { title:'序号', name:'funcid' ,width:30, align:'center', renderer: function (val, item, index) {
                return index+1;
            }},
           { title:'接口名称', name:'apiname' ,width:150, align:'center' },
           { title:'接口描述', name:'apimsg' ,width:120, align:'center' },
           { title:'接口类型', name:'apitype' ,width:70, align:'center' , renderer: function (val, item, index) {
                return $('#buiType option[value='+val+']').text();
            }},
           { title:'是否认证', name:'islogin' ,width:70, align:'center', renderer: function (val, item, index) {
                if(item.islogin == '1'){
                    return '是';
                }
                if(item.islogin == '0'){
                    return '否';
                }
            }},
           { title:'URL', name:'url' ,width:120, align:'center'},
           { title:'HTTP请求方式', name:'reqtype' ,width:100, align:'center'},
           { title:'请求参数', name:'reqparam' ,width:220, align:'center',renderer: function (val, item, index) {
                
                if (val != null && val != '' && val.length > 20) {
                    var temp = "";
                    temp += '<span class="orgManage-table-links">';
                    temp += "<a href='javascript:;' onclick='showParam(" + JSON.stringify(item) + ",1);' title='点击查看更多'>"+val.substring(0,18)+"...</a></span>";
                    return temp;
                }else{
                    return val;
                }
                
            }},
           { title:'返回结果', name:'repparam' ,width:220, align:'center',renderer: function (val, item, index) {
                var temp = "";
                if (val != null && val != '' && val.length > 20) {
                    temp += '<span class="orgManage-table-links">';
                    temp += "<a href='javascript:;' onclick='showParam(" + JSON.stringify(item) + ",0);' title='点击查看更多'>"+val.substring(0,18)+"...</a></span>";
                } 
                return temp;
            }},
           { title:'备注', name:'remarks' ,width:70, align:'center'},
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#channelSetTable').mmGrid({
                    multiSelect: true,// 多选
                    checkCol: true, // 选框列
                    height: '390',
                    cols: cols,
                    items: data,
                    loadingText: "loading...",
                    noDataText: "暂无数据。",
                    loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                    sortable: false
                });
        }
        parent.Common.loading(false);
    },
    btnClick: function () {
        var self = this,
            table = self.tabler,
            apiid = '';
        // 依赖服务查询
        $('#btnServiceSearch').off().on("click", function () {
            
            var selectedData = self.tabler.selectedRows();
            if(selectedData.length != 1) {
                parent.Common.dialog({
                    type: "error",
                    text: "请选择一条信息！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return;
            }else{

                var apiid  = selectedData[0].apiid;

                if(self.spager == null) {
                    self.createServerPager(apiid);
                }
                
                self.showServer(apiid, 1, self.spageSize, true, function () {
                    $('#mainBox').hide();
                    $("#serverBox").show();
                });
               

            }
            
        });
        // 新建
        $('#orgManage-btn-add').off().on("click", function () {
            var createHTML = $(".orgManage-popup-edit-container").html();
            parent.Common.popupShow(createHTML);
        });
        //删除
        $("#orgManage-btn-del").off().on("click", function () {
            var selectedData = self.tabler.selectedRows(),
                delIds = [];
            if(selectedData.length < 1) {
                parent.Common.dialog({
                    type: "error",
                    text: "请至少选择一条信息！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                parent.Common.delNone();
                return;
            }
            for(var i=0; i<selectedData.length; i++) {
                delIds.push(selectedData[i].apiid);
            }
            parent.Common.dialog({
                type: "tips",
                text: "是否确认删除？",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    self.del(delIds.join(","));
                }
            });
        });
        // 修改
        $('#orgManage-btn-modify').click(function(e){
            var selectedData = self.tabler.selectedRows();

            if(selectedData.length < 1) {
                parent.Common.dialog({
                    type: "error",
                    text: "请至少选择一条记录进行修改！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });
            } 
            if(selectedData.length>1){
                parent.Common.dialog({
                    type: "error",
                    text: "只能选择一条记录进行修改！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });
            }
            if(selectedData.length == 1) {

                var createHTML2 = $(".orgManage-popup-edit-container1").html();
                parent.Common.popupShow(createHTML2);
                

                parent.$("#buiCode1").val(selectedData[0].apiname);
                parent.$("#buiMsg1").val(selectedData[0].apimsg);
                parent.$('#buiType1 option[value="'+selectedData[0].apitype+'"]').attr('selected',true);
                if(selectedData[0].islogin == '1'){
                    parent.$('.radioWrap').find('input[name="ilogin1"]:first').prop('checked','checked');
                    parent.$('.radioWrap').find('input[name="ilogin1"]:last').prop('checked',false);
                } else {
                    parent.$('.radioWrap').find('input[name="ilogin1"]:first').prop('checked',false);
                    parent.$('.radioWrap').find('input[name="ilogin1"]:last').prop('checked','checked');
                }
                parent.$("#buiName1").val(selectedData[0].url);

                // parent.$("#buiDeve1").val(selectedData[0].reqtype);
                if(selectedData[0].reqtype == 'POST'){
                    parent.$('.radioWrap').find('input[name="buiDeve1"]:first').prop('checked','checked');
                    parent.$('.radioWrap').find('input[name="buiDeve1"]:last').prop('checked',false);
                } else {
                    parent.$('.radioWrap').find('input[name="buiDeve1"]:first').prop('checked',false);
                    parent.$('.radioWrap').find('input[name="buiDeve1"]:last').prop('checked','checked');
                }
                parent.$("#buiAddr1").val(selectedData[0].reqparam);

                parent.$("#buiParam1").val(selectedData[0].repparam);
                parent.$("#buiContacter1").val(selectedData[0].remarks);
                
            }
            
        });
        // 查询
        $('#btnQuery').off().on("click", function () {
            self.getTableData(1, self.pageSize, true);
        });
        // 导出
        $('#btnExport').off().on("click" , function() {
            var createHTML = $(".codeTable-export").html();
            parent.Common.popupShow(createHTML);
            
        });

        // 回退
        $('#orgManage-info-goBack').off().on("click" , function(){
            $('#mainBox').show();
            $("#serverBox").hide();
        });
    },
    exportExl:function () {
        $('#exportForm').form('submit',{
            url: './mi050ToExcel.do',
            onSubmit: function(param){
                var fileName = parent.$("#fileName").val();
                if(fileName == null || fileName == ""){
                    parent.$("#popup-container").hide();
                    parent.Common.dialog({
                        type: "error",
                        text: '请填写生成文件名称后进行提交',
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            parent.$("#popup-container").show();
                        }
                    });
                    return false;
                }else{
                    if(fileName.indexOf(".xls") > -1){
                        var filenameArr = fileName.split(".");
                        param.fileName=filenameArr[0]+".xls";
                    }else{
                        param.fileName=fileName+".xls";
                    }

                    param.titlesName = encodeURIComponent(parent.$("#export-title-Name").val());
                }
            },
            success: function(data){
                parent.$("#popup-container").hide();
            },
            error :function(){
                parent.Common.ajaxError();
            }
        });
        parent.Common.popupClose();
    },
    add: function () {
        
        var self = this;
        var apiname = parent.$('#buiCode').val(),
            apimsg = parent.$('#buiMsg').val(),
            apitype = parent.$('#buiType').val(),
            islogin = parent.$('.radioWrap').find('input[name="ilogin"]:checked').val(),
            url = parent.$('#buiName').val(),
            // reqtype = parent.$('#buiDeve').val(),
            reqtype = parent.$('.radioWrap').find('input[name="buiDeve"]:checked').val(),
            reqparam = parent.$('#buiAddr').val(),
            repparam = parent.$('#buiParam').val(),
            remarks = parent.$('#buiContacter').val();   
        // console.log('1:'+apiname+';2:'+islogin+';3:'+url+';4:'+reqtype+';5:'+reqparam+';6:'+repparam+';7:'+remarks)
        if(apiname.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "接口名称不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(apimsg.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "接口描述不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(url.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "URL不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        parent.Common.loading(true);
        parent.Common.popupClose();
        $.ajax({
            type:'POST',
            url:'./webapi05001.json',
            data: { 'apiname': apiname,'apimsg':apimsg,'apitype':apitype, 'islogin': islogin, 'url': url ,'reqtype': reqtype, 'reqparam': reqparam, 'repparam': repparam, 'remarks': remarks},
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData(1, self.pageSize, true);
                } else {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "error",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {}
                    });
                }
            }, 
            error:function(){
               parent.Common.ajaxError();
            }
                        
        });
    },
    del:function(ids){
       parent.Common.loading(true);
        var self = this;
            
        $.ajax({
            type: "POST",
            url: "./webapi05002.json",
            data: {
                "apiid": ids
            },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData(1,self.pageSize,true);
                } else {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "error",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {}
                    });
                }
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    edit: function () {
        parent.Common.loading(true);
        var self = this,
            selectedData = self.tabler.selectedRows();
         var apiid =  selectedData[0].apiid,
            apiname = parent.$('#buiCode1').val(),
            apimsg = parent.$('#buiMsg1').val(),
            apitype = parent.$('#buiType1').val(),
            islogin = parent.$('.radioWrap').find('input[name="ilogin1"]:checked').val(),
            url = parent.$('#buiName1').val(),
            // reqtype = parent.$('#buiDeve1').val(),
            reqtype = parent.$('.radioWrap').find('input[name="buiDeve1"]:checked').val(),
            reqparam = parent.$('#buiAddr1').val(),
            repparam = parent.$('#buiParam1').val(),
            remarks = parent.$('#buiContacter1').val();   
        parent.Common.popupClose();
        $.ajax({
            type: "POST",
            url: "./webapi05003.json",
            datatype: "json",
            data: { 'apiid':apiid,'apiname': apiname,'apimsg':apimsg,'apitype':apitype, 'islogin': islogin, 'url': url ,'reqtype': reqtype, 'reqparam': reqparam, 'repparam': repparam, 'remarks': remarks},
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData(1, self.pageSize, true);
                } else {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "error",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                        }
                    });
                }
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    showServer: function (apiid ,page, rows, pageRestBool, callback) {
        var self = this;
        console.log("apiid:"+apiid)
        $.ajax({
            type:'POST',
            url:'./webapi05005.json',
            data: { 'apiid': apiid ,'page':1, 'rows':rows},
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    console.log(data)
                    self.createServerTable(data.rows);
                    self.spager.reset({
                        itemLength: data.total,
                        pageSize: self.pageSize,
                        reset: pageRestBool
                    });
                    callback();
                } else {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "error",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                        }
                    });
                }
            }, 
            error:function(){
               parent.Common.ajaxError();
            }
                        
        });
    },
    createServerPager: function (id) {
        var self = this;
        self.spager = pages({
            el: "#serverTable-pages",
            itemLength: 2300,
            pageSize: 10,
            pageChanged: function (pageIndex, pageSize) {
                self.spageSize = pageSize;
                self.showServer(id,pageIndex, pageSize, false);
            }
        });
        // self.showServer(id, 1, self.pageSize, true);
    },
    createServerTable: function (data) {
        var self = this;
        var cols = [
           { title:'序号', name:'funcid' ,width:100, align:'center', renderer: function (val, item, index) {
                        return index+1;
                    }},
           { title:'服务名称', name:'servicename' ,width:500, align:'center'},
           { title:'服务描述', name:'servicemsg' ,width:500, align:'center'},
        ];
        if(self.stabler != null) {
            self.stabler.load(data);
        } else {
            self.stabler = $('#serverTable').mmGrid({
                    height: 'auto',
                    cols: cols,
                    items: data,
                    loadingText: "loading...",
                    noDataText: "暂无数据。",
                    loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                    sortable: false
                });
        }
        parent.Common.loading(false);
    },
    editLongText: function(v){

        if(v == 0){
            parent.$('.etitle').text('编辑返回结果');
            parent.$('.tval').text('填写返回结果');
            parent.$('.eidtpram').val(parent.$('.buiParam').val());
        }else{
            parent.$('.etitle').text('编辑请求参数');
            parent.$('.tval').text('填写请求参数');
            parent.$('.eidtpram').val(parent.$('.buiAddr').val());
        }
        

        parent.$('.showLongEdit').show();
        parent.$('.showmain').hide();
    },
    getLongTxt: function(){
        parent.$('.showLongEdit').hide();
        parent.$('.showmain').show();

        var title = parent.$('.etitle').text();
        
        if(title == '编辑请求参数'){
            parent.$('.buiAddr').val(parent.$('.eidtpram').val());
        }else{
            parent.$('.buiParam').val(parent.$('.eidtpram').val());
        }
        

    }
};

$(function(){

    // 接口类型
    $.ajax({
        type:'POST',
        url:'./getApiType.json',
        data:{},
        datatype:'json',
        success:function(data){
            if(typeof data == 'string'){
                data = JSON.parse(data);
            }
            if(data.recode == '000000'){
                var customerOptions = '<option value="">请选择</option>';
                for(var i = 0;i<data.mi007list.length;i++){
                    customerOptions += '<option value="'+data.mi007list[i].itemid+'">'+data.mi007list[i].itemval+'</option>'
                }
                $('#buiType,#buiType1').html(customerOptions);
            }
        },
        error:function(){
            parent.Common.ajaxError();
        }
    });
    channelInterface.createPager();
    channelInterface.btnClick();

});

function showParam(item,i){
    var createHTML = $(".orgManage-popup-edit-container4").html();
    parent.Common.popupShow(createHTML);

    var str = '';

    if(i == 0){
        parent.$('h2 span').text('查看返回结果');
        parent.$('.tval').text('返回结果');
        str = item.repparam;
    }else{
        parent.$('h2 span').text('查看请求参数');
        parent.$('.tval').text('请求参数');
        str = item.reqparam;
    }

    parent.$('#showname').val(item.apiname);
    parent.$('#showpram').val(str);

}



/**
 * Created by M on 2016/9/6.
 */

var user = top.userInfo,
    alldata = null;
var wx007 = {
    tabler:null,
    getTableData: function () {
        var self = this,
            regionId = $("#searchCustomer").val();
        if(!regionId)
            return;

        parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url:'./weixinapi00404.json',
            data: {regionId:regionId},
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.errcode == 0) {
                    if(typeof data.rows == "string") {
                        data.rows = JSON.parse(data.rows);                        
                    }
                    alldata = data.rows.result;
                    self.createTable(data.rows.result);
                } else {
                    
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "error",
                        text: data.errmsg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {}
                    });
                }
                parent.Common.popupClose();
            }, 
            error:function(){
               parent.Common.ajaxError();
            }
                        
        });
    },
    createTable: function (data) {
        var self = this;
        var cols = [
           { title:'序号', name:'id' ,width:50, align:'center', renderer: function (val, item, index) {
                        return index+1;
                    }},
           { title:'功能名称', name:'name' ,width:410, align:'center'},
           { title:'功能键值', name:'key' ,width:415, align:'center'},
           { title:'操作', name:'isflag',width:200, align: 'center', renderer: function (val, item, index) {
                var temp = "";
                if (val == '0') {
                    temp += '<span class="orgManage-table-links">';
                    temp += "<a href='javascript:;' onclick='addSubInfo();' title='添加子项'>添加子项</a>";
                    temp += '<span>|</span>';
                    temp += '<a href="javascript:;" class="red-link" onclick="showSubInfo('+index+')" title="查看子项">查看子项</a>';
                    temp += '</span>';
                } else {
                    temp = '-';
                }
                return temp;
            } }

        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#wechat-focusInfo-table').mmGrid({
                    multiSelect: true,// 多选
                    checkCol: true, // 选框列
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
    add: function () {
        var self = this,
            regionId = $("#searchCustomer").val(),
            funcname = parent.$("#funcname").val(),
            isflag = parent.$("#wechat-baseQuery-popup-hasSub").val(),
            functype = parent.$("#wechat-baseQuery-popup-type").val(),
            funckey = parent.$("#funckey").val();

        if(regionId.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "请选择城市机构！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(funcname.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "功能名称不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(isflag == 1 && funckey.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "功能键值不能为空！",
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
        $.ajax({
            type: "POST",
            url: "./weixinapi00403.json",
            data: { 
                regionId:regionId,
                funcname:funcname,
                isflag:isflag,
                functype:functype,
                funckey:funckey
            },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.errcode == "0") {
                    self.getTableData();
                } else {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "error",
                        text: data.errmsg,
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
        parent.Common.popupClose();
    },
    edit: function () {
        var self = this,
            regionId = $("#searchCustomer").val(),
            funcname = parent.$("#funcname2").val(),
            isflag = parent.$("#wechat-baseQuery-popup-hasSub2").val(),
            functype = parent.$("#wechat-baseQuery-popup-type2").val(),
            funckey = parent.$("#funckey2").val();

        if(regionId.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "请选择城市机构！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(funcname.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "功能名称不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(isflag == 1 && funckey.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "功能键值不能为空！",
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
        $.ajax({
            type: "POST",
            url: "./weixinapi00403.json",
            data: { 
                regionId:regionId,
                funcname:funcname,
                isflag:isflag,
                functype:functype,
                funckey:funckey
            },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.errcode == "0") {
                    self.getTableData();
                } else {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "error",
                        text: data.errmsg,
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
        parent.Common.popupClose();
    },
    del: function(){
        var self = this,
            selected = self.tabler.selectedRows(),
            regionId = $('#searchCustomer').val(),
            delIds = [];
        for(var i=0; i<selected.length; i++) {
            delIds.push(selected[i].name);
        }
        var funcnames = delIds.join(",");

        parent.Common.loading(true);
        
        $.ajax({
            type:'POST',
            url:'./weixinapi00405.json',
            data:{
                regionId:regionId,
                funcname:funcnames
            },
            datatype:'json',
            success:function(data){
                console.log(data)
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.errcode == "0") {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "success",
                        text: data.errmsg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            self.getTableData();
                        }
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
                parent.Common.popupClose();
            },
            error:function () {
                parent.Common.ajaxError();
            }
        });
    },
    addsub: function () {
        var self = this,
            selected = self.tabler.selectedRows(),
            regionId = $("#searchCustomer").val(),
            fathername = selected[0].name;
            funcname = parent.$("#funcname3").val(),
            functype = parent.$("#wechat-baseQuery-popup-type3").val(),
            funckey = parent.$("#funckey3").val();

        if(funcname.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "功能名称不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(funckey.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "功能键值不能为空！",
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
        $.ajax({
            type: "POST",
            url: "./weixinapi00406.json",
            data: { 
                fathername:fathername,
                regionId:regionId,
                funcname:funcname,
                functype:functype,
                funckey:funckey
            },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.errcode == "0") {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "success",
                        text: data.errmsg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            self.getTableData();
                        }
                    });
                    
                } else {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "error",
                        text: data.errmsg,
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
        parent.Common.popupClose();
    },
    isflag: function(add){

        var flag = '';
        if(add){
            flag = parent.$('#wechat-baseQuery-popup-hasSub').val();
        }else{
            flag = parent.$('#wechat-baseQuery-popup-hasSub2').val();
        }
        

        if(flag == '0'){
            parent.$(".wechat-baseQuery-popup-create div").eq(3).hide();
        }else{
            parent.$(".wechat-baseQuery-popup-create div").eq(3).show();
        }
    },
    btnClick:function(){
        var self = this;
            

        // 新建
        $('#wechat-btn-add').click(function(e){
            var regionId = $("#searchCustomer").val();
            if(!regionId)
                return;
            
            var createHTML = $(".wechat-create").html();
            parent.Common.popupShow(createHTML);
        });
        
        // 修改
        $('#wechat-btn-edit').click(function(e){
            var regionId = $("#searchCustomer").val();
            if(!regionId)
                return;
            var selected = self.tabler.selectedRows();

            if(selected.length < 1) {
                parent.Common.dialog({
                    type: "error",
                    text: "请至少选择一条记录进行修改！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });
                return;
            } 
            if(selected.length>1){
                parent.Common.dialog({
                    type: "error",
                    text: "只能选择一条记录进行修改！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });
                return;
            }

            var editHTML = $(".wechat-edit").html();
            parent.Common.popupShow(editHTML);

            parent.$("#funcname2").val(selected[0].name),
            parent.$("#wechat-baseQuery-popup-hasSub2").val(selected[0].isflag),
            parent.$("#wechat-baseQuery-popup-type2").val(selected[0].type),
            parent.$("#funckey2").val(selected[0].key);
            
        });
        
        // 删除
        $('#wechat-btn-del').click(function(e){
            var regionId = $("#searchCustomer").val();
            if(!regionId)
                return;
            var selected = $("#wechat-focusInfo-table").find("tr.selected");
            if(selected.length < 1) {
                parent.Common.dialog({
                    type: "error",
                    text: "请至少选择一条记录进行删除！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });
                return;
            } 
            parent.Common.dialog({
                type: "warning",
                text: "确定删除选中项？",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    self.del();
                }
            });
        });


        //查询
        $('#btnquery').click(function(e){
            var regionId = $("#searchCustomer").val();
            if(!regionId)
                return;
            self.getTableData();
        });


    }
}
// 添加子项
function addSubInfo(){
    var createHTML = $(".wechat-add-sub").html();
    parent.Common.popupShow(createHTML);
}
// 查看子项
function showSubInfo(i){

     var childdata = alldata[i].children;

    if(childdata.length < 1){
        parent.Common.dialog({
                type: "warning",
                text: "无子项，请先添加子项！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {}
            });
            return;
    }

    var content = "";
    content = content + '<li><p class="wechat-view-sub-p1">序号</p><p class="wechat-view-sub-p2">功能名称</p><p class="wechat-view-sub-p3">功能键值</p></li>';
    for(var i = 0; i < childdata.length; i++){
        content = content + '<li><p class="wechat-view-sub-p1">' + (i + 1) + '</p><p class="wechat-view-sub-p2">' + childdata[i].name + '</p><p class="wechat-view-sub-p3">' + childdata[i].key + '</p></li>';
    }
    $("#showsub").html(content);


    var viewHTML = $(".wechat-view-sub").html();
    parent.Common.popupShow(viewHTML);
}

$(function(){
    
    $.ajax({
		type:'POST',
		url:'./webappcomCenterId.json',
		data:{},
		datatype:'json',
		success:function(data){
			if(typeof data == 'string'){
				data = JSON.parse(data);
			}
			if(data.recode == '000000'){
				var customerOptions = '<option value="">请选择</option>';
				for(var i = 0;i<data.mi001list.length;i++){
					if(user.centerid != '00000000'){
						if(user.centerid == data.mi001list[i].centerid){
							customerOptions = '<option value="'+data.mi001list[i].centerid+'">'+data.mi001list[i].centername+'</option>'
						}
					} else {
						customerOptions += '<option value="'+data.mi001list[i].centerid+'">'+data.mi001list[i].centername+'</option>';
					}
				}

				$('#searchCustomer,#regionId,#regionId1').html(customerOptions);

			}
			
		},
		error:function(){
			parent.Common.ajaxError();
		}
	});
    wx007.getTableData();
    wx007.btnClick();
    
});













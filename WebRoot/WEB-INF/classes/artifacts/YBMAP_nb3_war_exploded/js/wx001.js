/**
 * Created by M on 2016/9/5.
 */

var user = top.userInfo;
var wx001 = {
    tabler:null,
    getTableData: function (bol) {
        var self = this,
        _data = {},
        _url = './weixinapi00103.json';
        if(bol.length > 0){
        	_data = {'regionId':$('#searchCustomer').val()};
        	_url = './weixinapi00102.json';
        }
        $.ajax({
            type:'POST',
            url:_url,
            data: _data,
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.errcode == "0") {
                    self.createTable(data.rows);
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
            error:function(){
               parent.Common.ajaxError();
            }
                        
        });
    },
    createTable: function (data) {
        var self = this;
        var cols = [
           { title:'序号', name:'id' ,width:40, align:'center', renderer: function (val, item, index) {
                        return index+1;
                    }},
           { title:'机构名称', name:'regionId' ,width:120, align:'center' , renderer: function (val, item, index) {
                        return $('#regionId option[value='+val+']').text();
                    }},
           { title:'微信账号', name:'weixinId' ,width:130, align:'center'},
           { title:'开发者帐号', name:'appId' ,width:180, align:'center'},

           { title:'开发者密钥', name:'appScret' ,width:180, align:'center'},
           { title:'中心Token', name:'msgToken' ,width:180, align:'center'},
           { title:'链接地址', name:'msgUrl' ,width:180, align:'center'},
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#wx001Table').mmGrid({
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
            regionId = parent.$("#regionId").val(),
            weixinId = parent.$("#weixinId").val(),
            appId = parent.$("#appId").val(),
            appScret = parent.$("#appScret").val(),
            msgToken = parent.$("#msgToken").val(),
            msgUrl = parent.$("#msgUrl").val();

        if(regionId.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "机构名称不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(weixinId.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "微信帐号不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(appId.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "开发者帐号不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(appScret.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "开发者密钥不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(msgToken.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "中心Token不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(msgUrl.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "链接地址不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(!an.isURL(parent.$('#msgUrl').val())){
        	parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "请输入正确的链接地址！",
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
            url: "./weixinapi00101.json",
            data: { 
                'regionId': regionId, 
                'weixinId': weixinId, 
                'appId': appId, 
                'appScret': appScret,
                'msgToken' :msgToken,
                'msgUrl':msgUrl
            },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.errcode == "0") {
                    self.getTableData('');
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
        parent.Common.loading(true);
        var self = this,
            selectedData = self.tabler.selectedRows();

        var regionId =  selectedData[0].regionId,

            weixinId = parent.$("#weixinId").val(),
            appId = parent.$("#appId").val(),
            appScret = parent.$("#appScret").val(),
            msgToken = parent.$("#msgToken").val();
            msgUrl = parent.$("#msgUrl").val();

        parent.Common.popupClose();
        $.ajax({
            type: "POST",
            url: "./weixinapi00104.json",
            datatype: "json",
            data: { 
                'regionId': regionId, 
                'weixinId': weixinId, 
                'appId': appId, 
                'appScret': appScret,
                'msgToken' :msgToken,
                'msgUrl':msgUrl
            },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.errcode == "0") {
                    self.getTableData('');
                } else {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "error",
                        text: data.errmsg,
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
    btnClick:function(){
        var self = this;

        $('#orgManage-btn-add').click(function(e){
            var createHTML = $(".orgManage-popup-edit-container").html();
            parent.Common.popupShow(createHTML);
        });
        
        
        $('#orgManage-btn-modify').click(function(e){
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

            var createHTML2 = $(".orgManage-popup-edit-container1").html();
            parent.Common.popupShow(createHTML2);


            parent.$("#regionId1").val(selected[0].regionId),
            parent.$("#weixinId1").val(selected[0].weixinId),
            parent.$("#appId1").val(selected[0].appId),
            parent.$("#appScret1").val(selected[0].appScret),
            parent.$("#msgToken1").val(selected[0].msgToken);
            parent.$("#msgUrl1").val(selected[0].msgUrl);
            
        });
        

        //查询
        $('#btnquery').click(function(){
            self.getTableData('query');
        });
        
    }
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

				console.log(customerOptions)
				
			}
			
		},
		error:function(){
			parent.Common.ajaxError();
		}
	});
    wx001.getTableData('');
    wx001.btnClick();
    
});













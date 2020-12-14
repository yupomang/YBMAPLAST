var pageSizeTemp = 10;
   var orgManagementPages = pages({
       el: "#orgManage-table-pages",
       itemLength: 2300,
       pageSize: 12,
       pageChanged: function (pageIndex, pageSize) {
           pageSizeTemp = pageSize;
           console.log("genggaihgahaha", pageIndex);
       }
   });

var cols = [
   { title:'序号', name:'a' ,width:60, align:'center' },
   { title:'利率ID', name:'b' ,width:90, align:'center' },
   { title:'中心ID/名称', name:'c' ,width:100, align:'center'},
   { title:'利率类型', name:'d' ,width:90, align:'center'},
   { title:'月数期限', name:'e' ,width:80, align:'center'},
   { title:'基准利率', name:'f' ,width:70, align:'center'},
   { title:'生效日期', name:'g' ,width:90, align:'center'},
   { title:'修改时间 ', name:'h' ,width:195, align:'center'},
   { title:'创建时间', name:'i' ,width:195, align:'center'},
];
var data = 
{
    "rows": [{
        'a':'1',
        'b':'利率',
        'c':'公积金中心',
        'd':'公积金贷款',
        'e':'360期',
        'f':'2.75%',
        'g':'2015-05-24',
        'h':'2015-08-26 14:34:37.730',
        'i':'2015-08-26 14:34:37.730',
    },
    {
        'a':'1',
        'b':'利率',
        'c':'公积金中心',
        'd':'公积金贷款',
        'e':'360期',
        'f':'2.75%',
        'g':'2015-05-24',
        'h':'2015-08-26 14:34:37.730',
        'i':'2015-08-26 14:34:37.730',
    },
    {
        'a':'1',
        'b':'利率',
        'c':'公积金中心',
        'd':'公积金贷款',
        'e':'360期',
        'f':'2.75%',
        'g':'2015-05-24',
        'h':'2015-08-26 14:34:37.730',
        'i':'2015-08-26 14:34:37.730',
    },
    {
        'a':'1',
        'b':'利率',
        'c':'公积金中心',
        'd':'公积金贷款',
        'e':'360期',
        'f':'2.75%',
        'g':'2015-05-24',
        'h':'2015-08-26 14:34:37.730',
        'i':'2015-08-26 14:34:37.730',
    },
    {
        'a':'1',
        'b':'利率',
        'c':'公积金中心',
        'd':'公积金贷款',
        'e':'360期',
        'f':'2.75%',
        'g':'2015-05-24',
        'h':'2015-08-26 14:34:37.730',
        'i':'2015-08-26 14:34:37.730',
    },
    {
        'a':'1',
        'b':'利率',
        'c':'公积金中心',
        'd':'公积金贷款',
        'e':'360期',
        'f':'2.75%',
        'g':'2015-05-24',
        'h':'2015-08-26 14:34:37.730',
        'i':'2015-08-26 14:34:37.730',
    },
    {
        'a':'1',
        'b':'利率',
        'c':'公积金中心',
        'd':'公积金贷款',
        'e':'360期',
        'f':'2.75%',
        'g':'2015-05-24',
        'h':'2015-08-26 14:34:37.730',
        'i':'2015-08-26 14:34:37.730',
    },
    {
        'a':'1',
        'b':'利率',
        'c':'公积金中心',
        'd':'公积金贷款',
        'e':'360期',
        'f':'2.75%',
        'g':'2015-05-24',
        'h':'2015-08-26 14:34:37.730',
        'i':'2015-08-26 14:34:37.730',
    },
    {
        'a':'1',
        'b':'利率',
        'c':'公积金中心',
        'd':'公积金贷款',
        'e':'360期',
        'f':'2.75%',
        'g':'2015-05-24',
        'h':'2015-08-26 14:34:37.730',
        'i':'2015-08-26 14:34:37.730',
    },
    ]
}

var mmg = $('#channelSetTable').mmGrid({
    multiSelect: true,// 多选
    checkCol: true, // 选框列
	height: 'auto',
	cols: cols,
	items: data.rows,
	loadingText: "loading...",
	noDataText: "暂无数据。",
	loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
	sortable: false
});


$(function(){
	$('#orgManage-btn-add').click(function(e){
		var createHTML = $(".orgManage-popup-edit-container").html();
		parent.Common.popupShow(createHTML);
	});
	
	
	$('#orgManage-btn-modify').click(function(e){
		var selected = $("#channelSetTable").find("tr.selected");
		var valBuiCode		= selected.find('td:eq(1)').text(),
    		valBuiArae		= selected.find('td:eq(2)').text(),
    		valBuiName		= selected.find('td:eq(3)').text(),
    		valBuiDeve		= selected.find('td:eq(4)').text(),
    		valBuiAddr		= selected.find('td:eq(5)').text(),
    		valBuiType		= selected.find('td:eq(6)').text(),
    		valBuiContacter	= selected.find('td:eq(7)').text(),
    		valBuiTel		= selected.find('td:eq(8)').text();
		if(selected.length < 1) {
	        parent.Common.dialog({
	            type: "error",
	            text: "至少选中一条记录！",
	            okShow: true,
	            cancelShow: false,
	            okText: "确定",
	            ok: function () {}
	        });
	    } 
	    if(selected.length>1){
	    	parent.Common.dialog({
	            type: "error",
	            text: "选多啦！只能选中一条！",
	            okShow: true,
	            cancelShow: false,
	            okText: "确定",
	            ok: function () {}
	        });
	    }
	    if(selected.length == 1) {
	    	
	    	$('#buiCode1').attr("value", valBuiCode);
    		$('#buiArea1').attr("value",valBuiArae);
    		$('#buiName1').attr("value",valBuiName);
    		$('#buiDeve1').attr("value",valBuiDeve);
    		$('#buiAddr1').attr("value",valBuiAddr);
    		$('#buiType1').attr("value",valBuiType);
    		$('#buiContacter1').attr("value",valBuiContacter);
    		$('#buiTel1').attr("value",valBuiTel);
	    	
	        var createHTML2 = $(".orgManage-popup-edit-container1").html();
			parent.Common.popupShow(createHTML2);
			
			
	    	
	    }
		
	});
	
	$('#orgManage-btn-del').click(function(){
		var selected = $("#channelSetTable").find("tr.selected");
		parent.Common.dialog({
            type: "warning",
            text: "确定删除选中项？",
            okShow: true,
            cancelShow: false,
            okText: "确定",
            ok: function () {
            	parent.$(selected).slideUp(0);
            }
        });
	});
	
});








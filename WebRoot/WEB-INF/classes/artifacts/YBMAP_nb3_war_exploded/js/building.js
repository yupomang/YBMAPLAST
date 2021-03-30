var pageSizeTemp = 10;
var orgManagementPages = pages({
	el: "#orgManage-table-pages",
	itemLength: 2300,
	pageSize: 12,
	pageChanged: function (pageIndex, pageSize) {
		pageSizeTemp = pageSize;
		getBuilding(pageIndex, pageSize);
	}
});
$("#citycode").val(top.userInfo.centerid);
$("#cityname").val(top.userInfo.centerName);
// 百度地图API功能
var mapValue=0;
var overlay=0;
var maparr;
var websiteId;
var websiteCode;
var address;
var positionX;
var positionY;
var map = new BMap.Map('mapWrap');
map.centerAndZoom(top.userInfo.centerName,13);
map.enableScrollWheelZoom();
map.addControl(new BMap.NavigationControl());
map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT}));
map.addControl(new BMap.OverviewMapControl({isOpen:true, anchor: BMAP_ANCHOR_TOP_RIGHT}));
map.addControl(new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_HYBRID_MAP]}));

var mmg = null;

$(function(){
	$('#orgManage-btn-add').click(function(e){
		var createHTML = $(".orgManage-popup-edit-container").html();
		parent.Common.popupShow(createHTML);
	});
	
	
	$('#orgManage-btn-modify').click(function(e){
		var selected = $("#buildingTable").find("tr.selected");
		var valBuiCode		= selected.find('td:eq(1)').text(),
    		valBuiArae		= selected.find('td:eq(2)').text(),
    		valBuiName		= selected.find('td:eq(3)').text(),
    		valBuiDeve		= selected.find('td:eq(4)').text(),
    		valBuiAddr		= selected.find('td:eq(5)').text(),
    		valBuiType		= selected.find('td:eq(6)').text(),
    		valBuiContacter	= selected.find('td:eq(7)').text(),
    		valBuiTel		= selected.find('td:eq(8)').text(),
    		valBuiBank		= selected.find('td:eq(9)').text();
		if(selected.length < 1) {
			parent.Common.editNone();
	    } 
	    if(selected.length>1){
			parent.Common.editMore();
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
    		$('#buiBank1').attr("value",valBuiBank);
    		setTimeout(function(){
    		console.log($('#buiCode1')[0]);},200);
	    	
	        var createHTML2 = $(".orgManage-popup-edit-container1").html();
			parent.Common.popupShow(createHTML2);
			
			
	    	
	    }
		
	});
	
	$('#orgManage-btn-del').click(function(){
		var selected = $("#buildingTable").find("tr.selected");
		if(selected.length < 1) {
			parent.Common.delNone();
			return;
		}
		parent.Common.dialog({
            type: "tips",
            text: "确定删除选中项？",
            okShow: true,
            cancelShow: true,
            okText: "确定",
            ok: function () {
				building.del();
            }
        });
	});

});
function getData(data){
	$('#buiBank1').attr('value',data);
	parent.Common.popupClose();
	var createHTML2 = $(".orgManage-popup-edit-container1").html();
	parent.Common.popupShow(createHTML2);
}
//点击选择银行
function showBank(){
	var createHTML3 = $(".orgManage-popup-edit-container2").html();
	parent.Common.popupShow(createHTML3);
	parent.showBanks();
}

$('.orgManage-popup-longInput').css({'padding':'3px 5px'});
$('.red-required').css({'line-height':'36px'});
$('.orgManage-popup-text').css({'line-height':'34px'});
$('.orgManage-popup-from li').css({'padding-bottom':'6px'});
$('.orgManage-popup-btns').css({'padding':'10px 0 20px'});
$('.orgManage-popup-from').css({'width':'460px'});
$('.orgManage-popup-longInput').css({'width':'300px'});
$('.orgManage-popup-from').each(function(){
	$(this).find('.orgManage-popup-longInput:last').css({'width':'300px'});
})


//显示地图

$('#orgManage-btn-mapinfo').click(function(){
	var createHTML = $(".orgManage-popup-edit-container4").html();
	parent.Common.popupShow(createHTML);
});

//批量导入
$('#orgManage-btn-import').click(function(){
	var createHTML = $(".orgManage-popup-edit-container5").html();
	parent.Common.popupShow(createHTML);
	parent.$('#file').on('change', function( e ){
		var name = e.currentTarget.files[0].name;
		parent.$('#excelfile').text( name );
	});
});
//所有的错误信息
function errMsg (){
	parent.Common.dialog({
		type:'error',
		text:'网络错误！请刷新页面或联系管理员！',
		isShow:true,
		cancelShow:true,
		okText:'确定',
		ok:function(){
			
		}
	});
}
function getBuilding(pageIndex, pageSize){
	parent.Common.loading(true);
	if(typeof parseInt(pageIndex) != "number" || typeof pageIndex == "undefined" || parseInt(pageIndex) < 1){
		var cpage = 1;
	} else {
		var cpage = pageIndex;
	}
	var paras = {'page':cpage,'rows':pageSize};
	if($.trim($("#f-housesName").val()).length > 0){
		paras.houseName = $.trim($("#f-housesName").val());
	}
	if($.trim($("#f-address").val()).length > 0){
		paras.address = $.trim($("#f-address").val());
	}
	if($.trim($("#f-houseType").val()).length > 0){
		paras.houseType = $.trim($("#f-houseType").val());
	}
	if($.trim($("#f-developerName").val()).length > 0){
		paras.developerName = $.trim($("#f-developerName").val());
	}
	if($.trim($("#f-bankNames").val()).length > 0){
		paras.bankNames = $.trim($("#f-bankNames").val());
	}
	$.ajax({
		type:'POST',
		url:'./webapi00804.json',
		data:paras,
		datatype:'json',
		success:function(data){
			parent.Common.loading(false);
			if(typeof data == 'string'){
				data = JSON.parse(data);
			}
			building.createTable(data);
			//修改分页
			orgManagementPages.reset({
				itemLength:data.total,
	            pageSize:data.pageSize
			});
		},
		error:function(){
			parent.Common.loading(false);
			errMsg();
		}
	});
}
function btnsClickBind(){
	$("#filterBtn").on("click",function(){
		getBuilding(1, pageSizeTemp);
	})
}
$(document).ready(function(){
	btnsClickBind();
	building.getArea();
	building.getCityList();
});

var building = {
	isAdd: true,
	areaList: [],
    cacheCityList: null,
	getArea: function () {
		var self = this;
		parent.Common.loading(true);
		$.ajax({
			type: "POST",
			url: './webapi00804GetArea.json',
			datatype: "json",
			success: function(data) {
				if(typeof data == "string") {
					data = JSON.parse(data);
				}
				if (data.recode == "000000") {
					parent.Common.loading(false);
					self.areaList = data.mi202list;
					var temp = '';
					data.mi202list.forEach(function (item) {
						temp += '<option value="'+ item.areaId + '">' + item.areaName + '</option>';
					});
					$(".channelShortSelect").html(temp);
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
			error: function () {
				parent.Common.ajaxError();
			}
		});
		getBuilding(1, pageSizeTemp);
		self.btnClick();
	},
	createTable: function (data) {
		var self = this;
		var cols = [
			{ title:'楼盘编码', name:'houseCode' ,width:64, align:'center' },
			{ title:'所属区域', name:'areaId' ,width:85, align:'center', renderer: function (val) {
				var temp = '';
				self.areaList.forEach(function (item) {
					if (val == item.areaId) {
						temp = item.areaName;
					}
				});
				return temp;
			} },
			{ title:'楼盘名称', name:'houseName' ,width:100, align:'center'},
			{ title:'开发商名称', name:'developerName' ,width:140, align:'center'},
			{ title:'楼盘地址', name:'address' ,width:140, align:'center'},
			{ title:'楼盘类型', name:'houseType' ,width:70, align:'center'},
			{ title:'联系人', name:'contacterName' ,width:70, align:'center'},
			{ title:'联系电话', name:'tel' ,width:70, align:'center'},
			{ title:'合作银行', name:'bankNames' ,width:90, align:'center'},
			{ title:'X坐标', name:'positionX' ,width:50, align:'center'},
			{ title:'Y坐标', name:'positionY' ,width:50, align:'center'},
		];
		if( mmg != null){
			mmg.load(data.rows);
		} else {
			mmg = $('#buildingTable').mmGrid({
				multiSelect: true,// 多选
				checkCol: true, // 选框列
				height: '390',
				cols: cols,
				items: data.rows,
				loadingText: "loading...",
				noDataText: "暂无数据。",
				loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
				sortable: false
			});
		}
	},
    getCityList: function () {
        var self = this;
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./ptl40003Qry.json",
            data: { 'centerid': top.userInfo.centerid },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.cacheCityList = data.rows;
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
	addVerify: function () {
		var houseCode = parent.$("#buiCode").val(), // 网点编码
			//areaId = parent.$("#buiArea").val(), // 楼盘区域
			houseName = parent.$("#buiName").val(), // 楼盘名称
			developerName = parent.$("#buiDeve").val(), // 开发商名称
			address = parent.$("#buiAddr").val(), // 楼盘地址
			houseType = parent.$("#buiType").val(), // 楼盘类型
			contacterName = parent.$("#buiContacter").val(), // 联系人姓名
			tel = parent.$("#buiTel").val(), // 联系电话
			bankNames = parent.$("#buiBank").val(); // 合作银行;
		var status = true;
		if(houseCode.length < 1) {
			parent.$("#popup-container").hide();
			parent.Common.dialog({
				type: "warning",
				text: "网点编码不能为空！",
				okShow: true,
				cancelShow: false,
				okText: "确定",
				ok: function () {
					parent.$("#popup-container").show();
				}
			});
			return false;
		}
		if(houseName.length < 1) {
			parent.$("#popup-container").hide();
			parent.Common.dialog({
				type: "warning",
				text: "楼盘名称不能为空！",
				okShow: true,
				cancelShow: false,
				okText: "确定",
				ok: function () {
					parent.$("#popup-container").show();
				}
			});
			return false;
		}
		if(developerName.length < 1) {
			parent.$("#popup-container").hide();
			parent.Common.dialog({
				type: "warning",
				text: "开发商名称不能为空！",
				okShow: true,
				cancelShow: false,
				okText: "确定",
				ok: function () {
					parent.$("#popup-container").show();
				}
			});
			return false;
		}
		if(address.length < 1) {
			parent.$("#popup-container").hide();
			parent.Common.dialog({
				type: "warning",
				text: "楼盘地址不能为空！",
				okShow: true,
				cancelShow: false,
				okText: "确定",
				ok: function () {
					parent.$("#popup-container").show();
				}
			});
			return false;
		}
		if(houseType.length < 1) {
			parent.$("#popup-container").hide();
			parent.Common.dialog({
				type: "warning",
				text: "楼盘类型不能为空！",
				okShow: true,
				cancelShow: false,
				okText: "确定",
				ok: function () {
					parent.$("#popup-container").show();
				}
			});
			return false;
		}
		if(contacterName.length < 1) {
			parent.$("#popup-container").hide();
			parent.Common.dialog({
				type: "warning",
				text: "联系人姓名不能为空！",
				okShow: true,
				cancelShow: false,
				okText: "确定",
				ok: function () {
					parent.$("#popup-container").show();
				}
			});
			return false;
		}
		if(tel.length < 1) {
			parent.$("#popup-container").hide();
			parent.Common.dialog({
				type: "warning",
				text: "联系人姓名不能为空！",
				okShow: true,
				cancelShow: false,
				okText: "确定",
				ok: function () {
					parent.$("#popup-container").show();
				}
			});
			return false;
		}
		console.log(tel, parent.Common.checkPhone(tel), parent.Common.checkMobile(tel));
		if(!(parent.Common.checkPhone(tel) || parent.Common.checkMobile(tel))) {
			parent.$("#popup-container").hide();
			parent.Common.dialog({
				type: "warning",
				text: "请填写正确格式的联系电话！座机区号需要以'-'分隔！",
				okShow: true,
				cancelShow: false,
				okText: "确定",
				ok: function () {
					parent.$("#popup-container").show();
				}
			});
			return false;
		}
		return status;
	},
	editVerify: function () {
		var houseCode = parent.$("#buiCode1").val(), // 网点编码
			//areaId = parent.$("#buiArea").val(), // 楼盘区域
			houseName = parent.$("#buiName1").val(), // 楼盘名称
			developerName = parent.$("#buiDeve1").val(), // 开发商名称
			address = parent.$("#buiAddr1").val(), // 楼盘地址
			houseType = parent.$("#buiType1").val(), // 楼盘类型
			contacterName = parent.$("#buiContacter1").val(), // 联系人姓名
			tel = parent.$("#buiTel1").val(), // 联系电话
			bankNames = parent.$("#buiBank1").val(); // 合作银行;
		var status = true;
		if(houseCode.length < 1) {
			parent.$("#popup-container").hide();
			parent.Common.dialog({
				type: "warning",
				text: "网点编码不能为空！",
				okShow: true,
				cancelShow: false,
				okText: "确定",
				ok: function () {
					parent.$("#popup-container").show();
				}
			});
			return false;
		}
		if(houseName.length < 1) {
			parent.$("#popup-container").hide();
			parent.Common.dialog({
				type: "warning",
				text: "楼盘名称不能为空！",
				okShow: true,
				cancelShow: false,
				okText: "确定",
				ok: function () {
					parent.$("#popup-container").show();
				}
			});
			return false;
		}
		if(developerName.length < 1) {
			parent.$("#popup-container").hide();
			parent.Common.dialog({
				type: "warning",
				text: "开发商名称不能为空！",
				okShow: true,
				cancelShow: false,
				okText: "确定",
				ok: function () {
					parent.$("#popup-container").show();
				}
			});
			return false;
		}
		if(address.length < 1) {
			parent.$("#popup-container").hide();
			parent.Common.dialog({
				type: "warning",
				text: "楼盘地址不能为空！",
				okShow: true,
				cancelShow: false,
				okText: "确定",
				ok: function () {
					parent.$("#popup-container").show();
				}
			});
			return false;
		}
		if(houseType.length < 1) {
			parent.$("#popup-container").hide();
			parent.Common.dialog({
				type: "warning",
				text: "楼盘类型不能为空！",
				okShow: true,
				cancelShow: false,
				okText: "确定",
				ok: function () {
					parent.$("#popup-container").show();
				}
			});
			return false;
		}
		if(contacterName.length < 1) {
			parent.$("#popup-container").hide();
			parent.Common.dialog({
				type: "warning",
				text: "联系人姓名不能为空！",
				okShow: true,
				cancelShow: false,
				okText: "确定",
				ok: function () {
					parent.$("#popup-container").show();
				}
			});
			return false;
		}
		if(tel.length < 1) {
			parent.$("#popup-container").hide();
			parent.Common.dialog({
				type: "warning",
				text: "联系人姓名不能为空！",
				okShow: true,
				cancelShow: false,
				okText: "确定",
				ok: function () {
					parent.$("#popup-container").show();
				}
			});
			return false;
		}
		console.log(tel, parent.Common.checkPhone(tel), parent.Common.checkMobile(tel));
		if(!(parent.Common.checkPhone(tel) || parent.Common.checkMobile(tel))) {
			parent.$("#popup-container").hide();
			parent.Common.dialog({
				type: "warning",
				text: "请填写正确格式的联系电话！座机区号需要以'-'分隔！",
				okShow: true,
				cancelShow: false,
				okText: "确定",
				ok: function () {
					parent.$("#popup-container").show();
				}
			});
			return false;
		}
		return status;
	},
	add: function () {
		var self = this;
		if(!self.addVerify()) { return; }
		var houseCode = parent.$("#buiCode").val(), // 网点编码
			areaId = parent.$(".channelShortSelect").val(), // 楼盘区域
			houseName = parent.$("#buiName").val(), // 楼盘名称
			developerName = parent.$("#buiDeve").val(), // 开发商名称
			address = parent.$("#buiAddr").val(), // 楼盘地址
			houseType = parent.$("#buiType").val(), // 楼盘类型
			contacterName = parent.$("#buiContacter").val(), // 联系人姓名
			tel = parent.$("#buiTel").val(), // 联系电话
			bankNames = parent.$("#buiBank").val(); // 合作银行;
		parent.Common.popupClose();
		parent.Common.loading(true);
		$.ajax({
			type: "POST",
			url: './webapi00801.json',
			data: {
				'houseCode': houseCode,
				'areaId': areaId,
				'houseName': houseName,
				'developerName': developerName,
				'address': address,
				'houseType': houseType,
				'contacterName': contacterName,
				'tel': tel,
				'bankNames': bankNames
			},
			datatype: "json",
			success: function(data) {
				if(typeof data == "string") {
					data = JSON.parse(data);
				}
				if (data.recode == "000000") {
					parent.Common.loading(false);
					parent.Common.dialog({
						type: "success",
						text: data.msg,
						okShow: true,
						cancelShow: false,
						okText: "确定",
						ok: function () {
							getBuilding(1, pageSizeTemp);
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
						ok: function () {
						}
					});
				}
			},
			error: function () {
				parent.Common.ajaxError();
			}
		});
	},
	edit: function () {
		var self = this,
			selected = mmg.selectedRows();
		if(!self.editVerify()) { return; }
		var housesId = selected[0].housesId,
			houseCode = parent.$("#buiCode1").val(), // 网点编码
			areaId = parent.$(".channelShortSelect").val(), // 楼盘区域
			houseName = parent.$("#buiName1").val(), // 楼盘名称
			developerName = parent.$("#buiDeve1").val(), // 开发商名称
			address = parent.$("#buiAddr1").val(), // 楼盘地址
			houseType = parent.$("#buiType1").val(), // 楼盘类型
			contacterName = parent.$("#buiContacter1").val(), // 联系人姓名
			tel = parent.$("#buiTel1").val(), // 联系电话
			bankNames = parent.$("#buiBank1").val(); // 合作银行;
		parent.Common.popupClose();
		parent.Common.loading(true);
		$.ajax({
			type: "POST",
			url: './webapi00803.json',
			data: {
				'housesId': housesId,
				'houseCode': houseCode,
				'areaId': areaId,
				'houseName': houseName,
				'developerName': developerName,
				'address': address,
				'houseType': houseType,
				'contacterName': contacterName,
				'tel': tel,
				'bankNames': bankNames
			},
			datatype: "json",
			success: function(data) {
				if(typeof data == "string") {
					data = JSON.parse(data);
				}
				if (data.recode == "000000") {
					parent.Common.loading(false);
					parent.Common.dialog({
						type: "success",
						text: data.msg,
						okShow: true,
						cancelShow: false,
						okText: "确定",
						ok: function () {
							getBuilding(1, pageSizeTemp);
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
						ok: function () {
						}
					});
				}
			},
			error: function () {
				parent.Common.ajaxError();
			}
		});
	},
	btnClick: function () {
		var self = this;
		//显示地图
		$('#orgManage-btn-mapinfo').click(function(){
			var selected = mmg.selectedRows();

			if(selected.length < 1) {
				parent.Common.dialog({
					type: "warning",
					text: "请至少选择一条记录！",
					okShow: true,
					cancelShow: false,
					okText: "确定",
					ok: function () {}
				});
				return;
			}
			if(selected.length>1){
				parent.Common.dialog({
					type: "warning",
					text: "只能选择一条记录！",
					okShow: true,
					cancelShow: false,
					okText: "确定",
					ok: function () {}
				});
				return;
			}


			$(".main").hide();
			$(".orgManage-popup-info-container").show();

            var cityObj = self.cacheCityList.filter(function (item) {
                if(item.centerid == selected[0].centerid) return item;
            });
			$('.mapFullAddr').val(selected[0].address);
			$('.mapCity').val(cityObj.length > 0 ? cityObj[0].freeuse1 : "");

			positionX=selected[0].positionX;
			positionY=selected[0].positionY;

			mapValue=1;
			overlay=1;
			map.reset();
			map.clearOverlays();
			websiteId=selected[0].housesId;
			websiteCode=selected[0].houseCode;
			address = selected[0].address;
			//url = "/YBMAP/webapi10103.json";
			if(positionX&&positionY){
				var point1 = new BMap.Point(positionX,positionY);
				map.centerAndZoom(point1, 16);
				map.addControl(new BMap.NavigationControl());
				var marker1 = new BMap.Marker(point1);
				map.addOverlay(marker1);
				window.setTimeout(function(){
					map.panTo(point1);
				},0);

			}
			else{
				self.selectMap();
			}

			$('#imgfile').on('change', function( e ){

				var name = e.currentTarget.files[0].name;
				name.length>15?name=name.substr(0,20)+'...':name;
				$('#imgname').text( name );
				console.log(name.length)

			});

		});
		// 地图定位
		$('#setposition').click(function(){
			self.selectMap();
		});

		// 地图保存
		$('.mapsave').click(function(){
			self.saveMap();
		});

		// 图片预览
		$('.mapViewBigimg img').click(function(){
			var createHTML = $(".orgManage-popup-edit-img").html();
			parent.Common.popupShow(createHTML);
		});

		$('#orgManage-info-goBack').click(function(){
			$(".main").show();
			$(".orgManage-popup-info-container").hide();
		});
	},
	selectMap:function(){
		var address = $('.mapFullAddr').val(),
			city = $('.mapCity').val();
		if(city.length < 1) {
			parent.Common.dialog({
				type: "error",
				text: "城市信息不可以为空！",
				okShow: true,
				cancelShow: false,
				okText: "确定",
				ok: function () {}
			});
			return;
		}
		if(address.length < 1) {
			parent.Common.dialog({
				type: "error",
				text: "详细地址信息不可以为空！",
				okShow: true,
				cancelShow: false,
				okText: "确定",
				ok: function () {}
			});
			return;
		}
		if(mapValue==1){
			overlay=1;
			map.reset();
			map.clearOverlays();
			var myGeo = new BMap.Geocoder();
			var address = $('.mapFullAddr').val();
			var cityname=$('.mapCity').val();
			myGeo.getPoint(address, function(point){
				if (point) {
					map.centerAndZoom(point, 16);
					map.addOverlay(new BMap.Marker(point));
					window.setTimeout(function(){
						map.panTo(point);
					},0);
				}
				else{
					$('.mapFullAddr').val('');
						$('.mapCity').val('');
					parent.Common.dialog({
						type: "error",
						text: "城市信息或详细地址信息不正确，未查询到相应位置！",
						okShow: true,
						cancelShow: false,
						okText: "确定",
						ok: function () {}
					});
					return;
				}
			}, cityname);
		}
	},
	saveMap:function (){
		var self = this;
		var arr = [
			{name : "housesId",value : websiteId},
			{name : "houseCode",value : websiteCode},
			{name : "address",value : $('.mapFullAddr').val()},
			{name : "positionX",value : positionX},
			{name : "positionY",value : positionY}
		];

		$.ajax( {
			type : "POST",
			url : './webapi00803.json',
			dataType: "json",
			data:arr,
			success : function(data) {
				parent.Common.dialog({
					type: "success",
					text: data.msg,
					okShow: true,
					cancelShow: false,
					okText: "确定",
					ok: function () {
						getBuilding(1, pageSizeTemp);
					}
				});
			},
			error :function(){
				parent.Common.ajaxError();
			}
		});
	},
	submitExl: function() {
		var self = this,
			excelFile=parent.$('#excelfile').text();
		if(null == excelFile || "" == excelFile){
			parent.$("#popup-container").hide();
			parent.Common.dialog({
				type: "warning",
				text: '请填写上传路径后进行提交',
				okShow: true,
				cancelShow: false,
				okText: "确定",
				ok: function () {
					parent.$("#popup-container").show();
				}
			});
			return false;
		}
		parent.$('#uploadform').ajaxSubmit({
			dataType : "json",
			success : function(data) {
				if(data.recode == '000000'){
					parent.$("#popup-container").hide();
					parent.Common.dialog({
						type: "success",
						text: '文件检查通过,是否执行批量导入?',
						okShow: true,
						cancelShow: true,
						okText: "确定",
						ok: function () {
							var citycode = top.userInfo.centerid;
							var cityname = top.userInfo.centerName;
							var path = parent.$('#path').val();
							var ruleMapStr = parent.$('#ruleMapStr').val();
							var tableName = parent.$('#tableName').val();
							var arr = [
								{name : "cityname",value : cityname},
								{name : "citycode",value : citycode},
								{name : "path",value : path},
								{name : "ruleMapStr",value : ruleMapStr},
								{name : "tableName",value : tableName},
								{name : "fileName",value : data.checkfile}
							];
							$.ajax({
								type : 'POST',
								url : "./webapi10102_uploadimg.do",
								dataType: 'json',
								data:arr,
								success : function(data) {
									if (data.recode == '000000') {
										parent.Common.dialog({
											type: "success",
											text: data.msg,
											okShow: true,
											cancelShow: false,
											okText: "确定",
											ok: function () {
												getBuilding(1, pageSizeTemp);
											}
										});
									}
									else{
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
								error :function(){
									parent.Common.ajaxError();
								}
							});
						}
					});
				}else if(data.recode == "000001"){
					parent.$("#popup-container").hide();
					parent.Common.dialog({
						type: "tips",
						text: '文件检查异常,是否下载检查结果',
						okShow: true,
						cancelShow: false,
						okText: "确定",
						ok: function () {
							window.location.href = data.checkfile;
							parent.Common.popupClose();
						}
					});
				}
			},
			error:function() {
				parent.$("#popup-container").hide();
				parent.Common.ajaxError();
			}
		});
	},
	del: function () {
		var selected = mmg.selectedRows(),
			ids = [];
		selected.forEach(function (item) {
			ids.push(item.housesId);
		});
		parent.Common.loading(true);
		$.ajax({
			type: "POST",
			url: './webapi00802.json',
			data: {
				'listHousesId': ids.join(',')
			},
			datatype: "json",
			success: function(data) {
				if(typeof data == "string") {
					data = JSON.parse(data);
				}
				if (data.recode == "000000") {
					parent.Common.loading(false);
					parent.Common.dialog({
						type: "success",
						text: data.msg,
						okShow: true,
						cancelShow: false,
						okText: "确定",
						ok: function () {
							getBuilding(1, pageSizeTemp);
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
						ok: function () {
						}
					});
				}
			},
			error: function () {
				parent.Common.ajaxError();
			}
		});
	}
};


map.addEventListener("click", showInfo);
function showInfo(e){
	var pointAction = new BMap.Point(e.point.lng, e.point.lat);
	var marker = new BMap.Marker(pointAction);
	positionX=e.point.lng;
	positionY=e.point.lat;
	if(overlay==1){
		map.addOverlay(marker);
		overlay=0;
		parent.Common.dialog({
			type: "success",
			text: "地理信息已确认，请点击保存！",
			okShow: true,
			cancelShow: false,
			okText: "确定",
			ok: function () {
				marker.setAnimation(BMAP_ANIMATION_BOUNCE);
			}
		});
	}
}
function upImg(){
	$('#imageid').val(websiteId);
	$('#imgcenterid').val(top.userInfo.centerid);
	if (!$("#formUpImg").form("validate")) return;
	$('#formUpImg').form('submit',{
		url: 'webapi10105.do',
		dataType:'json',
		onSubmit: function(){
			var uploadImage=$('#imgfile').val();
			if(null == uploadImage || "" == uploadImage){
				parent.Common.dialog({
					type: "error",
					text: "请选择楼盘图片后进行提交！",
					okShow: true,
					cancelShow: false,
					okText: "确定",
					ok: function () {
					}
				});
				return false;
			}
		},
		success: function(data){
			if(typeof data == "string") {
				data = JSON.parse(data);
			}
			var filePath = data.filePath;
			var url=filePath.replace(/amp;/g,"");

			$('#peview').attr('src',url);
		},
		error :function(){
			parent.Common.ajaxError();
		}
	});
}



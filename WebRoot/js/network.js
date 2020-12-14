/**
 * Created by M on 2016/8/29.
 */

var user = top.userInfo;
// 百度地图API功能
var mapValue=0;
var overlay=0;
var maparr;
var websiteId;
var websiteCode;
var positionX;
var positionY;
var map = new BMap.Map('mapWrap'); 
map.centerAndZoom(top.userInfo.centerName,13);
map.enableScrollWheelZoom();                            
map.addControl(new BMap.NavigationControl()); 
map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT}));
map.addControl(new BMap.OverviewMapControl({isOpen:true, anchor: BMAP_ANCHOR_TOP_RIGHT}));   
map.addControl(new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_HYBRID_MAP]}));

var network = {
    pageSize: 10,
    pager: null,
    spager:null,
    tabler:null,
    stabler: null,
    cacheCityList: null,
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
        page = page,
        rows = rows,
        websiteName = $('#websiteName').val(),
        areaId = $('#areaId').val()=='请选择'?'':$('#areaId').val(),
        serviceTime = $('#serviceTime').val(),
        address = $('#address').val();
        $.ajax({
            type:'POST',
            url:'./webapi10104.json',
            data: { 
                'areaId': areaId, 
                'websiteName': websiteName, 
                'serviceTime': serviceTime, 
                'address': address, 
                'page': page, 
                'rows': rows 
            },
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
           { title:'序号', name:'id' ,width:40, align:'center', renderer: function (val, item, index) {
                        return index+1;
                    }},
           { title:'网点名称', name:'websiteName' ,width:65, align:'center' },
           { title:'所属区域', name:'areaId' ,width:65, align:'center' , renderer: function (val, item, index) {
                        return $('#areaId option[value='+val+']').text();
                    }},
           { title:'网点类型', name:'websiteType' ,width:60, align:'center',renderer: function (val, item, index) {
                        return $('#buiType option[value='+val+']').text();
                    }},
           { title:'业务类型', name:'businessType' ,width:75, align:'center'},
           { title:'联系电话', name:'tel' ,width:110, align:'center'},
           { title:'营业时间', name:'serviceTime' ,width:140, align:'center'},
           { title:'网点地址', name:'address' ,width:150, align:'center'},
           { title:'X坐标', name:'positionX' ,width:70, align:'center'},
           { title:'Y坐标', name:'positionY' ,width:70, align:'center'},
           { title:'是否开通取号', name:'freeuse1' ,width:86, align:'center', renderer: function (val, item, index) {
               var temp = '-';
               if (val == '0') {
                   temp = '否';
               } else if (val == '1') {
                   temp = '是';
               }
               return temp;
           }},
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#networkTable').mmGrid({
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
    add: function () {
        var self = this,
            websiteCode = parent.$("#buiCode").val(),
            websiteName = parent.$("#buiArea").val(),
            areaId = parent.$("#buiName").val(),
            websiteType = parent.$("#buiType").val(),
            businessType = parent.$("#buiBuzType").val();
            address = parent.$("#buiAddr").val(),
            serviceTime = parent.$("#buiContacter").val(),
            tel = parent.$("#buiTel").val(),
            isOpenTakeNumber = parent.$("input[name='isOpen']:checked").val();

        if(websiteCode.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "网点编码不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(websiteName.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "网点名称不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(areaId.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "网点区域不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(websiteType.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "网点类型不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(address.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "网点地址不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(serviceTime.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "营业时间不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(tel.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "联系电话不能为空！",
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
            url: "./webapi10101.json",
            data: { 
                'websiteCode': websiteCode, 
                'websiteName': websiteName, 
                'areaId': areaId, 
                'websiteType': websiteType,
                'businessType' :businessType,
                'address':address,
                'serviceTime':serviceTime,
                'tel':tel,
                'freeuse1': isOpenTakeNumber
            },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData( 1, self.pageSize, true);
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
        parent.Common.popupClose();
    },
    getCityList: function () {
        var self = this;
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./ptl40003Qry.json",
            data: { 'centerid': user.centerid },
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
    del: function () {
        var self = this,
            selected = self.tabler.selectedRows(),
            delIds = [];
        for(var i=0; i<selected.length; i++) {
            delIds.push(selected[i].websiteId);
        }
        $.ajax({
            type: "POST",
            url: "./webapi10102.json",
            data: { 'deletes': delIds.join(",") },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData( 1, self.pageSize, true);
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

        var websiteId =  selectedData[0].websiteId,

            websiteCode = parent.$("#buiCode1").val(),
            websiteName = parent.$("#buiArea1").val(),
            areaId = parent.$("#buiName1").val(),
            websiteType = parent.$("#buiType1").val(),
            businessType = parent.$("#buiBuzType1").val(),
            address = parent.$("#buiAddr1").val(),
            serviceTime = parent.$("#buiContacter1").val(),
            tel = parent.$("#buiTel1").val(),
            isOpenTakeNumber = parent.$("input[name='isOpen']:checked").val();

        parent.Common.popupClose();
        $.ajax({
            type: "POST",
            url: "./webapi10103.json",
            datatype: "json",
            data: { 
                'websiteId':websiteId,
                'areaId': areaId,
                'websiteCode': websiteCode,
                'websiteName': websiteName,
                'businessType': businessType,
                'websiteType':websiteType,
                'tel': tel,
                'serviceTime': serviceTime,
                'address': address,
                'freeuse1': isOpenTakeNumber
                
            },
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
    submitExl: function() {    
        var self = this,
            excelFile=parent.$('#excelfile').text();
        if(null == excelFile || "" == excelFile){
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "error",
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
                    parent.Common.dialog({
                        type: "success",
                        text: '文件检查通过,是否执行批量导入?',
                        okShow: true,
                        cancelShow: true,
                        okText: "确定",
                        ok: function () {
                            var citycode = parent.$('#citycode').val();
                            var cityname = parent.$('#cityname').val();
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
                                url : "./excelMapToDB.do",
                                dataType: 'json',
                                data:arr,
                                success : function(data) {
                                
                                    if (data.recode == SUCCESS_CODE) {
                                        parent.Common.dialog({
                                            type: "success",
                                            text: data.msg,
                                            okShow: true,
                                            cancelShow: false,
                                            okText: "确定",
                                            ok: function () {
                                                self.getTableData( 1, self.pageSize, true);
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


            parent.$("#buiCode1").val(selected[0].websiteCode),
            parent.$("#buiArea1").val(selected[0].websiteName),
            parent.$("#buiName1").val(selected[0].areaId),
            parent.$("#buiType1").val(selected[0].websiteType),
            parent.$("#buiBuzType1").val(selected[0].businessType);
            parent.$("#buiAddr1").val(selected[0].address),
            parent.$("#buiContacter1").val(selected[0].serviceTime),
            parent.$("#buiTel1").val(selected[0].tel);
            parent.$("input[name='isOpen'][value='" + selected[0].freeuse1 + "']").prop("checked", true);
            
        });
        
        $('#orgManage-btn-del').click(function(){
            var selected = $("#networkTable").find("tr.selected");
            if(selected.length < 1) {
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
            parent.Common.dialog({
                type: "warning",
                text: "确定删除选中项？",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.Common.loading(true);
                    self.del();
                }
            });
        });

        //查询
        $('#orgManage-info-goBack').click(function(){
            $(".main").show();
            $(".orgManage-popup-info-container").hide();
        });
        
        //返回
        $('#btnquery').click(function(){
            self.getTableData( 1, self.pageSize, true);
        });

        //显示地图
        $('#orgManage-btn-mapinfo').click(function(){
            var selected = self.tabler.selectedRows();

            if(selected.length < 1) {
                parent.Common.dialog({
                    type: "error",
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
                    type: "error",
                    text: "只能选择一条记录！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });
                return;
            }

            var cityObj = self.cacheCityList.filter(function (item) {
                if(item.centerid == selected[0].centerid) return item;
            });
            $(".main").hide();
            $(".orgManage-popup-info-container").show();

            $('.mapFullAddr').val(selected[0].address);
            $('.mapCity').val(cityObj.length > 0 ? cityObj[0].freeuse1 : "");

            positionX=selected[0].positionX;
            positionY=selected[0].positionY;

            mapValue=1;
            overlay=1;      
            map.reset();
            map.clearOverlays();
            websiteId=selected[0].websiteId;
            websiteCode=selected[0].websiteCode;
            url = "/YBMAP/webapi10103.json"; 
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
                selectMap();
            } 

            $('#imgfile').on('change', function( e ){

                var name = e.currentTarget.files[0].name;
                name.length>15?name=name.substr(0,20)+'...':name=name;
                $('#imgname').text( name );
                console.log(name.length)

            });

        });

        //批量导入
        $('#orgManage-btn-import').click(function(){
            $('#citycode').val(user.centerid);
            var createHTML = $(".orgManage-popup-edit-container5").html();
            parent.Common.popupShow(createHTML);

            parent.$('#file').on('change', function( e ){

                var name = e.currentTarget.files[0].name;
                parent.$('#excelfile').text( name );

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
            var createHTML = $(".orgManage-popup-edit-container2").html();
            parent.Common.popupShow(createHTML);
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
                    $('.mapFullAddr').val(''),
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
            {name : "websiteId",value : websiteId},
            {name : "websiteCode",value : websiteCode},
            {name : "positionX",value : positionX},
            {name : "positionY",value : positionY}  
        ];
        
        $.ajax( {
            type : "POST",
            url : './webapi10103.json',
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
                        self.getTableData( 1, self.pageSize, true);
                    }
                });
            },
            error :function(){
                parent.Common.ajaxError();
            }
        });
    }
};





$(function(){
    
    $.ajax({
        type:'POST',
        url:'./page10104.json',
        data:{},
        datatype:'json',
        success:function(data){
            if(typeof data == 'string'){
                data = JSON.parse(data);
            }
            if(data.recode == '000000'){

                // 所在区域
                var customerOptions = '<option value="">请选择</option>';
                for(var i = 0;i<data.mi202list.length;i++){
                    customerOptions += '<option value="'+data.mi202list[i].areaId+'">'+data.mi202list[i].areaName+'</option>'
                }
                $('#areaId').html(customerOptions);
                $('#buiName,#buiName1').html(customerOptions);

                // 网点类型
                var websiteType = '<option value="">请选择</option>';
                for(var i = 0;i<data.mi007list.length;i++){
                    websiteType += '<option value="'+data.mi007list[i].itemid+'">'+data.mi007list[i].itemval+'</option>'
                }
                $('#buiType,#buiType1').html(websiteType);
                
            }
        },
        error:function(){
            parent.Common.ajaxError();
        }
    });
    network.createPager();
    network.getCityList();
    network.btnClick();

});
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
            type: "error",
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
    $('#imgcenterid').val(user.centerid);
    if (!$("#formUpImg").form("validate")) return;
    $('#formUpImg').form('submit',{  
        url: 'webapi10105.do',
        dataType:'json',        
        onSubmit: function(){              
            var uploadImage=$('#imgfile').val();
            if(null == uploadImage || "" == uploadImage){
                parent.Common.dialog({
                    type: "error",
                    text: "请选择网点图片后进行提交！",
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










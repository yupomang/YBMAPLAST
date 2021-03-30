/**
 * Created by Administrator on 2016/10/18.
 */
var holiday = {
	init: function(){
		var self = this;
		var yearStr;
		var monthStr;
		var Today = new Date();
		$('#year').val(Today.getFullYear());
		yearStr=$('#year').val();
		monthStr=((Today .getMonth()+1)<10?"0":"")+(Today .getMonth()+1);
		$("#date-query-btn").off().on("click", function () {
			yearStr=$('#year').val();
			//校验
			if (!checkyear(yearStr+"-01-01")){
				parent.Common.dialog({
					type: "warning",
					text: '输入正确的年度！',
					okShow: true,
					cancelShow: false,
					okText: "确定",
					ok: function () {
					}
				});
				return;
			}
			self.getYearCalendar(yearStr);

		});
		$("#save").off().on("click", function () {
			self.save();
		});
	},
	getYearCalendar:function (yearStr,monthStr) {
		var self = this,
			yearStr = yearStr;
		$.ajax( {
			type : "POST",
			url : "./webapi62701.json",
			dataType: "json",
			data: { 'year': yearStr },
			beforeSend: function(){
				parent.Common.loading(true);
			},
			complete: function(){
				parent.Common.loading(false);
			},
			success : function(data) {
				var Today = new Date();
				if(!monthStr){
					if(yearStr==Today.getFullYear()){
						monthStr=((Today .getMonth()+1)<10?"0":"")+(Today .getMonth()+1)
					}else{
						monthStr="01";
					}
				}
				self.showCalendar(yearStr,monthStr);
				$(".date-right").show();
			},
			error :function(){
				parent.Common.ajaxError();
			}
		});
	},
	showCalendar: function (yearStr, monthStr) {
		var self = this;
		$('#fullCalendar').attr("style","width:600px;height:300px");
		$('#fullCalendar').fullCalendar({
			width : 600,
			height : 380,
			fit : false,
			border : true,
			firstDay : 0,
			url : "./webapi62703.json",
			para:{"yearmonth":yearStr+"-"+(monthStr.length>1?"":"0")+monthStr},
			year:yearStr,
			month:monthStr,
			current:new Date(),
			onSelect : function (date, target) {
				var abbr=target.abbr.split(',');
				if(abbr[0]==yearStr){
					$('#date').val(abbr[0]+"-"+(abbr[1].length>1?"":"0")+abbr[1]+"-"+(abbr[2].length>1?"":"0")+abbr[2]);
					$('#isRest-'+abbr[3]).prop("checked",true);
					$('#remarks').val(abbr[4]=="undefined"?"":abbr[4]);
					yearStr=abbr[0];
					monthStr=(abbr[1].length>1?"":"0")+abbr[1];
				}else{
					self.resetForm();
				}
				$('#year').val(yearStr);
			},
			onChange : function (year, month) {
				yearStr=year;
				monthStr=(month.length>1?"":"0")+month;
				self.resetForm();
				$('#year').val(yearStr);
			}
		});
	},
	resetForm: function () {
		$("#date").val('');
		$('#isRest-0').prop("checked",true);
		$("#remarks").val('');
	},
	save: function () {
		var self = this;
		var festivaldate = $("#date").val(),
			festivalflag = $("input[name=isRest]:checked").val(),
			freeuse2 = $("#remarks").val();
		var festivalArr = festivaldate.split("-"),
			yearStr = festivalArr[0],
			monthStr = festivalArr[1];
		if(festivaldate.length < 1) {
			parent.Common.dialog({
				type: "warning",
				text: '请选择日期！',
				okShow: true,
				cancelShow: false,
				okText: "确定",
				ok: function () {
				}
			});
			return;
		}
		$.ajax({
			type:'POST',
			url:'./webapi62704.json',
			data:{
				'festivaldate': festivaldate,
				'festivalflag': festivalflag,
				'freeuse2': freeuse2
			},
			datatype:'json',
			success:function(data){
				if(typeof data == 'string'){
					data = JSON.parse(data);
				}
				if(data.recode == '000000'){
					parent.Common.dialog({
						type: "success",
						text: data.msg,
						okShow: true,
						cancelShow: false,
						okText: "确定",
						ok: function () {
							self.getYearCalendar(yearStr,monthStr);
						}
					});
				} else {
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
	}
};
function checkyear(year){
	var reg=/^([1-2]\d{3})[\/|\-](0?[1-9]|10|11|12)[\/|\-]([1-2]?[0-9]|0[1-9]|30|31)$/ig;
	return reg.test(year);
}
function auto(flag){
	var yearStr=$('#year').val();
	if (!checkyear(yearStr+"-01-01")){
		parent.Common.dialog({
			type: "warning",
			text: '输入正确的年度',
			okShow: true,
			cancelShow: false,
			okText: "确定",
			ok: function () {
			}
		});
		return;
	}
	if(yearStr!=""){
		if(flag=="add"){
			$('#year').val(parseFloat(yearStr)+1);
		}
		if(flag=="sub"){
			$('#year').val(parseFloat(yearStr)-1);
		}
	}
}
holiday.init();
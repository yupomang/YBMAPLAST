<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport"
        content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>贷款额度试算</title>
		<link href="css/mui.min.css" rel="stylesheet"/>
		<link href="css/reset.css" rel="stylesheet"/>
		<link href="css/button.css" rel="stylesheet"/>
		<link href="css/dkedss.css" rel="stylesheet"/>
		<script type="text/javascript" src="js/jquery-2.0.3.min.js" ></script>
	</head>
	<body>
<section>
<div class="d_title">主申请人信息</div>
<ul class="mui-table-view" id="list_area">
	<li class="mui-table-view-cell">
		<span>公积金缴存余额</span>
		<div class="mui-pull-right"><input id="m_ye" type="number" placeholder="请输入余额" maxlength="8" onkeyup="value=value.replace(/[^\d]/g,&#39;&#39;)" /><span>元</span></div>
	<li class="mui-table-view-cell">
		<span>最近6个月平均工资基数</span>
		<div class="mui-pull-right" style="width: 35%;"><input id="m_js" type="tel" placeholder="请输入" maxlength="8" onkeyup="value=value.replace(/[^\d]/g,&#39;&#39;)" style="width: 70%;"/><span>元</span></div>
	</li>
	<li class="mui-table-view-cell">
		<a href="#" class="mui-navigate-right">
		<span>连续缴存时间</span>
		<div class="mui-pull-right" id="lxjc" >
			<select id="m_jcsj">
				<option value="0.3">半年≤连续缴存时间≤1年</option>
				<option value="0.4">1年＜连续缴存时间≤3年</option>
				<option value="0.6">3年＜连续缴存时间≤5年</option>
				<option value="0.8">5年＜连续缴存时间≤7年</option>
				<option value="1">连续缴存时间&gt;7年</option>
				<option value="0">连续缴存时间&lt;半年</option>
			</select>
		</div>
		</a>
	</li>
	<li class="mui-table-view-cell" >
		<a href="#" class="mui-navigate-right">
			<span>出生年</span>
			<div class="mui-pull-right" id="csn" ><select id="s_year" name="year" style="width: 80%;"></select></div>
		</a>
	</li>
	<li class="mui-table-view-cell">
		<span>性别</span>
		<div class="mui-pull-right">
			<input name="sex1" type="radio" value="1" checked/>男&nbsp;&nbsp;&nbsp;&nbsp;
			<input name="sex1" type="radio" value="0"  />女
		</div>
	</li>
</ul>
<div class="d_title">配偶信息<span>(选填)</span></div>
<ul class="mui-table-view">
	<li class="mui-table-view-cell">
		<span>公积金缴存余额</span>
		<div class="mui-pull-right"><input id="o_ye" type="number" placeholder="请输入余额" maxlength="8" onkeyup="value=value.replace(/[^\d]/g,&#39;&#39;)"/><span>元</span></div>
		
	</li>
	<li class="mui-table-view-cell">
		<span>最近6个月平均工资基数</span>
		<div class="mui-pull-right" style="width: 35%;"><input id="o_js" type="tel" placeholder="请输入" maxlength="8" onkeyup="value=value.replace(/[^\d]/g,&#39;&#39;)" style="width: 70%;"/><span>元</span></div>
		
	</li>
	<li class="mui-table-view-cell">
		<a href="#" class="mui-navigate-right">
		<span>连续缴存时间</span>
		<div class="mui-pull-right" id="lxjc" >
			<select id="o_jcsj">
				<option value="0.3">半年≤连续缴存时间≤1年</option>
				<option value="0.4">1年＜连续缴存时间≤3年</option>
				<option value="0.6">3年＜连续缴存时间≤5年</option>
				<option value="0.8">5年＜连续缴存时间≤7年</option>
				<option value="1">连续缴存时间&gt;7年</option>
				<option value="0">连续缴存时间&lt;半年</option>
			</select>
		</div>
		</a>
	</li>
</ul>
<div class="d_title">其他还款人信息<span>(选填)</span></div>
<ul class="mui-table-view">
	<li class="mui-table-view-cell">
		<span>公积金缴存余额</span>
		<div class="mui-pull-right"><input id="t_ye" type="number" placeholder="请输入余额" maxlength="8" onkeyup="value=value.replace(/[^\d]/g,&#39;&#39;)"/><span>元</span></div>
		
	</li>
	<li class="mui-table-view-cell">
		<span>最近6个月平均工资基数</span>
		<div class="mui-pull-right" style="width: 35%;"><input id="t_js" type="tel" placeholder="请输入" maxlength="8" onkeyup="value=value.replace(/[^\d]/g,&#39;&#39;)" style="width: 70%;"/><span>元</span></div>
		
	</li>
	<li class="mui-table-view-cell">
		<a href="#" class="mui-navigate-right">
		<span>连续缴存时间</span>
		<div class="mui-pull-right" id="lxjc" >
			<select id="t_jcsj">
				<option value="0.3">半年≤连续缴存时间≤1年</option>
				<option value="0.4">1年＜连续缴存时间≤3年</option>
				<option value="0.6">3年＜连续缴存时间≤5年</option>
				<option value="0.8">5年＜连续缴存时间≤7年</option>
				<option value="1">连续缴存时间&gt;7年</option>
				<option value="0">连续缴存时间&lt;半年</option>
			</select>
		</div>
		</a>
	</li>
	<li class="mui-table-view-cell" >
		<a href="#" class="mui-navigate-right">
			<span>出生年</span>
			<div class="mui-pull-right" id="csn" ><select id="s_year_other" name="year" style="width: 80%;"></select></div>
		</a>
	</li>
	<li class="mui-table-view-cell">
		<span>性别</span>
		<div class="mui-pull-right">
			<input id="" name="sex3" type="radio" value="1"  checked/>男&nbsp;&nbsp;&nbsp;&nbsp;
			<input id="" name="sex3" type="radio" value="0"  />女
			
		</div>
	</li>
</ul>
<div class="d_title">购房信息</div>
<ul class="mui-table-view">
	<li class="mui-table-view-cell">
		<a href="#" class="mui-navigate-right">
			<span>计划贷款年限</span>
			<div class="mui-pull-right" id="csn"><select id="s_year_info"></select></div>
		</a>
	</li>
	<li class="mui-table-view-cell">
		<a href="#" class="mui-navigate-right">
		<span>计划购房区域</span>
		<div class="mui-pull-right" style="width: 69%;">
			<select id="ddlFWAreaList">
				<option selected="selected" value="1">历下/市中/槐荫/天桥/历城/长清/高新</option>
				<option value="0">章丘/平阴/济阳/商河</option>
			</select>
		</div>
		</a>
	</li>
</ul>
<div class="btn-area">
	<button id="b_count" type="button" class="mui-btn">计算</button>
</div>
</section>
<section id="resultArea">
<div class="d_title">试算结果</div>
<ul class="mui-table-view">
	<li class="mui-table-view-cell">
		<span>可贷金额：<span id="r_kdje"></span>（元）</span>
	</li>
	<li class="mui-table-view-cell">
		<span>可贷年限：<span id="r_kdnx"></span>（年）</span>
	</li>
</ul>
</section>
<script>
$(function(){
	$("#resultArea").hide();
	var sYear = (new Date()).getFullYear()-65;
	var eYear = (new Date()).getFullYear();
	for(var i = sYear; i <= eYear; i++){
		if(i == 1985){
			$("#s_year").append("<option  selected=\"selected\" value=\"" + i + "\">" + i + "年" + "</option>");	
			$("#s_year_other").append("<option  selected=\"selected\" value=\"" + i + "\">" + i + "年" + "</option>");	
		}else{
			$("#s_year").append("<option  value=\"" + i + "\">" + i + "年" + "</option>");	
			$("#s_year_other").append("<option  value=\"" + i + "\">" + i + "年" + "</option>");	
		}
	}
	for(var i = 1; i <= 30; i++){
		if(i == 30){
			$("#s_year_info").append("<option  selected=\"selected\" value=\"" + i + "\">" + i + "年" + "</option>");	
		}else{
			$("#s_year_info").append("<option  value=\"" + i + "\">" + i + "年" + "</option>");	
		}
	}
	$("#b_count").click(function(){
		//主申请人信息
		var _m_ye = $("#m_ye").val();
		var _m_js = $("#m_js").val();
		var _m_jcsj = $("#m_jcsj").val();
		var _m_year = $("#s_year").val();
		var _m_sex1 = $("input[name='sex1']:checked").val();
		
//-------------------判断开始---------------------------------------------------------------------------		
		if(!_m_ye){
			alert("请输入主申请人公积金缴存余额");
			$("#m_ye").focus();
			return false;
		}
		if(! _m_js){
			alert("请输入主申请人平均工资基数");
			$("#m_ye").focus();
			return false;
		}
		if(_m_jcsj <= 0){
			alert("主申请人连续缴存时间小于半年,不符合贷款条件");
			$("#m_ye").focus();
			return false;
		}

		//购房信息
		var _g_year = $("#s_year_info").val();
		var _g_area = $("#ddlFWAreaList").val();

		//贷款期限判断
//		住房公积金贷款最长期限不超过30年，贷款年限加借款人年龄不得超过65，即可贷款年限<出生年+65(男)-当前年，可贷款年限<出生年+60(女)-当前年。
		var _dkxq = eYear - _m_year;

		if(_m_sex1 == 1){
			if(_dkxq > 65){
				alert("贷款年限加借款人年龄不得超过65(男)");
				return false;
			}
		}else{
			if(_dkxq > 60){
			alert("贷款年限加借款人年龄不得超过60(女)");
			return false;
			}
		}
		//配偶信息
		var _o_ye = $("#o_ye").val();
		var _o_js = $("#o_js").val();
		var _o_jcsj = $("#o_jcsj").val();
		var _fg = false;

		if(_o_ye=="" && _o_js==""){
			_fg = true;
		}
		if(_o_ye.length > 0 && _o_js.length > 0){
			_fg = true;
		}
		if(!_fg){
			alert("请将配偶信息补充完整");
			$("#o_ye").focus();
			return false;
		}
		if(_o_jcsj <= 0){
			alert("配偶连续缴存时间小于半年,不符合贷款条件");
			$("#o_jcsj").focus();
			return false;
		}
		//除配偶外的其他共同还款人信息
		var _t_ye = $("#t_ye").val();
		var _t_js = $("#t_js").val();
		var _t_jcsj = $("#t_jcsj").val();
		var _t_year = $("#s_year_other").val();
		var _t_sex3 = $("input[name='sex3']:checked").val();
		var _tfg = false;
		
		if(_t_ye=="" && _t_js==""){
			_tfg = true;
		}
		if(_t_ye.length > 0 && _t_js.length > 0){
			_tfg = true;
		}
		if(!_tfg){
			alert("请将其他还款人信息补充完整");
			$("#t_ye").focus();
			return false;
		}
		if(_t_jcsj <= 0){
			alert("其他还款人连续缴存时间小于半年,不符合贷款条件");
			$("#t_jcsj").focus();
			return false;
		}
		//  除配偶外的共同还款人以及担保人的年龄加上申请贷款的年限，男性不得超过65，女性不得超过60。
		var _t_dkxq = parseInt(eYear - _t_year) + parseInt(_g_year);

		/* if(_t_sex3 == 1){
			if(_t_dkxq > 65){
				alert("贷款年限加其他还款人的年龄不得超过65(男)");
				return false;
			}
		}else{
			if(_t_dkxq > 60){
				alert("贷款年限加其他还款人的年龄不得超过60(女)");
				return false;
			}
		} */
//-------------------判断结束---------------------------------------------------------------------------
		var _dke = 0;
		var _mian_kded = 0;
		var _totle_kded = 0;
		var _kd_year = 0;
		//公式一：公积金贷款额度=借款人及参贷人公积金账户储存余额×15。
		// 公式二：公积金可贷额度=借款人可贷额度+共同还款人可贷额度
		//借款人或共同还款人公积金可贷额度=本人公积金月缴存基数（最近6个月平均数）×12×贷款年限×0.6×本人连续缴存时间系数
		//可贷金额的计算
		if( _o_ye=="" && _t_ye==""){//只有主申请人
			_mian_kded = _m_js*12*_g_year*0.6*_m_jcsj;
			_dke = _m_ye*15;
			if(_dke >= _mian_kded){
				_totle_kded = _dke;
			}else{
				_totle_kded = _mian_kded;
			}			
			if(_totle_kded > 400000){
				_totle_kded = 400000;
			}
			if(_g_area == 0 &&_totle_kded > 350000){
				_totle_kded = 350000;
			}			
		}
		if( _o_ye!="" && _t_ye==""){//主申请人+配偶
			_dke = _m_ye *15 + _o_ye *15;
			_mian_kded = _m_js*12*_g_year*0.6*_m_jcsj + _o_js*12*_g_year*0.6*_o_jcsj;
			if(_dke >= _mian_kded){
				_totle_kded = _dke;
			}else{
				_totle_kded = _mian_kded;
			}
			if(_totle_kded > 700000){
				_totle_kded = 700000;
			}
			if(_g_area == 0 &&_totle_kded > 600000){
				_totle_kded = 600000;
			}
		}
		if( _o_ye=="" && _t_ye!=""){//主申请人+其他还款人
			_dke = _m_ye *15 + _t_ye *15;
			_mian_kded = _m_js*12*_g_year*0.6*_m_jcsj + _t_js*12*_g_year*0.6*_t_jcsj;
			if(_dke >= _mian_kded){
				_totle_kded = _dke;
			}else{
				_totle_kded = _mian_kded;
			}
			if(_totle_kded > 700000){
				_totle_kded = 700000;
			}
			if(_g_area == 0 &&_totle_kded > 600000){
				_totle_kded = 600000;
			}
		}
		if( _o_ye!="" && _t_ye!=""){//主申请人+配偶+其他还款人
			_dke = _m_ye *15 + _o_ye *15 + _t_ye *15;
			_mian_kded = _m_js*12*_g_year*0.6*_m_jcsj + _o_js*12*_g_year*0.6*_o_jcsj + _t_js*12*_g_year*0.6*_t_jcsj;
			if(_dke >= _mian_kded){
				_totle_kded = _dke;
			}else{
				_totle_kded = _mian_kded;
			}
			if(_totle_kded > 700000){
				_totle_kded = 700000;
			}
			if(_g_area == 0 &&_totle_kded > 600000){
				_totle_kded = 600000;
			}
		}
		//可贷年限的计算
		//可贷款年限<出生年+65(男)-当前年，可贷款年限<出生年+60(女)-当前年
		if(_t_ye!="" && _t_year > _m_year){
			if(_t_sex3 == 1){
				_kd_year = (parseInt(_t_year) + 65) - parseInt(eYear);
			}else{
				_kd_year = (parseInt(_t_year) + 60) - parseInt(eYear);
			}
		}else{
			if(_m_sex1 == 1){
				_kd_year = (parseInt(_m_year) + 65) - parseInt(eYear);
			}else{
				_kd_year = (parseInt(_m_year) + 60) - parseInt(eYear);
			}
		}
		if(_kd_year>30){
			_kd_year = 30;
		}
		if(_totle_kded < 100000){
			_totle_kded = 100000;
		}
		var hun = _totle_kded.toString().substr(_totle_kded.toString().length-3,3);
		parseInt(hun)<500?_totle_kded=_totle_kded-hun:_totle_kded=parseInt(_totle_kded-hun)+1000;
		$("#resultArea").show();
		$("#r_kdje").html(_totle_kded);
		$("#r_kdnx").html(_kd_year);
		$(window).scrollTop($(document).height()+100);
	})
});
</script>
	</body>
</html>

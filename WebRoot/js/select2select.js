function showBanks (){
	var leftSel = $('#selectLeft');
	var rightSel = $('#selectRight');
	

	$("#moveToRight").bind("click",function(){		
		leftSel.find("option:selected").each(function(){
			$(this).remove().appendTo(rightSel.find('select'));
		});
	});
	$("#moveToLeft").bind("click",function(){		
		rightSel.find("option:selected").each(function(){
			$(this).remove().appendTo(leftSel.find('select'));
		});
	});
	leftSel.dblclick(function(){
		$(this).find("option:selected").each(function(){
			$(this).remove().appendTo(rightSel.find('select'));
		});
	});
	rightSel.dblclick(function(){
		$(this).find("option:selected").each(function(){
			$(this).remove().appendTo(leftSel.find('select'));
		});
	});
}
function searchList(strValue) {
	var count = 0;
	if (strValue != "") {
		$('#selectLeft option').each(function(i) {
			var contentValue = $(this).text();
			if (
				(contentValue.indexOf(strValue.toLowerCase()) < 0) 
				&& (contentValue.indexOf(strValue.toUpperCase()) < 0)
				) {
				$(this).css("display", "none");
				count++;
			} else {
				$(this).css("display", "block");
				$(this).attr('selected','selected')
			}
		});
	} else {
		showAllOptions();
	}
}
function checkThisText(strValue){
	if(strValue == ''){
		showAllOptions();
	}
}
function showAllOptions(){
	$('#selectLeft option').each(function(i) {
		$(this).css("display", "block");
	});
}



function getBank (){
	var options = [];
	$('#selectRight option').each(function(){
		var texts = $(this).text();
		options.push(texts);
	});
	var banks = options.join('|');
	window.frames['main'].getData(banks);
	
}













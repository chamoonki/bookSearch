function ajaxHelper(paramObj, preCallback, sucCallback, errCallback, compleCallback){
	$.ajax({
		url : paramObj.url,
		method: paramObj.method,
		data : paramObj.data,
		dataType: "json",
		beforeSend: preCallback,
		success: sucCallback,
		error: errCallback,
		complete: compleCallback
	});
}

function test (){
	console.log('#!#!#!#!!!!!!!!');
}
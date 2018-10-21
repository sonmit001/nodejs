//정규식
var vaildId = /^[a-zA-Z0-9]{4,12}$/;
var vaildName = /^[가-힣]{2,6}$/;
var vaildNick = /^[0-9a-zA-Z가-힣]{2,12}$/;
var vaildPhone = /^\d{2,3}-\d{3,4}-\d{4}$/;
var vaildPasswd = /^(((?=.*[a-z])((?=.*[A-Z])|(?=.*[0-9])|(?=.*[\W\_])))|((?=.*[A-Z])((?=.*[\W\_])|(?=.*[0-9])))|((?=.*[0-9])(?=.*[\W\_])))[\S]{6,12}$/;


//input 타입에 속성 넣기
function inputRole(id, kind, value ){
	if(kind == 'maxlength'){
		$('#'+id).attr('maxlength',value);
	}else if(kind =='prevent'){
		if(value == 'space'){
			$('#'+id).bind("keypress",function(e){
				var key = 'which' in e ? e.which : e.keyCode;
				if(key == 13 || key == 32){
					e.preventDefault();
				};
			})
		}
	}
}
//rsa 키 생성하기
function getPublicKey(){
	var json;
	console.log("getpublickey ajax");
	$.ajax({
		async: false,
		type:'POST',
		dataType: "json",
		url: "getRsaKey.do",
		success: function(data){
			json = data;
			console.log("util.js");
			console.log(data);
		},
		error: function (request, status, error) {
			alert("ErrorCode["+request.status+"]: "+error);
		}
	});
	return json;
}


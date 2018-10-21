<%@ page contentType = "text/html;charset=utf-8" %>
<html>
<head>
<title>sign up</title>
<%@include file="/views/common/preset.jsp" %>
<script type="text/javascript" src="//img.x1.co.kr/common/js/rsa.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#pwd_on').hide();
	inputRole('id_i','maxlength','14');
	inputRole('s_passwd_i','maxlength','14');
	inputRole('passwd_ck_i','maxlength','14');
	inputRole('phone_i','maxlength','14');
	inputRole('name_i','maxlength','19');
	inputRole('nickname_i','maxlength','19');
	inputRole('id_i','prevent','space');
	inputRole('s_passwd_i','prevent','space');
	inputRole('passwd_ck_i','prevent','space');
	inputRole('phone_i','prevent','space');
	inputRole('name_i','prevent','space');
	inputRole('nickname_i','prevent','space');
});

function chkDupId() {
	var chkid = $('#id_i').val();
	console.log(chkid);
	console.log(vaildId);
	if(vaildId.test(chkid) != true){
		alert("아이디 입력이 잘못되었습니다");
		$('#id_i').focus();
		return;
	}
	$.ajax({
		type: 'post',
		url: 'chkId.do',
		data: {chkId : chkid},
		success: function(result){
			if(result>0){
				alert("이미 사용 중인 아이디 입니다.");
			}else{
				console.log(result);
				alert("사용 가능 합니다.");
				$('#id').val(chkid);
			}
		}
	})
}
//닉네임 중복 확인
function chknickname(){
	var nickname = $('#nickname_i').val();
	
	if(vaildNick.test(nickname) !=true){
		alert("닉네임 입력이 잘못되었습니다.")
		return;
	}
	$.ajax({
		type: 'post',
		url: 'chkNicName.do',
		data:{'nickname': nickname},
		success:function(result){
			if(result>0){
				alert("이미 사용 중인 닉네임 입니다.")
			}else{
				alert("사용 가능 합니다.");
				$('#nickname').val(nickname);
			}
		}
	})
}
//비밀번호 유효성 판단
function checkpwd(){
	var pwd = $('#s_passwd_i').val();
	
	if(vaildPasswd.test(pwd) !=true){
		$('#pwd_off').show();
		$('#pwd_on').hide();
		return;
	}
	$('#pwd_off').hide();
	$('#pwd_on').show();
}
//비밀번호 동일성 확인
function checkpwdsame(){
	var pwd = $('#s_passwd_i').val();
	var pwdchk = $('#passwd_ck_i').val();
	
	if(pwd != pwdchk){
		$('#pwdck_off').show();
		return;
	}
	$('#pwdck_off').hide();
}
//가입하기 submit
function submit() {
	var id_i = $('#id_i').val();
	var pwd_i_1 = $('#s_passwd_i').val();
	var pwd_i_2 = $('#passwd_ck_i').val();
	var name_i = $('#name_i').val();
	var nickname_i = $('#nickname_i').val();
	var phone_i = $('#phone_i').val();
	var id = $('#id').val();
	var nickname = $('#nickname').val();
	
  	if(vaildId.test(id_i) != true){
		alert("아이디 입력이 잘못되었습니다");
		$('#id_i').focus();
		return;
	}
	
	if(id != id_i){
		alert("아이디 중복여부를 확인해 주세요");
		$('#id_i').focus();
		return;
	}
	
	if(vaildPasswd.test(pwd_i_1) != true){
		alert("비밀번호 입력이 잘못되었습니다");
		return;
	}
	
	if(pwd_i_1 != pwd_i_2){
		alert("비밀번호가 동일하지 않습니다")
		$('#passwd_ck_i').focus();
		return;
	}
	
	if(vaildName.test(name_i) != true){
		alert("성명 입력이 잘못 되었습니다");
		$('#name_i').focus();
		return;
	}
	
	if(vaildNick.test(nickname_i) != true){
		alert('닉네임 입력이 잘못되었습니다');
		$('#nickname_i').focus();
		return;
	}
	
	if(nickname_i != nickname){
		alert("닉네임 중복 확인해 주세요");
		$('#nickname_i').focus();
		return;
	}
	
	if(vaildPhone.test(phone_i) != true){
		alert('핸드폰을 - 을 넣어 작성해 주세요');
		$('#phone_i').focus();
		return;
	}  
	
	var rsa = new RSAKey();
	var json = getPublicKey();
	rsa.setPublic(json.publicKeyModulus, json.publicKeyExponent);
	
	id_i = rsa.encrypt(id_i);
	nickname_i = rsa.encrypt(nickname_i);
	pwd_i_1 = rsa.encrypt(pwd_i_1);
	name_i = rsa.encrypt(name_i);
	phone_i = rsa.encrypt(phone_i);
	$('#id').val(id_i);
	$('#nickname').val(nickname_i);
	$('#s_passwd').val(pwd_i_1);
	$('#name').val(name_i);
	$('#phone').val(phone_i); 
	$('#signUp').submit();
	
}

</script>
</head>
<body>
<%@include file="/views/common/nav.jsp" %>
<div class="container">
	<table class="table member">
		<colgroup>
			<col width="30%">
			<col width="40%">
			<col width="30%">
		</colgroup>
		<tr>
			<td class="info" style="vertical-align: middle;">아이디 : </td>
			<td><input type="text" id="id_i" name="id_i" class="form-control" placeholder="아이디 최대 14자"></td>
			<td><button type="button" onclick="chkDupId()" class="btn btn-info">중복확인</button></td>
		</tr>
		<tr>
			<td class="info" style="vertical-align: middle;">비밀번호 : </td>
			<td><input id="s_passwd_i" type="password" name="s_passwd_i" class="form-control" onkeyup="checkpwd()"></td>
			<td><span id="pwd_off">영어 대/소문자, 숫자, 특수문자 중 2가지 이상 조합 6자리~12자(띄어쓰기 불가)</span>
				<span id="pwd_on">사용 가능합니다.</span>
			</td>
		</tr>
		<tr>
			<td class="info" style="vertical-align: middle;">비밀번호 확인 :</td>
			<td><input id="passwd_ck_i" type="password" class="form-control" onkeyup="checkpwdsame()"></td>
			<td><span id="pwdck_off">비밀번호가 동일하지 않습니다.</span></td>
		</tr>
		<tr>
			<td class="info" style="vertical-align: middle;">이름 : </td>
			<td><input id="name_i" name="name_i" type="text" class="form-control" placeholder="이름 최소2글자"></td>
			<td><span></span></td>
		</tr>
		<tr>
			<td class="info" style="vertical-align: middle;">닉네임 : </td>
			<td><input id="nickname_i" name="nickname_i" type="text" class="form-control" placeholder="한글,영문,특수문자  최대 19자"></td>
			<td><button type="button" class="btn btn-info" onclick="chknickname()">중복확인</button></td>
		</tr>
		<tr>
			<td class="info" style="vertical-align: middle;">핸드폰 : </td>
			<td><input id="phone_i" name="phone_i" type="text" class="form-control" placeholder="ex) 010-1234-1234"></td>
			<td><span>'-'을 포함하여 주세요</span></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td><button class="btn btn-info" onclick="submit()">가입하기</button>
			<button class="btn btn-danger" type="button" onclick="location.href='home.do'">취소</button>
			</td>
			<td> </td>
		</tr>
	</table>
	
	<form id="signUp" method="post" action="signUp.do">
		<input type="hidden" name="id" id="id"/>
		<input type="hidden" name="s_passwd" id="s_passwd">
		<input type="hidden" name="name" id="name">
		<input type="hidden" name="nickname" id="nickname">
		<input type="hidden" name="phone" id="phone">
	</form>
</div>
</body>
</html>
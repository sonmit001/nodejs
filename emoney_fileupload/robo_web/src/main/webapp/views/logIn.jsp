<%@ page contentType = "text/html;charset=utf-8" %>
<html>
<head>
<title>login</title>
<%@include file="/views/common/preset.jsp" %>
<script type="text/javascript" src="//img.x1.co.kr/common/js/rsa.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	inputRole('id_i','maxlength','14');
});
function submit() {
	var id = $('#id_i').val();
	var pwd = $('#s_passwd_i').val();
	
	if(id == ''){
		alert('아이디를 입력해 주세요');
		return;
	}
	
	if(pwd == ''){
		alert('비밀번호를 입력해 주세요');
		return;
	}
	
	var rsa = new RSAKey();
	
	var json = getPublicKey();
	console.log(json);
	rsa.setPublic(json.publicKeyModulus, json.publicKeyExponent);
	
	pwd = rsa.encrypt(pwd);
	$('#id').val(id);
	$('#s_passwd').val(pwd);
	console.log("submit");
	$('#loginForm').submit();
		
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
			<td><input type="text" id="id_i" name="id_i"   class="form-control"></td>
		</tr>
		<tr>
			<td class="info" style="vertical-align: middle;">비밀번호 : </td>
			<td><input id="s_passwd_i" type="password" name="s_passwd_i" class="form-control" ></td>
		</tr>
		<tr>
			<td></td>
			<td><button class="btn btn-info" type="button" onclick="submit()">로그인</button>
				<button class="btn btn-danger" type="button" onclick="location.href='home.do'">취소</button>
			</td>
		</tr>
	</table>
	<form id="loginForm" action="logIn.do" method="post">
		<input type="hidden" name="id" id="id" />
		<input type="hidden" name="s_passwd" id="s_passwd" />
	</form>
</div>
</body>
</html>
<%@page import="net.sf.json.processors.JsonBeanProcessor"%>
<%@ page contentType = "text/html;charset=utf-8" %>
<html>
<head>
<title>View</title>
<%@include file="/views/common/preset.jsp" %>
<script type="text/javascript" src="/common/editor/js/HuskyEZCreator.js" charset="utf-8"></script>
<script src="/common/js/vendor/jquery.ui.widget.js"></script>
<script src="/common/js/jquery.iframe-transport.js"></script>
<script src="/common/js/jquery.fileupload.js"></script>
<script src="/common/js/jquery.fileupload-ui.js"></script>
<link href="/common/css/dropzone.css" type="text/css" rel="stylesheet" />
<script src="/common/js/myuploadfunction.js"></script>
<script type="text/javascript">
function sendWrite(){
	var obj = $("#sendwrite");
	if( obj.find("#title").val() == "" ) {
		alert("제목을 입력해주세요");
		obj.find("#title").focus();
		return;
	}
	oEditors.getById["contents"].exec("UPDATE_CONTENTS_FIELD", []);	// 에디터의 내용이 textarea에 적용됩니다.
	if( obj.find("#contents").val() == "" || obj.find("#contents").val() == "<p>&nbsp;</p>" ) {
		alert("내용을 입력해 주세요");
		return;
	}
	obj.submit();
	console.log($('#uploaded-files tr').length);
	var nofileboard = $('#uploaded-files tr').length;
	if(nofileboard == 1){
		$('#onlytext').val(1);
		//obj.submit();
	}
}

function delete_tr(a){
	console.log(arguments[0]);
	var hidden_id = arguments[0];
	console.log($("[id='" + hidden_id + "']"));
	$("[id='" + hidden_id + "']").remove();
	$("[class='" + hidden_id + "']").remove();
	//    $("[id=" + test + "]").css("background", "yellow");
	
}
$('.filename_btn').on("click",function(){
	console.log($(this).parent());
	$(this).parent().remove();
});

</script>
<style type="text/css">
.create {
margin-left: 20%;
padding-left: 30px;
width: 820px;
min-height: 800px;
}
.content-header{
	margin-top: 0;
    margin-bottom: 20px;
    color: #6f7275;
}
.article-from{
width: 7500px;
margin: auto;
}
.form-goup{
margin-bottom: 15px;
}
.pull-right{
float: right !important;
}
.has-feedback{
position: relative;
}
</style>
</head>
<body>
<%@include file="/views/common/nav.jsp" %>
<c:set var="binfo" value="${requestScope.bInfo}"/>
<div id=" article-create" class="create">
	<div class="content-head">
		<h3>글 수정하기</h3>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">${binfo.nickname }</div>
		<div class="panel-body">
		<form id="sendwrite" method="post" action="editBoard.do" class="article-form">
			<input type="hidden" id="nickname" name="nickname" value="${binfo.nickname }">
			<div class="form-goup has-feedback">
				<div>
					<input type="text" name="title" id="title" class="form-control" value="${binfo.title }">
				</div>
			</div>
			<div class="form-group">
				<textarea name="contents" id="contents" rows="10" cols="100" style="display:none;">${binfo.contents } </textarea>
				<script type="text/javascript" src="/common/editor/js/edit.js"></script>
			</div>
		</form>
		<div class="form-group has-feedback">
			<input id="fileupload" type="file" name="files[]" multiple>
			<table id="uploaded-files" class="file-table">
				<tr>
					<th>File Name</th>
				</tr>
			</table>
		</div>
		<button type="button" class="btn btn-default">취소</button>
		<button type="button" class="btn btn-info pull-right" id="submit_btn" onclick="sendWrite()">등록</button>
		</div>
	</div>
	
</div>



<fieldset class="form">
	<%-- <form id="sendwrite" action="writeok.do">
		<div class="form-group">
			<div>
				<input type="text" name="nickname" value="${binfo.nickname }" readonly="readonly">
			</div>
		</div>
		<div class="form-group">
			<div>
				<input type="text" name="title" id="title">
			</div>
		</div>
		<div class="form-group">
			 <textarea name="contents" id="contents" rows="10" cols="100" style="display:none;"> </textarea>
			 <script type="text/javascript" src="/common/editor/js/edit.js"></script>
		</div>
	</form> --%>
		
		
		
		<input id="fileupload" type="file" name="files[]" multiple>
		<div id="dropzone" class="fade well">Drop files here</div>
		<table id="uploaded-files" class="table">
			<tr>
				<th>File Name</th>
			</tr>
		</table>
	
<div><button class="btn btn-info" id="submit_btn" onclick="sendWrite()">글쓰기</button></div>
</fieldset>
<div>
</div>
</body>
</html>

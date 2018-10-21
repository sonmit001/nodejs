<%@page import="net.sf.json.processors.JsonBeanProcessor"%>
<%@ page contentType = "text/html;charset=utf-8" %>
<html>
<head>
<title>View</title>
<%@include file="/views/common/preset.jsp" %>
<!-- include summernote css/js-->
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var arr_json = JSON.parse('<%= request.getSession().getAttribute("json")%>');
	var curr_num = $('#curr_num').val();
	 
	
	$('#prev').on("click",function(){
		if(curr_num != 0){
			$('#mtext_id').val(arr_json[curr_num-1].mtext_id);
			$('#arr_num').val(1*curr_num-1);
			$('#submit').submit();
		}
	});
		
	$('#next').on("click",function(){
		if(curr_num < arr_json.length -1){
			var gonextpage = 1*curr_num + 1;
			$('#mtext_id').val(arr_json[gonextpage].mtext_id);
			$('#arr_num').val(gonextpage);
			$('#submit').submit();
		}
	});
	$('#comment-create').on("click",function(){
		$('#comment-create').css("display","none");
		$('#summernote').css("display","block");
		$('#summernote').summernote({
			 height: 300,                 // set editor height
			 minHeight: null,             // set minimum height of editor
			 maxHeight: null,             // set maximum height of editor
			 focus: true,
			 callbacks :{
				 onImageUpload: function(files, editor, welEditable) {
				      sendFile(files[0],editor,welEditable); 
				    } 
			 }
		});
	});
	
	function sendFile(file,editor,welEditable) 
	  {
	  data = new FormData();
	  data.append("file", file);
	            $.ajax({
	            data: data,
	            type: "POST",
	            url: 'insertFile.do',
	            cache: false,
	            contentType: false,
	            processData: false,
	            success: function(url) {
	            	console.log(url);
	            	var json = JSON.parse(url);
	            	console.log(json.realpath);
	            	$('#summernote').summernote('editor.insertImage', json.realpath);
	                   //editor.insertImage(welEditable, url);
	            }
	        }); 
	  }
	$('#reply_submit_btn').on("click",function(){
		var content = $('#summernote').summernote('code');
		console.log(content);
		if(content == '' || content == null || content == '<p><br></p>'){
			alert("내용을 입력해 주세요");
			return;
		}
		$('#contents').val(content);
		$('#reply_submit').submit();

	});
});
function editBoard() {
	location.href="editBoard.do?mtext_id=${}"
}
</script>
<style type="text/css">
#summernote{
display : none;
}
.view {
margin-left: 20%;
padding-left: 30px;
width: 850px;
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

.has-feedback{
position: relative;
}
.contents-container{
position: relative;
}
.content-body{
position: relative;
border-right: 1px solid #ddd;
width: 700px;
}
.pull-left {
    float: left !important;
}
.panel-body {
padding: 15px;
}
.content-function {
    position: relative;
    width: 100px;
    padding-top: 20px;
    color: #666;
}
.pull-right {
float: right !important;
}
.text-center {
text-align: center;
}
.content-function-group {
margin-bottom: 30px;
float: none;
}
.content-container {
    position: relative;
}
.content-text {
    width: 662px;
    overflow-x: auto;
    min-height: 180px;
    margin: 30px 0;
    word-break: break-all;
    word-wrap: break-word;
}
div {
    display: block;
     border-color: #ddd;
}
.panel-heading {
    background-color: #fff !important;
    border-color: #ddd;
}
ul {
    display: block;
    list-style-type: disc;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
    padding-inline-start: 40px;
}
.avatar-info {
    display: inline-block;
    vertical-align: middle;
}
.avatar-medium .date-created {
    display: block;
    color: #ccc;
    font-size: 12px;
}
.form {
    margin-top: 20px;
}
.list-group-item-text {
	
    margin-bottom: 0;
    line-height: 1.3;
}
.note-text {

    min-height: 50px;
    overflow-x: auto;
    word-break: break-all;
}
article {
    display: block;
}
.content-eval-count {
    font-size: 30px;
    font-weight: 100;
}
.self-padding-zero{
padding-left: 0 !important; 
}
.text-center {
    text-align: center;
}
.distric-comment{
margin-bottom: 100px;
}
.content-function-cog {
    position: absolute;
    bottom: 0;
    right: 0;
    width: 100px;
    text-align: center;
    font-size: 20px;
    margin-bottom: 10px;
    min-height: 23px;
}
fieldset {
    min-width: 0;
    padding: 0;
    margin: 0;
    border: 0;
}
textarea.form-control {
    height: auto;
}

</style>
</head>

<body>
<%@include file="/views/common/nav.jsp" %>
<c:set var="list" value="${requestScope.binfo}"/>
<c:set var="finfo" value="${requestScope.finfo}"/>
<c:set var="session" value="${sessionScope.json }" />
<c:set var="session_ui" value="${sessionScope.ui }" />
<c:set var="session_length" value = "${fn:length(sessionScope.json)}"/>
<c:set var="text" value="${requestScope.rtextinfo }"/>
<input type="hidden" id="curr_num" value="${list.arr_num}">
<form id="submit" method="POST" action="goBoardView.do">
	<input type="hidden" id="mtext_id" name="mtext_id">
	<input type="hidden" id="arr_num" name="arr_num">
</form>
<div id=" article-create" class="view">
	<div class="content-head">
		<h3>메인 게시판</h3>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading clearfix">
			<div class="pull-left">${list.nickname }</div>
			<div class="pull-right">${list.view_cnt }</div>
		</div>
	</div>
	<div class="content-container distric-comment clearfix">
		<div class="panel-body content-body pull-left">
			<h2 class="panel-title">${list.title }</h2>
			<hr>
				<c:forEach var="item" items="${finfo}" varStatus="status">
					${status.count } 번째 파일 : <a href="getfile.do?attach_id=${item.attach_id }" >${item.org_name }    </a>
				</c:forEach>
			<hr>
			<article class="content-text">
			${list.contents }
			</article>
		</div>
		<div class="content-function pull-right text-center">
			<div class="content-function-group">
				<a>위로</a>
				<div class="content-eval-count">${list.agree_cnt }</div>
				<a>아래</a>
			</div>
		</div>
		<c:if test="session_ui.nickname == list.nickname">
			<div class="content-function-cog">
				<button class="btn btn-default" onclick="location.href='editBoard.do?mtext_id=${list.mtext_id}'">수정</button>
			</div>
		</c:if>
		
	</div>
	<c:if test="${list.arr_num != 0 && session !=null }">
	<a id="prev">이전!</a>
</c:if>
<c:if test="${list.arr_num < session_length -1 && session !=null }">
	<a class="" id="next">다음!!</a>
</c:if>
	<div class="panel panel-default clearfix">
		<ul class="list-group">
			<li id="comment" class="list-group-item note-title"> 댓글</li>
			<c:forEach var="item" items="${text}">
				<li class="list-group-item note-item self-padding-zero clearfix">
					<div class="content-body panel-body pull-left">
						<div class="avatar avatar-medium clearfix ">
							<div class="avatar-info">
								<div>${item.nickname }</div>
								<div class="date-created">${item.create_time }</div>
							</div>
						</div>
						<fieldset class="form">
							<article class="list-group-item-text note-text">
							 ${item.contents}
							</article>
						</fieldset>
					</div>
					<div class="content-function pull-right text-center">
						<div class="content-function-group">
						위
						<div class="content-eval-count">${item.agree_cnt }</div>
						아래
						</div>
					</div>
				</li>
			</c:forEach>
			<c:choose>
				<c:when test="${session_ui != '' && session_ui ne null}">
					<li class="list-group-item note-item self-padding-zero clearfix">
						<div class="content-body panel-body pull-left">
							<div class="avatar avatar-medium clearfix ">
								<div class="avatar-info">
									<div>${session_ui.nickname }</div>
								</div>
							</div>
							<fieldset class="form">
								<form action="writeReply.do" id="reply_submit">
									<input type="hidden" name="mtext_id" value="${list.mtext_id }">
									<input type="hidden" name="contents" id="contents" >
									<input type="hidden" id="curr_num" value="${list.arr_num}">
								</form>
								
								<textarea id="comment-create" placeholder="댓글을 남기세요" style="width: 675px"></textarea>
								<div id="summernote"></div>
							</fieldset>
						</div>
						<div class="content-function-cog note-submit-buttons clearfix">
							<button id="reply_submit_btn" class="btn btn-info">글쓰기</button>
						</div>
					</li>
				</c:when>
				
				<c:otherwise> 
					<li class="list-group-item note-item self-padding-zero clearfix">
						<div class="panel-body">
							<h5 class="text-center">로그인을 하시면 글을 남길 수 있습니다.</h5>
						</div>
					</li>
				
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
</div>
</body>
</html>

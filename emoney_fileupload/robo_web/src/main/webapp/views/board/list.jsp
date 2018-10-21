<%@ page contentType = "text/html;charset=utf-8" %>
<html>
<head>
<title>Home</title>
<%@include file="/views/common/preset.jsp" %>
<script type="text/javascript">
$(document).ready(function(){
	$('#dataTable').DataTable({
	}); 
});
function getlist(){
	console.log(arguments[0])
	var mtext_id = arguments[0];
	var arr_num =0;
	console.log("....");
	console.log($('#dataTable tbody tr').length);
	var length = $('#dataTable tbody tr').length;
	console.log($('#dataTable tbody tr td'));
	console.log($('#dataTable tbody tr')[0].id);
	//var arr = new Array();
	var gojson = "";
	for(var i =0; i<length; i++){
		var ajson = new Object();
		ajson.mtext_id = $('#dataTable tbody tr')[i].id;
		
		if(ajson.mtext_id == mtext_id ){
			arr_num = i;
		}
		if(i == 0){
			gojson+= JSON.stringify(ajson);
		}else{
			gojson+= "," + JSON.stringify(ajson);
		}
	}
	
	$('#json').val(gojson);
	$('#mtext_id').val(mtext_id);
	$('#arr_num').val(arr_num);
	
	$('#submit').submit();
}

</script>
</head>
<body>
<c:set var="lsit" value="${requestScope.list}"/>

<%@include file="/views/common/nav.jsp" %>
<form id="submit" method="POST" action="goBoardView.do">
	<input type="hidden" id="json" name="json">
	<input type="hidden" id="mtext_id" name="mtext_id">
	<input type="hidden" id="arr_num" name="arr_num">
</form>
<div id="container">
	<div class="panel panel-primary">
		<div class="panel-heading">메인 게시판</div>
		<div class="panel-body">
		<div><a href="writeBoard.do"><button class="btn btn-info" >글쓰기</button></a></div>
			<table id="dataTable" class="table">
				<thead>
					<tr class="info">
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>			
						<th>작성일</th>	
						<th>View</th>		
					</tr>
				</thead>	
				<tbody>
				<c:set var="list" value="${requestScope.list}"/>
                <c:forEach var="item" items="${list}" varStatus="status">
                	<tr id="${item.mtext_id}" onclick="getlist(${item.mtext_id})">
                    	<td>${item.board_id}</td>
                    	<td>${item.title}</td>
                    	<td>${item.nickname}</td>
                    	<td>${item.create_time}</td>
                    	<td>${item.view_cnt}</td>
                    </tr>
                </c:forEach>
               </tbody>
			</table>
		</div>
	</div>
</div>

</body>
</html>

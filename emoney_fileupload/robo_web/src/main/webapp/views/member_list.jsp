<%@ page contentType = "text/html;charset=utf-8" %>
<%@include file="/views/common/preset.jsp" %>
<html>
<head>
	<title>Home</title>
</head>
<!--data table -->
<script type="text/javascript">
$(document).ready( function () {
     $('#dataTable').DataTable({
	}); 
     
    $("select[name=dataTable_length]").change(function(){
    	console.log($("select[name=dataTable_length]").val());
    })
} );
</script>

<body>
<%@include file="/views/common/nav.jsp" %>
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">김선진회원리스트</div>
			<div class="panel-body">
				<table id="dataTable" class="table">
					<thead>
						<tr class="info">
							<th>아이디</th>
							<th>닉네임</th>
							<th>이름</th>			
							<th>핸드폰</th>		
							<th>가입일</th>
							<th>최근접속일</th>		
						</tr>
					</thead>	
					<tbody>
						<c:set var="list" value="${requestScope.list}"/>
	                   <c:forEach var="item" items="${list}" varStatus="status">
	                       <tr id="${item.ACCNT_ID}">
	                       	<td>${item.ACCNT_ID}</td>
	                           <td>${item.NICKNAME}</td>
	                           <td>${item.NAME}</td>
	                           <td>${item.PHONE}</td>
	                           <td>${item.CREATE_TIME}</td>
	                           <td>${item.LAST_LOGIN}</td>
	                       </tr>
	                   </c:forEach>
	               </tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>

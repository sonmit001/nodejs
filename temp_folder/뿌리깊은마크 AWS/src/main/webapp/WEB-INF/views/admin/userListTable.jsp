<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	var userListTable = null;
	$(function(){
		userListTable = $("#userListTable").DataTable({
			responsive: true
		});
		
	});
	
	/*회원삭제  스크립트 START*/
	function deleteUser(uid, nname) {
		$.confirm({
			title : '회원 삭제',
			content : '삭제하시겠습니까?',
			theme: 'light',
			type: 'green',
			backgroundDismiss: true,
			closeIcon: true,
			buttons: {
		        '삭제': {
		        	btnClass : 'btn-danger',
		        	keys: ['enter'],
		        	action : function () {
		        		userListTable.row($('tr[id=' + nname + ']')).remove().draw(); // dataTable에서 지우기
		        		
		    			$.ajax({
		    				url: "blacklist.do",
		    				type: "post",
		    				data : {
		    					uid : uid // 회원 ID
		    				},
		    				success : function(data){
		    					console.log(data);
		    				}
		    			
		    			});
		        	}
		        },
		     
		        '취소': {
		        	btnClass : 'btn-success',
		        	action : function() {
		        		
		        	}
		        }
		    }
		});
	};
    /*회원삭제  스크립트 END*/
</script>

<!-- Main Content START -->
<div class="content-wrapper" style="min-height: 913px;">
	<section class="content-header">
		<h1><i class="fas fa-clipboard-list content-header-ico"></i>User List</h1>
		<ol class="breadcrumb">
			<li><i class="fa fa-home"></i>Home</li>
			<li class="active">user list</li>
		</ol>
	</section>
	<!--userList table start-->

	<section class="content">
		<div id="page-wrapper">
			<br>
			<div class="row">
				<div class="col-lg-12">
					<div class="panel">
						<div class="panel-heading"><i class="fa fa-arrow-right content-head"></i>회원 목록</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<table width="100%" class="table table-hover" id="userListTable">
								<thead>
									<tr>
										<th>Name</th>
										<th>Email</th>
										<th>Actions</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${userlist}" var="user">
									<tr id="${user.nname}">
										<td>${user.nname}</td>
										<td>${user.uid}</td>
										<td><i class="fas fa-trash-alt url-action" onclick="deleteUser('${user.uid}','${user.nname}')"></i></td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
							<!-- /.table-responsive -->
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /#page-wrapper -->
		<!--userList table end-->
	</section>
</div>
<!-- Main Content END -->
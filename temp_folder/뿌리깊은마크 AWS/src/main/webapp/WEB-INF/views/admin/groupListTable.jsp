<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	var groupListTable = null;
	
	$(document).ready(function() {
    	groupListTable = $('#groupListTable').DataTable({
			responsive: true
		});
    });

	/*그룹삭제  스크립트 START*/
	function deleteGroup(gid) {
		$.confirm({
			title : '그룹 삭제',
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
		        		groupListTable.row($('tr[id=' + gid + ']')).remove().draw(); // dataTable에서 지우기
		        		
		    			$.ajax({
		    				url: "deleteGroup.do",
		    				type: "post",
		    				data : {
		    					gid : gid // 그룹 ID
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
	}
    /*그룹삭제  스크립트 END*/
</script>

<!-- Main Content START -->
<div class="content-wrapper" style="min-height: 913px;">
	<section class="content-header">
		<h1><i class="fas fa-clipboard-list content-header-ico"></i>Group List</h1>
		<ol class="breadcrumb">
			<li><i class="fa fa-home"></i>Home</li>
			<li class="active">group list</li>
		</ol>
	</section>
	<!--groupList table start-->
	<section class="content">
		<div id="page-wrapper">
			<br>
			<div class="row">
				<div class="col-lg-12">
					<div class="panel">
						<div class="panel-heading"><i class="fa fa-arrow-right content-head"></i>그룹 목록</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<table width="100%" class="table table-hover" id="groupListTable">
								<thead>
									<tr>
										<th>GroupId</th>
										<th>GroupName</th>
										<th>GroupHashTag</th>
										<th>GroupHead</th>
										<th>Actions</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${grouplist}" var="group">
										<tr id="${group.gid}">
											<td>${group.gid}</td>
											<td>${group.gname}</td>
											<td>${group.htag}</td>
											<td>${group.nname}</td>
											<td><i class="fas fa-trash-alt url-action" onclick="deleteGroup(${group.gid})"></i></td>
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
		<!--groupList table end-->
	</section>
</div>
<!-- Main Content END -->
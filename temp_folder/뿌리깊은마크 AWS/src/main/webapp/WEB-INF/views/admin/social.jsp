<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- adminTable CSS START -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/social.css">
<!-- adminTable CSS END -->

<script type="text/javascript">
	var socialUserTable = null;
	var socialGroupTable = null;
	
	$(function() {
		socialUserTable = $('#socialUserBookmarkTable').DataTable({
			responsive : true,
			"order" : [ [ 3, "desc" ] ]
		});
		socialGroupTable = $('#socialGroupBookmarkTable').DataTable({
			responsive : true,
			"order" : [ [ 3, "desc" ] ]
		});
	});
	
	function deleteSocialUserBookmark(ubid) {
		$.confirm({
			title : '소셜 개인 북마크 삭제',
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
		        		socialUserTable.row($('tr[id=ubid' + ubid + ']')).remove().draw(); // dataTable에서 지우기
		        		
						$.ajax({
							url: "deleteSUBook.do",
							type: "post",
							data : {
								ubid : ubid // 관리자북마크 ID
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
	
	function deleteSocialGroupBookmark(gid) {
		$.confirm({
			title : '소셜 그룹 북마크 삭제',
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
		        		socialGroupTable.row($('tr[id=gid' + gid + ']')).remove().draw(); // dataTable에서 지우기
						$.ajax({
							url: "deleteGroup.do",
							type: "post",
							data : {
								gid : gid // 관리자북마크 ID
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
	
	
</script>


<!-- Main Content START -->
<div class="content-wrapper adminSocial" style="min-height: 913px;">
	<section class="content-header">
		<h1><i class="fas fa-clipboard-list content-header-ico"></i>Social Bookmark List</h1>
		<ol class="breadcrumb">
			<li><i class="fa fa-home"></i>Home</li>
			<li class="active">social bookmark list</li>
		</ol>
	</section>
	<!--userList table start-->

	<section class="content">
		<div id="page-wrapper">
			<br>
			<div class="row">
				<!-- Individual Share Bookmark START -->
				<!-- u_booklist start -->
				<div class="col-sm-6">
					<div class="panel">
						<div class="panel-heading"><i class="fa fa-arrow-right social-content-head"></i>소셜 개인 북마크 목록</div>
						<div class="panel-body">
							<table class="table table-hover" id="socialUserBookmarkTable">
								<thead>
									<tr>
										<th>사이트명</th>
										<th class="table-tag">태그</th>
										<th>작성자</th>
										<th class="table-date">공유날짜</th>
										<th class="table-click">조회수</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${u_list}" var="u_booklist">
										<!-- 개인 북마크 공유 -->
										<tr id="ubid${u_booklist.ubid}">
											<td><a href="${u_booklist.url}" target="_blank">${u_booklist.sname}</a></td>
											<td class="table-tag">"${u_booklist.htag}"</td>
											<td>${u_booklist.nname}</td>
											<td class="table-date">${u_booklist.sdate}</td>
											<td class="table-click">${u_booklist.view}</td>
											<td><i class="fas fa-trash-alt url-action" onclick="deleteSocialUserBookmark(${u_booklist.ubid})"></i></td>
										</tr>
									</c:forEach>
									<!-- 개인 북마크 공유 -->
								</tbody>
							</table>
						</div>
					</div>

				</div>
				<!-- u_booklist end -->
				<!-- Individual Share Bookmark START -->

				<!-- Group Share Bookmark START -->
				<div class="col-sm-6">
					<div class="panel">
						<div class="panel-heading"><i class="fa fa-arrow-right social-content-head"></i>소셜 그룹 북마크 목록</div>
						<div class="panel-body">
							<table class="table table-hover" id="socialGroupBookmarkTable">
								<thead>
									<tr>
										<th>그룹명</th>
										<th class="table-tag">태그</th>
										<th>그룹장</th>
										<th class="table-date">공유날짜</th>
										<th class="table-click">조회수</th>
										<th>Action</th>
									</tr>
								</thead>
								<!-- g_list start -->
								<tbody>
									<!-- 그룹 공유   -->
									<c:forEach items="${g_list}" var="g_booklist">
										<tr id="gid${g_booklist.gid}">
											<td>${g_booklist.gname}</td>
											<td class="table-tag">"${g_booklist.htag}"</td>
											<td>${g_booklist.nname}</td>
											<td class="table-date">${g_booklist.duedate}</td>
											<td class="table-click">${g_booklist.view}</td>
											<td><i class="fas fa-trash-alt url-action" onclick="deleteSocialGroupBookmark(${g_booklist.gid})"></i></td>
										</tr>
									</c:forEach>
									<!-- 그룹 공유   -->
								</tbody>
								<!-- g_list end -->
							</table>
						</div>
					</div>
				</div>
				<!-- Group Share Bookmark END -->
			</div>
		</div>
		<!-- /#page-wrapper -->
		<!--userList table end-->
	</section>
</div>
<!-- Main Content END -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	function headerNoticeReg() {
		$.confirm({
		    title: '공지사항 등록',
		    content: '' +
		    '<form id="noticeRegForm" action="${pageContext.request.contextPath}/admin/noticeReg.do" class="formName" method="post" onsubmit="return false;">' +
		    '<div class="form-group">' +
		    '<label>공지사항 내용</label>' +
		    '<input type="text" name="ncontent" placeholder="공지사항 내용" class="name form-control" required />' +
		    '</div>' +
		    '</form>',
		    theme: 'light',
			type: 'green',
		    closeIcon: true,
		    buttons: {
		        formSubmit: {
		            text: '등록',
		            btnClass: 'btn-green',
		            action: function () {
		                var name = this.$content.find('.name').val();
		                if(!name){
		                    $.alert('공지사항 내용을 입력해주세요');
		                    return false;
		                }
		                
		                
		                $("#noticeRegForm").submit();
		                $.alert("공지사항 등록 완료");
		            }
		        },
		        '취소': {
		            btnClass: 'btn-red',
		            action: function () {
		            //close
		            }
		        },
		    },
		    
		    onContentReady: function(){
		    	var jc = this;
		    	$("#noticeRegForm").ajaxForm({
                	success: function(data, statusText, xhr, $form){
                		var newNotice = '<li class="header"><a href="#">' + data.newNotice.ncontent + '</a></li>';
                		$("#noticeDropdownMenu").prepend(newNotice);
                		console.log(("#noticeDropdownMenu").length);
                		if(("#noticeDropdownMenu").length > 4){
                			console.log(("#noticeDropdownMenu").length);
                			$("#noticeDropdownMenu").children().eq(3).remove();
                		}
                	}
                });
		    	
		    }

		});
	}
</script>

<div id="app">
	<div class="wrapper">
		<header class="main-header">
			<span class="logo-mini"><a href="${pageContext.request.contextPath}/admin/main.do" data-duration="0.2s">
				<span class="img-responsive center-block logo">뿌리깊은마크</span></a>
			</span>
			<!-- header menu bar START -->
			<nav role="navigation" class="navbar navbar-static-top">
				<a href="javascript:;" data-toggle="offcanvas" role="button" class="sidebar-toggle"><span class="sr-only">Toggle navigation</span></a>
				<div class="navbar-custom-menu">
					<ul class="nav navbar-nav">
						<!-- <li class="dropdown messages-menu"><a href="javascript:;" data-toggle="dropdown" class="dropdown-toggle">
						<i class="fas fa-envelope fa-lg admin-mail"></i> <span class="label label-success">3</span></a> Message Alarm START
							<ul class="dropdown-menu">
								<li class="header">You have 1 message(s)</li>
								<li>
									<ul class="menu">
										<li><a href="javascript:;">
												<h4>
													Support Team <small><i class="fas fa-clock"></i> 5
														mins</small>
												</h4>
												<p>Why not consider this a test message?</p>
										</a></li>
									</ul>
								</li>
								<li class="footer"><a href="javascript:;">See All Messages</a></li>
							</ul> Message Alarm END
						</li> -->
						<!-- Notice Alarm START -->
						<li class="dropdown notifications-menu">
							<a href="javascript:;" data-toggle="dropdown" class="dropdown-toggle">
								<i class="fas fa-bell fa-lg admin-notice"></i>
							</a>
							<ul id="noticeDropdownMenu" class="dropdown-menu">
								<c:if test="${(headerNoticeList ne null) && (!empty headerNoticeList)}">
									<c:forEach items="${headerNoticeList}" var="headerNotice" >
										<li class="header"><a href="#">${headerNotice.ncontent}</a></li>
									</c:forEach>
								</c:if>
								<li class="header" onclick="headerNoticeReg();"><a><i class="fa fa-plus-circle" style="color: #f39c12;"></i> 공지사항 등록</a></li>
							</ul>
						</li>
						<!-- Notice Alarm END -->

						<!-- Admin Infomation START -->
						<li class="dropdown user user-menu">
							<a href="javascript:;" data-toggle="dropdown" class="dropdown-toggle">
								<i class="fas fa-user-circle"></i>
								<span class="hidden-xs">${sessionScope.info_usernname}</span>
							</a>
							<ul id="userMenu" class="dropdown-menu">
								<li class="header"><a href="<%= request.getContextPath() %>/security/logout">로그아웃</a></li>
							</ul>
						</li>
						<!-- Admin Infomation END -->
					</ul>
				</div>
			</nav>

			<!-- header menu bar END -->
		</header>

		<!-- SideMenu START -->
		<aside class="main-sidebar">
			<section class="sidebar">
				<ul class="sidebar-menu">
					<!-- Sidemenu Chart START -->
					<li class="header"><i class="fas fa-chart-area"></i>&nbsp;&nbsp;CHART</li>
					<li class="pageLink router-link-active">
						<a href="main.do" class="transition">&nbsp;&nbsp;&nbsp;
							<i class="fas fa-chart-line"></i> 
							<span class="page">&nbsp;&nbsp;Chart</span>
						</a>
					</li>
					<!-- Sidemenu Chart END -->

					<!-- Sidemenu Pages Bookmark list START -->
					<li class="header"><i class="fas fa-book"></i>&nbsp;&nbsp;Bookmark List</li>
					<li class="pageLink"><a href="mainBookList.do">&nbsp;&nbsp;&nbsp;<i class="fas fa-bookmark fa-fw"></i><span class="page">&nbsp;&nbsp;Main Page Bookmark</span></a></li>
					<li class="pageLink"><a href="social.do">&nbsp;&nbsp;&nbsp;<i class="fas fa-bookmark fa-fw"></i><span class="page">&nbsp;&nbsp;Social Page Bookmark</span></a></li>
					<!-- Sidemenu Pages Bookmark list END -->

					<!-- Sidemenu List START -->
					<li class="header"><i class="fa fa-list-alt"></i>&nbsp;&nbsp;List</li>
					<li class="pageLink"><a href="groupListTable.do">&nbsp;&nbsp;&nbsp;<i class="fas fa-list-ul"></i><span class="page">&nbsp;&nbsp;Group List</span></a></li>
					<li class="pageLink"><a href="userListTable.do">&nbsp;&nbsp;&nbsp;<i class="fas fa-list-ul"></i><span class="page">&nbsp;&nbsp;User List</span></a></li>
					<!-- Sidemenu List END -->
				</ul>
			</section>
		</aside>
		<!-- Sidemenu END -->
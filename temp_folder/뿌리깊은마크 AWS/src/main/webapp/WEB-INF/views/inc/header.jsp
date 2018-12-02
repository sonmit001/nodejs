<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="se" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	var headerTeamList = new Array(); // 팀 리스트 가져오기
	
	<c:forEach items="${headerTeamList}" var="team">
		headerTeamList.push("${team.gid}");
	</c:forEach>

	// header에 있는 그룹 추가 버튼 클릭 이벤트
	function headerAddGroup(gid) {
		$.confirm({
		    title: '그룹 추가',
		    content: '' +
		    '<form id="addGroupForm" action="${pageContext.request.contextPath}/addGroup.do" class="formName" method="post" onsubmit="return false;">' +
		    '<div class="form-group">' +
		    '<label>그룹명</label>' +
		    '<input type="text" name="gname" placeholder="그룹명" class="name form-control" maxlength="12" required />' +
		    '</div>' +
		    '</form>',
		    type: 'green',
		    closeIcon: true,
		    buttons: {
		    	formSubmit: {
		    		text: '추가',
		            btnClass: 'btn-green',
		            action: function () {
		                var name = this.$content.find('.name').val();
		                if(!name){
		                    $.alert('그룹명을 적어주세요');
		                    return false;
		                }
		               
		                $("#addGroupForm").submit();
		                $.alert("그룹 추가 성공");
		            }
		        },
		        '취소': {
		            btnClass: 'btn-red',
		        	action : function () {}
		            //close
		        },
		        
		    },
		    onContentReady: function(){
		    	var jc = this;
		    	$("#addGroupForm").ajaxForm({
		    		success: function(data, statusText, xhr, $form){
		    			var group = '<li id="headerGroup' + data.newTeam.gid + '" class="groupMenu"><a href="/team/main.do?gid=' + data.newTeam.gid + '&gname=' + data.newTeam.gname + '">' + data.newTeam.gname + '</a></li>';
		    			$("#groupDropdownMenu").children().last().before(group);
		    			if($(".groupMenu").length > 10){
		    				$("#groupDropdownMenu").children().last().remove();
		    			}
		    			
		    			if($("#participatingGroupList").length > 0){
		    				var groupListAdd = "";
		    				groupListAdd += '<li id="' + data.newTeam.gid + '" class="list-group-item">';
		    				groupListAdd += '<label class="my-group-list" onclick="location.href=\'/team/main.do?gid=' + data.newTeam.gid + '&gname=' + data.newTeam.gname + '\'"> ' + data.newTeam.gname + '</label>';
		    				groupListAdd += '<div class="pull-right action-buttons">';
		    				groupListAdd += '<a class="completed">';
		    				groupListAdd += '<span class="glyphicon glyphicon-check" onclick="completedGroup(' + data.newTeam.gid + ')"></span>';
		    				groupListAdd += '</a>';
		    				groupListAdd += '</div>';
		    				groupListAdd += '</li>';
		    				$("#participatingGroupList").children().last().before(groupListAdd);
		    			}
		    			
		    			headerTeamList.push(data.newTeam.gid);
		    		}
		    	});
		    }

		});
	}
</script>

<!-- Header START-->
<header id="header" class="header">
 	<div class="navbar navbar-inverse" role="banner">
		<div class="container">
			<div class="navbar-brand">
				<a class="logo-text" href="<%= request.getContextPath() %>/">뿌리깊은마크</a>
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
			</div>
			
			<div class="collapse navbar-collapse">
			
				<%-- <se:authorize access="isAuthenticated()"> --%>
				<c:if test="${sessionScope.info_userid != null}">
				<ul class="nav navbar-nav navbar-right">
					<li>
						<a href="<%= request.getContextPath() %>/user/mybookmark.do">MyBookmark</a>
					</li>
					<!-- Group Menu START -->
					<li id="groupDropdown" class="dropdown">
						<a href="#" id="group_menu">Group <i class="fa fa-angle-down"></i></a>
						<ul id="groupDropdownMenu" role="menu" class="sub-menu">
						<c:choose>
							<c:when test="${(headerTeamList ne null) && (!empty headerTeamList)}">
								<c:forEach items="${headerTeamList}" var="headerTeam" varStatus="status">
									<c:if test="${status.index < 10}">
										<li id="headerGroup${headerTeam.gid}" class="groupMenu"><a href="<%= request.getContextPath() %>/team/main.do?gid=${headerTeam.gid}&gname=${headerTeam.gname}">${headerTeam.gname}</a></li>
									</c:if>
									<c:if test="${status.last}">
										<c:if test="${status.count < 10}">
											<li id="headerGroupAdd" class="groupMenu" onclick="headerAddGroup()"><a href="#"><i class="fa fa-plus-circle" style="color: red;"></i>&nbsp;&nbsp;그룹 추가</a></li>
										</c:if>
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<li id="headerGroupAdd" class="groupMenu" onclick="headerAddGroup()"><a href="#"><i class="fa fa-plus-circle" style="color: red;"></i>&nbsp;&nbsp;그룹 추가</a></li>
							</c:otherwise>
						</c:choose>
						</ul> 
					</li>
					<!-- Group Menu END -->
					
					<!-- Social Link  -->
					<li>
						<a href="<%= request.getContextPath() %>/social/social.do">Social</a>
					</li>
					<!-- Social Link  -->
					
					<!-- Alarm START -->
					<li id="alarm_menu_li" class="dropdown">
						<a href="#" id="alarm_menu" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
							Notice <i class="fa fa-angle-down"></i>
						</a>
						<!-- headerAlarmList --> 
						<c:if test="${(headerAlarmList ne null) && (!empty headerAlarmList)}">
						<ul role="menu" class="g_alarm_ul dropdown-menu">
							<c:forEach items="${headerAlarmList}" var="alarmList" varStatus="status">
								<li id="alarmlist${alarmList.gid}" class="g_alarm_li">
									<span class="g_alarm_head">Group&nbsp;: <span class="g_alarm_name">${alarmList.gname}</span></span> 
									<i class="fas fa-times g_notice" onclick="deleteMemo('${alarmList.gid}','${alarmList.fromid}','${alarmList.ganame}');"></i>
									<br style="clear:both">
									<c:choose>
									
										<c:when test="${alarmList.ganame == '초대'}">
											<span class="g_alarm_head">
												From&nbsp;&nbsp;&nbsp;: <span class="g_alarm_name">${alarmList.fromid}</span>
												<span class="g_alarm_date">${alarmList.senddate}</span>
											</span>
											<br>
											<span class="g_alarm_content">해당 그룹에서 회원님을 초대했습니다!
											<i class="fas fa-check g_notice_ok" onclick="inviteOk('${alarmList.toid}','${alarmList.gid}',
																							      '${alarmList.gname}','${alarmList.fromid}',
																							  	  '${alarmList.ganame}');"></i>
											</span>
											<br style="clear:both">
										</c:when>
										
										<c:when test="${alarmList.ganame == '완료'}">
											<span class="g_alarm_head">
												From&nbsp;&nbsp;&nbsp;: <span class="g_alarm_name">${alarmList.fromid}</span>
												<span class="g_alarm_date">${alarmList.senddate}</span>
											</span>
											<br>
											<span class="g_alarm_content">해당 그룹이 완료되었습니다!
											<i class="fas fa-check g_notice_ok" onclick="deleteMemo('${alarmList.gid}','${alarmList.fromid}','${alarmList.ganame}');"></i>
											</span>
										</c:when>
											
										<c:otherwise>
											<span class="g_alarm_content">해당 그룹에서 회원님을 강퇴했습니다!</span>
											<i class="fas fa-ban g_notice_no" onclick="deleteMemo('${alarmList.gid}','${alarmList.fromid}','${alarmList.ganame}');"></i>
										</c:otherwise>
										
									</c:choose>
								</li>
							</c:forEach>
						</ul>
						</c:if>
						<div id="alarm-count-text" class="alarm-count-div animated flash"></div>
					</li>
					<script type="text/javascript">
						var alarm_count = $('.g_alarm_li').length;
						if( alarm_count <= 3 && alarm_count > 0 ) {
							$('#alarm-count-text').html("<i class='fas fa-bullhorn'>&nbsp;" + alarm_count + "&nbsp;</i>");
						}else if( alarm_count == 0 ){
							$('#alarm-count-text').html('');
						}else {
							$('#alarm-count-text').html("<i class='fas fa-bullhorn'>&nbsp;3+&nbsp;</i>");
						}
					</script>
					<!-- Alarm START END -->
					
					<!-- Notice Alarm START -->
					<li id="noticeDropdown" class="dropdown">
						<a href="#">
						<i class="far fa-bell fa-lg notice-alarm" style="margin-top: -3px;"></i>
						</a>
						<c:if test="${(headerNoticeList ne null) && (!empty headerNoticeList)}">
							<ul role="menu" class="sub-menu">
							<c:forEach items="${headerNoticeList}" var="headerNotice">
								<li><a href="#">${headerNotice.ncontent}</a></li>
							</c:forEach>
							</ul>
						</c:if>
					</li>
					<!-- Notice Alarm END -->
					
					<!-- USER INFO START -->
					<li>
						<a class="username" href="#">
						<c:choose>
							<c:when test="${sessionScope.info_oauth != null}">
								<img class="dropdown header-ico" src="${sessionScope.info_userprofile}" onerror="this.src='<%= request.getContextPath() %>/images/profile.png'">
							</c:when>
							<c:otherwise>
								<img class="dropdown header-ico" src="<%= request.getContextPath() %>/images/profile/${sessionScope.info_userprofile}" onerror="this.src='<%= request.getContextPath() %>/images/profile.png'">
							</c:otherwise>
						</c:choose>
							&nbsp;${sessionScope.info_usernname}
						</a>
						<ul role="menu" class="user sub-menu">
							<c:if test="${sessionScope.info_oauth == null}">
							<li><a href="<%= request.getContextPath() %>/myInfo.do">회원정보수정</a></li>
							</c:if>
							<li><a href='<%= request.getContextPath() %>/security/logout'>Logout</a></li>
						</ul>
					</li>
					<!-- USER INFO END -->
				</ul>
				</c:if>
				<%-- </se:authorize> --%>
				<script type="text/javascript">var userid = '<c:out value="${sessionScope.info_userid}"/>';</script>
			</div>
		</div>
	</div>
</header>
<!-- Header END-->


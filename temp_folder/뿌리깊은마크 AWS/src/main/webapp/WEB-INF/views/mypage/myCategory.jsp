<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
function completedGroup(gid) {
	$.confirm({
	    title: '그룹 완료',
	    content: '' +
	    '<form id="completedGroupForm" action="/user/completedGroup.do" class="formName" method="post" onsubmit="return false;">' +
	    '<div class="form-group">' +
	    '<label>해시태그</label>' +
	    '<input type="text" id="htag_btn2" name="htag" class="name2 form-control" onkeydown="addHashtag2()" maxlength="15"> ' +
	    '<div id="htag_append2"></div>' +
	    '<input type="hidden" class="gid" name="gid" />' + 
	    '</div>' +
	    '</form>',
	    closeIcon: true,
	    
	    buttons: {
	        formSubmit: {
	            text: '완료',
	            btnClass: 'btn-success',
	            action: function () {
	                this.$content.find('.gid').val(gid);
	                var htag='';
	                 
	                $.each(hashtagList2 , function(index,data){
	        	    	htag += data;
	        	    })
	        	    
	        	    if(htag == ""){
	        			$.alert("해시태그를 하나 이상 입력해주세요")
	        			return false;
	        		}else if(hashtagList2.length >10){
	        			$.alert("해시태그는 10개 까지만 입력 가능합니다");
	        			return false;
	        		}else{
		                
		                this.$content.find('.name2').val(htag);
		                
		                $("#completedGroupForm").ajaxForm({
		                	success: function(data, statusText, xhr, $form){
		                		var fromid = '${sessionScope.info_usernname}';
		                		
		    	        		stompClient.send('/alarm' , {}, 
		    	        			JSON.stringify({
		    	        			gid: data.completedGroup.gid,
		    	        			fromid:  fromid,
		    	        			gname: data.completedGroup.gname,
		    	        			gmemo: '완료',
		    	        			senddate: 'NOW'})
		    	        		);
		    	        		
		    	        		hashtagList2 = [];
		    	        		
		    	        		console.log(fromid);
		                	}
		                });
		                
		                $("#completedGroupForm").submit();
	        		}
	            }
	        },
	        '취소': {
	        	btnClass : 'btn-danger',
        		action : function() {
        		}
	        },
	    }
	    
	});
}


</script>


<div class="container">
	<div class="row" style="padding-top: 100px;"></div>
	<div class="row my-row-bg">
		<!-- 카테고리 div -->
		<div class="col-lg-4 mydiv-height my-bookmark-div">
			<img
				src="${pageContext.request.contextPath}/images/mypage/left_spring.png"
				alt="" class="spring left-top"> <img
				src="${pageContext.request.contextPath}/images/mypage/left_spring.png"
				alt="" class="spring left-bottom">
			<!-- 마이 북마크 Heading -->
			<div class="heading my-bookmark-list">
				<i class="material-icons md-32">archive</i><span
					class="mypage-title">마이 북마크</span>
				<button type="button" class="my-boomark-btn" id="addroot">New
					Category</button>
			</div>
			<div
				style="background-color: white; border-radius: 15px 15px 15px 15px" class="testing">
				<div id="jstree_container"></div>
			</div>
		</div>

		<!-- 선택한 폴더(카테고리)의 URL -->
		<div class="col-lg-4 mydiv-height my-bookmark-print-div">
			<img
				src="${pageContext.request.contextPath}/images/mypage/right_spring.png"
				alt="" class="spring right-top"> <img
				src="${pageContext.request.contextPath}/images/mypage/right_spring.png"
				alt="" class="spring right-bottom">
			<!-- 출력 Div Heading -->
			<div class="heading my-bookmark-print-list ">
				<i class="material-icons md-32 pull-left">view_list</i><span
					class="mypage-title pull-left">리스트</span> <span
					class="mypage-title">&nbsp;</span>
				<span class="pull-left loading_text" ><div id="loading"></div></span>
				<button type="button" class="my-boomark-btn" id="addurl">Add
					URL</button>
			</div>
			<div>
				<div id="jstree_container_child"></div>
			</div>

		</div>

		<!-- 내가 참여하는 그룹 리스트 -->
		<div class="col-lg-3 mydiv-height">
			<!-- 참여중인 그룹리스트 -->
			<div class="group-list-div panel group-list-panel">
				<div class="heading-post-top group-list">
					<i class="material-icons md-36">playlist_play</i><span
						class="mypage-title">그룹리스트</span>
				</div>
				<div class="panel-body">
					<ul id="participatingGroupList" class="group-list-list">
						<c:forEach items="${teamList}" var="team">
							<li id="${team.gid}" class="list-group-item">
								<label class="my-group-list" onclick="location.href='<%= request.getContextPath()%>/team/main.do?gid=${team.gid}&gname=${team.gname }'"> ${team.gname} </label>
								<div class="pull-right action-buttons">
									<c:choose>
										<c:when test="${team.grid == '1'}">
											<a class="completed">
												<span class="glyphicon glyphicon-check" onclick="completedGroup(${team.gid})"></span>
											</a>
										</c:when>
										<c:otherwise>
											<a class="trash">
												<span class="glyphicon glyphicon-trash" onclick="deleteGroup(${team.gid})">
												</span>
											</a>
										</c:otherwise>
									</c:choose>
								</div>
							</li>
						</c:forEach>
						<li class="list-group-item" onclick="addGroup()"><a class="plus"><span
								class="glyphicon glyphicon-plus-sign"></span></a>
							<label class="my-group-list"> 그룹을 만드시겠습니까? </label></li>
					</ul>
				</div>
			</div>

			<!-- 완료된 그룹리스트 -->
			<div
				class="completed-group-list-div panel group-completed-list-panel">
				<div class="heading-post-bottom group-completed-list">
					<i class="material-icons md-36">playlist_add_check</i><span
						class="mypage-title">완료된 그룹</span>
				</div>
				<div class="panel-body-scroll">
					<ul id="completedGroupList" class="group-list-list">
						<c:forEach items="${completedTeamList}" var="completedTeam">
							<li id="${completedTeam.gid}" class="list-group-item">
								<label class="my-group-list" onclick="open_completed_group_modal('${completedTeam.gname}', ${completedTeam.gid})"> ${completedTeam.gname} </label>
								<div class="pull-right action-buttons">
									<a class="trash"><span class="glyphicon glyphicon-trash" onclick="deleteCompletedGroup(${completedTeam.gid})"></span></a>
								</div>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
	

	<!-- 완료된 그룹 가져오기 Modal -->
	<div id="completedGroupModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="socialGroupModalLabel">
		<div class="main-modal-controller">
			<div class="main-modal-center">
				<div class="modal-dialog" role="document">
					<div class="modal-content social completed-group">
						<div class="modal-header group">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="gridSystemModalLabel">그룹 북마크 가져가기</h4>
						</div>
						<div class="modal-body row">
							<div class="completed-modal-left-group">
								<form id="form-to-mybookmark-left" action="user/addtomybookmark.do" method="post">
					                <h4 class="completed-modal-from" hidden="true"><b>URL :</b>
							        	<a class="groupshare-url" name="url"></a></h4>
							        <h4 class="completed-modal-from"><b>From : <span id="from-text" class="groupname"> </span></b></h4>
					                <div class="jstree-from" id="jstree-from-left">
					                
					                </div>
									<input type="hidden" class="groupshare-urlname-left" value="" name="urlname" readonly>
									<input type="hidden" class="groupshare-userpid-left" value="" name="pid" readonly>
									<input type="hidden" class="groupshare-abid-left" value="" name="abid" readonly>
									<input type="hidden" class="groupshare-gid-left" value="" name="gid" readonly>
								</form>
				            </div>   
				            <div class="completed-modal-right-group">
				               	<h4 class="completed-modal-to"><b>To : </b></h4>
				               	<!-- Dropdown -->
				               	<div class="dropdown completed-modal-dropdown">
				                   	<button id="dropdownMenuButton" class="btn btn-secondary groupshare dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				                       	Click <span class="caret"></span>
				                   	</button>
				                   	<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
				                        <li id="completed-modal-mybook" class="dropdown-item" href="#">나의 북마크</li>
				                        <hr class="divider-hr">
				                        <li class="dropdown-item dropdown-submenu">
				                            <a tabindex="-1" href="#">나의 그룹북마크</a>
				                        </li>
				                    </div>
					      		</div>
					      		
				                <div id="jstree-to-right" class="jstree-to" style="float: left;">
				                
				                </div>
				            </div>
						</div>
						<div class="modal-footer group">
							<button type="button" class="btn btn-default group" data-dismiss="modal">취소</button>
							<button id="into-my-bookmark-getgroup-btn" type="button" class="btn btn-primary group" style="display: inline;">확인</button>
							<button id="into-group-bookmark-getgroup-btn" type="button" class="btn btn-primary group" style="display: none;">확인</button>
						</div>
					</div><!-- /.modal-content -->
				</div><!-- /.modal-dialog -->
			</div>
		</div>
	</div><!-- /.modal -->
</div>

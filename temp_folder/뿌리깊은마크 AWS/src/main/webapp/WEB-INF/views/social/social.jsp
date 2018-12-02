<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="se" uri="http://www.springframework.org/security/tags" %>

<!-- social 개인 북마크 가져가기 div START -->
<div id="socialIndiModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="socialIndiModalLabel">
	<div class="main-modal-controller">
		<div class="main-modal-center">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="gridSystemModalLabel">북마크 가져가기</h4>
					</div>
					<div class="modal-body">
						<div class="completed-modal-left">
							<form id="form-to-getmybookmark" action="getmybookmark.do" method="post">
				                <h4 class="completed-modal-from"><b>URL :</b>
				                	<input type="text" class="indishare-url" name="url" readonly></h4>
									<input type="hidden" class="indishare-urlname"  name="urlname" readonly>
									<input type="hidden" class="indishare-userpid" value="" name="pid" readonly>
									<input type="hidden" class="indishare-abid" value="" name="abid" readonly>
									<input type="hidden" class="indishare-gid" value="" name="gid" readonly>
							</form>
			            </div>
			            <hr>
			            <div class="completed-modal-left abc123">
			                <h4 class="completed-modal-to"><b>가져가기 : </b></h4>
			                <!-- Dropdown -->
			                <div class="dropdown completed-modal-dropdown">
			                    <button id="dropdownMenuButton-indi" class="btn btn-secondary indishare dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			                    	Click <span class="caret"></span>
			                    </button>
			                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton-indi">
			                        <li id="dropdown-my-book" class="dropdown-item-mybook">My Bookmark</li>
			                        <hr class="divider-hr">
			                        <li class="dropdown-item-indishare dropdown-submenu">
			                            <a tabindex="-1">Group</a>
			                        	<c:if test="${(headerTeamList ne null) && (!empty headerTeamList)}">
											<ul class="dropdown-menu">
											<c:forEach items="${headerTeamList}" var="TeamList" varStatus="status">
												<c:choose>
													<c:when test="${ status.index == 0}">
														<li class="dropdown-group-item" onclick="selectedGroup('${TeamList.gname}', ${TeamList.gid})">
														<span tabindex="-1">${TeamList.gname}</span></li><hr class="divider-hr">
													</c:when>
													<c:otherwise>
														<li class="dropdown-group-item" onclick="selectedGroup('${TeamList.gname}', ${TeamList.gid})">
														<span>${TeamList.gname}</span></li><hr class="divider-hr">
													</c:otherwise>													
												</c:choose>
											</c:forEach>
											</ul>
										</c:if>
			                        </li>
			                    </div>
			                </div>
			                <div id="jstree-to-bottom" style="clear: both;"></div>
			            </div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default indishare" data-dismiss="modal">취소</button>
						<button id="into-my-bookmark" type="button" class="btn btn-primary">확인</button>
						<button id="into-group-bookmark" type="button" class="btn btn-primary" style="display: none;">확인</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div>
	</div>
</div><!-- /.modal -->
<!-- social 개인 북마크 가져가기 div END -->

<!-- social 그룹 북마크 가져가기 start -->
<div id="socialGroupModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="socialGroupModalLabel">
	<div class="main-modal-controller">
		<div class="main-modal-center">
			<div class="modal-dialog" role="document">
				<div class="modal-content social">
					<div class="modal-header group">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
					</button>
							<h4 class="modal-title" id="gridSystemModalLabel">그룹 북마크 가져가기</h4>
					</div>
					<div class="modal-body row">
						<div class="completed-modal-left group">
							<form id="form-to-mybookmark-left" action="../user/addtomybookmark.do" method="post">
				                <h4 class="completed-modal-from" hidden="true"><b>URL :</b>
						        	<a class="groupshare-url" name="url"></a></h4>
						        <h4 class="completed-modal-from"><b>From : <span id="from-text" class="groupname"> </span></b></h4>
				                <div class="jstree-from" id="jstree-from-left-group">
				                
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
			                   	<button class="btn btn-secondary groupshare dropdown-toggle" type="button" id="dropdownMenuButton-group" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			                       	Click <span class="caret"></span>
			                   	</button>
			                   	<div class="dropdown-menu" aria-labelledby="dropdownMenuButton-group">
			                       	<li id="dropdown-my-bookmark-getgroup" class="dropdown-item-mybook">My Bookmark</li>
			                       	<hr class="divider-hr">
			                       	<li class="dropdown-item dropdown-submenu">
					                	<a tabindex="-1">Group</a>
					                </li>
			                   	</div>
				      		</div>
			                <div class="jstree-to" id="jstree-to-right-group" style="float:right;">
			                
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
<!-- social 그룹 북마크 가져가기 end  -->

<!-- social 작성자 파도타기 start -->
<div id="socialSurfingModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="socialSurfingModalLabel">
	<div class="main-modal-controller">
		<div class="main-modal-center">
			<div class="modal-dialog" role="document">
				<div class="modal-content social">
					<div class="modal-header surfingshare">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
					</button>
							<h4 class="modal-title" id="gridSystemModalLabel">회원 URL 파도타기</h4>
					</div>
					<div class="modal-body row">
						<div class="completed-modal-left surfingshare">
							<form id="form-to-mybookmark-left" action="../user/addtomybookmark.do" method="post">
				                <h4 class="completed-modal-from" hidden="true"><b>URL :</b>
						        	<a class="indishare-url-surfing" name="url"></a></h4>
						        <h4 class="completed-modal-from"><b>From : <span id="from-text" class="nname">[회원이름]</span></b></h4>
				                <div class="jstree-from" id="jstree-from-left-all">
				                </div>
								<input type="hidden" class="indishare-urlname-left" value="" name="urlname" readonly>
								<input type="hidden" class="indishare-userpid-left" value="" name="pid" readonly>
								<input type="hidden" class="indishare-abid-left" value="" name="abid" readonly>
								<input type="hidden" class="indishare-gid-left" value="" name="gid" readonly>
							</form>
			            </div>   
			            <div class="completed-modal-right-all surfingshare">
			               	<h4 class="completed-modal-to"><b>To : </b></h4>
			               	<!-- Dropdown -->
			               	<div class="dropdown completed-modal-dropdown">
			                   	<button class="btn btn-secondary surfingshare dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			                       	Click <span class="caret"></span>
			                   	</button>
			                   	<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
			                       	<li id="dropdown-my-bookmark" class="dropdown-item-surfing">My Bookmark</li>
			                       	<hr class="divider-hr">
			                       	<li class="dropdown-item dropdown-submenu surfing">
					                	<a tabindex="-1">Group</a>
					                </li>
			                   	</div>
				      		</div>
			                <div class="jstree-to" id="jstree-to-right-all" style="float:right;">
			                </div>
			            </div>
					</div>
					<div class="modal-footer surfingshare">
						<button type="button" class="btn btn-default surfingshare" data-dismiss="modal">취소</button>
						<button id="into-my-bookmark-surfing-btn" type="button" class="btn btn-primary surfingshare" style="display: inline;">확인</button>
						<button id="into-group-bookmark-surfing-btn" type="button" class="btn btn-primary surfingshare" style="display: none;">확인</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div>
	</div>
</div><!-- /.modal -->
<!-- social 작성자 파도타기 end -->

<section class="ranking-div">
	<div class="container">
		<div class="row">
			<!-- Individual TOP5 DIV START -->
			<!-- top5 list start -->
			<div class="col-sm-5">
				<div class="panel-body rank-table">
					<span class="ranktitle"><img src="<%=request.getContextPath()%>/icon/trophy.png"
						class="rankimg">개인 Top 5</span>
					<table class="top5-table">
						<thead>
							<tr>
								<th>Rank</th>
								<th>사이트명</th>
								<th>공유개수</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${u_top5}" var="user_top" varStatus="status">
								<tr>
									<c:choose>
										<c:when test="${status.count == '1'}">
											<td class="rank"><img class="rankimg"
												src="<%=request.getContextPath()%>/icon/gold.png">${status.count}</td>
										</c:when>
										<c:when test="${status.count == '2'}">
											<td class="rank"><img class="rankimg"
												src="<%=request.getContextPath()%>/icon/silver.png">${status.count}</td>
										</c:when>
										<c:when test="${status.count == '3'}">
											<td class="rank"><img class="rankimg"
												src="<%=request.getContextPath()%>/icon/bronze.png">${status.count}</td>
										</c:when>
										<c:when test="${status.count == '4'}">
											<td class="rank"><img class="rankimg"
												src="<%=request.getContextPath()%>/icon/medal2.png">${status.count}</td>
										</c:when>
										<c:when test="${status.count == '5'}">
											<td class="rank"><img class="rankimg"
												src="<%=request.getContextPath()%>/icon/medal2.png">${status.count}</td>
										</c:when>
									</c:choose>
									<td><a href="${user_top.url}" target="_blank">${user_top.urlname}</a></td>
									<td>${user_top.ucount}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<!-- top5 list end -->
			<!-- Individual TOP5 DIV END -->
			<div class="col-sm-2"></div>
			
			<!-- Group TOP5 DIV START -->
			<!-- Gtop5 list start -->
			<div class="col-sm-5">
				<div class="panel-body rank-table">
					<span class="ranktitle"><img src="../icon/trophy.png"
						class="rankimg">그룹 Top 5</span>
					<table class="top5-table">
						<thead>
							<tr>
								<th>Rank</th>
								<th>사이트명</th>
								<th>공유개수</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${g_top5}" var="group_top" varStatus="status">
								<tr>
									<c:choose>
										<c:when test="${status.count == '1'}">
											<td class="rank"><img class="rankimg"
												src="<%=request.getContextPath()%>/icon/gold.png">${status.count}</td>
										</c:when>
										<c:when test="${status.count == '2'}">
											<td class="rank"><img class="rankimg"
												src="<%=request.getContextPath()%>/icon/silver.png">${status.count}</td>
										</c:when>
										<c:when test="${status.count == '3'}">
											<td class="rank"><img class="rankimg"
												src="<%=request.getContextPath()%>/icon/bronze.png">${status.count}</td>
										</c:when>
										<c:when test="${status.count == '4'}">
											<td class="rank"><img class="rankimg"
												src="<%=request.getContextPath()%>/icon/medal2.png">${status.count}</td>
										</c:when>
										<c:when test="${status.count == '5'}">
											<td class="rank"><img class="rankimg"
												src="<%=request.getContextPath()%>/icon/medal2.png">${status.count}</td>
										</c:when>
									</c:choose>
									<td><a href="${group_top.url}" target="_blank">${group_top.urlname}</a></td>
									<td>${group_top.ucount}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<!-- Gtop5 list end -->
		</div>
		<!-- Group TOP5 DIV END -->
	</div>
</section>
<!-- Top5 DIV END -->
<section class="slideimg"></section>

<!-- Share Bookmark div START -->
<section class="bookmark-share-div">
	<!-- <img src="../images/social/books.png" class="bg-bottom"> -->
	<div class="container">
		<div class="row">
			<!-- Individual Share Bookmark START -->
			<!-- u_booklist start -->
			<div class="col-sm-6">
				<div class="panel-body">
					<span class="share-head"><img src="../icon/hash.png"
						class="shareimg">개인 북마크 공유</span>
					<table class="table table-hover" id="listTable1">
						<thead>
							<tr>
								<th class="table-site">사이트명</th>
								<th class="table-tag">태그</th>
								<th class="table-write">작성자</th>
								<th class="table-date">공유날짜</th>
								<th class="table-click">조회수</th>
								<th class="table-icon"></th>
							</tr>
						</thead>
						<!-- 개인 북마크 공유 목록 START -->
						<tbody>
							<c:forEach items="${u_list}" var="u_booklist">
								<tr id="social_${u_booklist.ubid}">
									<td class="table-site"><a onclick="indiViewCount(${u_booklist.ubid})" href="${u_booklist.url}" target="_blank">${u_booklist.sname}</a></td>
									<td class="table-tag">"${u_booklist.htag}"</td>
									<td class="table-write"><a onclick="surfing_modal(this)" id="${u_booklist.nname}">${u_booklist.nname}</a></td>
									<td class="table-date">${u_booklist.sdate}</td>
									<td class="table-click indiCount">${u_booklist.view}</td>
									<td class="table-icon indi-share getbookmark" data-toggle="modal" data-target="#socialIndiModal" data-title="${u_booklist.url}" data-urlname = "${u_booklist.sname}">
										<i class="fa fa-share "></i>
									</td>
								</tr>
							</c:forEach>
						</tbody>
						<!-- 개인 북마크 공유 목록 END -->
					</table>
				</div>
			</div>
			<!-- u_booklist end -->
			<!-- Individual Share Bookmark START -->

			<!-- Group Share Bookmark START -->
			<div class="col-sm-6">
				<div class="panel-body">
					<span class="share-head"><img src="../icon/hash.png"
						class="shareimg">그룹 북마크 공유</span>
					<table class="table table-hover" id="listTable2">
						<thead>
							<tr>
								<th class="table-groupname">그룹명</th>
								<th class="table-tag">태그</th>
								<th class="table-write">그룹장</th>
								<th class="table-date">공유날짜</th>
								<th class="table-click">조회수</th>
								<th class="table-icon"></th>
							</tr>
						</thead>
						<!-- 그룹 북마크 공유 목록 start -->
						<tbody>
							<c:forEach items="${g_list}" var="g_booklist">
								<tr id="group_${g_booklist.gid}">
									<td class="table-groupname">${g_booklist.gname}</td>
									<td class="table-tag">"${g_booklist.htag}"</td>
									<td class="table-write"><a onclick="surfing_modal(this)" id="${g_booklist.nname}">${g_booklist.nname}</a></td>
									<td class="table-date">${g_booklist.duedate}</td>
									<td class="table-click groupCount">${g_booklist.view}</td>
									<td class="table-icon">
										<a onclick="socialGetGroup(this, ${g_booklist.gid})" id="${g_booklist.gid}" name="${g_booklist.gname}"><i class="fa fa-share get_groupbook"></i></a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
						<!-- 그룹 북마크 공유 목록 end -->
					</table>
				</div>
			</div>
			<!-- Group Share Bookmark END -->
		</div>
	</div>
</section>


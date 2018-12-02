<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<%@ taglib prefix="se" uri="http://www.springframework.org/security/tags" %>

	<!-- Main에서 개인 북마크로 가져가기 모달 START -->
	<div id="mainIndiModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mainIndiModalLabel">
		<div id="main-modal-controller">
			<div id="main-modal-center">
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
								<form id="form-to-mybookmark" action="user/addtomybookmark.do" method="post">
					                <h4 class="completed-modal-from"><b>URL :</b>
					                	<input type="text" class="indishare-url" value="" name="url" readonly></h4>
									<input type="hidden" class="indishare-urlname" value="" name="urlname" readonly>
									<input type="hidden" class="indishare-userpid" value="" name="pid" readonly>
									<input type="hidden" class="indishare-abid" value="" name="abid" readonly>
									<input type="hidden" class="indishare-gid" value="" name="gid" readonly>
								</form>
				            </div>
				            <hr>
				            <div class="completed-modal-left">
				                <h4 class="completed-modal-to"><b>가져가기 : </b></h4>
				
				                <!-- Dropdown -->
				                <div class="dropdown completed-modal-dropdown">
				                    <button id="dropdownMenuButton" class="btn btn-secondary indishare dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				                    	Click <span class="caret"></span>
				                    </button>
				                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
				                        <li id="dropdown-my-book" class="dropdown-i-tem">My Bookmark</li>
				                        <hr class="divider-hr">
				                        <li class="dropdown-item dropdown-submenu">
				                            <a tabindex="-1">Group</a>
				                        </li>
				                    </div>
				                </div>
				
				                <div id="jstree-to-bottom" style="clear: both;"></div>
				            </div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default indishare" data-dismiss="modal">취소</button>
							<button id="into-my-bookmark" type="button" class="btn btn-primary" style="display: block;">확인</button>
							<button id="into-group-bookmark" type="button" class="btn btn-primary" style="display: none;">확인</button>
						</div>
					</div><!-- /.modal-content -->
				</div><!-- /.modal-dialog -->
			</div><!-- /.modal -->
		</div>
	</div>
	<!-- MAIN to MY BOOK 가져가기 div END -->

	<!-- Home slider START-->
    <section id="home-slider">
        <div class="container">
            <div class="row">
                <div class="main-slider">
                    <div class="slide-text">
                        <!-- introduce text START -->
                        <div class="introduce-text">
                        	<span class="intro-text">어디서든 빠르고 쉽게</span><br>
							<span class="intro-span1">
								<span class="intro-text1">개발자</span>가 사용하기 좋은 사이트들을 제공해줍니다!</span><br>
							<span class="intro-span2">
								<span class="intro-text2">자신만의 북마크</span>를 만들고</span><br>
							<span class="intro-span3">
								<span class="intro-text3">그룹</span>과
								<span class="intro-text3">소셜</span> 페이지 에서</span><br>
							<span class="intro-span4">다른 사람들과 
								<span class="intro-text2">북마크를 공유</span>해보세요!</span><br>
                        </div>
                        <!-- introduce text END -->
                        <!-- Login / Roll in Button START -->
                        
                        <%-- <se:authorize access="!hasRole('ROLE_USER')"> --%>
                        <c:if test="${sessionScope.info_userid == null}">
                        <a href="javascript:void(0)" data-toggle="modal" onclick="openLoginModal();" class="btn btn-common">LOG IN</a>
                        <a href="javascript:void(0)" data-toggle="modal" onclick="openRegisterModal();" class="btn btn-common">SIGN UP</a>
                        </c:if>
                        <%-- </se:authorize> --%>
                        <!-- Login / Roll in Button END -->
                    </div>
                   	
                    <!-- Login / Roll in / password find modal START -->
                    <div id="main-modal-controller">
					<div id="main-modal-center">
                    <div class="modal fade login" id="loginModal" style="top: 10%;">
                        <div class="modal-dialog login animated">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                    <h4 class="modal-title">Login with</h4>
                                </div>
                                <div class="modal-body">
                                    <div class="box">
                                        <div class="content">
                                            <div class="social">
                                                <a class="logo-text-modal" href="#">뿌리깊은마크</a>
                                                
                                                <a id="google_login" class="circle google" href="#">
                                                    <i class="fab fa-google"></i>
                                                </a>
                                                <script type="text/javascript">
                                                	$('#google_login').dblclick(function() {});
                                                	$('#google_login').click(function() {
                                                		$.ajax({ 
                                                			url:"joinus/googleLogin",
                                                	        type:"GET",
                                                	        dataType:"json", 
                                                	        beforeSend: function() { $('html').css("cursor", "wait"); },
                                                	        complete: function() { $('html').css("cursor", "auto"); },
                                                	        success: function(data){
                                                	 			//alert(data.url);
                                                	        	location.href= data.url;
                                                	        }
                                                	    });
                                                	});
                                                </script>
                                                
                                                <a id="facebook_login" class="circle github" href="#">
                                                    <i class="fab fa-facebook-f"></i>
                                                </a>
                                                <script type="text/javascript">
                                                	$('#facebook_login').dblclick(function() {});
                                                	$('#facebook_login').click(function() {
                                                		$.alert("준비중입니다!")
                                                	});
                                                </script>
                                                
                                                <a id="naver_login" class="circle facebook" href="#">
                                                    <span class="naver">N</span>
                                                </a>
                                                <script type="text/javascript">
                                                	$('#naver_login').dblclick(function() {});
                                                	$('#naver_login').click(function() {
                                                		$.alert("준비중입니다!")
                                                	});
                                                </script>
                                            </div>
                                                
                                            <div class="division">
                                                <div class="line l"></div>
                                                	<span>or</span>
                                                <div class="line r"></div>
                                            </div>
                                            <div class="error"></div>
                                            <div class="form loginBox">
                                                <!-- Login START -->
                                                <form id="login-form">
                                                	<strong class="error-text"></strong>
                                                    <input id="uid" class="form-control" type="text" placeholder="Email" name="uid" maxlength="33">
                                                    <input id="pwd" class="form-control" type="password" placeholder="Password" name="pwd" maxlength="15">
                                    
                                                    <input id="loginAjax" class="btn btn-default btn-login" type="button" value="Login">
                                                </form>
                                                <!-- Login END -->
                                            </div>
                                        </div>
                                    </div>
                                    <div class="box">
                                        <div class="content registerBox" style="display:none;">
                                            <!-- Roll in START -->
                                            <div class="form">
                                                <form id="rollin-form">
                                                	<strong class="error-text"></strong>
                                                    <input id="uid_join" class="form-control" type="text" placeholder="Email@example.com" name="uid" autocomplete="off" maxlength="33">
                                                    <div id="auth-div" class="form-group" style="display: none">
                                                       <input id="authcode_join" class="form-control" type="text" placeholder="Auth Code" name="authcode" autocomplete="off" maxlength="10">
                                                       <input id="authcode_check" class="btn btn-default" type="button" value="인증키 재전송">
                                                   	</div>
                                                   	
                                                    <input id="pwd_join" class="form-control" type="password" placeholder="Password" name="pwd" autocomplete="off" maxlength="15">
                                                    <input id="pwd_confirmation" class="form-control" type="password" placeholder="Repeat Password" name="pwd_confirmation" autocomplete="off" maxlength="15">
                                                    <input id="nname_join" class="form-control" type="text" placeholder="Nickname" name="nname" autocomplete="off" maxlength="10">

                                                    <div>
                                                    	<input id="agree-site-rule" class="form-check-input" type="checkbox"><span class="agree-site-rule-text">
                                                    	뿌리깊은마크를 악의적인 용도로 사용하면 안됩니다.</span>
                                                    </div>
                                                    <input id="rollinAjax" class="btn btn-default btn-register" type="button" value="Create account" name="commit">
                                                </form>
                                            </div>
                                            <!-- Roll in END -->
                                        </div>
                                    </div>
                                    <div class="box">
                                        <div class="content findBox" style="display:none;">
                                            <!-- password find START -->
                                            <div class="form">
                                                <form id="find-form">
                                                    <input id="uid_find" class="form-control" type="text" placeholder="Email">
                                                    <input id="check_email_find" class="btn btn-default btn-find" type="button" value="Are you a member?">
                                                    
                                                    <p class="confrim_code_find" style="display: none;"><b>회원님</b>의 이메일로 인증 코드가 발송되었습니다.</p>
                                                    <input id="authcode_find" class="form-control confrim_code_find" type="text" placeholder="Authcode" style="display: none;">
                                                    <input id="find-password" class="btn btn-default btn-find confrim_code_find" type="button" value="Find Password" style="display: none;">
                                                </form>
                                            </div>
                                            <!-- password find END -->
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <div class="forgot login-footer">
                                        <span>아직 계정이 없으신가요?
                                                 <a class="login-form" href="javascript: showRegisterForm();">회원가입</a>
                                            </span><br>
                                        <span>비밀번호를 잊으셨나요?
                                                 <a class="login-form" href="javascript: showFindForm();">비밀번호 찾기</a>
                                            </span>
                                    </div>
                                    <div class="forgot register-footer" style="display:none">
                                        <span>이미 계정을 가지고 계신가요?</span>
                                        <a class="login-form" href="javascript: showLoginForm();">로그인</a>
                                    </div>
                                    <div class="forgot find-footer" style="display:none">
                                        <span></span>
                                        <a class="login-form" href="javascript: showLoginForm();">돌아가기</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    </div>
                    </div>
                    <!-- Login / Roll in / password find modal END -->
                    <img src="images/home/slider/hill.png" class="slider-hill" alt="slider image">
                </div>
            </div>
        </div>
        <div class="preloader"><i class="fa fa-sun-o fa-spin"></i></div>
    </section>
    <!-- Home slider END-->
    
    <!-- Admin bookmark & Preview START -->
    <section class="main-admin-category">
        <div class="container">
            <div class="row">
                <!-- Admin Bookmark & Category Area START -->
                <div class="col-sm-12">
                    <!-- Admin Bookmark & Search Area START -->
                    <span class="bookmark-title">
                        <i class="fas fa-seedling" aria-hidden="true" style="color: #54e00e"></i>&nbsp;&nbsp;추천 사이트
                    </span>
                    <div id="custom-search-input">
                        <div class="input-group">
                            <span class="input-group-btn"></span>
                        </div>
                    </div>
                    <hr class="hr-clear">
                    <!-- Admin Bookmark & Search Area END -->

                    <!-- Category Area START -->
                    <div id="category-div" class="col-sm-12">
                        <div class="category-div">
                            <span class="bookmark-category">
                            <i class="fa fa-tags" aria-hidden="true" style="color: #294400; font-size: 20px;"></i>&nbsp;카테고리
                          </span>
                        </div>
                        <div id="category-display" class="col-sm-12 category-items">
                            <div id="showall" class="category reddiv" style="background-color: #008B8B">
                                <span class="category-title">Show All</span></div>
                            <c:forEach items="${categoryList}" var="cList">
                            	<div id="${cList.acid}" data-category="${cList.acname}" class="category" style="background-color: ${cList.color}">
                                <span class="category-title">${cList.acname}</span></div>
                            </c:forEach>
                        </div>
                    </div>
                    <hr class="hr-clear">
                    <!-- Category Area END -->
                </div>
                <!-- Admin Bookmark & Category Area END -->

                <!-- URL Table Area START -->
                <div class="col-sm-6">
                    <ul class="ul-table">
                    	<c:forEach items="${categoryList}" var="cList">
                    	<li id="${cList.acname}">
                            <div class="component">
                                <h2 class="component_title color4" style="border-bottom: 3px solid ${cList.color}">
                                    <i class="fa fa-folder-open show_close_img" aria-hidden="true" style="color: ${cList.color}"></i>
                                    <span class="title">${cList.acname}</span>
                                </h2>
                                <ul>
                                	<c:set var="index">1</c:set>
                                	<c:forEach items="${bookList}" var="bList" varStatus="status">
                                	<c:choose>
                                		<c:when test="${cList.acid == bList.acid}">
                               			<li>
                               				<%-- <se:authorize access="isAuthenticated()"> --%>
                               				<c:if test="${sessionScope.info_userid != null}">
	                                        <button class="url_hover_btn" type="button" data-toggle="modal" data-target="#mainIndiModal">
	                                        	<img class="zoom_img" src="icon/url_save.png"></button>
	                                        </c:if>
	                                       	<%-- </se:authorize> --%>
	                                        <button class="url_hover_btn" type="button">
	                                        <img class="zoom_img" src="icon/open_preview.png" onclick="preview(${bList.abid})"></button>
	                                        <img class="favicon" src="https://www.google.com/s2/favicons?domain=${bList.url}">
	                                        <p class="url ${bList.abid}" data-abid="${bList.abid}"
				                                        			     data-url="${bList.url}"
				                                        			     data-regdate="${bList.regdate}"
				                                        			     data-views="${bList.view}"
				                                        			     data-urlname="${bList.urlname}">${bList.urlname}
	                                        			   <img class="url_link_btn" src="icon/open_link.png">
	                                        			   <c:if test="${index <= 2}">
	                                        			   		<i class="fas fa-h-square" style="color: #ff5400;"></i>
	                                        			   		<c:set var="index" value="${index + 1}"></c:set>
	                                        			   		
	                                        			   </c:if>
	                                        			   <script type="text/javascript">
	                                        			   		isNewURL("${bList.abid}", "${bList.regdate}");
	                                        			   </script>
	                                        </p>
	                                    </li>
                                		</c:when>
                                	</c:choose>
                                    </c:forEach>
                                </ul>
                            </div>
                        </li>
                    	</c:forEach>
                    </ul>
                </div>
                <!-- URL Table Area END -->

                <!--Preview Floating Box Area START -->
                <div class="col-sm-6">

                    <div id="floatMenu">
                        <div id="preview_title">
                            <h2>
                                <i class="fa fa-rss" aria-hidden="true" style="color: #287bfb"></i>
                                <span class="title">Preview</span>
                            </h2>
                        </div>
                        <div id="preview_content" >
                            <div id="layout">미리보기: Page Image</div>
                            <div id="comment" >
                            	<div id="comment-detail" >
                            		설명 Detail
                            	</div>
                            	<div id="ajax-loading-div">
                            	</div>
                            	<div id="world-ranking-visitor">
                            	</div>
                            	<div id="url-sub-domain">
                            	</div>
                            </div>
                        </div>
                        <div id="advertise_content">

                        </div>
                    </div>
                </div>
                <!--Preview Floating Box Area END -->
            </div>
        </div>
    </section>
    <!-- Admin bookmark & My bookmark END -->
    
    <!-- Bookmark url input START -->
    <div class="modal fade addBookmark" id="addBookmarkModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <form name="add" method="post" action="/addBookmark" accept-charset="UTF-8">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title"></h4>
                    </div>

                    <div class="modal-body">
                        <div class="box">
                            <!--one floor insert-->
                            <div class="content">
                                <div class="form urlBox">
                                    <label class="control-label" for="url">URL을 입력하세요</label>
                                    <input id="url" class="form-control" type="text" placeholder="URL" name="url"><br>

                                </div>
                            </div>
                        </div>

                        <div class="box">
                            <!-- Two floor -->
                            <div class="content categoryBox" style="display:none;">
                                <div class="form">
                                    <label class="control-label" for="urlname">제목</label>
                                    <input id="urlname" class="form-control" type="text" placeholder="URLNAME" name="urlname"><br>
                                    <label class="control-label" for="pid">카테고리(임시)</label><br>
                                    <select id="pid" class="form-control" name="pid">
                                            <option>카테고리 1-1</option>
                                            <option>카테고리 1-2</option>
                                        </select>
                                        <select id="ubid" class="form-control" name="ubid">
                                            <option>카테고리 2-1</option>
                                            <option>카테고리 2-2</option>
                                        </select><br><br>
                                    <div>
                                        <label class="control-label" for="share">공유</label>
                                        &nbsp;&nbsp;
                                        <input id="share" type="checkbox" name="share" value="share">
                                    </div>
                                    <br>
                                </div>
                            </div>
                        </div>

                        <div class="box">
                            <!-- Three floor -->
                            <div class="content shareBox" style="display:none;">
                                <div class="form">
                                    <label class="control-label" for="sname">공유제목</label>
                                    <input id="sname" class="form-control" type="text" placeholder="SNAME" name="sname"><br>
                                    <label class="control-label" for="htag_input">해시태그</label>
                                    <input id="htag_input" class="form-control" type="text" placeholder="HTAG_input" name="htag_input" onkeydown="addHashtag()"><br>
                                    <div id="htag_append"></div><br>
                                    <input id="htag" class="form-control" type="hidden" placeholder="HTAG" name="htag"><br>
                                </div>
                            </div>
                        </div>
                        <!--</form>-->
                    </div>

                    <div class="modal-footer">
                        <div class="url-footer">
                            <input class="btn btn-default btn-next" type="button" value="다음" onclick="showCategoryForm()">
                        </div>
                        <div class="category_unshared-footer" style="display:none">
                            <input class="btn btn-default btn-prev" type="button" value="이전" onclick="showUrlForm()">
                            <input class="btn btn-default btn-comp" type="button" value="등록" onclick="addBookmarkButton()">
                        </div>

                        <div class="category_share-footer" style="display:none">
                            <input class="btn btn-default btn-prev" type="button" value="이전" onclick="showUrlForm()">
                            <input class="btn btn-default btn-next" type="button" value="다음" onclick="showShareForm()">
                        </div>
                        <div class="share-footer" style="display:none">
                            <input class="btn btn-default btn-prev" type="button" value="이전" onclick="showCategoryForm()">
                            <input class="btn btn-default btn-comp" type="button" value="등록" onclick="addBookmarkButton()">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- Bookmark url input END -->
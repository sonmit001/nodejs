<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="home.do">myungsoo project</a>
		</div>
		<ul class="nav navbar-nav">
			<li><a href="getMemberList.do">회원 정보</a></li>
			<li><a href="gounslider.do">slick 슬라이더</a></li>
			<li><a href="goBoard.do">메인 게시판</a></li>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<c:choose>
				<c:when test="${sessionScope.ui != null}">
					<li><a href="#"><c:out value="${sessionScope.ui.nickname}"></c:out> 님</a></li>
					<li><a href="logOut.do"><span class="glyphicon glyphicon-log-in"></span> LogOut</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="signUp.do"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
      				<li><a href="logIn.do"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
				</c:otherwise>
			</c:choose>
   		</ul>
	</div>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>뿌리깊은마크</title>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/icon/book_favicon.ico">
	<meta property="og:url" content="">
    <meta property="og:title" content="뿌리깊은마크">
    <!-- <meta property="og:image" content="https://s.pstatic.net/static/www/mobile/edit/2016/0705/mobile_212852414260.png"> -->
    <meta property="og:description" content="개발자가 사용하기 좋은 사이트들을 제공해줍니다.자신만의 북마크를 꾸미세요">
	
	<!-- Latest compiled Bootstrap Common CSS -->
	<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- Bootstrap Common CSS END -->
    
    <!-- jQuery Confirm START -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.css">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.js"></script>
	<!-- jQuery Confirm END -->

	<!-- Common Script START -->
    <!-- Latest compiled JavaScript & CSS -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
    <link rel=stylesheet href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Script Common JavaScript & CSS END -->

	<!-- CSS START -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/AdminLTE.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/skin.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/pace.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/app.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/pagetransition.css">
	<!-- CSS END -->

	<!-- adminTable CSS START -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/adminTable.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/dataTables.bootstrap.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/colorPick.css?ver=2">
	<!-- adminTable CSS END -->

	<!--CHART Resources START-->
	<script src="https://www.amcharts.com/lib/3/amcharts.js"></script>
	<script src="https://www.amcharts.com/lib/3/serial.js"></script>
	<script src="https://www.amcharts.com/lib/3/plugins/export/export.min.js"></script>
	<link rel="stylesheet" href="https://www.amcharts.com/lib/3/plugins/export/export.css" type="text/css" media="all" />
	<script src="https://www.amcharts.com/lib/3/themes/dark.js"></script>
	<!--CHART Resources END-->
	
	<!-- jQuery Ajax Form START -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js" integrity="sha384-FzT3vTVGXqf7wRfy8k4BiyzvbNfeYjK+frTVqZeNDFl8woCbF0CYG6g2fMEFFo/i" crossorigin="anonymous"></script>
	<!-- jQuery Ajax Form END -->
	
	
	
<title>뿌리깊은마크 관리자 페이지</title>
</head>
<body class="skin-blue sidebar-mini pace-done"
	style="overflow-y: scroll">
	<div id="main-header">
		<tiles:insertAttribute name="header" />
	</div>

	<div id="main">
		<tiles:insertAttribute name="content" />
	</div>

	<div id="main-footer">
		<tiles:insertAttribute name="footer" />
	</div>

	<!-- Script START -->
	<script src="https://www.google-analytics.com/analytics.js"></script>
	<script src="${pageContext.request.contextPath}/js/admin/app.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/admin/pace.js"></script>
	<script src="//d2wy8f7a9ursnm.cloudfront.net/v4/bugsnag.min.js"></script>
	<script src="//d2wy8f7a9ursnm.cloudfront.net/bugsnag-plugins/v1/bugsnag-vue.min.js"></script>
	<!-- Script END -->
	
	<!-- adminTable JS START -->
    <script src="${pageContext.request.contextPath}/js/admin/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/admin/dataTables.bootstrap.min.js"></script>
    <!-- adminTable JS END -->
    
	<!-- Color Picker JS START -->
	<script src="${pageContext.request.contextPath}/js/admin/colorPick.js"></script>
</body>
</html>
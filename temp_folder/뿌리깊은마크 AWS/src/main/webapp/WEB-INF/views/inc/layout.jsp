<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="se" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>뿌리깊은마크</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/icon/book_favicon.ico">
    <meta property="og:url" content="">
    <meta property="og:title" content="뿌리깊은마크">
    <!-- <meta property="og:image" content="https://s.pstatic.net/static/www/mobile/edit/2016/0705/mobile_212852414260.png"> -->
    <meta property="og:description" content="개발자가 사용하기 좋은 사이트들을 제공해줍니다.자신만의 북마크를 꾸미세요">
    
    <!-- Latest compiled Bootstrap Common CSS -->
    <script type="text/javascript" src="js/jquery.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- Bootstrap Common CSS END -->
    
    <!-- Common Script START -->
    <!-- Latest compiled JavaScript & CSS -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <!-- Script Common JavaScript & CSS END -->

    <!-- Google Icon CDN -->
	<link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<!-- Google Icon CDN END -->

    <!-- Main Page CSS -->
    <link href="css/mainpage/main.css?ver=2" rel="stylesheet">
    <link href="css/mainpage/modal.css?ver=2" rel="stylesheet">
    <link href="css/mainpage/header.css" rel="stylesheet">
    <link href="css/mainpage/footer.css" rel="stylesheet">
    <link href="css/mainpage/list_table.css?ver=1" rel="stylesheet">
    <link href="css/mainpage/responsive.css" rel="stylesheet">
    <link href="css/mainpage/login-register.css?ver=2" rel="stylesheet" />
    <link href="css/addBookmarkStepModal-register.css" rel="stylesheet" />
    <link href="css/animate.min.css" rel="stylesheet">
    <!-- Main Page CSS END -->
    
    <!-- Text Effect(Alarm) -->
    <link href="${pageContext.request.contextPath}/css/text-effect.css" rel="stylesheet">
    <!-- Text Effect(Alarm) END -->
    
    <!-- User Info Page CSS -->
    <link href="css/userinfo/userinfo.css?ver=2" rel="stylesheet">
	<!-- User Info CSS END -->
	
    <!-- Login / roll-in Modal Script Start -->
    <script src="js/script.js"></script>
    <!-- Login / roll-in Modal Script Start END -->
    
    <!-- URL Bookmark Modal Script Start -->
    <script src="js/modal.js"></script>
    <!--  URL Bookmark Modal Script END -->

    <!-- Category Input Script START -->
    <script src="js/category_insert.js"></script>
    <!-- Category Input Script END -->
    
	<!-- jQuery Confirm START -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.css">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.js"></script>
	<!-- jQuery Confirm END -->
	
	<!-- Custom Script START -->
    <script type="text/javascript" src="js/main.js"></script>
    <script type="text/javascript" src="js/wow.min.js"></script>
    <script type="text/javascript" src="js/login.js?ver=2"></script>
    <!-- Custom Script END -->
    
    <!-- Header Socket JS -->
	<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/team/stomp.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/header/alarm_socket.js?ver=1"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/header/alarm.js?ver=1"></script>
	<!-- Header Socket JS END -->
    
    <!-- jstree css & javascript -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
    <script src="${pageContext.request.contextPath}/js/jstree.min.js"></script>
    
    <!-- jQuery Ajax Form START -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js" integrity="sha384-FzT3vTVGXqf7wRfy8k4BiyzvbNfeYjK+frTVqZeNDFl8woCbF0CYG6g2fMEFFo/i" crossorigin="anonymous"></script>
	<!-- jQuery Ajax Form END -->
    
    <script>
    	/**************************  Category Click Evnet Start  *******************/
	    $(function(){
	    	
	    	var categoryList = new Array(); // 전체 카테고리 리스트 비동기로 받아오기
	    	
	    	<c:forEach items="${categoryList}" var="category">
	    		categoryList.push("${category.acname}");
	    	</c:forEach>
	    	
	    	var selectedCategory = [];
	    	
	    	$(document).on("click", ".category", function() {
	    		var id = $(this).text().trim();
	    		$(this).removeClass("animated jello").addClass("animated rubberBand");
	    		/* 
	    			category class를 클릭한 text가 Show All일 경우, 전체 카테고리 리스트를 slideDown!! 
	    			선택된 카테고리 리스트는 배경색 기존색으로 변경(removeClass)
	    			Show All 카테고리는 custom색으로 변경		
	    		*/
	    		if (id == "Show All") {
	    			$.each(categoryList, function(index, element) {
	    				$('li[id="' + element + '"]').slideDown("fast");
	    			});
	    			$.each(selectedCategory, function(index, element) {
	    				$(".category").removeClass("reddiv");
	    			});
	
	    			$("#showall").addClass("reddiv");
	    			selectedCategory = []; 
	
	    		} else {
	    			/* Show All이 아닌 카테고리 선택시 Show All style 배경색 기존색으로 변경(removeclass)*/
	    			$("#showall").removeClass("reddiv");
	    			
	    			/* 선택된 카테고리를 다시 클릭시 해당 카테고리만 SelectCategory에서 지우기 */
	    			if($(this).hasClass("reddiv") == true) {
	    				$(this).removeClass("reddiv");
	    				$(this).removeClass("animated rubberBand").addClass("animated jello");
	    				
	    				const idx = selectedCategory.indexOf(id);
	    				selectedCategory.splice(idx, 1);
	    				
	    				// 이미 선택된 카테고리가 1개 이상인 경우
	    				if(selectedCategory.length > 0){
	    					$.each(categoryList, function(index, element) {
	    						$('li[id="' + element + '"]').slideUp("fast");
	    					});
	    					$.each(selectedCategory, function(index, element) {
	    						$('li[id="' + element + '"]').slideDown("fast");
	    					});
	    				}else { // 선택된 카테고리가 하나도 없을 경우 Show All로 변경
	    					$.each(categoryList, function(index, element) {
	    						$('li[id="' + element + '"]').slideDown("fast");
	    					});
	    					$.each(selectedCategory, function(index, element) {
	    						$(".category").removeClass("reddiv");
	    					});
	
	    					$("#showall").addClass("reddiv");
	    				}
	    				
	    			}else {
	    				/* 
	    					카테고리를 선택한 경우 
	    					전체 카테고리 리스트 SlideUp, 선택된 카테고리 리스트는 SlideDown
	    				*/
	    				selectedCategory.push(id);
	    				$(this).addClass("reddiv");
	    				
	    				$.each(categoryList, function(index, element) {
	    					$('li[id="' + element + '"]').slideUp("fast");
	    				});
	    				
	    				$.each(selectedCategory, function(index, element) {
	    					if(index == 0) {
	    						$('li[id="' + element + '"]').slideDown("fast");
	    					}else {
	    						// 선택된 카테고리 보여줄 시 이전 카테고리 위로 insert해서 보여줌
	    						$('li[id="' + element + '"]').insertBefore($('li[id="' + selectedCategory[index-1] + '"]'));
	    						$('li[id="' + element + '"]').slideDown("fast");
	    					}
	    				});
	    			}
	    		}
	    	});
	    });
    	/**************************  Category Click Evnet End  *******************/
    </script>
</head>
<body>

	<div id="main-header">
		<tiles:insertAttribute name="header" />
	</div>
	
	
	<div id="main">
		<tiles:insertAttribute name="content" />
	</div>
	
	<div id="main-footer">
		<tiles:insertAttribute name="footer" />
	</div>
	
</body>
</html>
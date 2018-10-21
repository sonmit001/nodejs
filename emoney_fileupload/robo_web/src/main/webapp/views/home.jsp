<%@ page contentType = "text/html;charset=utf-8" %>

<html>
<head>
<title>Home</title>
<%@include file="/views/common/preset.jsp" %>
<link rel="stylesheet" type="text/css" href="/views/common/css/banner.css?ver=1.0" />
<!-- Popper JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<!-- Latest compiled JavaScript -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.css">
<script src="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.min.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	
	var list = $('.move_wrap > ul > li');
	var arr = $.makeArray(list);
	console.log(arr);
	arr.sort(function(left,right){
		var leftimg = $(left).find('img').prop('alt');
		var rightimg = $(right).find('img').prop('alt');
		
		if(leftimg < rightimg) return -1;
		if(leftimg > rightimg) return 1;
		
		return 0;
	});
	$('.move_wrap > ul').empty();
	
	$.each(arr, function(){
		$('.move_wrap > ul').append(this);
	})
	
	
	var slider = $('.move_wrap > ul').bxSlider({
		mode : 'horizontal',
		auto : true,
		pause : 2000,
		pager : true,
		controls:false,
		maxSlides: 4,
		moveSlides: 1,
		slideWidth: 478,
		startSlide : 0,
	});
	
	$('.btn_next').click(function(){
		slider.goToNextSlide();
		return false;
	})
	$('.btn_prev').click(function(){
		slider.goToPrevSlide();
		return false;
	})
	
	$('.carousel-showmanymoveone .carousel-item').each(function(){
	    var itemToClone = $(this);
	    for (var i=1;i<3;i++) {
	      itemToClone = itemToClone.next();

	      // wrap around if at end of item collection
	      if (!itemToClone.length) {
	        itemToClone = $(this).siblings(':first');
	      }

	      // grab item, clone, add marker class, add to collection
	      itemToClone.children(':first-child').clone()
	        .addClass("cloneditem-"+(i))
	        .appendTo($(this));
	    }
	  });
	
});

</script>
</head>

<body>
<c:set var="date" value="${requestScope.date}"/>
<c:set var="year" value = "${fn:substring(date,0,4)}"/>
<c:set var="month" value = "${fn:substring(date,4,6)}"/>
<c:set var="day" value = "${fn:substring(date,6,8)}"/>
<c:set var="time" value="${requestScope.time}"/>
<c:set var="hour" value= "${fn:substring(time,0,2)}"/>
<c:set var="min" value= "${fn:substring(time,2,4)}"/>
<c:set var="dayweek" value="${requestScope.dayweek}"/>

<div class="container">
	<h1>
		오늘의 날짜
	</h1>
	<P>${year}년 ${month}월 ${day}일 ${hour}시 ${min}분</P>
</div>
<%@include file="/views/common/nav.jsp" %>
<div id="wrap">
	<div class="visual_wrap visual_slide_move">
		<div class="visual">
	    	<p class="btn_prev"><a href="#link"><img src="//img.x1.co.kr/x1/images/main/right_arrow.png" alt="이전" /></a></p>
	        <div class="move_wrap">
	        	<div class="mask_left"><img src="//img.x1.co.kr/x1/images/main/mask2.png" alt="" /></div>
				<div class="mask_right"><img src="//img.x1.co.kr/x1/images/main/mask2.png" alt="" /></div>
	        	<ul>
					<li>
						<a href="/live.x1?action=notice_view&mtext_id=81307" target="banner01"><img src="//img.x1.co.kr/x1/images/popup/pop_main1055.png" alt="1" /></a>
					</li>
					
					<li>
						<a href="/live.x1?action=notice_view&mtext_id=81421&searchType=&searchText=&curPage=1"  target="banner03"><img src="//img.x1.co.kr/x1/images/popup/pop_main1057.png" alt="5" /></a>
					</li>
					<c:if test="${dayweek ne '토' && dayweek ne '일' }">
						<li>
							<a href="/live/expert.x1?action=home&anal_id=519" target="banner04"><img src="//img.x1.co.kr/x1/images/popup/pop_main1047.png" alt="4" /></a>
						</li>
						<c:if test="${time >= 0800 && time <= 1730 }">
							<li>
				                <img src="//img.x1.co.kr/x1/images/popup/pop_main1065.png" alt="2" usemap="#banner02"/>
				                <map name="banner02">
				                	<area shape="rect" coords="28,303,186,343" onclick="openFree('1188');" alt="공개방송">
				                    <area shape="rect" coords="268,303,423,344" href="/live.x1?action=notice_view&mtext_id=81673&searchType=&searchText=&curPage=1" alt="추석방송 일정보기" target="banner02">
				                </map>
			                </li>
		                </c:if>
					</c:if>
					<li>
	                    <a onclick="pop_event_common('/ad/?seq=4533&corp=x1', 666, 964, 'kakaoleading')" ><img src="//img.x1.co.kr/x1/images/popup/pop_main1040.png" alt="3" /></a>
	                </li>
	                
	            </ul>
	        </div>
	        <p class="btn_next"><a href="#link"><img src="//img.x1.co.kr/x1/images/main/left_arrow.png" alt="다음" /></a></p>
	        <a href="#" class="sample_bn_play">재생</a>
	        <a href="#" class="sample_bn_stop">정지</a>        
	    </div>
	</div>
</div>
<div>
<c:set var="result" value="${requestScope.result}"/>
${result}
</div>
<!-- bootstrap4 에서 지원해주는 carousel -->
<!-- <div class="row">
	<div class="col-md-12">
		<div id="boot4" class="carousel carousel-showmanymoveone slide " data-ride="carousel" data-interval="2000">
		  <ol class="carousel-indicators">
		    <li data-target="#boot4" data-slide-to="0" class="active"></li>
		    <li data-target="#boot4" data-slide-to="1"></li>
		    <li data-target="#boot4" data-slide-to="2"></li>
		  </ol>
		  <div class="row carousel-inner">
		    <div class="carousel-item active ">
		    	<div class="col-xs-4">
		    		<img class="d-block w-100" src="//img.x1.co.kr/x1/images/popup/pop_main1057.png" alt="첫번째 슬라이드">
		    	</div>
		    </div>
		    <div class="col carousel-item ">
		    	<div class="col-xs-4">
		      		<img class="d-block w-100" src="//img.x1.co.kr/x1/images/popup/pop_main1065.png" alt="두번째 슬라이드">
		    	</div>
		    </div>
		    <div class="col carousel-item">
		    	<div class="col-xs-4">
		     		<img class="d-block w-100" src="//img.x1.co.kr/x1/images/popup/pop_main1047.png" alt="세번째 슬라이드">
		    	</div>
		    </div>
		  </div>
		  <a class="carousel-control-prev" href="#boot4" role="button" data-slide="prev">
		    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
		    <span class="sr-only">이전</span>
		  </a>
		  <a class="carousel-control-next" href="#boot4" role="button" data-slide="next">
		    <span class="carousel-control-next-icon" aria-hidden="true"></span>
		    <span class="sr-only">다음</span>
		  </a>
		</div>
	</div>
</div>

 -->
	
	          
</body>
</html>

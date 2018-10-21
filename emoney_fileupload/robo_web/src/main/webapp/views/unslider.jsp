<%@ page contentType = "text/html;charset=utf-8" %>
<%@include file="/views/common/preset.jsp" %>
<html>
<head>
<title>Home</title>
<link rel="stylesheet" type="text/css" href="/views/common/css/banner.css?ver=1.0" />

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<!-- Popper JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
<script type="text/javascript" src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
<script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>

<!-- https://kenwheeler.github.io/slick/ -->
<script type="text/javascript">
$(document).ready(function(){
	 $('.banner_li').slick({
		slidesToShow: 4,
		slidesToScroll: 1,
		autoplay: true,
		autoplaySpeed: 2000,
		dots:true,
		nextArrow: $('.btn_next'), 
		prevArrow: $('.btn_prev'),
		
	}); 
});

</script>
</head>

<body>
<%@include file="/views/common/nav.jsp" %>
<div id="wrap">
	<div class="visual_wrap visual_slide_move">
		<div class="visual">
	    	<p class="btn_prev"><a href="#link"><img src="//img.x1.co.kr/x1/images/main/right_arrow.png" alt="이전" /></a></p>
	        <div class="move_wrap">
	        	<div class="mask_left"><img src="//img.x1.co.kr/x1/images/main/mask2.png" alt="" /></div>
				<div class="mask_right"><img src="//img.x1.co.kr/x1/images/main/mask2.png" alt="" /></div>
	        	<ul class="banner_li">
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
							<li>
				                <img src="//img.x1.co.kr/x1/images/popup/pop_main1065.png" alt="2" usemap="#banner02"/>
				                <map name="banner02">
				                	<area shape="rect" coords="28,303,186,343" onclick="openFree('1188');" alt="공개방송">
				                    <area shape="rect" coords="268,303,423,344" href="/live.x1?action=notice_view&mtext_id=81673&searchType=&searchText=&curPage=1" alt="추석방송 일정보기" target="banner02">
				                </map>
			                </li>
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
<%-- <div id="wrap">
	<div class="visual_wrap visual_slide_move">
		<div class="visual">
	    	<p class="btn_prev"><a href="#link"><img src="//img.x1.co.kr/x1/images/main/right_arrow.png" alt="이전" /></a></p>
	        <div class="move_wrap_unslider">
	        	<div class="mask_left"><img src="//img.x1.co.kr/x1/images/main/mask2.png" alt="" /></div>
				<div class="mask_right"><img src="//img.x1.co.kr/x1/images/main/mask2.png" alt="" /></div>
	        	
	        	<div class="banner">
					<div>
						<a href="/live.x1?action=notice_view&mtext_id=81307" target="banner01"><img src="//img.x1.co.kr/x1/images/popup/pop_main1055.png" alt="1" /></a>
					</div>					
					<div>
						<a href="/live.x1?action=notice_view&mtext_id=81421&searchType=&searchText=&curPage=1"  target="banner03"><img src="//img.x1.co.kr/x1/images/popup/pop_main1057.png" alt="5" /></a>
					</div>
					<c:if test="${dayweek ne '토' && dayweek ne '일' }">
						<div>
							<a href="/live/expert.x1?action=home&anal_id=519" target="banner04"><img src="//img.x1.co.kr/x1/images/popup/pop_main1047.png" alt="4" /></a>
						</div>
					<c:if test="${time >= 0800 && time <= 1730 }">
						<div>
		              	  <img src="//img.x1.co.kr/x1/images/popup/pop_main1065.png" alt="2" usemap="#banner02"/>
		               		 <map name="banner02">
		               	 	<area shape="rect" coords="28,303,186,343" onclick="openFree('1188');" alt="공개방송">
		               	    <area shape="rect" coords="268,303,423,344" href="/live.x1?action=notice_view&mtext_id=81673&searchType=&searchText=&curPage=1" alt="추석방송 일정보기" target="banner02">
		                </map>
	                </div>
	                </c:if>
					</c:if>
					<div>
	                    <a onclick="pop_event_common('/ad/?seq=4533&corp=x1', 666, 964, 'kakaoleading')" ><img src="//img.x1.co.kr/x1/images/popup/pop_main1040.png" alt="3" /></a>
	                </div>
	           </div>
	        </div>
	        <p class="btn_next"><a href="#link"><img src="//img.x1.co.kr/x1/images/main/left_arrow.png" alt="다음" /></a></p>
	        <a href="#" class="sample_bn_play">재생</a>
	        <a href="#" class="sample_bn_stop">정지</a>        
	    </div>
	</div>
</div> --%>
</body>
</html>

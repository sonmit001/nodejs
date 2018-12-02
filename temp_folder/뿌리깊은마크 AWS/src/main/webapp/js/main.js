
// New 아이콘 표시하기 함수
function isNewURL(abid, url_regdate) {
	var temp = url_regdate.split('-');
	var reg_date = new Date(temp[0], temp[1]-1, temp[2]); // 2003/07/16 00:00:00
	var today = new Date();
	today.setDate(today.getDate() - 15); // 30 일 전
	if(reg_date > today) {
		$(".url"+"."+abid).append('<i class="material-icons" style="color: #66bfff; font-size: 20px; vertical-align: bottom;">new_releases</i>');
	}
	return;
}

/* ******************** Header Scroll Shadow Start *************************** */
$(function() {
    var header = $('#header');
    $(window).scroll(function(e) {
        if (header.offset().top !== 0) {
            if (!header.hasClass('shadow')) {
                header.addClass('shadow');
            }
        } else {
            header.removeClass('shadow');
        }
    })
});
/* ******************** Header Scroll Shadow END ***************************** */

/* *************************  Main Table Start  ********************************* */
$(function() {
	// URL Click, VIEW +1
    $(document).on("click", ".url", function() { 
    	var abid = $(this).data('abid');
    	$.ajax({
    		url: "clickurl.do",
			type: "post",
			data : { abid : abid },
			success : function(data){
				//console.log(data.click);
			}
    	});
    });
    $('.url').on({
        mouseover: function() { $(this).children('.url_link_btn').css('display', 'block') },
        mouseleave: function() { $(this).children('.url_link_btn').css('display', 'none') }
    });
    $('li').on({
        mouseover: function() { $(this).children('button').css('display', 'block') },
        mouseleave: function() { $(this).children('button').css('display', 'none') }
    });
    $(document).on("click", ".fa-folder-open", function() {
        $(this).parent().parent().children('ul').hide(300);
        $(this).removeClass("fa-folder-open");
        $(this).addClass("fa-folder");
    });
    $(document).on("click", ".fa-folder", function() {
        $(this).parent().parent().children('ul').show(300);
        $(this).removeClass("fa-folder");
        $(this).addClass("fa-folder-open");
    });
});
/* *************************  Main Table END  ********************************* */

jQuery(function($) {
	'use strict';

	//Responsive Nav
	$('li.dropdown').find('.fa-angle-down').each(function() {
		$(this).on('click', function() {
			if ($(window).width() < 768) {
				$(this).parent().next().slideToggle();
			}
		return false;
		});
	});

	//Fit Vids
	if ($('#video-container').length) {
		$("#video-container").fitVids();
	}

	//Initiat WOW JS
	new WOW().init();

	// portfolio filter
	$(window).load(function() {

		$('.main-slider').addClass('animate-in');
		$('.preloader').remove();
		//End Preloader

		if ($('.masonery_area').length) {
			$('.masonery_area').masonry(); //Masonry
		}

		var $portfolio_selectors = $('.portfolio-filter >li>a');

		if ($portfolio_selectors.length) {

			var $portfolio = $('.portfolio-items');
			$portfolio.isotope({
				itemSelector: '.portfolio-item',
				layoutMode: 'fitRows'
			});

			$portfolio_selectors.on('click', function() {
				$portfolio_selectors.removeClass('active');
				$(this).addClass('active');
				var selector = $(this).attr('data-filter');
				$portfolio.isotope({
					filter: selector
				});
				return false;
			});
		}
	});
	$('.timer').each(count);

	function count(options) {
		var $this = $(this);
		options = $.extend({}, options || {}, $this.data('countToOptions') || {});
		$this.countTo(options);
	}

	// Search
	$('.fa-search').on('click', function() {
		$('.field-toggle').fadeToggle(200);
	});

	// Contact form
	var form = $('#main-contact-form');
	form.submit(function(event) {
		event.preventDefault();
		var form_status = $('<div class="form_status"></div>');
		$.ajax({
			url: $(this).attr('action'),
			beforeSend: function() {
				form.prepend(form_status.html('<p><i class="fa fa-spinner fa-spin"></i> Email is sending...</p>').fadeIn());
			}
		}).done(function(data) {
			form_status.html('<p class="text-success">Thank you for contact us. As early as possible  we will contact you</p>').delay(3000).fadeOut();
		});
	});

	// Progress Bar
	$.each($('div.progress-bar'), function() {
		$(this).css('width', $(this).attr('data-transition') + '%');
	});

	if ($('#gmap').length) {
		var map;

		map = new GMaps({
			el: '#gmap',
			lat: 43.04446,
			lng: -76.130791,
			scrollwheel: false,
			zoom: 16,
			zoomControl: false,
			panControl: false,
			streetViewControl: false,
			mapTypeControl: false,
			overviewMapControl: false,
			clickable: false
		});

		map.addMarker({
			lat: 43.04446,
			lng: -76.130791,
			animation: google.maps.Animation.DROP,
			verticalAlign: 'bottom',
			horizontalAlign: 'center',
			backgroundColor: '#3e8bff',
		});
	}

	$(document).ready(function() {
		var scrollPos = 0;
		
		// 기존 css에서 플로팅 배너 위치(top)값을 가져와 저장한다.
		var floatPosition = parseInt($("#floatMenu").css('top'));
		// category-div height
		var categoryBottomPos = $('#category-div').offset().top + 100;
		// FloatMecu 절대좌표floatMenu
		var floatBottomPos = $("#floatMenu").offset().top + 800;
		// Footer 절대좌표
		var footerPos = $("#footer").offset().top;
		
		$(window).scroll(function() {
			var curScrollPos = $(this).scrollTop();
			
			floatBottomPos = $("#floatMenu").offset().top + 800;
			footerPos = $("#footer").offset().top;
			//console.log(floatBottomPos + "/" + footerPos);
			
			// 현재 스크롤 위치를 가져온다.
			var newPosition = curScrollPos + floatPosition - 635 + "px";
			
			if (curScrollPos > scrollPos) { //Scrolling Down
	        	//console.log("donw");
	        	if (curScrollPos <= categoryBottomPos) {
					$("#floatMenu").css("top","0px");
				}
				else if (floatBottomPos >=  footerPos) {
					$("#floatMenu").css("top", (footerPos - 1480) + "px");
				}
				else{
					$("#floatMenu").css('top', newPosition);
				}
	            
	        } else { //Scrolling Up
	        	//console.log("up");
	        	if (curScrollPos <= categoryBottomPos) {
					$("#floatMenu").css("top","0px");
				}
				else if (floatBottomPos >=  footerPos) {
					$("#floatMenu").css('top', newPosition);
				}
				else{
					$("#floatMenu").css('top', newPosition);
				}
	        }

			scrollPos = curScrollPos;
		});
	});
});

/**************************  Preview Start  **********************************/
function preview(abid){
	$('#world-ranking-visitor').html('');
	$('#url-sub-domain').html('');
	$.ajax({
		url: "preview.do",
		type: "post",
		data : { abid : abid },// 북마크 ID
		beforeSend: function() {
			$('#layout').html('<img src="/images/loading/preview.gif" style="margin-top: 60px;"/>');
		},
		complete: function() {
			$('#layout').html('');
		},
		success : function(data){
			//console.log(data);
			$('#comment').fadeOut(10, function(){
				var layout = '<img src="/images/homepage/' + abid + '.png" style="width:100%; height:100%">';
				$("#layout").html(layout);
				
				var comment = "";
				if(data.title != "" && data.title != null){
					comment = "<b>" + data.title + "</b>";
				}
				/*if(data.url != "" && data.url != null){
					comment += "&nbsp;-&nbsp;<a href='" + data.url + "' target='_blank'>" + data.url + "</a>";
				}*/
				if(data.description != "" && data.description != null){
					comment += "<br> <p>&nbsp;&nbsp;" + data.description + "</p>";
				}
				$("#comment-detail").html(comment);
				$('#comment').fadeIn(1000);
				
				previewDetail(abid);
			});
		}
	});
};

// Preview Details: Rank, Sub String
function previewDetail(abid) {
	$.ajax({
		url: "previewdetail.do",
		type: "post",
		data : { abid : abid },// 북마크 ID
		beforeSend: function() {
			$('#ajax-loading-div').html('<img id="loading-img" src="/images/loading/writer.gif" style="width:35%;"/>');
		},
		complete: function() {
			$('#ajax-loading-div').html('');
		},
		success : function(data){
			$('#world-ranking-visitor').html('');
			$('#url-sub-domain').html('');
			var ranking = "<i class='fas fa-globe' style='color: #1192e8;'><p class='detail-text'>Global Rank</p></i>" 
						+ "<span id='world-ranking'>";
			var visitors = "<i class='fas fa-eye' style='color: #e46100;'><p class='detail-text'>Daily Visitors</p></i>" 
						+ "<span id='world-visitor'>";
			var sub_domain = "<i class='fas fa-link' style='color: #328618;'><p class='detail-text'>Subdomains</p></i><br>" 
						+ "<span>";
			
			if(data.rank != "" && data.rank != null) {
				ranking += numberWithCommas(data.rank) + "</span><br>";
			}else {
				ranking += "Not supported</span><br>";
			}
			if(data.visitor != "" && data.visitor != null) {
				visitors += data.visitor + "</span>";
			}else {
				visitors += "Not supported</span>";
			}
			if(data.suburl != "" && data.suburl != null) {
				var i = 1;
				for(index in data.suburl) {
					//console.log(data.suburl[index][0]);
					//console.log(data.suburl[index][1]);
					sub_domain += (i++) + ". " + data.suburl[index][0] + "</span>";
					sub_domain += "<div class='progress'><div class='progress-bar' role='progressbar' "
								+ "style='width: " + data.suburl[index][1] + "' aria-valuenow='25' aria-valuemin='0' aria-valuemax='100'>"
								+ data.suburl[index][1] + "</div></div>";
        		}
			}else {
				sub_domain += "Not supported</span>";
			}
			//console.log(sub_domain);
			$("#world-ranking-visitor").append(ranking);
			$("#world-ranking-visitor").append(visitors);
			$("#url-sub-domain").html(sub_domain);
		}
	});
}
/**************************  Preview End  **********************************/
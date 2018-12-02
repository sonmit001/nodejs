<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 페이지 전환 Script START -->
<script type="text/javascript">
	$(window).load(function() {
		setTimeout(function() {
			$(".loader").addClass('end');
		}, 100);
		$('.skin-blue').on(
			'click',
			'transition',
			function(e) {
				e.preventDefault();
				var t = $(this), duration = t
				.data('duration'), getNum = /[^0-9]/g, href = t
				.attr('href'), time = parseFloat(duration);
				
				time = time * 1000;
				console.log(time);

				$('.loader')
					.find('*')
					.css(
					{
						'-moz-transition-duration' : duration,
						'-o-transition-duration' : duration,
						'-webkit-transition-duration' : duration,
						'transition-duration' : duration
					});
				$('.loader-circle').addClass('exit');
				window.setTimeout(function() {
					window.location = href
				}, time)
			}
		);
	});
</script>
<!-- 페이지 전환 Script END -->

<!-- Page Transition START -->
<div class="loader">
	<svg height="100%" width="100%" class="svg-wrap">
    <circle class="circle" r="100%" cx="50%" cy="50%" fill="#17aaa9" />
  </svg>
</div>
<!-- Page Transition END -->

<!-- Main Content START -->
<div class="content-wrapper" style="min-height: 913px;">
	<section class="content-header">
		<h1><i class="fas fa-chart-line content-header-chartico"></i>Chart</h1>
		<ol class="breadcrumb">
			<li><i class="fa fa-home"></i>Home</li>
			<li>chart</li>
		</ol>
	</section>
	<!-- 일일/누적 방문자 수 차트 START -->
	<section class="content chart">
		<h1>
			<i class="fa fa-arrow-right"></i>&nbsp;&nbsp;방문자 수
		</h1>
		<div class="row" id="chartdiv1">
			<script>
				var visit_chartdata = '${visit_chartdata}';
				//console.log(visit_data);

				var chart = AmCharts.makeChart("chartdiv1", {
					"type" : "serial",
					"theme" : "light",
					"legend" : {
						"useGraphSettings" : true
					},
					"synchronizeGrid" : true,
					"valueAxes" : [ {
						"id" : "v1",
						"axisColor" : "#FF6600",
						"axisThickness" : 2,
						"axisAlpha" : 1,
						"position" : "left"
					}, {
						"id" : "v2",
						"axisColor" : "#FCD202",
						"axisThickness" : 2,
						"axisAlpha" : 1,
						"position" : "right"
					} ],
					"graphs" : [ {
						"valueAxis" : "v1",
						"lineColor" : "#FF6600",
						"bullet" : "round",
						"bulletBorderThickness" : 1,
						"hideBulletsCount" : 30,
						"title" : "일일 방문자",
						"valueField" : "visit",
						"fillAlphas" : 0
					}, {
						"valueAxis" : "v2",
						"lineColor" : "#FCD202",
						"bullet" : "square",
						"bulletBorderThickness" : 1,
						"hideBulletsCount" : 30,
						"title" : "누적 방문자",
						"valueField" : "total_visit",
						"fillAlphas" : 0
					} ],

					"chartScrollbar" : {},
					"chartCursor" : {
						"cursorPosition" : "mouse"
					},
					"categoryField" : "date",
					"categoryAxis" : {
						"parseDates" : true,
						"axisColor" : "#DADADA",
						"minorGridEnabled" : true
					},
					"export" : {
						"enabled" : true,
						"position" : "bottom-right"
					}
				});
				chart.addListener("dataUpdated", zoomChart);
				chart.dataProvider = JSON.parse(visit_chartdata);

				function zoomChart() {
					chart.zoomToIndexes(chart.dataProvider.length - 20,
							chart.dataProvider.length - 1);
				}
			</script>
		</div>
	</section>
	<!-- 일일/누적 방문자 수 차트 END -->

	<!-- 일일 가입자/ 전체 회원 수 START -->
	<section class="content chart">
		<h1>
			<i class="fa fa-arrow-right"></i>&nbsp;&nbsp;사이트 회원 수
		</h1>
		<div class="row" id="chartdiv2">
			<script>
				var member_chartdata = '${member_chartdata}';

				var chart = AmCharts.makeChart("chartdiv2", {
					"type" : "serial",
					"theme" : "light",
					"legend" : {
						"useGraphSettings" : true
					},
					"synchronizeGrid" : true,
					"valueAxes" : [ {
						"id" : "v1",
						"axisColor" : "#FF6600",
						"axisThickness" : 2,
						"axisAlpha" : 1,
						"position" : "left"
					}, {
						"id" : "v2",
						"axisColor" : "#FCD202",
						"axisThickness" : 2,
						"axisAlpha" : 1,
						"position" : "right"
					} ],
					"graphs" : [ {
						"valueAxis" : "v1",
						"lineColor" : "#FF6600",
						"bullet" : "round",
						"bulletBorderThickness" : 1,
						"hideBulletsCount" : 30,
						"title" : "일일 가입자",
						"valueField" : "new_member",
						"fillAlphas" : 0
					}, {
						"valueAxis" : "v2",
						"lineColor" : "#FCD202",
						"bullet" : "square",
						"bulletBorderThickness" : 1,
						"hideBulletsCount" : 30,
						"title" : "전체 회원수",
						"valueField" : "total_member",
						"fillAlphas" : 0
					} ],

					"chartScrollbar" : {},
					"chartCursor" : {
						"cursorPosition" : "mouse"
					},
					"categoryField" : "date",
					"categoryAxis" : {
						"parseDates" : true,
						"axisColor" : "#DADADA",
						"minorGridEnabled" : true
					},
					"export" : {
						"enabled" : true,
						"position" : "bottom-right"
					}
				});
				chart.addListener("dataUpdated", zoomChart);
				chart.dataProvider = JSON.parse(member_chartdata);

				function zoomChart() {
					chart.zoomToIndexes(chart.dataProvider.length - 20,
							chart.dataProvider.length - 1);
				}
			</script>
		</div>
	</section>
	<!-- 일일 가입자/ 전체 회원 수 END -->

	<!-- 개인  페이지에서 추가한 북마크 수 START -->
	<section class="content chart">
		<h1>
			<i class="fa fa-arrow-right"></i>&nbsp;&nbsp;개인 북마크 수
		</h1>
		<div class="row" id="chartdiv3">
			<script>
				var user_bookmark_chartdata = '${user_bookmark_chartdata}';

				var chart = AmCharts.makeChart("chartdiv3", {
					"type" : "serial",
					"theme" : "light",
					"legend" : {
						"useGraphSettings" : true
					},
					"synchronizeGrid" : true,
					"valueAxes" : [ {
						"id" : "v1",
						"axisColor" : "#FF6600",
						"axisThickness" : 2,
						"axisAlpha" : 1,
						"position" : "left"
					}, {
						"id" : "v2",
						"axisColor" : "#FCD202",
						"axisThickness" : 2,
						"axisAlpha" : 1,
						"position" : "right"
					} ],
					"graphs" : [ {
						"valueAxis" : "v1",
						"lineColor" : "#FF6600",
						"bullet" : "round",
						"bulletBorderThickness" : 1,
						"hideBulletsCount" : 30,
						"title" : "개인 일일  북마크 수",
						"valueField" : "new_user_bookmark",
						"fillAlphas" : 0
					}, {
						"valueAxis" : "v2",
						"lineColor" : "#FCD202",
						"bullet" : "square",
						"bulletBorderThickness" : 1,
						"hideBulletsCount" : 30,
						"title" : "개인 누적 북마크 수",
						"valueField" : "total_user_bookmark",
						"fillAlphas" : 0
					} ],

					"chartScrollbar" : {},
					"chartCursor" : {
						"cursorPosition" : "mouse"
					},
					"categoryField" : "date",
					"categoryAxis" : {
						"parseDates" : true,
						"axisColor" : "#DADADA",
						"minorGridEnabled" : true
					},
					"export" : {
						"enabled" : true,
						"position" : "bottom-right"
					}
				});
				chart.addListener("dataUpdated", zoomChart);
				chart.dataProvider = JSON.parse(user_bookmark_chartdata);

				function zoomChart() {
					chart.zoomToIndexes(chart.dataProvider.length - 20,
							chart.dataProvider.length - 1);
				}
			</script>
		</div>
	</section>
	<!-- 개인  그룹 페이지에서 추가한 북마크 수 END -->
	
	<!-- 그룹 페이지에서 추가한 북마크 수 START -->
	<section class="content chart">
		<h1>
			<i class="fa fa-arrow-right"></i>&nbsp;&nbsp;그룹 북마크 수
		</h1>
		<div class="row" id="chartdiv4">
			<script>
				var group_bookmark_chartdata = '${group_bookmark_chartdata}';

				var chart = AmCharts.makeChart("chartdiv4", {
					"type" : "serial",
					"theme" : "light",
					"legend" : {
						"useGraphSettings" : true
					},
					"synchronizeGrid" : true,
					"valueAxes" : [ {
						"id" : "v1",
						"axisColor" : "#FF6600",
						"axisThickness" : 2,
						"axisAlpha" : 1,
						"position" : "left"
					}, {
						"id" : "v2",
						"axisColor" : "#FCD202",
						"axisThickness" : 2,
						"axisAlpha" : 1,
						"position" : "right"
					} ],
					"graphs" : [ {
						"valueAxis" : "v1",
						"lineColor" : "#FF6600",
						"bullet" : "round",
						"bulletBorderThickness" : 1,
						"hideBulletsCount" : 30,
						"title" : "그룹 일일  북마크 수",
						"valueField" : "new_group_bookmark",
						"fillAlphas" : 0
					}, {
						"valueAxis" : "v2",
						"lineColor" : "#FCD202",
						"bullet" : "square",
						"bulletBorderThickness" : 1,
						"hideBulletsCount" : 30,
						"title" : "그룹 누적 북마크 수",
						"valueField" : "total_group_bookmark",
						"fillAlphas" : 0
					} ],

					"chartScrollbar" : {},
					"chartCursor" : {
						"cursorPosition" : "mouse"
					},
					"categoryField" : "date",
					"categoryAxis" : {
						"parseDates" : true,
						"axisColor" : "#DADADA",
						"minorGridEnabled" : true
					},
					"export" : {
						"enabled" : true,
						"position" : "bottom-right"
					}
				});
				chart.addListener("dataUpdated", zoomChart);
				chart.dataProvider = JSON.parse(group_bookmark_chartdata);

				function zoomChart() {
					chart.zoomToIndexes(chart.dataProvider.length - 20,
							chart.dataProvider.length - 1);
				}
			</script>
		</div>
	</section>
	<!-- 그룹 페이지에서 추가한 북마크 수 END -->
</div>
<!-- Main Content END -->
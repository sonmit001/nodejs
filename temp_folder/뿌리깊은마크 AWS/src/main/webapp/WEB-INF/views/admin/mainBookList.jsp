<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- adminTable CSS START -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/mainbooklist.css">
<!-- adminTable CSS END -->

<!-- mainBookList.js START -->
<script src="${pageContext.request.contextPath}/js/admin/mainBookList.js"></script>
<!-- mainBookList.js END -->

<script type="text/javascript">
	var dataTableList = new Array();
	$(function(){
		var categorylist = new Array(); 
		
		<c:forEach items="${categorylist}" var="category" varStatus="status">
			categorylist.push(new Array("${category.acid}", "${category.color}"));
		</c:forEach>
		
		// console.log(categorylist);
		// DataTable 적용
		$.each(categorylist, function(index, element){
			//console.log(element);
			var dataTable = $('#table'+ element[0]).DataTable({
				responsive: true
			});
			dataTableList[element[0]] = dataTable;
			
			// ColorPicker
			
			var color = element[1];
			if(color == "#000"){
				color = "#FFFFFF";
			}else if (color == "#191970"){
				color = "#c9c9ff";
			}else if (color == "#696969"){
				color = "#d1d1d1";
			}
			
			$('span[id="'+ element[0] + '"]').css("color", color);
			//console.log(color);
			
			$(".categoryColor"+element[0]).colorPick({
	            'initialColor': element[1],
	            'onColorSelected': function() {
	            	var selectColor = this.color;
	            	if(selectColor == "#000"){
	            		selectColor = "#FFFFFF";
	    			}else if (selectColor == "#191970"){
	    				selectColor = "#c9c9ff";
	    			}else if (selectColor == "#696969"){
	    				selectColor = "#d1d1d1";
	    			}
	            	
	                this.element.css({
	                    'backgroundColor': this.color,
	                    'color': this.color
	                });
	                
	                $.ajax({
	            		url: "editCategoryCclor.do",
	    				type: "post",
	    				data : {
	    					acid : element[0], // 카테고리 ID
	    					color : this.color // 카테고리 색상
	    				},
	    				success : function(data){
	    					//console.log(data);
	    				}
	            	});
	                
	                $('span[id="'+ element[0] + '"]').css("color", selectColor);
	            }
	        });
		});
	});
</script>

<!-- Main Content START -->
<div class="content-wrapper" style="min-height: 913px;">
	<section class="content-header">
		<h1><i class="fas fa-clipboard-list content-header-ico"></i>Main Bookmark List
			<!-- Category insert / Delete START -->
			<span class="span-category">
				<span class="btn-category" data-toggle="dropdown"><i
					class="fa fa-th-list category-dropdown"></i></span>
				<ul class="dropdown-menu category">
					<li>
						<a data-toggle="modal" onclick="openCategoryModal();">카테고리 추가
						<i class="fa fa-plus"></i></a>
					</li>
					<li>
						<a data-toggle="modal" onclick="openCategoryDeleteModal();">카테고리 삭제
						<i class="fa fa-minus"></i></a>
					</li>
				</ul>
			</span>
			<span class="span-categorylist">
				<!-- Category lsit Dropdown START -->
				<button class="btn-category categorylist" data-toggle="dropdown" type="button">
					카테고리<i class="fa fa-caret-right"></i>
				</button>
				<ul id="dropDownCategoryList" class="dropdown-menu categorylist">
					<c:forEach items="${categorylist}" var="category">
						<li id="dropDownCategoryList${category.acid}"><a href="#panel${category.acid}">${category.acname}</a></li>
					</c:forEach>
				</ul>
				<!-- Category lsit Dropdown END -->
			</span>
			<!-- Category inert / Delete END -->
		</h1>
		<ol class="breadcrumb">
			<li><a><i class="fa fa-home"></i>Home</a></li>
			<li class="active">main bookmark list</li>
		</ol>
	</section>

	<!--groupList table start-->
	<section class="content">
		<div id="page-wrapper">
			<div class="row">
				<!-- Category List Table START -->
				<c:forEach items="${url_by_category}" var="list">
					<c:forEach items="${list}" var="hashmap">
						<div class="col-sm-6">
						<div class="panel" id="panel${hashmap.key.acid}" >
							<!-- Category Name & edit & insert START -->

							<div class="panel-heading">
								<span id="categoryName${hashmap.key.acid}"> ${hashmap.key.acname}</span>
								<!--color picker START -->
								<button class="colorPickSelector categoryColor${hashmap.key.acid}"></button>
								<!--color picker END -->
								<i class="fas fa-pencil-alt" data-toggle="modal" onclick="openCategoryEditModal(${hashmap.key.acid}, '${hashmap.key.acname}');"></i>
								<div class="pull-right">
									<i class="fa fa-plus-circle i-plus-circle" data-toggle="modal" onclick="openUrlModal(${hashmap.key.acid}, '${hashmap.key.acname}');"></i>
								</div>
							</div>
							<!-- /.panel-heading -->

							<!-- Category Name & edit & insert END -->
							<div class="panel-body">
								<table width="100%" class="table table-hover" id= "table${hashmap.key.acid}">
									<thead>
										<tr>
											<th>사이트명</th>
											<th>URL 주소</th>
											<th>Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${hashmap.value}" var="book">
											<tr id="${book.abid}">
												<td>${book.urlname}</td>
												<td><a href="${book.url}" target="_blank">${book.url}</a></td>
												<td>
													<i class="fas fa-pencil-alt url-action" onclick="openUrlEditModal(${hashmap.key.acid}, '${hashmap.key.acname}', '${book.url}', ${book.abid});"></i>
													<i class="fas fa-trash-alt url-action" onclick="deleteUrl(${book.abid}, ${hashmap.key.acid})"></i>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					</c:forEach>	
				</c:forEach>
			</div>
		</div>
		<!-- /#page-wrapper -->

		<!--categoryModal start-->
		<div class="modal fade addCategory" id="addCategoryModal">
			<div class="modal-dialog">

				<div class="modal-content">
					<form id="addCategoryForm" name="addCategory" method="post" action="addCategory.do"
						accept-charset="UTF-8" onsubmit="return false;">

						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title"></h4>
						</div>

						<div class="modal-body">
							<div class="box">
								<!--카테고리명 입력-->
								<div class="form addCategoryBox">
									<label class="control-label" for="acname">추가할 카테고리명을
										입력하세요</label> <input id="acname1" class="form-control" type="text"
										placeholder="카테고리명" name="acname" maxlength="15"/><br>
								</div>
							</div>
						</div>

						<div class="modal-footer">
							<div class="addCategory-footer">
								<input class="btn btn-default btn-comp" type="button" value="등록"
									onclick="addAdminCategory();">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!--categoryModal end-->

		<!--categoryEditModal start-->
		<div class="modal fade editCategory" id="editCategoryModal">
			<div class="modal-dialog">
				<div class="modal-content">
					<form id="editCategoryForm" name="editCategory" method="post" action="editCategory.do" accept-charset="UTF-8">
						<div class="modal-header" onsubmit="return false;">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title"></h4>
						</div>

						<div class="modal-body">
							<div class="box">
								<!--카테고리명 입력-->
								<div class="form editCategoryBox">
									<input type="hidden" id="editCategoryID" name="acid">
									<label class="control-label" for="acname">카테고리명을 입력하세요</label>
									<input id="acname2" class="form-control" type="text" placeholder="acname" name="acname" maxlength="15"><br>
								</div>
							</div>
						</div>

						<div class="modal-footer">
							<div class="editCategory-footer">
								<input class="btn btn-default btn-comp" type="button" value="수정" onclick="editAdminCategory();">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!--categoryEditModal end-->

		<!--categoryDeleteModal start-->
		<div class="modal fade deleteCategory" id="deleteCategoryModal">
			<div class="modal-dialog">

				<div class="modal-content">
					<form id="deleteCategoryForm" name="deleteCategory" method="post" action="deleteCategory.do" accept-charset="UTF-8" onsubmit="return false;">

						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title"></h4>
						</div>

						<div class="modal-body">
							<div class="box">
								<!--카테고리명 입력-->
								<div class="form deleteCategoryBox">
									<label class="control-label" for="acnameList2">삭제할 카테고리를 선택하세요</label>&nbsp;
									<select id="acnameList2" class="form-control" name="acid">
										<c:forEach items="${categorylist}" var="category">
											<option id="deleteCategoryList${category.acid}" value="${category.acid}">${category.acname}</option>
										</c:forEach>
									</select><br> <input id="acname3" class="form-control" type="hidden" name="acname"><br>
								</div>
							</div>
						</div>

						<div class="modal-footer">
							<div class="deleteCategory-footer">
								<input class="btn btn-default btn-comp" type="button" value="삭제" onclick="deleteAdminCategory();">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!--categoryDeleteModal end-->

		<!--urlAddModal start-->
		<div class="modal fade addUrl" id="addUrlModal">
			<div class="modal-dialog">

				<div class="modal-content">
					<form id="addUrlForm" name="addUrl" method="post" action="${pageContext.request.contextPath}/admin/addUrl.do" accept-charset="UTF-8" onsubmit="return false;">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title"></h4>
						</div>

						<div class="modal-body">
							<div class="box">
								<!--1단계 URL 입력-->
								<div class="form addUrlBox">
									<label class="control-label" for="url">URL을 입력하세요</label>
									<input id="url" class="form-control" type="text" placeholder="URL" name="url" maxlength="150"><br>
									<input type="hidden" id="adminCategoryID" name="acid">
								</div>
							</div>

							<div class="box">
								<!--2단계 카테고리 선택-->
								<div class="categoryBox" style="display: none;">
									<div class="form">
										<label class="control-label" for="urlname">제목</label>
										<span class="bookmark-loader">
											<span>loading title</span>&nbsp;&nbsp;<i class="fas fa-spinner fa-spin"></i>
										</span>
										<input id="addUrlname" class="form-control" type="text" placeholder="URL명" name="urlname" maxlength="33">
										<br> <label class="control-label" for="acname4">선택된 카테고리</label>&nbsp;
										<input id="acname4" class="form-control" type="text" placeholder="선택된 카테고리 (readonly)" name="acname" readonly><br>
									</div>
								</div>
							</div>
						</div>

						<div class="modal-footer">
							<div class="url-footer">
								<input class="btn btn-default btn-next" type="button" value="다음" onclick="getTitleWithWebCrawling()">
							</div>
							<div class="category_unshared-footer" style="display: none">
								<input class="btn btn-default btn-prev" type="button" value="이전" onclick="showUrlForm()">
								<input class="btn btn-default btn-comp" type="button" value="등록" onclick="addBookmarkButton()">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!--urlAddModal end-->
		
		<!--urlEditModal start-->
		<div class="modal fade editUrl" id="editUrlModal">
			<div class="modal-dialog">

				<div class="modal-content">
					<form id="editUrlForm" name="editUrl" method="post" action="editUrl.do" accept-charset="UTF-8" onsubmit="return false;">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title"></h4>
						</div>

						<div class="modal-body">
							<div class="box">
								<!--1단계 URL 입력-->
								<div class="form editUrlBox">
									<label class="control-label" for="editurl">URL을 입력하세요</label>
									<input id="editurl" class="form-control" type="text" placeholder="URL" name="url" maxlength="150"><br>
									<input type="hidden" id="editAdminCategoryID" name="acid">
									<input type="hidden" id="editUrlID" name="abid">
								</div>
							</div>

							<div class="box">
								<!--2단계 카테고리 선택-->
								<div class="categoryBox" style="display: none;">
									<div class="form">
										<label class="control-label" for="urlname">제목</label>
										<span class="bookmark-loader">
											<span>loading title</span>&nbsp;&nbsp;<i class="fas fa-spinner fa-spin"></i>
										</span>
										<input id="editUrlname" class="form-control" type="text" placeholder="URL명" name="urlname" maxlength="33"><br> 
										<label class="control-label" for="acname5">선택된 카테고리</label>&nbsp; 
										<input id="acname5" class="form-control" type="text" placeholder="선택된 카테고리 (readonly)" name="acname" readonly><br>
									</div>
								</div>
							</div>
						</div>

						<div class="modal-footer">
							<div class="url-footer">
								<input class="btn btn-default btn-next" type="button" value="다음" onclick="getTitleEditUrl()">
							</div>
							<div class="category_unshared-footer" style="display: none">
								<input class="btn btn-default btn-prev" type="button" value="이전" onclick="showEditUrlForm()">
								<input class="btn btn-default btn-comp" type="button" value="수정" onclick="editBookmarkButton()">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!--urlEditModal end-->
	</section>
</div>
<!-- Main Content END -->



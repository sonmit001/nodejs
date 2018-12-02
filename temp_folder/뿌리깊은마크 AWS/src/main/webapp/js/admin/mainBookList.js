$(function(){
	// ajax Form 설정 Start
	// URL 추가
	$("#addUrlForm").ajaxForm({
		success: function(data, statusText, xhr, $form){
			console.log(data.newBook);
			dataTableList[data.newBook.acid].row.add([
				data.newBook.urlname,
				'<a href="' + data.newBook.url + '" target="_blank">' + data.newBook.url + '</a>',
				'<i class="fas fa-pencil-alt url-action" onclick="openUrlEditModal(' + data.newBook.acid + ", '" + data.newBook.acname + "', '" + data.newBook.url + "', " + data.newBook.abid + ');"></i>' 
				+ '<i class="fas fa-trash-alt url-action" onclick="deleteUrl(' + data.newBook.abid + ", '" + data.newBook.acid + "'" + ')"></i>'
			]).node().id = data.newBook.abid;
			
			dataTableList[data.newBook.acid].draw(false);
		}
	});
	
	
	// URL 수정
	$("#editUrlForm").ajaxForm({
		success: function(data, statusText, xhr, $form){
			console.log(data.updateBook.urlname);
			var tr = $("#table" + data.updateBook.acid + " tbody tr[id=" + data.updateBook.abid + "]");
			var tableData = dataTableList[data.updateBook.acid].row(tr);
			tableData[0] = data.updateBook.urlname;
			tableData[1] = '<a href="' + data.updateBook.url + '" target="_blank">' + data.updateBook.url + '</a>';
			tableData[2] = '<i class="fas fa-pencil-alt url-action" onclick="openUrlEditModal(' + data.updateBook.acid + ", '" + data.updateBook.acname + "', '" + data.updateBook.url + "', " + data.updateBook.abid + ');"></i>' 
					+ '<i class="fas fa-trash-alt url-action" onclick="deleteUrl(' + data.updateBook.abid + ", " + data.updateBook.acid + ')"></i>';
			
			dataTableList[data.updateBook.acid].row(tr).data(tableData).draw();
		}
	});
	
	// 카테고리 추가
	$("#addCategoryForm").ajaxForm({
		success: function(data, statusText, xhr, $form) {
			console.log(data.newCategory);
			
			// content row에 테이블 추가
			var html = '<div class="col-sm-6">';
				html += '<div id="panel' + data.newCategory.acid + '" class="panel">';
					html += '<div class="panel-heading">';
						html += '<span id="categoryName' + data.newCategory.acid + '">'+ data.newCategory.acname + '</span>';
						html += '<button class="colorPickSelector categoryColor'+ data.newCategory.acid + '"></button>';
						html += '<i class="fas fa-pencil-alt" data-toggle="modal" onclick="openCategoryEditModal('+ data.newCategory.acid + ", '" + data.newCategory.acname + "'" + ');"></i>';
						html += '<div class="pull-right">';
							html += '<i class="fa fa-plus-circle i-plus-circle" data-toggle="modal" onclick="openUrlModal(' + data.newCategory.acid + ", '" + data.newCategory.acname + "'" +');"></i>';
						html += '</div>'
					html += '</div>';		
				html += '<div class="panel-body">';
					html += '<table width="100%" class="table table-hover" id="table' + data.newCategory.acid + '">';
					html += '<thead>';
					html += '<tr>';
					html += '<th>사이트명</th>';
					html += '<th>URL 주소</th>';
					html += '<th>Actions</th>';
					html += '</tr>';
					html += '</thead>';
					html += '</table>';
				
				html += '</div>';
			html += '</div>';
			
			$(".content>#page-wrapper>.row").append(html);
			
			
			// DataTable 적용
			var dataTable = $('#table'+ data.newCategory.acid).DataTable({
				responsive: true
			});
			dataTableList[data.newCategory.acid] = dataTable;
			
			var color = data.newCategory.color;
			if(color == "#000"){
				color = "#FFFFFF";
			}else if (color == "#191970"){
				color = "#c9c9ff";
			}else if (color == "#696969"){
				color = "#d1d1d1";
			}
			
			$('span[id="'+ data.newCategory.acid + '"]').css("color", color);
			
			$(".categoryColor"+data.newCategory.acid).colorPick({
	            'initialColor': data.newCategory.color,
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
	    					acid : data.newCategory.acid, // 카테고리 ID
	    					color : this.color // 카테고리 색상
	    				},
	    				success : function(data){
	    				}
	            	});
	                
	                $('span[id="'+ data.newCategory.acid + '"]').css("color", selectColor);
	            }
	        });
			
			// 카테고리 리스트 추가
			var category = '<li id="dropDownCategoryList' + data.newCategory.acid + '"><a href="#panel' + data.newCategory.acid + '">' + data.newCategory.acname + '</a></li>';
			$("#dropDownCategoryList").append(category);
			
			var deleteCategory = '<option id="deleteCategoryList' + data.newCategory.acid + '" value="' + data.newCategory.acid + '">' + data.newCategory.acname + '</option>';
			$("#acnameList2").append(deleteCategory);
			
		}
	});
	
	
	
	// 카테고리 수정
	$("#editCategoryForm").ajaxForm({
		success: function(data, statusText, xhr, $form) {
			$("#categoryName" + data.editCategory.acid).text(data.editCategory.acname);
			
			$("#deleteCategoryList" + data.editCategory.acid).text(data.editCategory.acname);
			
			$("#dropDownCategoryList" + data.editCategory.acid).children('a').text(data.editCategory.acname);
		}
	
	});
	
	// 카테고리 삭제
	$("#deleteCategoryForm").ajaxForm({
		success: function(data, statusText, xhr, $form) {
			$("#panel" + data.acid).remove();
			
			$("#deleteCategoryList" + data.acid).remove();
			
			$("#dropDownCategoryList" + data.acid).remove();
		}
	});
	
	// ajax Form 설정 End
});



/* categoryModal script start */
/* 카테고리 추가 모달 바로열기 */
function showCategoryForm() {
	$('.addCategoryBox').fadeIn('fast');
	$('.addCategory-footer').fadeIn('fast');
	$('.modal-title').html('카테고리 추가');
}

function openCategoryModal() {
	$("#addCategoryForm")[0].reset();
	showCategoryForm();
	setTimeout(function() {
		$('#addCategoryModal').modal('show');
	}, 230);
}
    
function addAdminCategory() {
	// console.log("click");
	// console.log($("#acname1").val().trim());
	if($("#acname1").val().trim() == ""){
		$.alert("카테고리 명을 입력해주세요");
		$("#acname1").focus();
	}else {
		$("#addCategoryForm").submit();
		setTimeout(function() {
			$('#addCategoryModal').modal('hide');
		}, 230);
	}
}
/* categoryModal script end */

/* categoryEditModal script start */
/*카테고리 수정 모달 바로열기*/
function showCategoryEditForm() {
	$('.editCategoryBox').fadeIn('fast');
	$('.editCategory-footer').fadeIn('fast');
	$('.modal-title').html('카테고리 수정');
}

function openCategoryEditModal(acid, acname) {
	$("#editCategoryForm")[0].reset();
	$("#editCategoryID").val(acid); // 카테고리 ID hidden type에 넣어주기
	$("#acname2").val($("#categoryName"+acid).text()); // 카테고리 명 미리 입력해주기
	
	showCategoryEditForm();
	setTimeout(function() {
		$('#editCategoryModal').modal('show');
	}, 230);
}

function editAdminCategory() {
	if($("#acname2").val().trim() == ""){
		$.alert("카테고리명을 입력해주세요");
	}else {
		$.confirm({
			title : '카테고리 수정',
			content : '수정하시겠습니까?',
			theme: 'light',
			type: 'green',
			backgroundDismiss: true,
			closeIcon: true,
			buttons: {
				'수정': {
					btnClass : 'btn-success',
					keys: ['enter'],
					action : function () {
						$("#editCategoryForm").submit();
						setTimeout(function() {
							$('#editCategoryModal').modal('hide');
						}, 230);
					}
				},
				'취소': {
					btnClass : 'btn-danger',
					action : function() {}
				}
			}
		});
	}
}
/* categoryEditModal script end */

/* categoryDeleteModal script start */
/*카테고리 삭제 모달 바로열기*/
function showCategoryDeleteForm() {
	$('.deleteCategoryBox').fadeIn('fast');
	$('.deleteCategory-footer').fadeIn('fast');
	$('.modal-title').html('카테고리 삭제');
}

function openCategoryDeleteModal() {
	$("#deleteCategoryForm")[0].reset();
	showCategoryDeleteForm();
	setTimeout(function() {
		$('#deleteCategoryModal').modal('show');
	}, 230);
}
            
function deleteAdminCategory() {
	var js = $.confirm({
		title : '카테고리 삭제',
		content : '삭제하시겠습니까?',
		closeIcon: true,
		type: 'green',
		theme: 'light',
		backgroundDismiss: true,
		buttons: {
			'삭제': {
				btnClass : 'btn-danger',
				keys: ['enter'],
				action : function () {
					$("#deleteCategoryForm").submit();
					setTimeout(function() {
						$('#deleteCategoryModal').modal('hide');
					}, 230);
				}
			},
			'취소': {
				btnClass : 'btn-success',
				action : function() {}
			}
		},
	});
}
/* categoryDeleteModal script end */

/* urlAddModal script start */
/* URL 추가 1단계 모달 */
function showUrlForm() {
	$('.categoryBox').fadeOut('fast', function() {
		$('.addUrlBox').fadeIn('fast');
		$('.category_unshared-footer').fadeOut('fast', function() {
			$('.url-footer').fadeIn('fast')
		})
	});
	$('.modal-title').html('1단계');
}
            
/* URL 1단계에서 다음 눌렀을 때 WebCrawling을 통해 비동기로 타이틀 가져오기 */
function getTitleWithWebCrawling() {
	var url = $("#url").val().trim();
	
	// URL를 입력 안한 경우 alert 창을 띄운다.
	var regex =/^(?:(?:(?:https?|ftp):)?\/\/)(?:\S+(?::\S*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})))(?::\d{2,5})?(?:[/?#]\S*)?$/;
	if(!(regex.test(url))){
		$.alert("URL을 확인해주세요");
	}else {
		preview(url);
	}
}

// 비동기로 제목 가져오는 함수
function preview(url){
	$.ajax({
		url: "preview.do",
		type: "post",
		data : {
			url : url // 북마크 ID
		},
		beforeSend: function() {
			$(".bookmark-loader").css("visibility", "visible");
			$("#addUrlname").val("");
			showUrlStep2Form();
		},
		complete: function() {
			$(".bookmark-loader").css("visibility", "hidden");
		},
		success : function(data){
			$("#addUrlname").val(data.title);
		},
	});
}
            
/*URL 추가 2단계 모달*/
function showUrlStep2Form() {
	$('.addUrlBox').fadeOut('fast', function() {
		$('.categoryBox').fadeIn('fast');
		$('.url-footer').fadeOut('fast', function() {
			$('.category_unshared-footer').fadeIn('fast')
		});
	});
	$('.modal-title').html('2단계');
}

/*URL 추가 1단계 모달 바로열기*/
function openUrlModal(acid, acname) {
	$("#addUrlForm")[0].reset();
	$("#acname4").val($("#categoryName"+acid).text());
	$("#adminCategoryID").val(acid);
            	
	showUrlForm();
	setTimeout(function() {
		$('#addUrlModal').modal('show');
	}, 230);
}
            
/* URL 추가 */
function addBookmarkButton() {
	var urlname = $("#addUrlname").val().trim();
	
	// 사이트명을 입력하지 않은 경우 alert창을 띄운다.
	if(urlname == ""){
		$.alert("사이트명을 입력해주세요")
	}else {
		$("#addUrlForm").submit();
		setTimeout(function() {
			$('#addUrlModal').modal('hide');
		}, 230);
	}
}
/* urlAddModal script end */

/* urlEditModal script star */
/* URL 수정 1단계 모달 */
function showEditUrlForm() {
	$('.categoryBox').fadeOut('fast', function() {
		$('.editUrlBox').fadeIn('fast');
		$('.category_unshared-footer').fadeOut('fast', function() {
			$('.url-footer').fadeIn('fast')
		});
	});
	$('.modal-title').html('1단계');
}
            
/* URL 1단계에서 다음 눌렀을 때 WebCrawling을 통해 비동기로 타이틀 가져오기 */
function getTitleEditUrl() {
	var url = $("#editurl").val().trim();
	
	// URL을 입력하지 않은 경우 alert창을 띄운다.
	var regex =/^(?:(?:(?:https?|ftp):)?\/\/)(?:\S+(?::\S*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})))(?::\d{2,5})?(?:[/?#]\S*)?$/;
	if(!(regex.test(url))){
		$.alert("URL을 확인해주세요");
	}else {
		editUrlPreview(url);
	}
}
            
function editUrlPreview(url){
	$.ajax({
		url: "preview.do",
		type: "post",
		data : {
			url : url // 북마크 ID
		},
		beforeSend: function() {
			$(".bookmark-loader").css("visibility", "visible");
			$("#editUrlname").val("");
			showUrlEditStep2Form();
		},
		complete: function() {
			$(".bookmark-loader").css("visibility", "hidden");
		},
		success : function(data){
			$("#editUrlname").val(data.title);
			showUrlEditStep2Form();
		}
	});
};

/*URL 수정 2단계 모달*/
function showUrlEditStep2Form() {
	$('.editUrlBox').fadeOut('fast', function() {
		$('.categoryBox').fadeIn('fast');
		$('.url-footer').fadeOut('fast', function() {
			$('.category_unshared-footer').fadeIn('fast')
		});
	});
	$('.modal-title').html('2단계');
}

/*URL 수정 1단계 모달 바로열기*/
function openUrlEditModal(acid, acname, url, abid) {
	$("#editUrlForm")[0].reset();
	$("#editurl").val(url);
	$("#acname5").val($("#categoryName"+acid).text());
	$("#editAdminCategoryID").val(acid);
	$("#editUrlID").val(abid);
	
	showEditUrlForm();
	setTimeout(function() {
		$('#editUrlModal').modal('show');
	}, 230);
}
            
/* URL 수정  */
function editBookmarkButton() {
	var urlname = $("#editUrlname").val().trim();

	// 사이트명을 입력하지 않은 경우 alert창을 띄운다.
	if(urlname == ""){
		$("#editUrlname").focus();
	}else {
		$.confirm({
			title : 'URL 수정',
			content : '수정하시겠습니까?',
			type: 'green',
			theme: 'light',
			backgroundDismiss: true,
			closeIcon: true,
			buttons: {
				'수정': {
					btnClass : 'btn-success',
					keys: ['enter'],
					action : function () {
						$("#editUrlForm").submit();
						setTimeout(function() {
							$('#editUrlModal').modal('hide');
						}, 230);
					}
				},
				'취소': {
					btnClass : 'btn-danger',
					action : function() {}
				}
			}
		});
	}
}
/* urlEditModal script end */

/* urlDelete script Start */
function deleteUrl(abid, acid) {
	$.confirm({
		title : 'URL 삭제',
		content : '삭제하시겠습니까?',
		theme: 'light',
		type: 'green',
		backgroundDismiss: true,
		closeIcon: true,
		buttons: {
			'삭제': {
				btnClass : 'btn-danger',
				keys: ['enter'],
				action : function () {
					//$("#" + abid).remove(); // dataTable에서 지우기
					dataTableList[acid].row($('tr[id=' + abid + ']')).remove().draw();
					
					$.ajax({
						url: "deleteUrl.do",
						type: "post",
						data : {
							abid : abid // 관리자북마크 ID
						},
						success : function(data){
							console.log(data);
						}
					});
	        	}
	        },
	        
	        '취소': {
	        	btnClass : 'btn-success',
	        	action : function() {
	        		
	        	}
	        }
	    }
	});
}

/* urlDelete script End*/ 

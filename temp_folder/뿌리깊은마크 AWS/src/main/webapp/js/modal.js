
jQuery(function($) {
	$(function() {
		// 링크 내 북마크로 가져가기 버튼 클릭
		$('.url_hover_btn').on('dblclick', function(){});
	    $('.url_hover_btn').on('click', function(){
	    	// 모달 초기화 START
	    	$('#dropdownMenuButton').html("Click <span class='caret'></span>");
	    	$('#jstree-to-bottom').html('');
	    	$('#dropdown-empty-area').remove();	// 모달 초기화 END
	    	var url = $(this).parent().children('p').data('url');
	    	$('.indishare-url').val(url);
	    	$('.indishare-urlname').val($(this).parent().children('p').data('urlname'));
	    	$('.indishare-abid').val( $(this).parent().children('p').data('abid'));
	    	
	    	// 진행중인 팀 리스트 가져오기
	    	$.ajax({
				url: "team/getTeamList.do",
			    type: "post",
			    success : function(data){
			    	var html = '<ul class="dropdown-menu">';
			    	
			    	var index = 0;
			    	for(var key in data.teamlist){
			    		//console.log(data.teamlist[key]);
			    		if(index == 0){
			    			html += '<li class="dropdown-group-item" onclick="seletedGroup('
			    					+ "'" + data.teamlist[key].gname + "', '" + data.teamlist[key].gid
			    					+ "');" + '"><span tabindex="-1">' + data.teamlist[key].gname
			    					+ '</span></li><hr class="divider-hr">';
			    		}else {
			    			html += '<li class="dropdown-group-item" onclick="seletedGroup('
			    					+ "'" + data.teamlist[key].gname + "', '" + data.teamlist[key].gid
			    					+ "');" + '"><span>' + data.teamlist[key].gname
			    					+ '</span></li><hr class="divider-hr">';
			    		}
			    		index += 1;
			    	}
			    	html += '</ul>';
			    	$(".dropdown-item").append(html);
			    }
			});
	    });
	    
	    // 나의 북마크 선택했을 때, 
	    $('#dropdown-my-book').on('dblclick', function(){});
	    $('#dropdown-my-book').on('click', function(){
	    	// Modal init()
	    	$('#dropdownMenuButton').text($(this).text());
	    	$('#into-my-bookmark').css('display', 'block');
	    	$('#into-group-bookmark').css('display', 'none');
	    	$('#jstree-to-bottom').remove();
	    	$('.completed-modal-left:eq(1)').append('<div id="jstree-to-bottom" style="clear: both;"></div>');
	    	
	    	$.ajax({
				url : "user/getCategoryList.do",
				type:"POST",
				dataType:"json",
				success : function(data){
					/*jstree 시작하기 jstree 생성하고 싶은 div의 id를 적어준다.*/	
					$("#jstree-to-bottom").jstree({	
							"core": {
								'data' : data, 				//ajax로 가져온 json data jstree에 넣어주기
								'themes':{
									'name' : 'proton', 		//테마 이름
									'responsive' : true,
									"dots": false, 			// 연결선 없애기
								}
							}
					}).bind("select_node.jstree", function (e, data) {
						/*노드(폴더)가 선택시 실행되는 함수*/					
	 					var id = data.node.id;
	 					$('.indishare-userpid').val(id);
	 					
					});
				}
			});
	    });
	   
	    // [확인]: 나의 북마크로 추가 버튼 클릭했을 때, 
	    $('#into-my-bookmark').on('dblclick', function(){});
	    $('#into-my-bookmark').on('click', function(){
	    	var params = $("#form-to-mybookmark").serialize();
    		$.ajax({
				url : "user/addtomybookmark.do",
				type:"POST",
				data: params,
				dataType:"json",
				success : function(data){
					//console.log(data.result);
					if(data.result == "success") {
						swal("Thank you!", "북마크에 추가되었습니다!", "success");
						$('#mainIndiModal').modal("toggle");
					}else {
                        swal({
                            title: "목적지 폴더를 확인하셨나요?",
                            text: "잠시후 다시 시도해주세요!",
                            icon: "warning",
                            buttons: true,
                            dangerMode: true
						});
					}
				},
				error : function(error) {
					swal({
                        title: "목적지 폴더를 확인하셨나요?",
                        text: "잠시후 다시 시도해주세요!",
                        icon: "warning",
                        buttons: true,
                        dangerMode: true
					});
			    }
			});
	    });
	    // [확인]: 그룹 북마크로 추가 버튼 클릭했을 때, 
	    $('#into-group-bookmark').on('dblclick', function(){});
	    $('#into-group-bookmark').on('click', function(){
           $.ajax({
                url : "team/addGroupBookmark.do",
                type: "POST",
                data: {
                	url: $('.indishare-url').val(),
                	urlname: $('.indishare-urlname').val(),
                	pid: $('.indishare-userpid').val(),
                	abid: $('.indishare-abid').val(),
                	gid: $('.indishare-gid').val()
                },
                dataType:"json",
                success : function(data){
                    //console.log(data.result);
                    if(data.result == "success") {
                        swal("Thank you!", "북마크에 추가되었습니다!", "success");
                        $('#mainIndiModal').modal("toggle");
                    }else {
                       swal({
                           title: "목적지 폴더를 확인하셨나요?",
                           text: "잠시후 다시 시도해주세요!",
                           icon: "warning",
                           buttons: true,
                           dangerMode: true
                        });
                    }
                },
                error : function(error) {
                    swal({
                       title: "목적지 폴더를 확인하셨나요?",
                       text: "잠시후 다시 시도해주세요!",
                       icon: "warning",
                       buttons: true,
                       dangerMode: true
                    });
                }
            });
	    });
	});
});

// 내의 그룹리스트 중 하나를 선택 했을 때,
function seletedGroup(group, gid) {
	// Modal Init()
	$('.indishare-gid').val(gid);
	$('#dropdownMenuButton').text(group);
	$('#into-my-bookmark').css('display', 'none');
	$('#into-group-bookmark').css('display', 'block');
	$('#jstree-to-bottom').remove();
	$('.completed-modal-left:eq(1)').append('<div id="jstree-to-bottom" style="clear: both;"></div>');
	
	$.ajax({
		url : "team/getGroupCategoryList.do",
		type:"POST",
		data: {gid: gid},
		dataType:"json",
		success : function(data){
			//console.log(data.data);
			// jstree 시작하기 jstree 생성하고 싶은 div의 id를 적어준다.	
			$("#jstree-to-bottom").jstree({	
					"core": {
						'data' : data.data, 		// ajax로 가져온 json data jstree에 넣어주기
						'themes':{
							'name' : 'proton', 		// 테마 이름
							'responsive' : true,
							"dots": false, 			// 연결선 없애기
						}
					}
			}).bind("select_node.jstree", function (e, data) {
			//노드(폴더)가 선택시 실행되는 함수
				var id = data.node.id;
				//console.log(id);
				$('.indishare-userpid').val(id);
			});
		}
	});
}




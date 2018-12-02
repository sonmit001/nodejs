var urlname =null;
$('.indi-share').on('dblclick', function(){ return });
$('.indi-share').on('click', function(){
	var title = $(this).data('title');
	urlname= $(this).data('urlname');
	$('.indishare-url').val(title);
});
  

$('.group-share').on('dblclick', function(){ return });
$('.group-share').on('click', function(){
	var title = $(this).data('title');
	$('.groupshare-name').text(title);
});

/* 민재 onclick */
// 개인 북마크 목록 가져가기 START
$(function() {
	// 링크 내 북마크로 가져가기 버튼 클릭
	$('.getbookmark').on('dblclick', function(){});
    $('.getbookmark').on('click', function(){
    	$('#socialIndiModal').css({"z-index":"9999"});
    	// 모달 초기화 START
    	$('#dropdownMenuButton-indi').html("Click <span class='caret'></span>");
    	$('#jstree-to-bottom').html('');
    	/*$('#dropdown-empty-area').remove();	*/
    	$('.indishare-urlname').val($(this).parent().parent().children().eq(0).children('a').text()); 
    	// 모달 초기화 END
    });
    
    // 나의 북마크 선택했을 때, 
    $('#dropdown-my-book').on('dblclick', function(){});
    $('#dropdown-my-book').on('click', function(){
    	// Modal init()
    	$('#dropdownMenuButton-indi').text($(this).text());
    	$('#into-my-bookmark').css('display', 'block');
    	$('#into-group-bookmark').css('display', 'none');
    	$('#jstree-to-bottom').remove();
    	$('.completed-modal-left:eq(1)').append('<div id="jstree-to-bottom" style="clear: both;"></div>');
    	
    	$.ajax({
			url : "/user/getCategoryList.do",
			type:"POST",
			dataType:"json",
			success : function(data){
				/*jstree 시작하기 jstree 생성하고 싶은 div의 id를 적어준다.*/
				$("#jstree-to-bottom").on("click",'.jstree-anchor',function(e){// 한번만 클릭해서 폴더 열기
					$('#jstree-to-bottom').jstree(true).toggle_node(e.target);
				}).jstree({	
						"core": {
							"dblclick_toggle" : false, 	// 두번 클릭해서 폴더여는거 false
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
    	var submit_obj = [];
    	var selected_node_id = $(".indishare-userpid").val();
    	
    	if(selected_node_id == 0) {
    		swal({title: "목적지 폴더를 확인하셨나요?",
                text: "잠시후 다시 시도해주세요!",
                icon: "warning",
                dangerMode: true
			});
	        return false
	    };
    	
    	submit_obj.push({
    			"url": $(".indishare-url").val(),
				"urlname" : urlname,
				"pid": selected_node_id
    			});
    	var submit_obj_json = JSON.stringify(submit_obj);
    	
		$.ajax({
			url : "getmybookmark.do",
			type: "post",
			data: {obj : submit_obj_json},
			//dataType: "json",
			success : function(data){
				if(data.result == "success") {
					swal("Thank you!", "북마크에 추가되었습니다!", "success");
					$(".indishare-userpid").val(0);
					$('#socialIndiModal').modal("toggle");
				}else {
                    swal({
                        title: "목적지 폴더를 확인하셨나요?",
                        text: "잠시후 다시 시도해주세요!",
                        icon: "warning",
                        dangerMode: true
					});
				}
			},
			error : function(error) {
				swal({
                    title: "목적지 폴더를 확인하셨나요?",
                    text: "잠시후 다시 시도해주세요!",
                    icon: "warning",
                    dangerMode: true
				});
		    }
		});
    });
    
    // [확인]: 그룹 북마크로 추가 버튼 클릭했을 때, 
    $('#into-group-bookmark').on('dblclick', function(){});
    $('#into-group-bookmark').on('click', function(){
    	var selected_node_id = $(".indishare-userpid").val();
    	
    	if(selected_node_id == 0) {
    		swal({title: "목적지 폴더를 확인하셨나요?",
                text: "잠시후 다시 시도해주세요!",
                icon: "warning",
                dangerMode: true
			});
	        return false
	    };
	    
		$.ajax({
			url : "getGroupBook.do",
			type: "POST",
			data: {
				"url": $(".indishare-url").val(),
				"urlname" : urlname,
				"pid": selected_node_id, 
				"gid": $(".indishare-gid").val()
			},
			dataType:"json",
			success : function(data){
				if(data.result == "success") {
					swal("Thank you!", "북마크에 추가되었습니다!", "success");
					$(".indishare-userpid").val(0);
					$('#socialIndiModal').modal("toggle");
				}else {
                    swal({
                    	title: "목적지 폴더를 확인하셨나요?",
                        text: "잠시후 다시 시도해주세요!",
                        icon: "warning",
                        dangerMode: true
					});
				}
			},
			error : function(error) {
				swal({
					title: "목적지 폴더를 확인하셨나요?",
                    text: "잠시후 다시 시도해주세요!",
                    icon: "warning",
                    dangerMode: true
				});
		    }
		});
    });
    $('#socialIndiModal').css({"z-index":"0"});
});

//내의 그룹리스트 중 하나를 선택 했을 때,
function selectedGroup(group, gid) {
	// Modal Init()
	$('#dropdownMenuButton-indi').text(group);
	$('.indishare-gid').val(gid);
	$('#into-my-bookmark').css('display', 'none');
	$('#into-group-bookmark').css('display', 'block');
	$('#jstree-to-bottom').remove();
	$('.completed-modal-left:eq(1)').append('<div id="jstree-to-bottom" style="clear: both;"></div>');
	$.ajax({
		url : "/team/getGroupCategoryList.do",
		type:"POST",
		data: {gid: gid},
		dataType:"json",
		success : function(data){
			// jstree 시작하기 jstree 생성하고 싶은 div의 id를 적어준다.	
			$("#jstree-to-bottom").on("click",'.jstree-anchor',function(e){// 한번만 클릭해서 폴더 열기
				$('#jstree-to-bottom').jstree(true).toggle_node(e.target);
			}).jstree({	
					"core": {
						"dblclick_toggle" : false, 	// 두번 클릭해서 폴더여는거 false
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
				$('.indishare-userpid').val(id);
					
			});
		}
	});
}
//개인 북마크 목록 가져가기 END

//그룹 공유 북마크 가져가기 START
var second_data = null

function get_groupbook(group){
	//클릭한 작성자 닉네임
	var gid = group.id;
	var groupName = group.name;
	$('.groupname').text(groupName);
	$.ajax({
		url : "/user/getCompletedTeamBookmark.do",
		type : "POST",
		data : {gid : gid},
		dataType :"json",
		success : function(obj){
			second_data = obj;
			$('#jstree-from-left-group').jstree(true).settings.core.data = obj;
			$('#jstree-from-left-group').jstree(true).refresh();
		}
	})
	//모달열기
	$('#socialGroupModal').modal();
	$('#socialGroupModal').css({"z-index":"9999"});
};

$(document).ready(function(){
    $("#jstree-from-left-group").jstree({
    	"core" : {
    		'data' : second_data,
    		'themes':{
    			'name' : 'proton',
    			'responsive' : true,
    			'dots' : false,
    		}
    	},
    	"checkbox" : { // 체크 박스 클릭시에만 checked 되기
    		"whole_node" : false,
    		"tie_selection" : false
	    },
	    "plugins" : ["checkbox" ]
    
	}).bind("select_node.jstree",function(event,data){
	    var url = $('#jstree-from-left-group').jstree(true).get_node(data.node.id).a_attr.href;
	    $('.groupshare-url').text(url);
        var urlname = $('#jstree-from-left-group').jstree(true).get_node(data.node.id).original.text;
        $('.groupshare-urlname-left').val(urlname);
    });
    
    //나의 북마크 선택했을 때
    $('#dropdown-my-bookmark-getgroup').on('dblclick', function(){});
    $('#dropdown-my-bookmark-getgroup').on('click', function(){

    	$('#dropdownMenuButton-group').text($(this).text());
    	$('#into-my-bookmark-getgroup-btn').css('display', 'inline');
    	$('#into-group-bookmark-getgroup-btn').css('display', 'none');
    	$('#jstree-to-right-group').remove();
    	$('.completed-modal-right-group').append('<div id="jstree-to-right-group"></div>');

    	$.ajax({
    		url : "/user/getCategoryList.do",
			type:"POST",
			dataType:"json",
			success : function(data){
				//jstree 시작, jstree를 뿌려주고 싶은 div의 id를 적어준다
				$("#jstree-to-right-group").jstree({
					"core": {
						'data' : data, 				//ajax로 가져온 json data jstree에 넣어주기
						'themes': {
							'name' : 'proton', 		//테마 이름
							'responsive' : true,
							"dots": false, 			//연결선 없애기
						}
					}
				//노드(폴더)가 선택시 실행되는 함수
				}).bind("select_node.jstree", function (e, data) {
	 				var id = data.node.id;
	 				$('.groupshare-userpid-left').val(id);	
				});
			}
    	});	
    });
    
    // 링크 내 북마크로 가져가기 버튼 클릭
    $('.get_groupbook').on('dblclick', function(){});
    $('.get_groupbook').on('click', function(){
    	//모달 초기화 START
    	$('#dropdownMenuButton-group').html("Click <span class='caret'></span>");
    	$('#jstree-to-right-group').html('');
	    $('.groupshare-url').text('');
	    $('.groupshare-urlname-left').val('');
	    $('.groupshare-abid-left').val('');
	    //모달 초기화 END
	    
	    //진행중인 팀 리스트 가져오기
	    $.ajax({
	    	url: "/team/getTeamList.do",
	    	type: "post",
	    	success : function(data){
	    		var html = '<ul class="dropdown-menu">';
			    var index = 0;
			    for(var key in data.teamlist){
			    	
			    	if (index == 0) {
			    		html += '<li class="dropdown-group-item" onclick="selectedGroupget('
			    			+ "'" + data.teamlist[key].gname + "', '" + data.teamlist[key].gid
			    			+ "');" + '"><span tabindex="-1">' + data.teamlist[key].gname
			    			+ '</span></li><hr class="divider-hr">';
			    	} else {
			    		html += '<li class="dropdown-group-item" onclick="selectedGroupget('
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
  
    //[버튼]:나의 북마크로 추가 버튼 클릭했을 때
    $('#into-my-bookmark-getgroup-btn').on('dblclick', function(){});
    $('#into-my-bookmark-getgroup-btn').on('click', function(){
    	
    	var checked_ids = [];
    	var submit_obj = [];
    	var selected_node_id = $('.groupshare-userpid-left').val();
	    checked_ids = $('#jstree-from-left-group').jstree("get_checked",null,true);
	    
	    if(checked_ids == null){
	    	swal({title: "선택한 URL이 없습니다!",
                text: "잠시후 다시 시도해주세요!",
                icon: "warning",
                dangerMode: true
			});
	        return false
	    };
	    
	    if(selected_node_id == 0) {
	    	swal({title: "목적지 폴더를 확인하셨나요?",
                text: "잠시후 다시 시도해주세요!",
                icon: "warning",
                dangerMode: true
			});
	        return false
	    };
	    
	    $.each(checked_ids,function(key,value) {
	        //폴더가 아닌 url만 골라 가져가기
	        var checked_url = $('#jstree-from-left-group').jstree(true).get_node(value).a_attr.href;
	        var urlname = $('#jstree-from-left-group').jstree(true).get_node(value).text;
	        if(checked_url !='#'){
	            submit_obj.push({
	            	url : checked_url,
	            	urlname : urlname,
	            	pid : selected_node_id
	            }) 
	        }
	     });

        if(submit_obj.length == 0){
    		swal({
    			title: "해당 카테고리엔 URL이 존재하지 않습니다!",
                text: "잠시후 다시 시도해주세요!",
                icon: "warning",
                dangerMode: true
    		});
    		return;
    	}
        
	    var submit_obj_json = JSON.stringify(submit_obj);

    	$.ajax({
    		url : "/user/insertGroupUrl.do",
    		type:"POST",
    		data: {obj : submit_obj_json},
    		beforeSend : function(){
    			console.log(submit_obj_json);
    		},
    		success : function(data){
    			if(data.result == "success") {
    				swal("Thank you!", "북마크에 추가되었습니다!", "success");
    				$('.groupshare-userpid-left').val(0);
    				$('#socialGroupModal').modal("toggle");
    				$('#socialGroupModal').css({"z-index":"0"});
    			} else {
    				swal({
    					title: "목적지 폴더를 확인하셨나요?",
                        text: "잠시후 다시 시도해주세요!",
                        icon: "warning",
                        dangerMode: true
    				});
    				return;
    			}
    		},
    		error : function(error) {
    			swal({
    				title: "목적지 폴더를 확인하셨나요?",
                    text: "잠시후 다시 시도해주세요!",
                    icon: "warning",
                    dangerMode: true
    			});
    			return;
    		}
		})
    });
    
    //[버튼]:그룹 북마크로 추가 버튼 클릭했을 때
    $('#into-group-bookmark-getgroup-btn').on('dblclick', function(){});
	$('#into-group-bookmark-getgroup-btn').on('click', function(){
		if($('.groupshare-url').text() == '#'){
			swal({
				title: "목적지 폴더를 확인하셨나요?",
                text: "잠시후 다시 시도해주세요!",
                icon: "warning",
                dangerMode: true
			});
			return;
		}

    	var checked_ids = [];
    	var submit_obj = [];
    	var selected_node_id = $('.groupshare-userpid-left').val();
	    checked_ids = $('#jstree-from-left-group').jstree("get_checked",null,true);
	    
	    if(checked_ids == null){
	    	swal({title: "선택한 URL이 없습니다!",
                text: "잠시후 다시 시도해주세요!",
                icon: "warning",
                dangerMode: true
			});
	        return false
	    };
	    
	    if(selected_node_id == 0) {
	    	swal({title: "목적지 폴더를 확인하셨나요?",
                text: "잠시후 다시 시도해주세요!",
                icon: "warning",
                dangerMode: true
			});
	        return false
	    };
	    
	    $.each(checked_ids,function(key,value) {
	        //폴더가 아닌 url만 골라 가져가기
	        var checked_url = $('#jstree-from-left-group').jstree(true).get_node(value).a_attr.href;
	        var urlname = $('#jstree-from-left-group').jstree(true).get_node(value).text;
	        var gid = $('.groupshare-gid-left').val();
	        if(checked_url !='#'){
	            submit_obj.push({
	            	url : checked_url,
	            	urlname : urlname,
	            	pid : selected_node_id,
	            	gid : gid
	            }) 
	        }
	    });

        if(submit_obj.length == 0){
    		swal({
    			title: "해당 카테고리엔 URL이 존재하지 않습니다!",
                text: "잠시후 다시 시도해주세요!",
                icon: "warning",
                dangerMode: true
    		});
    		return;
    	}
        
	    var submit_obj_json = JSON.stringify(submit_obj);

		$.ajax({
			url : "getGroupBookList.do",
			type: "POST",
			data: {
				obj : submit_obj_json
			},
			success : function(data){
				if(data.result == "success") {
					swal("Thank you!", "북마크에 추가되었습니다!", "success");
					$('.groupshare-userpid-left').val(0);
					$('#socialGroupModal').modal("toggle");
					$('#socialGroupModal').css({"z-index":"0"});
				} else {
					swal({
						title: "목적지 폴더를 확인하셨나요?",
	                    text: "잠시후 다시 시도해주세요!",
	                    icon: "warning",
	                    dangerMode: true
					});
				}
			},
			error : function(error) {
				swal({
					title: "목적지 폴더를 확인하셨나요?",
                    text: "잠시후 다시 시도해주세요!",
                    icon: "warning",
                    dangerMode: true
				});
			}
		});
	});
})
    
//내 그룹리스트 중 하나를 선택 했을 때,
function selectedGroupget(group, gid) {
	$('#dropdownMenuButton-group').text(group);
	$('.groupshare-gid-left').val(gid);
	$('#into-my-bookmark-getgroup-btn').css('display', 'none');
	$('#into-group-bookmark-getgroup-btn').css('display', 'inline');
	$('#jstree-to-right-group').remove();
	$('.completed-modal-right-group').append('<div id="jstree-to-right-group"></div>');
	
	$.ajax({
		url : "/team/getGroupCategoryList.do",
		type:"POST",
		data: {gid: gid},
		dataType:"json",
		success : function(data){
			$("#jstree-to-right-group").jstree({
				"core": {
					'data' : data.data,
					'themes':{
						'name' : 'proton',
						'responsive' : true,
						"dots": false,
					}
				}
			
			}).bind("select_node.jstree", function (e, data) {
				var id = data.node.id;
				$('.groupshare-userpid-left').val(id);
			});
		}
	});
}
/* 민재 onclick END */

/*진수 start*/
var first_data = null;

function surfing_modal(d){
	$('.nname').text(d.id);
	var nname = d.id; 	//클릭한 작성자 닉네임

	$.ajax({
		url : "getCategoryList.do",
		type : "POST",
		data : {nname : nname},
		dataType :"json",
		success : function(obj){
			first_data = obj;
			$('#jstree-from-left-all').jstree(true).settings.core.data = obj;
			$('#jstree-from-left-all').jstree(true).refresh();
		}
	})
	//모달열기
	$('#socialSurfingModal').modal();
	$('#socialSurfingModal').css({'Z-index':'9999'});
};

$(document).ready(function(){
	//파도타기 모달 왼쪽 jstree
    $("#jstree-from-left-all").jstree({
    	"core" : {
    		'data' : first_data,
    		'themes':{
    			'name' : 'proton',
    			'responsive' : true,
    			'dots' : false,
    		}
    	},
    	"checkbox" : { // 체크 박스 클릭시에만 checked 되기
    		"whole_node" : false,
            "tie_selection" : false
          },
          "plugins" : ["checkbox" ]
          
    })
        
    //파도타기 모달 오른쪽 jstree
    //나의 북마크 선택했을 때
    $('#dropdown-my-bookmark').on('dblclick', function(){});
    $('#dropdown-my-bookmark').on('click', function(){

    	$('#dropdownMenuButton').text($(this).text());
    	$('#into-my-bookmark-surfing-btn').css('display', 'inline');
    	$('#into-group-bookmark-surfing-btn').css('display', 'none');
    	$('#jstree-to-right-all').remove();
    	$('.completed-modal-right-all').append('<div id="jstree-to-right-all"></div>');

    	$.ajax({
    		url : "/user/getCategoryList.do",
			type:"POST",
			dataType:"json",
			success : function(data){
				//jstree 시작, jstree를 뿌려주고 싶은 div의 id를 적어준다
				$("#jstree-to-right-all").jstree({
					"core": {
						'data' : data, 				//ajax로 가져온 json data jstree에 넣어주기
						'themes': {
							'name' : 'proton', 		//테마 이름
							'responsive' : true,
							"dots": false, 			//연결선 없애기
						}
					}
				//노드(폴더)가 선택시 실행되는 함수
				}).bind("select_node.jstree", function (e, data) {
	 				var id = data.node.id;
	 				$('.indishare-userpid-left').val(id);	
				});
			}
    	});	
    });
    
    // 링크 내 북마크로 가져가기 버튼 클릭
    $('.table-write').on('dblclick', function(){});
    $('.table-write').on('click', function(){
    	//모달 초기화 START
    	$('#dropdownMenuButton').html("Click <span class='caret'></span>");
    	$('#jstree-to-right-all').html('');
	    $('.indishare-url-surfing').text('');
	    $('.indishare-urlname-left').val('');
	    $('.indishare-abid-left').val('');
	    //모달 초기화 END
	    
	    //진행중인 팀 리스트 가져오기
	    $.ajax({
	    	url: "/team/getTeamList.do",
	    	type: "post",
	    	success : function(data){
	    		var html = '<ul class="dropdown-menu">';
			    var index = 0;
			    for(var key in data.teamlist){
			    	
			    	if (index == 0) {
			    		html += '<li class="dropdown-group-item" onclick="seletedGroup('
			    			+ "'" + data.teamlist[key].gname + "', '" + data.teamlist[key].gid
			    			+ "');" + '"><span tabindex="-1">' + data.teamlist[key].gname
			    			+ '</span></li><hr class="divider-hr">';
			    	} else {
			    		html += '<li class="dropdown-group-item" onclick="seletedGroup('
			    			+ "'" + data.teamlist[key].gname + "', '" + data.teamlist[key].gid
			    			+ "');" + '"><span>' + data.teamlist[key].gname
			    			+ '</span></li><hr class="divider-hr">';
			    	}
			    	index += 1;
			    }
			    html += '</ul>';
			    $(".surfing").append(html);
	    	}
	    });
		$('#socialSurfingModal').css({'Z-index':'0'});
    });
    
    //[버튼]:나의 북마크로 추가 버튼 클릭했을 때
    $('#into-my-bookmark-surfing-btn').on('dblclick', function(){});
    $('#into-my-bookmark-surfing-btn').on('click', function(){
    	if($('.indishare-url-surfing').text() == '#'){
    		swal({
    			title: "목적지 폴더를 확인하셨나요?",
                text: "잠시후 다시 시도해주세요!",
                icon: "warning",
                dangerMode: true
    		});
    		return;
    	}
    	
    	var checked_ids = [];
        var submit_obj = [];
        var selected_node_id = $('.indishare-userpid-left').val();
        checked_ids = $('#jstree-from-left-all').jstree("get_checked",null,true);
        
        if(checked_ids == null){
        	swal({title: "선택한 URL이 없습니다!",
                text: "잠시후 다시 시도해주세요!",
                icon: "warning",
                dangerMode: true
			});
	        return false
	    };
	    
	    if(selected_node_id == 0) {
	    	swal({title: "목적지 폴더를 확인하셨나요?",
                text: "잠시후 다시 시도해주세요!",
                icon: "warning",
                dangerMode: true
			});
	        return false
	    };
        
        $.each(checked_ids,function(key,value) {
            //폴더가 아닌 url만 골라 가져가기
            var checked_url = $('#jstree-from-left-all').jstree(true).get_node(value).a_attr.href;
            var urlname = $('#jstree-from-left-all').jstree(true).get_node(value).text;
            if(checked_url !='#'){
            	submit_obj.push({
	            	url : checked_url,
	            	urlname : urlname,
	            	pid : selected_node_id
	            }) 
            }
        });
        
        if(submit_obj.length == 0){
    		swal({
    			title: "해당 카테고리엔 URL이 존재하지 않습니다!",
                text: "잠시후 다시 시도해주세요!",
                icon: "warning",
                dangerMode: true
    		});
    		return;
    	}
        
        var submit_obj_json = JSON.stringify(submit_obj);
    	
    	$.ajax({
    		url : "/user/insertGroupUrl.do",
    		type:"POST",
    		data: {
    			obj : submit_obj_json
    		},
    		success : function(data){
    			if(data.result == "success") {
    				swal("Thank you!", "북마크에 추가되었습니다!", "success");
    				$('.indishare-userpid-left').val(0);
    				$('#socialSurfingModal').modal("toggle");
    			} else {
    				swal({
    					title: "목적지 폴더를 확인하셨나요?",
                        text: "잠시후 다시 시도해주세요!",
                        icon: "warning",
                        dangerMode: true
    				});
    			}
    		},
    		error : function(error) {
    			swal({
    				title: "목적지 폴더를 확인하셨나요?",
                    text: "잠시후 다시 시도해주세요!",
                    icon: "warning",
                    dangerMode: true
    			});
    		}
		});
    	$('#socialSurfingModal').css({'Z-index':'0'});
    });
    
    //[버튼]:그룹 북마크로 추가 버튼 클릭했을 때
    $('#into-group-bookmark-surfing-btn').on('dblclick', function(){});
	$('#into-group-bookmark-surfing-btn').on('click', function(){
		if($('.indishare-url-surfing').text() == '#'){
			swal({
				title: "목적지 폴더를 확인하셨나요?",
                text: "잠시후 다시 시도해주세요!",
                icon: "warning",
                dangerMode: true
			});
			return;
		}
		
		var checked_ids = [];
        var submit_obj = [];
        var selected_node_id = $('.indishare-userpid-left').val();
        checked_ids = $('#jstree-from-left-all').jstree("get_checked",null,true);
		
        if(checked_ids == null){
        	swal({title: "선택한 URL이 없습니다!",
                text: "잠시후 다시 시도해주세요!",
                icon: "warning",
                dangerMode: true
			});
	        return false
	    };
	    
	    if(selected_node_id == 0) {
	    	swal({title: "목적지 폴더를 확인하셨나요?",
                text: "잠시후 다시 시도해주세요!",
                icon: "warning",
                dangerMode: true
			});
	        return false
	    };
        
        $.each(checked_ids,function(key,value) {
            //폴더가 아닌 url만 골라 가져가기
            var checked_url = $('#jstree-from-left-all').jstree(true).get_node(value).a_attr.href;
            var urlname = $('#jstree-from-left-all').jstree(true).get_node(value).text;
            var gid = $('.indishare-gid-left').val();
            if(checked_url !='#'){
            	submit_obj.push({
	            	url : checked_url,
	            	urlname : urlname,
	            	pid : selected_node_id,
	            	gid : gid
	            }) 
            }
        });

        if(submit_obj.length == 0){
    		swal({
    			title: "해당 카테고리엔 URL이 존재하지 않습니다!",
                text: "잠시후 다시 시도해주세요!",
                icon: "warning",
                dangerMode: true
    		});
    		return;
    	}
        
        var submit_obj_json = JSON.stringify(submit_obj);
        
		$.ajax({
			url : "getGroupBookList.do",
			type: "POST",
			data: {
				obj : submit_obj_json
			},
			success : function(data){
				if(data.result == "success") {
					swal("Thank you!", "북마크에 추가되었습니다!", "success");
					$('.indishare-userpid-left').val(0);
					$('#socialSurfingModal').modal("toggle");
				} else {
					swal({
						title: "목적지 폴더를 확인하셨나요?",
	                    text: "잠시후 다시 시도해주세요!",
	                    icon: "warning",
	                    dangerMode: true
					});
				}
			},
			error : function(error) {
				swal({
					title: "목적지 폴더를 확인하셨나요?",
                    text: "잠시후 다시 시도해주세요!",
                    icon: "warning",
                    dangerMode: true
				});
			}
		});
		$('#socialSurfingModal').css({"Z-index":"0"});
	});
})
    
//내 그룹리스트 중 하나를 선택 했을 때,
function seletedGroup(group, gid) {
	$('#dropdownMenuButton').text(group);
	$('.indishare-gid-left').val(gid);
	$('#into-my-bookmark-surfing-btn').css('display', 'none');
	$('#into-group-bookmark-surfing-btn').css('display', 'inline');
	$('#jstree-to-right-all').remove();
	$('.completed-modal-right-all').append('<div id="jstree-to-right-all"></div>');
	
	$.ajax({
		url : "/team/getGroupCategoryList.do",
		type:"POST",
		data: {gid: gid},
		dataType:"json",
		success : function(data){
			$("#jstree-to-right-all").jstree({
				"core": {
					'data' : data.data,
					'themes':{
						'name' : 'proton',
						'responsive' : true,
						"dots": false,
					}
				}
			
			}).bind("select_node.jstree", function (e, data) {
				var id = data.node.id;
				$('.indishare-userpid-left').val(id);
			});
		}
	});
}

// 진수, 개인 북마크 조회수 증가
function indiViewCount(ubid) {
	var count = $('#social_'+ ubid + ' .indiCount').text();
	
	$.ajax({
		url : "indiViewCount.do",
		type:"POST",
		data: {ubid : ubid},
		success : function(data){
			if(data.result == "success"){
				$('#social_'+ ubid + ' .indiCount').text(Number(count) + 1);
			} else {
				$('#social_'+ ubid + ' .indiCount').text(Number(count));
			}
		}
	});
}


//진수, 그룹 조회수 증가
function groupViewCount(gid) {
	var count = $('#group_'+ gid + ' .groupCount').text();
	
	$.ajax({
		url : "groupViewCount.do",
		type:"POST",
		data: {gid : gid},
		success : function(data){
			if(data.result == "success"){
				$('#group_'+ gid + ' .groupCount').text(Number(count) + 1);
			} else {
				$('#group_'+ gid + ' .groupCount').text(Number(count));
			}
		}
	});
}

function socialGetGroup(group, gid){
	get_groupbook(group);
	groupViewCount(gid);
}

/*진수 end*/
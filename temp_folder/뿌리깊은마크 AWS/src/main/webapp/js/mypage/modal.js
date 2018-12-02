
/* **************************************************************************
 * 완료된 그룹 모창 JS
 ************************************************************************** */

var selected_node_id = 0;

//완료된 그룹 리스트 클릭시 해당 그룹의 북마크 가져온다.
function open_completed_group_modal(gname, gid){
	$('#completedGroupModal').css({"z-index":"9999"});
	$('#from-text').text(gname);
	$('#dropdownMenuButton').html('Click<span class="caret"></span>');
	$('#jstree-to-right').jstree().deselect_all(true);
	$('#jstree-to-right').jstree(true).settings.core.data = null;
	$('#jstree-to-right').jstree(true).refresh();
	
	// 좌측: 완료된 그룹 북마크 가져오기
	$.ajax({
		url : "getCompletedTeamBookmark.do",
		type : "POST",
		data : {gid : gid},	/* group id 를 넣어야 한다. */
		dataType :"json",
		success : function(obj){
			//모달 왼쪽 jstree에 data 넣어주기
			first_data = obj;
			$('#jstree-from-left').jstree().deselect_all(true);
			$('#jstree-from-left').jstree(true).settings.core.data = obj;
			$('#jstree-from-left').jstree(true).refresh();
		}
	})
	
	// 우측 Dropdown: 진행중인 팀 리스트 가져오기
    $.ajax({
    	url: "/team/getTeamList.do",
    	type: "post",
    	success : function(data){
    		var html = '<a tabindex="-1" href="#">나의 그룹북마크</a><ul class="dropdown-menu">';
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
		    $(".dropdown-submenu").html(html);
    	}
    });
	//완료 그룹 모달 띄우기
	$('#completedGroupModal').modal();
}

$(document).ready(function(){
	/* Modal fade in & out */
	//완료된 그룹 모달 > 오른쪽 > 나의 북마크 클릭시
	$('#completed-modal-mybook').click(function() {
	    $('#dropdownMenuButton').text($(this).text());
	    
		$.ajax({
			url : "getCategoryList.do",
			type:"POST",
			dataType:"json",
			success : function(data){
				right_data = data;
				$('#jstree-to-right').jstree(true).settings.core.data = data;
				$('#jstree-to-right').jstree(true).refresh();
				$('#jstree-to-right').jstree("open_all");
				
			}
		})
	});
	
	// [확인]: 나의 북마크로 추가 버튼 클릭했을 때, 
    $('#into-my-bookmark-getgroup-btn').on('dblclick', function(){});
    $('#into-my-bookmark-getgroup-btn').on('click', function(){

    	var checked_ids = $('#jstree-from-left').jstree("get_checked",null,true);
    	var selected_node_id = $('.groupshare-userpid-left').val();
    	var submit_obj = [];
	    
	    if(checked_ids == null || selected_node_id == 0) {
	        swal({
				title: "두 경로를 정확하게 선택해주세요!",
                text: "좌측은 체크박스를 우측은 폴더 경로를 클릭해 주세요!",
                icon: "warning",
                dangerMode: true
			});
	        return false
	    };
	    
	    $.each(checked_ids, function(key,value) {
	        //폴더가 아닌 url만 골라 가져가기
	        var checked_url = $('#jstree-from-left').jstree(true).get_node(value).a_attr.href;
	        var urlname = $('#jstree-from-left').jstree(true).get_node(value).text;
	        if(checked_url !='#'){
	            submit_obj.push({
	            	url : checked_url,
	            	urlname : urlname,
	            	pid : selected_node_id
	            }) 
	        }
    	});
	    
	    var submit_obj_json = JSON.stringify(submit_obj);

    	$.ajax({
    		url : "insertGroupUrl.do",
    		type:"POST",
    		data: {obj : submit_obj_json},
    		success : function(data){
    			if(data.result == "success") {
    				swal("Thank you!", "북마크에 추가되었습니다!", "success");
    				$('#completedGroupModal').modal("toggle");
    				$('#completedGroupModal').css({"z-index":"0"});
    				selected_node_id = 0;
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
		})
    });
    
    // [확인]: 그룹 북마크로 추가 버튼 클릭했을 때, 
    $('#into-group-bookmark-getgroup-btn').on('dblclick', function(){});
    $('#into-group-bookmark-getgroup-btn').on('click', function(){
    	
    	var checked_ids = $('#jstree-from-left').jstree("get_checked",null,true);
    	var selected_node_id = $('.groupshare-userpid-left').val();
    	console.log(selected_node_id);
    	var gid = $('.groupshare-gid-left').val();
    	var submit_obj = [];
    	
    	// 좌측 DIV 폴더클릭 막기
    	if($('.groupshare-url').text() == '#'){
			swal({
				title: "두 경로를 정확하게 선택해주세요!",
                text: "좌측은 체크박스를 우측은 폴더 경로를 클릭해 주세요!",
                icon: "warning",
                dangerMode: true
			});
			return;
		}

        $.each(checked_ids, function(key,value) {
    	        //폴더가 아닌 url만 골라 가져가기
    	        var checked_url = $('#jstree-from-left').jstree(true).get_node(value).a_attr.href;
    	        var urlname = $('#jstree-from-left').jstree(true).get_node(value).text;
    	        if(checked_url !='#'){
    	            submit_obj.push({
    	            	url : checked_url,
    	            	urlname : urlname,
    	            	pid : selected_node_id,
    	            	gid : gid
    	            }) 
    	        }
        	});
        
        var submit_obj_json = JSON.stringify(submit_obj);
        
    	$.ajax({
			url : "/social/getGroupBookList.do",
			type: "POST",
			data:  {obj : submit_obj_json},
			success : function(data){
				if(data.result == "success") {
					swal("Thank you!", "북마크에 추가되었습니다!", "success");
					$('#completedGroupModal').modal("toggle");
					$('#completedGroupModal').css({"z-index":"0"});
				} else {
					swal({
						title: "두 경로를 정확하게 선택해주세요!",
		                text: "좌측은 체크박스를 우측은 폴더 경로를 클릭해 주세요!",
		                icon: "warning",
		                dangerMode: true
					});
				}
			},
			error : function(error) {
				swal({
					title: "두 경로를 정확하게 선택해주세요!",
	                text: "좌측은 체크박스를 우측은 폴더 경로를 클릭해 주세요!",
	                icon: "warning",
	                dangerMode: true
				});
			}
		});
    });
});

//내 그룹리스트 중 하나를 선택 했을 때,
function selectedGroupget(group, gid) {
	$('#dropdownMenuButton').text(group);
	$('.groupshare-gid-left').val(gid);
	$('#into-my-bookmark-getgroup-btn').css('display', 'none');
	$('#into-group-bookmark-getgroup-btn').css('display', 'inline');
	$('#jstree-to-right').remove();
	$('.completed-modal-right-group').append('<div id="jstree-to-right"></div>');
	
	$.ajax({
		url : "/team/getGroupCategoryList.do",
		type:"POST",
		data: {gid: gid},
		dataType:"json",
		success : function(data){
			$("#jstree-to-right").jstree({
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

//완료된 그룹 url 선택후 save 버튼 클릭시
function submitgroupurl() {
	var checked_ids = [];
	var submit_obj = [];
	
	checked_ids = $('#jstree-from-left').jstree("get_checked",null,true);
	
	if(checked_ids == null){
		alert("선택한 URL이 없습니다.")
		return false
	};
	if(selected_node_id == 0) {
		alert("가져가기 할 폴더를 선택하지 않았습니다.")
		return false
	};
	
	$.each(checked_ids,function(key,value) {
		//폴더가 아닌 url만 골라 가져가기
		var checked_url = $('#jstree-from-left').jstree(true).get_node(value).a_attr.href;
		var urlname = $('#jstree-from-left').jstree(true).get_node(value).text;
		if(checked_url !='#'){
			submit_obj.push({url : checked_url , urlname : urlname, pid : selected_node_id}) 
		}
	});
	
	var submit_obj_json = JSON.stringify(submit_obj);
	$.ajax({
		
		url : "insertGroupUrl.do",
		type : "POST",
		data : {obj : submit_obj_json},
		success : function(){
			
		    $('#completedGroupModal').modal("toggle"); 
			$('#jstree_container').jstree().deselect_all(true);
			$('#jstree_container').jstree(true).select_node(selected_node_id);			
			selected_node_id = 0;
		}
	});
	
	$('#completedGroupModal').css({"z-index":"-10"});
}
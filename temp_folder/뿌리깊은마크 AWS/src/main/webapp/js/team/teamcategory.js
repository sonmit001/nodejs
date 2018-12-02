var urlpid = null;
var target = '';
var type= '#';
var new_name = '#';
var location1 = '';
var doing = '';
var modal_url = '#';
var modal_urlname = '#';
var checked_id = '#';
var addUrlFolder_id = '';
var counturl = '';
//(grid , gid, uid ,nname){
function jstreetable(){
	form = {gid : gid}
	/* 그룹 시작시 jstree 가져오기 */
	$.ajax({
		url : "getTeamJstree.do",
		type:"POST",
		data :form,
		dataType:"json",
		success : function(data){	
			/*왼쪽 jstree 시작하기 jstree 생성하고 싶은 div의 id를 적어준다.*/					
			$("#jstree_container")
				.jstree({	
					"core": {
						"data" : data, //ajax로 가져온 json data jstree에 넣어주기
						'themes':{
							'name' : 'proton', //테마 이름
							'responsive' : true,
							"dots": false, // 연결선 없애기
						},
						"check_callback" : function(op, node, par, pos, more){ // 특정 이벤트 실행 전에 잡아 낼 수 있음
							target = node.text;
							location1 = par.text;
							if(node.a_attr.href =='#')
								type='폴더';
							else
								type='URL';

						    switch(op){
						        case 'create_node':   doing = "생성"; 
						        break;
						        case 'rename_node':   doing = "수정";
						        break;
						        case 'delete_node':   doing = "삭제";
						        break;
						        case 'move_node':   doing = "이동"; 
						        break;
						    }
												
							if(op=='move_node'){
							// dnd 일경우 more.core =ture 일 경우에만 메세지 보내기
								if(par.id == '#'){
									return false;
								}
								if(par.a_attr.href != "#"){ // 최상단(root)와 동급 불가										
									return false;	
								}
								
							}else if	(op == 'rename_node'){
								new_name = pos;
								
							}else if(op =='delete_node'){
								
							}else if(op == 'create_node'){
								sendmessagejstree()
							}
							
							//DND 처리 
							if(op === "move_node"){ // dnd 이벤트 일때 
								var dragnode = node.id;
								var dropnode = par.id;
								form = {dragnode : dragnode , dropnode : dropnode};
								
								$.ajax({	
									
									url : 'dropTeamNode.do',
									type : 'POST',
									data : form,
									beforeSend : function(){
									},
									success : function(data){
										if(more.core){
											new_name = '1';//move 라는거 구분하기 위해서
											location1 = par.text;
											sendmessagejstree()
										}//dnd 성공
									}
								})
								return true;
							}
							return true;	
						}
					},
					"plugins" : [ "dnd","contextmenu" ,"sort"], //drag n drop , 과 우클릭시 플러그인 가져옴
					"sort" : function(a,b){
						return this.get_node(a).a_attr.href.length > this.get_node(b).a_attr.href.length ? 1: -1;
					} ,
					/*우클릭 메뉴 설정*/
					"contextmenu" : { 
						"select_node" : false, // 우클릭 했을 경우 왼클릭되는거 막음
						
						/*왼쪽 jstree  우클릭시 생성되는 메뉴 구성하기 START*/
						"items" : customMenu
					}			    
				})	
				.bind("loaded.jstree", function (event, data) {
					$('#jstree_container').jstree("open_all");
					var test = data.instance._model.data
				})
			    .bind('rename_node.jstree', function(event, data){
		    		var node_id = data.node.id;
		    		var node_text = data.text;
		    		/*왼쪽 jstree 폴더 이름 수정하기*/			    		
		    		$.ajax({
	        			url : 'updateTeamNodeText.do',
	        			type: 'POST',
	        			data: {'gbid' : node_id, 'text' : node_text},
	        			beforeSend : function(){
     					},
	        			success : function(result){
	        				//console.log(result.result);
	        				sendmessagejstree()
	        			}
	        		});   
		    		
		    	})
		    	.bind('delete_node.jstree',function(event,data){
			    		/*왼쪽 jstree 폴더 삭제하기*/
		    		var node_id = data.node.id;
		    		var form = {gbid : node_id}
		    		
		    		$.ajax({
		    			url:'deleteTeamNode.do',
		    			type:'POST',
		    			dataType : "json",
		    			data: form,
		    			beforeSend : function(){
     					},
     					success : function(result){
     						//console.log(result.result);
     						sendmessagejstree()
						}
					})  
		    	})
		    	.bind("select_node.jstree",function(e,data){
		    		var href = data.node.a_attr.href;
		    		if(href !='#'){
						window.open(href); 
		    		}
		    	})
		}
	})
	/*왼쪽 jstree 폴더 열렸을 경우 아이콘 변경해 주기*/	
	$("#jstree_container").on('open_node.jstree', function(e,data){
		$.jstree.reference('#jstree_container').set_icon(data.node, "fa fa-folder-open")
	})
	/*왼쪽 jstree 폴더 닫혔을 경우 아이콘 변경해 주기*/
	$("#jstree_container").on('close_node.jstree', function(e,data){
		$.jstree.reference('#jstree_container').set_icon(data.node, "fa fa-folder")
	})	
	
	/*모달창 마이카테고리 폴더 jstree*/
	$.ajax({
		url : "/user/getCategoryList.do",
		type:"POST",
		dataType:"json",
		success : function(data){	
			$('#jstree-to-mybookmark')
			.jstree({	
				"core": {
					"data" : data, //ajax로 가져온 json data jstree에 넣어주기
					'themes':{
						'name' : 'proton', //테마 이름
						'responsive' : true,
						"dots": false, // 연결선 없애기
					},
				}
			})
			.bind("loaded.jstree", function (event, data) {
				$('#jstree-to-mybookmark').jstree("open_all");
			})
			.bind("select_node.jstree",function(e,data){
				checked_id =  data.node.id;
			})
		}
	})
	
	/*모달창 마이카테고리 전체 jstree*/
	$.ajax({
		url : "getMyCategoryList.do",
		type : "POST",
		dataType :"json",
		success : function(obj){
			
			$('#jstree-from-mybook')
			.jstree({	
				"core": {
					"data" : obj, //ajax로 가져온 json data jstree에 넣어주기
					'themes':{
						'name' : 'proton', //테마 이름
						'responsive' : true,
						"dots": false, // 연결선 없애기
					},
				},
				"checkbox" : { // 체크 박스 클릭시에만 checked 되기
		    		"whole_node" : false,
		            "tie_selection" : false
		          },
		          "plugins" : ["checkbox" ]
			})
			.bind("loaded.jstree", function (event, data) {
				$('#jstree-to-mybookmark').jstree("open_all");
			})
		}
	})

};
	
function addUrlLevel1() {
	$(".addUrlLevel1").show();
	$(".addUrlLevel2").hide();
	$(".addUrlLevel2").hide();
}

function openAddUrlLevel2() {
	
	var url = $("#url_btn").val().trim();
	var regex =/^(?:(?:(?:https?|ftp):)?\/\/)(?:\S+(?::\S*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})))(?::\d{2,5})?(?:[/?#]\S*)?$/;
	if(!(regex.test($('#url_btn').val()))){
		$.alert("URL을 확인해주세요");
	}else {
		$.ajax({
    		url: "/user/preview.do",
			type: "post",
			data : {
				url : url // URL 주소
			},
			beforeSend: function() {
				
				$("#title_btn").css("cursor", "wait ");
         		$("#title_btn").val("");
         		//console.log("부모 ID : " + urlpid);
         		
         		var text = $("#jstree_container").jstree(true).get_node(urlpid).text;
         		//console.log("카테고리 : " + text + "/////")
         		$("#category_btn").val(text);
         		addUrlLevel2();
            },
            complete: function() {
            	$("#title_btn").css("cursor", "default");
            },
			success : function(data){
				$("#title_btn").val(data.title);
			},
    	});
	}
	
}

//2단계 폼 보여주기
function addUrlLevel2() {
	$(".addUrlLevel2").show();
	$(".addUrlLevel1").hide();
}

//url 추가 
$('#addurlbtn').on('dblclick', function(){ return });
$('#addurlbtn').on("click",function(){
	var url = $('#url_btn').val(); //추가 url 값
	var title = $('#title_btn').val(); // 추가 url 명값
	var tree = $("#jstree_container").jstree(true);
	var form = {url : url , urlname : title , pid : urlpid, gid:gid}
	//console.log(form);
	 if(title == ""){
		 $.alert("제목을 입력해주세요")
	 }else {
		$.ajax({
			url: "addTeamFolderOrUrl.do",
			type :"POST",
			data : form,
			success : function(data){
				$('#linkAdd_btn').modal("toggle"); 
				var par_node = $('#jstree_container').jstree(true).get_node(urlpid);
				var node_id = $.trim(data.result);
				tree.create_node(par_node , {text : title , id : node_id  , icon : "https://www.google.com/s2/favicons?domain="+ url ,uid: uid ,a_attr : {href: url}} ,"last",function(new_node){
				});
			}
		})
	 }	
})

function customMenu($node){
	var node_uid = $node.original.uid;
	var node_root = $node.parent;
	var href = $node.a_attr.href;
	var tree = $("#jstree_container").jstree(true);	
	urlpid = $node.id;
	// 링크 만들기, 폴더 만들기, 이름 바꾸기, 삭제
	var items = { 
			"link_create" : {
				"icon" : "fa fa-plus",
				"separator_before": false,
				"separator_after": false,
				"label": "북마크 추가",
				"action": false,
				"submenu" : {
					"addurl":{
						"separator_before": false,
						"separator_after": false,
						"label": "직접 추가",
						"action": function (obj) {
							$('#form_btn')[0].reset();// form 내부 값 reset
							$('#linkAdd_btn').modal(); //url 추가 모달 창 띄우기
							addUrlLevel1()
						}
					},
					"fromMyBook":{
						"separator_before": false,
						"separator_after": false,
						"label": "내 북마크",
						"action" : function(obj){
							
							$('#jstree-from-mybook').jstree().uncheck_all( );		
							var inst = $.jstree.reference(obj.reference);
							var par_text = inst.get_node(obj.reference).text;
							addUrlFolder_id= inst.get_node(obj.reference).id;
							location1 = par_text;
							$('#addUrlFolder').val(par_text);
							$('#fromMytoGroup').modal();
							
						}
					}
				}
			},
			"folder_create": {
				"icon" : "fa fa-plus-circle",
				"separator_before": false,
				"separator_after": false,
				"_disabled" : false, 
				"label": "폴더 추가",
				"action": function (obj) {
					var inst = $.jstree.reference(obj.reference);
					var par_node = inst.get_node(obj.reference);
					var par = inst.get_node(obj.reference).id;
					var form = {urlname : "새 폴더", pid : par , gid : gid};// 해당 유저의 아이디 가져오기
					
					$.ajax({
						url: "addTeamFolderOrUrl.do",
						type :"POST",
						data : form,
						beforeSend : function(){
						},
		     			success : function(data){
		     				var node_id = $.trim(data.result);
		     				
		     				/*왼쪽 jstree 새폴더 생성과 동시에 이름 수정하게 하기*/							     			
		     				tree.create_node(par_node , {text : "새 폴더" , id : node_id  ,icon : "fa fa-folder",uid: uid ,a_attr : {href: '#'}} ,"last",function(new_node){
		     					new_node.id = node_id;
		     					tree.edit(new_node);
		     					$(".jstree-rename-input").attr("maxLength",33);
	            			});
	          			}
	               	})
				}
			},
			"rename" : {
				"icon" : "fa fa-edit",
				"separator_before": false,
				"separator_after": false,
				"label": "이름 수정",
				"action" : function (obj) {
					/*왼쪽 jstree 이름 수정하기 아래에 함수 있음*/
					tree.edit($node);			
					$(".jstree-rename-input").attr("maxLength",33);
				}
			},			
			"editurl" : {
				"separator_before"	: false,
				"separator_after"	: false,
				"label" : "URL 수정",
				"action" : function(obj){
					
					$('#form3')[0].reset();	// url 모달창 reset
					$('#editurl').modal();	//url 수정 모달창 띄우기
					
					var inst = $.jstree.reference(obj.reference);
					var url = inst.get_node(obj.reference).a_attr.href;
					var id = inst.get_node(obj.reference).id;
					
					$('#editurlval').val(url);
					$('#editurlsubmit').on('dblclick', function(){ return });
					$('#editurlsubmit').off("click").on("click",function(){
						
						var newurl = $('#editurlval').val();
						var form = {gbid : id, url : newurl }
						var regex =/^(?:(?:(?:https?|ftp):)?\/\/)(?:\S+(?::\S*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})))(?::\d{2,5})?(?:[/?#]\S*)?$/;
						
						if(!(regex.test(newurl))){
							$.alert("URL을 확인해주세요");
						}else{
							$.ajax({
								
								url: "editTeamUrl.do",
								type: "POST",
								data: form ,
								beforeSend : function(){
								},
								success: function(data){
									//$('#editurl').modal("toggle");
									//$('.modal-backdrop').remove()
									$('.modal.in').modal('hide');
									//href 가 반드시 http 로 시작해야한다.
									$(inst.get_node(obj.reference).a_attr).attr("href", newurl);
									$.jstree.reference('#jstree_container').set_icon(inst.get_node(obj.reference), "https://www.google.com/s2/favicons?domain="+ newurl);
									doing = "1"; //url 수정 메세지
									new_name = newurl;
									sendmessagejstree()
								}
							}) 
						}
					})
				}
			},
			"geturl" : {
				"icon" : "fas fa-share",
				"separator_before": false,
				"separator_after": false,
				"label": "내 북마크로",
				"action": function (obj) { 
					
					$('#jstree-to-mybookmark').jstree().deselect_all(true);	
					var inst = $.jstree.reference(obj.reference);
					modal_url = inst.get_node(obj.reference).a_attr.href;
					modal_urlname =  inst.get_node(obj.reference).text;
					
					$('#modalurl').val(modal_url);
					$('#fromGroupToMy').modal();
				}
			},
			"remove" : {
				"icon" : "fa fa-trash",
				"separator_before": false,
				"separator_after": false,
				"label": "삭제",
				"action": function (obj) { 
					tree.delete_node($node);
				}
			}
	    };
	
	if(grid == '3'){ // 일반 그룹
		if(href == '#'){ // 폴더
			if(uid == node_uid){ // 자기꺼
				delete items.remove;
				delete items.editurl;
				delete items.geturl;
			}else{ // 남이 생성한거
				delete items.rename;
				delete items.remove;
				delete items.editurl;
				delete items.geturl;
			}
		}else{ // 링크	
			if(uid == node_uid){ // 자기꺼			
				delete items.folder_create;	
				delete items.link_create;
			}else{ // 남이 생성한거	
				delete items.link_create;
				delete items.folder_create;
				delete items.remove;	
				delete items.rename;
				delete items.editurl;
			}
		}
	}else{//매니저 그룹장
		if(href == '#'){// 폴더
			delete items.editurl;
			delete items.geturl;
			if(node_root == '#')
				delete items.remove;
		}else{ //url
			delete items.folder_create;
			delete items.link_create;
		}
	}
	
	return items;
}
//jstree 수정된거 메세지 폼 만들어서 보내기
function sendmessagejstree() {
	var op_msg = "";
	
	if(doing == "1"){ //url 수정 메세지
		op_msg = nname + "님이 " + location1 + "폴더에서 "+target+ "의 URL을 "+new_name+"으로 수정하였습니다.";    
	}else if(doing == "2"){ // 내북마크에서 다중 urㅣ 가져오기
		op_msg = nname + "님의  " +"북마크에서 "+ location1 +" 폴더 아래에 " +counturl+ " 개의 URL을 추가하였습니다.";
	}else if(new_name == "#" || new_name == null){
    	op_msg =  nname + "님이 " + location1 + "폴더에서 "+target+"("+type+")를 "+doing+"하였습니다.";             
     }else if(new_name == "1"){
    	op_msg =  nname + "님이 " +target + "("+type+")를 " + location1 +"으로 이동하였습니다.";
     }else{
     	op_msg =  nname + "님이 " +location1 + "폴더에서 "+target+"("+type+")를 "+new_name+"으로 "+doing+"하였습니다.";    
     }
	/*stompClient.send("/JSTREE/" + gid, {}, JSON.stringify({
       	nname: nname,
       	content: op_msg
    }));*/
	
/*	stompClient.send("/chat/" + gid, {}, JSON.stringify({
		content: op_msg,
       	nname:  "시스템",
       	profile: "system.png"
    }));*/
	
	new_name = '#';
	doing = '';
	
}

//그룹에서 내 북마크로 가져가기 확인 버튼 누를시
$('#into-my-bookmark').on('dblclick', function(){ return });
$('#into-my-bookmark').on("click",function(){
	var submit_obj = [];
	
    if(modal_url == '#'){
        alert("선택한 URL이 없습니다.")
        return false
    };
    
    if(checked_id == '#'){
    	 alert("선택한 폴더가 없습니다.")
         return false
    }
    submit_obj.push({
		"url": modal_url,
		"urlname" : modal_urlname,
		"pid": checked_id
		});
    
    var submit_obj_json = JSON.stringify(submit_obj);
    
    $.ajax({
		url : "/social/getmybookmark.do",
		type: "post",
		data: {obj : submit_obj_json},
		success : function(data){
			if(data.result == "success") {
				swal("Thank you!", "북마크에 추가되었습니다!", "success");
				$('#fromGroupToMy').modal("toggle");
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
})

//내 북마크에서 그룹으로 url 보내기 확인 버튼 누를시
$('#from-my-bookmark').on('dblclick', function(){ return });
$('#from-my-bookmark').on("click",function(){
	var checked_ids = [];
	var submit_obj = [];
	
	checked_ids = $('#jstree-from-mybook').jstree("get_checked",null,true);
	if(checked_ids == null){
		alert("선택한 URL이 없습니다.")
		return false
	};
    
	$.each(checked_ids,function(key,value) {
		//폴더가 아닌 url만 골라 가져가기
		var checked_url = $('#jstree-from-mybook').jstree(true).get_node(value).a_attr.href;
		var urlname = $('#jstree-from-mybook').jstree(true).get_node(value).text;
		if(checked_url !='#'){
			submit_obj.push({
				url : checked_url , 
				urlname : urlname, 
				pid : addUrlFolder_id, 
				gid : gid
			}) 
		}
	});
    counturl = submit_obj.length;
    var submit_obj_json = JSON.stringify(submit_obj);
    $.ajax({
		url : "/social/getGroupBookList.do",
		type: "post",
		data: {obj : submit_obj_json},
		success : function(data){
			//console.log(data);
			if(data.result == "success") {
				swal("Thank you!", "북마크에 추가되었습니다!", "success");
				$('#fromMytoGroup').modal("toggle");
				doing = "2";//다중의 url 내북마크에서 가져오기
				sendmessagejstree();
				//전체 refresh
				form = {gid : gid}
	            $.ajax({
	             
	            	url : "getTeamJstree.do",
	         		type:"POST",
	         		data :form,
	         		dataType:"json",
	         		success : function(data){
	         			$("#jstree_container").jstree(true).settings.core.data = data;
						$("#jstree_container").jstree(true).refresh();
	         		}
	             })
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
})
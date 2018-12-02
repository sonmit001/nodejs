/*team.jsp 에서 gid, uid 를 가져오기 위한 함수*/
var gid;
var uid;

function get_info(gid, uid){
	gid = gid;
	uid = uid;
}

/* 멤버 초대 */
function member_insert(){
    $.confirm({
        title: '멤버 초대',
        content: '' +
        '<form id="insertMember" action="" class="formGroup" method="post" onsubmit="return false;>' +
        '<div class="form-group">' +
        '<label>추가 할 멤버의 이메일을 입력하세요</label>' +
        '<input type="text" name="uid" class="insertUid form-control" maxlength="30"/>' +
        '<input type="hidden" name="gid" value="'+gid+'" class="banName form-control"/>' +
        '</div>' +
        '</form>',
        closeIcon: true,
        closeIconClass: 'fa fa-close',
        buttons: {
            formSubmit: {
                text: '초대',
                btnClass: 'btn-success',
                keys: ['enter'],
                action: function () {
                    var toid = this.$content.find('.insertUid').val();
                    if(!toid){
	                    $.alert('이메일을 적어주세요');
	                    return false;
	                }
                    
                    $.ajax({
                		url: "invite.do",
            			type: "post",
            			data : { toid : toid, gid: gid },
            			success : function(data){
            				var msg = data.result.trim().toUpperCase();
            				if(msg == "SUCCESS") {
            					/*stompClient.send('/alarm/' + toid , {}, 
            									 	JSON.stringify({
            									 		gid: gid,
            									 		toid: toid,
            									 		fromid: nname,
            									 		gname: gname,
            									 		gmemo: '초대',
            									 		senddate: 'NOW'
            										})
            									);
            					$.alert("초대 쪽지가 전달되었습니다!" + "\n(" + toid + ")");
            					*/
            				} else if(msg == "FAIL") {
            					$.alert("존재하지 않는 이메일입니다!");
            					
            				} else if(msg == "SELF") {
            					$.alert("본인을 초대하실 수 없습니다!");
            					
            				} else if(msg == "MEMBER") {
            					$.alert("이미 그룹 멤버입니다!");
            					
            				} else if(msg == "ALREADY") {
            					$.alert("이미 초대된 사용자입니다!");
            					
            				} else {
            					$.alert("잠시후 다시 시도해주세요!");
            					
            				}
            			}
                	});
                }
            },
            '취소': {
                btnClass: 'btn-red',
                action: function () {
                //close
                }
            },
        }
    });
}

/* 그룹 탈퇴 */
function group_leave(){
    $.confirm({
        title: '그룹 탈퇴',
        content: '' +
        '<form id="leaveGroup" action="/user/leaveGroup.do" class="formGroup" method="post" onsubmit="return false;>' +
        '<div class="form-group">' +
        '<label>그룹을 탈퇴하시겠습니까</label>' +
        '<input type="hidden" name="uid" value="'+uid+'" class="leaveUid form-control"/>' +
        '<input type="hidden" name="gid" value="'+gid+'" class="banName form-control"/>' +
        '</div>' +
        '</form>',
        closeIcon: true,
        closeIconClass: 'fa fa-close',
        buttons: {
            formSubmit: {
                text: '탈퇴',
                btnClass: 'btn-success',
                action: function () {
                    var name = this.$content.find('.leaveUid').val();

                    $("#leaveGroup").submit();

                }
            },
            '취소': {
                btnClass: 'btn-red',
                action: function () {
                //close
                }
            },
        },
        onContentReady: function(){
        	// 그룹 탈퇴  ajaxFrom()
        	$("#leaveGroup").ajaxForm({
        		success: function(data, statusText, xhr, $form){
        			
        			$.alert("현재 그룹에서 탈퇴하셨습니다!");
        			setTimeout(function(){ 
        				location.href= "/user/mybookmark.do"; 
        			}, 1000);
        		}
        	});
        }
    });
}

/* 그룹 완료 */
function group_complete(){
    $.confirm({
    	title: '그룹 완료',
	    content: '' +
	    '<form id="completeGroup" action="/user/completedGroup.do" class="formGroup" method="post" onsubmit="return false;">' +
	    '<div class="form-group">' +
	    '<label>해시태그</label>' +
	    '<input type="text" id="htag_btn2" name="htag" class="name2 form-control" onkeydown="addHashtag2()" maxlength="15">' +
	    '<div id="htag_append2"></div>' +
	    '<input type="hidden" name="gid" value="'+gid+'" class="banName form-control"/>' +
	    '</div>' +
	    '</form>',
	    closeIcon: true,
        closeIconClass: 'fa fa-close',
        buttons: {
            formSubmit: {
                text: '완료',
                btnClass: 'btn-success',
                action: function () {
                	var htag='';
	                 
	                $.each(hashtagList2 , function(index,data){
	        	    	htag += data;
	        	    })
	        	    
	        	    if(htag == ""){
	        			$.alert("해시태그를 하나 이상 입력해주세요")
	        			return false;
	        		}else if(hashtagList2.length >10){
	        			$.alert("해시태그는 10개 까지만 입력 가능합니다");
	        			return false;
	        		}else{
	        			this.$content.find('.name2').val(htag);
	        			$("#completeGroup").submit();
	        		}
                }
            },
            '취소': {
                btnClass: 'btn-red',
                action: function () {
                //close
                }
            },
        },
        onContentReady: function(){
        	// 그룹 완료  ajaxFrom()
        	$("#completeGroup").ajaxForm({
        		success: function(data, statusText, xhr, $form){
        			
        			for(var i = 0; i < $('.member').length; i++){
        				var member_nname = $('.member').eq(i).text();
        				console.log(member_nname);
        				
        				/*stompClient.send('/alarm' + member_nname, {}, 
						 	JSON.stringify({
						 		gid: gid,
						 		fromid: nname,
						 		gname: gname,
						 		gmemo: '완료',
						 		senddate: 'NOW'
							})
						);*/
        			}
        			
        			$.alert("현재 그룹이 완료되었습니다!");
        			setTimeout(function(){ 
        				location.href= "/user/mybookmark.do"; 
        			}, 1000);
        		}
        	});
        }
    });
}

/* 마우스 오른쪽 이벤트 (회원강퇴) 추가*/
function myContextMenu() {
	if( grid == 1 ){
		$.contextMenu({
	        selector: '.member', 
	        callback: function(key, opt) {
	            var targetNname = opt.$trigger.text().trim();
	            var hisGrid = opt.$trigger.eq(0).attr("data-grid");
	            
	            if(key == "ban"){
	            	member_ban(targetNname, hisGrid);
	            }
	            else {
					member_auth(key, targetNname, hisGrid);
	            }
	        },
	        items: {
	            "manager": {name: "매니저 승급"},
	            "member": {name: "매니저 강등"},
	            "sep1": "---------",
	            "ban": {name: "강퇴"}
	        }
	    });   
	}
	
	if( grid == 2 ){
		$.contextMenu({
	        selector: '.member', 
	        callback: function(key, opt) {
	            var targetNname = opt.$trigger.text().trim();
	            var hisGrid = opt.$trigger.eq(0).data("grid");
	            
	            if(key == "ban"){
	            	member_ban(targetNname, hisGrid);
	            }
	        },
	        items: {
	            "ban": {name: "강퇴"}
	        }
	    });   
	}
}

/* 멤버 권한 관리 START */
function member_auth(key, targetNname, hisGrid){   	
	// 그룹원 권한 권리  Ajax
	$.ajax({
		url: "giveGorupRole.do",
		type: "post",
		data : { key: key, nname: targetNname, gid: gid, grid: hisGrid },
		success : function(data){
			var recv_data = data.result;
			
			if(recv_data == 'fail') { $.alert('잠시후 다시 시도해주세요!'); }
			else if(recv_data == 'master') { $.alert('그룹장이십니다!'); }
			else if(recv_data == 'manager') { $.alert('이미 매니저입니다!'); }
			else if(recv_data == 'member') { $.alert('이미 그룹원입니다!'); }
			else if(data.result == "success") { 
				$.alert("해당 권한이 부여되었습니다");
				$("#"+targetNname).attr("data-grid", (key=="manager" ? 2 : 3));
				if(key == "manager") {
    				$("#" + targetNname).append(' <i class="fas fa-chess-knight group-manager"></i>');
    			}else {
    				$("#" + targetNname).children().last().remove();
    			}
				
				/*stompClient.send('/alarm/' + targetNname, {}, 
					 	JSON.stringify({
					 		gid: gid,
					 		toid: targetNname,
					 		fromid: uid,
					 		gname: gname,
					 		gmemo: key,
					 		senddate: 'NOW'
						})
				);*/
				//수정 요
				/*stompClient.send('/chat/' + gid, {}, 
					 	JSON.stringify({
					 		nname: "시스템",
					 		content: "'" + targetNname + "'님이 " + (key=="manager" ? "매니저" : "그룹원") + "이(가) 되셨습니다!",
					 		profile: "system.png"
						})
				);*/
			}
			else {
				$.alert("잠시후 다시 시도해주세요!");
			}
		}
	});
}
/* 멤버 권한 관리 END */

/* 멤버 강퇴 START */
function member_ban(targetNname, hisGrid){
    $.confirm({
        title: '멤버 강퇴',
        content: '' +
        '<form id="banMember" action="banMember.do" class="formGroup" method="post" onsubmit="return false;">' +
        '<div class="form-group">' +
        '<label>['+targetNname+'] 회원을 강퇴하시겠습니까</label>' +
        '<input type="hidden" name="nname" value="' + targetNname + '" class="banName form-control"/>' +
        '<input type="hidden" name="gid" value="' + gid + '" class="banName form-control"/>' +
        '<input type="hidden" name="grid" value="' + hisGrid + '" class="banName form-control"/>' +
        '<input type="hidden" name="mygrid" value="' + grid + '" class="banName form-control"/>' +
        '</div>' +
        '</form>',
        closeIcon: true,
        closeIconClass: 'fa fa-close',
        buttons: {
            formSubmit: {
                text: '강퇴',
                btnClass: 'btn-success',
                action: function () {
                    $("#banMember").submit();
                    
                }
            },
            '취소': {
                btnClass: 'btn-red',
                action: function () {
                //close
                }
            },
        },
        onContentReady: function(){
        	
        	// 그룹원 강퇴 ajaxFrom()
        	$("#banMember").ajaxForm({
        		success: function(data, statusText, xhr, $form){
        			var recv_data = data.result;
        			
        			if(recv_data == 'fail') { $.alert('잠시후 다시 시도해주세요!'); }
        			else if(recv_data == 'empty') { $.alert('해당 그룹원이 존재하지 않습니다!'); }
        			else if(recv_data == 'master') { $.alert('그룹장이십니다!'); }
        			else if(recv_data == 'manager') { $.alert('매니저입니다!'); }
        			else {
        				var toid = recv_data;
        				
        				/*stompClient.send('/alarm/' + toid , {}, 
						 	JSON.stringify({
						 		gid: gid,
						 		toid: toid,
						 		gname: gname,
						 		gmemo: '강퇴',
						 		senddate: 'NOW'
							})
						);*/
        				
        				$("#" + targetNname).remove();
        				$.alert('해당 그룹원이 강퇴되었습니다!');
        				
        			}
        		}
        	});
        }

    });
}

/* 멤버 강퇴 END */

	

var hashtagList2 = [];
var hashtagStartPoint2 = 0;

/*URL 해시태그 추가(그룹완료)*/ 
function addHashtag2() {
	if (event.keyCode == 13 || event.keyCode == 32 ) {
		if(jQuery.trim($('#htag_btn2').val()) != "" && hashtagList2.length < 10){
			hashtagList2.push("#"+$.trim($('#htag_btn2').val()));
			var hashtag = $.trim($('#htag_btn2').val());
			$('#htag_btn2').val('');
			$('#htag_btn2').focus();
			$('#htag_append2').append("<input class='btn btn-default btn-hash' id='btnHash2" + hashtagStartPoint2 + "' type='button' value='#" + hashtag + "' onclick='deleteHashtag2(this)'>");
			hashtagStartPoint2++;
		}else {
			$.alert("해시태그를 입력해주세요 (최대 10개 입력)");
		}
	}
}

/*해시태그 삭제(그룹완료)*/
function deleteHashtag2(data) {
	var str = $(data).attr('id');
	$('#' + str).remove();
	var val = $(data).val();
	hashtagList2.splice($.inArray(val, hashtagList2), 1);
}
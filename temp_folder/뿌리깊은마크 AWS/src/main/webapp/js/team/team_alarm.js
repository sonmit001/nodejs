


//Header Alarm socket connect
function alarmConnect(stompClient, userid) {/*

    // 메세지 구독
    // WebSocketMessageBrokerConfigurer의 configureMessageBroker() 메소드에서 설정한 subscribe prefix("/subscribe")를 사용해야 함
    stompClient.subscribe('/subscribe/alarm/' + userid, function(message) {
    	
    	var recv_alarm = JSON.parse(message.body);
    	var recv_gid = recv_alarm.gid;
    	var recv_toid = recv_alarm.toid;
    	var recv_fromid = recv_alarm.fromid;
    	var recv_gname = recv_alarm.gname;
    	var recv_ganame = recv_alarm.gmemo
    	var recv_senddate = recv_alarm.senddate;
    	
    	$('#alarm_menu').addClass('animated flash');
    	$('#alarm_menu').css('color', '#ff8300');
    	$('#alarm_menu').css('font-weight', '900');
    	
    	if($('#alarm_menu_li').children('ul').length == 0) {
    		$('#alarm_menu_li').append('<ul role="menu" class="g_alarm_ul dropdown-menu"></ul>');
		}
    	
    	var common_form = '<li id="alarmlist' +recv_gid+ '" class="g_alarm_li">'
    						+ '<span class="g_alarm_head">Group&nbsp;: <span class="g_alarm_name">' +recv_gname+ '</span></span>'
    						+ '<i class="fas fa-times g_notice" onclick="deleteMemo(\''+recv_gid+'\',\''+recv_fromid+'\',\''+recv_ganame+'\');"></i>'
    						+ '<br style="clear:both">';
    	
    	if( recv_ganame == "초대" ) {
    		common_form += '<span class="g_alarm_head">From&nbsp;&nbsp;&nbsp;: '
							+ '<span class="g_alarm_name">' +recv_fromid+ '</span>'
							+ '<span class="g_alarm_date">' +recv_senddate+ '</span>'
							+ '</span><br><span class="g_alarm_content">해당 그룹에서 회원님을 초대했습니다!'
							+ '<i class="fas fa-check g_notice_ok" '
							+ 'onclick="inviteOk(\''+recv_toid+'\',\''+recv_gid+'\',\''+recv_gname+'\',\''+recv_fromid+'\',\''+recv_ganame+'\');"></i>'
							+ '</span><br style="clear:both">';
    	
    	}else if( recv_ganame == "강퇴" ) {
    		
    		if( gid == recv_gid ) {
    			$.alert("현재 그룹에서 강퇴당하셨습니다!");
    			setTimeout(function(){ location.href="/user/mybookmark.do"; }, 3000);
    			return
    			
    		}else {
    			common_form += '<span class="g_alarm_content">해당 그룹에서 회원님을 강퇴했습니다!</span>'
							+ '<i class="fas fa-ban g_notice_no" '
							+ 'onclick="deleteMemo(\''+recv_gid+'\',\''+recv_fromid+'\',\''+recv_ganame+'\');"></i>';
    		}
    		
    	}else if(recv_ganame == "manager") {
    		common_form += '<span class="g_alarm_content">해당 그룹에서 매니저로 임명되었습니다!</span>'
						+ '<i class="fas fa-ban g_notice_no" '
						+ 'onclick="deleteMemo(\''+gid+'\');"></i>';
		
		}else if(recv_ganame == "member") {
			common_form += '<span class="g_alarm_content">해당 그룹에서 일반 그룹원이 되었습니다!</span>'
						+ '<i class="fas fa-ban g_notice_no" '
						+ 'onclick="deleteMemo(\''+gid+'\');"></i>';
			
		}else { return; }
    	
    	common_form += '</li>';
    	$('.g_alarm_ul').prepend(common_form);
    	
    	alarm_count = $('.g_alarm_li').length;
    	if( alarm_count <= 3 && alarm_count > 0 ) {
			$('#alarm-count-text').html("<i class='fas fa-bullhorn'>&nbsp;" + alarm_count + "&nbsp;</i>");
		}else if( alarm_count == 0 ){
			$('#alarm-count-text').html('');
		}else {
			$('#alarm-count-text').html("<i class='fas fa-bullhorn'>&nbsp;3+&nbsp;</i>");
		}
    });
   
    stompClient.subscribe('/subscribe/alarm', function(message) {
    	
    	var recv_alarm = JSON.parse(message.body);
    	var recv_gid = recv_alarm.gid;
    	var recv_toid = recv_alarm.toid;
    	var recv_fromid = recv_alarm.fromid;
    	var recv_gname = recv_alarm.gname;
    	var recv_ganame = recv_alarm.gmemo
    	var recv_senddate = recv_alarm.senddate;
    	
    	if( !headerTeamList.includes(recv_gid) ){ return }
    	
    	if($('#alarm_menu_li').children('ul').length == 0) {
    		$('#alarm_menu_li').append('<ul role="menu" class="g_alarm_ul dropdown-menu"></ul>');
		}
    	
    	var common_form = '<li id="alarmlist' +recv_gid+ '" class="g_alarm_li">'
    						+ '<span class="g_alarm_head">Group&nbsp;: <span class="g_alarm_name">' +recv_gname+ '</span></span>'
    						+ '<i class="fas fa-times g_notice" onclick="deleteMemo(\''+recv_gid+'\',\''+recv_fromid+'\',\''+recv_ganame+'\');"></i>'
    						+ '<br style="clear:both">';
    	
    	if( recv_ganame == "완료" ) {
    		$('#alarm_menu').addClass('animated flash');
    		$('#alarm_menu').css('color', '#ff8300');
    		$('#alarm_menu').css('font-weight', '900');
    		
    		if( gid == recv_gid ) {
    			
    			$.alert("그룹장(" +recv_fromid+ ")에 의해 완료되었습니다!");
    			setTimeout(function(){ location.href="/user/mybookmark.do"; }, 3000);
    			return
    		}
    		else {
        		common_form += '<span class="g_alarm_head">From&nbsp;&nbsp;&nbsp;: '
								+ '<span class="g_alarm_name">'+recv_fromid+'</span>'
								+ '<span class="g_alarm_date">'+recv_senddate+'</span>'
								+ '<i class="fas fa-times g_notice" onclick="deleteMemo(\''+recv_gid+'\',\''+recv_fromid+'\',\''+recv_ganame+'\');"></i>'
							 + '</span><br><span class="g_alarm_content">해당 그룹이 완료되었습니다!</span>';
    		}
    	}
    	// 매니저 혹은 그룹원 권한 처리
    	else {
    		if( gid == recv_gid ) {
    			// 그룹장에게는 안오게
    			if(recv_fromid == uid) {
    				return
    				
    			}// 본인꺼 찾기
    			else if(recv_toid == nname) {
    				$trigger = $('.member');
    				$.alert("그룹장님이 '" + (recv_ganame == "manager" ? "매니저" : "그룹원") + "\' 권한을 부여하셨습니다!");
    				grid = (recv_ganame == "manager" ? 2 : 3);
    				// 우클릭 부활 for 처음 들어온 녀석
    				myContextMenu();
    				(grid == 2) ? $trigger.contextMenu(true) : $trigger.contextMenu(false);

    			}
    			
    			$("#"+recv_toid).attr("data-grid", (recv_ganame == "manager" ? 2 : 3));
    			if(recv_ganame == "manager") {
    				$("#" + recv_toid).append(' <i class="fas fa-chess-knight group-manager"></i>');
    			}else {
    				$("#" + recv_toid).chlidren().last().remove();
    			}
    		}
    		return;
    	}
    	
    	common_form += '</li>';
    	//console.log(common_form);
    	$('.g_alarm_ul').prepend(common_form);
    	
    	alarm_count = $('.g_alarm_li').length;
    	if( alarm_count <= 3 && alarm_count > 0 ) {
			$('#alarm-count-text').html("<i class='fas fa-bullhorn'>&nbsp;" + alarm_count + "&nbsp;</i>");
		}else if( alarm_count == 0 ){
			$('#alarm-count-text').html('');
		}else {
			$('#alarm-count-text').html("<i class='fas fa-bullhorn'>&nbsp;3+&nbsp;</i>");
		}
    });
*/}

//Header Alarm socket disconnect
function alarmDisconnect() {
    stompClient.disconnect();
}
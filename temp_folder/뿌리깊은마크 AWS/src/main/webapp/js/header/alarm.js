
/* 초대 수락 */
function inviteOk(user_nname, gid, gname, fromid, alarm_kind){
	var alarm_item = $("#alarmlist" + gid);
	$.ajax({
		url: "/alarm/joinGroup.do",
		type: "post",
		data : { gid: gid, nname: user_nname, gname: gname },
		success : function(data){
			if( data.result.trim() == "joined" ) {
				$.alert("'" + gname + "'에 가입되셨습니다!");
				deleteMemo(gid, fromid, alarm_kind);
				location.href = data.path;
				
			}else if( data.result.trim() == "already" ){
				$.alert("이미 '" + gname + "' 그룹원이십니다!");
				deleteMemo(gid, fromid, alarm_kind);
			}else {
				$.alert("잠시후 다시 시도해주세요!");
			}
		}
	});
}
/* 쪽지 없애기 */
function deleteMemo(gid, fromid, alarm_kind){
	var alarm_item = $("#alarmlist" + gid);
	var gaid = 0;
	if(alarm_kind == '강퇴') {gaid = 3;}
	else if(alarm_kind == '완료') {gaid = 2;}
	else {gaid = 1;}
	$.ajax({
		url: "/alarm/deleteMemo.do",
		type: "post",
		data : { gid: gid, fromid: fromid, gaid: gaid },
		success : function(data){
			if( data.result == "deleted" ) {
				alarm_item.remove();
				
				if($('.g_alarm_ul').children().length == 0) {
					$('.g_alarm_ul').remove();
				}
				
				alarm_count = $('.g_alarm_li').length;
				if(alarm_count != 0) {
					$('#alarm-count-text').html("&nbsp;" + alarm_count + "&nbsp;");
				}else {
					$('#alarm-count-text').html("");
				}
				
			}else {
				/*$.alert("잠시후 다시 시도해주세요!");*/
			}
		}
	});
	return
}
function deleteMemo(gid){
	var alarm_item = $("#alarmlist" + gid);
	
	alarm_item.remove();
	
	if($('.g_alarm_ul').children().length == 0) {
		$('.g_alarm_ul').remove();
	}
	
	alarm_count = $('.g_alarm_li').length;
	if(alarm_count != 0) {
		$('#alarm-count-text').html("&nbsp;" + alarm_count + "&nbsp;");
	}else {
		$('#alarm-count-text').html("");
	}
	return;
}

/*Alarm icon script START*/

$(function() {
	
	$('#alarm_menu_li').click(function(){
		$('#alarm_menu').removeClass("animated flash");
		$('#alarm_menu').css('color', '#000');
		$('#alarm_menu').css('font-weight', '400');
	})
	
	$('.g_alarm_ul').on('click', function(event){ //알림창 내의 목록 클릭시 알림창이 꺼지지 않도록 이벤트 멈춤
	    event.stopPropagation();
	});
})

/*Alarm icon script END*/
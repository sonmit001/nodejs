// 화면 전환시 채팅 스크롤 최하단으로 위치
$(".chat-element").scrollTop($(".chatting-contents").height());
$('#chat-textbox-text').each(function() {
    this.contentEditable = true;
});
$('#chat-textbox-text').click(function() {
    $('#chat-textbox-text').focus()
});
$('#chat-textbox-text').keyup(function (e) {return});
$('#chat-textbox-text').keydown(function (e) {
    if( e.shiftKey && e.keyCode == 13 ) {
        e.stopPropagation();
        $('#chat-textbox-text').append('\n');
    } else if(e.keyCode == 13) { // Ctrl-Enter pressed
        event.preventDefault();
        sendMessage();
        $('#chat-textbox-text').html('');
    }
});
var messages; //새로운 메시지

$(function() {
	'use strict';

	// Initialize Firebase
	//firebase 초기화
	var config = {
		apiKey: "AIzaSyB7n03dwp9enZJNNxckocDrMAoYj1RhmKU",
	    authDomain: "rootmark-chat.firebaseapp.com",
	    databaseURL: "https://rootmark-chat.firebaseio.com",
	    projectId: "rootmark-chat",
	    storageBucket: "rootmark-chat.appspot.com",
	    messagingSenderId: "843207578967"
	  };
	
	firebase.initializeApp(config);
	  
	var db = firebase.database().ref();
	//초기화 끝
	
	//변수 , 상수 설정
	
	$(".chatting-contents").empty();//대화창 초기화
	
	startChat();
	//connect();
	jstreetable();
	myContextMenu();
	
	var lastDate = null;
	
	//firebase message 가져오기
	function startChat(){
		
		messages = db.child('messages/' + gid);
		showMessage(); //기존 채팅 메시지 출력
	}
	
	function showMessage(){
		$(".chatting-contents").empty();//대화창 초기화
		messages.on('child_added', makeMessage);
	};
	
	function makeMessage(snapshot){
		var message = snapshot.val();
		showTxMessage(message);
		$(".chat-element").scrollTop($(".chatting-contents").height());
	};

	function showTxMessage(message){
		var time = new Date(message.timestamp);
		var hour = time.getHours();
		var min = time.getMinutes();
		var ampm ;
		var ampm ;
		if(time>12){
			time -= 12;
			ampm ="pm";
		}else{
			ampm = "am"
		}
		
		var chat_div = "";
    	chat_div += '<img class="chatting-profile-img" onerror="this.src=\'/images/profile.png\'" src="/images/profile/' + message.profile + '">';
    	chat_div += '<div class="chatting-text-div">';
    	chat_div += '<p class="chatting-userid">';
    	chat_div += message.nname + '&nbsp;<span class="chatting-time">' + hour + "시&nbsp;" + min + '분&nbsp;' + ampm + '</span>';
    	chat_div += '</p>';
    	chat_div += '<span class="chatting-text">';
        chat_div += message.text;
        chat_div += '</span>';
        chat_div += '</div>';
        
        $(".chatting-contents").append(chat_div);
        $(".chat-element").scrollTop($(".chatting-contents").height());
		
	};
	
	
	var scrollTop = $('.chat-element').scrollTop();
	
	var scrollPos = $('.chat-element').scrollTop();
    var date_eq = $(".chatting-contents").children(".divider").length - 1;
    
	$('.chat-element').scroll(function() {
        var curScrollPos = $(this).scrollTop();
        var date_line = $(".divider:eq(" + date_eq + ")").position().top;

        if (curScrollPos > scrollPos) { //Scrolling Down
            if(date_line <= 35 ) {
                var temp = $(".divider:eq(" + date_eq + ") > span").text(); // 가장 맨 위의 내용
                //console.log("donw: " + date_line);
                //console.log(temp);
                $("#header-date").text(temp);
                if( date_eq < $(".chatting-contents").children(".divider").length - 1 ) { date_eq += 1; }
                
            }
        } else { //Scrolling Up
            if(date_line > 30 ) {
                if( date_eq > 0 ) { date_eq -= 1; }
                var temp = $(".divider:eq(" + date_eq + ") > span").text(); // 가장 맨 위의 내용
                //console.log("up: " + date_line);
                //console.log(temp);
                $("#header-date").text(temp);
                
            }
        }
        
        scrollPos = curScrollPos;
      /*  
        if($(this).scrollTop() == 0){
        	//console.log("scroll TOP");
        	
        	if(position > 0){
        		
        		
        		if(chatList.length - position > 50){
        			for(var index=position; index < position+50; index++){
        				chatList[index] = chatList[index].split('▣');
        				// <div id="2018-06-27" class="divider"><hr class="left"/><span>2018-06-27</span><hr class="right"/></div>
        				//console.log(chatList[index]);
        				var chatListIndex = chatList[index];
        				
        				var time =  chatListIndex[2].split("T");
        				
        				if(lastDate == null){
        					lastDate = time[0];
        					var Now = new Date();
        					var NowTime = Now.getFullYear();
        					if(Now.getMonth() < 10){
        						NowTime += '-0' + (Now.getMonth() + 1) ;
        					}else {
        						NowTime += '-' + (Now.getMonth() + 1) ;
        					}
        					NowTime += '-' + Now.getDate();
        					
        					var today = time[0];
        					//console.log("today" + NowTime);
        					if(NowTime == time[0]){
        						today = "Today";
        					}
        					
        					var date = '<div id="' + time[0]+ '" class="divider"><hr class="left"/><span>' + today + '</span><hr class="right"/></div>';
        					$(".chatting-contents").prepend(date);
        				}else if(lastDate != time[0]){
        					var date = '<div id="' + time[0]+ '" class="divider"><hr class="left"/><span>' + time[0] + '</span><hr class="right"/></div>';
        					$(".chatting-contents").prepend(date);
        					lastDate = time[0];
        				}
        				
        				time[1] = time[1].split(":");
        	        	var hour = time[1][0];
        	        	var min = time[1][1];
        	        	var ampm = "";
        	        	if(hour > 12) {
        	        		ampm = "PM";
        	        		hour -= 12;
        	        	}else {
        	        		ampm = "AM";
        	        	}
        				
        				var chat_list_div = "";
        				chat_list_div += '<img class="chatting-profile-img" onerror="this.src=\'/images/profile.png\'" src="/images/profile/' + chatListIndex[0] + '">';
        				chat_list_div += '<div class="chatting-text-div">';
        				chat_list_div += '<p class="chatting-userid">';
        				chat_list_div += chatListIndex[1] + '<span class="chatting-time">' + hour + "시&nbsp;" + min + '분&nbsp;' + ampm + '</span>';
        				chat_list_div += '</p>';
        				chat_list_div += '<span class="chatting-text">';
        				chat_list_div += chatListIndex[3];
        				chat_list_div += '</span>';
        				chat_list_div += '</div>';  	
        				
        	            //console.log(chat_list_div);
        	            $("#" + time[0]).after(chat_list_div);
        	            //$(".chat-element").scrollTop($(".chatting-contents").height());
        			}
        			position += 50;
        			$(this).scrollTop(50 * 42);
        		}else {
        			for(var index = position; index < chatList.length; index++){
        				chatList[index] = chatList[index].split('▣');
        				// <div id="2018-06-27" class="divider"><hr class="left"/><span>2018-06-27</span><hr class="right"/></div>
        				//console.log(chatList[index]);
        				var chatListIndex = chatList[index];
        				
        				var time =  chatListIndex[2].split("T");
        				
        				if(lastDate == null){
        					lastDate = time[0];
        					var Now = new Date();
        					var NowTime = Now.getFullYear();
        					if(Now.getMonth() < 10){
        						NowTime += '-0' + (Now.getMonth() + 1) ;
        					}else {
        						NowTime += '-' + (Now.getMonth() + 1) ;
        					}
        					NowTime += '-' + Now.getDate();
        					
        					var today = time[0];
        					//console.log("today" + NowTime);
        					if(NowTime == time[0]){
        						today = "Today";
        					}
        					
        					var date = '<div id="' + time[0]+ '" class="divider"><hr class="left"/><span>' + today + '</span><hr class="right"/></div>';
        					$(".chatting-contents").prepend(date);
        				}else if(lastDate != time[0]){
        					var date = '<div id="' + time[0]+ '" class="divider"><hr class="left"/><span>' + time[0] + '</span><hr class="right"/></div>';
        					$(".chatting-contents").prepend(date);
        					lastDate = time[0];
        				}
        				
        				time[1] = time[1].split(":");
        	        	var hour = time[1][0];
        	        	var min = time[1][1];
        	        	var ampm = "";
        	        	if(hour > 12) {
        	        		ampm = "PM";
        	        		hour -= 12;
        	        	}else {
        	        		ampm = "AM";
        	        	}
        				
        				var chat_list_div = "";
        				chat_list_div += '<img class="chatting-profile-img" onerror="this.src=\'/images/profile.png\'" src="/images/profile/' + chatListIndex[0] + '">';
        				chat_list_div += '<div class="chatting-text-div">';
        				chat_list_div += '<p class="chatting-userid">';
        				chat_list_div += chatListIndex[1] + '<span class="chatting-time">' + hour + "시&nbsp;" + min + '분&nbsp;' + ampm + '</span>';
        				chat_list_div += '</p>';
        				chat_list_div += '<span class="chatting-text">';
        				chat_list_div += chatListIndex[3];
        				chat_list_div += '</span>';
        				chat_list_div += '</div>';  	
        				
        	            //console.log(chat_list_div);
        	            $("#" + time[0]).after(chat_list_div);
        	            //$(".chat-element").scrollTop($(".chatting-contents").height());
        			}
        			$(this).scrollTop((chatList.length - position) * 42);
        			console.log((chatList.length - position) * 42);
        			position = -1;
        		}
        	}
        }*/
	});
	
	

});
// 채팅 메세지 전달
function sendMessage() {
	
    var str = $("#chat-textbox-text").html().trim();
    str = str.replace(/ /gi, '&nbsp;')
    str = str.replace(/\n|\r/g, '<br>');
    str = str.replace(/"/gi, '&uml;');
    if(str.length > 0) {
    	
    	messages.push({
    		'userid' : uid,
    		'nname' : nname,
    		'text' : str,
    		'timestamp': firebase.database.ServerValue.TIMESTAMP,
    		'profile' : profile
    		
    	})
    }
}
/*//채팅방 연결
function connect() {
    //console.log("connect");
    // WebSocketMessageBrokerConfigurer의 registerStompEndpoints() 메소드에서 설정한 endpoint("/endpoint")를 파라미터로 전달
    var ws = new SockJS("/endpoint");
    stompClient = Stomp.over(ws);
    stompClient.connect({}, function(frame) {
        //JSTREE 알림 메시지 ex) 누구님이 무엇을 수정했습니다
 		stompClient.subscribe('/subscribe/JSTREE/' + gid,function(message){
 			var body = JSON.parse(message.body);
            var whosend = body.nname;
            var content = body.content;
            
            if(nname == whosend){
            	
            }else{
	             
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
	             
	             ohSnap(content, {color: 'red'});
            }
        });
 		
 		stompClient.send('/online/' + gid , {}, JSON.stringify({nname: nname, status: "ON"}));
 		stompClient.subscribe('/subscribe/online/' + gid, function(message) {
 			var new_connect = JSON.parse(message.body);
 			var temp_member = new_connect.nname;
 			
 			var your_grid = ($('#' + temp_member).attr("data-grid") != null) ? $('#' + temp_member).attr("data-grid") : "3";
			var insertOnline = '<p id="'+ temp_member +'"' + ' class="member" data-grid="' +your_grid+ '">'
								+ '<i class="fas fa-circle online-ico"></i>'
								+ temp_member
						  +'</p>';
			
			//var $who = $('#' + temp_member).clone();
			$('#' + temp_member).remove();
			$('#online-member').prepend(insertOnline);
			
			if(your_grid == "1") {
				$("#" + temp_member).append('<i class="fas fa-crown group-master"></i>');
			}else if(your_grid == "2") {
				$("#" + temp_member).append('<i class="fas fa-chess-knight group-manager"></i>');
			}
 		});

 		stompClient.subscribe('/subscribe/offline/' + gid, function(message) {
 			var new_connect = JSON.parse(message.body);
 			var temp_member = new_connect.nname;

 			var your_grid = ($('#' + temp_member).attr("data-grid") != null) ? $('#' + temp_member).attr("data-grid") : "3";
			var insertOffline = '<p id="' + temp_member + '"' + ' class="member" data-grid="' +your_grid+ '">'
								+ '<i class="fas fa-circle offline-ico"></i>'
								+ temp_member
						  +'</p>';
			
			//var $who = $('#' + temp_member).clone();
			$('#' + temp_member).remove();
			$('#offline-member').prepend(insertOffline);
			
			if(your_grid == "1") {
				$("#" + temp_member).append('<i class="fas fa-crown group-master"></i>');
			}else if(your_grid == "2") {
				$("#" + temp_member).append('<i class="fas fa-chess-knight group-manager"></i>');
			}
 		});
 		
 		// Header Alarm socket connect
 		if(userid != null || userid != "") {
 			alarmConnect(stompClient, userid);
 		}
 		
    }, function(message) {
        stompClient.disconnect();

    });
    
    
    ws.onclose = function() {
    	disconnect();
        location.href = "/user/mybookmark.do";
    };
}
 

 
// 채팅방 연결 끊기
function disconnect() {
	stompClient.send("/offline/" + gid, {}, JSON.stringify({
       	nname: nname,
       	status: "OFF"
    }));
	
    stompClient.disconnect();
}
*/
/* Chatting End */



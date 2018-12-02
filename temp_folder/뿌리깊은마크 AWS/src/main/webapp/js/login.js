
/* Login / Roll_in / Password find script START */
function showRegisterForm() {
	clearForm();
	$('.findBox').fadeOut('fast');
    $('.loginBox').fadeOut('fast', function() {
		$('.registerBox').fadeIn('fast');
        $('.login-footer').fadeOut('fast', function() {
            $('.register-footer').fadeIn('fast');
        });
        $('.modal-title').html('Register with');
    });
    $('.error').removeClass('alert alert-danger').html('');
}

function showLoginForm() {
	clearForm();
    $('.findBox').fadeOut('fast');
    $('.registerBox').fadeOut('fast', function() {
        $('.social').fadeIn('fast');
        $('.division').fadeIn('fast');
        $('.loginBox').fadeIn('fast');
        $('.register-footer').fadeOut('fast', function() {
            $('.find-footer').fadeOut('fast', function() {
                $('.login-footer').fadeIn('fast')
            });
        });
        $('.modal-title').html('Login with');
    });
    $('.error').removeClass('alert alert-danger').html('');
}

function showFindForm() {
	clearForm();
    $('.social').fadeOut('fast');
    $('.division').fadeOut('fast');
    $('.loginBox').fadeOut('fast', function() {
        $('.findBox').fadeIn('fast');
        $('.login-footer').fadeOut('fast', function() {
            $('.find-footer').fadeIn('fast');
        })
        $('.modal-title').html('Find password')
    })
    $('.error').removeClass('alert alert-danger').html('');
}

function clearForm() {
	$("#uid_join").on("blur", email_check);
	$('#login-form')[0].reset();
	$('#rollin-form')[0].reset();
	$('#find-form')[0].reset();
	$('#uid_join').prop('readonly', false);
	$('#auth-div').css('display', 'none');
}

function openLoginModal() {
	clearForm();
    showLoginForm();
    setTimeout(function() {
        $('#loginModal').modal('show');
    }, 230);
}

function openRegisterModal() {
	clearForm();
    showRegisterForm();
    setTimeout(function() {
        $('#loginModal').modal('show');
    }, 230);
}

function openFindModal() {
	clearForm();
    showFindForm();
    setTimeout(function() {
        $('#loginModal').modal('show');
    }, 230);
}

function shakeModal_login() {
    $('#loginModal .modal-dialog').addClass('shake');
    $('.error').addClass('alert alert-danger').html("이메일/비밀번호가 존재하지 않습니다.");
    $('input[type="password"]').val('');
    setTimeout(function() {
        $('#loginModal .modal-dialog').removeClass('shake');
    }, 1000);
}

/*
 * 회원가입 Ajax + 유효성 확인
 */
function email_check(){
	var regex = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/; 
    
    // 이메일 길이 확인
    if(($('#uid_join').val().trim() == "") || !($('#uid_join').val().length >= 5 )){
        $('.error').addClass('alert alert-danger').html("이메일은 5자 이상 입력해야 합니다!");
        $('#uid_join').removeClass('clear_join');
        $('#uid_join').focus();
        return
    }
    // 이메일 형식 확인
    else if( !(regex.test($('#uid_join').val())) ) {
        $('.error').addClass('alert alert-danger').html("형식에 맞지 않은 이메일 입니다!");
        $('#uid_join').removeClass('clear_join');
        $('#uid_join').focus();
        return
    }
    else{
        $('.error').removeClass('alert alert-danger').html('');
        $('.error').removeClass('alert alert-success').html('');
    }
	
	// 1. User ID Check
    $.ajax({ 
		url:"joinus/checkuid.do",
        type:"POST",
        data: {	"uid": $('#uid_join').val() },
        dataType:"json", 
        beforeSend: function() {
            //마우스 커서를 로딩 중 커서로 변경
            $('html').css("cursor", "wait");
        },
        complete: function() {
            //마우스 커서를 원래대로 돌린다
            $('html').css("cursor", "auto");
        },
        success:function(data){
        	// 사용 가능한 ID
        	if(data.result == 'pass') {
        		$('.error').removeClass('alert alert-danger').html('');
        		
        		
        		if( $('#auth-div').css('display') == 'block' ) {
        			$('#uid_join').removeClass('clear_join');
        			return
        		}else {
        			$('#uid_join').removeClass('clear_join').addClass('clear_join');
        			$("#uid_join").prop('readonly', true);
            		$("#uid_join").off("blur");
            		$('#auth-div').css('display', 'block');
            		
        		}
        		
        		// 2. 가입자 이메일로 인증키 전송
        		$.ajax({ 
            		url:"joinus/emailsend.do",
                    type:"POST",
                    data: {	"uid": $('#uid_join').val() },
                    dataType:"json",
                    success:function(data){
                    	if(data.email == 'pass') {
                    		$.alert("해당 이메일로 인증키가 발송되었습니다!");
                    		$('#uid_join').addClass('clear_join');
                    	}
                    	else {
                    		$('#loginModal .modal-dialog').addClass('shake');
                    		$('.error').addClass('alert alert-danger').html("회원님의 실제 이메일을 입력해주세요!");
    	                    setTimeout(function() {
    	                    	$('#uid_join').removeClass('clear_join');
    	                        $('#loginModal .modal-dialog').removeClass('shake');
    	                    }, 500);
                    	}
                    }
                });
        	}
        	// 사용 불가능한 ID
        	else { 
        		$('#loginModal .modal-dialog').addClass('shake');
        		$("#uid_join").val("");
        		$("#uid_join").focus();
        		$('.error').removeClass('alert alert-danger').addClass('alert alert-danger').html("이미 가입된 이메일입니다");
                setTimeout(function() {
                	$('#uid_join').removeClass('clear_join');
                    $('#loginModal .modal-dialog').removeClass('shake');
                }, 500);
        	}
        },
        error:function(e){  
        	console.log("Error: " + e.responseText); 
        }
    });
}



$(function() {
    /* 회원가입 Ajax() START */
	// 이메일 형식 확인 함수
    $('#uid_join').focus(function() {
    	if($('#uid_join').prop('readonly') == true) { return }
    	$('.error').addClass('alert alert-success').html("회원님의 실제 이메일을 적어주세요!");
    });
    
    $('#uid_join').dblclick(function() {
    	if($('#uid_join').prop('readonly') == true) {
    		$.confirm({
    	        title: '이메일 수정',
    	        content: '회원가입하실 이메일을 수정하시겠습니까?',
    	        closeIcon: true,
    	        closeIconClass: 'fa fa-close',
    	        buttons: {
    	            formSubmit: {
    	                text: '완료',
    	                btnClass: 'btn-success',
    	                action: function () {
    	                	$('#agree-site-rule').prop('checked', false);
    	                	$("#rollinAjax").prop("disabled", true);
    	                	$("#uid_join").on("blur", email_check);
    	                	$('#uid_join').removeClass('clear_join');
    	                	$('#uid_join').prop('readonly', false);
    	                	$('#auth-div').css('display', 'none');              	
    	                	
    	                	$('#authcode_join').val('');
    	                	$('#authcode_join').removeClass('clear_join');
    	                	$('#authcode_join').prop('disabled', false);
    	                	$('#authcode_check').prop("disabled", false);
    	                }
    	            },
    	            '취소': {
    	                btnClass: 'btn-red',
    	                action: function () { /* close */ }
    	            }
    	        }
    	    }); 
    	}
    	else { return }
    });
	
	$("#uid_join").on("blur", email_check);
	
	// 이메일 authcode 형식 확인
    $('#authcode_join').keyup(function(){
    	// authcode 길이 확인
        if( ($('#authcode_join').val().length >= 11 ) ) {
             $('.error').addClass('alert alert-danger').html("형식에 맞지 않은 인증키 입니다.");
        }
        else{
             $('.error').removeClass('alert alert-danger').html('');
        }
    });
	
	//3.비밀번호 길이 확인 함수
    $('#pwd_join').focus(function() {
    	$('#agree-site-rule').prop('checked', false);
    	$("#rollinAjax").prop("disabled", true);
    	
    	$('#pwd_confirmation').val('');
    	$('#pwd_join').removeClass('clear_join')
    	$('#pwd_confirmation').removeClass('clear_join');
    });
    $('#pwd_join').keyup(function() {
    	
        if (($('#pwd_join').val().trim() == "") || !($('#pwd_join').val().length >= 5 && $('#pwd_join').val().length <= 15)) {
             $('.error').addClass('alert alert-danger').html("비밀번호는 5~15자로 입력해주세요");
             $('#pwd_join').removeClass('clear_join')
        } else {
             $('.error').removeClass('alert alert-danger').html('');
             $('#pwd_join').removeClass('clear_join').addClass('clear_join');
        }
    });
    
    
    //4.비밀번호 동일 확인 함수
    $('#pwd_confirmation').focus(function(){
    	$('#agree-site-rule').prop('checked', false);
    	$("#rollinAjax").prop("disabled", true);
    });
    
    $('#pwd_confirmation').keyup(function() {
        if (!($('#pwd_join').val() == $('#pwd_confirmation').val())) {
              $('.error').addClass('alert alert-danger').html("입력한 비밀번호가 다릅니다.");
              $('#pwd_confirmation').removeClass('clear_join');
        } else if(($('#pwd_join').val().trim() == "") || !($('#pwd_join').val().length >= 5 && $('#pwd_join').val().length <= 15)) {
              $('.error').addClass('alert alert-danger').html("비밀번호는 5자~15자 사이로 만들어야 합니다.");
              $('#pwd_confirmation').removeClass('clear_join');
        } else {
        	  $('.error').removeClass('alert alert-danger').html('');
        	  $('#pwd_confirmation').removeClass('clear_join').addClass('clear_join');
        }
    });
	
    // 재수정시 체크박스 해제
    $("#nname_join").focus(function(){
    	$('#agree-site-rule').prop('checked', false);
    	$("#rollinAjax").prop("disabled", true);
    	$('#nname_join').removeClass('clear_join');
    });
    $("#nname_join").keydown(function(event){
    	
    	// 공백 입력 방지
		if (event.keyCode == 32) {
			event.preventDefault();
			$.alert("공백 입력은 불가능합니다!");
			
			var remove_blank = $('#nname_join').val().replace(" ", "");
			$('#nname_join').val( remove_blank );

			return;
		}
		
    	var regex = /[a-zA-Z0-9|가-힣]{2,10}/;
		if( !(regex.test($('#nname_join').val())) ) {
			$('.error').addClass('alert alert-danger').html("<small><b>※닉네임은 변경 불가능하니 신중하게 적어주세요!</b></small><br>"+
															" 닉네임은 2~10자의 한글, 알파벳 대/소문자<br>또는 한글과 알파벳의 조합입니다!");
            $('#nname_join').removeClass('clear_vaildate');
            
		}else {
			$('.error').removeClass('alert alert-danger').html('');
			$('#nname_join').addClass('clear_vaildate');
		}
    });
    
    //5.닉네임 중복확인
	$("#nname_join").blur(function(){
		if( !$('#nname_join').hasClass('clear_vaildate') ) {
			$('#nname_join').removeClass('clear_join');
			$('#nname_join').focus();
			return
		}
		
		if (($('#nname_join').val().trim() == "") || !($('#nname_join').val().length >= 2 && $('#nname_join').val().length <= 10)) {
			$('.error').addClass('alert alert-danger').html("닉네임은 2~10자 입니다!");
			$('#nname_join').focus();
            $('#nname_join').removeClass('clear_join');
            
		}else {
			$.ajax({ 
	    		url:"joinus/checknname.do",
	            type:"POST",
	            data: {	"nname": $('#nname_join').val() },
	            dataType:"json", 
	            beforeSend: function() { $('html').css("cursor", "wait"); },
	            complete: function() { $('html').css("cursor", "auto"); },
	            success:function(data){
	            	if(data.result == 'pass') {
	            		$('.error').removeClass('alert alert-danger').html('');
	            		$('.error').addClass('alert alert-success').html("사용 가능한 닉네임입니다");
	            		$('#nname_join').removeClass('clear_join').addClass('clear_join');
	            	}
	            	else {
	            		$('#loginModal .modal-dialog').addClass('shake');
	            		$('.error').addClass('alert alert-danger').html("해당 닉네임이 이미 존재합니다.");
		                    setTimeout(function() {
		                        $('#loginModal .modal-dialog').removeClass('shake');
		                    }, 500);
	            		$("#nname_join").focus();
	            		$('#agree-site-rule').prop('checked', false);
	            		$("#rollinAjax").prop("disabled", true);
	            		$('#nname_join').removeClass('clear_vaildate');
	            		$('#nname_join').removeClass('clear_join');
	            	}
	            }
	        });
		}
    });
	
	//5.이메일 authcode 확인
	$("#authcode_join").keyup(function(){
        $.ajax({ 
    		url:"joinus/emailauth.do",
            type:"POST",
            data: {	"uid": $('#uid_join').val(), "authcode": $("#authcode_join").val() },
            dataType:"json", 
            beforeSend: function() { //마우스 커서를 로딩 중으로
                $('html').css("cursor", "wait");
            },
            complete: function() {
                $('html').css("cursor", "auto");
            },
            success:function(data){
            	if(data.auth == 'pass') {
            		$('.error').removeClass('alert alert-danger').html('');
            		$('.error').addClass('alert alert-success').html("인증키가 확인되었습니다.");
            		$("#authcode_join").prop("disabled", true);
            		$("#authcode_check").prop("disabled", true);
            		$('#authcode_join').removeClass('clear_join').addClass('clear_join');
            		
            	}else {
            		$('#loginModal .modal-dialog').addClass('shake');
            		$('#authcode_join').removeClass('clear_join');
            		$("#authcode_check").prop("disabled", false);
            		$('.error').addClass('alert alert-danger').html("잘못된 인증키 입니다.");
	                    setTimeout(function() {
	                        $('#loginModal .modal-dialog').removeClass('shake');
	                    }, 500);
            	}
            },
            error:function(e){  
            	console.log("Error: " + e.responseText); 
            }
        });
    });
	
	$("#authcode_check").prop("disabled", true);
	$("#rollinAjax").prop("disabled", true);
	
	$("#agree-site-rule").on("dblclick", function(){});
	$("#agree-site-rule").on("click", function(){ 
		if($(this).is(':checked')) {
			if($('#uid_join').hasClass('clear_join') && $('#pwd_join').hasClass('clear_join')
					&& $('#pwd_confirmation').hasClass('clear_join') && $('#nname_join').hasClass('clear_join')
					&& $('#authcode_join').hasClass('clear_join')) {
				$("#rollinAjax").prop("disabled", false);
			}else {
				$.alert("위 항목을 모두 정확히 작성해주세요");
				$("#agree-site-rule").prop("checked", false);
				$("#rollinAjax").prop("disabled", true);
			}
		}else {
			//console.log("xxxx");
			$("#rollinAjax").prop("disabled", true);
		}
	});
	
	$("#authcode_check").on("dblclick", function(){ });
    $("#authcode_check").on("click", function(){
    	$.ajax({ 
    		url:"joinus/emailsend.do",
            type:"POST",
            data: {	"uid": $('#uid_join').val() },
            dataType:"json",
           /*  crossDomain: false, */
            success:function(data){
            	if(data.email == 'pass') {
            		$.alert("인증 코드가 재발송되었습니다.");
            		$("#authcode_check").prop("disabled", true);
            	}else { 
            		$('#loginModal .modal-dialog').addClass('shake');
            		$('.error').addClass('alert alert-danger').html("잘못된 접근입니다. 확인 부탁드립니다.");
	                    setTimeout(function() {
	                        $('#loginModal .modal-dialog').removeClass('shake');
	                    }, 500);
            		$("#uid_join").focus();
            	}
            },
            error:function(e){  
            	console.log("Error: " + e.responseText); 
            }
        });
    });
	
	$("#rollinAjax").on("dblclick", function(){ });
    $("#rollinAjax").on("click", function(){
    	$.ajax({ 
    		url:"joinus/rollin.do",
            type:"POST",
            data: {	"uid": $('#uid_join').val(),
            		"nname": $('#nname_join').val(),
            		"pwd": $('#pwd_join').val()
            },
            dataType:"json",
           /*  crossDomain: false, */
            success:function(data){
            	console.log(data);
            	if(data.rollin == 'pass') {
            		location.replace("/"); 
            	}
            	else { 
            		$("#rollinAjax > strong").html("잘못된 접근입니다. 잠시후 다시 시도해주세요.");
            	}
            },
            error:function(e){  
            	console.log("Error: " + e.responseText); 
            }
        });
    });
    /* 회원가입 Ajax() END */
    
    
    
    
    /* 로그인 Ajax() START */
    /*
    // 이메일 형식 확인 함수
    $('#uid').keyup(function() {
        var regex = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/; 
        
        // 이메일 길이 확인
        if(($('#uid').val().trim() == "") || !($('#uid').val().length >= 5 )){
        	$('.error').addClass('alert alert-success').html('뿌리깊은나무@*****.***');
        } 
        // 이메일 형식 확인
        else if( !(regex.test($('#uid').val())) ) {
        	$('.error').addClass('alert alert-danger').html("형식에 맞지 않은 이메일 입니다.");
        }
        else{
        	$('.error').removeClass('alert alert-danger').html('');
            $('.error').addClass('alert alert-success').html('환영합니다');
        }
    })
    //비밀번호 길이 확인 함수
    $(function() {
        $('#pwd').keyup(function() {
            if (($('#pwd').val().trim() == "") || !($('#pwd').val().length >=5 && $('#pwd').val().length <= 15)) {
            	$('.error').addClass('alert alert-success').html("비밀번호를 5자~15자 사이로 입력해주세요.");
            } else {
                $('.error').removeClass('alert alert-danger').html('');
            }
         })
    });
    */
	$("#loginAjax").on("dblclick", function(){ });
    $("#loginAjax").on("click", function(){
    	$.ajax({ 
    		url:"security/login",
            type:"POST",
            data:{uid: $("#uid").val(), pwd: $("#pwd").val()},
            dataType:"json",
           /*  crossDomain: false, */
            success:function(data){
            	if(data.login == 'success') {
            		location.href = data.path;
            	
            	}else if(data.login == 'duplicate') {
            		$.alert("이미 로그인된 아이디입니다. 확인 부탁드립니다!");
            		setTimeout(function(){ location.replace("/"); }, 500);
            		
            	}else {  
	            	$("#pwd").val('');
	        		$("#login-form > strong").html("아이디 또는 비밀번호 오류입니다.");
            	}
            },
            error:function(e){  
            	console.log("Error: " + e.responseText); 
            }
        });
    });
    /* 로그인 Ajax() END */
});

/**************************  테이블  **********************************/
$(function() {
    $(document).on("click", ".url", function() {
        //console.log(this.dataset.url);
        window.open(this.dataset.url, '_blank');
    });
});


/* Login / Roll_in / Password find script END */


/*  Password find script START */
$(function() {
	$('#check_email_find').dblclick(function() { return });
	$('#check_email_find').click(function() {
		$.ajax({ 
    		url:"confirmuser.do",
            type:"POST",
            data:{uid: $("#uid_find").val()},
            dataType:"json",
            beforeSend: function() {$('html').css("cursor", "wait");},
            complete: function() {$('html').css("cursor", "auto");},
            success:function(data){
            	console.log(data);
            	if(data.result == 'member') {
            		$('#check_email_find').css('display', 'none');
            		$('.confrim_code_find').css('display', 'block');
            		$('#loginModal .modal-dialog').addClass('shake');
            		$('.error').addClass('alert alert-success').html("회원님 이메일로 인증키가 발송되었습니다.");
	                    setTimeout(function() {
	                        $('#loginModal .modal-dialog').removeClass('shake');
	                    }, 500);
            	}
            	else {
	            	$('#loginModal .modal-dialog').addClass('shake');
            		$('.error').addClass('alert alert-danger').html("가입된 회원이 아니십니다. 확인 부탁드립니다.");
	                    setTimeout(function() {
	                        $('#loginModal .modal-dialog').removeClass('shake');
	                    }, 500);
            	}
            },
            error:function(e){  
            	console.log("Error: " + e.responseText); 
            }
        });
	});
	
	$('#find-password').dblclick(function() { return });
	$('#find-password').click(function() {
		console.log('#find-password AJAX START');
		$.ajax({ 
    		url:"findpwd.do",
            type:"POST",
            data:{uid: $("#uid_find").val(), authcode: $("#authcode_find").val()},
            dataType:"json",
            beforeSend: function() {$('html').css("cursor", "wait");},
            complete: function() {$('html').css("cursor", "auto");},
            success:function(data){
            	if(data.result == 'success') {
            		alert('발송된 임시 비밀번호로 로그인 해주세요');
            		console.log(data.path);
            		location.href = data.path;
            	}
            	else {
            		$('#loginModal .modal-dialog').addClass('shake');
            		$('.error').addClass('alert alert-danger').html("잘못된 인증키입니다. 확인 부탁드립니다.");
	                    setTimeout(function() {
	                        $('#loginModal .modal-dialog').removeClass('shake');
	                    }, 500);
            	}
            },
            error:function(e){  
            	console.log("Error: " + e.responseText); 
            }
        });
	});
});
/*  Password find script END */




